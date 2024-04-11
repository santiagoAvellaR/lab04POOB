package domain;  
 
import java.util.ArrayList;

public class Composed extends Activity{
    private boolean parallel;
    private ArrayList<Activity> activities;
    
    /**
     * Constructs a new composed activity
     * @param name 
     * @param cost
     * @param parallel
     */
    public Composed(String name, Integer cost, boolean parallel){
        super(name,cost);
        this.parallel=parallel;
        activities= new ArrayList<Activity>();
    }

     /**
     * Add a new activity
     * @param a the activity you want to add
     */   
    public void add(Activity a){
        activities.add(a);
    }
 
    @Override
    public int cost(){
        return cost;
    }
    
    private int getTheMaximunTimeInActivities() throws ProjectException{
        int max = 0;
        for (Activity activity : activities){
            if (activity.time() > max){
                max = activity.time();
            }
        }
        return max;
    }
    
    private int getTheTimeSumOfActivities() throws ProjectException{
        int sum = 0;
        for (Activity activity : activities){
            sum += activity.time();
        }
        return sum;
    }
    
    /**
     * @return time
     * @throws ProjectException, if the time is out of range or time is null or composed is empty
     */
    @Override
    public int time() throws ProjectException{
        if(activities.size() <= 0){throw new ProjectException(ProjectException.COMPOSED_EMPTY);}
        int totalTime = 0;
        if(parallel){
            totalTime = getTheMaximunTimeInActivities();
        }
        else{
            totalTime = getTheTimeSumOfActivities();
        }
        return totalTime;
    }
    
    private int getTheMaximunTimeInActivitiesHandleException(int dUnknow, int dError, int dEmpty){
        int max = 0;
        for (Activity activity : activities){
            try{
                if (activity.time() > max){
                    max = activity.time();
                }
            }
            catch(ProjectException e){
                if(e.getMessage().equals(ProjectException.TIME_EMPTY)){
                    max = dUnknow>max?dUnknow:max;
                }
                else if(e.getMessage().equals(ProjectException.TIME_ERROR)){
                    max = dError>max?dError:max;
                }
                else if(e.getMessage().equals(ProjectException.COMPOSED_EMPTY)){
                    max = dEmpty>max?dEmpty:max;
                }
            }
        }
        return max;
    }
    
    private int getTheTimeSumOfActivitiesHandleException(int dUnknow, int dError, int dEmpty){
        int sum = 0;
        for (Activity activity : activities){
            try{
                sum += activity.time();
            }
            catch(ProjectException e){
                if(e.getMessage().equals(ProjectException.TIME_EMPTY)){
                    sum += dUnknow;
                }
                else if(e.getMessage().equals(ProjectException.TIME_ERROR)){
                    sum += dError;
                }
                else if(e.getMessage().equals(ProjectException.COMPOSED_EMPTY)){
                    sum += dEmpty;
                }
            }
        }
        return sum;
    }
    
     /**
     * Calculates an estimated price using default values when necessary
     * @param dUnknown
     * @param dError
     * @param dEmpty
     * @return 
     */
    public int time(int dUnknow, int dError, int dEmpty){
        int totalTime = 0;
        if(parallel){
            totalTime = getTheMaximunTimeInActivitiesHandleException(dUnknow, dError, dEmpty);
        }
        else{
            totalTime = getTheTimeSumOfActivitiesHandleException(dUnknow, dError, dEmpty);
        }
        return totalTime;
    }
    
    /**
     * Calculate an estimated price considering the modality, if is possible.
     * @param modality ['A'(verage), 'M' (ax)] Use the average or maximum time of known activities to estimate unknown ones or those with error.
     * @return 
     * @throws ProjectException  IMPOSSIBLE, if it can't be calculated
    */
    public int time(char modality){
        return 0;
    } 
    
    /**
     * 
     * Calculates an time of a subactivity
     * @return the duration of the subactivity
     * @throws ProjectException UNKNOWN, if it doesn't exist. IMPOSSIBLE, if it can't be calculated
     */
    public int time(String activityName) throws ProjectException{
        Activity activity = findActivity(activityName);
        if(activity == null){throw new ProjectException(ProjectException.UNKNOWN);}
        try{
            int time = activity.time();
            return time;
        }
        catch(ProjectException e){
            throw new ProjectException(ProjectException.IMPOSSIBLE);
        }
    }
    
    /**
     * Find the activity given the name of an activity
     * @param the name of the activity
     * @return the activity that matchs with the name
     */
    private Activity findActivity(String activityName){
        Activity activity = null;
        for(Activity act : activities){
            if(act.name().equals(activityName)){
                activity = act;
            }
        }
        return activity;
    }
    
    @Override
    public String data() throws ProjectException{
        StringBuffer answer=new StringBuffer();
        answer.append(name+". Tipo "+ (parallel ? "Paralela": "Secuencial")+". ");
        for(Activity b: activities) {
            answer.append("\n\t"+b.data());
        }
        return answer.toString();
    }
}
