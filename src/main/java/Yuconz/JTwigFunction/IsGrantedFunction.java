package Yuconz.JTwigFunction;

import com.sallyf.sallyf.AccessDecisionManager.AccessDecisionManager;
import com.sallyf.sallyf.AccessDecisionManager.DecisionStrategy;
import com.sallyf.sallyf.JTwig.JTwigServiceFunction;
import com.sallyf.sallyf.Server.RuntimeBag;
import org.jtwig.functions.FunctionRequest;

/**
 * JTwig function for testing if user has expected privileges.
 */
public class IsGrantedFunction implements JTwigServiceFunction
{
    private AccessDecisionManager accessDecisionManager;

    /**
     * New IsGrantedFunction
     * @param accessDecisionManager the accessDeciscionManager
     */
    public IsGrantedFunction(AccessDecisionManager accessDecisionManager)
    {
        this.accessDecisionManager = accessDecisionManager;
    }

    /**
     * Name of function.
     * @return name of function.
     */
    @Override
    public String name()
    {
        return "is_granted";
    }

    /**
     * Execute function.
     * @param request page request
     * @return true if has privilege, else false
     */
    @Override
    public Object execute(FunctionRequest request)
    {
        request.minimumNumberOfArguments(2);
        request.maximumNumberOfArguments(4);

        RuntimeBag runtimeBag = (RuntimeBag) request.get(0);
        String attribute = (String) request.get(1);

        if (request.getNumberOfArguments() == 2) {
            return accessDecisionManager.vote(runtimeBag, attribute, null);
        }

        Object subject = request.get(2);

        if (request.getNumberOfArguments() == 3) {
            return accessDecisionManager.vote(runtimeBag, attribute, subject);
        }

        String strategyStr = (String) request.get(3);

        DecisionStrategy strategy = DecisionStrategy.valueOf(strategyStr);

        return accessDecisionManager.vote(runtimeBag, attribute, subject, strategy);
    }
}
