package jmp.rest;

import jmp.rest.service.MongoCLIResourceConfig;
import jmp.rest.service.MongoCLIRestService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by Nona_Sarokina on 10/31/2016.
 */
public class Runner {

    public static final String HTTP_LOCALHOST = "http://localhost/";
    public static final String PATH_SPEC = "/*";

    public static void main(String[] args) {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        URI baseUri = UriBuilder.fromUri(HTTP_LOCALHOST).port(8888).build();
        ResourceConfig config = new MongoCLIResourceConfig();
        Server jettyServer = JettyHttpContainerFactory.createServer(baseUri, config, false);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class,  PATH_SPEC);
        //jerseyServlet.setInitParameter("cacheControl","max-age=0,public");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                MongoCLIRestService.class.getCanonicalName());

        try {
            jettyServer.start();
            jettyServer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jettyServer.destroy();
        }
    }
}
