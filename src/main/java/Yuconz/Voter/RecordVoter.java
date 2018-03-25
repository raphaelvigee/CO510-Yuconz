package Yuconz.Voter;

import Yuconz.Entity.AbstractRecord;
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
public class RecordVoter implements VoterInterface
{
    public static final String LIST = "list_records";

    public static final String VIEW = "view_record";

    public static final String EDIT = "edit_record";

    private YuconzAuthenticationManager authenticationManager;

    private AnnualReviewVoter annualReviewVoter;

    private AnnualReviewManager annualReviewManager;

    public RecordVoter(YuconzAuthenticationManager authenticationManager, AnnualReviewVoter annualReviewVoter, AnnualReviewManager annualReviewManager)
    {
        this.authenticationManager = authenticationManager;
        this.annualReviewVoter = annualReviewVoter;
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
        if (!Arrays.asList(VIEW, LIST, EDIT).contains(attribute)) {
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
        User currentUser = (User) authenticationManager.getUser();

        if (subject instanceof User) {
            User user = (User) subject;

            switch (attribute) {
                case LIST:
                    return canList(currentUser, user);
            }
        }

        if (subject instanceof AbstractRecord) {
            AbstractRecord record = (AbstractRecord) subject;

            switch (attribute) {
                case EDIT:
                    return canEdit(currentUser, record);
                case VIEW:
                    return canView(currentUser, record);
            }
        }

        return false;
    }

    private boolean canList(User currentUser, User user)
    {
        if (currentUser.equals(user)) {
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

            return reviewedUsers.contains(user);
        }

        return false;
    }

    private boolean isEmployee()
    {
        LoginRole currentRole = authenticationManager.getCurrentRole();

        return currentRole.equals(LoginRole.EMPLOYEE);
    }

    private boolean canEdit(User currentUser, AbstractRecord record)
    {
        if (record instanceof AnnualReviewRecord) {
            return annualReviewVoter.vote(AnnualReviewVoter.EDIT, record);
        }

        return false;
    }

    private boolean canView(User currentUser, AbstractRecord record)
    {
        if (record instanceof AnnualReviewRecord) {
            return annualReviewVoter.vote(AnnualReviewVoter.VIEW, record);
        }

        LoginRole currentRole = authenticationManager.getCurrentRole();
        switch (currentRole) {
            case DIRECTOR:
            case HR_EMPLOYEE:
                return true;
        }

        return record.getUser().equals(currentUser);
    }
}
