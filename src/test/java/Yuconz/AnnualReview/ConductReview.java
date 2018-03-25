package Yuconz.AnnualReview;

import Yuconz.AbstractTest;
import Yuconz.Entity.AnnualReviewRecord;
import Yuconz.Service.Hibernate;
import com.gargoylesoftware.htmlunit.html.*;
import org.hamcrest.core.StringContains;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class ConductReview extends AbstractTest
{
    @Test
    public void conductReview() throws Exception
    {
        Hibernate hibernate = app.getContainer().get(Hibernate.class);
        Session session = hibernate.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        HtmlPage dashboard1 = login(UserDefinition.EMPLOYEE, "EMPLOYEE");

        HtmlAnchor createAnchor = dashboard1.getAnchorByText("Create");

        HtmlPage createPage = createAnchor.click();

        HtmlForm createForm = createPage.getForms().get(0);

        HtmlTextArea employeeCommentsField = createForm.getTextAreaByName("review[employeeComments]");
        employeeCommentsField.setText("employee comments");

        HtmlSubmitInput submit = createForm.getInputByValue("Submit");

        HtmlPage dashboard2 = submit.click();

        assertEquals(200, dashboard2.getWebResponse().getStatusCode());
        assertThat(dashboard2.getWebResponse().getContentAsString(), StringContains.containsString("New Annual review created"));

        HtmlAnchor completeAnchor1 = dashboard2.getAnchorByText("Complete");

        HtmlPage editPage = completeAnchor1.click();

        Pattern pattern = Pattern.compile("/annual-review/([a-zA-Z0-9-]+)/edit");
        Matcher matcher = pattern.matcher(editPage.getUrl().getPath());

        assertTrue(matcher.find());

        final String annualReviewId = matcher.group(1);

        AnnualReviewRecord review = session.get(AnnualReviewRecord.class, annualReviewId);

        HtmlPage dashboard3 = login(UserDefinition.HR_EMPLOYEE, "HR_EMPLOYEE");
        HtmlAnchor completeAnchor3 = dashboard3.getAnchorByText("Complete");

        HtmlPage editPage3 = completeAnchor3.click();
        HtmlForm editForm3 = editPage3.getForms().get(0);

        HtmlSelect reviewer2Select = editForm3.getSelectByName("review[reviewer2]");
        reviewer2Select.setSelectedIndex(1);

        editForm3.getInputByValue("Submit").click();

        session.refresh(review);

        HtmlPage dashboard4 = login(review.getReviewee().getEmail(), "123", "EMPLOYEE");
        sign(dashboard4);

        HtmlPage dashboard5 = login(review.getReviewer1().getEmail(), "123", "REVIEWER");
        sign(dashboard5);

        HtmlPage dashboard6 = login(review.getReviewer2().getEmail(), "123", "REVIEWER");
        sign(dashboard6);

        HtmlPage dashboard7 = login(UserDefinition.HR_EMPLOYEE, "HR_EMPLOYEE");
        HtmlAnchor completeAnchor7 = dashboard7.getAnchorByText("Complete");

        HtmlPage editPage7 = completeAnchor7.click();
        HtmlForm editForm7 = editPage7.getForms().get(0);

        HtmlCheckBoxInput acceptedCheckbox = editForm7.getInputByName("review[accepted]");
        assertFalse(acceptedCheckbox.isDisabled());
        acceptedCheckbox.setChecked(true);

        editForm7.getInputByValue("Submit").click();

        transaction.commit();
    }

    private HtmlPage sign(HtmlPage dashboard)
    {
        try {
            HtmlAnchor completeAnchor = dashboard.getAnchorByText("Complete");

            HtmlPage completeReviewer1 = completeAnchor.click();

            HtmlAnchor signAnchor = completeReviewer1.getAnchorByText("Sign");

            HtmlPage signPage = signAnchor.click();

            HtmlForm htmlForm = signPage.getForms().get(0);

            HtmlInput signButton = htmlForm.getInputByValue("Sign");

            return signButton.click();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
