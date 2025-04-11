// Atreyi Seal
// Period 4

/*
 * This is a stub class that will be completed for the Final Submission.
 *
 * For the Final submission:
 *   Students should leverage the SimpleDateFormat and/or DateFormat.
 *   Do NOT implement from scratch. Instead, search the internet for help
 *   on how to use SimpleDateFormat/DateFormat to accomplish the desired
 *   behavior.
 * 
 *   A properly implemented class can have as few as ~10 lines of code.
 */

 import java.text.*;
 import java.util.*;

public class DateCell extends Cell {
    public String toString() {    
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM d, yyyy");
        String output = null;
        try {
            Date date = new SimpleDateFormat("MM/dd/yy").parse(getExpression());
            output = outputFormat.format(date);
        } catch (ParseException e) {
        } if (output.equals(null)) {
            try {
                Date date = new SimpleDateFormat("MM/dd/yyyy").parse(getExpression());
                output = outputFormat.format(date); 
            } catch (ParseException e) {

            }
        }
        return output;
    }
}




