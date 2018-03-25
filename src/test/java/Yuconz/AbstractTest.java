package Yuconz;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.sallyf.sallyf.Exception.FrameworkException;
import com.sallyf.sallyf.Kernel;
import com.sallyf.sallyf.Server.FrameworkServer;
import org.junit.BeforeClass;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;
import java.util.logging.Level;

public abstract class AbstractTest
{
    public enum UserDefinition
    {
        EMPLOYEE("employee@yuconz", "123"),
        HR_EMPLOYEE("hr_employee@yuconz", "123"),
        MANAGER("manager@yuconz", "123"),
        DIRECTOR("director@yuconz", "123");

        final String email;

        final String password;

        UserDefinition(String email, String password)
        {
            this.email = email;
            this.password = password;
        }
    }

    public static Kernel app;

    @BeforeClass
    public static void setUpClass()
    {
        app = App.start(randomPort());
    }

    public static int randomPort()
    {
        Random r = new Random();

        int low = 2000;
        int high = 9999;

        int i = 0;
        while (i < high - low) {
            int tmp = r.nextInt(high - low) + low;

            try {
                ServerSocket serverSocket = new ServerSocket(tmp);
                serverSocket.close();
                return tmp;
            } catch (IOException ignored) {
            }
            i++;
        }

        throw new FrameworkException("Unable to find available port");
    }

    public String getUrl(String path)
    {
        return app.getContainer().get(FrameworkServer.class).getRootURL() + path;
    }

    public HtmlPage login(UserDefinition userDefinition, String role) throws IOException
    {
        return login(userDefinition.email, userDefinition.password, role);
    }

    public HtmlPage login(String email, String password, String role) throws IOException
    {
        WebClient webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        HtmlPage page1 = webClient.getPage(getUrl("/auth/login"));

        HtmlForm form = page1.getForms().get(0);

        HtmlTextInput emailField = form.getInputByName("email");
        emailField.setValueAttribute(email);

        HtmlPasswordInput passwordField = form.getInputByName("password");
        passwordField.setValueAttribute(password);

        HtmlSelect select = page1.getElementByName("role");
        HtmlOption option = select.getOptionByValue(role);
        select.setSelectedAttribute(option, true);

        HtmlSubmitInput button = form.getInputByValue("Login");

        return button.click();
    }

    public Page anchorClick(HtmlAnchor anchor) throws IOException
    {
        return anchor.getPage().getWebClient().getPage(getUrl(anchor.getHrefAttribute()));
    }
}
