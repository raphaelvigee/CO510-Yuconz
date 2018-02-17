package Yuconz.Entity;

import Yuconz.Model.Role;
import com.sallyf.sallyf.Authentication.UserInterface;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements UserInterface<Integer>
{
    private Integer id;

    private String username;

    private String password;

    private List<Role> roles = new ArrayList();

    @Override
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer getId()
    {
        return id;
    }

    @Override
    public void setId(Integer id)
    {
        this.id = id;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public void setUsername(String username)
    {
        this.username = username;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public void setPassword(String password)
    {
        this.password = password;
    }

    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    public List<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    public void addRole(Role role)
    {
        roles.add(role);
    }

    public void removeRole(Role role)
    {
        roles.remove(role);
    }

    @Override
    public String toString()
    {
        return String.format("%s %s", getUsername(), getRoles().toString());
    }
}
