package tombenpotter.icarus.common;

import tombenpotter.icarus.ConfigHandler;
import tombenpotter.icarus.api.enchants.EnchantWingBase;

public class IcarusEnchants {

    public static EnchantWingBase wingJumpBoost;
    public static EnchantWingBase wingHover;
    public static EnchantWingBase wingWaterproof;

    public static void registerEnchants() {
        wingJumpBoost = new EnchantWingBase("boost", 5, ConfigHandler.boostEnchantID, 8);
        wingHover = new EnchantWingBase("hover", 5, ConfigHandler.hoverEnchantID, 5);
        wingWaterproof = new EnchantWingBase("waterproof", 5, ConfigHandler.waterproofEnchantID, 3);
    }
}
