package Yuconz;

import com.sallyf.sallyf.Exception.FrameworkException;

import java.beans.Statement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropertyAccessor
{
    private static String capitalize(final String line)
    {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    public static void set(Object instance, String name, Object value)
    {
        Statement stmt = new Statement(instance, "set" + capitalize(name), new Object[]{value});

        try {
            stmt.execute();
        } catch (Exception e) {
            throw new FrameworkException(e);
        }
    }

    public static Object get(Object instance, String name)
    {
        name = capitalize(name);

        try {
            Method isMethod = instance.getClass().getDeclaredMethod("is" + name);
            return isMethod.invoke(instance);
        } catch (NoSuchMethodException e) {
            try {
                Method getMethod = instance.getClass().getDeclaredMethod("get" + name);
                return getMethod.invoke(instance);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e1) {
                throw new FrameworkException(e1);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new FrameworkException(e);
        }
    }
}
