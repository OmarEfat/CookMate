package comp3350.CookMate.tests.objects;

import org.junit.Test;

import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.objects.DietaryRestriction;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

public class CookTest {

    @Test
    public void testGetCook() {
        Cook cook;
        List<DietaryRestriction> dietaryRestriction = new LinkedList<>();
        dietaryRestriction.add(new DietaryRestriction("Halal"));
        dietaryRestriction.add(new DietaryRestriction("Vegan"));

        System.out.println("\nStarting testGetCook");

        cook = new Cook("001", "Jane Doe", "123", "Jane", dietaryRestriction);
        assertNotNull(cook);
        assertTrue("001".equals(cook.getUserID()));
        assertTrue("Jane Doe".equals(cook.getUserName()));
        assertTrue("123".equals(cook.getPassword()));
        assertTrue("Jane".equals(cook.getPreferredName()));
        assertTrue(dietaryRestriction.equals(cook.getDietaryRestrictions()));

        System.out.println("\nEnding testGetCook");

    }

    @Test
    public void testSetCook() {
        Cook cook;
        List<DietaryRestriction> dietaryRestriction = new LinkedList<>();
        dietaryRestriction.add(new DietaryRestriction("Halal"));
        dietaryRestriction.add(new DietaryRestriction("Vegan"));

        System.out.println("\nStarting testSetCook");

        cook = new Cook("001", "Jane Doe", "123", "Jane", dietaryRestriction);
        assertNotNull(cook);
        cook.setUserID("002");
        cook.setUserName("John Doe");
        cook.setPassword("321");
        cook.setPreferredName("John");
        dietaryRestriction.clear();
        dietaryRestriction.add(new DietaryRestriction("Lactose Tolerant"));
        cook.setDietaryRestrictions(dietaryRestriction);
        assertTrue("002".equals(cook.getUserID()));
        assertTrue("John Doe".equals(cook.getUserName()));
        assertTrue("321".equals(cook.getPassword()));
        assertTrue("John".equals(cook.getPreferredName()));
        assertTrue(dietaryRestriction.equals(cook.getDietaryRestrictions()));
        System.out.println("\nEnding testSetCook");
    }

    @Test
    public void testCookUserEnterEmpty(){
        Cook cook;
        List<DietaryRestriction> dietaryRestriction = new LinkedList<>();

        System.out.println("\nStarting testCookUserEnterEmpty");

        cook = new Cook("", "", "", "", dietaryRestriction);
        assertNotNull(cook);
        assertEquals(cook.getUserID(),"");
        assertEquals(cook.getUserName(),"");
        assertEquals(cook.getPassword(),"");
        assertEquals(cook.getPreferredName(),"");
        System.out.println("\nEnding testCookUserEnterEmpty");
    }
}
