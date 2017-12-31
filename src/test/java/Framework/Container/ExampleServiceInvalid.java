package Framework.Container;

public class ExampleServiceInvalid implements ContainerAwareInterface
{
    public ExampleServiceInvalid()
    {
    }

    @Override
    public Container getContainer()
    {
        return null;
    }
}
