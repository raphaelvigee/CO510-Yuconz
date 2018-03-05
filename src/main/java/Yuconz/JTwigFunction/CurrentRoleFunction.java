package Yuconz.JTwigFunction;

import Yuconz.Manager.YuconzAuthenticationManager;
import com.sallyf.sallyf.JTwig.JTwigServiceFunction;
import com.sallyf.sallyf.Server.RuntimeBag;
import com.sallyf.sallyf.Server.RuntimeBagContext;
import org.jtwig.functions.FunctionRequest;

import javax.servlet.http.HttpSession;

/**
 * JTwig function for fetching a user's current role.
 */
public class CurrentRoleFunction implements JTwigServiceFunction
{
    private YuconzAuthenticationManager authenticationManager;

    /**
     * New CurrentRoleFunction.
     * @param authenticationManager the system's authentication manager
     */
    public CurrentRoleFunction(YuconzAuthenticationManager authenticationManager)
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
        return "getRole";
    }

    /**
     * Execute function.
     * @param request page request
     * @return the user's current role
     */
    @Override
    public Object execute(FunctionRequest request)
    {
        request.minimumNumberOfArguments(0);
        request.maximumNumberOfArguments(0);

        return authenticationManager.getCurrentRole();
    }
}
