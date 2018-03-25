package Yuconz.Voter;

import Yuconz.Entity.User;
import Yuconz.Manager.AnnualReviewManager;
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

    private AnnualReviewManager annualReviewManager;

    /**
     * Generates new PersonalDetailsVoter.
     *
     * @param authenticationManager system AuthenticationManager
     * @param authorisationManager  system AuthorisationManager
     */
    public PersonalDetailsVoter(YuconzAuthenticationManager authenticationManager, AuthorisationManager authorisationManager, AnnualReviewManager annualReviewManager)
    {
        this.authenticationManager = authenticationManager;
        this.authorisationManager = authorisationManager;
        this.annualReviewManager = annualReviewManager;
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
                return canCreate();
            case LIST:
                return canList();
        }

        return false;
    }

    /**
     * Checks if user can get list of system users.
     *
     * @return
     */
    private boolean canList()
    {
        LoginRole currentRole = authenticationManager.getCurrentRole();

        switch (currentRole) {
            case REVIEWER:
            case DIRECTOR:
            case HR_EMPLOYEE:
                return true;
        }

        return false;
    }

    /**
     * Checks if user can create a system user.
     *
     * @return
     */
    private boolean canCreate()
    {
        LoginRole currentRole = authenticationManager.getCurrentRole();
        if (LoginRole.HR_EMPLOYEE.equals(currentRole)) {
            return true;
        }

        return false;
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
        return canEdit(user, currentUser);
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
        LoginRole currentRole = authenticationManager.getCurrentRole();

        if (user.equals(currentUser)) {
            switch (currentRole) {
                case EMPLOYEE:
                case MANAGER:
                case DIRECTOR:
                case HR_EMPLOYEE:
                    return true;
            }

            return false;
        }

        return isHR();
    }

    /**
     * Identifies if a user has the rights of an HR employee.
     *
     * @return
     */
    private boolean isHR()
    {
        LoginRole currentRole = authenticationManager.getCurrentRole();

        return currentRole.equals(LoginRole.HR_EMPLOYEE);
    }
}
