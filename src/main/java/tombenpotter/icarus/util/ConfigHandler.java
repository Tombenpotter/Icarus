package tombenpotter.icarus.util;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    public static Configuration config;
    public static String general = "general";

    public static boolean  holdSneakToHover;

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void load() {
        config.load();
    }

    public static void syncConfig() {
        config.addCustomCategoryComment(general, "General options");

        holdSneakToHover = config.getBoolean("holdSneakToHover", general, false, "Hold the Sneaking key to make your wings hover.\nIf not enabled, you wings will make you hover as soon as you are off the groud.\nClientside only config, changing it serverside will do nothing.");

        config.save();
    }
}
