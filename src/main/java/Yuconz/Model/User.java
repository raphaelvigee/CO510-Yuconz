package Yuconz.Model;

public class User extends com.sallyf.sallyf.Authentication.User
{
    private final Role role;

    public User(String username, String password, Role role)
    {
        super(username, password);
        this.role = role;
    }

    public Role getRole()
    {
        return role;
    }

    @Override
    public String toString()
    {
        return String.format("%s %s", getUsername(), getRole());
    }
}
