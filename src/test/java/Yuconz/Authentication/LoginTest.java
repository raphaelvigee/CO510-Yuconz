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
        testLogin(UserDefinition.EMPLOYEE, "EMPLOYEE");
    }

    @Test
    public void testLoginHREmployee() throws Exception
    {
        testLogin(UserDefinition.HR_EMPLOYEE, "HR_EMPLOYEE");
    }

    @Test
    public void testLoginManager() throws Exception
    {
        testLogin(UserDefinition.MANAGER, "MANAGER");
    }

    @Test
    public void testLoginDirector() throws Exception
    {
        testLogin(UserDefinition.DIRECTOR, "DIRECTOR");
    }

    @Test
    public void testLoginEmployeeAsManagerFailure() throws Exception
    {
        HtmlPage page = login(UserDefinition.EMPLOYEE, "MANAGER");

        String path = page.getUrl().getPath();

        Assert.assertEquals("/auth/login", path);
    }

    @Test
    public void testLoginEmployeeFailure() throws Exception
    {
        HtmlPage page = login("non@existent.user", "invalidPassword", "EMPLOYEE");

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
