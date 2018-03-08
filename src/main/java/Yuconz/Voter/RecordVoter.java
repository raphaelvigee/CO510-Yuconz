package Yuconz.Voter;

import Yuconz.Entity.AbstractRecord;
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
    public static final String VIEW = "view_record";

    public static final String CREATE_ANNUAL_REVIEW = "create_annual_review";

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
        if (!Arrays.asList(VIEW, CREATE_ANNUAL_REVIEW).contains(attribute)) {
            return false;
        }

        UserInterface currentUser = authenticationManager.getUser();

        if (currentUser == null) {
            return false;
        }

        if (attribute.equals(VIEW) && !(subject instanceof AbstractRecord)) {
            return false;
        }

        if (attribute.equals(CREATE_ANNUAL_REVIEW) && subject != null) {
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

        switch (attribute) {
            case CREATE_ANNUAL_REVIEW:
                return canCreateAnnualReview();
            case VIEW:
                return canView(record);
        }

        return false;
    }

    private boolean canCreateAnnualReview()
    {
        LoginRole currentRole = authenticationManager.getCurrentRole();

        return currentRole.equals(LoginRole.EMPLOYEE);
    }

    private boolean canView(AbstractRecord record)
    {
        return true; // TODO: Implement logic
    }
}
