package Framework.Router;

import Framework.Server.Status;

public class RedirectResponse extends Response
{
    private String targetUrl;

    public RedirectResponse(String targetUrl, Status status)
    {
        this.targetUrl = targetUrl;
        setStatus(status);
    }

    public String getTargetUrl()
    {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl)
    {
        this.targetUrl = targetUrl;
    }
}
