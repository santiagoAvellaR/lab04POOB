package domain; 
import java.lang.String.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project
 * @author POOB  
 * @version ECI 2022
 */

public class Project{
    private HashMap<String,Activity> activities;
    private int simpleActivities;
    private int composedActivities;
    /**
     * Create a Project
     */
    public Project() throws ProjectException{
        activities = new HashMap<String,Activity>();
        simpleActivities = 0;
        composedActivities = 0;
        addSome();
    }

    private void addSome() throws ProjectException {
        String [][] activities= {{"Buscar datos","50","50", "" },
                                 {"Evaluar datos","80","80",""},
                                 {"Limpiar datos","100","100",""},
                                 {"Preparar datos", "50", "Secuencial", "Buscar datos\nEvaluar datos\nLimpiar datos"}};
        for (String [] c: activities){
            add(c[0],c[1],c[2],c[3]);
        }
    }

    /**
     * Consult a activity
     * @param name
     * @return 
     */
    public Activity consult(String name){
        return activities.get(name.toUpperCase());
    }

    /**
     * Add a new activity
     * @param name 
     * @param time
     * @param type
    */
    public void add(String name, String cost, String timeType, String theActivities) throws ProjectException{ 
        Activity activity;
        
        if (theActivities.equals("")){
           if((!(cost.matches("[^0-9]+")) && !cost.equals("")) || (!(timeType.matches("[^0-9]+")) && !timeType.equals("")))
           {
               throw new ProjectException(ProjectException.COST_AND_TIME_ARE_NOT_NUMBERS);
           }
           activity = new Simple(name, cost.equals("") ? null : Integer.parseInt(cost), timeType.equals("") ? null : Integer.parseInt(timeType));
           simpleActivities += 1;
        }
        else{
            // si empieza está vacío, por defecto es paralela. Si no, verifica que el primer caracter es 'P' para asignarle el resultado de la comparacion
            if(timeType.toUpperCase() != "PARALELA" || timeType.toUpperCase() != "SECUENCIAL")
            {
                throw new ProjectException(ProjectException.INVALID_TYPE);
            }
            activity = new Composed(name,cost.equals("") ? null : Integer.parseInt(cost), timeType.equals("") ? true : timeType.toUpperCase().charAt(0)=='P');
            composedActivities += 1; 
            String [] aSimples= theActivities.split("\n");
            for (String b : aSimples){
                ((Composed)activity).add(activities.get(b.toUpperCase()));
            }
        }
        if(activities.containsKey(name.toUpperCase())){
            throw new ProjectException(ProjectException.NAME_ALREADY_USED);
        }
        activities.put(name.toUpperCase(), activity);
    }
    
    /**
     * Consults the activities that start with a prefix
     * @param  
     * @return 
     */
    
    public LinkedList<Activity> select(String prefix){
        LinkedList <Activity> answers = null;
        prefix=prefix.toUpperCase();
        for(int i=0;i<activities.size();i++){
            if(activities.get(i).name().toUpperCase().startsWith(prefix.toUpperCase())){
                answers.add(activities.get(i));
            }   
        }
        return answers;
    }

    /**
     * Consult selected activities
     * @param selected
     * @return  
     */
    public String data(LinkedList<Activity> selected){
        StringBuffer answer = new StringBuffer();
        answer.append(activities.size() + " actividades\n");
        for(Activity activity : selected) {
            try{
                answer.append('>' + activity.data());
                answer.append("\n");
            }catch(ProjectException e){
                answer.append("** "+e.getMessage());
            }
        }    
        return answer.toString();
    }
    
     /**
     * Return the data of activities with a prefix
     * @param prefix
     * @return  
     */ 
    public String search(String prefix){
        return data(select(prefix));
    }
    
    /**
     * Return the data of all activities
     * @return  
     */    
    public String toString(){
        return data(new LinkedList<>(activities.values()));
    }
    
    /**
     * Consult the number of activities
     * @return 
     */
    public int numberActivities(){
        return activities.size();
    }
    
    /**
     * Consult the number of activities
     * @return 
     */
    public int numberComposedActivities(){
        return composedActivities;
    }
    
    /**
     * Consult the number of activities
     * @return 
     */
    public int numberSimpleActivities(){
        return simpleActivities;
    }
    
    public LinkedList<Activity> linkedListOfActivitiesValues(){
        return (new LinkedList<>(activities.values()));
    }
}