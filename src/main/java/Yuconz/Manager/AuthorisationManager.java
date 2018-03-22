package Yuconz.Manager;

import Yuconz.Entity.Department;
import Yuconz.Entity.User;
import Yuconz.Model.LogType;
import Yuconz.Model.LoginRole;
import Yuconz.Model.UserRole;
import com.sallyf.sallyf.Container.ServiceInterface;
import com.sallyf.sallyf.Server.RuntimeBag;
import com.sallyf.sallyf.Server.RuntimeBagContext;
import org.eclipse.jetty.server.Request;

import java.util.function.Supplier;

/**
 * Service for managing authorisation.
 */
public class AuthorisationManager implements ServiceInterface
{
    private LogManager logManager;

    private YuconzAuthenticationManager authenticationManager;

    /**
     * New Authorisation manager
     *
     * @param logManager logManager
     */
    public AuthorisationManager(LogManager logManager)
    {
        this.logManager = logManager;
    }

    /**
     * Sets AuthorisationManager's Authentication Manager.
     *
     * @param authenticationManager
     */
    public void setAuthenticationManager(YuconzAuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Tests if user has the rights for the specified role.
     *
     * @param user         user to test on
     * @param expectedRole role to test for
     * @return true if has rights, else false
     */
    public boolean hasUserRights(User user, LoginRole expectedRole)
    {
        RuntimeBag runtimeBag = RuntimeBagContext.get();
        Request request = runtimeBag.getRequest();

        UserRole userRole = user.getRole();
        boolean isHr = user.getSection().getDepartment().equals(Department.HUMAN_RESOURCES);

        Supplier<Boolean> supplier = () -> {
            if (userRole == null) {
                return false;
            }

            switch (userRole) {
                case EMPLOYEE:
                    if (expectedRole == LoginRole.HR_EMPLOYEE) {
                        return isHr;
                    }

                    return expectedRole == LoginRole.EMPLOYEE;
                case MANAGER:
                    if (expectedRole == LoginRole.HR_EMPLOYEE) {
                        return isHr;
                    }

                    return expectedRole == LoginRole.MANAGER || expectedRole == LoginRole.EMPLOYEE || expectedRole == LoginRole.REVIEWER;
                case DIRECTOR:
                    return true;
            }

            return false;
        };

        boolean r = supplier.get();

        logManager.log(user, request.getRemoteAddr(), LogType.AUTHORISATION_CHECK, expectedRole.toString() + ", res:" + r);

        return r;

    }

    /**
     * Tests if a user's session role has the rights of an expected role.
     *
     * @param user         user to test
     * @param expectedRole role to test for
     * @return true if has rights, else false
     */
    public boolean hasSessionRights(User user, LoginRole expectedRole)
    {
        RuntimeBag runtimeBag = RuntimeBagContext.get();
        Request request = runtimeBag.getRequest();

        LoginRole loginRole = authenticationManager.getCurrentRole();

        boolean r = loginRole.getContainedRoles().contains(expectedRole);

        logManager.log(user, request.getRemoteAddr(), LogType.AUTHORISATION_CHECK, expectedRole.toString() + ", res: " + r);

        return r;
    }
}
