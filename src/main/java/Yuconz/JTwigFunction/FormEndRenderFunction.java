package Yuconz.JTwigFunction;

import com.sallyf.sallyf.Form.FormManager;
import com.sallyf.sallyf.Form.FormView;
import com.sallyf.sallyf.Form.Renderer.FormRenderer;

import java.util.function.BiFunction;

/**
 * JTwig function for rendering a form start.
 */
public class FormEndRenderFunction extends AbstractFormRenderFunction
{
    public FormEndRenderFunction(FormManager formManager)
    {
        super(formManager);
    }

    @Override
    public String name()
    {
        return "form_end";
    }

    @Override
    public BiFunction<FormRenderer, FormView, Object> getRenderer()
    {
        return FormRenderer::renderFormEnd;
    }
}
