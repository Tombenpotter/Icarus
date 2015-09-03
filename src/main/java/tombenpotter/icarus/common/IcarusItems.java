package tombenpotter.icarus.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tombenpotter.icarus.common.items.ItemRFWing;
import tombenpotter.icarus.common.items.ItemSingleWing;
import tombenpotter.icarus.common.items.ItemWing;
import tombenpotter.icarus.common.items.Wing;

import java.util.ArrayList;

public class IcarusItems {

    public static ArrayList<String> wingNames = new ArrayList<String>();

    public static ItemSingleWing singleWings;

    public static ItemWing cardboardWings, featherWings, ironWings, goldDiamondWings, thaumiumWings, bronzeWings;

    public static ItemRFWing leadstoneWings, electrumWings, enderiumWings;

    public static ItemArmor.ArmorMaterial THAUMIUM = EnumHelper.addArmorMaterial("ICARUS_THAUMIUM", 25, new int[]{2, 6, 5, 2}, 25);
    public static ItemArmor.ArmorMaterial CLOTH = EnumHelper.addArmorMaterial("ICARUS_CLOTH", 5, new int[]{1, 3, 2, 1}, 15);
    public static final ItemArmor.ArmorMaterial ELECTRUM = EnumHelper.addArmorMaterial("ICARUS_ELECTRUM", 100, new int[]{3, 8, 6, 3}, 20);
    public static final ItemArmor.ArmorMaterial ENDERIUM = EnumHelper.addArmorMaterial("ICARUS_ENDERIUM", 100, new int[]{4, 9, 7, 4}, 30);

    public static void registerItems() {
        ItemStealer.load();

        singleWings = new ItemSingleWing();
        GameRegistry.registerItem(singleWings, "ItemSingleWings");

        cardboardWings = new ItemWing(CLOTH, new Wing("CardboardWing", 64, 96, 0.25, 0.95, -0.25, -0.5, 0.8));
        GameRegistry.registerItem(cardboardWings, "ItemCardboardWings");
        GameRegistry.addShapedRecipe(new ItemStack(singleWings, 1, 0), "XX ", "XYY", " XX", 'X', Items.stick, 'Y', Items.paper);
        GameRegistry.addShapelessRecipe(new ItemStack(cardboardWings), new ItemStack(singleWings, 1, 0), new ItemStack(singleWings, 1, 0));

        featherWings = new ItemWing(ItemArmor.ArmorMaterial.CHAIN, new Wing("FeatherWing", 96, 128, 0.35, 0.9, -0.15, -0.3, 0.8));
        GameRegistry.registerItem(featherWings, "ItemFeatherWings");
        GameRegistry.addShapedRecipe(new ItemStack(singleWings, 1, 1), "XX ", "XYY", " XX", 'X', Items.bone, 'Y', Items.feather);
        GameRegistry.addShapelessRecipe(new ItemStack(featherWings), new ItemStack(singleWings, 1, 1), new ItemStack(singleWings, 1, 1));

        ironWings = new ItemWing(ItemArmor.ArmorMaterial.IRON, new Wing("IronWing", 256, 132, 0.5, 0.85, -0.2, -0.4, 0.5));
        GameRegistry.registerItem(ironWings, "ItemIronWings");
        GameRegistry.addShapedRecipe(new ItemStack(singleWings, 1, 2), "XX ", "XYY", " XX", 'X', Items.iron_ingot, 'Y', Items.feather);
        GameRegistry.addShapelessRecipe(new ItemStack(ironWings), new ItemStack(singleWings, 1, 2), new ItemStack(singleWings, 1, 2));

        goldDiamondWings = new ItemWing(ItemArmor.ArmorMaterial.GOLD, new Wing("DiamondGoldWing", 1024, 196, 0.6, 0.7, -0.2, -0.3, 0.6));
        GameRegistry.registerItem(goldDiamondWings, "ItemGoldDiamondWings");
        GameRegistry.addShapedRecipe(new ItemStack(singleWings, 1, 3), "XX ", "XYY", " XX", 'X', Items.diamond, 'Y', Items.gold_ingot);
        GameRegistry.addShapelessRecipe(new ItemStack(goldDiamondWings), new ItemStack(singleWings, 1, 3), new ItemStack(singleWings, 1, 3));

        thaumiumWings = new ItemWing(THAUMIUM, new Wing("ThaumiumWing", 512, 256, 0.65, 0.55, -0.1, -0.2, 0.7));
        if (OreDictionary.doesOreNameExist("ingotThaumium")) {
            GameRegistry.registerItem(thaumiumWings, "ItemThaumiumWing");
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(singleWings, 1, 4), "XX ", "XYY", " XX", 'X', "ingotThaumium", 'Y', "ingotThaumium"));
            GameRegistry.addShapelessRecipe(new ItemStack(thaumiumWings), new ItemStack(singleWings, 1, 4), new ItemStack(singleWings, 1, 4));
        }

        bronzeWings = new ItemWing(ItemArmor.ArmorMaterial.IRON, new Wing("BronzeWing", 384, 132, 0.5, 0.85, -0.2, -0.4, 0.5));
        if (OreDictionary.doesOreNameExist("ingotBronze")) {
            GameRegistry.registerItem(bronzeWings, "ItemBronzeWings");
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(singleWings, 1, 5), "XX ", "XYY", " XX", 'X', "ingotBronze", 'Y', Items.feather));
            GameRegistry.addShapelessRecipe(new ItemStack(bronzeWings), new ItemStack(singleWings, 1, 5), new ItemStack(singleWings, 1, 5));
        }

        leadstoneWings = new ItemRFWing(ItemArmor.ArmorMaterial.IRON, new Wing("LeadstoneWing", 131072, 512, 0.6, 0.65, -0.3, -0.4, 0.3));
        if (ItemStealer.leadstoneCapacitor != null) {
            GameRegistry.registerItem(leadstoneWings, "ItemLeadstoneWings");
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(singleWings, 1, 6), "XX ", "XYY", " XX", 'X', "ingotLead", 'Y', ItemStealer.leadstoneCapacitor));
            GameRegistry.addShapelessRecipe(new ItemStack(leadstoneWings), new ItemStack(singleWings, 1, 6), new ItemStack(singleWings, 1, 6));
        }


        electrumWings = new ItemRFWing(ELECTRUM, new Wing("ElectrumWing", 393216, 1024, 0.7, 0.35, -0.3, -0.4, 0.6));
        if (ItemStealer.redstoneCapacitor != null) {
            GameRegistry.registerItem(electrumWings, "ItemElectrumWings");
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(singleWings, 1, 7), "XX ", "XYY", " XX", 'X', "ingotElectrum", 'Y', ItemStealer.redstoneCapacitor));
            GameRegistry.addShapelessRecipe(new ItemStack(electrumWings), new ItemStack(singleWings, 1, 7), new ItemStack(singleWings, 1, 7));
        }

        enderiumWings = new ItemRFWing(ENDERIUM, new Wing("EnderiumWing", 786432, 2048, 0.8, 0.15, -0.2, -0.3, 0.8));
        if (ItemStealer.resonantCapacitor != null) {
            GameRegistry.registerItem(enderiumWings, "ItemEnderiumWings");
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(singleWings, 1, 8), "XX ", "XYY", " XX", 'X', "ingotEnderium", 'Y', ItemStealer.resonantCapacitor));
            GameRegistry.addShapelessRecipe(new ItemStack(enderiumWings), new ItemStack(singleWings, 1, 8), new ItemStack(singleWings, 1, 8));
        }
    }
}
