import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ToStringHelper {

    public static String resultSet(ResultSet resultSet) throws SQLException {

        String s = "";

        int colCount = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()){
            for (int i = 0; i < colCount; i++) {
                s+= resultSet.getMetaData().getColumnName(i) +"   " + resultSet.getObject(i).toString()+"\n";
            }
            s+="\n";
        }
        return s;
    }
}
