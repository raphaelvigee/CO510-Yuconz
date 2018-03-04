package Yuconz.Model;

import com.sallyf.sallyf.FlashManager.FlashEntry;

public class FlashMessage extends FlashEntry<String>
{
    private String body;

    private String type;

    private String icon;

    public FlashMessage(String body, String type, String icon)
    {
        super(body);

        this.body = body;
        this.type = type;
        this.icon = icon;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }
}
