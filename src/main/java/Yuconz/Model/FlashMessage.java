package Yuconz.Model;

import com.sallyf.sallyf.FlashManager.FlashEntry;

/**
 * Holds flash message data.
 */
public class FlashMessage extends FlashEntry<String>
{
    private String body;

    private String type;

    private String icon;

    /**
     * New FlashMessage.
     * @param body details
     * @param type message type
     * @param icon message icon
     */
    public FlashMessage(String body, String type, String icon)
    {
        super(body);

        this.body = body;
        this.type = type;
        this.icon = icon;
    }

    /**
     * Gets FlashMessage's body (detail)
     * @return body text (details)
     */
    public String getBody()
    {
        return body;
    }

    /**
     * Sets FlashMessage's body (detail)
     * @param body body text (details)
     */
    public void setBody(String body)
    {
        this.body = body;
    }

    /**
     * Gets FlashMessage's Type
     * @return type
     */
    public String getType()
    {
        return type;
    }

    /**
     * Sets FlashMessage's Type
     * @param type type
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * Gets FlashMessage's Icon
     * @return icon
     */
    public String getIcon()
    {
        return icon;
    }

    /**
     * Sets FlashMessage's Icon
     * @return Micon to set
     */
    public void setIcon(String icon)
    {
        this.icon = icon;
    }
}
