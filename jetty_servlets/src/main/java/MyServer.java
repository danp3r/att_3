import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.Map;

public class MyServer {
    private static final int PORT = 8080;
    private Map<Class, QuaeryGenerator> quaeryGeneratorMap;
    private PostgresConnection postgresConnection;

    public MyServer(Map<Class, QuaeryGenerator> quaeryGeneratorMap, PostgresConnection postgresConnection) {
        this.quaeryGeneratorMap = quaeryGeneratorMap;
        this.postgresConnection = postgresConnection;
    }
    public void startServer() throws Exception {
        Server server = new Server(PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        for (Class c: quaeryGeneratorMap.keySet()) {
            context.addServlet(
                    new ServletHolder(
                            new MyServlet(
                                    postgresConnection,
                                    quaeryGeneratorMap.get(c)
                            )
                    ),
                    "/%s".formatted(c.getSimpleName().toLowerCase())
            );
        }

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { context });
        server.setHandler(handlers);

        server.start();
        server.join();

    }


}