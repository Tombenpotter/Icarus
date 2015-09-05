package tombenpotter.icarus.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import thaumcraft.api.ThaumcraftApi;
import tombenpotter.icarus.common.items.*;
import tombenpotter.icarus.util.IcarusWing;
import tombenpotter.icarus.util.ModItemGetter;

import java.util.ArrayList;

public class IcarusItems {

    public static ArrayList<String> wingNames = new ArrayList<String>();
    //Last damage value used: 9
    public static ItemSingleWing singleWings;
    public static ItemWing cardboardWings, featherWings, ironWings, goldDiamondWings, bronzeWings;
    public static ItemWingThaumcraft thaumiumWings, voidMetalWings;
    public static ItemWingRF leadstoneWings, electrumWings, enderiumWings;

    public static ItemArmor.ArmorMaterial CLOTH = EnumHelper.addArmorMaterial("ICARUS_CLOTH", 5, new int[]{1, 3, 2, 1}, 15);
    public static final ItemArmor.ArmorMaterial ELECTRUM = EnumHelper.addArmorMaterial("ICARUS_ELECTRUM", 100, new int[]{3, 8, 6, 3}, 20);
    public static final ItemArmor.ArmorMaterial ENDERIUM = EnumHelper.addArmorMaterial("ICARUS_ENDERIUM", 100, new int[]{4, 9, 7, 4}, 30);

