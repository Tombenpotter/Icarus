package tombenpotter.icarus;

import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.HashSet;

public class ConfigHandler {

    public static Configuration config;
    public static String general = "general";
    public static String compat = "mod compatibilty";
    public static String enchant = "enchantments";

    public static boolean holdKeyToHover, showWingsStats, showWingsHUD;
    public static float hungerConsumed;
    public static HashSet<Integer> dimensionNoWingBurn = new HashSet<Integer>();
    public static HashSet<Integer> dimensionWingsDisabled = new HashSet<Integer>();
    public static boolean enableThaumcraftCompat, enableTECompat, enableOreDictCompat, enableEIOCompat, enableBotaniaCompat, enableACCompat, enableBMCompat, enableIECompat, enableWitcheryCompat;
    public static int boostEnchantID, hoverEnchantID, waterproofEnchantID;
    public static int[] wingsHUDCoords = new int[2];

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void load() {
        config.load();
    }

    public static void syncConfig() {
        config.addCustomCategoryComment(general, "General options");
        config.addCustomCategoryComment(compat, "Enable or disable mod compatibility here");
        config.addCustomCategoryComment(enchant, "Change enchantment IDs here");

        //General
        holdKeyToHover = config.getBoolean("holdKeyToHover", general, false, "Hold the Hovering key to make your wings hover.\nIf not enabled, you wings will make you hover as soon as you are off the groud.\nClientside only config, changing it serverside will do nothing.");
        showWingsStats = config.getBoolean("showWingsStats", general, true, "Allows wings stats to be shown when holding shift and hovering over wings.\nDisable to prevent stats to be shown altogether");
        int[] dimsNoBurn = config.get(general, "dimensionsWingsDontBurn", new int[]{1}, "The IDs of the dimensions where Wings will never be able to burn.").getIntList();
        for (int i : dimsNoBurn) {
            dimensionNoWingBurn.add(i);
        }
        hungerConsumed = config.getFloat("hungerConsumed", general, 0.5F, 0.25F, Float.MAX_VALUE, "Change the hunger consumed by each flap of the wings.");
        wingsHUDCoords = config.get(general, "wingsHUDCoords", new int[]{0, 0}, "The coords on the screen where the wings' HUD will show up.\nThe numbers are in pixels.").getIntList();
        showWingsHUD = config.getBoolean("showWingsHUD", general, true, "Whether or not to show the HUD for the wings.");
        int[] dimsNoWork = config.get(general, "dimensionsWingsDisabled", new int[]{}, "The IDs of the dimensions where Wings will never be able to work.").getIntList();
        for (int i : dimsNoWork) {
            dimensionWingsDisabled.add(i);
        }

        //Compat
        enableThaumcraftCompat = config.getBoolean("enableThaumcraftCompatibility", compat, true, modCompat("Thaumcraft"));
        enableTECompat = config.getBoolean("enableTECompatibility", compat, true, modCompat("Thermal Expansion"));
        enableOreDictCompat = config.getBoolean("enableOreDictCompatibilty", compat, true, "Enable Ore Dictionary wings when the ores exist in the Ore Dict.\nWill do nothing if the Ore Dictionary entries don't exist");
        enableBotaniaCompat = config.getBoolean("enableBotaniaCompatibility", compat, true, modCompat("Botania"));
        enableEIOCompat = config.getBoolean("enableEnderIOCompatibility", compat, true, modCompat("EnderIO"));
        enableACCompat = config.getBoolean("enableAuraCascadeCompatibility", compat, true, modCompat("Aura Cascade"));
        enableIECompat = config.getBoolean("enableImmersiveEngineeringCompatibility", compat, true, modCompat("Immersive Engineering"));
        enableWitcheryCompat = config.getBoolean("enableWitcheryCompatibility", compat, true, modCompat("Witchery"));

        //Enchants
        boostEnchantID = config.getInt("boostEnchantID", enchant, 100, 0, 255, "Change the ID for the Flight Boost Enchant here.\nDo not go over 255 or the game will crash!");
        hoverEnchantID = config.getInt("hoverEnchantID", enchant, 101, 0, 255, "Change the ID for the Hover Enchant here.\nDo not go over 255 or the game will crash!");
        waterproofEnchantID = config.getInt("waterproofEnchantID", enchant, 102, 0, 255, "Change the ID for the Waterproof Enchant here.\nDo not go over 255 or the game will crash!");

        config.save();
    }

    private static String modCompat(String mod) {
        return "Enable " + mod + " wings when the mod is present.\nWill do nothing if " + mod + " isn't in your modlist";
    }
}
