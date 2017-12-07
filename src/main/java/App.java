import Controller.AppController;
import Framework.Container;
import Framework.Routing;
import Framework.YServer;

public class App
{
    private Container container;

    public App(Container container)
    {
        this.container = container;
    }

    public static App newInstance()
    {
        Container container = new Container();

        container.add(YServer.class);
        container.add(Routing.class);
        container.add(AppController.class);

        container.get(Routing.class).get("/hello/{name}/{job}", container.get(AppController.class).helloWorldAction);

        return new App(container);
    }
}
