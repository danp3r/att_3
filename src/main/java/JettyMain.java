import java.lang.reflect.Field;
import java.util.Map;

public class JettyMain {
    private static PostgresConnection postgressConnection = new PostgresConnection();
    private static Map<Class, QuaeryGenerator> quaeryGeneratorMap;
    static {
        try {
            quaeryGeneratorMap = Map.of(
                    Location.class, new QuaeryGenerator("locations", Location.class),
                    OnlineShop.class, new QuaeryGenerator("shops", OnlineShop.class),
                    Order.class, new QuaeryGenerator("orders", Order.class),
                    PointOfIssue.class, new QuaeryGenerator("issues", PointOfIssue.class),
                    User.class, new QuaeryGenerator("users", User.class)
            );
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {

        MyServer myServer = new MyServer(
                quaeryGeneratorMap,
                postgressConnection
        );
        myServer.startServer();
    }
}
