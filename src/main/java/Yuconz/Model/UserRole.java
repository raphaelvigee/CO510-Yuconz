package Yuconz.Model;

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
