package Yuconz.Controller;

import Yuconz.Entity.AnnualReviewRecord;
import Yuconz.Entity.User;
import Yuconz.Manager.AnnualReviewManager;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.Model.FlashMessage;
import Yuconz.Model.LoginRole;
import Yuconz.SecurityHandler.LoginRedirectHandler;
import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.AccessDecisionManager.AccessDecisionManager;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Authentication.Annotation.Security;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.FlashManager.FlashManager;
import com.sallyf.sallyf.JTwig.JTwigResponse;
import com.sallyf.sallyf.Router.URLGenerator;

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
    public JTwigResponse index(YuconzAuthenticationManager authenticationManager, Hibernate hibernate, FlashManager flashManager, URLGenerator urlGenerator, AnnualReviewManager annualReviewManager, AccessDecisionManager accessDecisionManager) throws Exception
    {
        User user = (User) authenticationManager.getUser();
        LoginRole currentRole = authenticationManager.getCurrentRole();

        BiFunction<AnnualReviewRecord, String, String> annualReviewLinkSupplier = (review, action) -> {
            return urlGenerator.path("AnnualReviewController." + action, new HashMap<String, String>()
            {{
                put("record", review.getId());
            }});
        };

        List<AnnualReviewRecord> incompleteAnnualReviews = annualReviewManager.getIncomplete(user);

        List<AnnualReviewRecord> underReviewAnnualReviews = annualReviewManager.getUnderReview(user);

        if (currentRole.equals(LoginRole.HR_EMPLOYEE)) {
            List<AnnualReviewRecord> hrAnnualReviews = annualReviewManager.getRequiresAttentionAsHR();

            for (AnnualReviewRecord review : hrAnnualReviews) {
                String reviewLink = annualReviewLinkSupplier.apply(review, "edit");

                String body = "You have an annual review that requires attention <a class=\"btn\" href=\"" + reviewLink + "\">Complete</a>";
                flashManager.getCurrentFlashes().add(new FlashMessage(body, "warning", "exclamation-triangle"));
            }
        }

        for (AnnualReviewRecord review : incompleteAnnualReviews) {
            String reviewLink = annualReviewLinkSupplier.apply(review, "edit");

            if ((user.equals(review.getReviewer1()) || user.equals(review.getReviewer2())) && !currentRole.equals(LoginRole.REVIEWER)) {
                String link = urlGenerator.path("AuthenticationController.login") + "?next=" + URLEncoder.encode(reviewLink, "UTF-8") + "&role=REVIEWER";
                String messageBody = "You have an incomplete annual review, log in as a <a class=\"btn\" href=\"" + link + "\">Reviewer</a>";
                flashManager.getCurrentFlashes().add(new FlashMessage(messageBody, "warning", "exclamation-triangle"));
            } else if (user.equals(review.getReviewee()) && !currentRole.equals(LoginRole.EMPLOYEE)) {
                String link = urlGenerator.path("AuthenticationController.login") + "?next=" + URLEncoder.encode(reviewLink, "UTF-8") + "&role=EMPLOYEE";
                String messageBody = "You have an incomplete annual review, log in as a <a class=\"btn\" href=\"" + link + "\">Employee</a>";
                flashManager.getCurrentFlashes().add(new FlashMessage(messageBody, "warning", "exclamation-triangle"));
            } else {
                String body = "You have an incomplete annual review <a class=\"btn\" href=\"" + reviewLink + "\">Complete</a>";
                flashManager.getCurrentFlashes().add(new FlashMessage(body, "warning", "exclamation-triangle"));
            }
        }

        for (AnnualReviewRecord review : underReviewAnnualReviews) {

            String body = "You have an Annual Review under review";

            if(accessDecisionManager.vote("view_annual_review", review)) {
                String link = annualReviewLinkSupplier.apply(review, "view");
                body += " <a class=\"btn\" href=\"" + link + "\">View</a> ";
            }

            if(accessDecisionManager.vote("edit_annual_review", review)) {
                String link = annualReviewLinkSupplier.apply(review, "edit");
                body += " <a class=\"btn\" href=\"" + link + "\">Edit</a> ";
            }

            flashManager.getCurrentFlashes().add(new FlashMessage(body, "info", "check"));
        }

        if (annualReviewManager.needsNew(user)) {
            String reviewLink = urlGenerator.path("AnnualReviewController.create");

            switch (currentRole) {
                case EMPLOYEE:
                    String body = "You need to create a new annual review <a class=\"btn\" href=\"" + reviewLink + "\">Create</a>";
                    flashManager.getCurrentFlashes().add(new FlashMessage(body, "warning", "exclamation-triangle"));
                    break;
                default:
                    String link = urlGenerator.path("AuthenticationController.login") + "?next=" + URLEncoder.encode(reviewLink, "UTF-8") + "&role=EMPLOYEE";
                    String messageBody = "You need to create a new annual review, log in as an <a class=\"btn\" href=\"" + link + "\">Employee</a>";
                    flashManager.getCurrentFlashes().add(new FlashMessage(messageBody, "warning", "exclamation-triangle"));
                    break;
            }
        }

        return new JTwigResponse("views/dashboard/base.twig");
    }
}
