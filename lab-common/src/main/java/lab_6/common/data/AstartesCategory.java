package lab_6.common.data;


/**
 * Enumeration with marine category constants.
 */
public enum AstartesCategory {
    AGGRESSOR,
    INCEPTOR,
    TACTICAL,
    CHAPLAIN,
    HELIX;

    /**
     * Generates a list of enum string values.
     * @return String with all enum values.
     */
    public static String listOfCategory() {
        String listofCategory = "";
        for (AstartesCategory category : values()) {
            listofCategory += category.name() + ", ";
        }
        return listofCategory.substring(0, listofCategory.length()-2);
    }
}