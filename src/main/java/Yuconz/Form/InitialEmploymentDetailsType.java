package Yuconz.Form;

import com.sallyf.sallyf.Form.Form;
import com.sallyf.sallyf.Form.FormBuilder;
import com.sallyf.sallyf.Form.FormTypeInterface;
import com.sallyf.sallyf.Form.Options;
import com.sallyf.sallyf.Form.Type.AbstractFormType;
import com.sallyf.sallyf.Form.Type.TextareaType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form type for initial employment details.
 */
public class InitialEmploymentDetailsType extends AbstractFormType<Options, Object>
{
    /**
     * Builds a form structure.
     *
     * @param form form
     */
    @Override
    public void buildForm(Form<?, Options, Object> form)
    {
        FormBuilder<?, Options, Object> builder = form.getBuilder();

        builder
                .add("interviewNotes", TextareaType.class, options -> {
                    options.setLabel("Interview Notes");

                })
                .add("startDate", DateType.class, options -> {
                    options.setLabel("Start date");

                });
    }

    /**
     * Transforms the data of the form, into the representation of the data.
     *
     * @param form form
     * @param <T>  generic class
     * @return representation of the data
     */
    @Override
    public <T extends FormTypeInterface<Options, Object>> Object resolveData(Form<T, Options, Object> form)
    {
        Map<String, Object> out = new LinkedHashMap<>();

        for (Form child : form.getChildren()) {
            out.put(child.getName(), child.resolveData());
        }

        return out;
    }
}
