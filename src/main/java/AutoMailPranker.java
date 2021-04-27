import model.*;
import smtp.SmtpClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.Properties;




public class AutoMailPranker {
    public static void main(String[] args) throws IOException {

        Person p = new Person("enzo.allemano@hotmail.com");

        System.out.println(p.getAddress());
        System.out.println(p.getFirstName());
        System.out.println(p.getLastName());

        Group g = new Group();
        g.addMember(p);

        Path currentRelativePath = Paths.get("");
        String currentDir = currentRelativePath.toAbsolutePath().toString();
        String appConfigPath = currentDir + "\\src\\main\\java\\config\\config.properties";
        String messageFilePath = currentDir + "\\src\\main\\java\\config\\messages.utf8";
        String victimsFilePath = currentDir + "\\src\\main\\java\\config\\victims.utf8";

        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));
        System.out.println(appProps.getProperty("smtpServerAddress"));
        System.out.println(appProps.getProperty("smtpServerPort"));
        System.out.println(appProps.getProperty("numberOfGroup"));
        System.out.println(appProps.getProperty("witnessToCC"));

        /*SmtpClient SMTPc1 = new SmtpClient(appProps.getProperty("smtpServerAddress"),Integer.parseInt(appProps.getProperty("smtpServerPort")));

        Mail m1 = new Mail();

        SMTPc1.sendMail(m1);*/

        PrankGenerator pg = new PrankGenerator(messageFilePath,victimsFilePath);
        pg.generate();


    }
}
