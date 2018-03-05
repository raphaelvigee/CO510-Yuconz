package Yuconz.Voter;

import Yuconz.Entity.User;
import Yuconz.Manager.AuthorisationManager;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.Model.LoginRole;
import com.sallyf.sallyf.AccessDecisionManager.Voter.VoterInterface;
import com.sallyf.sallyf.Authentication.UserInterface;
import com.sallyf.sallyf.Server.RuntimeBag;

import java.util.Arrays;

/**
 * Voter for Authorisation.
 */
public class AuthorisationVoter implements VoterInterface
{
    public static final String HAS_RIGHTS = "has_rights";

    private YuconzAuthenticationManager authenticationManager;

    private AuthorisationManager authorisationManager;

    /**
     * Generates new authorisation voter.
     * @param authenticationManager authentication manager
     * @param authorisationManager authorisation manager
     */
    public AuthorisationVoter(YuconzAuthenticationManager authenticationManager, AuthorisationManager authorisationManager)
    {
        this.authenticationManager = authenticationManager;
        this.authorisationManager = authorisationManager;
    }

    /**
     * Checks if voter has authority to vote.
     * @param attribute the name of the attribute being voted on
     * @param subject User being voted on
     * @return true if supports
     */
    @Override
    public boolean supports(String attribute, Object subject)
    {
        if (!Arrays.asList(HAS_RIGHTS).contains(attribute)) {
            return false;
        }

        UserInterface currentUser = authenticationManager.getUser();

        if (currentUser == null) {
            return false;
        }

        if (!(subject instanceof String)) {
            return false;
        }

        return true;
    }

    /**
     * Check if User has the requested rights
     * @param attribute the name of the attribute being voted on
     * @param subject object being voted on
     * @return
     */
    @Override
    public boolean vote(String attribute, Object subject)
    {
        LoginRole expectedRole = LoginRole.valueOf((String) subject);

        User currentUser = (User) authenticationManager.getUser();

        switch (attribute) {
            case HAS_RIGHTS:
                return hasRights(currentUser, expectedRole);
        }

        return false;
    }

    /**
     * Checks if the User's Session has the selected authority role.
     * @param currentUser user being checked
     * @param expectedRole expected role for action
     * @return
     */
    private boolean hasRights(User currentUser, LoginRole expectedRole)
    {
        return authorisationManager.hasSessionRights(currentUser, expectedRole);
    }
}
