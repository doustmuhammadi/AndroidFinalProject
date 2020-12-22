package com.cobacobms.finalproject.utilities;

import android.annotation.SuppressLint;

public class DataValidation {
    public static void CheckMobile(String mobile) {
        if (!mobile.startsWith("+98"))
            throw new NullPointerException("Mobile must start with \"+98\"");
        else {
            mobile = mobile.substring(3);
            if (mobile.length() != 10)
                throw new NullPointerException("Mobile length is incorrect. (" + mobile.length() + " digits)");
            else if (!mobile.startsWith("9"))
                throw new NullPointerException("Incorect Mobile Number. Mobile code is \"9\"");
        }
    }

    @SuppressLint("NewApi")
    public static void CheckName(String name, String type) {
        if (name.length() < 3)
            throw new NullPointerException("It is not a real " + type);

        char ch = name.charAt(0);
        if (Character.isDigit(ch))
            throw new NullPointerException(type + " shouldn't start with Number");
        if (Character.isSpaceChar(ch))
            throw new NullPointerException(type + " shouldn't start with \"Space\"");
        if (!Character.isAlphabetic(ch))
            throw new NullPointerException(type + " must start with Alphabet");
    }

    public static void CheckNationalId(String id) {
        if (id.length() != 9)
            throw new NullPointerException("National ID length is incorrect (" + id.length() + " digits)");
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            throw new NullPointerException("National ID is not valid");
        }
    }

    public static void CheckPostalCode(String postalCode) {
        if (postalCode.length() != 11)
            throw new NullPointerException("National ID length is incorrect (" + postalCode.length() + " digits)");
        try {
            Double.parseDouble(postalCode);
        } catch (NumberFormatException ex) {
            throw new NullPointerException("Postal Code is not valid");
        }
    }

    public static void CheckPassword(String password, String confirmPassword) {
        if (password.length() < 6)
            throw new NullPointerException("Password length is incorrect (" + password.length() + " characters)");
        if (!password.equals(confirmPassword))
            throw new NullPointerException("Passwords do not match");
    }
}

