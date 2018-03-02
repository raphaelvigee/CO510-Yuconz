package Yuconz.Model;

public enum Role
{
    EMPLOYEE("Employee"), HR_EMPLOYEE("HR Employee"), MANAGER("Manager"), DIRECTOR("Director");

    private String name;

    Role(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
