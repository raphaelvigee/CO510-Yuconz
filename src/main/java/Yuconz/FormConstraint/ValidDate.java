package Yuconz.FormConstraint;

import com.sallyf.sallyf.Form.ConstraintInterface;
import com.sallyf.sallyf.Form.ErrorsBag;
import com.sallyf.sallyf.Form.Form;
import com.sallyf.sallyf.Form.ValidationError;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;

/**
 * Form Constraint for date validation.
 */
public class ValidDate implements ConstraintInterface
{
    /**
     * Checks if the date is valid.
     *
     * @param form      form
     * @param errorsBag errorsBag
     */
    @Override
    public void validate(Form<?, ?, ?> form, ErrorsBag errorsBag)
    {
        Integer day = (Integer) form.getChild("day").resolveData();
        Month month = (Month) form.getChild("month").resolveData();
        Integer year = (Integer) form.getChild("year").resolveData();

        try {
            LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            errorsBag.addError(new ValidationError("This date is invalid"));
        }
    }
}
