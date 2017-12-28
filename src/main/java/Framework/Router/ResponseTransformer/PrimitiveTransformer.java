package Framework.Router.ResponseTransformer;

import Framework.Router.Response;
import Framework.Router.ResponseTransformerInterface;
import Framework.Server.RuntimeBag;

public class PrimitiveTransformer implements ResponseTransformerInterface
{
    @Override
    public boolean supports(RuntimeBag runtimeBag, Object response)
    {
        return response instanceof String || response instanceof Number;
    }

    @Override
    public Response resolve(RuntimeBag runtimeBag, Object response)
    {
        return new Response(response.toString());
    }
}
