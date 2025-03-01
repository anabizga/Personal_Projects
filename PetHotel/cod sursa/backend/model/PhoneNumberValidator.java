package hotelanimale.is.model;

import java.util.regex.Pattern;

public class PhoneNumberValidator {
    private static final String PHONE_PATTERN = "^07\\d{8}$";

    public static void validate(String number) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        if (!pattern.matcher(number).matches()) {
            throw new IllegalArgumentException("Phone number is not a valid phone number!");
        }
    }
}
