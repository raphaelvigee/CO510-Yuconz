package Yuconz.FormRenderer;

import Yuconz.Form.DateType;
import com.sallyf.sallyf.Form.FormManager;
import com.sallyf.sallyf.Form.FormTypeInterface;
import com.sallyf.sallyf.Form.FormView;
import com.sallyf.sallyf.Form.Renderer.BaseFormRenderer;

/**
 * Renders dates in forms. (e.g. dd/mm/yy inputs)
 */
public class DateRenderer extends BaseFormRenderer<DateType, DateType.DateOptions>
{
    private FormManager manager;

    /**
     * Constructor.
     *
     * @param manager manager
     */
    public DateRenderer(FormManager manager)
    {
        this.manager = manager;
    }

    /**
     * Check if supported.
     *
     * @param form form
     * @return True or false
     */
    @Override
    public boolean supports(FormTypeInterface form)
    {
        return form.getClass().equals(DateType.class);
    }

    /**
     * Renders the widget.
     *
     * @param formView formView
     * @return rendered widget
     */
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
