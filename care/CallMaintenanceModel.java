package care;

import care.supportingClasses.AccountsDetailDefaultModel;
import care.supportingClasses.KeyValueCache;
import care.supportingClasses.PhoneNumber;
import care.supportingClasses.StringUtils;
import care.supportingClasses.ValidationInfo;

public class CallMaintenanceModel extends AccountsDetailDefaultModel {
    // ...

    public PhoneNumber resolvePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || StringUtils.length(phoneNumber) <= 0) {
            return new PhoneNumber(new ValidationInfo(true), null);
        }

        String phoneFormat = KeyValueCache.getSafeKeyValue("VALPHONEFORMAT");
        PhoneNumber finalNumber = new PhoneNumber();
        finalNumber.setFormattedNumber(phoneNumber);

        if (StringUtils.lengthTrimmed(phoneFormat) > 0) {
            if (PubsFunctions.verify_phone_digits(phoneNumber)) {
                StringBuffer formattedNumber = new StringBuffer();

                finalNumber.setValidationInformation(
                        PubsFunctions.phone_out_format(phoneNumber, phoneFormat, formattedNumber));

                if (finalNumber.getValidationInformation().isValid()) {
                    finalNumber.setFormattedNumber(formattedNumber.toString().trim());
                }
            } else {
                finalNumber.setValidationInformation(PubsFunctions.valid_phoneformat(phoneNumber, phoneFormat));
            }
        } else {
            finalNumber.setValidationInformation(new ValidationInfo(true));
        }

        return finalNumber;
    }

    // ...
}
