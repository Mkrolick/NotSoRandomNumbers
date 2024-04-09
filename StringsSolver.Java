import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        // Assuming you have an iterator named "iterator"
        Iterator<String> iterator = ...; // Replace ... with your iterator initialization

        while (iterator.hasNext()) {
            String element = iterator.next();
            // Do something with the element
            System.out.println(element);
        }
    }
}