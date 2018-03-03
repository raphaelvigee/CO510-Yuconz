package Yuconz.Voter;

import Yuconz.Entity.User;
import Yuconz.Manager.AuthorisationManager;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.Model.Role;
import com.sallyf.sallyf.AccessDecisionManager.Voter.VoterInterface;
import com.sallyf.sallyf.Authentication.UserInterface;
import com.sallyf.sallyf.Server.RuntimeBag;

import java.util.Arrays;
import java.util.List;

public class PersonalDetailsVoter implements VoterInterface
{
    public static final String EDIT = "edit_user";

    public static final String CREATE = "create_user";

    public static final String LIST = "list_users";

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
        if (!Arrays.asList(EDIT, CREATE, LIST).contains(attribute)) {
            return false;
        }

        UserInterface currentUser = authenticationManager.getUser(runtimeBag);

        if (currentUser == null) {
            return false;
        }

        List<String> subjectFreeActions = Arrays.asList(CREATE, LIST);

        if (!(subject instanceof User) && !subjectFreeActions.contains(attribute)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean vote(String attribute, Object subject, RuntimeBag runtimeBag)
    {
        User user = (User) subject;

        User currentUser = (User) authenticationManager.getUser(runtimeBag);

        switch (attribute) {
            case EDIT:
                return canEdit(user, currentUser, runtimeBag);
            case CREATE:
                return canCreate(currentUser, runtimeBag);
            case LIST:
                return canList(currentUser, runtimeBag);
        }

        return false;
    }

    private boolean canList(User currentUser, RuntimeBag runtimeBag)
    {
        return isHR(currentUser, runtimeBag);
    }

    private boolean canCreate(User currentUser, RuntimeBag runtimeBag)
    {
        return isHR(currentUser, runtimeBag);
    }

    private boolean canEdit(User user, User currentUser, RuntimeBag runtimeBag)
    {
        if (user.equals(currentUser)) {
            return true;
        }

        return isHR(currentUser, runtimeBag);
    }

    private boolean isHR(User currentUser, RuntimeBag runtimeBag)
    {
        return authorisationManager.hasSessionRights(runtimeBag.getRequest(), currentUser, Role.HR_EMPLOYEE);
    }
}
