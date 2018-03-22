package Yuconz.Entity;

import Yuconz.Model.UserRole;
import com.github.javafaker.Faker;
import com.sallyf.sallyf.Authentication.UserInterface;
import com.sallyf.sallyf.Exception.FrameworkException;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Entity for system users (i.e. employees).
 * Stores all user data and references records.
 */
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

    @Enumerated(EnumType.STRING)
    private Section section;

    @Enumerated(EnumType.STRING)
    private UserRole role;

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
    @Enumerated(EnumType.STRING)
    public UserRole getRole()
    {
        return role;
    }

    /**
     * Sets User's current role.
     * @param roles role
     */
    public void setRole(UserRole roles)
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
     * Updates User properties to those from provided hashmap.
     * @param map Hashmap of User properties
     */
    public void applyHashMap(Map<String, Object> map)
    {
        setFirstName((String) map.get("firstName"));
        setLastName((String) map.get("lastName"));
        setEmail((String) map.get("email"));
        setBirthdate((LocalDate) map.get("birthdate"));
        getAddress().applyHashMap((Map<String, Object>) map.get("address"));
        setPhoneNumber((String) map.get("phoneNumber"));
        setMobileNumber((String) map.get("mobileNumber"));
        setEmergencyContact((String) map.get("emergencyContact"));
        setEmergencyContactNumber((String) map.get("emergencyContactNumber"));
        setSection((Section) map.get("section"));
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
            return Base64.getEncoder().encodeToString(hash);
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

    /**
     * Custom toString method.
     * @return The String to be returned.
     */
    @Override
    public String toString()
    {
        if (role == null) {
            return getEmail();
        }

        return String.format("%s %s", getEmail(), getRole().toString());
    }

    /**
     * Custom equals method.
     * @param o The object to be tested.
     * @return True or false.
     */
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof User) {
            User user = (User) o;
            return user.getId().equals(getId());
        }

        return false;
    }

    /**
     * Generates a User.
     * @return New Random User.
     */
    public static User bulk()
    {
        Faker faker = new Faker();

        User user = new User();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setBirthdate(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        user.setPassword(User.hash("123"));

        Address address = new Address();
        address.setLine1(faker.address().fullAddress());
        address.setLine2(faker.lorem().sentence(3));
        address.setCity(faker.address().city());
        address.setCounty(faker.address().state());
        address.setPostcode(faker.address().zipCode());
        address.setCountry(faker.address().country());
        user.setAddress(address);

        user.setPhoneNumber(faker.phoneNumber().phoneNumber());
        user.setMobileNumber(faker.phoneNumber().phoneNumber());
        user.setEmergencyContact(faker.name().fullName());
        user.setEmergencyContactNumber(faker.phoneNumber().phoneNumber());
        user.setSection(Section.random());
        user.setRole(UserRole.random());

        return user;
    }
}
