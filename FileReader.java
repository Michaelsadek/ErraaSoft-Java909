
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    public static void main(String[] args) {
        try {
            File file = new File("C:\\Users\\DELL\\Desktop\\tasks.tx");
            Scanner filScanner = new Scanner(file);

            while (filScanner.hasNextLine()) {
                System.out.println(filScanner.nextLine());
                
            }
            filScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
    
}
