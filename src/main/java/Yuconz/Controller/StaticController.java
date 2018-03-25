package Yuconz.Controller;

import com.sallyf.sallyf.Annotation.Requirement;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.Exception.FrameworkException;
import com.sallyf.sallyf.Router.Response;
import com.sallyf.sallyf.Router.RouteParameters;
import com.sallyf.sallyf.Server.Status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Controller for static content types (css, js, etc).
 */
@Route(path = "/assets")
public class StaticController extends BaseController
{
    /**
     * Route for static asset types.
     *
     * @param routeParameters the route's parameters
     * @return response
     */
    @Route(path = "/{path}", requirements = {
            @Requirement(name = "path", requirement = "(.*)")
    })
    public Response assets(RouteParameters routeParameters)
    {
        String path = (String) routeParameters.get("path");
        String fullpath = "assets/" + path;

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream stream = classLoader.getResourceAsStream(fullpath);

        String content = getContent(stream);

        String mime;
        try {
            mime = Files.probeContentType(Paths.get(fullpath));
        } catch (IOException e) {
            mime = "text/plain";
        }

        return new Response(content, Status.OK, mime);
    }

    /**
     * Get content string for a file's InputStream.
     *
     * @param stream the input stream
     * @return String
     */
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
