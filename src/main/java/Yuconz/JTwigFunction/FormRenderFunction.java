package Yuconz.JTwigFunction;

import com.sallyf.sallyf.Form.FormManager;
import com.sallyf.sallyf.Form.FormView;
import com.sallyf.sallyf.Form.Renderer.FormRenderer;

import java.util.function.BiFunction;

/**
 * JTwig function for rendering a form.
 */
public class FormRenderFunction extends AbstractFormRenderFunction
{
    public FormRenderFunction(FormManager formManager)
    {
        super(formManager);
    }

    @Override
    public String name()
    {
        return "form";
    }

    @Override
    public BiFunction<FormRenderer, FormView, Object> getRenderer()
    {
        return FormRenderer::renderRow;
    }
}
