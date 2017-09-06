import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Reflections {

    /**
     * Returns all Fields of this class as stringized Key-Value Map
     *
     * @return Map<String, String> ... all Fields as Key-Value
     */
    public Map<String, String> getAllFields() {
        Method[] methods = this.getClass().getMethods();
        Map<String, String> map = new HashMap<>();
        for (Method m : methods) {
            if (m.getName().startsWith("get")) {
                String value = null;
                try {
                    value = (String) m.invoke(this);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                map.put(m.getName().substring(3), value);
            }
        }
        return map;
    }
}
