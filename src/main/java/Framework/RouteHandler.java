package Framework;

public interface RouteHandler<I, O>
{
    public O call(I input);
}