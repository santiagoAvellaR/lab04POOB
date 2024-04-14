package test;

import domain.*;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 * The test class ProjectTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ProjectTest
{
    private Project project;
    private int defaultActivities;
    private int defaultSimpleActivities;
    private int defaultComposedActivities;
    /**
     * Default constructor for test class ProjectTest
     */
    public ProjectTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        try{
            project = new Project();
            defaultActivities = project.numberActivities();
            defaultSimpleActivities = project.numberSimpleActivities();
            defaultComposedActivities = project.numberComposedActivities();
        }
        catch(ProjectException e){fail("Threw a exception");}
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
    
    //-------- TEST add(String name, String cost, String timeType, String theActivities) -----------------------
    @Test
    public void shouldAddASimpleActivity(){
        try{
            project.add("Desayunar","10","10", "" );
            assertEquals(defaultActivities + 1, 5);
            assertEquals(defaultSimpleActivities + 1, 4);
            assertEquals(defaultComposedActivities, 1);
        }
        catch(ProjectException e){fail("Threw a exception");}
    }
    @Test
    public void shouldAddAComposedActivity(){
        try{
            project.add("Ir a la universidad", "80", "Secuencial", "Buscar datos");
            assertEquals(defaultActivities + 1, 5);
            assertEquals(defaultSimpleActivities, 3);
            assertEquals(defaultComposedActivities + 1, 2);
        }
        catch(ProjectException e){fail("Threw a exception");}
    }
    @Test
    public void shouldAddAComposedAndASimpleActivity(){
        try{
            project.add("Desayunar","10","10", "" );
            project.add("Ir a la universidad", "80", "Secuencial", "Buscar datos");
            assertEquals(defaultActivities + 2, 6);
            assertEquals(defaultSimpleActivities + 1, 4);
            assertEquals(defaultComposedActivities + 1, 2);
        }
        catch(ProjectException e){fail("Threw a exception");}
    }
    @Test
    public void shouldAddSomeComposedAndSomeSimpleActivities(){
        try{
            project.add("Desayunar","10","10", "" );
            project.add("Bañarse","10","10", "" );
            project.add("Cambiarse","10","10", "" );
            project.add("Alistarse para salir", "80", "Secuencial", "Desayunar\nBañarse\nCambiarse");
            project.add("Tomar el SITP","10","10", "" );
            project.add("Tomar el TSM","10","10", "" );
            project.add("Bajarse en jardines","10","10", "" );
            project.add("Ir a la universidad", "80", "Secuencial", "Tomar el SITP\nTomar el TSM\nBajarse en jardines");
            assertEquals(defaultActivities + 8, 12);
            assertEquals(defaultSimpleActivities + 6, 9);
            assertEquals(defaultComposedActivities + 2, 3);
        }
        catch(ProjectException e){fail("Threw a exception");}
    }
    @Test
    public void shouldAddAComposedInsideTwoComposed(){
        try{
            project.add("Desayunar","10","10", "" );
            project.add("Bañarse","10","10", "" );
            project.add("Cambiarse","10","10", "" );
            project.add("Alistarse para salir", "80", "Secuencial", "Desayunar\nBañarse\nCambiarse");
            project.add("Tomar el SITP","10","10", "" );
            project.add("Tomar el TSM","10","10", "" );
            project.add("Bajarse en jardines","10","10", "" );
            project.add("Ir a la universidad", "80", "Secuencial", "Tomar el SITP\nTomar el TSM\nBajarse en jardines");
            project.add("Hacer mi jornada", "80", "Secuencial", "Alistarse para salir\nIr a la universidad");
            assertEquals(defaultActivities + 9, 13);
            assertEquals(defaultSimpleActivities + 6, 9);
            assertEquals(defaultComposedActivities + 3, 4);
        }
        catch(ProjectException e){fail("Threw a exception");}
    }
    
    //-------- TEST data(LinkedList<Activity> selected) -----------------------
    @Test
    public void shouldReturnTheDataOfTheDefaultActivities(){
        assertEquals("4 actividades\n>Buscar datos. Costo:50.Tiempo:50\n>Limpiar datos. Costo:100.Tiempo:100\n>Evaluar datos. Costo:80.Tiempo:80\n>Preparar datos. Tipo Secuencial.\n\tBuscar datos. Costo:50.Tiempo:50\n\tEvaluar datos. Costo:80.Tiempo:80\n\tLimpiar datos. Costo:100.Tiempo:100\n",
        project.data(project.linkedListOfActivitiesValues()).toString());
    }
    @Test
    public void shouldReturnTheDataOfASimpleActivity(){
        try{
            project.add("Desayunar","10","10", "" );
            assertEquals("5 actividades\n>Buscar datos. Costo:50.Tiempo:50\n>Limpiar datos. Costo:100.Tiempo:100\n>Desayunar. Costo:10.Tiempo:10\n>Evaluar datos. Costo:80.Tiempo:80\n>Preparar datos. Tipo Secuencial.\n\tBuscar datos. Costo:50.Tiempo:50\n\tEvaluar datos. Costo:80.Tiempo:80\n\tLimpiar datos. Costo:100.Tiempo:100\n",
            project.data(project.linkedListOfActivitiesValues()).toString());
        }
        catch(ProjectException e){fail("Threw a exception");}
    }
    @Test
    public void shouldReturnTheDataOfAComposedActivity(){
        try{
            project.add("Ir a la universidad", "80", "Secuencial", "Buscar datos");
            assertEquals("5 actividades\n>Ir a la universidad. Tipo Secuencial.\n\tBuscar datos. Costo:50.Tiempo:50\n>Buscar datos. Costo:50.Tiempo:50\n>Limpiar datos. Costo:100.Tiempo:100\n>Evaluar datos. Costo:80.Tiempo:80\n>Preparar datos. Tipo Secuencial.\n\tBuscar datos. Costo:50.Tiempo:50\n\tEvaluar datos. Costo:80.Tiempo:80\n\tLimpiar datos. Costo:100.Tiempo:100\n",
            project.data(project.linkedListOfActivitiesValues()).toString());
        }
        catch(ProjectException e){fail("Threw a exception");}
    }
    
    //----- TEST NAME_ALREADY_USED ------------------
    @Test
    public void shouldExceptionNameAlreadyUsed(){
        try{
            project.add("Bañarse","10","10", "" );
            project.add("Cambiarse","10","10", "" );
            project.add("Desayunar","10","Secuencial", "Bañarse\nCambiarse" );    
            project.add("Desayunar","10","5", "" );
            fail("Threw a exception");
        }catch(ProjectException e){
             assertEquals(ProjectException.NAME_ALREADY_USED,e.getMessage());
        }
    }
    @Test
    public void shouldNotExceptionNameAlreadyUsed(){
        try{
            project.add("Bañarse","10","10", "" );
            project.add("Cambiarse","10","10", "" );
            project.add("Tomar el SITP","10","10", "" );
            project.add("salir","10","5", "" );    
        }catch(ProjectException e){
             fail("Threw a exception");
        }
    }
    
    //----- TEST THE_SUBACTIVITY_NOT_EXISTS ------------------
    @Test
    public void shouldThrowExceptionSubactivity(){
        try{
            project.add("Bañarse","10","10", "" );
            project.add("Cambiarse","10","10", "" );
            project.add("Desayunar","10","Secuencial", "Bañarse\nCambiarse\nTomar el SITP" );
            fail("Threw a exception");
        }catch(ProjectException e){
             assertEquals(ProjectException.THE_SUBACTIVITY_NOT_EXISTS,e.getMessage());
        }
    }
    @Test
    public void shouldNotThrowExceptionSubactivity(){
        try{
            project.add("Bañarse","10","10", "" );
            project.add("Cambiarse","10","10", "" );
            project.add("Tomar el SITP","10","10", "" );
            project.add("Desayunar","10","Secuencial", "Bañarse\nCambiarse\nTomar el SITP" );        
        }catch(ProjectException e){
             fail("Threw a exception");
        }
    }
    
    //----- TEST COST_AND_TIME_ARE_NOT_NUMBERS ------------------
    @Test
    public void shouldExceptionCostIsNotNumbers(){
        try{
            project.add("Bañarse","10","10", "" );
            project.add("Cambiarse","10","10", "" );
            project.add("Desayunar","10","Secuencial", "Bañarse\nCambiarse" );    
            project.add("Despedirse","hola","5", "" );
            fail("Did not throw exception");
        }catch(ProjectException e){
             assertEquals(ProjectException.COST_AND_TIME_ARE_NOT_NUMBERS,e.getMessage());
        }
    }
    @Test
    public void shouldExceptionTimeIsNotNumbers(){
        try{
            project.add("Bañarse","10","10", "" );
            project.add("Cambiarse","10","10", "" );
            project.add("Tomar el SITP","10","10", "" );
            project.add("salir","10","ADIOS", "" );
            fail("Did not throw exception");
        }catch(ProjectException e){
            assertEquals(ProjectException.COST_AND_TIME_ARE_NOT_NUMBERS,e.getMessage());
        }
    }
    
    //----- TEST INVALID_TYPE ------------------
    @Test
    public void shouldThrowInvalidType(){
        try{
            project.add("Desayunar","10","10", "" );
            project.add("Bañarse","10","10", "" );
            project.add("Cambiarse","10","10", "" );
            project.add("Alistarse para salir", "80", "Regular", "Desayunar\nBañarse\nCambiarse");
            fail("Did not throw exception");
        }
        catch(ProjectException e){
            assertEquals(ProjectException.INVALID_TYPE, e.getMessage());
        }
    }
    @Test
    public void shouldNotThrowInvalidType(){
        try{
            project.add("Desayunar","10","10", "" );
            project.add("Bañarse","10","10", "" );
            project.add("Cambiarse","10","10", "" );
            project.add("Alistarse para salir", "80", "Paralela", "Desayunar\nBañarse\nCambiarse");
        }
        catch(ProjectException e){
            fail("Threw a exception");
        }
    }
    @Test
    public void shouldNotThrowInvalidType2(){
        try{
            project.add("Desayunar","10","10", "" );
            project.add("Bañarse","10","10", "" );
            project.add("Cambiarse","10","10", "" );
            project.add("Alistarse para salir", "80", "Secuencial", "Desayunar\nBañarse\nCambiarse");
        }
        catch(ProjectException e){
            fail("Threw a exception");
        }
    }
}
