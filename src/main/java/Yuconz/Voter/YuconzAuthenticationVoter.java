package Yuconz.Voter;

import Yuconz.Manager.YuconzAuthenticationManager;
import com.sallyf.sallyf.Authentication.Voter.AuthenticationVoter;

public class YuconzAuthenticationVoter extends AuthenticationVoter
{
    public YuconzAuthenticationVoter(YuconzAuthenticationManager authenticationService)
    {
        super(authenticationService);
    }
}
