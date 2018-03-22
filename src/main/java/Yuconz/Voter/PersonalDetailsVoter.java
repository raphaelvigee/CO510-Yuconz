package Yuconz.Voter;

import Yuconz.Entity.User;
import Yuconz.Manager.AuthorisationManager;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.Model.LoginRole;
import com.sallyf.sallyf.AccessDecisionManager.Voter.VoterInterface;
import com.sallyf.sallyf.Authentication.UserInterface;

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
     *
     * @param authenticationManager system AuthenticationManager
     * @param authorisationManager  system AuthorisationManager
     */
    public PersonalDetailsVoter(YuconzAuthenticationManager authenticationManager, AuthorisationManager authorisationManager)
    {
        this.authenticationManager = authenticationManager;
        this.authorisationManager = authorisationManager;
    }

    /**
     * Checks if voter has authority to vote.
     *
     * @param attribute the name of the attribute being voted on
     * @param subject   User being voted on
     * @return true if supports
     */
    @Override
    public boolean supports(String attribute, Object subject)
    {
        if (!Arrays.asList(EDIT, CREATE, LIST, VIEW).contains(attribute)) {
            return false;
        }

        UserInterface currentUser = authenticationManager.getUser();

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
     *
     * @param attribute the name of the attribute being voted on
     * @param subject   object being voted on
     * @return
     */
    @Override
    public boolean vote(String attribute, Object subject)
    {
        User user = (User) subject;

        User currentUser = (User) authenticationManager.getUser();

        switch (attribute) {
            case EDIT:
                return canEdit(user, currentUser);
            case VIEW:
                return canView(user, currentUser);
            case CREATE:
                return canCreate(currentUser);
            case LIST:
                return canList(currentUser);
        }

        return false;
    }

    /**
     * Checks if user can get list of system users.
     *
     * @param currentUser user to test
     * @return
     */
    private boolean canList(User currentUser)
    {
        return isHR(currentUser);
    }

    /**
     * Checks if user can create a system user.
     *
     * @param currentUser user to test
     * @return
     */
    private boolean canCreate(User currentUser)
    {
        LoginRole currentRole = authenticationManager.getCurrentRole();
        if (LoginRole.DIRECTOR.equals(currentRole)) {
            return false;
        }

        return isHR(currentUser);
    }

    /**
     * Checks if user can view a system user.
     *
     * @param user        user to test
     * @param currentUser user to view
     * @return
     */
    private boolean canView(User user, User currentUser)
    {
        LoginRole currentRole = authenticationManager.getCurrentRole();
        if (currentRole.equals(LoginRole.REVIEWER)) {
            return false;
        }

        if (user.equals(currentUser)) {
            return true;
        }

        return isHR(currentUser);
    }

    /**
     * Checks if a user can edit a system user.
     *
     * @param user        user to test
     * @param currentUser user to edit
     * @return
     */
    private boolean canEdit(User user, User currentUser)
    {
        if (user.equals(currentUser)) {
            return true;
        }

        LoginRole currentRole = authenticationManager.getCurrentRole();
        if (LoginRole.DIRECTOR.equals(currentRole)) {
            return false;
        }

        return isHR(currentUser);
    }

    /**
     * Identifies if a user has the rights of an HR employee.
     *
     * @param currentUser
     * @return
     */
    private boolean isHR(User currentUser)
    {
        return authorisationManager.hasSessionRights(currentUser, LoginRole.HR_EMPLOYEE);
    }
}
