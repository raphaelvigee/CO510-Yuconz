package Yuconz.Authentication;

import Yuconz.AbstractTest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class LoginTest extends AbstractTest
{
    @Test
    public void testLoginEmployee() throws Exception
    {
        testLogin(UserDefinition.EMPLOYEE, "employee");
    }

    @Test
    public void testLoginHREmployee() throws Exception
    {
        testLogin(UserDefinition.HR_EMPLOYEE, "hr_employee");
    }

    @Test
    public void testLoginManager() throws Exception
    {
        testLogin(UserDefinition.MANAGER, "manager");
    }

    @Test
    public void testLoginDirector() throws Exception
    {
        testLogin(UserDefinition.DIRECTOR, "director");
    }

    @Test
    public void testLoginEmployeeAsManagerFailure() throws Exception
    {
        HtmlPage page = login(UserDefinition.EMPLOYEE, "manager");

        String path = page.getUrl().getPath();

        Assert.assertEquals("/auth/login", path);
    }

    @Test
    public void testLoginEmployeeFailure() throws Exception
    {
        HtmlPage page = login("non@existent.user", "invalidPassword", "employee");

        String path = page.getUrl().getPath();

        Assert.assertEquals("/auth/login", path);
    }

    private void testLogin(AbstractTest.UserDefinition userDefinition, String role) throws IOException
    {
        HtmlPage page = login(userDefinition, role);

        String path = page.getUrl().getPath();

        Assert.assertEquals("/dashboard", path);
    }
}
