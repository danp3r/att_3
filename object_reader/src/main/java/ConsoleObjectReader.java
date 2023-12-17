import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleObjectReader implements ObjectReader {
    
    private ValueGetterInterface valGet = new ConsoleValueGetter();

    public ConsoleObjectReader() {
    }

    @Override
    public Map<String, Object> readObject(List<Field> fields) {
        Map<String, Object> object = new HashMap<>();
        for (Field f: fields) {
            Object val = valGet.getVal(f);
            if (isFilled(val)){
                object.put(
                        f.getName(),
                        val
                ); 
            }
            
        }
        return object;
    }

    @Override
    public Map<String, Object> readObject(List<Field> fields, String prefix) throws InstantiationException, IllegalAccessException {
        Map<String, Object> object = new HashMap<>();
        for (Field f: fields) {
            Object val = valGet.getVal(f, prefix);
            if (isFilled(val)){
                object.put(
                        f.getName(),
                        val
                );
            }

        }
        return object;
    }
}
