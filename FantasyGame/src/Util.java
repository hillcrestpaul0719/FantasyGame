import java.util.*;

public class Util {
    public static String humanList(List<String> items) {
        String result = "";
        if (items.size() == 0) return result;
        else result = items.get(0);
        for (int i=1; i<items.size()-1; i++) {
            result += ", " + items.get(i);
        }
        result += ", and " + items.get(items.size()-1);
        return result;
    }
}
