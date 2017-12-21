package Framework.EventDispatcher;

@FunctionalInterface
public interface EventHandlerInterface<E extends EventInterface>
{
    void dispatch(EventType<E> eventType, E e);
}
