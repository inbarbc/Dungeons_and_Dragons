package Game.Handelers;

import Game.DatabaseUnits;
import View.Input.InputProvider;

import java.util.Scanner;

public class InputHandler {

    private static String userInputRegex = "[" + InputProvider.CastAbility.getRegex() + "]";
    private static String menuRegex = "[1-" + DatabaseUnits.playerPool.size() + "]";

    public static InputProvider InputPlayerGame() {

        return InputProvider.FindByKey(InputCache(userInputRegex) + "");

    }

    public static char InputMenu() {
        return InputCache(menuRegex);
    }

    private static char InputCache(String regex) {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String input = "";
        do {
            input = myObj.nextLine();
        } while (!ValidateWithRegex(input, regex));
        return input.charAt(0);
    }


    private static Boolean ValidateWithRegex(String s, String regex) {
        if (s.length() == 1)
            if (s.matches(regex))
                return true;
        return false;
    }
}