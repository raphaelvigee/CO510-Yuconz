package Yuconz.JTwigFunction;

import com.sallyf.sallyf.Exception.FrameworkException;
import com.sallyf.sallyf.Form.FormManager;
import com.sallyf.sallyf.Form.FormTypeInterface;
import com.sallyf.sallyf.Form.FormView;
import com.sallyf.sallyf.Form.Renderer.FormRenderer;
import com.sallyf.sallyf.Form.RendererInterface;
import com.sallyf.sallyf.Form.Type.FormType;
import com.sallyf.sallyf.JTwig.JTwigServiceFunction;
import org.jtwig.functions.FunctionRequest;

import java.util.function.BiFunction;

/**
 * JTwig function for rendering a form start.
 */
public abstract class AbstractFormRenderFunction implements JTwigServiceFunction
{
    private FormManager formManager;

    /**
     * New FormRenderFunction
     *
     * @param formManager the form manager
     */
    public AbstractFormRenderFunction(FormManager formManager)
    {
        this.formManager = formManager;
    }

    /**
     * Execute function.
     *
     * @param request page request
     * @return the requested form, rendered
     */
    @Override
    public Object execute(FunctionRequest request)
    {
        request.minimumNumberOfArguments(1);
        request.maximumNumberOfArguments(1);

        FormView formView = (FormView) request.get(0);

        FormTypeInterface formType = formView.getForm().getFormType();

        if (formType instanceof FormType) {
            for (RendererInterface<?, ?> renderer : formManager.getRenderers()) {
                if (renderer instanceof FormRenderer) {
                    FormRenderer formRenderer = (FormRenderer) renderer;

                    return getRenderer().apply(formRenderer, formView);
                }
            }
        }

        throw new FrameworkException("Unable to render form start");
    }

    abstract public BiFunction<FormRenderer, FormView, Object> getRenderer();
}
