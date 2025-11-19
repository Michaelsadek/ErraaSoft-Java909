public class NullPointer {
    public static void printUpper(String str) {
        try {
            String upper = str.toUpperCase();
            System.out.println(upper);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException caught!");
        }
    }
    public static void main(String[] args) {
        printUpper("Hello World");
        printUpper(null);
    }

}
