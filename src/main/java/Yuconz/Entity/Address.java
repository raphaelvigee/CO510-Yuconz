package Yuconz.Entity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

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

    public String getLine1()
    {
        return line1;
    }

    public void setLine1(String line1)
    {
        this.line1 = line1;
    }

    public String getLine2()
    {
        return line2;
    }

    public void setLine2(String line2)
    {
        this.line2 = line2;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCounty()
    {
        return county;
    }

    public void setCounty(String county)
    {
        this.county = county;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }
}
