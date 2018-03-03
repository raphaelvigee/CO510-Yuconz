package Yuconz.JTwigFunction;

import Yuconz.Manager.YuconzAuthenticationManager;
import com.sallyf.sallyf.JTwig.JTwigServiceFunction;
import com.sallyf.sallyf.Server.RuntimeBag;
import org.jtwig.functions.FunctionRequest;

import javax.servlet.http.HttpSession;

public class CurrentRoleFunction implements JTwigServiceFunction
{
    private YuconzAuthenticationManager authenticationManager;

    public CurrentRoleFunction(YuconzAuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String name()
    {
        return "getRole";
    }

    @Override
    public Object execute(FunctionRequest request)
    {
        request.minimumNumberOfArguments(1);
        request.maximumNumberOfArguments(1);

        RuntimeBag runtimeBag = (RuntimeBag) request.get(0);

        return authenticationManager.getCurrentRole(runtimeBag);
    }
}
