package Yuconz.Voter;

import Yuconz.Manager.YuconzAuthenticationManager;
import com.sallyf.sallyf.Authentication.Voter.AuthenticationVoter;

/**
 * Voter for Yuconz Authentication
 */
public class YuconzAuthenticationVoter extends AuthenticationVoter
{
    /**
     * Generates new Yuconz Authentication Voter.
     * @param authenticationService system authentication service.
     */
    public YuconzAuthenticationVoter(YuconzAuthenticationManager authenticationService)
    {
        super(authenticationService);
    }
}
