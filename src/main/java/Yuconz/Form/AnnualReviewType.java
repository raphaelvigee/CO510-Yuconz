package Yuconz.Form;

import Yuconz.Model.LoginRole;
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
public class AnnualReviewType extends AbstractFormType<AnnualReviewType.AnnualReviewOptions, Object>
{
    public class AnnualReviewOptions extends Options
    {
        public void setCurrentRole(LoginRole role)
        {
            put("role", role);
        }

        public LoginRole getCurrentRole()
        {
            return (LoginRole) get("role");
        }
    }

    @Override
    public AnnualReviewOptions createOptions()
    {
        return new AnnualReviewOptions();
    }

    /**
     * Builds a form structure.
     *
     * @param form form
     */
    @Override
    public void buildForm(Form<?, AnnualReviewOptions, Object> form)
    {
        FormBuilder<?, AnnualReviewOptions, Object> builder = form.getBuilder();

        LoginRole role = form.getOptions().getCurrentRole();

        boolean isHR = role.equals(LoginRole.HR_EMPLOYEE);
        boolean isEmployee = role.equals(LoginRole.EMPLOYEE);
        boolean isReviewer = role.equals(LoginRole.REVIEWER);

        builder
                .add("employeeComments", TextareaType.class, options -> {
                    options.setLabel("Employee Comments");
                    options.setDisabled(isHR || isReviewer);

                })
                .add("futureObjectivePlans", TextareaType.class, options -> {
                    options.setLabel("Future Objectives Plans");
                    options.setDisabled(isHR || isEmployee);

                })
                .add("trainingMentoringReview", TextareaType.class, options -> {
                    options.setLabel("Training Mentoring Review");
                    options.setDisabled(isHR || isEmployee);

                })
                .add("reviewerSummary", TextareaType.class, options -> {
                    options.setLabel("Reviewer Summary");
                    options.setDisabled(isHR || isEmployee);

                })
                .add("trainingMentoringDevelopment", TextareaType.class, options -> {
                    options.setLabel("Training Mentoring Development");
                    options.setDisabled(isHR || isEmployee);

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
    public <T extends FormTypeInterface<AnnualReviewOptions, Object>> Object resolveData(Form<T, AnnualReviewOptions, Object> form)
    {
        Map<String, Object> out = new LinkedHashMap<>();

        for (Form child : form.getChildren()) {
            out.put(child.getName(), child.resolveData());
        }

        return out;
    }
}
