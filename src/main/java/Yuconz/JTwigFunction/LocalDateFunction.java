package Yuconz.JTwigFunction;

import com.sallyf.sallyf.JTwig.JTwigServiceFunction;
import org.jtwig.functions.FunctionRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * JTwig function for displaying a formatted date.
 */
public class LocalDateFunction implements JTwigServiceFunction
{
    /**
     * Name of function.
     * @return name of function.
     */
    @Override
    public String name()
    {
        return "localdate";
    }

    /**
     * Execute function.
     * @param request page request
     * @return the requested date formatted
     */
    @Override
    public Object execute(FunctionRequest request)
    {
        request.minimumNumberOfArguments(2);
        request.maximumNumberOfArguments(2);

        LocalDate date = (LocalDate) request.get(0);
        String format = (String) request.get(1);

        if (date == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }
}
