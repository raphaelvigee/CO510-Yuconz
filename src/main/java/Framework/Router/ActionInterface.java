package Framework.Router;

@FunctionalInterface
public interface ActionInterface
{
    Response apply(Object... args);
}
