package utilities;

public class CaseSwitcher {

    public static String toProperCase(String input) {
        StringBuilder properCase = new StringBuilder();
        boolean firstChar = true;

        for (char c : input.toCharArray()) {
            if (firstChar) {
                c = Character.toUpperCase(c);
                firstChar = false;
            } else {
                c = Character.toLowerCase(c);
            }

            properCase.append(c);
        }

        return properCase.toString();
    }
}
