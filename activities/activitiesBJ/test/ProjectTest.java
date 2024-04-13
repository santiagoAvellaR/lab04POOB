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
        project = new Project();
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
        int defaultActivities = project.numberActivities();
        int defaultSimpleActivities = project.numberSimpleActivities();
        int defaultComposedActivities = project.numberComposedActivities();
        project.add("Desayunar","10","10", "" );
        assertEquals(defaultActivities + 1, 5);
        assertEquals(defaultSimpleActivities + 1, 4);
        assertEquals(defaultComposedActivities, 1);
    }
    @Test
    public void shouldAddAComposedActivity(){
        int defaultActivities = project.numberActivities();
        int defaultSimpleActivities = project.numberSimpleActivities();
        int defaultComposedActivities = project.numberComposedActivities();
        project.add("Ir a la universidad", "80", "Secuencial", "Buscar datos");
        assertEquals(defaultActivities + 1, 5);
        assertEquals(defaultSimpleActivities, 3);
        assertEquals(defaultComposedActivities + 1, 2);
    }
    @Test
    public void shouldAddAComposedAndASimpleActivity(){
        int defaultActivities = project.numberActivities();
        int defaultSimpleActivities = project.numberSimpleActivities();
        int defaultComposedActivities = project.numberComposedActivities();
        project.add("Desayunar","10","10", "" );
        project.add("Ir a la universidad", "80", "Secuencial", "Buscar datos");
        assertEquals(defaultActivities + 2, 6);
        assertEquals(defaultSimpleActivities + 1, 4);
        assertEquals(defaultComposedActivities + 1, 2);
    }
    @Test
    public void shouldAddSomeComposedAndSomeSimpleActivities(){
        int defaultActivities = project.numberActivities();
        int defaultSimpleActivities = project.numberSimpleActivities();
        int defaultComposedActivities = project.numberComposedActivities();
        project.add("Desayunar","10","10", "" );
        project.add("Bañarse","10","10", "" );
        project.add("Cambiarse","10","10", "" );
        project.add("Alistarse para salir", "80", "Secuencial", "Desayunar\nBañarse\nCambiarse");
        project.add("Tomar el SITP","10","10", "" );
        project.add("Tomar el TSM","10","10", "" );
        project.add("Bajarse en jardines","10","10", "" );
        project.add("Ir a la universidad", "80", "Secuencial", "Tomar el TSM\nTomar el TSM\nBajarse en jardines\nIr a la universidad");
        assertEquals(defaultActivities + 8, 12);
        assertEquals(defaultSimpleActivities + 6, 9);
        assertEquals(defaultComposedActivities + 2, 3);
    }
    @Test
    public void shouldAddAComposedInsideTwoComposed(){
        int defaultActivities = project.numberActivities();
        int defaultSimpleActivities = project.numberSimpleActivities();
        int defaultComposedActivities = project.numberComposedActivities();
        project.add("Desayunar","10","10", "" );
        project.add("Bañarse","10","10", "" );
        project.add("Cambiarse","10","10", "" );
        project.add("Alistarse para salir", "80", "Secuencial", "Desayunar\nBañarse\nCambiarse");
        project.add("Tomar el SITP","10","10", "" );
        project.add("Tomar el TSM","10","10", "" );
        project.add("Bajarse en jardines","10","10", "" );
        project.add("Ir a la universidad", "80", "Secuencial", "Tomar el TSM\nTomar el TSM\nBajarse en jardines\nIr a la universidad");
        project.add("Hacer mi jornada", "80", "Secuencial", "Alistarse para salir\nIr a la universidad");
        assertEquals(defaultActivities + 9, 13);
        assertEquals(defaultSimpleActivities + 6, 9);
        assertEquals(defaultComposedActivities + 3, 4);
    }
    
    //-------- TEST data(LinkedList<Activity> selected) -----------------------
    @Test
    public void shouldReturnTheDataOfTheDefaultActivities(){
        assertEquals("4 actividades\n>Buscar datos. Costo:50.Tiempo:50\n>Limpiar datos. Costo:100.Tiempo:100\n>Evaluar datos. Costo:80.Tiempo:80\n>Preparar datos. Tipo Secuencial.\n\tBuscar datos. Costo:50.Tiempo:50\n\tEvaluar datos. Costo:80.Tiempo:80\n\tLimpiar datos. Costo:100.Tiempo:100\n",
        project.data(project.linkedListOfActivitiesValues()).toString());
    }
    @Test
    public void shouldReturnTheDataOfASimpleActivity(){
        project.add("Desayunar","10","10", "" );
        assertEquals("5 actividades\n>Buscar datos. Costo:50.Tiempo:50\n>Limpiar datos. Costo:100.Tiempo:100\n>Desayunar. Costo:10.Tiempo:10\n>Evaluar datos. Costo:80.Tiempo:80\n>Preparar datos. Tipo Secuencial.\n\tBuscar datos. Costo:50.Tiempo:50\n\tEvaluar datos. Costo:80.Tiempo:80\n\tLimpiar datos. Costo:100.Tiempo:100\n",
        project.data(project.linkedListOfActivitiesValues()).toString());
    }
    @Test
    public void shouldReturnTheDataOfAComposedActivity(){
        project.add("Ir a la universidad", "80", "Secuencial", "Buscar datos");
        assertEquals("5 actividades\n>Ir a la universidad. Tipo Secuencial.\n\tBuscar datos. Costo:50.Tiempo:50\n>Buscar datos. Costo:50.Tiempo:50\n>Limpiar datos. Costo:100.Tiempo:100\n>Evaluar datos. Costo:80.Tiempo:80\n>Preparar datos. Tipo Secuencial.\n\tBuscar datos. Costo:50.Tiempo:50\n\tEvaluar datos. Costo:80.Tiempo:80\n\tLimpiar datos. Costo:100.Tiempo:100\n",
        project.data(project.linkedListOfActivitiesValues()).toString());
    }
}
