import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ConsoleReader {

    private Map<Class, QuaeryGenerator> quaeryGeneratorMap;
    private PostgresConnection postgresConnection;

    private Set<String> commands = Set.of(
            "exit", "help", "tables", "commands", "insert", "delete", "update", "find", "findall"
    );

    private void commands(){
        System.out.println("COMMANDS:");
        for (String c:commands) {
            System.out.println(c);
        }
        System.out.println();
    }
    private void tables(){
        System.out.println("TABLES: ");
        for (QuaeryGenerator qg:quaeryGeneratorMap.values()) {
            System.out.println(qg.getTableName());
        }
        System.out.println();
    }
    private void help(){
        commands();
        tables();
    }


    public ConsoleReader(Map<Class, QuaeryGenerator> quaeryGeneratorMap, PostgresConnection postgresConnection) {
        this.quaeryGeneratorMap = quaeryGeneratorMap;
        this.postgresConnection = postgresConnection;
    }

    public void readAll() throws Exception {

        while (true){
            System.out.print("command: ");
            String command = new Scanner(System.in).nextLine().trim();
            if(!command.contains(command)){
                System.out.println("this command dont support");
                System.out.println("supported commands");
                commands();
            } else if (command.equals("exit")) {
                return;
            } else if (command.equals("help")) {
                help();
            } else if (command.equals("tables")) {
                tables();
            } else if(command.equals("commands")){
                commands();
            }else {
                System.out.print("table: ");
                String table = new Scanner(System.in).nextLine().trim();
                QuaeryGenerator qg = byTable(table);
                if (qg == null){
                    System.out.println("this table dont exist");
                    System.out.println("existing tables");
                    tables();
                    continue;
                }
                if (command.equals("findAll")){
                    System.out.println("++++");
                    System.out.println(
                            ToStringHelper.resultSet(postgresConnection.executeQuaery(qg.findAll()))
                    );

                } else if (command.equals("find")) {

                    Map<String, Object> values = new ConsoleObjectReader().readObject(qg.getFields());
                    System.out.println(
                            ToStringHelper.resultSet(postgresConnection.executeQuaery(qg.find(values)))
                    );
                } else if (command.equals("delete")) {
                    Map<String, Object> values = new ConsoleObjectReader().readObject(qg.getFields());
                    postgresConnection.execute(qg.delete(values));
                } else if (command.equals("insert")) {
                    Map<String, Object> values = new ConsoleObjectReader().readObject(qg.getFields());
                    postgresConnection.execute(qg.delete(values));
                } else if (command.equals("update")) {
                    System.out.println("condition: ?");
                    Map<String, Object> condition = new ConsoleObjectReader().readObject(qg.getFields());
                    System.out.println("set: ?");
                    Map<String, Object> set = new ConsoleObjectReader().readObject(qg.getFields());
                    postgresConnection.execute(qg.update(condition, set));
                }
            }
        }
    }

    private QuaeryGenerator byTable(String tableName){
        for (QuaeryGenerator qg: quaeryGeneratorMap.values()) {
            if(qg.getTableName().equals(tableName))
                return qg;
        }
        return null;
    }

}
