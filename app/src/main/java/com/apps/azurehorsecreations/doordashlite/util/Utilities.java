package com.apps.azurehorsecreations.doordashlite.util;

/**
 * Utilities
 */

public class Utilities {
    /**
     * getFormattedAmount
     * From https://www.developerfeed.com/how-to-convert-amount-in-cents-to-dollars-with-formatting/
     *
     * @param amount
     * @return String
     */
    public static String getFormattedAmount(int amount) {
        int cents = amount % 100;
        int dollars = (amount - cents) / 100;

        if (amount == 0) {
            return "Free";
        }

        String camount;
        if (cents <= 9) {
            camount = 0 + "" + cents;
        } else {
            camount = "" + cents;
        }

        return "$" + dollars + "." + cents;
    }
}
