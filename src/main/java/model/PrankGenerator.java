package model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class PrankGenerator {

    String pathToMessageFile;
    String pathToVictimsFile;
    Vector<String> vMessage = new Vector<>();
    Vector<Person> vPerson = new Vector<>();
    BufferedReader bfMessages;
    BufferedReader bfMailAddress;

    public PrankGenerator(String pathToMessageFile, String pathToVictimsFile){
        this.pathToMessageFile = pathToMessageFile;
        this.pathToVictimsFile = pathToVictimsFile;

    }

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


        bfMessages.close();
        bfMailAddress.close();
    }
}
