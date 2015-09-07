package tombenpotter.icarus.common.util;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    public static Configuration config;
    public static String general = "general";
    public static String compat = "mod compatibilty";

    public static boolean holdSneakToHover;
    public static boolean enableThaumcraftCompat, enableTECompat, enableOreDictCompat, enableEIOCompat, enableBotaniaCompat, enableBMCompat;

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

        holdSneakToHover = config.getBoolean("holdSneakToHover", general, false, "Hold the Sneaking key to make your wings hover.\nIf not enabled, you wings will make you hover as soon as you are off the groud.\nClientside only config, changing it serverside will do nothing.");

        enableThaumcraftCompat = config.getBoolean("enableThaumcraftCompatibility", compat, true, "Enable Thaumcraft wings when the mod is present.\nWill do nothing if Thaumcraft isn't in your modlist");
        enableTECompat = config.getBoolean("enableTECompatibility", compat, true, "Enable Thermal Expansion wings when the mod is present.\nWill do nothing if Thermal Expansion isn't in your modlist");
        enableOreDictCompat = config.getBoolean("enableOreDictCompatibilty", compat, true, "Enable Ore Dictionary wings when the ores exist in the Ore Dict.\nWill do nothing if the Ore Dictionary entries don't exist");

        config.save();
    }
}
