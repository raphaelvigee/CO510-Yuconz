package Yuconz.Manager;

import Yuconz.Entity.Department;
import Yuconz.Entity.User;
import Yuconz.Model.LogType;
import Yuconz.Model.LoginRole;
import Yuconz.Model.UserRole;
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

    public boolean hasUserRights(Request request, User user, LoginRole expectedRole)
    {
        logManager.log(user, request.getRemoteAddr(), LogType.AUTHORISATION_CHECK, expectedRole.toString());

        UserRole userRole = user.getRole();
        boolean isHr = user.getSection().getDepartment().equals(Department.HUMAN_RESOURCES);

        switch (userRole) {
            case EMPLOYEE:
                if (expectedRole == LoginRole.HR_EMPLOYEE) {
                    return isHr;
                }

                return expectedRole == LoginRole.EMPLOYEE;
            case MANAGER:
                if (expectedRole == LoginRole.HR_MANAGER || expectedRole == LoginRole.HR_EMPLOYEE) {
                    return isHr;
                }

                return expectedRole == LoginRole.MANAGER || expectedRole == LoginRole.EMPLOYEE;
            case DIRECTOR:
                return true;
        }

        return false;
    }

    public boolean hasSessionRights(Request request, User user, LoginRole expectedRole)
    {
        logManager.log(user, request.getRemoteAddr(), LogType.AUTHORISATION_CHECK, expectedRole.toString());

        LoginRole loginRole = authenticationManager.getCurrentRole(request);

        return loginRole.getContainedRoles().contains(expectedRole);
    }
}
