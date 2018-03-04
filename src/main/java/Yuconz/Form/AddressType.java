package Yuconz.Form;

import com.sallyf.sallyf.Form.Constraint.NotEmpty;
import com.sallyf.sallyf.Form.Form;
import com.sallyf.sallyf.Form.FormBuilder;
import com.sallyf.sallyf.Form.FormTypeInterface;
import com.sallyf.sallyf.Form.Options;
import com.sallyf.sallyf.Form.Type.AbstractFormType;
import com.sallyf.sallyf.Form.Type.TextType;

import java.util.LinkedHashMap;
import java.util.Map;

public class AddressType extends AbstractFormType<Options, Object>
{
    /**
     * Builds a form structure.
     * @param form form
     */
    @Override
    public void buildForm(Form<?, Options, Object> form)
    {
        FormBuilder<?, Options, Object> builder = form.getBuilder();

        builder
                .add("line1", TextType.class, options -> {
                    options.setLabel("Address line 1");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("line2", TextType.class, options -> {
                    options.setLabel("Address line 2");
                })
                .add("city", TextType.class, options -> {
                    options.setLabel("City");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("county", TextType.class, options -> {
                    options.setLabel("County");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("postcode", TextType.class, options -> {
                    options.setLabel("Postcode");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("country", TextType.class, options -> {
                    options.setLabel("Country");
                    options.getConstraints().add(new NotEmpty());
                });
    }

    /**
     * Transforms the data of the form, into the representation of the data.
     * @param form form
     * @param <T> generic class
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
