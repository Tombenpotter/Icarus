package tombenpotter.icarus;

import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import tombenpotter.icarus.common.IcarusEnchants;
import tombenpotter.icarus.common.network.PacketClientConfig;
import tombenpotter.icarus.common.network.PacketHandler;
import tombenpotter.icarus.reference.Reference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ConfigHandler {

    public static Configuration config;
    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_COMPAT = "mod compatibilty";
    public static final String CATEGORY_ENCHANTMENTS = "enchantments";

    public static boolean holdSneakToHover, showWingsStats, showWingsHUD, shapelessWings;
    public static float hungerConsumed;
    public static HashSet<Integer> dimensionNoWingBurn = new HashSet<Integer>();
    public static HashSet<Integer> dimensionWingsDisabled = new HashSet<Integer>();
    public static boolean enableThaumcraftCompat, enableTECompat, enableOreDictCompat, enableEIOCompat, enableBotaniaCompat, enableACCompat, enableBMCompat = false;
    public static int boostEnchantID = 102;
    public static int hoverEnchantID = 103;
    public static int waterproofEnchantID = 104;
    public static int[] wingsHUDCoords = new int[2];
    private static int enchantmentArrayLimit = 255;

    private static String modComment(String name) {
        return "icarus.config.enablemodcompat" + " " + name + " " + StatCollector.translateToLocal("icarus.config.enablemodcompat.2") + "\n" + StatCollector.translateToLocal("icarus.config.donothing") + " " + name + " "+ StatCollector.translateToLocal("icarus.config.donothing.2");
    }

    public static void init(File file) {
        if (config == null) {
            config = new Configuration(file);
            loadConfig();
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if(event.modID.equalsIgnoreCase(Reference.ID)) {
            loadConfig();
            PacketHandler.INSTANCE.sendToServer(new PacketClientConfig());
        }
    }

    private static void loadConfig() {
        Property prop;

        config.addCustomCategoryComment(CATEGORY_COMPAT, StatCollector.translateToLocal("icarus.config.compat.desc"));
        config.addCustomCategoryComment(CATEGORY_ENCHANTMENTS, StatCollector.translateToLocal("icarus.config.enchantments.desc"));
        config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, StatCollector.translateToLocal("icarus.config.general.desc"));
        config.setCategoryRequiresWorldRestart(CATEGORY_COMPAT, true);
        config.setCategoryRequiresWorldRestart(CATEGORY_ENCHANTMENTS, true);

        //General
        prop = config.get(Configuration.CATEGORY_GENERAL, "holdSneakToHover", false);
        prop.comment = StatCollector.translateToLocal("icarus.config.sneaktohover.desc") + "\n" + StatCollector.translateToLocal("icarus.config.sneaktohover.desc.2") + "\n" + StatCollector.translateToLocal("icarus.config.sneaktohover.desc.3");
        holdSneakToHover = prop.getBoolean();
        prop = config.get(Configuration.CATEGORY_GENERAL, "showWingsStats", true); 
        prop.comment = StatCollector.translateToLocal("icarus.config.showwingsstats.desc") + "\n" + StatCollector.translateToLocal("icarus.config.showwingsstats.desc.2");
        showWingsStats = prop.getBoolean();

        prop = config.get(Configuration.CATEGORY_GENERAL, "dimensionsWingsDontBurn", new int[]{});
        prop.comment = StatCollector.translateToLocal("icarus.config.dimensionswingsdontburn.desc");
        int[] dimsNoBurn = prop.getIntList();
        for (int i : dimsNoBurn) {
            dimensionNoWingBurn.add(i);
        }

        prop = config.get(Configuration.CATEGORY_GENERAL, "dimensionsWingsAreDisabled", new int[]{});
        prop.comment = StatCollector.translateToLocal("icarus.config.dimensionswingsdisabled.desc");
        int[] dimsDisable = prop.getIntList();
        for (int i : dimsDisable) {
            dimensionWingsDisabled.add(i);
        }

        prop = config.get(Configuration.CATEGORY_GENERAL, "hungerConsumed", 0.5F);
        prop.setMinValue(0.25F).setMaxValue(Float.MAX_VALUE);
        prop.comment = StatCollector.translateToLocal("icarus.config.hunger.desc");
        hungerConsumed = convertToFloat(prop.getDouble());
        

        wingsHUDCoords = config.get(Configuration.CATEGORY_GENERAL, "wingsHUDCoords", new int[]{0, 0}, StatCollector.translateToLocal("icarus.config.hudcoords.desc") + "\n" + StatCollector.translateToLocal("icarus.config.hudcoords.desc.2")).getIntList();
        prop = config.get(Configuration.CATEGORY_GENERAL, "showWingsHUD", true);
        prop.comment = StatCollector.translateToLocal("icarus.config.winghud.desc");
        showWingsHUD = prop.getBoolean();
        

        enableOreDictCompat = loadCompatBool("enableOreDictCompatibility", "Ore Dictionary", true, true);
        enableThaumcraftCompat = loadCompatBool("enableThaumcraftCompatibility", "Thaumcraft", true, false);
        enableTECompat = loadCompatBool("enableTECompatibility", "ThermalExpansion", true, false);
        enableBotaniaCompat = loadCompatBool("enableBotaniaCompatibility", "Botania", true, false);
        enableEIOCompat = loadCompatBool("enableEnderIOCompatibility", "EnderIO", true, false);
        enableACCompat = loadCompatBool("enableAuraCascadeCompatibility", "Aura Cascade", true, false);

        //Enchants
        boostEnchantID = loadPropEnchantID("boostEnchantID", StatCollector.translateToLocal("enchantment.icarus.boost"), boostEnchantID);
        hoverEnchantID = loadPropEnchantID("hoverEnchantID", StatCollector.translateToLocal("enchantment.icarus.hover"), hoverEnchantID);
        waterproofEnchantID = loadPropEnchantID("waterproofEnchantID", StatCollector.translateToLocal("enchantment.icarus.waterproof"), waterproofEnchantID);
        if (config.hasChanged())
            config.save();
    }

    @SuppressWarnings("unchecked")
    public static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.addAll(new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
        list.addAll(new ConfigElement(config.getCategory(CATEGORY_COMPAT)).getChildElements());
        list.addAll(new ConfigElement(config.getCategory(CATEGORY_ENCHANTMENTS)).getChildElements());
        return list;
    }

    public static int loadPropEnchantID(String propName, String trueName, int default_) {
        String desc = "Change the ID for " + trueName + " Enchant here." + "\n" + "Do not go over " + enchantmentArrayLimit + " or the game will crash!";
        Property prop = config.get(CATEGORY_ENCHANTMENTS, propName, default_, desc);
        int val = prop.getInt(default_);
        if(val > enchantmentArrayLimit) {
            val = default_;
            prop.set(default_);
        }

        return val;
    }

   public static boolean loadCompatBool(String propName, String trueName, boolean default_, boolean customDesc) {
        String desc;
        if (customDesc == true) {
                desc = "Enable " + trueName + " wings when specific ingots are present in the " + trueName + ".";
        } else {
            desc = "Enable " + trueName + " wings when the mod is present." + "\n" + "Does nothing if " + trueName + " isn't in your modlist.";
        }
        Property prop = config.get(CATEGORY_COMPAT, propName, default_, desc);
        return prop.getBoolean(default_);
    }

    public static float convertToFloat(Double trouble) {
        Float plswork = trouble.floatValue();
        return plswork;
    }
}

