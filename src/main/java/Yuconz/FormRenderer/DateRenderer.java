package Yuconz.FormRenderer;

import Yuconz.Form.DateType;
import com.sallyf.sallyf.Form.FormManager;
import com.sallyf.sallyf.Form.FormTypeInterface;
import com.sallyf.sallyf.Form.FormView;
import com.sallyf.sallyf.Form.Renderer.BaseFormRenderer;

public class DateRenderer extends BaseFormRenderer<DateType, DateType.DateOptions>
{
    private FormManager manager;

    public DateRenderer(FormManager manager)
    {
        this.manager = manager;
    }

    @Override
    public boolean supports(FormTypeInterface form)
    {
        return form.getClass().equals(DateType.class);
    }

    @Override
    public String renderWidget(FormView<DateType, DateType.DateOptions, ?> formView)
    {

        String s = "<div class=\"row date\">";

        for (FormView child : formView.getChildren()) {
            s += manager.render(child);
        }

        s += "</div>";

        return s;
    }
}
