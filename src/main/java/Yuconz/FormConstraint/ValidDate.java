package Yuconz.FormConstraint;

import com.sallyf.sallyf.Form.ConstraintInterface;
import com.sallyf.sallyf.Form.ErrorsBag;
import com.sallyf.sallyf.Form.Form;
import com.sallyf.sallyf.Form.ValidationError;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;

public class ValidDate implements ConstraintInterface
{
    @Override
    public void validate(Form<?, ?, ?> form, ErrorsBag errorsBag)
    {
        Integer day = (Integer) form.getChildren("day").resolveData();
        Month month = (Month) form.getChildren("month").resolveData();
        Integer year = (Integer) form.getChildren("year").resolveData();

        try {
            LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            errorsBag.addError(new ValidationError("This date is invalid"));
        }
    }
}
