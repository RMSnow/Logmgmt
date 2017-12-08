import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SetTest {
    public static void main(String[] args){
        Set<String> sets = new HashSet<>();
        sets.add("a");
        sets.add("b");
        sets.add("c");
        sets.add("c");
        sets.add("a");
        sets.add("b");

        Iterator iterator = sets.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        if (sets.contains("a")){
            System.out.println("a ok");
        }

        if (sets.contains("6")){
            System.out.println("6 ok");
        }
    }
}
