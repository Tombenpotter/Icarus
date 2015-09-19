package tombenpotter.icarus.common.util;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public abstract class ModItemGetter {

    public static ItemStack leadstoneCapacitor = null;
    public static ItemStack redstoneCapacitor = null;
    public static ItemStack resonantCapacitor = null;

    public static ItemStack basicCapacitor = null;
    public static ItemStack doubleLayeredCapacitor = null;
    public static ItemStack octadicCapacitor = null;

    private ModItemGetter() {
    }

    public static void load() {
        leadstoneCapacitor = new ItemStack(GameRegistry.findItem("ThermalExpansion", "capacitor"), 1, 2);
        redstoneCapacitor = new ItemStack(GameRegistry.findItem("ThermalExpansion", "capacitor"), 1, 4);
        resonantCapacitor = new ItemStack(GameRegistry.findItem("ThermalExpansion", "capacitor"), 1, 5);

        basicCapacitor = new ItemStack(GameRegistry.findItem("EnderIO", "itemBasicCapacitor"), 1, 0);
        doubleLayeredCapacitor = new ItemStack(GameRegistry.findItem("EnderIO", "itemBasicCapacitor"), 1, 1);
        octadicCapacitor  = new ItemStack(GameRegistry.findItem("EnderIO", "itemBasicCapacitor"), 1, 2);
    }
}
