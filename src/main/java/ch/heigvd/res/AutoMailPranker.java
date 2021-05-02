package ch.heigvd.res;

import ch.heigvd.res.model.*;

import java.io.IOException;
import java.nio.file.*;

public class AutoMailPranker {
    public static void main(String[] args) throws IOException {

        Path currentRelativePath = Paths.get("");
        String currentDir = currentRelativePath.toAbsolutePath().toString();
        String appConfigPath = currentDir + "\\config\\config.properties";
        String messageFilePath = currentDir + "\\config\\messages.utf8";
        String victimsFilePath = currentDir + "\\config\\victims.utf8";

        PrankGenerator pg = new PrankGenerator(messageFilePath,victimsFilePath,appConfigPath);
        pg.generate();
    }
}
