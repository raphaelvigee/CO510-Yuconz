package Yuconz.Voter;

import Yuconz.Entity.User;
import Yuconz.Manager.AuthorisationManager;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.Model.LoginRole;
import com.sallyf.sallyf.AccessDecisionManager.Voter.VoterInterface;
import com.sallyf.sallyf.Authentication.UserInterface;
import com.sallyf.sallyf.Server.RuntimeBag;

import java.util.Arrays;

public class AuthorisationVoter implements VoterInterface
{
    public static final String HAS_RIGHTS = "has_rights";

    private YuconzAuthenticationManager authenticationManager;

    private AuthorisationManager authorisationManager;

    public AuthorisationVoter(YuconzAuthenticationManager authenticationManager, AuthorisationManager authorisationManager)
    {
        this.authenticationManager = authenticationManager;
        this.authorisationManager = authorisationManager;
    }

    @Override
    public boolean supports(String attribute, Object subject, RuntimeBag runtimeBag)
    {
        if (!Arrays.asList(HAS_RIGHTS).contains(attribute)) {
            return false;
        }

        UserInterface currentUser = authenticationManager.getUser(runtimeBag);

        if (currentUser == null) {
            return false;
        }

        if (!(subject instanceof String)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean vote(String attribute, Object subject, RuntimeBag runtimeBag)
    {
        LoginRole expectedRole = LoginRole.valueOf((String) subject);

        User currentUser = (User) authenticationManager.getUser(runtimeBag);

        switch (attribute) {
            case HAS_RIGHTS:
                return hasRights(runtimeBag, currentUser, expectedRole);
        }

        return false;
    }

    private boolean hasRights(RuntimeBag runtimeBag, User currentUser, LoginRole expectedRole)
    {
        return authorisationManager.hasSessionRights(runtimeBag.getRequest(), currentUser, expectedRole);
    }
}
