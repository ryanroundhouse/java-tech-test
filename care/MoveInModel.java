package care;

import java.util.List;

import care.supportingClasses.AbstractFormModel;
import care.supportingClasses.CareAccountsHolder;
import care.supportingClasses.KeyValueCache;
import care.supportingClasses.MoveInInfo;
import care.supportingClasses.PhoneNumber;
import care.supportingClasses.StringUtils;
import care.supportingClasses.ValidationInfo;

public class MoveInModel extends AbstractFormModel {
    // ...

    private PhoneNumber resolvePhoneNumber(String phoneNumber) {
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

    public boolean save(List<MoveInInfo> moveInInfo) {
        // ...
        for (MoveInInfo info : moveInInfo) {
            if (!validate(info.name)) {
                return false;
            }
        }
        // ...
        return true;
    }

    public boolean validate(String harrisname) {
        boolean passed = true;
        // ...

        if (harrisname.equalsIgnoreCase("business_phone")) {
            PhoneNumber formattedNumber = resolvePhoneNumber(getCareAccountsHolder().getBusiness_phone());

            passed = formattedNumber.getValidationInformation().isValid();

            if (!passed) {
                registerError(harrisname, "The format of the business phone is not correct.");
                return false;
            } else {
                getCareAccountsHolder().setBusiness_phone(formattedNumber.getFormattedNumber());
            }
        } else if (harrisname.equalsIgnoreCase("home_phone")) {
            PhoneNumber formattedNumber = resolvePhoneNumber(getCareAccountsHolder().getHome_phone());
            passed = formattedNumber.getValidationInformation().isValid();

            if (!passed) {
                registerError(harrisname, "The format of the home phone is not correct. ");
                return false;
            } else {
                getCareAccountsHolder().setHome_phone(formattedNumber.getFormattedNumber());
            }
        }
        // ...

        return passed;
    }

    private CareAccountsHolder getCareAccountsHolder() {
        return new CareAccountsHolder();
    }

    private void registerError(String harrisname, String string) {
    }
    // ...
}
