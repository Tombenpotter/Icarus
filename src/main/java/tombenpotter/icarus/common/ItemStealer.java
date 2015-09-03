package tombenpotter.icarus.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class ItemStealer {

    public static ItemStack leadstoneCapacitor = null;
    public static ItemStack redstoneCapacitor = null;
    public static ItemStack resonantCapacitor = null;

    public static void load() {
        leadstoneCapacitor = new ItemStack(GameRegistry.findItem("ThermalExpansion", "capacitor"), 1, 2);
        redstoneCapacitor = new ItemStack(GameRegistry.findItem("ThermalExpansion", "capacitor"), 1, 4);
        resonantCapacitor = new ItemStack(GameRegistry.findItem("ThermalExpansion", "capacitor"), 1, 5);
    }
}
