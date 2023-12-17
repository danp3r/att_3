import java.lang.reflect.Field;
import java.util.Scanner;

public class ConsoleValueGetter implements ValueGetterInterface{

    @Override
    public Object getVal(Field field, String prefix) {
        System.out.print(prefix+field.getName()+": ");
        String val = new Scanner(System.in).nextLine().trim();

        return ValueConverter.convert(val, field.getType());
    }
}
