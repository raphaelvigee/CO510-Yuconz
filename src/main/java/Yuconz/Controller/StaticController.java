package Yuconz.Controller;

import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.Router.Response;
import com.sallyf.sallyf.Router.RouteParameters;
import com.sallyf.sallyf.Server.Status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

@Route(path = "/assets")
public class StaticController extends BaseController
{
    @Route(path = "/{folder}/{name}")
    public Response assets(RouteParameters routeParameters)
    {
        String folder = (String) routeParameters.get("folder");
        String name = (String) routeParameters.get("name");
        String path = "assets/" + folder + "/" + name;

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());

        String content = getFile(file);

        String mime;
        try {
            mime = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            mime = "text/plain";
        }

        return new Response(content, Status.OK, mime);
    }

    private String getFile(File file)
    {
        StringBuilder result = new StringBuilder("");

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
