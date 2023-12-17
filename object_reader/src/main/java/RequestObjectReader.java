import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestObjectReader implements ObjectReader{

    private HttpServletRequest request;
    private ValueGetterInterface valGet = new RequestValueGetter(request);

    public RequestObjectReader(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public Map<String, Object> readObject(List<Field> fields, String prefix) throws InstantiationException, IllegalAccessException {
        Map<String, Object> object = new HashMap<>();

        for (Field f: fields) {
            Object val = valGet.getVal(f, prefix);
            if(isFilled(val))
                object.put(f.getName(), val);
        }

        return object.isEmpty() ? null : object;
    }
}
