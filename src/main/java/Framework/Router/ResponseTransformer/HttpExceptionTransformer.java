package Framework.Router.ResponseTransformer;

import Framework.Exception.HttpException;
import Framework.Router.Response;
import Framework.Router.ResponseTransformerInterface;
import Framework.Server.RuntimeBag;
import Framework.Server.Status;

public class HttpExceptionTransformer implements ResponseTransformerInterface<HttpException, Response>
{
    @Override
    public boolean supports(RuntimeBag runtimeBag, Object response)
    {
        return response instanceof HttpException;
    }

    @Override
    public Response transform(RuntimeBag runtimeBag, HttpException response)
    {
        Status status = response.getStatus();

        return new Response(status.getDescription(), status, "text/html");
    }
}
