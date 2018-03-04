package Yuconz.FormRenderer;

import com.sallyf.sallyf.Form.FormManager;
import com.sallyf.sallyf.Form.FormView;
import com.sallyf.sallyf.Form.Renderer.ChoiceRenderer;
import com.sallyf.sallyf.Form.Type.ChoiceType;

/**
 * Renderer for custom select elements.
 */
public class CustomChoiceRenderer extends ChoiceRenderer
{
    /**
     * Constructor for CustomChoiceRenderer.
     * @param manager manager
     */
    public CustomChoiceRenderer(FormManager manager)
    {
        super(manager);
    }

    /**
     * Renders the select.
     * @param form form
     * @return rendered select
     */
    @Override
    public String renderSelect(FormView<ChoiceType, ChoiceType.ChoiceOptions, ?> form)
    {
        String s = "<div class=\"selectWrapper\">";
        s += super.renderSelect(form);
        s += "</div>";

        return s;
    }
}
