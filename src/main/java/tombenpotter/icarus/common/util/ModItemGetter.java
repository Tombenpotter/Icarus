package tombenpotter.icarus.common.util;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tombenpotter.icarus.common.items.ItemWingAuraCascade;

import java.util.ArrayList;

public abstract class ModItemGetter {

    public static ItemStack leadstoneCapacitor = null;
    public static ItemStack redstoneCapacitor = null;
    public static ItemStack resonantCapacitor = null;

    public static ItemStack basicCapacitor = null;
    public static ItemStack doubleLayeredCapacitor = null;
    public static ItemStack octadicCapacitor = null;

    public static ArrayList<ItemStack> angelsteelIngots = new ArrayList<ItemStack>();

    private ModItemGetter() {
    }

    public static void load() {
        leadstoneCapacitor = findItem("ThermalExpansion", "capacitor", 1, 2);
        redstoneCapacitor = findItem("ThermalExpansion", "capacitor", 1, 4);
        resonantCapacitor = findItem("ThermalExpansion", "capacitor", 1, 5);

        basicCapacitor = findItem("EnderIO", "itemBasicCapacitor", 1, 0);
        doubleLayeredCapacitor = findItem("EnderIO", "itemBasicCapacitor", 1, 1);
        octadicCapacitor = findItem("EnderIO", "itemBasicCapacitor", 1, 2);

        Item angelsteelIngot = GameRegistry.findItem("aura", "ingotAngelSteel");
        if (angelsteelIngot != null) {
            for (int i = 0; i < ItemWingAuraCascade.MAX_TIER; i++) {
                angelsteelIngots.add(new ItemStack(angelsteelIngot, 1, i));
            }
        }
    }

    public static ItemStack findItem(String modID, String itemName, int amount, int meta) {
        Item item = GameRegistry.findItem(modID, itemName);

        if (item != null) {
            return new ItemStack(item, amount, meta);
        }
        return null;
    }
}
