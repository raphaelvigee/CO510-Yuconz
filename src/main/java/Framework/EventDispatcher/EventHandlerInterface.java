package Framework.EventDispatcher;

@FunctionalInterface
public interface EventHandlerInterface<E extends EventInterface>
{
    void dispatch(E e);
}
