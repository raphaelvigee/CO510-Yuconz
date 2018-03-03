package Yuconz.Voter;

import Yuconz.Entity.User;
import Yuconz.Manager.AuthorisationManager;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.Model.Role;
import com.sallyf.sallyf.AccessDecisionManager.Voter.VoterInterface;
import com.sallyf.sallyf.Authentication.UserInterface;
import com.sallyf.sallyf.Server.RuntimeBag;

import java.util.Arrays;

public class PersonalDetailsVoter implements VoterInterface
{
    public static final String EDIT = "edit";

    private YuconzAuthenticationManager authenticationManager;

    private AuthorisationManager authorisationManager;

    public PersonalDetailsVoter(YuconzAuthenticationManager authenticationManager, AuthorisationManager authorisationManager)
    {
        this.authenticationManager = authenticationManager;
        this.authorisationManager = authorisationManager;
    }

    @Override
    public boolean supports(String attribute, Object subject, RuntimeBag runtimeBag)
    {
        if (!Arrays.asList(new String[]{EDIT}).contains(attribute)) {
            return false;
        }

        UserInterface currentUser = authenticationManager.getUser(runtimeBag);

        if (currentUser == null) {
            return false;
        }

        if (!(subject instanceof User)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean vote(String attribute, Object subject, RuntimeBag runtimeBag)
    {
        User user = (User) subject;

        switch (attribute) {
            case EDIT:
                return canEdit(user, runtimeBag);
        }

        return false;
    }

    private boolean canEdit(User user, RuntimeBag runtimeBag)
    {
        User currentUser = (User) authenticationManager.getUser(runtimeBag);

        if (user.equals(currentUser)) {
            return true;
        }

        return authorisationManager.hasRights(runtimeBag.getRequest(), currentUser, Role.HR_EMPLOYEE);
    }
}
