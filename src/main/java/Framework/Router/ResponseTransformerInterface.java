package Framework.Router;

import Framework.Server.RuntimeBag;

public interface ResponseTransformerInterface
{
    boolean supports(RuntimeBag runtimeBag, Object response);

    Response resolve(RuntimeBag runtimeBag, Object response);
}
