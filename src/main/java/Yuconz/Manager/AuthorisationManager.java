package Yuconz.Manager;

import Yuconz.Entity.User;
import Yuconz.Model.Role;
import com.sallyf.sallyf.Container.ServiceInterface;

public class AuthorisationManager implements ServiceInterface
{
    public boolean hasRights(User user, Role expectedRole)
    {
        switch (user.getRole()) {
            case EMPLOYEE:
                return expectedRole == Role.EMPLOYEE;
            case HR_EMPLOYEE:
                return expectedRole == Role.EMPLOYEE || expectedRole == Role.HR_EMPLOYEE;
            case MANAGER:
                return expectedRole == Role.EMPLOYEE || expectedRole == Role.MANAGER;
            case DIRECTOR:
                return expectedRole == Role.DIRECTOR;
        }

        return false;
    }
}
