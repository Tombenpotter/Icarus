package tombenpotter.icarus.common;

import tombenpotter.icarus.ConfigHandler;
import tombenpotter.icarus.api.enchants.EnchantWingBase;

public class IcarusEnchants {

    public static EnchantWingBase wingJumpBoost;
    public static EnchantWingBase wingHover;

    public static void registerEnchants(){
        wingJumpBoost = new EnchantWingBase("boost", ConfigHandler.boostEnchantID, 8);
        wingHover = new EnchantWingBase("hover", ConfigHandler.hoverEnchantID, 5);
    }
}
