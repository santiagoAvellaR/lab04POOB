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
    
    private int getTheMaximunTimeInActivities(int dUnknow, int dError, int dEmpty){
        int max = 0;
        for (Activity activity : activities){
            try{
                int activityTime = (activity instanceof Composed)?((Composed)activity).time(dUnknow, dError, dEmpty):activity.time(); 
                if (activityTime > max){
                    max = activityTime;
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
    
    private int getTheTimeSumOfActivities(int dUnknow, int dError, int dEmpty){
        int sum = 0;
        for (Activity activity : activities){
            try{
                sum += (activity instanceof Composed)?((Composed)activity).time(dUnknow, dError, dEmpty):activity.time(); 
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
     * @return the time of the activity
     */
    public int time(int dUnknow, int dError, int dEmpty){
        if(activities.size()>=1){
            if(parallel){
                return getTheMaximunTimeInActivities(dUnknow, dError, dEmpty);
            }
            return getTheTimeSumOfActivities(dUnknow, dError, dEmpty);
        }
        return dEmpty;
    }
    
    /**
     * Calculate an estimated price considering the modality, if is possible.
     * @param modality ['A'(verage), 'M' (ax)] Use the average or maximum time of known activities to estimate unknown ones or those with error.
     * @return 
     * @throws ProjectException  IMPOSSIBLE, if it can't be calculated
     */
    public int time(char modality) throws ProjectException{
        if(activities.size()==0){
            throw new ProjectException(ProjectException.IMPOSSIBLE);
        }
        if(modality == 'A'){
            return calculateTimeModalityA();
        }
        if(modality == 'M'){
            return calculateTimeModalityM();
        }
        return -500;
    }
    
    private int calculateTimeByType(int time1, int time2){
        if(parallel){return Math.max(time1, time2);}
        return time1 + time2;
    }
    
    private int calculateTimeModalityA() throws ProjectException{
        int totalTime = 0;
        int sumeTimeValidActivities = 0;
        int ValidActivities = 0;
        int unknownActivities = 0;
        for(Activity activity: activities){
            try{
               int activityTime = activity.time();
               totalTime = calculateTimeByType(totalTime, activityTime);
               sumeTimeValidActivities += activityTime;
               ValidActivities += 1;
            }catch(ProjectException e){
                unknownActivities += 1;
            }
        }
        if(ValidActivities == 0){throw new ProjectException(ProjectException.IMPOSSIBLE);}
        if(unknownActivities == 0){return totalTime;}
        int average = sumeTimeValidActivities/ValidActivities;
        if(parallel){totalTime = Math.max(totalTime, average);}
        else{totalTime += unknownActivities * average;}
        return totalTime;
    }
    
    private int calculateTimeModalityM() throws ProjectException{
        int totalTime = 0;
        int unknownActivities = 0;
        int maxValue = 0;
        for(Activity a: activities){
            try{
               int getTime = a.time();
               totalTime = calculateTimeByType(totalTime, getTime);
               maxValue = Math.max(maxValue, getTime);
            }catch(ProjectException e){
                unknownActivities += 1;              
            }
        }
        if(totalTime==0){throw new ProjectException(ProjectException.IMPOSSIBLE);}
        if(unknownActivities == 0){return totalTime;}
        if(!parallel){totalTime += maxValue * unknownActivities;}
        return totalTime;
    }
    
     /**
     * Calculates an time of a subactivity
     * @param activity name
     * @return time of the activity
     * @throws ProjectException UNKNOWN, if it doesn't exist. IMPOSSIBLE, if it can't be calculated
     */
    public int time(String activityName) throws ProjectException{
        for(Activity activity: activities){
            if(activityName.equals(activity.name)){
                try{
                    int time = activity.time();
                    return time;
                }
                catch(ProjectException e){
                    throw new ProjectException(ProjectException.IMPOSSIBLE);
                }
            }
            if(activity instanceof Composed){
                try{
                    Composed composed = (Composed) activity;
                    return composed.time(activityName);
                }
                catch(ProjectException e){
                    if(e.getMessage().equals(ProjectException.IMPOSSIBLE)){
                        throw new ProjectException(ProjectException.IMPOSSIBLE);
                    }
                }
            }
        }
        throw new ProjectException(ProjectException.UNKNOWN);
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
