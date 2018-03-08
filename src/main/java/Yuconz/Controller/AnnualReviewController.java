package Yuconz.Controller;

import Yuconz.Entity.AnnualReviewRecord;
import Yuconz.Entity.User;
import Yuconz.Form.AnnualReviewEmployeeType;
import Yuconz.Model.FlashMessage;
import Yuconz.SecurityHandler.LoginRedirectHandler;
import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Authentication.Annotation.Security;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.FlashManager.FlashManager;
import com.sallyf.sallyf.Form.Form;
import com.sallyf.sallyf.Form.Type.FormType;
import com.sallyf.sallyf.Form.Type.SubmitType;
import com.sallyf.sallyf.JTwig.JTwigResponse;
import com.sallyf.sallyf.Server.Method;
import com.sallyf.sallyf.Utils.MapUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Map;

import static com.sallyf.sallyf.Utils.MapUtils.entry;

@Security(value = "is_granted('authenticated')", handler = LoginRedirectHandler.class)
@Route(path = "/annual-review")
public class AnnualReviewController extends BaseController
{
    @Route(path = "/create", methods = {Method.GET, Method.POST})
    @Security("is_granted('create_annual_review')")
    public Object create(User user, Hibernate hibernate, FlashManager flashManager)
    {
        Form<FormType, FormType.FormOptions, Object> form = createFormBuilder()
                .add("review", AnnualReviewEmployeeType.class)
                .add("submit", SubmitType.class, options -> {
                    options.setLabel("Submit");
                })
                .getForm();

        form.handleRequest();

        if (form.isSubmitted() && form.isValid()) {
            Map<String, Object> data = (Map<String, Object>) form.getChild("review").resolveData();

            AnnualReviewRecord review = new AnnualReviewRecord();
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
                entry("form", form.createView())
        ));
    }
}
