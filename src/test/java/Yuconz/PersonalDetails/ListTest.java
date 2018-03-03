package Yuconz.PersonalDetails;

import Yuconz.AbstractTest;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.Test;

public class ListTest extends AbstractTest
{
    @Test(expected = ElementNotFoundException.class)
    public void failureTest() throws Exception
    {
        HtmlPage loginPage = login(UserDefinition.HR_EMPLOYEE, "EMPLOYEE");

        loginPage.getAnchorByText("Employee Search");
    }

    @Test
    public void listTest() throws Exception
    {
        HtmlPage loginPage = login(UserDefinition.HR_EMPLOYEE, "HR_EMPLOYEE");

        HtmlAnchor listAnchor = loginPage.getAnchorByText("Employee Search");

        Page page = anchorClick(listAnchor);

        Assert.assertEquals(200, page.getWebResponse().getStatusCode());
        Assert.assertEquals("/details", page.getUrl().getPath());
    }
}
