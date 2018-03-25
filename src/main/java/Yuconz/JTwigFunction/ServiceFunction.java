package Yuconz.JTwigFunction;

import com.sallyf.sallyf.Container.Container;
import com.sallyf.sallyf.JTwig.JTwigServiceFunction;
import org.jtwig.functions.FunctionRequest;

/**
 * JTwig function for fetching a Service from a Container.
 */
public class ServiceFunction implements JTwigServiceFunction
{
    private Container container;

    /**
     * New service function.
     *
     * @param container the container
     */
    public ServiceFunction(Container container)
    {
        this.container = container;
    }

    @Override
    public String name()
    {
        return "service";
    }

    @Override
    public Object execute(FunctionRequest request)
    {
        request.minimumNumberOfArguments(1);
        request.maximumNumberOfArguments(1);

        String name = (String) request.get(0);

        return container.find(name);
    }
}
