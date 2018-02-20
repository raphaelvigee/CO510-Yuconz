package Yuconz.Entity;

import Yuconz.Model.Role;
import com.sallyf.sallyf.Authentication.UserInterface;
import com.sallyf.sallyf.Exception.FrameworkException;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Set;

@Entity
@Table
public class User implements UserInterface<String>, Serializable
{
    @Id
    @GenericGenerator(
            name = "sequence_yuconz_user_id",
            strategy = "Yuconz.Generator.YuconzIdGenerator"
    )
    @GeneratedValue(generator = "sequence_yuconz_user_id")
    private String id;

    private String username;

    private String firstName;

    private String lastName;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<AbstractRecord> records;

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

    public String getId()
    {
        return id;
    }

    public void setId(String yid)
    {
        this.id = yid;
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

    @Enumerated(EnumType.STRING)
    public Role getRole()
    {
        return role;
    }

    public void setRole(Role roles)
    {
        this.role = roles;
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

    public String getFullName()
    {
        return String.format("%s %s", getFirstName(), getLastName());
    }

    @Override
    public String toString()
    {
        return String.format("%s %s", getUsername(), getRole().toString());
    }

    private static String random(String chars, int l)
    {
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();

        while (salt.length() < l) {
            int index = (int) (rnd.nextFloat() * chars.length());
            salt.append(chars.charAt(index));
        }

        return salt.toString();
    }

    public static String generateYuconzId()
    {
        String chars = random("abcdefghijklmnopqrstuvwxyz", 3);
        String nums = random("1234567890", 3);

        return chars + nums;
    }

    public Set<AbstractRecord> getRecords()
    {
        return records;
    }

    public void setRecords(Set<AbstractRecord> records)
    {
        this.records = records;
    }
}
