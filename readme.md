# Cell Phone Number Formatting is inconsistently applied

## Description

Some phone fields on move service orders ignore the phone number format specified by VALPHONEFORMAT.
Cell Phone field on Move In orders (see CIS-6164_CellField_MoveIn.JPG)

## Acceptance Criteria

The Cell Phone field on Move-In orders uses the format specified by VALPHONEFORMAT.

## Current classes with reference to VALPHONEFORMAT

care/callmaintenance:
    CallMaintenanceModel.java
    MoveInInfoModel.java
    MoveInModel.java
