package Yuconz;

import Framework.Container;
import Framework.Kernel;
import Framework.Routing;
import Framework.YServer;
import Yuconz.Controller.AppController;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Kernel app = Kernel.newInstance();

        Container c = app.getContainer();

        c.add(YServer.class);
        c.add(Routing.class);
        c.add(AppController.class);

        c.get(Routing.class).get("/hello/{name}/{job}", c.get(AppController.class).helloWorldAction);
    }
}
