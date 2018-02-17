package Yuconz.Controller;

import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.Router.Response;
import com.sallyf.sallyf.Router.RouteParameters;
import com.sallyf.sallyf.Server.Status;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Scanner;

@Route(path = "/assets")
public class StaticController extends BaseController
{
    @Route(path = "/{folder}/{name}")
    public Response assets(RouteParameters routeParameters)
    {
        String folder = (String) routeParameters.get("folder");
        String name = (String) routeParameters.get("name");

        String file = getFile("assets/" + folder + "/" + name);

        String mime = URLConnection.guessContentTypeFromName(name);

        return new Response(file, Status.OK, mime);
    }

    private String getFile(String fileName)
    {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();

    }
}
