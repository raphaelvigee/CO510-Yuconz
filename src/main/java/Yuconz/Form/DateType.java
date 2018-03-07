package Yuconz.Form;

import Yuconz.FormConstraint.ValidDate;
import com.sallyf.sallyf.Form.Form;
import com.sallyf.sallyf.Form.FormTypeInterface;
import com.sallyf.sallyf.Form.Options;
import com.sallyf.sallyf.Form.Type.AbstractFormType;
import com.sallyf.sallyf.Form.Type.ChoiceType;
import org.eclipse.jetty.server.Request;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Form type for dates.
 */
public class DateType extends AbstractFormType<DateType.DateOptions, LocalDate>
{
    public class DateOptions extends Options
    {

    }

    /**
     * Creates the options container.
     * @return new DateOptions object
     */
    @Override
    public DateOptions createOptions()
    {
        return new DateOptions();
    }

    /**
     * Builds a form structure.
     * @param form form
     */
    @Override
    public void buildForm(Form<?, DateOptions, LocalDate> form)
    {
        int year = Year.now().getValue();
        LinkedHashSet<?> days = IntStream.rangeClosed(1, 31).boxed().collect(Collectors.toCollection(LinkedHashSet::new));
        LinkedHashSet<?> years = IntStream.rangeClosed(year - 100, year).boxed().sorted(Collections.reverseOrder()).collect(Collectors.toCollection(LinkedHashSet::new));

        form.getBuilder()
                .add("day", ChoiceType.class, options -> {
                    options.setExpanded(false);
                    options.setMultiple(false);

                    options.setChoices(days);
                })
                .add("month", ChoiceType.class, options -> {
                    options.setExpanded(false);
                    options.setMultiple(false);

                    options.setChoices(new LinkedHashSet<>(Arrays.asList(Month.values())));

                    options.setChoiceLabelResolver(m -> ((Month) m).getDisplayName(TextStyle.FULL, Locale.ENGLISH));
                    options.setChoiceValueResolver(m -> String.valueOf(((Month) m).getValue()));
                })
                .add("year", ChoiceType.class, options -> {
                    options.setExpanded(false);
                    options.setMultiple(false);

                    options.setChoices(years);
                });

        form.getOptions().getConstraints().add(new ValidDate());
    }

    /**
     * Transforms the data of the form, into the representation of the data.
     * @param form form
     * @param <T> generic class
     * @return representation of the data
     */
    @Override
    public <T extends FormTypeInterface<DateType.DateOptions, LocalDate>> Object resolveData(Form<T, DateOptions, LocalDate> form)
    {
        Integer day = (Integer) form.getChild("day").resolveData();
        Month month = (Month) form.getChild("month").resolveData();
        Integer year = (Integer) form.getChild("year").resolveData();

        return LocalDate.of(year, month, day);
    }
}
