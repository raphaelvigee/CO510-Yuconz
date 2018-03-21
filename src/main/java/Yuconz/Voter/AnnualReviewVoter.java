package Yuconz.Voter;

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
public class AnnualReviewVoter implements VoterInterface
{
    public static final String CREATE = "create_annual_review";

    public static final String EDIT = "edit_annual_review";

    private YuconzAuthenticationManager authenticationManager;

    public AnnualReviewVoter(YuconzAuthenticationManager authenticationManager)
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
        if (!Arrays.asList(CREATE, EDIT).contains(attribute)) {
            return false;
        }

        UserInterface currentUser = authenticationManager.getUser();

        if (currentUser == null) {
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
        AnnualReviewRecord annualReview = (AnnualReviewRecord) subject;

        switch (attribute) {
            case CREATE:
                return canCreate();
            case EDIT:
                return canEdit(annualReview);
        }

        return false;
    }

    private boolean canEdit(AnnualReviewRecord review)
    {
        User currentUser = (User) authenticationManager.getUser();

        if (currentUser.equals(review.getUser())) {
            if (isEmployee()) {
                return true;
            }
        }

        if (Arrays.asList(review.getReviewer1(), review.getReviewer2()).contains(currentUser)) {
            if (isReviewer()) {
                return true;
            }
        }

        return false;
    }

    private boolean isReviewer()
    {
        LoginRole currentRole = authenticationManager.getCurrentRole();

        return currentRole.equals(LoginRole.REVIEWER);
    }

    private boolean isEmployee()
    {
        LoginRole currentRole = authenticationManager.getCurrentRole();

        return currentRole.equals(LoginRole.EMPLOYEE);
    }


    private boolean canCreate()
    {
        return isEmployee();
    }
}
