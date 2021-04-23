import model.*;
import smtp.SmtpClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class AutoMailPranker {
    public static void main(String[] args) throws IOException {

        Person p = new Person("enzo.allemano@hotmail.com");

        System.out.println(p.getAddress());
        System.out.println(p.getFirstName());
        System.out.println(p.getLastName());

        Group g = new Group();
        g.addMember(p);

        String appConfigPath = "C:\\Users\\jetca\\Documents\\RES\\labo4\\Teaching-HEIGVD-RES-2021-Labo-SMTP\\src\\main\\java\\config\\config.properties";
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));
        System.out.println(appProps.getProperty("smtpServerAddress"));
        System.out.println(appProps.getProperty("smtpServerPort"));
        System.out.println(appProps.getProperty("numberOfGroup"));
        System.out.println(appProps.getProperty("witnessToCC"));

        SmtpClient SMTPc1 = new SmtpClient(appProps.getProperty("smtpServerAddress"),Integer.parseInt(appProps.getProperty("smtpServerPort")));



    }
}
