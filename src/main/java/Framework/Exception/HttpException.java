package Framework.Exception;

import Framework.Server.Status;

public class HttpException extends FrameworkException
{
    private Status status;

    public HttpException(Status status)
    {
        this.status = status;
    }

    public Status getStatus()
    {
        return status;
    }
}
