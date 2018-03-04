package Yuconz.Manager;

import Yuconz.Entity.User;
import Yuconz.Model.LogType;
import Yuconz.Model.Role;
import com.sallyf.sallyf.Container.ServiceInterface;
import org.eclipse.jetty.server.Request;

public class AuthorisationManager implements ServiceInterface
{
    private LogManager logManager;

    private YuconzAuthenticationManager authenticationManager;

    public AuthorisationManager(LogManager logManager)
    {
        this.logManager = logManager;
    }

    public void setAuthenticationManager(YuconzAuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }

    public boolean hasUserRights(Request request, User user, Role expectedRole)
    {
        logManager.log(user, request.getRemoteAddr(), LogType.AUTHORISATION_CHECK, expectedRole.toString());

        Role currentRole = user.getRole();

        return hasRights(currentRole, expectedRole);
    }

    public boolean hasSessionRights(Request request, User user, Role expectedRole)
    {
        logManager.log(user, request.getRemoteAddr(), LogType.AUTHORISATION_CHECK, expectedRole.toString());

        Role currentRole = authenticationManager.getCurrentRole(request);

        return hasRights(currentRole, expectedRole);
    }

    private boolean hasRights(Role currentRole, Role expectedRole)
    {
        if (currentRole == null) {
            return false;
        }

        switch (currentRole) {
            case EMPLOYEE:
                return expectedRole == Role.EMPLOYEE;
            case HR_EMPLOYEE:
                return expectedRole == Role.EMPLOYEE || expectedRole == Role.HR_EMPLOYEE;
            case MANAGER:
                return expectedRole == Role.EMPLOYEE || expectedRole == Role.MANAGER;
            case DIRECTOR:
                return true;
        }

        return false;
    }
}
