package fr.istic.vv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class DateTest {




    @Test
    public void getDateTest(){
        Date date = new Date(20,2 ,2001);
        String result = date.getDate();
        assertEquals("20/2/2001", result, "La date renvoyée devrait être \'20/2/2001\' en réalité on obtient :\'"+result+"\'");
    }

    @Test
    public void isValidDateTest()
    {
        // Partition
        // year < 0 / 0 / leap / common
        // month 2 / 30d / 31d / <= 0 / > 12
        // day : <29 / 29 / 30 / 31 / < 0

        // On utilise BCC
        // Cas de base 
        // year : leap = 2004
        // month : 31d = 12
        // day : 28


        boolean result = Date.isValidDate(28, 2, 2004);
        assertTrue(result,"La date \'28/12/2004\' devrait être valide");
        // year
        result = Date.isValidDate(28, 12, 2003); // common year
        assertTrue(result,"La date \'28/12/2003\' devrait être valide");
        result = Date.isValidDate(28, 12, 0); // 0
        assertTrue(result,"La date \'28/12/0\' devrait être valide");
        result = Date.isValidDate(28, 12, -100); // <0
        assertTrue(result,"La date \'28/12/-100\' devrait être valide");
        // month
        result = Date.isValidDate(28, 11, 2004); // 30
        assertTrue(result,"La date \'28/11/2004\' devrait être valide");
        result = Date.isValidDate(28, 2, 2004); // 2
        assertTrue(result,"La date \'28/2/2004\' devrait être valide");
        result = Date.isValidDate(1, 13, 2004); 
        assertFalse(result,"La date \'28/13/2004\' devrait être invalide");       
        result = Date.isValidDate(1, -2, 2004); 
        assertFalse(result,"La date \'28/-2/2004\' devrait être invalide");   
        // day
        result = Date.isValidDate(29, 12, 2004); // 29
        assertTrue(result,"La date \'29/12/2004\' devrait être valide");
        result = Date.isValidDate(30, 12, 2004); // 30
        assertTrue(result,"La date \'30/12/2004\' devrait être valide");
        result = Date.isValidDate(31, 12, 2004); // 31
        assertTrue(result,"La date \'31/12/2004\' devrait être valide");
        result = Date.isValidDate(-1, 2, 2004);
        assertFalse(result,"La date \'-1/2/2004\' devrait être invalide");   

        // ajout
        result = Date.isValidDate(29, 2, 2004); 
        assertTrue(result,"La date \'29/2/2004\' devrait être valide");     
        /*result = Date.isValidDate(29, 3, 2004); 
        assertTrue(result,"La date \'29/3/2004\' devrait être valide");*/    
        result = Date.isValidDate(29, 2, 2003); 
        assertFalse(result,"La date \'29/2/2003\' devrait être invalide");
        result = Date.isValidDate(31, 11, 2004); 
        assertFalse(result,"La date \'31/11/2003\' devrait être invalide");                

    }
    
    @Test
    public void isLeapYearTest()
    {
        // Seul year est concerner
        // year : <0 / 0 / leap / common
        boolean result = Date.isLeapYear(2004);
        assertTrue(result, "L'année 2004 doit être bissextile");
        result = Date.isLeapYear(2);
        assertFalse(result, "L'année 2 ne doit pas être bissextile");
        result = Date.isLeapYear(0);
        assertTrue(result, "L'année 0 doit être bissextile");
        result = Date.isLeapYear(-2004);
        assertTrue(result, "L'année 2004 doit être bissextile");
        
    }

    @Test 
    public void nextDateTest(){
        //Partition
        // year < 0 / 0 / leap / common
        // month : 2 / 30d / 31d / 12 
        // day : last / before last / common

        // On utilise BCC
        // Cas de base 
        // year : leap = 2004
        // month : 12
        // day : last

        Date date = new Date(31, 12, 2004);
        Date result = date.nextDate();
        assertTrue(result.equals(new Date(1,1,2005)));
        //assertEquals(new Date(1,1,2005), result, "La date devrait être \'1/1/2005\' en l'occurence on a : \'"+result.getDate()+"\'");

        //year
        date = new Date(31, 12, 2003);
        result = date.nextDate();
        assertTrue(result.equals(new Date(1,1,2004)));
        //assertEquals(new Date(1,1,2004), result, "La date devrait être \'1/1/2004\' en l'occurence on a : \'"+result.getDate()+"\'");
        date = new Date(31, 12, 0);
        result = date.nextDate();
        assertTrue(result.equals(new Date(1,1,1)));
        //assertEquals(new Date(1,1,1), result, "La date devrait être \'1/1/1\' en l'occurence on a : \'"+result.getDate()+"\'");
        date = new Date(31, 12, -1);
        result = date.nextDate();
        assertTrue(result.equals(new Date(1,1,0)));
        //assertEquals(new Date(1,1,0), result, "La date devrait être \'1/1/0\' en l'occurence on a : \'"+result.getDate()+"\'");

        //month
        date = new Date(29, 2, 2004);
        result = date.nextDate();
        assertTrue(result.equals(new Date(1,3,2004)), "La date devrait être \'1/3/2004\' en l'occurence on a : \'"+result.getDate()+"\'");
        date = new Date(30, 11, 2004);
        result = date.nextDate();
        assertTrue(result.equals(new Date(1,12,2004)), "La date devrait être \'1/12/2004\' en l'occurence on a : \'"+result.getDate()+"\'");
        date = new Date(31, 10, 2004);
        result = date.nextDate();
        assertTrue(result.equals(new Date(1,11,2004)), "La date devrait être \'1/11/2004\' en l'occurence on a : \'"+result.getDate()+"\'");


        //day
        date = new Date(30, 12, 2004);
        result = date.nextDate();
        assertTrue(result.equals(new Date(31,12,2004)), "La date devrait être \'31/12/2004\' en l'occurence on a : \'"+result.getDate()+"\'");
        date = new Date(29, 12, 2004);
        result = date.nextDate();
        assertTrue(result.equals(new Date(30,12,2004)), "La date devrait être \'30/12/2004\' en l'occurence on a : \'"+result.getDate()+"\'");

        //ajout
        date = new Date(28, 2, 2003);
        result = date.nextDate();
        assertTrue(result.equals(new Date(1,3,2003)), "La date devrait être \'1/3/2004\' en l'occurence on a : \'"+result.getDate()+"\'");
        date = new Date(28, 2, 2004);
        result = date.nextDate();
        assertTrue(result.equals(new Date(29,2,2004)), "La date devrait être \'1/3/2004\' en l'occurence on a : \'"+result.getDate()+"\'");
    }

    @Test
    public void previousDateTest()
    {
        //Partition
        // year < 0 / 0 / leap / common
        // month : 3 / 30d / 31d / 1
        // day : 1 / other

        // On utilise BCC
        // Cas de base 
        // year : leap = 2004
        // month : 1
        // day : 1

        Date date = new Date(1, 1, 2004);
        Date result = date.previousDate();
        assertTrue(result.equals(new Date(31,12,2003)), "La date devrait être \'31/12/2003\' en l'occurence on a : \'"+result.getDate()+"\'");

        // year
        date = new Date(1, 1, 2003);
        result = date.previousDate();
        assertTrue(result.equals(new Date(31,12,2002)), "La date devrait être \'31/12/2002\' en l'occurence on a : \'"+result.getDate()+"\'");
        date = new Date(1, 1, 0);
        result = date.previousDate();
        assertTrue(result.equals(new Date(31,12,-1)), "La date devrait être \'31/12/-1\' en l'occurence on a : \'"+result.getDate()+"\'"); 
        date = new Date(1, 1, -1);
        result = date.previousDate();
        assertTrue(result.equals(new Date(31,12,-2)), "La date devrait être \'31/12/-2\' en l'occurence on a : \'"+result.getDate()+"\'");
        //month
        date = new Date(1, 3, 2004);
        result = date.previousDate();
        assertTrue(result.equals(new Date(29,2,2004)), "La date devrait être \'29/2/2004\' en l'occurence on a : \'"+result.getDate()+"\'");
        date = new Date(1, 12, 2004);
        result = date.previousDate();
        assertTrue(result.equals(new Date(30,11,2004)), "La date devrait être \'30/11/2004\' en l'occurence on a : \'"+result.getDate()+"\'");
        date = new Date(1, 11, 2004);
        result = date.previousDate();
        assertTrue(result.equals(new Date(31,10,2004)), "La date devrait être \'31/10/2004\' en l'occurence on a : \'"+result.getDate()+"\'");
        //day
        date = new Date(2, 1, 2004);
        result = date.previousDate();
        assertTrue(result.equals(new Date(1,1,2004)), "La date devrait être \'1/1/2004\' en l'occurence on a : \'"+result.getDate()+"\'");

        //added
        date = new Date(1, 3, 2003);
        result = date.previousDate();
        assertTrue(result.equals(new Date(28,2,2003)), "La date devrait être \'28/2/2003\' en l'occurence on a : \'"+result.getDate()+"\'");

    }

    @Test
    public void compareToTest()
    {
        // month + day : m-month + last day ; m-month+1 + first day / m-month+1 + first day ; m-month + last day / same / other
        // year : 0 ; other / < 0 ; < 0 / > 0 ; < 0 / > 0 ; > 0 / same / other

        ///BCC
        // month + day : same
        // year : same

        Date date1 = new Date(26,02,2004);
        Date date2 = new Date(26,02,2004);
        int result = date1.compareTo(date2);
        int expected = 0;
        assertTrue( result == expected, "Les dates devrait être égale mais elle renvoie : "+result);

        // month+date
        date1 = new Date(30,11,2004);
        date2 = new Date(1,12,2004);
        result = date1.compareTo(date2);
        assertTrue( result < 0, "la comparaison des dates devrait être < 0 mais en l'ocurrance on a : "+result);
        result = date2.compareTo(date1);
        assertTrue( result > 0, "la comparaison des dates devrait être > 0 mais en l'ocurrance on a : "+result);
        date1 = new Date(23,6,2004);
        date2 = new Date(2,10,2004);
        result = date1.compareTo(date2);
        assertTrue( result < 0, "la comparaison des dates devrait être < 0 mais en l'ocurrance on a : "+result);
        result = date2.compareTo(date1);
        assertTrue( result > 0, "la comparaison des dates devrait être > 0 mais en l'ocurrance on a : "+result);

        // year
        date1 = new Date(30,11,2003);
        date2 = new Date(30,11,2004);
        result = date1.compareTo(date2);
        assertTrue( result < 0, "la comparaison des dates devrait être < 0 mais en l'ocurrance on a : "+result);
        result = date2.compareTo(date1);
        assertTrue( result > 0, "la comparaison des dates devrait être > 0 mais en l'ocurrance on a : "+result);

        date1 = new Date(30,11,0);
        date2 = new Date(30,11,2004);
        result = date1.compareTo(date2);
        assertTrue( result < 0, "la comparaison des dates devrait être < 0 mais en l'ocurrance on a : "+result);
        result = date2.compareTo(date1);
        assertTrue( result > 0, "la comparaison des dates devrait être > 0 mais en l'ocurrance on a : "+result);

        date1 = new Date(30,11,-1);
        date2 = new Date(30,11,1);
        result = date1.compareTo(date2);
        assertTrue( result < 0, "la comparaison des dates devrait être < 0 mais en l'ocurrance on a : "+result);
        result = date2.compareTo(date1);
        assertTrue( result > 0, "la comparaison des dates devrait être > 0 mais en l'ocurrance on a : "+result);

        date1 = new Date(30,11,-2004);
        date2 = new Date(30,11,-2003);
        result = date1.compareTo(date2);
        assertTrue( result < 0, "la comparaison des dates devrait être < 0 mais en l'ocurrance on a : "+result);
        result = date2.compareTo(date1);
        assertTrue( result > 0, "la comparaison des dates devrait être > 0 mais en l'ocurrance on a : "+result);

    }
    @Test
    public void approxToDaysTest()
    {
        int day = 1;
        int month = 1;
        int year = 1;
        Date date = new Date(day,month,year);
        int result = date.approxToDays();
        int expected = day + 31 * (month + 12 * year);
        assertEquals(result, expected, "Le resultat devrait être "+expected+" mais on a actuellement : "+result);
    }
    @Test
    public void equalsTest()
    {
        // Partition
        // year : same / different
        // month : same / different
        // day : same / different

        //BCC
        // year : same
        // month : same
        // day : same

        Date date1 = new Date(30,11,10);
        Date date2 = new Date(30,11,10);
        boolean result = date1.equals(date2);
        assertTrue(result, "Les deux dates devrait être identique mais ne le sont pas");

        date1 = new Date(30,11,9);
        date2 = new Date(30,11,10);
        result = date1.equals(date2);
        assertFalse(result, "Les deux dates devraient être différentes mais elles sont identiques.");

        date1 = new Date(30,10,10);
        date2 = new Date(30,11,10);
        result = date1.equals(date2);
        assertFalse(result, "Les deux dates devraient être différentes mais elles sont identiques.");

        date1 = new Date(29,11,10);
        date2 = new Date(30,11,10);
        result = date1.equals(date2);
        assertFalse(result, "Les deux dates devraient être différentes mais elles sont identiques.");



    }


}