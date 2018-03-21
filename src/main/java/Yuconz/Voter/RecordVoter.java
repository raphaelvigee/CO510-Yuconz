package Yuconz.Voter;

import Yuconz.Entity.AbstractRecord;
import Yuconz.Entity.AnnualReviewRecord;
import Yuconz.Entity.User;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.Model.LoginRole;
import com.sallyf.sallyf.AccessDecisionManager.Voter.VoterInterface;
import com.sallyf.sallyf.Authentication.UserInterface;

import java.util.Arrays;

/**
 * Voter for Records actions.
 */
public class RecordVoter implements VoterInterface
{
    public static final String LIST = "list_records";

    public static final String VIEW = "view_record";

    public static final String EDIT = "edit_record";

    private YuconzAuthenticationManager authenticationManager;

    public RecordVoter(YuconzAuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
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
        if (!Arrays.asList(VIEW, LIST, EDIT).contains(attribute)) {
            return false;
        }

        UserInterface currentUser = authenticationManager.getUser();

        if (currentUser == null) {
            return false;
        }

        if (Arrays.asList(VIEW, EDIT).contains(attribute) && !(subject instanceof AbstractRecord)) {
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
        AbstractRecord record = (AbstractRecord) subject;

        User currentUser = (User) authenticationManager.getUser();

        switch (attribute) {
            case LIST:
                return canList();
            case EDIT:
                return canEdit(currentUser, record);
            case VIEW:
                return canView(currentUser, record);
        }

        return false;
    }

    private boolean isEmployee()
    {
        LoginRole currentRole = authenticationManager.getCurrentRole();

        return currentRole.equals(LoginRole.EMPLOYEE);
    }

    private boolean canList()
    {
        return isEmployee();
    }

    private boolean canEdit(User currentUser, AbstractRecord record)
    {
        return false;
    }

    private boolean canView(User currentUser, AbstractRecord record)
    {
        return true; // TODO: Implement logic
    }
}
