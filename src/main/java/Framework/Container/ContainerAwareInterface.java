package Framework.Container;


public interface ContainerAwareInterface
{
    Container getContainer();

    default void initialize() {}
}
