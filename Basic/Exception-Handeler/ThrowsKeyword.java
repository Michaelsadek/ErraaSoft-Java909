import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ThrowsKeyword {
    public static void readFile(String fileName)throws IOException{

        System.out.println("Attemting to read: " + fileName);
        File file = new File(fileName);
        Scanner fileReader = new Scanner(file);
        System.out.println("File Found");
        fileReader.close();
    }

    public static void main(String[] args){
        try {
            readFile("tasks.tx");
        } catch (IOException e) {
            System.out.println("Caught IOException in main:" + e.getMessage());
        }
    }
    
}
