import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class sheetReader {

    public static String readFile(String filename) throws IOException {

        String filePath = String.format("%s.txt", filename);
        int charCode;

        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();

        while ((charCode = bufferedReader.read()) != -1) {
            stringBuilder.append((char) charCode);
        }

        bufferedReader.close();

        return stringBuilder.toString();
    }
}
