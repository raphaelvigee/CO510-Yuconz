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

public class UserType extends AbstractFormType<Options, Object>
{
    @Override
    public void buildForm(Form<?, Options, Object> form)
    {
        FormBuilder<?, Options, Object> builder = form.getBuilder();

        builder
                .add("firstname", TextType.class, options -> {
                    options.setLabel("First name");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("lastname", TextType.class, options -> {
                    options.setLabel("Last name");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("address", AddressType.class)
                .add("phone_number", TextType.class, options -> {
                    options.setLabel("Phone number");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("mobile_number", TextType.class, options -> {
                    options.setLabel("Mobile phone");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("emergency_contact", TextType.class, options -> {
                    options.setLabel("Emergency contact");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("emergency_contact_number", TextType.class, options -> {
                    options.setLabel("Emergency contact number");
                    options.getConstraints().add(new NotEmpty());
                });
    }

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