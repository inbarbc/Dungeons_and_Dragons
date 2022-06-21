package Game;

import Game.Tiles.Units.Enemies.Boss;
import Game.Tiles.Units.Enemies.Monster;
import Game.Tiles.Units.Enemies.Trap;
import Game.Tiles.Units.Player.Hunter;
import Game.Tiles.Units.Player.Mage;
import Game.Tiles.Units.Player.Warrior;
import Game.Tiles.Units.Player.Rogue;
import Game.Tiles.Units.Unit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DatabaseUnits {


    public static Map<String, Unit> playerPool = new HashMap<String, Unit>();
    public static Map<String, Unit> enemyPool = new HashMap<String, Unit>();
    private final static String dirAddons = "src/addons";

    public DatabaseUnits() {
        BuildDictionary();
    }

    public static void BuildDictionary() {
        BuildUnit("/addons/dbPlayer", playerPool); // players list
        BuildUnit("/addons/dbEnemy", enemyPool); // enemies list
    }

    public static String GetFileJar(String address) {
        String fileText = "";
        InputStream in = DatabaseUnits.class.getResourceAsStream(address);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        fileText = reader.lines().collect(Collectors.joining("\n"));
        return fileText;
    }

    // transfer the table to presentation view
    private static void BuildUnit(String address, Map<String, Unit> map) {
        String txtToSplit = GetFileJar(address);
        ArrayList<String> enemyUnit = new ArrayList<String>(Arrays.asList(txtToSplit.split("\n")));
        for (String unitStr : enemyUnit) {
            ArrayList<String> argumentUnit = new ArrayList<String>(Arrays.asList(unitStr.split("\\|")));
            Unit unitObj = FacrotyUnit(argumentUnit);
            map.put(unitObj.toString(), unitObj);
        }

    }

    private static Unit FacrotyUnit(ArrayList<String> typeUnit) {

        String type = typeUnit.get(0);

        String name = typeUnit.get(1);
        char tile = typeUnit.get(2).charAt(0);
        int health = Integer.parseInt(typeUnit.get(3));
        int attack = Integer.parseInt(typeUnit.get(4));
        int defence = Integer.parseInt(typeUnit.get(5));
        if (type.equals("Monster")) {
            int visionRange = Integer.parseInt(typeUnit.get(6));
            int expirenceValue = Integer.parseInt(typeUnit.get(7));
            return new Monster(name, tile, health, attack, defence, expirenceValue, visionRange);
        }
        if (type.equals("Trap")) {
            int visibilityTime = Integer.parseInt(typeUnit.get(7));
            int invisibilityTime = Integer.parseInt(typeUnit.get(8));
            int expirenceValue = Integer.parseInt(typeUnit.get(6));
            return new Trap(name, tile, health, attack, defence, expirenceValue, visibilityTime, invisibilityTime);
        }
        if (type.equals("Warrior")) {
            int cooldown = Integer.parseInt(typeUnit.get(6));
            return new Warrior(name, tile, health, attack, defence, cooldown);
        }
        if (type.equals("Rogue")) {
            int cost = Integer.parseInt(typeUnit.get(6));
            return new Rogue(name, tile, health, attack, defence, cost);
        }
        if (type.equals("Mage")) {
            int manaPool = Integer.parseInt(typeUnit.get(6));
            int manaCost = Integer.parseInt(typeUnit.get(7));
            int spellPower = Integer.parseInt(typeUnit.get(8));
            int hitCount = Integer.parseInt(typeUnit.get(9));
            int range = Integer.parseInt(typeUnit.get(10));
            return new Mage(name, tile, health, attack, defence, manaPool, manaCost, spellPower, hitCount, range);
        }
        if (type.equals("Hunter")) {
            int range = Integer.parseInt(typeUnit.get(6));
            return new Hunter(name, tile, health, attack, defence, range);
        }

        if (type.equals("Boss")) {
            int visionRange = Integer.parseInt(typeUnit.get(6));
            int expirenceValue = Integer.parseInt(typeUnit.get(7));
            int abilityFrequency = Integer.parseInt(typeUnit.get(8));
            return new Boss(name, tile, health, attack, defence, expirenceValue, visionRange, abilityFrequency);
        }

        return null;
    }
}
