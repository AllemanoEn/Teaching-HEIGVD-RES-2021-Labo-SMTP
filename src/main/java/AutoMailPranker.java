import model.*;
import smtp.SmtpClient;

import java.io.IOException;
import java.nio.file.*;

public class AutoMailPranker {
    public static void main(String[] args) throws IOException {

        Path currentRelativePath = Paths.get("");
        String currentDir = currentRelativePath.toAbsolutePath().toString();
        String appConfigPath = currentDir + "\\src\\main\\java\\config\\config.properties";
        String messageFilePath = currentDir + "\\src\\main\\java\\config\\messages.utf8";
        String victimsFilePath = currentDir + "\\src\\main\\java\\config\\victims.utf8";

        PrankGenerator pg = new PrankGenerator(messageFilePath,victimsFilePath,10,appConfigPath);
        pg.generate();
    }
}
