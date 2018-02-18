package Yuconz.Manager;

import Yuconz.Entity.User;
import Yuconz.Model.LogType;
import Yuconz.Model.Role;
import com.sallyf.sallyf.Container.ServiceInterface;
import org.eclipse.jetty.server.Request;

public class AuthorisationManager implements ServiceInterface
{
    private LogManager logManager;

    public AuthorisationManager(LogManager logManager)
    {
        this.logManager = logManager;
    }

    public boolean hasRights(Request request, User user, Role expectedRole)
    {
        logManager.log(user, request.getRemoteAddr(), LogType.AUTHORISATION_CHECK, expectedRole.toString());

        switch (user.getRole()) {
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
