package Yuconz.Controller;

import Yuconz.Entity.AnnualReviewRecord;
import Yuconz.Entity.User;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.Model.FlashMessage;
import Yuconz.Model.LoginRole;
import Yuconz.SecurityHandler.LoginRedirectHandler;
import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Authentication.Annotation.Security;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.FlashManager.FlashManager;
import com.sallyf.sallyf.JTwig.JTwigResponse;
import com.sallyf.sallyf.Router.URLGenerator;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * Controller for system dashboard.
 */
@Route(path = "/dashboard")
@Security(value = "is_granted('authenticated')", handler = LoginRedirectHandler.class)
public class DashboardController extends BaseController
{
    /**
     * Display main dashboard from Twig.
     *
     * @return response
     */
    @Route(path = "")
    public JTwigResponse index(YuconzAuthenticationManager authenticationManager, Hibernate hibernate, FlashManager flashManager, URLGenerator urlGenerator) throws Exception
    {
        User user = (User) authenticationManager.getUser();
        LoginRole currentRole = authenticationManager.getCurrentRole();

        Session session = hibernate.getCurrentSession();

        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from AnnualReviewRecord where (reviewer1 = :user or reviewer2 = :user) and accepted = false")
                .setParameter("user", user);

        List<AnnualReviewRecord> reviews = query.getResultList();

        transaction.commit();

        Function<AnnualReviewRecord, String> annualReviewLinkSupplier = review -> {
            return urlGenerator.path("AnnualReviewController.edit", new HashMap<String, String>()
            {{
                put("record", review.getId());
            }});
        };

        switch (currentRole) {
            case REVIEWER:
                for (AnnualReviewRecord review : reviews) {
                    String link = annualReviewLinkSupplier.apply(review);

                    String body = "You have an incompleted annual review <a class=\"btn\" href=\"" + link + "\">Complete</a>";
                    flashManager.getCurrentFlashes().add(new FlashMessage(body, "info", "exclamation-triangle"));
                }
                break;
            default:
                for (AnnualReviewRecord review : reviews) {
                    String reviewLink = annualReviewLinkSupplier.apply(review);
                    String link = urlGenerator.path("AuthenticationController.login") + "?next=" + URLEncoder.encode(reviewLink, "UTF-8") + "&role=REVIEWER";
                    String body = "You have an incompleted annual review, log in as a <a class=\"btn\" href=\"" + link + "\">Reviewer</a>";
                    flashManager.getCurrentFlashes().add(new FlashMessage(body, "warning", "exclamation-triangle"));
                }
                break;
        }

        return new JTwigResponse("views/dashboard/base.twig");
    }
}
