package tombenpotter.icarus.common.util;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tombenpotter.icarus.common.items.ItemWingAuraCascade;

import java.util.ArrayList;

public abstract class ModItemGetter {

    /* Thermal Expansion */
    public static ItemStack potatoCapacitor = null;
    public static ItemStack leadstoneCapacitor = null;
    public static ItemStack redstoneCapacitor = null;
    public static ItemStack resonantCapacitor = null;

    /* EnderIO */
    public static ItemStack basicCapacitor = null;
    public static ItemStack doubleLayeredCapacitor = null;
    public static ItemStack octadicCapacitor = null;

    /* Aura Cascade */
    public static ArrayList<ItemStack> angelsteelIngots = new ArrayList<ItemStack>();

    /* Witchery */
    public static ItemStack nullifiedLeather = null;
    public static ItemStack wowenCruor = null;
    public static ItemStack mandrakeRoot = null;
    public static ItemStack tongueOfDog = null;

    /* Erebus */
    public static ItemStack petrifiedWood = null;
    public static ItemStack exoskeletonPlate = null;
    public static ItemStack jade = null;
    public static ItemStack altarFragment = null;
    public static ItemStack shardBone = null;
    public static ItemStack flyWing = null;

    private ModItemGetter() {
    }

    public static void load() {
        potatoCapacitor = findItem("ThermalExpansion", "capacitor", 1, 1);
        leadstoneCapacitor = findItem("ThermalExpansion", "capacitor", 1, 2);
        redstoneCapacitor = findItem("ThermalExpansion", "capacitor", 1, 4);
        resonantCapacitor = findItem("ThermalExpansion", "capacitor", 1, 5);

        basicCapacitor = findItem("EnderIO", "itemBasicCapacitor", 1, 0);
        doubleLayeredCapacitor = findItem("EnderIO", "itemBasicCapacitor", 1, 1);
        octadicCapacitor = findItem("EnderIO", "itemBasicCapacitor", 1, 2);

        nullifiedLeather = findItem("witchery", "ingredient", 1, 131);
        wowenCruor = findItem("witchery", "ingredient", 110, 161);
        mandrakeRoot = findItem("witchery", "ingredient", 1, 22);
        tongueOfDog = findItem("witchery", "ingredient", 1, 25);

        petrifiedWood = findItem("erebus", "materials", 1, 7);
        exoskeletonPlate = findItem("erebus", "materials", 1, 0);
        jade = findItem("erebus", "materials", 1, 1);
        altarFragment = findItem("erebus", "materials", 1, 15);
        shardBone = findItem("erebus", "materials", 1, 2);
        flyWing = findItem("erebus", "materials", 1, 6);
    }

    public static void auraCascadeIngotBecausePixlepixIsDuh() {
        Item angelsteelIngot = GameRegistry.findItem("aura", "ingotAngelSteel");
        if (angelsteelIngot != null) {
            for (int i = 0; i < ItemWingAuraCascade.MAX_TIER; i++) {
                angelsteelIngots.add(new ItemStack(angelsteelIngot, 1, i));
            }
        }
    }

    public static ItemStack findItem(String modID, String itemName, int amount, int meta) {
        Item item = GameRegistry.findItem(modID, itemName);

        return item != null ? new ItemStack(item, amount, meta) : null;
    }
}
