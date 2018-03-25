package Yuconz.Model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * LoginRoles are the user's requested session role.
 */
public enum LoginRole
{
    EMPLOYEE("Employee"),
    HR_EMPLOYEE("HR Employee"),
    MANAGER("Manager"),
    DIRECTOR("Director"),
    REVIEWER("Reviewer");

    private String name;

    /**
     * New LoginRole.
     * @param name name of login role.
     */
    LoginRole(String name)
    {
        this.name = name;
    }

    /**
     * Gets LoginRole's name
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns list of all contained LoginRoles for current role.
     * @return List of contained roles.
     */
    public List<LoginRole> getContainedRoles()
    {
        switch (this) {
            case EMPLOYEE:
                return Arrays.asList(LoginRole.EMPLOYEE);
            case HR_EMPLOYEE:
                return Arrays.asList(LoginRole.EMPLOYEE, LoginRole.HR_EMPLOYEE);
            case MANAGER:
                return Arrays.asList(LoginRole.EMPLOYEE, LoginRole.MANAGER);
            case DIRECTOR:
                return Arrays.asList(LoginRole.values());
        }

        return new LinkedList<>();
    }
}
