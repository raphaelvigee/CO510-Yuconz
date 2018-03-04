package Yuconz.Model;

/**
 * Employee User role levels.
 */
public enum UserRole
{
    EMPLOYEE("Employee"), MANAGER("Manager"), DIRECTOR("Director");

    private String name;

    UserRole(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public static UserRole random()
    {
        return UserRole.values()[(int) (Math.random() * UserRole.values().length)];
    }
}
