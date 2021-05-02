package ch.heigvd.res.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;

public class Person {
    @Getter private String firstName;
    @Getter private String lastName;
    @Getter private String address;

    public Person(String firstName,String lastName,String address) {
        this.address = address;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Person(String address){
        this.address = address;
        Pattern pattern = Pattern.compile("(.*)\\.(.*)@");
        Matcher matcher = pattern.matcher(address);
        boolean found = matcher.find();
        if (found){
            this.firstName = matcher.group(1);
            this.lastName = matcher.group(2);
        }else{
            System.out.println("Mail non valide !");
        }
    }
}
