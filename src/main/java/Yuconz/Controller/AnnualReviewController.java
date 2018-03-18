package Yuconz.Controller;

import Yuconz.App;
import Yuconz.Entity.AnnualReviewRecord;
import Yuconz.Entity.InitialEmploymentDetailsRecord;
import Yuconz.Entity.User;
import Yuconz.Form.AnnualReviewType;
import Yuconz.FormUtils;
import Yuconz.Manager.RecordManager;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.Model.FlashMessage;
import Yuconz.Model.LoginRole;
import Yuconz.ParameterResolver.RecordResolver;
import Yuconz.SecurityHandler.LoginRedirectHandler;
import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.Annotation.Requirement;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Authentication.Annotation.Security;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.FlashManager.FlashManager;
import com.sallyf.sallyf.Form.Form;
import com.sallyf.sallyf.Form.FormBuilder;
import com.sallyf.sallyf.Form.Type.FormType;
import com.sallyf.sallyf.Form.Type.SubmitType;
import com.sallyf.sallyf.JTwig.JTwigResponse;
import com.sallyf.sallyf.Router.ParameterResolver;
import com.sallyf.sallyf.Router.RouteParameters;
import com.sallyf.sallyf.Server.Method;
import com.sallyf.sallyf.Server.RuntimeBag;
import com.sallyf.sallyf.Utils.MapUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.sallyf.sallyf.Utils.MapUtils.entry;

@Security(value = "is_granted('authenticated')", handler = LoginRedirectHandler.class)
@Route(path = "/annual-review")
public class AnnualReviewController extends BaseController
{
    @Route(path = "/create", methods = {Method.GET, Method.POST})
    @Security("is_granted('create_annual_review')")
    public Object create(User user, Hibernate hibernate, FlashManager flashManager, YuconzAuthenticationManager authenticationManager, RecordManager recordManager)
    {
        AnnualReviewRecord lastAnnualReview = recordManager.getLastRecord(user, AnnualReviewRecord.class);

        AnnualReviewRecord review = new AnnualReviewRecord();

        if (lastAnnualReview != null) {
            LocalDate start = lastAnnualReview.getPeriodEnd().plusDays(1);
            review.setPeriodStart(start);
            review.setPeriodEnd(start.plusYears(1));
        } else {
            InitialEmploymentDetailsRecord lastIED = recordManager.getLastRecord(user, InitialEmploymentDetailsRecord.class);

            LocalDate start = lastIED.getStartDate();
            review.setPeriodStart(start);
            review.setPeriodEnd(start.plusYears(1));
        }

        Form<FormType, FormType.FormOptions, Object> form = createFormBuilder()
                .add("review", AnnualReviewType.class, options -> {
                    options.setCurrentRole(authenticationManager.getCurrentRole());
                })
                .add("submit", SubmitType.class, options -> {
                    options.setLabel("Submit");
                })
                .getForm();

        form.handleRequest();

        if (form.isSubmitted() && form.isValid()) {
            Map<String, Object> data = (Map<String, Object>) form.getChild("review").resolveData();
            FormUtils.sanitize(data, new String[]{"employeeComments"});

            review.setUser(user);
            review.setReviewer1(user); // TODO: unfake

            review.apply(data);

            Session session = hibernate.getCurrentSession();

            Transaction transaction = session.beginTransaction();
            review = (AnnualReviewRecord) session.merge(review);

            session.flush();
            transaction.commit();

            flashManager.addFlash(new FlashMessage("New Annual review created", "success", "check"));

            return this.redirectToRoute("AppController.index");
        }

        return new JTwigResponse("views/record/form.twig", MapUtils.createHashMap(
                entry("review", review),
                entry("form", form.createView())
        ));
    }

    @Route(path = "/{record}/edit", methods = {Method.GET, Method.POST}, requirements = {
            @Requirement(name = "record", requirement = App.RECORD_REGEX)
    })
    @ParameterResolver(name = "record", type = RecordResolver.class)
    @Security("is_granted('edit_record', record)")
    public Object edit(RuntimeBag runtimeBag, RouteParameters routeParameters, User user, Hibernate hibernate, FlashManager flashManager, YuconzAuthenticationManager authenticationManager)
    {
        AnnualReviewRecord review = (AnnualReviewRecord) routeParameters.get("record");

        LoginRole currentRole = authenticationManager.getCurrentRole();

        FormBuilder<FormType, FormType.FormOptions, Object> builder = createFormBuilder()
                .add("review", AnnualReviewType.class, options -> {
                    options.setCurrentRole(currentRole);
                })
                .add("submit", SubmitType.class, options -> {
                    options.setLabel("Submit");
                });

        Form<FormType, FormType.FormOptions, Object> form = builder.getForm();

        HashMap<String, Object> in = new HashMap<>();
        in.put("review", FormUtils.normalize(form.getChild("review"), review));

        form.setData(in);
        form.propagateChildData();

        form.handleRequest();

        if (form.isSubmitted() && form.isValid()) {
            Map<String, Object> data = (Map<String, Object>) form.getChild("review").resolveData();

            String[] allowedKeys = new String[]{};

            switch (currentRole) {
                case EMPLOYEE:
                    allowedKeys = new String[]{"employeeComments"};
                    break;
                case REVIEWER:
                    allowedKeys = new String[]{
                            "futureObjectivePlans",
                            "trainingMentoringReview",
                            "reviewerSummary",
                            "trainingMentoringDevelopment"
                    };
                    break;
            }

            FormUtils.sanitize(data, allowedKeys);

            review.setUser(user);
            review.setReviewer1(user); // TODO: unfake

            review.apply(data);

            Session session = hibernate.getCurrentSession();

            Transaction transaction = session.beginTransaction();
            review = (AnnualReviewRecord) session.merge(review);

            session.flush();
            transaction.commit();

            flashManager.addFlash(new FlashMessage("Annual review amended", "success", "check"));

            return this.redirect(runtimeBag.getRequest().getRequestURI());
        }

        return new JTwigResponse("views/record/form.twig", MapUtils.createHashMap(
                entry("form", form.createView())
        ));
    }
}
