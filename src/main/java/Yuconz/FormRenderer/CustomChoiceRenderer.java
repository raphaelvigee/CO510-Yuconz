package Yuconz.FormRenderer;

import com.sallyf.sallyf.Form.FormManager;
import com.sallyf.sallyf.Form.FormView;
import com.sallyf.sallyf.Form.Renderer.ChoiceRenderer;
import com.sallyf.sallyf.Form.Type.ChoiceType;

public class CustomChoiceRenderer extends ChoiceRenderer
{
    public CustomChoiceRenderer(FormManager manager)
    {
        super(manager);
    }

    @Override
    public String renderSelect(FormView<ChoiceType, ChoiceType.ChoiceOptions, ?> form)
    {
        String s = "<div class=\"selectWrapper\">";
        s += super.renderSelect(form);
        s += "</div>";

        return s;
    }
}
