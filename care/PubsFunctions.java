package care;

import care.supportingClasses.StringUtils;
import care.supportingClasses.ValidationInfo;

public class PubsFunctions {

    /**
     * Verifies that a phone number is all digits
     *
     * @param phoneNumber
     *                    The phone number to verify. Gracefully accepts null
     * @return true if the phone number is null, empty or all digits; false
     *         otherwise
     */
    public static boolean verify_phone_digits(String phoneNumber) {
        return (StringUtils.length(phoneNumber) > 0 ? phoneNumber.matches("^\\d{" + phoneNumber.length() + "}$")
                : true);
    }

    /**
     * Validates a phone format string. Displays a common dialog if the
     * format string is invalid.
     *
     * @param phoneFormat
     *                    The phone format to validate. Gracefully accepts null
     * @return false validation error if the phoneFormat string is greater than 13
     */
    public static ValidationInfo valid_format(String phoneFormat) {
        if (StringUtils.length(phoneFormat) > 13) {
            return new ValidationInfo(false, "Phone Format greater than 13 characters");
        }

        return new ValidationInfo(true);
    }

    /**
     * Verifies whether a phone number is properly formatted. If the
     * phone format/number is not valid, displays an error
     *
     * @param phoneNumber
     *                    The phone number to validate. Gracefully accepts null
     * @param phoneFormat
     *                    The format to place the number in. Gracefully accepts null
     * @return true if the phone number is properly formatted; false otherwise
     */
    public static ValidationInfo valid_phoneformat(String phoneNumber, String phoneFormat) {
        ValidationInfo validationInfo = valid_format(phoneFormat);
        if (validationInfo.isValid()) {

            StringBuffer phoneDigits = new StringBuffer();
            for (int x = 0; x < StringUtils.length(phoneNumber); x++) {
                if (phoneNumber.substring(x, x + 1).matches("[0-9]")) {
                    phoneDigits.append(phoneNumber.substring(x, x + 1));
                }
            }

            StringBuffer formatDigits = new StringBuffer();
            int i = 0;
            for (int x = 0; x < StringUtils.length(phoneFormat); x++) {
                String phoneFormatChar = phoneFormat.substring(x, x + 1);
                if (phoneFormatChar.matches("[A-Z]")) {
                    formatDigits.append(StringUtils.safeSubstring(phoneDigits.toString(), i, i + 1));
                    i++;
                } else {
                    formatDigits.append(phoneFormatChar);
                }
            }

            if (!phoneNumber.equals(formatDigits.toString())) {
                return new ValidationInfo(false, "Phone Format is not correct");
            }
            if (formatDigits.length() != phoneFormat.trim().length()) {
                return new ValidationInfo(false, "Number of digits does not match the format");
            }
        } else {
            return validationInfo;
        }

        return new ValidationInfo(true);
    }

    /**
     * Writes out a phone number in a proper format
     *
     * @param phoneNumber
     *                        The phone number to write out. Gracefully accepts null
     * @param phoneFormat
     *                        The format - all X's will be replaced by the phone
     *                        number
     * @param formattedNumber
     *                        The resultant formatted phone number
     * @return false if the phoneNumber could not be properly formatted; true
     *         otherwise
     */
    public static ValidationInfo phone_out_format(String phoneNumber, String phoneFormat,
            StringBuffer formattedNumber) {
        ValidationInfo validationInfo = valid_format(phoneFormat);

        if (validationInfo.isValid()) {
            int i = 0;
            for (int x = 0; x < StringUtils.length(phoneFormat); x++) {
                if (phoneFormat.charAt(x) == 'X') {
                    int start = i++;
                    formattedNumber.append(StringUtils.safeSubstring(phoneNumber, start, start + 1));
                } else {
                    formattedNumber.append(phoneFormat.substring(x, x + 1));
                }
            }

            if (StringUtils.length(phoneNumber) != i) {
                return new ValidationInfo(false, "Number of digits does not match the format");
            } else {
                return new ValidationInfo(true);
            }
        } else {
            return validationInfo;
        }
    }

    // ...
}
