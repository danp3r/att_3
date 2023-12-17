import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.net.http.HttpRequest;

public class RequestValueGetter implements ValueGetterInterface{
    private HttpServletRequest request;

    public RequestValueGetter(HttpServletRequest request) {
        this.request = request;
    }


    @Override
    public Object getVal(Field field, String prefix) {
        String val = request.getParameter(prefix+field.getName());
        return ValueConverter.convert(val, field.getType());
    }
}
