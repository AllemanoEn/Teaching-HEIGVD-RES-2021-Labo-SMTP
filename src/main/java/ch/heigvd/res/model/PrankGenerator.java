package ch.heigvd.res.model;

import ch.heigvd.res.smtp.SmtpClient;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PrankGenerator{

    String pathToMessageFile;
    String pathToVictimsFile;
    Vector<String> vMessage = new Vector<>();
    Vector<Person> vPerson = new Vector<>();
    Vector<Group> vGroup = new Vector<>();
    Vector<Prank> vPrank = new Vector<>();
    BufferedReader bfMessages;
    BufferedReader bfMailAddress;
    int groupSize;
    int nbGroup;
    Properties appProps;

    /**
     * Constructor of the class PrankGenerator
     *
     * @param pathToMessageFile path to the message file
     * @param pathToVictimsFile  path to the victims file
     * @param groupSize   size of the group
     * @param appConfigPath   path to the config file
     */
    public PrankGenerator(String pathToMessageFile, String pathToVictimsFile, int groupSize, String appConfigPath) throws IOException {

        appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        nbGroup = Integer.parseInt(appProps.getProperty("numberOfGroup"));

        if (groupSize < 3 || nbGroup < 1){
            throw new RuntimeException("Group number must be grather or egal than 3 \nGroup size must be grather than or egal than  1");
        }

        this.pathToMessageFile = pathToMessageFile;
        this.pathToVictimsFile = pathToVictimsFile;
        this.groupSize = groupSize;
    }

    /**
     * Method to generate the prank and initialize everything useful
     *
     */
    public void generate() throws IOException {
        bfMessages = new BufferedReader(new InputStreamReader(new FileInputStream(pathToMessageFile),StandardCharsets.UTF_8));
        bfMailAddress = new BufferedReader(new InputStreamReader(new FileInputStream(pathToVictimsFile),StandardCharsets.UTF_8));

        //Put all message from message file into a vector
        String line = "";
        String it = "";
        while ((line = bfMessages.readLine()) != null){
            if(line.equals("--")){
                vMessage.add(it);
                it = "";
            }else{
                it += line+"\r\n";
            }
        }

        //Create all person by using fake mail address
        String person;
        while ((person = bfMailAddress.readLine()) != null){
            vPerson.add(new Person(person));
        }

        for (int i = 0;i < nbGroup;i++){
            vGroup.add(new Group());
        }

        for(Group g : vGroup){
            for(int i = 0;i < groupSize;i++){
                g.addMember(vPerson.elementAt(i));
                Collections.shuffle(vPerson);
            }
        }

        int random;
        Random generator = new Random();
        for (int i = 0;i < nbGroup;i++){
            random = generator.nextInt(vMessage.size());
            vPrank.add(new Prank(vGroup.elementAt(i),vMessage.elementAt(random)));
        }

        SmtpClient smtpClient = new SmtpClient(appProps.getProperty("smtpServerAddress"),Integer.parseInt(appProps.getProperty("smtpServerPort")));

        for(Prank p : vPrank){
            smtpClient.sendMail(p.generateMailMessage());
        }

        bfMessages.close();
        bfMailAddress.close();
    }
}
