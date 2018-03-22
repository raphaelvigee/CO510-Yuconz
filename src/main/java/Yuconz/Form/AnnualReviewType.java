package Yuconz.Form;

import Yuconz.Entity.AnnualReviewRecord;
import Yuconz.Entity.User;
import Yuconz.Manager.AnnualReviewManager;
import Yuconz.Model.LoginRole;
import com.sallyf.sallyf.Container.ServiceInterface;
import com.sallyf.sallyf.Form.Form;
import com.sallyf.sallyf.Form.FormBuilder;
import com.sallyf.sallyf.Form.FormTypeInterface;
import com.sallyf.sallyf.Form.Options;
import com.sallyf.sallyf.Form.Type.AbstractFormType;
import com.sallyf.sallyf.Form.Type.CheckboxType;
import com.sallyf.sallyf.Form.Type.ChoiceType;
import com.sallyf.sallyf.Form.Type.TextareaType;

import java.util.*;

/**
 * Form type for initial employment details.
 */
public class AnnualReviewType extends AbstractFormType<AnnualReviewType.AnnualReviewOptions, Object> implements ServiceInterface
{
    private AnnualReviewManager annualReviewManager;

    private LoginRole currentRole;

    public AnnualReviewType(AnnualReviewManager annualReviewManager)
    {
        this.annualReviewManager = annualReviewManager;
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

        public void setAnnualReview(AnnualReviewRecord review)
        {
            put("annualReview", review);
        }

        public AnnualReviewRecord getAnnualReview()
        {
            return (AnnualReviewRecord) get("annualReview");
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

        AnnualReviewRecord annualReview = form.getOptions().getAnnualReview();

        List<User> reviewers1 = annualReviewManager.getCandidateReviewer1(annualReview.getReviewee());
        List<User> reviewers2 = annualReviewManager.getCandidateReviewer2(annualReview);
        reviewers2.add(0, null);

        builder
                .add("employeeComments", TextareaType.class, options -> {
                    options.setLabel("Employee Comments");
                    options.setDisabled(disableExcept(LoginRole.EMPLOYEE));

                })
                .add("previousYearReview", TextareaType.class, options -> {
                    options.setLabel("A review of the previous year’s achievements and outcomes.");
                    options.setDisabled(disableExcept(LoginRole.REVIEWER));

                })
                .add("previousYearTrainingMentoring", TextareaType.class, options -> {
                    options.setLabel("A review of the previous year’s training and mentoring and their outcomes.");
                    options.setDisabled(disableExcept(LoginRole.REVIEWER));

                })
                .add("futureObjectivePlans", TextareaType.class, options -> {
                    options.setLabel("Future objectives and plans: a preview of planned future performance: achievements/outcomes.");
                    options.setDisabled(disableExcept(LoginRole.REVIEWER));

                })
                .add("trainingMentoringReview", TextareaType.class, options -> {
                    options.setLabel("Training, mentoring, etc., required to enhance performance and to realize development potential.");
                    options.setDisabled(disableExcept(LoginRole.REVIEWER));

                })
                .add("reviewerSummary", TextareaType.class, options -> {
                    options.setLabel("Reviewer Summary");
                    options.setDisabled(disableExcept(LoginRole.REVIEWER));

                })
                .add("reviewer1", ChoiceType.class, options -> {
                    options.setLabel("Reviewer 1");
                    options.setExpanded(false);
                    options.setMultiple(false);
                    options.setDisabled(disableExcept(LoginRole.HR_EMPLOYEE));
                    options.setChoices(new LinkedHashSet<>(reviewers1));

                    options.setChoiceValueResolver(u -> u == null ? null : ((User) u).getId());
                    options.setChoiceLabelResolver(u -> u == null ? "None" : ((User) u).getFullName());
                })
                .add("reviewer2", ChoiceType.class, options -> {
                    options.setLabel("Reviewer 2");
                    options.setExpanded(false);
                    options.setMultiple(false);
                    options.setDisabled(disableExcept(LoginRole.HR_EMPLOYEE));
                    options.setChoices(new LinkedHashSet<>(reviewers2));

                    options.setChoiceValueResolver(u -> u == null ? null : ((User) u).getId());
                    options.setChoiceLabelResolver(u -> u == null ? "None" : ((User) u).getFullName());
                })
                .add("accepted", CheckboxType.class, options -> {
                    options.setLabel("Accepted");
                    options.setDisabled(disableExcept(LoginRole.HR_EMPLOYEE) || !annualReview.isReady());
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
