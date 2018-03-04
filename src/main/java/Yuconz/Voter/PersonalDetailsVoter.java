package Yuconz.Voter;

import Yuconz.Entity.User;
import Yuconz.Manager.AuthorisationManager;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.Model.LoginRole;
import com.sallyf.sallyf.AccessDecisionManager.Voter.VoterInterface;
import com.sallyf.sallyf.Authentication.UserInterface;
import com.sallyf.sallyf.Server.RuntimeBag;

import java.util.Arrays;
import java.util.List;

/**
 * Voter for PersonalDetails actions.
 */
public class PersonalDetailsVoter implements VoterInterface
{
    public static final String EDIT = "edit_user";

    public static final String CREATE = "create_user";

    public static final String VIEW = "view_user";

    public static final String LIST = "list_users";

    private YuconzAuthenticationManager authenticationManager;

    private AuthorisationManager authorisationManager;

    /**
     * Generates new PersonalDetailsVoter.
     * @param authenticationManager system AuthenticationManager
     * @param authorisationManager system AuthorisationManager
     */
    public PersonalDetailsVoter(YuconzAuthenticationManager authenticationManager, AuthorisationManager authorisationManager)
    {
        this.authenticationManager = authenticationManager;
        this.authorisationManager = authorisationManager;
    }

    /**
     * Checks if voter has authority to vote.
     * @param attribute the name of the attribute being voted on
     * @param subject User being voted on
     * @param runtimeBag The runtimeBag itself.
     * @return true if supports
     */
    @Override
    public boolean supports(String attribute, Object subject, RuntimeBag runtimeBag)
    {
        if (!Arrays.asList(EDIT, CREATE, LIST, VIEW).contains(attribute)) {
            return false;
        }

        UserInterface currentUser = authenticationManager.getUser(runtimeBag);

        if (currentUser == null) {
            return false;
        }

        List<String> subjectFreeActions = Arrays.asList(CREATE, LIST);

        if (!(subject instanceof User) && !subjectFreeActions.contains(attribute)) {
            return false;
        }

        return true;
    }

    /**
     * Check if User has the requested rights for specified action.
     * @param attribute the name of the attribute being voted on
     * @param subject object being voted on
     * @param runtimeBag The runtimeBag itself.
     * @return
     */
    @Override
    public boolean vote(String attribute, Object subject, RuntimeBag runtimeBag)
    {
        User user = (User) subject;

        User currentUser = (User) authenticationManager.getUser(runtimeBag);

        switch (attribute) {
            case EDIT:
                return canEdit(user, currentUser, runtimeBag);
            case VIEW:
                return canView(user, currentUser, runtimeBag);
            case CREATE:
                return canCreate(currentUser, runtimeBag);
            case LIST:
                return canList(currentUser, runtimeBag);
        }

        return false;
    }

    /**
     * Checks if user can get list of system users.
     * @param currentUser user to test
     * @param runtimeBag The runtimeBag itself.
     * @return
     */
    private boolean canList(User currentUser, RuntimeBag runtimeBag)
    {
        return isHR(currentUser, runtimeBag);
    }

    /**
     * Checks if user can create a system user.
     * @param currentUser user to test
     * @param runtimeBag The runtimeBag itself.
     * @return
     */
    private boolean canCreate(User currentUser, RuntimeBag runtimeBag)
    {
        return isHR(currentUser, runtimeBag);
    }

    /**
     * Checks if user can view a system user.
     * @param user user to test
     * @param currentUser user to view
     * @param runtimeBag The runtimeBag itself.
     * @return
     */
    private boolean canView(User user, User currentUser, RuntimeBag runtimeBag)
    {
        return canEdit(user, currentUser, runtimeBag);
    }

    /**
     * Checks if a user can edit a system user.
     * @param user user to test
     * @param currentUser user to edit
     * @param runtimeBag The runtimeBag itself.
     * @return
     */
    private boolean canEdit(User user, User currentUser, RuntimeBag runtimeBag)
    {
        if (user.equals(currentUser)) {
            return true;
        }

        return isHR(currentUser, runtimeBag);
    }

    /**
     * Identifies if a user has the rights of an HR employee.
     * @param currentUser
     * @param runtimeBag The runtimeBag itself.
     * @return
     */
    private boolean isHR(User currentUser, RuntimeBag runtimeBag)
    {
        return authorisationManager.hasSessionRights(runtimeBag.getRequest(), currentUser, LoginRole.HR_EMPLOYEE);
    }
}
