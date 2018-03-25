package Yuconz.JTwigFunction;

import com.sallyf.sallyf.AccessDecisionManager.AccessDecisionManager;
import com.sallyf.sallyf.AccessDecisionManager.DecisionStrategy;
import com.sallyf.sallyf.JTwig.JTwigServiceFunction;
import org.jtwig.functions.FunctionRequest;

/**
 * JTwig function for testing if user has expected privileges.
 */
public class IsGrantedFunction implements JTwigServiceFunction
{
    private AccessDecisionManager accessDecisionManager;

    /**
     * New IsGrantedFunction
     *
     * @param accessDecisionManager the accessDeciscionManager
     */
    public IsGrantedFunction(AccessDecisionManager accessDecisionManager)
    {
        this.accessDecisionManager = accessDecisionManager;
    }

    @Override
    public String name()
    {
        return "is_granted";
    }

    @Override
    public Object execute(FunctionRequest request)
    {
        request.minimumNumberOfArguments(1);
        request.maximumNumberOfArguments(3);

        String attribute = (String) request.get(0);

        if (request.getNumberOfArguments() == 1) {
            return accessDecisionManager.vote(attribute, null);
        }

        Object subject = request.get(1);

        if (request.getNumberOfArguments() == 2) {
            return accessDecisionManager.vote(attribute, subject);
        }

        String strategyStr = (String) request.get(2);

        DecisionStrategy strategy = DecisionStrategy.valueOf(strategyStr);

        return accessDecisionManager.vote(attribute, subject, strategy);
    }
}
