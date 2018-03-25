package Yuconz.Voter;

import Yuconz.Entity.AnnualReviewRecord;
import Yuconz.Entity.User;
import Yuconz.Manager.AnnualReviewManager;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.Model.LoginRole;
import com.sallyf.sallyf.AccessDecisionManager.Voter.VoterInterface;
import com.sallyf.sallyf.Authentication.UserInterface;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Voter for Records actions.
 */
public class AnnualReviewVoter implements VoterInterface
{
    public static final String LIST = "list_annual_reviews";

    public static final String VIEW = "view_annual_review";

    public static final String CREATE = "create_annual_review";

    public static final String EDIT = "edit_annual_review";

    public static final String SIGN = "sign_annual_review";

    private YuconzAuthenticationManager authenticationManager;

    private AnnualReviewManager annualReviewManager;

    public AnnualReviewVoter(YuconzAuthenticationManager authenticationManager, AnnualReviewManager annualReviewManager)
    {
        this.authenticationManager = authenticationManager;
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
        if (!Arrays.asList(LIST, CREATE, VIEW, EDIT, SIGN).contains(attribute)) {
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

        User currentUser = (User) authenticationManager.getUser();

        switch (attribute) {
            case LIST:
                return canList();
            case CREATE:
                return canCreate();
            case VIEW:
                return canView(currentUser, annualReview);
            case EDIT:
                return canEdit(currentUser, annualReview);
            case SIGN:
                return canSign(currentUser, annualReview);
        }

        return false;
    }

    private boolean canView(User currentUser, AnnualReviewRecord review)
    {
        if (currentUser.equals(review.getReviewee())) {
            return true;
        }

        LoginRole currentRole = authenticationManager.getCurrentRole();
        switch (currentRole) {
            case DIRECTOR:
            case HR_EMPLOYEE:
                return true;
        }

        if (currentRole.equals(LoginRole.REVIEWER)) {
            List<AnnualReviewRecord> incomplete = annualReviewManager.getIncomplete(currentUser);
            List<User> reviewedUsers = incomplete.stream().map(AnnualReviewRecord::getReviewee).collect(Collectors.toList());

            return reviewedUsers.contains(review.getReviewee());
        }

        return false;
    }

    private boolean canList()
    {
        return isHR();
    }

    private boolean canSign(User currentUser, AnnualReviewRecord review)
    {
        if (isReviewer()) {
            if (currentUser.equals(review.getReviewer1())) {
                return review.getReviewer1Signature() == null;
            }

            if (currentUser.equals(review.getReviewer2())) {
                return review.getReviewer2Signature() == null;
            }
        }

        if (isEmployee() && currentUser.equals(review.getReviewee())) {
            return review.getRevieweeSignature() == null;
        }

        return false;
    }

    private boolean canEdit(User currentUser, AnnualReviewRecord review)
    {
        if (review.isAccepted()) {
            return false;
        }

        if (isHR()) {
            return true;
        }

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

    private boolean isHR()
    {
        LoginRole currentRole = authenticationManager.getCurrentRole();

        return currentRole.equals(LoginRole.HR_EMPLOYEE);
    }

    private boolean canCreate()
    {
        User user = (User) authenticationManager.getUser();

        return isEmployee() && annualReviewManager.needsNew(user);
    }
}
