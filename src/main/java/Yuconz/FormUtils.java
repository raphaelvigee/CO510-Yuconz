package Yuconz;

import com.sallyf.sallyf.Form.Form;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class FormUtils
{
    public static Object normalize(Form<?, ?, ?> form, Object object)
    {
        Set<Form> children = form.getChildren()
                .stream()
                .filter(child -> !Objects.equals(child.getOptions().get("mapped"), false))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        if (form.getOptions().containsKey("normalizer")) {
            BiFunction<Form<?, ?, ?>, Object, Object> normalizer = (BiFunction<Form<?, ?, ?>, Object, Object>) form.getOptions().get("normalizer");
            return normalizer.apply(form, object);
        }

        if (children.isEmpty()) {
            return object;
        } else {
            return children.stream()
                    .collect(Collectors.toMap(
                            Form::getName,
                            child -> normalize(child, PropertyAccessor.get(object, child.getName()))
                    ));
        }
    }

    public static void apply(Form<?, ?, ?> form, Object object)
    {
        Object data = form.resolveData();

        if (data instanceof Map) {
            ((Map<String, ?>) data).forEach((name, value) -> {
                apply(form.getChild(name), PropertyAccessor.get(object, name));
            });
        } else {
            PropertyAccessor.set(object, form.getName(), data);
        }
    }
}
