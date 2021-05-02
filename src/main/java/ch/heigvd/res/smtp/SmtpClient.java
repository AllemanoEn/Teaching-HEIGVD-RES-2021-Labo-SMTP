package ch.heigvd.res.smtp;

import ch.heigvd.res.model.Mail;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class SmtpClient implements ISmtpClient{

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());

    private String smtpServerAddress;
    private int smtpServerPort;

    private BufferedReader br;
    private PrintWriter pw;
    private Socket clientSocket;

    public SmtpClient(String smtpServerAddress,int smtpServerPort){
        this.smtpServerAddress=smtpServerAddress;
        this.smtpServerPort=smtpServerPort;
    }

    @Override
    public void sendMail(Mail mail) throws IOException {
        clientSocket = new Socket(smtpServerAddress, smtpServerPort);

        System.out.println("-- STARTING SMPT CONNECTION --");

        br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
        pw = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),StandardCharsets.UTF_8),true);

        String fromSMTPserver = br.readLine();

        System.out.println(fromSMTPserver);
        pw.println("EHLO localhost");

        fromSMTPserver = br.readLine();
        if(!fromSMTPserver.startsWith("250")){
            throw new IOException("SMTP error: "+ fromSMTPserver);
        }

        System.out.println(fromSMTPserver);

        while (fromSMTPserver.startsWith("250-")){
            fromSMTPserver = br.readLine();
            System.out.println(fromSMTPserver);
        }

        pw.println("MAIL FROM:" + mail.getFrom());
        fromSMTPserver = br.readLine();
        System.out.println(fromSMTPserver + " - MAIL FROM");

        //at this point, we could not differ between TO, CC and BCC

        pw.println("RCPT TO:" + mail.getTo());
        fromSMTPserver = br.readLine();
        System.out.println(fromSMTPserver + " - RCPT TO");

        for (String to : mail.getCc()){
            pw.println("RCPT TO:" + to);
            fromSMTPserver = br.readLine();
            System.out.println(fromSMTPserver + " - RCPT TO");
        }

        pw.println("DATA");
        fromSMTPserver = br.readLine();
        System.out.println(fromSMTPserver + " - DATA");
        pw.println("Content-type: text/plain; charset=utf-8");
        pw.println("From: " + mail.getFrom());

        pw.println("To: " + mail.getTo());

        pw.write("Cc: " + mail.getCc()[0]);
        for(int i = 1; i < mail.getCc().length;i++){
            pw.write(", " + mail.getCc()[i]);
        }
        pw.write("\r\n");
        pw.flush();

        pw.println("Subject: " + mail.getSubject());
        pw.write("\r\n");

        pw.println(mail.getBody());

        pw.write(".\r\n");
        pw.flush();
        
        fromSMTPserver = br.readLine();
        System.out.println(fromSMTPserver + " - Body");

        pw.println("QUIT");

        pw.close();
        br.close();
        clientSocket.close();
    }
}
