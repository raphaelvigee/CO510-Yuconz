package Yuconz.JTwigFunction;

import Yuconz.Manager.YuconzAuthenticationManager;
import com.sallyf.sallyf.JTwig.JTwigServiceFunction;
import com.sallyf.sallyf.Server.RuntimeBag;
import org.jtwig.functions.FunctionRequest;

/**
 * JTwig function for fetching the current user.
 */
public class CurrentUserFunction implements JTwigServiceFunction
{
    private YuconzAuthenticationManager authenticationManager;

    /**
     * New CurrentUserFunction.
     * @param authenticationManager the system's authentication manager
     */
    public CurrentUserFunction(YuconzAuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Name of function.
     * @return name of function.
     */
    @Override
    public String name()
    {
        return "getUser";
    }

    /**
     * Execute function.
     * @param request page request
     * @return the current user
     */
    @Override
    public Object execute(FunctionRequest request)
    {
        request.minimumNumberOfArguments(1);
        request.maximumNumberOfArguments(1);

        return authenticationManager.getUser((RuntimeBag) request.get(0));
    }
}
