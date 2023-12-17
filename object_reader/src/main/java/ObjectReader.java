import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public interface ObjectReader {
    default Map<String, Object> readObject(List<Field> fields) throws InstantiationException, IllegalAccessException{
        return readObject(fields, "");
    }
    Map<String, Object> readObject(List<Field> fields, String prefix) throws InstantiationException, IllegalAccessException;
    default boolean isFilled(Object object){
        return object != null && !object.toString().isEmpty();
    }
}
