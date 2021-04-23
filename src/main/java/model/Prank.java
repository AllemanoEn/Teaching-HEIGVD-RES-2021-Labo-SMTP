package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Prank {
    private Person victimSender;
    private List<Person> victimList = new ArrayList<>();
    private List<Person> witnessList = new ArrayList<>();
    private String message;

    public Mail generateMailMessage(){
        Mail msg = new Mail();
        msg.setBody(this.message + "\r\n" + victimSender.getFirstName());

        String[] to = victimList.stream().map(person -> person.getAddress()).collect(Collectors.toList()).toArray(new String[]{});
        msg.setTo(to);

        String[] cc = witnessList.stream().map(person -> person.getAddress()).collect(Collectors.toList()).toArray(new String[]{});
        msg.setCc(cc);

        msg.setFrom(victimSender.getAddress());

        return msg;
    }
}
