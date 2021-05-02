package ch.heigvd.res.model;

import java.util.*;
import lombok.Getter;

public class Group {
    @Getter private Vector<Person> members = new Vector<>();

    /**
     * Method to add a member to a group
     *
     * @param person person to be added
     */
    public void addMember(Person person){
        members.add(person);
    }
}
