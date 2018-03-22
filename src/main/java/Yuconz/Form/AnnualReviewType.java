package Yuconz.Form;

import Yuconz.Entity.User;
import Yuconz.Model.LoginRole;
import Yuconz.Model.UserRole;
import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.Container.ServiceInterface;
import com.sallyf.sallyf.Form.Form;
import com.sallyf.sallyf.Form.FormBuilder;
import com.sallyf.sallyf.Form.FormTypeInterface;
import com.sallyf.sallyf.Form.Options;
import com.sallyf.sallyf.Form.Type.AbstractFormType;
import com.sallyf.sallyf.Form.Type.CheckboxType;
import com.sallyf.sallyf.Form.Type.ChoiceType;
import com.sallyf.sallyf.Form.Type.TextareaType;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Form type for initial employment details.
 */
public class AnnualReviewType extends AbstractFormType<AnnualReviewType.AnnualReviewOptions, Object> implements ServiceInterface
{
    private Hibernate hibernate;

    private LoginRole currentRole;

    public AnnualReviewType(Hibernate hibernate)
    {
        this.hibernate = hibernate;
    }

    public class AnnualReviewOptions extends Options
    {
        public void setCurrentRole(LoginRole role)
        {
            put("currentRole", role);
        }

        public LoginRole getCurrentRole()
        {
            return (LoginRole) get("currentRole");
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

        currentRole = form.getOptions().getCurrentRole();

        Session session = hibernate.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query reviewersQuery = session.createQuery("from User where role in (:roles)")
                .setParameter("roles", Arrays.asList(UserRole.MANAGER, UserRole.DIRECTOR));

        LinkedHashSet<User> reviewers = new LinkedHashSet<>(reviewersQuery.getResultList());

        transaction.commit();

        builder
                .add("employeeComments", TextareaType.class, options -> {
                    options.setLabel("Employee Comments");
                    options.setDisabled(disableExcept(LoginRole.EMPLOYEE));

                })
                .add("futureObjectivePlans", TextareaType.class, options -> {
                    options.setLabel("Future Objectives Plans");
                    options.setDisabled(disableExcept(LoginRole.REVIEWER));

                })
                .add("trainingMentoringReview", TextareaType.class, options -> {
                    options.setLabel("Training Mentoring Review");
                    options.setDisabled(disableExcept(LoginRole.REVIEWER));

                })
                .add("reviewerSummary", TextareaType.class, options -> {
                    options.setLabel("Reviewer Summary");
                    options.setDisabled(disableExcept(LoginRole.REVIEWER));

                })
                .add("trainingMentoringDevelopment", TextareaType.class, options -> {
                    options.setLabel("Training Mentoring Development");
                    options.setDisabled(disableExcept(LoginRole.REVIEWER));

                })
                .add("reviewer1", ChoiceType.class, options -> {
                    options.setLabel("Reviewer 1");
                    options.setExpanded(false);
                    options.setMultiple(false);
                    options.setDisabled(disableExcept(LoginRole.HR_EMPLOYEE));
                    options.setChoices(reviewers);

                    options.setChoiceValueResolver(u -> ((User) u).getId());
                    options.setChoiceLabelResolver(u -> ((User) u).getFullName());
                })
                .add("reviewer2", ChoiceType.class, options -> {
                    options.setLabel("Reviewer 2");
                    options.setExpanded(false);
                    options.setMultiple(false);
                    options.setDisabled(disableExcept(LoginRole.HR_EMPLOYEE));
                    options.setChoices(reviewers);

                    options.setChoiceValueResolver(u -> ((User) u).getId());
                    options.setChoiceLabelResolver(u -> ((User) u).getFullName());
                })
                .add("accepted", CheckboxType.class, options -> {
                    options.setLabel("Accepted");
                    options.setDisabled(disableExcept(LoginRole.HR_EMPLOYEE));
                });
    }

    private boolean disableExcept(LoginRole... allowedRoles)
    {
        if (currentRole == null) {
            return true;
        }

        return !Arrays.asList(allowedRoles).contains(currentRole);
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
