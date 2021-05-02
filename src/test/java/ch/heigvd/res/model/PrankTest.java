package ch.heigvd.res.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrankTest {

    @Test
    public void itShouldCreateAPrankProperly() {
        Person p1 = new Person("enzo.allemano@heig-vd.ch");
        Person p2 = new Person("luca.zacheo@heig-vd.ch");
        Person p3 = new Person("bigbrother.theone@heig-vd.ch");

        Group g1 = new Group();
        g1.addMember(p1);
        g1.addMember(p2);
        g1.addMember(p3);

        Prank prank1 = new Prank(g1, "SUBJECT : No1 PRANK (accent éèà)!!\n" +
                "Ceci est le message No1 PRANK. On peut meme utiliser des retours à la ligne,\r\n" +
                "car le séparateur de message est un double trait d'union.");

        Mail m1 = prank1.generateMailMessage();
        assertEquals("enzo.allemano@heig-vd.ch",m1.getFrom());
        assertEquals("No1 PRANK (accent éèà)",m1.getSubject());
        assertEquals("luca.zacheo@heig-vd.ch",m1.getTo());

        String[] test =m1.getCc();

        assertEquals("bigbrother.theone@heig-vd.ch",test[0]);

    }





}