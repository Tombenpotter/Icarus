package tombenpotter.icarus.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import thaumcraft.api.ThaumcraftApi;
import tombenpotter.icarus.ConfigHandler;
import tombenpotter.icarus.common.items.ItemSingleWing;
import tombenpotter.icarus.common.items.ItemWing;
import tombenpotter.icarus.common.items.ItemWingRF;
import tombenpotter.icarus.common.items.ItemWingThaumcraft;
import tombenpotter.icarus.common.util.IcarusWing;
import tombenpotter.icarus.common.util.ModItemGetter;

import java.util.ArrayList;

public class IcarusItems {

    public static final ItemArmor.ArmorMaterial ELECTRUM = EnumHelper.addArmorMaterial("ICARUS_ELECTRUM", 100, new int[]{3, 8, 6, 3}, 20);
    public static final ItemArmor.ArmorMaterial ENDERIUM = EnumHelper.addArmorMaterial("ICARUS_ENDERIUM", 100, new int[]{4, 9, 7, 4}, 30);
    public static ArrayList<String> wingNames = new ArrayList<String>();
    //Last damage value used: 9
    public static ItemSingleWing singleWings;
    public static ItemWing cardboardWings, featherWings, ironWings, goldDiamondWings, bronzeWings;
    public static ItemWingThaumcraft thaumiumWings, voidMetalWings;
    public static ItemWingRF leadstoneWings, electrumWings, enderiumWings;
    public static ItemArmor.ArmorMaterial CLOTH = EnumHelper.addArmorMaterial("ICARUS_CLOTH", 5, new int[]{1, 3, 2, 1}, 15);

    public static void registerItems() {
        ModItemGetter.load();

        singleWings = new ItemSingleWing();
        GameRegistry.registerItem(singleWings, "ItemSingleWings");

        cardboardWings = new ItemWing(CLOTH, new IcarusWing("CardboardWing", 64, 96, 0.25, 0.95, -0.25, -0.5, 0.8));
        GameRegistry.registerItem(cardboardWings, "ItemCardboardWings");
        addWingRecipe(0, cardboardWings, "logWood", Items.paper);

        featherWings = new ItemWing(ItemArmor.ArmorMaterial.CHAIN, new IcarusWing("FeatherWing", 96, 128, 0.35, 0.65, -0.15, -0.3, 0.8));
        GameRegistry.registerItem(featherWings, "ItemFeatherWings");
        addWingRecipe(1, featherWings, Items.bone, Items.feather);

        ironWings = new ItemWing(ItemArmor.ArmorMaterial.IRON, new IcarusWing("IronWing", 256, 132, 0.5, 0.9, -0.2, -0.4, 0.5));
        GameRegistry.registerItem(ironWings, "ItemIronWings");
        addWingRecipe(2, ironWings, "ingotIron", Items.feather);

        goldDiamondWings = new ItemWing(ItemArmor.ArmorMaterial.GOLD, new IcarusWing("DiamondGoldWing", 1024, 196, 0.6, 0.7, -0.2, -0.3, 0.6));
        GameRegistry.registerItem(goldDiamondWings, "ItemGoldDiamondWings");
        addWingRecipe(3, goldDiamondWings, "gemDiamond", "ingotGold");

        thaumiumWings = new ItemWingThaumcraft(ThaumcraftApi.armorMatThaumium, new IcarusWing("ThaumiumWing", 512, 256, 0.65, 0.55, -0.1, -0.2, 0.7));
        if (ConfigHandler.enableThaumcraftCompat && OreDictionary.doesOreNameExist("ingotThaumium") && OreDictionary.doesOreNameExist("gemAmber")) {
            GameRegistry.registerItem(thaumiumWings, "ItemThaumiumWing");
            addWingRecipe(4, thaumiumWings, "ingotThaumium", "gemAmber");
        }

        bronzeWings = new ItemWing(ItemArmor.ArmorMaterial.IRON, new IcarusWing("BronzeWing", 384, 132, 0.5, 0.9, -0.2, -0.4, 0.5));
        if (ConfigHandler.enableOreDictCompat && OreDictionary.doesOreNameExist("ingotBronze")) {
            GameRegistry.registerItem(bronzeWings, "ItemBronzeWings");
            addWingRecipe(5, bronzeWings, "ingotBronze", Items.feather);
        }

        leadstoneWings = new ItemWingRF(ItemArmor.ArmorMaterial.IRON, new IcarusWing("LeadstoneWing", 131072, 512, 0.6, 0.75, -0.3, -0.4, 0.3));
        if (ConfigHandler.enableTECompat && ModItemGetter.leadstoneCapacitor != null && OreDictionary.doesOreNameExist("ingotLead")) {
            GameRegistry.registerItem(leadstoneWings, "ItemLeadstoneWings");
            addWingRecipe(6, leadstoneWings, "ingotLead", ModItemGetter.leadstoneCapacitor);
        }

        electrumWings = new ItemWingRF(ELECTRUM, new IcarusWing("ElectrumWing", 393216, 1024, 0.7, 0.55, -0.3, -0.4, 0.6));
        if (ConfigHandler.enableTECompat && ModItemGetter.redstoneCapacitor != null && OreDictionary.doesOreNameExist("ingotElectrum")) {
            GameRegistry.registerItem(electrumWings, "ItemElectrumWings");
            addWingRecipe(7, electrumWings, "ingotElectrum", ModItemGetter.redstoneCapacitor);
        }

        enderiumWings = new ItemWingRF(ENDERIUM, new IcarusWing("EnderiumWing", 786432, 2048, 0.75, 0.35, -0.2, -0.3, 0.8));
        if (ConfigHandler.enableTECompat && ModItemGetter.resonantCapacitor != null && OreDictionary.doesOreNameExist("ingotEnderium")) {
            GameRegistry.registerItem(enderiumWings, "ItemEnderiumWings");
            addWingRecipe(8, enderiumWings, "ingotEnderium", ModItemGetter.resonantCapacitor);
        }

        voidMetalWings = new ItemWingThaumcraft.ItemWingVoidMetal(ThaumcraftApi.armorMatVoid, new IcarusWing("VoidMetalWing", 768, 384, 0.7, 0.4, -0.1, -0.1, 0.75));
        if (ConfigHandler.enableThaumcraftCompat && OreDictionary.doesOreNameExist("ingotVoid") && OreDictionary.doesOreNameExist("shardEntropy")) {
            GameRegistry.registerItem(voidMetalWings, "ItemVoidMetalWings");
            addWingRecipe(9, voidMetalWings, "ingotVoid", "shardEntropy");
        }
    }

