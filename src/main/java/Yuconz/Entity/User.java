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

    public void setUsername(String username)
    {
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
    public UserRole getRole()
    {
        return role;
    }

    public void setRole(UserRole roles)
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
        if (address == null) {
            address = new Address();
        }

        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public LocalDate getBirthdate()
    {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate)
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
        if (role == null) {
            return getEmail();
        }

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
        map.put("section", getSection());

        return map;
    }

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
        setSection((Section) map.get("section"));
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
