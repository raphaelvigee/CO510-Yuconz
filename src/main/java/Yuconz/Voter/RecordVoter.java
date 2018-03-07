package Yuconz.Voter;

import Yuconz.Entity.AbstractRecord;
import Yuconz.Entity.User;
import Yuconz.Manager.YuconzAuthenticationManager;
import com.sallyf.sallyf.AccessDecisionManager.Voter.VoterInterface;
import com.sallyf.sallyf.Authentication.UserInterface;

import java.util.Arrays;
import java.util.List;

/**
 * Voter for Records actions.
 */
public class RecordVoter implements VoterInterface
{
    public static final String VIEW = "view_record";

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
        if (!Arrays.asList(VIEW).contains(attribute)) {
            return false;
        }

        UserInterface currentUser = authenticationManager.getUser();

        if (currentUser == null) {
            return false;
        }

        List<String> subjectFreeActions = Arrays.asList();

        if (!(subject instanceof AbstractRecord) && !subjectFreeActions.contains(attribute)) {
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
            case VIEW:
                return canView(record, currentUser);
        }

        return false;
    }

    private boolean canView(AbstractRecord record, User currentUser)
    {
        return true; // TODO: Implement logic
    }
}
