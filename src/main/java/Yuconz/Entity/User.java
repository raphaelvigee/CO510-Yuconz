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
import java.time.LocalDate;
import java.util.*;

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

    private LocalDate birthdate;

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

    /**
     * Gets User's employee ID
     * @return yuconz employee ID
     */
    public String getId()
    {
        return id;
    }

    /**
     * Sets User's Employee ID.
     * @param yid Yuconz employee ID
     */
    public void setId(String yid)
    {
        this.id = yid;
    }

    /**
     * Sets User's username.
     * @return username string
     */
    public String getUsername()
    {
        return getEmail();
    }

    /**
     * Sets User's username.
     * @param username username
     */
    public void setUsername(String username)
    {
        setEmail(username);
    }

    /**
     * Gets User's email address.
     * @return email address
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Sets User's email.
     * @param username employee email address
     */
    public void setEmail(String username)
    {
        this.email = username;
    }

    /**
     * Gets User's password (hashed).
     * @return hashed password
     */
    @Override
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets User's password (prehashed).
     * @param password password string (hashed)
     */
    @Override
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Gets User's current role.
     * @return current role
     */
    public Role getRole()
    {
        return role;
    }

    /**
     * Sets User's current role.
     * @param roles role
     */
    public void setRole(Role roles)
    {
        this.role = roles;
    }

    /**
     * Gets User's first name.
     * @return first name
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Sets User's first name.
     * @param firstName first name
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Gets User's last name.
     * @return last name
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Sets User's last name.
     * @param lastName last name
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * Gets User's full name ([firstname] [lastname]).
     * @return full name
     */
    public String getFullName()
    {
        return String.format("%s %s", getFirstName(), getLastName());
    }

    /**
     * Gets User's address, if none set then set an empty one and return it.
     * @return address
     */
    public Address getAddress()
    {
        if (address == null) {
            address = new Address();
        }

        return address;
    }

    /**
     * Sets User's address
     * @param address address
     */
    public void setAddress(Address address)
    {
        this.address = address;
    }

    /**
     * Gets User's birthdate.
     * @return birthdate
     */
    public LocalDate getBirthdate()
    {
        return birthdate;
    }

    /**
     * Sets User's birthdate.
     * @param birthdate birthdate
     */
    public void setBirthdate(LocalDate birthdate)
    {
        this.birthdate = birthdate;
    }

    /**
     * Gets User's phone number
     * @return phone number.
     */
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * Sets User's phone number.
     * @param phoneNumber phone number
     */
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets User's mobile number.
     * @return mobile number
     */
    public String getMobileNumber()
    {
        return mobileNumber;
    }

    /**
     * Sets User's mobile number.
     * @param mobileNumber mobile number
     */
    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    /**
     * Gets User's emergency contact name.
     * @return emergency contact name
     */
    public String getEmergencyContact()
    {
        return emergencyContact;
    }

    /**
     * Sets User's emergency contact.
     * @param emergencyContact emergency contact name
     */
    public void setEmergencyContact(String emergencyContact)
    {
        this.emergencyContact = emergencyContact;
    }

    /**
     * Gets User's emergency contact number.
     * @return Emergency contact number
     */
    public String getEmergencyContactNumber()
    {
        return emergencyContactNumber;
    }

    /**
     * Sets User's emergency contact number.
     * @param emergencyContactNumber emergency contact number to set to
     */
    public void setEmergencyContactNumber(String emergencyContactNumber)
    {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    /**
     * Returns list of all Employee records.
     * @return Set of Employee records
     */
    public Set<AbstractRecord> getRecords()
    {
        return records;
    }

    /**
     * Replaces all the Employee's records with those passed in as a parameter.
     * @param records Set of new user Records
     */
    public void setRecords(Set<AbstractRecord> records)
    {
        this.records = records;
    }

    /**
     * Returns user's section.
     * @return user's section
     */
    public Section getSection()
    {
        return section;
    }

    /**
     * Updates user's section.
     * @param section Section to set to
     */
    public void setSection(Section section)
    {
        this.section = section;
    }

    /**
     * Generates a hashmap of all the User's data.
     * @return User data hashmap
     */
    public Map<String, Object> toHashMap()
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("firstname", getFirstName());
        map.put("lastname", getLastName());
        map.put("email", getEmail());

        if (getBirthdate() != null) {
            Map<String, Object> birthdate = new LinkedHashMap<>();
            birthdate.put("day", getBirthdate().getDayOfMonth());
            birthdate.put("month", getBirthdate().getMonth());
            birthdate.put("year", getBirthdate().getYear());
            map.put("birthdate", birthdate);
        }

        map.put("address", getAddress().toHashMap());
        map.put("phone_number", getPhoneNumber());
        map.put("mobile_number", getMobileNumber());
        map.put("emergency_contact", getEmergencyContact());
        map.put("emergency_contact_number", getEmergencyContactNumber());

        return map;
    }

    /**
     * Updates User properties to those from provided hashmap.
     * @param map Hashmap of User properties
     */
    public void applyHashMap(Map<String, Object> map)
    {
        setFirstName((String) map.get("firstname"));
        setLastName((String) map.get("lastname"));
        setEmail((String) map.get("email"));
        setBirthdate((LocalDate) map.get("birthdate"));
        getAddress().applyHashMap((Map<String, Object>) map.get("address"));
        setPhoneNumber((String) map.get("phone_number"));
        setMobileNumber((String) map.get("mobile_number"));
        setEmergencyContact((String) map.get("emergency_contact"));
        setEmergencyContactNumber((String) map.get("emergency_contact_number"));
    }

    /**
     * Returns a hashed String of input text using SHA-256.
     * @param password input String
     * @return hashed String
     */
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
    /**
     * Generates a new randomised string of a specific length from a provided set of characters (strictly, a string).
     * @param chars Characters to use as the input source for randomisation
     * @param l Length of generated string
     * @return Generated string
     */
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

    /**
     * Generates new Yuconz-styled Employee ID.
     * @return Employee identifier string
     */
    public static String generateYuconzId()
    {
        String chars = random("abcdefghijklmnopqrstuvwxyz", 3);
        String nums = random("1234567890", 3);

        return chars + nums;
    }

    @Override
    public String toString()
    {
        if (role == null) {
            return getEmail();
        }

        return String.format("%s %s", getEmail(), getRole().toString());
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof User) {
            User user = (User) o;

            return user.getId().equals(getId());
        }

        return false;
    }
}
