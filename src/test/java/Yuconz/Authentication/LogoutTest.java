package Yuconz.Authentication;

import Yuconz.AbstractTest;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.Test;

public class LogoutTest extends AbstractTest
{
    @Test
    public void testLogout() throws Exception
    {
        HtmlPage page1 = login(UserDefinition.EMPLOYEE, "EMPLOYEE");
        WebClient webClient = page1.getEnclosingWindow().getWebClient();

        String path1 = page1.getUrl().getPath();

        Assert.assertEquals("/dashboard", path1);

        HtmlAnchor logout = page1.querySelector("#nav > div.account > div > div.dropdown > div > a");

        Page page2 = webClient.getPage(getUrl(logout.getHrefAttribute()));

        String path2 = page2.getUrl().getPath();

        Assert.assertEquals("/auth/login", path2);

        Page page3 = webClient.getPage(getUrl("/dashboard"));

        String path3 = page3.getUrl().getPath();

        Assert.assertEquals("/auth/login", path3);
    }
}
