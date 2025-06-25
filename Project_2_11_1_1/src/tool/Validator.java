package tool;

public class Validator {
    
    // Regex patterns
    public static final String NATIONAL_ID_PATTERN = "^\\d{12}$";
    public static final String FULL_NAME_PATTERN = "^[A-Za-z][A-Za-z\\s]{1,24}$";
    public static final String GENDER_PATTERN = "^[MFmf]$";
    public static final String PHONE_PATTERN = "^(03[2-9]|086|09[6-8]|090|093|089|070|079|077|076|078|081|082|083|084|085|088|091|094|052|056|058|092)\\d{7}$";
    public static final String ROOM_ID_PATTERN = "^R\\d{3}$";

    // Generic validation method
    public static boolean isValid(String input, String pattern) {
        return input.matches(pattern);
    }
} 