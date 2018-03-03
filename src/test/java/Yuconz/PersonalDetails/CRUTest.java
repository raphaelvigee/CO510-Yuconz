package Yuconz.PersonalDetails;

import Yuconz.AbstractTest;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CRUTest extends AbstractTest
{
    @Test
    public void createUpdateTest() throws Exception
    {
        Faker faker = new Faker();

        Supplier<Map<String, String>> dataSupplier = () -> {
            Map<String, String> data = new LinkedHashMap<>();
            data.put("user[firstname]", faker.name().firstName());
            data.put("user[lastname]", faker.name().lastName());
            data.put("user[address][line1]", faker.address().fullAddress());
            data.put("user[address][line2]", faker.lorem().sentence(3));
            data.put("user[address][city]", faker.address().city());
            data.put("user[address][county]", faker.address().state());
            data.put("user[address][postcode]", faker.address().zipCode());
            data.put("user[address][country]", faker.address().country());
            data.put("user[phone_number]", faker.phoneNumber().phoneNumber());
            data.put("user[mobile_number]", faker.phoneNumber().phoneNumber());
            data.put("user[emergency_contact]", faker.name().fullName());
            data.put("user[emergency_contact_number]", faker.phoneNumber().phoneNumber());

            return data;
        };

        BiConsumer<HtmlForm, Map<String, String>> formSetter = (form, data) -> {
            data.forEach((name, value) -> {
                HtmlTextInput field = form.getInputByName(name);
                field.setValueAttribute(value);
            });
        };

        BiConsumer<HtmlForm, Map<String, String>> formChecker = (form, data) -> {
            data.forEach((name, value) -> {
                HtmlTextInput field = form.getInputByName(name);
                Assert.assertEquals(value, field.getValueAttribute());
            });
        };

        WebClient webClient = login(UserDefinition.HR_EMPLOYEE, "HR_EMPLOYEE").getWebClient();

        HtmlPage page1 = webClient.getPage(getUrl("/details/create"));

        HtmlForm form1 = page1.getForms().get(0);

        Map<String, String> data = dataSupplier.get();

        formSetter.accept(form1, data);

        HtmlSubmitInput button1 = form1.getInputByValue("Create");

        HtmlPage page2 = button1.click();

        Pattern pattern = Pattern.compile("^/details/([a-z]{3}[0-9]{3})$");
        Matcher matcher = pattern.matcher(page2.getUrl().getPath());
        Assert.assertTrue(matcher.find());

        DomElement userIdH2 = page2.getElementById("user-id");

        Assert.assertEquals("ID: " + matcher.group(1), userIdH2.getTextContent());

        HtmlForm form2 = page2.getForms().get(0);

        formChecker.accept(form2, data);

        Map<String, String> newData = dataSupplier.get();

        formSetter.accept(form2, newData);

        HtmlSubmitInput button2 = form2.getInputByValue("Update");

        HtmlPage page3 = button2.click();

        HtmlForm form3 = page3.getForms().get(0);

        formChecker.accept(form3, newData);
    }
}