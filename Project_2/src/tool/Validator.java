package tool;



public class Validator {
    
    // Regex patterns
    public static final String NATIONAL_ID_PATTERN = "^\\d{12}$";
    public static final String FULL_NAME_PATTERN = "^[A-Za-z][A-Za-z\\s]{1,24}$";
    public static final String GENDER_PATTERN = "^[MFmf]$";
    public static final String PHONE_PATTERN = "^\\d{10}$";
    public static final String ROOM_ID_PATTERN = "^R\\d{3}$";
    public static final String DATE_PATTERN = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
    public static final String POSITIVE_INTERGER_PATTER ="^[1-9]\\d*$";

    // Generic validation method
    public static boolean isValid(String input, String pattern) {
        return input.matches(pattern);
    }
} 