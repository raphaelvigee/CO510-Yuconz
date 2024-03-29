package Yuconz.PersonalDetails;

import Yuconz.AbstractTest;
import Yuconz.Entity.Section;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
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

        Supplier<Map<String, String>> dataTextSupplier = () -> {
            Map<String, String> data = new LinkedHashMap<>();
            data.put("user[firstName]", faker.name().firstName());
            data.put("user[lastName]", faker.name().lastName());
            data.put("user[email]", faker.internet().emailAddress());
            data.put("user[address][line1]", faker.address().fullAddress());
            data.put("user[address][line2]", faker.lorem().sentence(3));
            data.put("user[address][city]", faker.address().city());
            data.put("user[address][county]", faker.address().state());
            data.put("user[address][postcode]", faker.address().zipCode());
            data.put("user[address][country]", faker.address().country());
            data.put("user[phoneNumber]", faker.phoneNumber().phoneNumber());
            data.put("user[mobileNumber]", faker.phoneNumber().phoneNumber());
            data.put("user[emergencyContact]", faker.name().fullName());
            data.put("user[emergencyContactNumber]", faker.phoneNumber().phoneNumber());

            return data;
        };

        Supplier<Map<String, String>> dataSelectSupplier = () -> {
            Map<String, String> data = new LinkedHashMap<>();
            LocalDate birthday = faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            data.put("user[birthdate][day]", String.valueOf(birthday.getDayOfMonth()));
            data.put("user[birthdate][month]", String.valueOf(birthday.getMonthValue()));
            data.put("user[birthdate][year]", String.valueOf(birthday.getYear()));
            data.put("user[section]", String.valueOf(Section.random()));

            return data;
        };

        BiConsumer<HtmlForm, Map<String, String>> formTextSetter = (form, data) -> {
            data.forEach((name, value) -> {
                HtmlInput field = form.getInputByName(name);
                field.setValueAttribute(value);
            });
        };

        BiConsumer<HtmlForm, Map<String, String>> formTextChecker = (form, data) -> {
            data.forEach((name, value) -> {
                HtmlInput field = form.getInputByName(name);
                Assert.assertEquals(value, field.getValueAttribute());
            });
        };

        BiConsumer<HtmlForm, Map<String, String>> formSelectSetter = (form, data) -> {
            data.forEach((name, value) -> {
                HtmlSelect field = form.getSelectByName(name);
                field.setSelectedAttribute(value, true);
            });
        };

        BiConsumer<HtmlForm, Map<String, String>> formSelectChecker = (form, data) -> {
            data.forEach((name, value) -> {
                HtmlSelect field = form.getSelectByName(name);
                Assert.assertEquals(value, field.getSelectedOptions().get(0).getValueAttribute());
            });
        };

        WebClient webClient = login(UserDefinition.HR_EMPLOYEE, "HR_EMPLOYEE").getWebClient();

        HtmlPage page1 = webClient.getPage(getUrl("/employee/create"));

        HtmlForm form1 = page1.getForms().get(0);

        Map<String, String> textData = dataTextSupplier.get();
        Map<String, String> selectData = dataSelectSupplier.get();

        formTextSetter.accept(form1, textData);
        formSelectSetter.accept(form1, selectData);

        HtmlInput field = form1.getInputByName("user[password]");
        field.setValueAttribute("123");

        HtmlSubmitInput button1 = form1.getInputByValue("Create");

        HtmlPage page2 = button1.click();

        String url = page2.getUrl().getPath();

        Pattern pattern = Pattern.compile("^/employee/([a-z]{3}[0-9]{3})/edit$");
        Matcher matcher = pattern.matcher(url);
        Assert.assertTrue(matcher.find());

        DomElement userIdH2 = page2.getElementById("user-id");

        Assert.assertEquals("ID: " + matcher.group(1), userIdH2.getTextContent());

        HtmlForm form2 = page2.getForms().get(0);

        formTextChecker.accept(form2, textData);
        formSelectChecker.accept(form2, selectData);

        Map<String, String> newTextData = dataTextSupplier.get();
        Map<String, String> newSelectData = dataSelectSupplier.get();

        formTextSetter.accept(form2, newTextData);
        formSelectSetter.accept(form2, newSelectData);

        HtmlSubmitInput button2 = form2.getInputByValue("Update");

        HtmlPage page3 = button2.click();

        HtmlForm form3 = page3.getForms().get(0);

        formTextChecker.accept(form3, newTextData);
        formSelectChecker.accept(form3, newSelectData);
    }
}
