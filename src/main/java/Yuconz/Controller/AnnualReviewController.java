package Yuconz.Controller;

import Yuconz.App;
import Yuconz.Entity.AnnualReviewRecord;
import Yuconz.Entity.InitialEmploymentDetailsRecord;
import Yuconz.Entity.Signature;
import Yuconz.Entity.User;
import Yuconz.Form.AnnualReviewType;
import Yuconz.FormUtils;
import Yuconz.Manager.AnnualReviewManager;
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
import org.eclipse.jetty.server.Request;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.sallyf.sallyf.Utils.MapUtils.entry;

@Security(value = "is_granted('authenticated')", handler = LoginRedirectHandler.class)
@Route(path = "/annual-review")
public class AnnualReviewController extends BaseController
{
    @Route(path = "/create", methods = {Method.GET, Method.POST})
    @Security("is_granted('create_annual_review')")
    public Object create(User user, Hibernate hibernate, FlashManager flashManager, YuconzAuthenticationManager authenticationManager, RecordManager recordManager, AnnualReviewManager annualReviewManager)
    {
        AnnualReviewRecord lastAnnualReview = recordManager.getLastRecord(user, AnnualReviewRecord.class);

        AnnualReviewRecord review = new AnnualReviewRecord();
        review.setReviewee(user);

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

        AnnualReviewRecord finalReview = review;
        Form<FormType, FormType.FormOptions, Object> form = createFormBuilder()
                .add("review", AnnualReviewType.class, options -> {
                    options.setCurrentRole(authenticationManager.getCurrentRole());
                    options.setAnnualReview(finalReview);
                })
                .add("submit", SubmitType.class, options -> {
                    options.setLabel("Submit");
                })
                .getForm();

        form.handleRequest();

        if (form.isSubmitted() && form.isValid()) {
            Map<String, Object> data = (Map<String, Object>) form.getChild("review").resolveData();
            FormUtils.sanitize(data, new String[]{"employeeComments"});

            List<User> candidateReviewers = annualReviewManager.getCandidateReviewer1(user);
            Random rand = new Random();
            User reviewer1 = candidateReviewers.get(rand.nextInt(candidateReviewers.size()));
            review.setReviewer1(reviewer1);

            review.apply(data);

            Session session = hibernate.getCurrentSession();

            Transaction transaction = session.beginTransaction();
            review = (AnnualReviewRecord) session.merge(review);

            session.flush();
            transaction.commit();

            flashManager.addFlash(new FlashMessage("New Annual review created", "success", "check"));

            return this.redirectToRoute("AppController.index");
        }

        return new JTwigResponse("views/annual-review/form.twig", MapUtils.createHashMap(
                entry("review", review),
                entry("form", form.createView())
        ));
    }

