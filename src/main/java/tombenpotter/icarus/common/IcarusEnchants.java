package tombenpotter.icarus.common;

import tombenpotter.icarus.ConfigHandler;
import tombenpotter.icarus.common.enchants.EnchantWingBase;

public class IcarusEnchants {

    public static EnchantWingBase wingJumpBoost;

    public static void registerEnchants(){
        wingJumpBoost = new EnchantWingBase("boost", ConfigHandler.boostEnchantID, 8);
    }
}
