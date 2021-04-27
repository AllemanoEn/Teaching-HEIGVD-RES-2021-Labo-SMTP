package smtp;

import model.Mail;

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

        LOG.info(fromSMTPserver);
        pw.println("EHLO localhost");

        fromSMTPserver = br.readLine();
        if(!fromSMTPserver.startsWith("250")){
            throw new IOException("SMTP error: "+ fromSMTPserver);
        }

        while (fromSMTPserver.startsWith("250-")){
            fromSMTPserver = br.readLine();
            LOG.info(fromSMTPserver);
        }

        pw.println("MAIL FROM:" + mail.getFrom());
        fromSMTPserver = br.readLine();

        //at this point, we could not differ between TO, CC and BCC

        for (String to : mail.getTo()){
            pw.println("RCPT TO:" + to);
            fromSMTPserver = br.readLine();
            LOG.info(fromSMTPserver);
        }

        for (String to : mail.getCc()){
            pw.println("RCPT TO:" + to);
            fromSMTPserver = br.readLine();
            LOG.info(fromSMTPserver);
        }

        for (String to : mail.getBcc()){
            pw.println("RCPT TO:" + to);
            fromSMTPserver = br.readLine();
            LOG.info(fromSMTPserver);
        }

        pw.println("DATA");
        fromSMTPserver = br.readLine();
        LOG.info(fromSMTPserver);
        pw.println("Content Type: text/plain; charset=utf-8");
        pw.println("From: " + mail.getFrom());

        pw.write("To: " + mail.getTo()[0]);
        for(int i = 1; i < mail.getTo().length;i++){
            pw.write(", " + mail.getTo()[i]);
        }
        pw.write("\r\n");

        pw.write("Cc: " + mail.getCc()[0]);
        for(int i = 1; i < mail.getCc().length;i++){
            pw.write(", " + mail.getCc()[i]);
        }
        pw.write("\r\n");

        pw.write("Bcc: " + mail.getBcc()[0]);
        for(int i = 1; i < mail.getBcc().length;i++){
            pw.write(", " + mail.getBcc()[i]);
        }
        pw.write("\r\n");

        pw.flush();

        LOG.info(mail.getBody());
        pw.println(mail.getBody());

        fromSMTPserver = br.readLine();
        LOG.info(fromSMTPserver);

        pw.println("QUIT");

        pw.close();
        br.close();
        clientSocket.close();
    }
}
