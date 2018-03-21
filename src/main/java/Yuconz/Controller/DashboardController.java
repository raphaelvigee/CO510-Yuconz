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
import java.util.function.BiFunction;

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

        BiFunction<AnnualReviewRecord, String, String> annualReviewLinkSupplier = (review, action) -> {
            return urlGenerator.path("AnnualReviewController." + action, new HashMap<String, String>()
            {{
                put("record", review.getId());
            }});
        };

        Session session = hibernate.getCurrentSession();

        Transaction transaction = session.beginTransaction();
        Query currentAnnualReviewQuery = session.createQuery("from AnnualReviewRecord where (reviewer1 = :user and reviewer1Signature is null) or (reviewer2 = :user and reviewer2Signature is null) or (user = :user and revieweeSignature is null)")
                .setParameter("user", user);

        List<AnnualReviewRecord> currentAnnualReviews = currentAnnualReviewQuery.getResultList();

        Query underReviewAnnualReviewQuery = session.createQuery("from AnnualReviewRecord where (reviewer1 = :user or reviewer2 = :user or user = :user) and accepted != true")
                .setParameter("user", user);
        List<AnnualReviewRecord> underReviewAnnualReviews = underReviewAnnualReviewQuery.getResultList();

        if (currentRole.equals(LoginRole.HR_EMPLOYEE)) {
            Query hrQuery = session.createQuery("from AnnualReviewRecord where reviewer1 is null or reviewer2 is null or user is null or accepted != true");

            List<AnnualReviewRecord> hrAnnualReviews = hrQuery.getResultList();

            for (AnnualReviewRecord review : hrAnnualReviews) {
                String reviewLink = annualReviewLinkSupplier.apply(review, "edit");

                String body = "You have an annual review that requires attention <a class=\"btn\" href=\"" + reviewLink + "\">Complete</a>";
                flashManager.getCurrentFlashes().add(new FlashMessage(body, "warning", "exclamation-triangle"));
            }
        }

        transaction.commit();

        for (AnnualReviewRecord review : currentAnnualReviews) {
            String reviewLink = annualReviewLinkSupplier.apply(review, "edit");

            switch (currentRole) {
                case REVIEWER:
                    String body = "You have an incomplete annual review <a class=\"btn\" href=\"" + reviewLink + "\">Complete</a>";
                    flashManager.getCurrentFlashes().add(new FlashMessage(body, "warning", "exclamation-triangle"));
                    break;
                default:
                    String link = urlGenerator.path("AuthenticationController.login") + "?next=" + URLEncoder.encode(reviewLink, "UTF-8") + "&role=REVIEWER";
                    String messageBody = "You have an incomplete annual review, log in as a <a class=\"btn\" href=\"" + link + "\">Reviewer</a>";
                    flashManager.getCurrentFlashes().add(new FlashMessage(messageBody, "warning", "exclamation-triangle"));
                    break;
            }
        }

        for (AnnualReviewRecord review : underReviewAnnualReviews) {
            String reviewLink = annualReviewLinkSupplier.apply(review, "view");

            String body = "You have an Annual Review under review, <a class=\"btn\" href=\"" + reviewLink + "\">View</a>";
            flashManager.getCurrentFlashes().add(new FlashMessage(body, "info", "check"));
        }

        return new JTwigResponse("views/dashboard/base.twig");
    }
}
