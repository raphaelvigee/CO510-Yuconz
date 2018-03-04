package Yuconz.Entity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;

@Entity
@Embeddable
public class Address
{
    private String line1;

    private String line2;

    private String postcode;

    private String city;

    private String county;

    private String country;

    /**
     * Get line 1.
     * @return line1
     */
    public String getLine1()
    {
        return line1;
    }

    /**
     * Set line 1.
     * @param line1 line1
     */
    public void setLine1(String line1)
    {
        this.line1 = line1;
    }

    /**
     * Get line 2.
     * @return line2
     */
    public String getLine2()
    {
        return line2;
    }

    /**
     * Set line 2.
     * @param line2 line2
     */
    public void setLine2(String line2)
    {
        this.line2 = line2;
    }

    /**
     * Get the postcode.
     * @return postcode
     */
    public String getPostcode()
    {
        return postcode;
    }

    /**
     * Set the postcode.
     * @param postcode postcode
     */
    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }

    /**
     * get the city.
     * @return city
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Set the city.
     * @param city city
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * Get the county.
     * @return county
     */
    public String getCounty()
    {
        return county;
    }

    /**
     * Set the county.
     * @param county county
     */
    public void setCounty(String county)
    {
        this.county = county;
    }

    /**
     * Get the country.
     * @return country
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * Set the country.
     * @param country country
     */
    public void setCountry(String country)
    {
        this.country = country;
    }

    /**
     * Converts to a hashmap.
     * @return hashmap
     */
    public Map<String, Object> toHashMap()
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("line1", getLine1());
        map.put("line2", getLine2());
        map.put("postcode", getPostcode());
        map.put("city", getCity());
        map.put("county", getCounty());
        map.put("country", getCountry());

        return map;
    }

    /**
     * Applies the data in the hashmap.
     * @param map map.
     */
    public void applyHashMap(Map<String, Object> map)
    {
        setLine1((String) map.get("line1"));
        setLine2((String) map.get("line2"));
        setPostcode((String) map.get("postcode"));
        setCity((String) map.get("city"));
        setCounty((String) map.get("county"));
        setCountry((String) map.get("country"));
    }
}
