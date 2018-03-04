package Yuconz.JTwigFunction;

import com.sallyf.sallyf.Form.FormManager;
import com.sallyf.sallyf.Form.FormView;
import com.sallyf.sallyf.JTwig.JTwigServiceFunction;
import org.jtwig.functions.FunctionRequest;

/**
 * JTwig function for rendering a form.
 */
public class FormRenderFunction implements JTwigServiceFunction
{
    private FormManager formManager;

    /**
     * New FormRenderFunction
     * @param formManager the form manager
     */
    public FormRenderFunction(FormManager formManager)
    {
        this.formManager = formManager;
    }

    /**
     * Name of function.
     * @return name of function.
     */
    @Override
    public String name()
    {
        return "form";
    }

    /**
     * Execute function.
     * @param request page request
     * @return the requested form, rendered
     */
    @Override
    public Object execute(FunctionRequest request)
    {
        request.minimumNumberOfArguments(1);
        request.maximumNumberOfArguments(1);

        return formManager.render((FormView) request.get(0));
    }
}