    public static void addWingRecipe(int singleWingMeta, ItemWing output, Item item1, Item item2) {
        GameRegistry.addShapedRecipe(new ItemStack(singleWings, 1, singleWingMeta), "XX ", "XYY", " XX", 'X', item1, 'Y', item2);
        GameRegistry.addShapelessRecipe(new ItemStack(output), new ItemStack(singleWings, 1, singleWingMeta), new ItemStack(singleWings, 1, singleWingMeta));
    }

    public static void addWingRecipe(int singleWingMeta, ItemWing output, String item1, Item item2) {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(singleWings, 1, singleWingMeta), "XX ", "XYY", " XX", 'X', item1, 'Y', item2));
        GameRegistry.addShapelessRecipe(new ItemStack(output), new ItemStack(singleWings, 1, singleWingMeta), new ItemStack(singleWings, 1, singleWingMeta));
    }

    public static void addWingRecipe(int singleWingMeta, ItemWing output, String item1, ItemStack item2) {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(singleWings, 1, singleWingMeta), "XX ", "XYY", " XX", 'X', item1, 'Y', item2));
        GameRegistry.addShapelessRecipe(new ItemStack(output), new ItemStack(singleWings, 1, singleWingMeta), new ItemStack(singleWings, 1, singleWingMeta));
    }

    public static void addWingRecipe(int singleWingMeta, ItemWing output, String item1, String item2) {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(singleWings, 1, singleWingMeta), "XX ", "XYY", " XX", 'X', item1, 'Y', item2));
        GameRegistry.addShapelessRecipe(new ItemStack(output), new ItemStack(singleWings, 1, singleWingMeta), new ItemStack(singleWings, 1, singleWingMeta));
    }
}
