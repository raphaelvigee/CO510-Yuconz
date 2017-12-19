package Framework.Container;

abstract public class ContainerAware implements ContainerAwareInterface
{
    private Container container;

    public ContainerAware(Container container)
    {
        this.container = container;
    }

    @Override
    public Container getContainer()
    {
        return container;
    }
}
