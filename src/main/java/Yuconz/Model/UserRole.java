package Yuconz.Model;

/**
 * Employee User role levels.
 */
public enum UserRole
{
    EMPLOYEE("Employee"), MANAGER("Manager"), DIRECTOR("Director");

    private String name;

    /**
     * New UserRole with name.
     *
     * @param name name of role
     */
    UserRole(String name)
    {
        this.name = name;
    }

    /**
     * Selects one randomised UserRole.
     *
     * @return user role
     */
    public static UserRole random()
    {
        return UserRole.values()[(int) (Math.random() * UserRole.values().length)];
    }

    /**
     * Gets name of role.
     *
     * @return name
     */
    public String getName()
    {
        return name;
    }
}
