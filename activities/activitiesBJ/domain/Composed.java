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
     * @param a
     */   
    public void add(Activity a){
        activities.add(a);
    }
       
 
    @Override
    public int cost(){
        return 0;
    }
    
    private int getTheMaximunIntInActivities() throws ProjectException{
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
        if(activities.size()<=0){
            throw new ProjectException(ProjectException.COMPOSED_EMPTY);
        }
        else{
            int totalTime = 0;
            if(parallel){
                totalTime = getTheMaximunIntInActivities();
            }
            else{
                totalTime = getTheTimeSumOfActivities();
            }
            return totalTime;
        } 
    }
    
    private int getTheMaximunIntInActivitiesHandleException(int dUnknow, int dError, int dEmpty){
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
            totalTime = getTheMaximunIntInActivitiesHandleException(dUnknow, dError, dEmpty);
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
     * /
    public int time(char modality){
        return 0;
    } 
    
     /**
     * Calculates an time of a subactivity
     * @return 
     * @throws ProjectException UNKNOWN, if it doesn't exist. IMPOSSIBLE, if it can't be calculated
     */
    public int time(String activity) throws ProjectException{
        return 0;
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
