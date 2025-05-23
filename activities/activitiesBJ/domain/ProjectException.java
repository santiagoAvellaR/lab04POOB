package domain;

/**
 * Write a description of class ProjectException here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ProjectException extends Exception
{
    // instance variables - replace the example below with your own
    public static final String TIME_EMPTY = "The time can't be null";
    public static final String TIME_ERROR = "The time must be between 1 and 24";
    public static final String COMPOSED_EMPTY = "The composed activities must have activities related with them";
    public static final String IMPOSSIBLE = "It's impossible calculate the time of the activity";
    public static final String UNKNOWN = "The activity doesn't exists";
    
    public static final String NAME_ALREADY_USED = "The name is already used";
    public static final String THE_SUBACTIVITY_NOT_EXISTS = "A sub-activity that compose the composed activity don't exists";
    public static final String COST_AND_TIME_ARE_NOT_NUMBERS = "Cost and time should be numerous ";
    public static final String INVALID_TYPE = "The type cannot be different from “Paralela” or “Secuencial";
    
    public static final String ACTIVITY_NOT_FOUND = "Cero activities found";
    /**
     * Constructor for objects of class ProjectException
     */
    public ProjectException(String message)
    {
        super(message);
    }
}