package Yuconz;

import com.sallyf.sallyf.Form.Form;

import java.util.*;
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

            if (object == null) {
                return null;
            }

            return normalizer.apply(form, object);
        }

        if (children.isEmpty()) {
            return object;
        } else {
            Map<String, Object> out = new LinkedHashMap<>();
            children.forEach(child -> {
                out.put(child.getName(), normalize(child, PropertyAccessor.get(object, child.getName())));
            });

            return out;
        }
    }

    /**
     * Applying form handling data to object
     *
     * @param form
     * @param object
     */
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

    /**
     * Prevents form hijacking
     *
     * @param map
     * @param allowedKeys
     */
    public static void sanitize(Map<String, Object> map, String[] allowedKeys)
    {
        List<String> allowedKeysList = Arrays.asList(allowedKeys);

        map.entrySet().removeIf(e -> !allowedKeysList.contains(e.getKey()));
    }
}
