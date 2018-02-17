package Yuconz.Controller;

import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.Exception.FrameworkException;
import com.sallyf.sallyf.Router.Response;
import com.sallyf.sallyf.Router.RouteParameters;
import com.sallyf.sallyf.Server.Status;
import org.parboiled.common.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        InputStream stream = classLoader.getResourceAsStream(path);

        String content = getContent(stream);

        String mime;
        try {
            mime = Files.probeContentType(Paths.get(path));
        } catch (IOException e) {
            mime = "text/plain";
        }

        return new Response(content, Status.OK, mime);
    }

    private String getContent(InputStream stream)
    {
        StringBuilder result = new StringBuilder("");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            throw new FrameworkException(e);
        }

        return result.toString();

    }
}
