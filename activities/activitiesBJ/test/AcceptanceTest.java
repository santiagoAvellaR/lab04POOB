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
public class AcceptanceTest
{
    private Project project;
    private int defaultActivities;
    private int defaultSimpleActivities;
    private int defaultComposedActivities;
    /**
     * Default constructor for test class ProjectTest
     */
    public AcceptanceTest()
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
    
    // ------------- Adicionar y listar. Todo OK. 
    // ------------- 6. Propongan y ejecuten una prueba de aceptación
    @Test
    public void AcceptanceTestNumber1(){
        try{
            // el usuario añade una actividad simple
            project.add("Desayunar","10","10", "" );
            assertEquals(4, defaultSimpleActivities + 1);
            // el usuario añade una actividad simple
            project.add("Bañarse","10","10", "" );
            assertEquals(5, defaultSimpleActivities + 2);
            // el usuario añade una actividad simple
            project.add("Cambiarse","10","10", "" );
            assertEquals(6, defaultSimpleActivities + 3);
            // el usuario considera que las 3 primeras actividades que ingresó hacen parte de una actividad más grande llamada "Alistarse para salir"
            // por lo que añade una actividad compuesta, secuencial, con las 3 actividades de antes
            project.add("Alistarse para salir", "80", "Secuencial", "Desayunar\nBañarse\nCambiarse");
            assertEquals(2, defaultComposedActivities + 1);
            // el usuario añade una actividad simple
            project.add("Tomar el SITP","10","10", "" );
            assertEquals(7, defaultSimpleActivities + 4);
            // el usuario añade una actividad simple
            project.add("Bajarse en jardines","10","10", "" );
            assertEquals(8, defaultSimpleActivities + 5);
            // el usuario considera que las 2 anteriores actividades que ingresó hacen parte de una actividad más grande llamada "Ir a la universidad"
            // por lo que añade una actividad compuesta, secuencial, con las 2 ultimas actividades
            project.add("Ir a la universidad", "80", "Secuencial", "Tomar el SITP\nBajarse en jardines");
            assertEquals(3, defaultComposedActivities + 2);
            // el usuario quiere ver cómo quedaron organizadas sus tareas diarias, por lo que las lista
            assertEquals("11 actividades\n>Cambiarse. Costo:10.Tiempo:10\n>Ir a la universidad. Tipo Secuencial.\n\tTomar el SITP. Costo:10.Tiempo:10\n\tBajarse en jardines. Costo:10.Tiempo:10\n>Bañarse. Costo:10.Tiempo:10\n>Tomar el SITP. Costo:10.Tiempo:10\n>Buscar datos. Costo:50.Tiempo:50\n>Limpiar datos. Costo:100.Tiempo:100\n>Desayunar. Costo:10.Tiempo:10\n>Alistarse para salir. Tipo Secuencial.\n\tDesayunar. Costo:10.Tiempo:10\n\tBañarse. Costo:10.Tiempo:10\n\tCambiarse. Costo:10.Tiempo:10\n>Evaluar datos. Costo:80.Tiempo:80\n>Bajarse en jardines. Costo:10.Tiempo:10\n>Preparar datos. Tipo Secuencial.\n\tBuscar datos. Costo:50.Tiempo:50\n\tEvaluar datos. Costo:80.Tiempo:80\n\tLimpiar datos. Costo:100.Tiempo:100\n",
            project.data(project.linkedListOfActivitiesValues()).toString());
        }
        catch(ProjectException e){fail("Threw a exception");}
        // LA IMAGEN DE COMO RESPONDE EL GUI SE ENCUENTRA EN EL .doc
    }
    
    // ------------- Adicionar una actividad.  Funcionalidad robusto
    // CASO #1: ¿Y si el nombre de la actividad ya existe?
    // ------------- 1. Propongan una prueba de aceptación que genere el fallo. 
    @Test
    public void AcceptanceTest2TheNameAlReadyExists(){
        try{
            // el usuario añade una actividad simple
            project.add("Comer","10","10", "" );
            assertEquals(4, defaultSimpleActivities + 1);
            // el usuario añade una actividad simple
            project.add("Bañarse","10","10", "" );
            assertEquals(5, defaultSimpleActivities + 2);
            // el usuario añade una actividad simple
            project.add("Cambiarse","10","10", "" );
            assertEquals(6, defaultSimpleActivities + 3);
            // el usuario considera que las 3 primeras actividades que ingresó hacen parte de una actividad más grande llamada "Alistarse para salir"
            // por lo que añade una actividad compuesta, secuencial, con las 3 actividades de antes
            project.add("Alistarse para salir", "80", "Secuencial", "Comer\nBañarse\nCambiarse");
            // durante la tarde, suele comer, por lo que vuelve a agregar la actividad comer
            project.add("Comer","10","10", "" );
            fail("Did not throw exception");
        }
        catch(ProjectException e){
            assertEquals(ProjectException.NAME_ALREADY_USED,e.getMessage());
        }
    }
    
    // ------------- Adicionar una actividad.  Funcionalidad robusto
    // CASO #2: ¿Y si en precio o costo no da un número? ¿si el tipo no es paralelo o secuancial?
    // ------------- 1. Propongan una prueba de aceptación que genere el fallo.
    @Test
    public void aceptationTestNumber3(){
    }
    
    // ------------- Adicionar una actividad.  Funcionalidad robusto
    // CASO #3: ¿Y si trata de añadir como una subactividad a una actividad que aún no ha creado/añadido?
    // ------------- 1. Propongan una prueba de aceptación que genere el fallo. 
    @Test
    public void aceptationTestNumber4(){
    }
}
