package Yuconz.JTwigFunction;

import com.sallyf.sallyf.Form.FormManager;
import com.sallyf.sallyf.Form.FormView;
import com.sallyf.sallyf.JTwig.JTwigServiceFunction;
import org.jtwig.functions.FunctionRequest;

public class FormRenderFunction implements JTwigServiceFunction
{
    private FormManager formManager;

    public FormRenderFunction(FormManager formManager)
    {
        this.formManager = formManager;
    }

    @Override
    public String name()
    {
        return "form";
    }

    @Override
    public Object execute(FunctionRequest request)
    {
        request.minimumNumberOfArguments(1);
        request.maximumNumberOfArguments(1);

        return formManager.render((FormView) request.get(0));
    }
}
