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
    
    private int getTheTimeSumOfActivities(int dUnknow, int dError, int dEmpty){
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
            totalTime = getTheMaximunTimeInActivities(dUnknow, dError, dEmpty);
        }
        else{
            totalTime = getTheTimeSumOfActivities(dUnknow, dError, dEmpty);
        }
        return totalTime;
    }
    
    /**
     * Calculate an estimated price considering the modality, if is possible.
     * @param modality ['A'(verage), 'M' (ax)] Use the average or maximum time of known activities to estimate unknown ones or those with error.
     * @return 
     * @throws ProjectException  IMPOSSIBLE, if it can't be calculated
     */
    public int time(char modality) throws ProjectException{
        if(activities.size()==0) throw new ProjectException(ProjectException.IMPOSSIBLE);
        if(modality=='A'){
            return calculateTimeModalityA();
        }
        if(modality=='M'){
            return calculateTimeModalityM();
        }
        return 0;
    }
    
    private int calculateTimeByType(int time, int otherTime){
        if(parallel){return Math.max(time, otherTime);}
        return time + otherTime;
    }
    
    private int calculateTimeModalityA() throws ProjectException{
        int time = 0;
        int sumValue = 0;
        int n = 0;
        int unknown = 0;
        for(Activity a: activities){
            try{
               int getTime = a.time();
               time = calculateTimeByType(time, getTime);
               sumValue += getTime;
               n += 1;
            }catch(ProjectException e){
                unknown += 1;
            }
        }
        if(n==0){throw new ProjectException(ProjectException.IMPOSSIBLE);}
        if(unknown == 0){return time;}
        int average = sumValue/n;
        if(parallel){time = Math.max(time, average);}
        else{time += unknown*average;}
        return time;
    }
    
    private int calculateTimeModalityM() throws ProjectException{
        int time = 0;
        int unknown = 0;
        int maxValue = 0;
        for(Activity a: activities){
            try{
               int getTime = a.time();
               time = calculateTimeByType(time, getTime);
               maxValue = Math.max(maxValue,getTime);
            }catch(ProjectException e){
                unknown+=1;              
            }
        }
        if(time==0){throw new ProjectException(ProjectException.IMPOSSIBLE);}
        if(unknown==0){return time;}
        if(!parallel){time += maxValue*unknown;}
        return time;
    }
    
     /**
     * Calculates an time of a subactivity
     * @return 
     * @throws ProjectException UNKNOWN, if it doesn't exist. IMPOSSIBLE, if it can't be calculated
     */
    public int time(String activity) throws ProjectException{
        for(Activity v: activities){
            if(activity.equals(v.name)){
                try{
                    int time = v.time();
                    return time;
                }
                catch(ProjectException e){
                    throw new ProjectException(ProjectException.IMPOSSIBLE);
                }
            }
            if(v instanceof Composed){
                try{
                    Composed composed = (Composed) v;
                    return composed.time(activity);
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
