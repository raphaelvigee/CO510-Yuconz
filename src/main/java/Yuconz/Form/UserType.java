package Yuconz.Form;

import Yuconz.Entity.Section;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.Model.LoginRole;
import Yuconz.Model.UserRole;
import com.sallyf.sallyf.Container.ServiceInterface;
import com.sallyf.sallyf.Form.Constraint.NotEmpty;
import com.sallyf.sallyf.Form.Form;
import com.sallyf.sallyf.Form.FormBuilder;
import com.sallyf.sallyf.Form.FormTypeInterface;
import com.sallyf.sallyf.Form.Options;
import com.sallyf.sallyf.Form.Type.AbstractFormType;
import com.sallyf.sallyf.Form.Type.ChoiceType;
import com.sallyf.sallyf.Form.Type.PasswordType;
import com.sallyf.sallyf.Form.Type.TextType;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Form type for user details.
 */
public class UserType extends AbstractFormType<UserType.UserOptions, Object> implements ServiceInterface
{
    private YuconzAuthenticationManager authenticationManager;

    public class UserOptions extends Options
    {
        public void setCreate(boolean c)
        {
            put("create", c);
        }

        public boolean isCreate()
        {
            return (boolean) get("create");
        }
    }

    public UserType(YuconzAuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserOptions createOptions()
    {
        return new UserOptions();
    }

    /**
     * Builds a form structure.
     *
     * @param form form
     */
    @Override
    public void buildForm(Form<?, UserOptions, Object> form)
    {
        UserOptions parentOptions = form.getOptions();

        FormBuilder<?, UserOptions, Object> builder = form.getBuilder();

        builder
                .add("firstName", TextType.class, options -> {
                    options.setLabel("First name");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("lastName", TextType.class, options -> {
                    options.setLabel("Last name");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("email", TextType.class, options -> {
                    options.setLabel("Email address");
                    options.getConstraints().add(new NotEmpty());
                    options.getAttributes().put("autocomplete", "off");
                })
                .add("password", PasswordType.class, options -> {
                    if (parentOptions.isCreate()) {
                        options.setLabel("Password");
                        options.getConstraints().add(new NotEmpty());
                    } else {
                        options.setLabel("Password (Leave blank to leave unchanged)");
                    }

                    options.put("mapped", false);
                    options.getAttributes().put("autocomplete", "off");
                })
                .add("birthdate", DateType.class, options -> {
                    options.setLabel("Birth date");
                })
                .add("address", AddressType.class)
                .add("phoneNumber", TextType.class, options -> {
                    options.setLabel("Phone number");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("mobileNumber", TextType.class, options -> {
                    options.setLabel("Mobile phone");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("emergencyContact", TextType.class, options -> {
                    options.setLabel("Emergency contact");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("emergencyContactNumber", TextType.class, options -> {
                    options.setLabel("Emergency contact number");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("section", ChoiceType.class, options -> {
                    options.setMultiple(false);
                    options.setExpanded(false);
                    options.setLabel("Section");

                    options.setChoices(new LinkedHashSet<>(Arrays.asList(Section.values())));
                    options.setChoiceLabelResolver(Object::toString);
                });

        if (authenticationManager.getCurrentRole().equals(LoginRole.HR_EMPLOYEE)) {
            builder
                    .add("role", ChoiceType.class, options -> {
                        options.setLabel("Role");
                        options.setExpanded(false);
                        options.setMultiple(false);

                        options.setChoices(new LinkedHashSet<>(Arrays.asList(UserRole.values())));
                        options.setChoiceLabelResolver(r -> ((UserRole) r).getName());
                        options.setChoiceValueResolver(r -> ((UserRole) r).getName());
                    });
        }
    }

    /**
     * Transforms the data of the form, into the representation of the data.
     * @param form form
     * @param <T>  generic class
     * @return representation of the data
     */
    @Override
    public <T extends FormTypeInterface<UserOptions, Object>> Object resolveData(Form<T, UserOptions, Object> form)
    {
        Map<String, Object> out = new LinkedHashMap<>();

        for (Form child : form.getChildren()) {
            out.put(child.getName(), child.resolveData());
        }

        return out;
    }
}
