package Framework.Router;

@FunctionalInterface
public interface ActionInvokerInterface
{
    Response invoke(Object[] parameters);
}
