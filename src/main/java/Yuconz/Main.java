package Yuconz;

import Framework.Container;
import Framework.Kernel;
import Framework.Routing;
import Yuconz.Controller.AppController;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Kernel app = Kernel.newInstance();

        Container c = app.getContainer();
        Routing routing = c.get(Routing.class);

        routing.get("/hello/{name}/{job}", AppController::helloWorldAction);
    }
}
