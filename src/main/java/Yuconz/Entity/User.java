package Yuconz.Entity;

import Yuconz.Model.Role;
import com.sallyf.sallyf.Authentication.UserInterface;
import com.sallyf.sallyf.Exception.FrameworkException;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements UserInterface<Integer>
{
    private Integer id;

    private String username;

    private String firstName;

    private String lastName;

    private String password;

    private List<Role> roles = new ArrayList();

    public static String hash(String password)
    {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return new String(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new FrameworkException(e);
        }
    }

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    @Transient
    public String getFullName()
    {
        return String.format("%s %s", getFirstName(), getLastName());
    }

    @Override
    public String toString()
    {
        return String.format("%s %s", getUsername(), getRoles().toString());
    }
}
