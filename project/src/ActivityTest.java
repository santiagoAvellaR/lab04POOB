package test;
import domain.*;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ActivityTest{
    public ActivityTest(){}

    @Before
    public void setUp(){}

    @After
    public void tearDown(){}
    
    /*-------- TEST METHOD ------ time()*/
    /*LEVEL 2*/
    @Test
    public void shouldCalculateTheTimeOfAComposedSecuencialActivityLevel2(){
        Composed c = new Composed("IS-BASICA", 100 , false );
        c.add(new Simple("AYED", 10, 10));
        c.add(new Simple("MBDA", 10, 20));
        c.add(new Simple("POOB", 10, 15));
        try {
           assertEquals(45,c.time());
        } catch (ProjectException e){
            fail("Threw a exception");
        }    
    }    
    @Test
    public void shouldCalculateTheTimeOfAComposedParallelActivityLevel2(){
        Composed c = new Composed("IS-BASICA", 100 , true );
        c.add(new Simple("AYED", 10, 10));
        c.add(new Simple("MBDA", 10, 20));
        c.add(new Simple("POOB", 10, 15));
        try {
           assertEquals(20,c.time());
        } catch (ProjectException e){
            fail("Threw a exception");
        }    
    }
    @Test
    public void shouldThrowExceptionIfComposedIsEmptyLevel2(){
        Composed c = new Composed("IS-BASICA", 100 , true);
        try { 
           int time=c.time();
           fail("Did not throw exception");
        } catch (ProjectException e) {
            assertEquals(ProjectException.COMPOSED_EMPTY,e.getMessage());
        }    
    }    
    @Test
    public void shouldThrowExceptionIfThereIsErrorInTimeLevel2(){
        Composed c = new Composed("IS-BASICA", 100 , false );
        c.add(new Simple("AYED", 10, 10));
        c.add(new Simple("MBDA", 10, -20));
        c.add(new Simple("POOB", 10, 30));
        try { 
           int time=c.time();
           fail("Did not throw exception");
        } catch (ProjectException e) {
            assertEquals(ProjectException.TIME_ERROR,e.getMessage());
        }    
    }     
    @Test
    public void shouldThrowExceptionIfTimeIsNotKnownLevel2(){
        Composed c = new Composed("IS-BASICA", 100 , true );
        c.add(new Simple("AYED", 10, 10));
        c.add(new Simple("MBDA", 10, null));
        c.add(new Simple("POOB", 10, 30));
        try { 
           int time=c.time();
           fail("Did not throw exception");
        } catch (ProjectException e) {
            assertEquals(ProjectException.TIME_EMPTY,e.getMessage());
        }    
    }
    /*LEVEL 3*/
    @Test
    public void shouldCalculateTheTimeOfAComposedSecuencialActivityLevel3(){
        Composed c = new Composed("IS PREGADO", 100 , false );
        Composed c1 = new Composed("IS-AVANZADA", 100 , false );
        Composed c2 = new Composed("IS-MEDIA", 100 , false );
        Composed c3 = new Composed("IS-BASICA", 100 , false );
        c.add(c1);
        c.add(c2);
        c.add(c3);
        c1.add(new Simple("AYPR", 10, 5));
        c1.add(new Simple("MMIN", 10, 5));
        c1.add(new Simple("LCAT", 10, 5));
        c2.add(new Simple("AYED", 10, 10));
        c2.add(new Simple("MBDA", 10, 10));
        c2.add(new Simple("POOB", 10, 10));
        c3.add(new Simple("CVDS", 10, 20));
        c3.add(new Simple("ARSW", 10, 20));
        try {
           assertEquals(85,c.time());
        } catch (ProjectException e){
            fail("Threw a exception");
        }
    }    
    @Test
    public void shouldCalculateTheTimeOfAComposedParallelActivityLevel3(){
        Composed c = new Composed("IS PREGADO", 100 , true );
        Composed c1 = new Composed("IS-AVANZADA", 100 , true );
        Composed c2 = new Composed("IS-MEDIA", 100 , true );
        Composed c3 = new Composed("IS-BASICA", 100 , true );
        c.add(c1);
        c.add(c2);
        c.add(c3);
        c1.add(new Simple("AYPR", 10, 10));
        c1.add(new Simple("MMIN", 10, 10));
        c1.add(new Simple("LCAT", 10, 10));
        c2.add(new Simple("AYED", 10, 10));
        c2.add(new Simple("MBDA", 10, 20));
        c2.add(new Simple("POOB", 10, 15));
        c3.add(new Simple("CVDS", 10, 10));
        c3.add(new Simple("ARSW", 10, 10));
        try {
           assertEquals(20,c.time());
        } catch (ProjectException e){
            fail("Threw a exception");
        }    
    }  
    @Test
    public void shouldThrowExceptionIfComposedIsEmptyLevel3(){
        Composed c = new Composed("IS PREGADO", 100 , false );
        Composed c1 = new Composed("IS-AVANZADA", 100 , false );
        Composed c2 = new Composed("IS-MEDIA", 100 , false );
        Composed c3 = new Composed("IS-BASICA", 100 , false );
        try { 
           int time=c.time();
           fail("Did not throw exception");
        } catch (ProjectException e) {
            assertEquals(ProjectException.COMPOSED_EMPTY,e.getMessage());
        }    
    }    
    @Test
    public void shouldThrowExceptionIfThereIsErrorInTimeLevel3(){
        Composed c = new Composed("IS PREGADO", 100 , false );
        Composed c1 = new Composed("IS-AVANZADA", 100 , false );
        Composed c2 = new Composed("IS-MEDIA", 100 , false );
        Composed c3 = new Composed("IS-BASICA", 100 , false );
        c.add(c1);
        c.add(c2);
        c.add(c3);
        c1.add(new Simple("AYPR", 10, 10));
        c1.add(new Simple("MMIN", 10, 5));
        c1.add(new Simple("LCAT", 10, 10));
        c2.add(new Simple("AYED", 10, 7));
        c2.add(new Simple("MBDA", 10, 9));
        c2.add(new Simple("POOB", 10, 15));
        c3.add(new Simple("CVDS", 10, 10));
        c3.add(new Simple("ARSW", 10, -10));
        try { 
           int time=c.time();
           fail("Did not throw exception");
        } catch (ProjectException e) {
            assertEquals(ProjectException.TIME_ERROR,e.getMessage());
        }    
    }
    @Test
    public void shouldThrowExceptionIfTimeIsNotKnownLevel3(){
        Composed c = new Composed("IS PREGADO", 100 , false );
        Composed c1 = new Composed("IS-AVANZADA", 100 , false );
        Composed c2 = new Composed("IS-MEDIA", 100 , false );
        Composed c3 = new Composed("IS-BASICA", 100 , false );
        c.add(c1);
        c.add(c2);
        c.add(c3);
        c1.add(new Simple("AYPR", 10, 10));
        c1.add(new Simple("MMIN", 10, 10));
        c1.add(new Simple("LCAT", 10, 10));
        c2.add(new Simple("AYED", 10, 10));
        c2.add(new Simple("MBDA", 10, 20));
        c2.add(new Simple("POOB", 10, 15));
        c3.add(new Simple("CVDS", 10, 10));
        c3.add(new Simple("ARSW", 10, null));
        try { 
           int time=c.time();
           fail("Did not throw exception");
        } catch (ProjectException e) {
            assertEquals(ProjectException.TIME_EMPTY,e.getMessage());
        }    
    }
    
    /*-------- TEST METHOD ------ time(int dUnknow, int dError, int dEmpty)*/
    /*LEVEL 1*/
    @Test
    public void shouldCalculateTheTimeOfAComposedSecuencialLevel1(){
        Composed c = new Composed("IS-BASICA", 100 , false );
        assertEquals(15,c.time(5, 10, 15));
    }
    /*LEVEL 2*/
    @Test
    public void shouldCalculateTheTimeOfAComposedSecuencialLevel21(){
        Composed c = new Composed("IS-BASICA", 100 , false );
        c.add(new Simple("AYED", 10, null));
        c.add(new Simple("MBDA", 10, -15));
        c.add(new Simple("POOB", 10, 10));
        assertEquals(25,c.time(5, 10, 15));
    }
    /*LEVEL 3*/
    @Test
    public void shouldCalculateTheTimeOfAComposedSecuencialLevel31(){
        Composed c = new Composed("IS PREGADO", 100 , false );
        Composed c1 = new Composed("IS-AVANZADA", 100 , false );
        Composed c2 = new Composed("IS-MEDIA", 100 , false );
        Composed c3 = new Composed("IS-BASICA", 100 , false );
        c.add(c1);
        c.add(c2);
        c.add(c3);
        assertEquals(45,c.time(5, 10, 15));
    }
    @Test
    public void shouldCalculateTheTimeOfAComposedSecuencialLevel32(){
        Composed c = new Composed("IS PREGADO", 100 , false );
        Composed c1 = new Composed("IS-AVANZADA", 100 , false );
        Composed c2 = new Composed("IS-MEDIA", 100 , false );
        Composed c3 = new Composed("IS-BASICA", 100 , false );//15
        c.add(c1);
        c.add(c2);
        c.add(c3);
        c1.add(new Simple("AYPR", 10, null));//5
        c1.add(new Simple("MMIN", 10, 10));
        c1.add(new Simple("LCAT", 10, 10));
        c2.add(new Simple("AYED", 10, -5));//10
        c2.add(new Simple("MBDA", 10, 20));
        c2.add(new Simple("POOB", 10, 15));
        assertEquals(5+10+10+10+20+15+15,c.time(5, 10, 15));
    }
    
    /*TEST time(String activityName)*/
    @Test
    public void shouldReturnTheTimeOfASubActivity(){
        Composed c = new Composed("IS PREGADO", 100 , false );
        Composed c1 = new Composed("IS-AVANZADA", 100 , false );
        Composed c2 = new Composed("IS-MEDIA", 100 , false );
        Composed c3 = new Composed("IS-BASICA", 100 , false );
        c.add(c1);
        c.add(c2);
        c.add(c3);
        c1.add(new Simple("AYPR", 10, 10));
        c1.add(new Simple("MMIN", 10, 10));
        c1.add(new Simple("LCAT", 10, 10));
        c2.add(new Simple("AYED", 10, 10));
        c2.add(new Simple("MBDA", 10, 20));
        c2.add(new Simple("POOB", 10, 15));
        c3.add(new Simple("CVDS", 10, 10));
        c3.add(new Simple("ARSW", 10, 10));
        try { 
           int time=c.time("IS-AVANZADA");
           assertEquals(time, 30);
        } catch (ProjectException e) {
            fail("Did not throw exception");
        }    
    }
    @Test
    public void shouldReturnTheTimeOfASubActivityLevel3(){
        Composed c = new Composed("IS PREGADO", 100 , false );
        Composed c1 = new Composed("IS-AVANZADA", 100 , false );
        Composed c2 = new Composed("IS-MEDIA", 100 , false );
        Composed c3 = new Composed("IS-BASICA", 100 , false );
        c.add(c1);
        c.add(c2);
        c.add(c3);
        c1.add(new Simple("AYPR", 10, 10));
        c1.add(new Simple("MMIN", 10, 10));
        c1.add(new Simple("LCAT", 10, 10));
        c2.add(new Simple("AYED", 10, 10));
        c2.add(new Simple("MBDA", 10, 20));
        c2.add(new Simple("POOB", 10, 15));
        c3.add(new Simple("CVDS", 10, 10));
        c3.add(new Simple("ARSW", 10, 10));
        try { 
           int time=c.time("LCAT");
           assertEquals(time, 10);
        } catch (ProjectException e) {
            fail("Did not throw exception");
        }    
    }
    @Test
    public void shouldThrowUNKOWNException(){
        Composed c = new Composed("IS PREGADO", 100 , false );
        Composed c1 = new Composed("IS-AVANZADA", 100 , false );
        Composed c2 = new Composed("IS-MEDIA", 100 , false );
        Composed c3 = new Composed("IS-BASICA", 100 , false );
        c.add(c1);
        c.add(c2);
        c.add(c3);
        c1.add(new Simple("AYPR", 10, 10));
        c1.add(new Simple("MMIN", 10, 10));
        c1.add(new Simple("LCAT", 10, 10));
        c2.add(new Simple("AYED", 10, 10));
        c2.add(new Simple("MBDA", 10, 20));
        c2.add(new Simple("POOB", 10, 15));
        c3.add(new Simple("CVDS", 10, 10));
        c3.add(new Simple("ARSW", 10, 10));
        try { 
           int time=c.time("ISIS");
           fail("Did not throw exception");
        } catch (ProjectException e) {
            assertEquals(e.getMessage(), ProjectException.UNKNOWN);
        }    
    }
    @Test
    public void shouldThrowIMPOSSIBLEExceptionComposedEmpty(){
        Composed c = new Composed("IS PREGADO", 100 , false );
        Composed c1 = new Composed("IS-AVANZADA", 100 , false );
        Composed c2 = new Composed("IS-MEDIA", 100 , false );
        Composed c3 = new Composed("IS-BASICA", 100 , false );
        c.add(c1);
        c.add(c2);
        c.add(c3);
        c1.add(new Simple("AYPR", 10, 10));
        c1.add(new Simple("MMIN", 10, 10));
        c1.add(new Simple("LCAT", 10, 10));
        c2.add(new Simple("AYED", 10, 10));
        c2.add(new Simple("MBDA", 10, 20));
        c2.add(new Simple("POOB", 10, 15));
        try { 
           int time=c.time("IS-BASICA");
           fail("Did not throw exception");
        } catch (ProjectException e) {
            assertEquals(e.getMessage(), ProjectException.IMPOSSIBLE);
        }    
    }
    @Test
    public void shouldThrowUNKOWNExceptionNullValues(){
        Composed c = new Composed("IS PREGADO", 100 , false );
        Composed c1 = new Composed("IS-AVANZADA", 100 , false );
        Composed c2 = new Composed("IS-MEDIA", 100 , false );
        Composed c3 = new Composed("IS-BASICA", 100 , false );
        c.add(c1);
        c.add(c2);
        c.add(c3);
        c1.add(new Simple("AYPR", 10, 10));
        c1.add(new Simple("MMIN", 10, 10));
        c1.add(new Simple("LCAT", 10, 10));
        c2.add(new Simple("AYED", 10, 10));
        c2.add(new Simple("MBDA", 10, 20));
        c2.add(new Simple("POOB", 10, 20));
        c3.add(new Simple("CVDS", 10, null));
        c3.add(new Simple("ARSW", 10, null));
        try { 
           int time=c.time("IS-BASICA");
           fail("Did not throw exception");
        } catch (ProjectException e) {
            assertEquals(e.getMessage(), ProjectException.IMPOSSIBLE);
        }    
    }
    @Test
    public void shouldThrowUNKOWNExceptionValuesOutOfRange(){
        Composed c = new Composed("IS PREGADO", 100 , false );
        Composed c1 = new Composed("IS-AVANZADA", 100 , false );
        Composed c2 = new Composed("IS-MEDIA", 100 , false );
        Composed c3 = new Composed("IS-BASICA", 100 , false );
        c.add(c1);
        c.add(c2);
        c.add(c3);
        c1.add(new Simple("AYPR", 10, 10));
        c1.add(new Simple("MMIN", 10, 10));
        c1.add(new Simple("LCAT", 10, 10));
        c2.add(new Simple("AYED", 10, 10));
        c2.add(new Simple("MBDA", 10, 20));
        c2.add(new Simple("POOB", 10, 20));
        c3.add(new Simple("CVDS", 10, 30));
        c3.add(new Simple("ARSW", 10, 50));
        try { 
           int time=c.time("IS-BASICA");
           fail("Did not throw exception");
        } catch (ProjectException e) {
            assertEquals(e.getMessage(), ProjectException.IMPOSSIBLE);
        }    
    }
    
    /*-------- TEST METHOD ------ time(char modality)*/
    @Test
    public void shouldModalityImpossible(){
        Composed c = new Composed("IS-BASICA", 100 , false );
        try { 
           int time=c.time('A');
           fail("Did not throw exception");
        } catch (ProjectException e) {
            assertEquals(ProjectException.IMPOSSIBLE,e.getMessage());
        }   
    }
    @Test
    public void shouldModalityImpossible2(){
        Composed c = new Composed("IS-BASICA", 100 , false );
        c.add(new Simple("AYPR", 10, -10));
        c.add(new Simple("ARSW", 10, null));
        try { 
           int time=c.time('M');
           fail("Did not throw exception");
        } catch (ProjectException e) {
            assertEquals(ProjectException.IMPOSSIBLE,e.getMessage());
        }   
    }
    @Test
    public void shouldModalityResult(){
        Composed c = new Composed("IS-BASICA", 100 , false );
        c.add(new Simple("LCAT", 10, -5));
        c.add(new Simple("AYED", 10, 10));
        try {
           assertEquals(10,c.time('A'));
        } catch (ProjectException e){
            fail("Threw a exception");
        }  
    }
}