    public static void registerItems() {
        ModItemGetter.load();

        singleWings = new ItemSingleWing();
        GameRegistry.registerItem(singleWings, "ItemSingleWings");

        cardboardWings = new ItemWing(CLOTH, new IcarusWing("CardboardWing", 64, 96, 0.25, 0.95, -0.25, -0.5, 0.8));
        GameRegistry.registerItem(cardboardWings, "ItemCardboardWings");
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(singleWings, 1, 0), "XX ", "XYY", " XX", 'X', "logWood", 'Y', Items.paper));
        GameRegistry.addShapelessRecipe(new ItemStack(cardboardWings), new ItemStack(singleWings, 1, 0), new ItemStack(singleWings, 1, 0));

        featherWings = new ItemWing(ItemArmor.ArmorMaterial.CHAIN, new IcarusWing("FeatherWing", 96, 128, 0.35, 0.9, -0.15, -0.3, 0.8));
        GameRegistry.registerItem(featherWings, "ItemFeatherWings");
        GameRegistry.addShapedRecipe(new ItemStack(singleWings, 1, 1), "XX ", "XYY", " XX", 'X', Items.bone, 'Y', Items.feather);
        GameRegistry.addShapelessRecipe(new ItemStack(featherWings), new ItemStack(singleWings, 1, 1), new ItemStack(singleWings, 1, 1));

        ironWings = new ItemWing(ItemArmor.ArmorMaterial.IRON, new IcarusWing("IronWing", 256, 132, 0.5, 0.85, -0.2, -0.4, 0.5));
        GameRegistry.registerItem(ironWings, "ItemIronWings");
        GameRegistry.addShapedRecipe(new ItemStack(singleWings, 1, 2), "XX ", "XYY", " XX", 'X', Items.iron_ingot, 'Y', Items.feather);
        GameRegistry.addShapelessRecipe(new ItemStack(ironWings), new ItemStack(singleWings, 1, 2), new ItemStack(singleWings, 1, 2));

        goldDiamondWings = new ItemWing(ItemArmor.ArmorMaterial.GOLD, new IcarusWing("DiamondGoldWing", 1024, 196, 0.6, 0.7, -0.2, -0.3, 0.6));
        GameRegistry.registerItem(goldDiamondWings, "ItemGoldDiamondWings");
        GameRegistry.addShapedRecipe(new ItemStack(singleWings, 1, 3), "XX ", "XYY", " XX", 'X', Items.diamond, 'Y', Items.gold_ingot);
        GameRegistry.addShapelessRecipe(new ItemStack(goldDiamondWings), new ItemStack(singleWings, 1, 3), new ItemStack(singleWings, 1, 3));

        thaumiumWings = new ItemWingThaumcraft(ThaumcraftApi.armorMatThaumium, new IcarusWing("ThaumiumWing", 512, 256, 0.65, 0.55, -0.1, -0.2, 0.7));
        if (OreDictionary.doesOreNameExist("ingotThaumium") && OreDictionary.doesOreNameExist("gemAmber")) {
            GameRegistry.registerItem(thaumiumWings, "ItemThaumiumWing");
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(singleWings, 1, 4), "XX ", "XYY", " XX", 'X', "ingotThaumium", 'Y', "gemAmber"));
            GameRegistry.addShapelessRecipe(new ItemStack(thaumiumWings), new ItemStack(singleWings, 1, 4), new ItemStack(singleWings, 1, 4));
        }

        voidMetalWings = new ItemWingThaumcraft.ItemWingVoidMetal(ThaumcraftApi.armorMatVoid, new IcarusWing("VoidMetalWing", 768, 384, 0.7, 0.4, -0.1, -0.1, 0.75));
        if (OreDictionary.doesOreNameExist("ingotVoid") && OreDictionary.doesOreNameExist("shardEntropy")) {
            GameRegistry.registerItem(voidMetalWings, "ItemVoidMetalWings");
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(singleWings, 1, 5), "XX ", "XYY", " XX", 'X', "ingotVoid", 'Y', "shardEntropy"));
            GameRegistry.addShapelessRecipe(new ItemStack(voidMetalWings), new ItemStack(singleWings, 1, 5), new ItemStack(singleWings, 1, 5));
        }

        bronzeWings = new ItemWing(ItemArmor.ArmorMaterial.IRON, new IcarusWing("BronzeWing", 384, 132, 0.5, 0.85, -0.2, -0.4, 0.5));
        if (OreDictionary.doesOreNameExist("ingotBronze")) {
            GameRegistry.registerItem(bronzeWings, "ItemBronzeWings");
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(singleWings, 1, 6), "XX ", "XYY", " XX", 'X', "ingotBronze", 'Y', Items.feather));
            GameRegistry.addShapelessRecipe(new ItemStack(bronzeWings), new ItemStack(singleWings, 1, 6), new ItemStack(singleWings, 1, 6));
        }

        leadstoneWings = new ItemWingRF(ItemArmor.ArmorMaterial.IRON, new IcarusWing("LeadstoneWing", 131072, 512, 0.6, 0.65, -0.3, -0.4, 0.3));
        if (ModItemGetter.leadstoneCapacitor != null && OreDictionary.doesOreNameExist("ingotLead")) {
            GameRegistry.registerItem(leadstoneWings, "ItemLeadstoneWings");
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(singleWings, 1, 7), "XX ", "XYY", " XX", 'X', "ingotLead", 'Y', ModItemGetter.leadstoneCapacitor));
            GameRegistry.addShapelessRecipe(new ItemStack(leadstoneWings), new ItemStack(singleWings, 1, 7), new ItemStack(singleWings, 1, 7));
        }

        electrumWings = new ItemWingRF(ELECTRUM, new IcarusWing("ElectrumWing", 393216, 1024, 0.7, 0.35, -0.3, -0.4, 0.6));
        if (ModItemGetter.redstoneCapacitor != null && OreDictionary.doesOreNameExist("ingotElectrum")) {
            GameRegistry.registerItem(electrumWings, "ItemElectrumWings");
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(singleWings, 1, 8), "XX ", "XYY", " XX", 'X', "ingotElectrum", 'Y', ModItemGetter.redstoneCapacitor));
            GameRegistry.addShapelessRecipe(new ItemStack(electrumWings), new ItemStack(singleWings, 1, 8), new ItemStack(singleWings, 1, 8));
        }

        enderiumWings = new ItemWingRF(ENDERIUM, new IcarusWing("EnderiumWing", 786432, 2048, 0.75, 0.25, -0.2, -0.3, 0.8));
        if (ModItemGetter.resonantCapacitor != null && OreDictionary.doesOreNameExist("ingotEnderium")) {
            GameRegistry.registerItem(enderiumWings, "ItemEnderiumWings");
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(singleWings, 1, 9), "XX ", "XYY", " XX", 'X', "ingotEnderium", 'Y', ModItemGetter.resonantCapacitor));
            GameRegistry.addShapelessRecipe(new ItemStack(enderiumWings), new ItemStack(singleWings, 1, 9), new ItemStack(singleWings, 1, 9));
        }
    }
}
