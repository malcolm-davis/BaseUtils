package base.utils;


public class Argument {
    public static void IllegalArgumentNullCheck(Object value, String vairableName) {
        if(value==null) {
            throw new IllegalArgumentException("Null value for " + vairableName);
        }
    }

    public static void IllegalArgumentEmptyCheck(String value, String vairableName) {
        if(value==null || value.trim().length()==0 ) {
            throw new IllegalArgumentException("Empty value for " + vairableName);
        }
    }
}
