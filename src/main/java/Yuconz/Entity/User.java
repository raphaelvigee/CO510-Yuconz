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
import java.util.Date;
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

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    @Embedded
    private Address address;

    @Temporal(TemporalType.DATE)
    private Date birthdate;

    private String phoneNumber;

    private String mobileNumber;

    private String emergencyContact;

    private String emergencyContactNumber;

    @ManyToOne
    private Section section;

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

    public String getUsername()
    {
        return getEmail();
    }

    public void setUsername(String username) {
        setEmail(username);
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String username)
    {
        this.email = username;
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

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public Date getBirthdate()
    {
        return birthdate;
    }

    public void setBirthdate(Date birthdate)
    {
        this.birthdate = birthdate;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    public String getEmergencyContact()
    {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact)
    {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyContactNumber()
    {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber)
    {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    @Override
    public String toString()
    {
        return String.format("%s %s", getEmail(), getRole().toString());
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

    public Section getSection()
    {
        return section;
    }

    public void setSection(Section section)
    {
        this.section = section;
    }
}