    @Route(path = "/{record}/edit", methods = {Method.GET, Method.POST}, requirements = {
            @Requirement(name = "record", requirement = App.RECORD_REGEX)
    })
    @ParameterResolver(name = "record", type = RecordResolver.class)
    @Security("is_granted('edit_annual_review', record)")
    public Object edit(RuntimeBag runtimeBag, RouteParameters routeParameters, User user, Hibernate hibernate, FlashManager flashManager, YuconzAuthenticationManager authenticationManager)
    {
        AnnualReviewRecord review = (AnnualReviewRecord) routeParameters.get("record");

        LoginRole currentRole = authenticationManager.getCurrentRole();

        AnnualReviewRecord finalReview = review;
        FormBuilder<FormType, FormType.FormOptions, Object> builder = createFormBuilder()
                .add("review", AnnualReviewType.class, options -> {
                    options.setCurrentRole(currentRole);
                    options.setAnnualReview(finalReview);
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
                case HR_EMPLOYEE:
                    allowedKeys = new String[]{
                            "reviewer1",
                            "reviewer2",
                            "accepted",
                    };
                    break;
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

            review.apply(data);

            if (!currentRole.equals(LoginRole.HR_EMPLOYEE)) {
                review.setReviewer1Signature(null);
                review.setReviewer2Signature(null);
                review.setRevieweeSignature(null);
            }

            Session session = hibernate.getCurrentSession();

            Transaction transaction = session.beginTransaction();
            review = (AnnualReviewRecord) session.merge(review);

            session.flush();
            transaction.commit();

            flashManager.addFlash(new FlashMessage("Annual review amended", "success", "check"));

            return this.redirect(runtimeBag.getRequest().getRequestURI());
        }

        return new JTwigResponse("views/annual-review/form.twig", MapUtils.createHashMap(
                entry("review", review),
                entry("form", form.createView())
        ));
    }

    @Route(path = "/{record}/sign", methods = {Method.GET, Method.POST}, requirements = {
            @Requirement(name = "record", requirement = App.RECORD_REGEX)
    })
    @ParameterResolver(name = "record", type = RecordResolver.class)
    @Security("is_granted('sign_annual_review', record)")
    public Object sign(Request request, RouteParameters routeParameters, YuconzAuthenticationManager authenticationManager, User currentUser, FlashManager flashManager, Hibernate hibernate)
    {
        AnnualReviewRecord review = (AnnualReviewRecord) routeParameters.get("record");

        Form<FormType, FormType.FormOptions, Object> form = this.createFormBuilder()
                .add("submit", SubmitType.class, options -> {
                    options.setLabel("Sign");
                })
                .getForm();

        form.handleRequest();

        if (form.isSubmitted() && form.isValid()) {
            LoginRole currentRole = authenticationManager.getCurrentRole();

            Supplier<Boolean> isReviewer = () -> currentRole.equals(LoginRole.REVIEWER);
            Supplier<Boolean> isEmployee = () -> currentRole.equals(LoginRole.EMPLOYEE);

            Function<AnnualReviewRecord, Signature> signer = (r) -> {
                Signature s = new Signature(currentUser);

                if (isReviewer.get()) {
                    if (currentUser.equals(r.getReviewer1())) {
                        r.setReviewer1Signature(s);
                        return s;
                    }

                    if (currentUser.equals(r.getReviewer2())) {
                        r.setReviewer2Signature(s);
                        return s;
                    }
                }

                if (isEmployee.get() && currentUser.equals(r.getReviewee())) {
                    r.setRevieweeSignature(s);
                    return s;
                }

                return null;
            };

            Signature signature = signer.apply(review);

            if (signature == null) {
                flashManager.addFlash(new FlashMessage("Unable to sign", "error", "times"));

                return this.redirect(request.getRequestURI());
            }

            Session session = hibernate.getCurrentSession();

            Transaction transaction = session.beginTransaction();
            session.persist(signature);
            review = (AnnualReviewRecord) session.merge(review);
            session.flush();
            transaction.commit();

            flashManager.addFlash(new FlashMessage("Annual review signed", "success", "check"));

            return this.redirectToRoute("DashboardController.index");
        }

        return new JTwigResponse("views/annual-review/sign.twig", MapUtils.createHashMap(
                entry("review", review),
                entry("form", form.createView())
        ));
    }

    @Route(path = "/{record}", methods = {Method.GET, Method.POST}, requirements = {
            @Requirement(name = "record", requirement = App.RECORD_REGEX)
    })
    @ParameterResolver(name = "record", type = RecordResolver.class)
    @Security("is_granted('view_annual_review', record)")
    public Object view(RouteParameters routeParameters)
    {
        AnnualReviewRecord review = (AnnualReviewRecord) routeParameters.get("record");

        Form<FormType, FormType.FormOptions, Object> form = createFormBuilder()
                .add("review", AnnualReviewType.class, options -> {
                    options.setCurrentRole(null);
                    options.setAnnualReview(review);
                })
                .getForm();

        HashMap<String, Object> in = new HashMap<>();
        in.put("review", FormUtils.normalize(form.getChild("review"), review));

        form.setData(in);
        form.propagateChildData();

        return new JTwigResponse("views/annual-review/view.twig", MapUtils.createHashMap(
                entry("review", review),
                entry("form", form.createView())
        ));
    }
}
