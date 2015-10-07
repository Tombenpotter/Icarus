package tombenpotter.icarus.common;

import cpw.mods.fml.common.registry.GameData;
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
import tombenpotter.icarus.common.items.*;
import tombenpotter.icarus.common.util.ArmorWingRecipe;
import tombenpotter.icarus.common.util.IcarusUtil;
import tombenpotter.icarus.common.util.IcarusWing;
import tombenpotter.icarus.common.util.ModItemGetter;
import vazkii.botania.api.BotaniaAPI;

import java.util.ArrayList;

public class IcarusItems {

    public static ArrayList<String> wingNames = new ArrayList<String>();
    //Last damage value used: 27
    public static ItemSingleWing singleWings;
    public static ItemWingVanilla cardboardWings, featherWings, ironWings, goldDiamondWings, bronzeWings;
    public static ItemWingThaumcraft thaumiumWings, voidMetalWings;
    public static ItemWingRF leadstoneWings, electrumWings, enderiumWings;
    public static ItemWingBotania livingwoodWings, manasteelWings, terrasteelWings, elementiumWings;
    public static ItemWingRF conductiveWings, energeticWings, vibrantWings;
    public static ItemWingAuraCascade angelsteelWings;

    //Equivalent of the cloth material, without the coloring stuffs
    public static ItemArmor.ArmorMaterial CLOTH = addArmorMaterialWithRepair("ICARUS_CLOTH", 5, new int[]{1, 3, 2, 1}, 15, Items.string);
    public static final ItemArmor.ArmorMaterial ELECTRUM = EnumHelper.addArmorMaterial("ICARUS_ELECTRUM", 100, new int[]{3, 8, 6, 3}, 20);
    public static final ItemArmor.ArmorMaterial ENDERIUM = EnumHelper.addArmorMaterial("ICARUS_ENDERIUM", 100, new int[]{4, 9, 7, 4}, 30);

    public static void registerItems() {
        ModItemGetter.load();
        IcarusItems.addArmorsToList();

        singleWings = new ItemSingleWing();
        GameRegistry.registerItem(singleWings, "ItemSingleWings");

        cardboardWings = new ItemWingVanilla(CLOTH, new IcarusWing("CardboardWing", 64, 96, 0.25, 0.95, -0.25, -0.5, 0.8));
        registerWing(cardboardWings, "ItemCardboardWings");
        addWingRecipe(0, cardboardWings, "logWood", Items.paper);

        featherWings = new ItemWingVanilla(ItemArmor.ArmorMaterial.CHAIN, new IcarusWing("FeatherWing", 96, 128, 0.35, 0.65, -0.15, -0.3, 0.6));
        registerWing(featherWings, "ItemFeatherWings");
        addWingRecipe(1, featherWings, Items.bone, Items.feather);

        ironWings = new ItemWingVanilla(ItemArmor.ArmorMaterial.IRON, new IcarusWing("IronWing", 256, 132, 0.5, 0.9, -0.2, -0.4, 0.5));
        registerWing(ironWings, "ItemIronWings");
        addWingRecipe(2, ironWings, "ingotIron", Items.feather);

        goldDiamondWings = new ItemWingVanilla(ItemArmor.ArmorMaterial.GOLD, new IcarusWing("DiamondGoldWing", 1024, 196, 0.6, 0.7, -0.2, -0.3, 0.4));
        registerWing(goldDiamondWings, "ItemGoldDiamondWings");
        addWingRecipe(3, goldDiamondWings, "gemDiamond", "ingotGold");

        thaumiumWings = new ItemWingThaumcraft(ThaumcraftApi.armorMatThaumium, new IcarusWing("ThaumiumWing", 512, 256, 0.65, 0.52, -0.1, -0.2, 0.65));
        if (ConfigHandler.enableThaumcraftCompat && OreDictionary.doesOreNameExist("ingotThaumium") && OreDictionary.doesOreNameExist("gemAmber")) {
            registerWing(thaumiumWings, "ItemThaumiumWing");
            addWingRecipe(4, thaumiumWings, "ingotThaumium", "gemAmber");
        }

        bronzeWings = new ItemWingVanilla(ItemArmor.ArmorMaterial.IRON, new IcarusWing("BronzeWing", 384, 132, 0.5, 0.9, -0.2, -0.4, 0.5));
        if (ConfigHandler.enableOreDictCompat && OreDictionary.doesOreNameExist("ingotBronze")) {
            registerWing(bronzeWings, "ItemBronzeWings");
            addWingRecipe(5, bronzeWings, "ingotBronze", Items.feather);
        }

        leadstoneWings = new ItemWingRF(ItemArmor.ArmorMaterial.IRON, new IcarusWing("LeadstoneWing", 131072, 512, 0.6, 0.75, -0.3, -0.4, 0.7));
        if (ConfigHandler.enableTECompat && ModItemGetter.leadstoneCapacitor != null && OreDictionary.doesOreNameExist("ingotLead")) {
            registerWing(leadstoneWings, "ItemLeadstoneWings");
            addWingRecipe(6, leadstoneWings, "ingotLead", ModItemGetter.leadstoneCapacitor);
        }

        electrumWings = new ItemWingRF(ELECTRUM, new IcarusWing("ElectrumWing", 393216, 1024, 0.7, 0.55, -0.3, -0.4, 0.45));
        if (ConfigHandler.enableTECompat && ModItemGetter.redstoneCapacitor != null && OreDictionary.doesOreNameExist("ingotElectrum")) {
            registerWing(electrumWings, "ItemElectrumWings");
            addWingRecipe(7, electrumWings, "ingotElectrum", ModItemGetter.redstoneCapacitor);
        }

        enderiumWings = new ItemWingRF(ENDERIUM, new IcarusWing("EnderiumWing", 786432, 2048, 0.75, 0.35, -0.2, -0.3, 0.2));
        if (ConfigHandler.enableTECompat && ModItemGetter.resonantCapacitor != null && OreDictionary.doesOreNameExist("ingotEnderium")) {
            registerWing(enderiumWings, "ItemEnderiumWings");
            addWingRecipe(8, enderiumWings, "ingotEnderium", ModItemGetter.resonantCapacitor);
        }

        voidMetalWings = new ItemWingThaumcraft.ItemWingVoidMetal(ThaumcraftApi.armorMatVoid, new IcarusWing("VoidMetalWing", 768, 384, 0.7, 0.4, -0.1, -0.1, 0.25));
        if (ConfigHandler.enableThaumcraftCompat && OreDictionary.doesOreNameExist("ingotVoid") && OreDictionary.doesOreNameExist("shardEntropy")) {
            registerWing(voidMetalWings, "ItemVoidMetalWings");
            addWingRecipe(9, voidMetalWings, "ingotVoid", "shardEntropy");
        }

        livingwoodWings = new ItemWingBotania(CLOTH, new IcarusWing("LivingwoodWing", 128, 102, 0.28, 0.93, -0.25, -0.5, 0.8));
        if (ConfigHandler.enableBotaniaCompat && OreDictionary.doesOreNameExist("livingwood")) {
            registerWing(livingwoodWings, "ItemLivingwoodWings");
            addWingRecipe(10, livingwoodWings, "livingwood", Items.paper);
        }

        manasteelWings = new ItemWingBotania(BotaniaAPI.manasteelArmorMaterial, new IcarusWing("ManasteelWing", 448, 164, 0.53, 0.85, -0.18, -0.36, 0.5));
        if (ConfigHandler.enableBotaniaCompat && OreDictionary.doesOreNameExist("ingotManasteel")) {
            registerWing(manasteelWings, "ItemManasteelWings");
            addWingRecipe(11, manasteelWings, "ingotManasteel", Items.feather);
        }

        terrasteelWings = new ItemWingBotania(BotaniaAPI.terrasteelArmorMaterial, new IcarusWing("TerrasteelWing", 1792, 196, 0.7, 0.57, -0.18, -0.15, 0.35));
        if (ConfigHandler.enableBotaniaCompat && OreDictionary.doesOreNameExist("ingotTerrasteel") && OreDictionary.doesOreNameExist("shardPrismarine")) {
            registerWing(terrasteelWings, "ItemTerrasteelWings");
            addWingRecipe(12, terrasteelWings, "ingotTerrasteel", "shardPrismarine");
        }

        elementiumWings = new ItemWingBotania(BotaniaAPI.elementiumArmorMaterial, new IcarusWing("ElementiumWing", 896, 196, 0.55, 0.72, -0.15, -0.18, 0.3));
        if (ConfigHandler.enableBotaniaCompat && OreDictionary.doesOreNameExist("ingotElvenElementium") && OreDictionary.doesOreNameExist("elvenPixieDust")) {
            registerWing(elementiumWings, "ItemElementiumWings");
            addWingRecipe(13, elementiumWings, "ingotElvenElementium", "elvenPixieDust");
        }

        conductiveWings = new ItemWingRF(ItemArmor.ArmorMaterial.IRON, new IcarusWing("ConductiveWing", 100000, 256, 0.5, 0.8, -0.3, -0.3, 0.5));
        if (ConfigHandler.enableEIOCompat && ModItemGetter.basicCapacitor != null && OreDictionary.doesOreNameExist("ingotConductiveIron")) {
            registerWing(conductiveWings, "ItemConductiveWings");
            addWingRecipe(14, conductiveWings, "ingotConductiveIron", ModItemGetter.basicCapacitor);
        }

        energeticWings = new ItemWingRF(ItemArmor.ArmorMaterial.DIAMOND, new IcarusWing("EnergeticWing", 200000, 1024, 0.65, 0.55, -0.17, -0.21, 0.4));
        if (ConfigHandler.enableEIOCompat && ModItemGetter.doubleLayeredCapacitor != null && OreDictionary.doesOreNameExist("ingotEnergeticAlloy")) {
            registerWing(energeticWings, "ItemEnergeticWings");
            addWingRecipe(15, energeticWings, "ingotEnergeticAlloy", ModItemGetter.doubleLayeredCapacitor);
        }

        vibrantWings = new ItemWingRF(ELECTRUM, new IcarusWing("VibrantWing", 500000, 2048, 0.73, 0.29, -0.12, -0.15, 0.2));
        if (ConfigHandler.enableEIOCompat && ModItemGetter.octadicCapacitor != null && OreDictionary.doesOreNameExist("ingotPhasedGold")) {
            registerWing(vibrantWings, "ItemVibrantWings");
            addWingRecipe(16, vibrantWings, "ingotPhasedGold", ModItemGetter.octadicCapacitor);
        }
    }

    public static void registerItemsInInitBecausePixlepix() {
        ModItemGetter.auraCascadeIngotBecausePixlepixIsDuh();

        angelsteelWings = new ItemWingAuraCascade(ItemArmor.ArmorMaterial.GOLD, new IcarusWing("AngelsteelWing", 512, 192, 0.36, 0.85, -0.3, -0.33, 0.84));
        if (ConfigHandler.enableACCompat && !ModItemGetter.angelsteelIngots.isEmpty()) {
            registerWing(angelsteelWings, "ItemAngelsteelWings");
            for (int i = 0; i < ModItemGetter.angelsteelIngots.size(); i++) {
                addWingRecipe(17 + i, ItemWingAuraCascade.angelsteelWings.get(i), ModItemGetter.angelsteelIngots.get(i), Items.feather);
            }
        }
    }

    public static void registerWing(ItemWing itemWing, String name) {
        GameRegistry.registerItem(itemWing, name);
        IcarusUtil.wingList.add(itemWing);
        GameRegistry.addRecipe(new ArmorWingRecipe(itemWing));
    }

    /*
    * item1 and item2 are only to be either Items, ItemStacks, or Strings.
    * Method is private to avoid stupidity.
     */
    private static void addWingRecipe(int singleWingMeta, ItemWing output, Object item1, Object item2) {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(singleWings, 1, singleWingMeta), "XX ", "XYY", " XX", 'X', item1, 'Y', item2));
        GameRegistry.addShapelessRecipe(new ItemStack(output), new ItemStack(singleWings, 1, singleWingMeta), new ItemStack(singleWings, 1, singleWingMeta));
    }

    /*
    * item1 and item2 are only to be either Items, ItemStacks, or Strings.
    * Method is private to avoid stupidity.
     */
    private static void addWingRecipe(int singleWingMeta, ItemStack output, Object item1, Object item2) {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(singleWings, 1, singleWingMeta), "XX ", "XYY", " XX", 'X', item1, 'Y', item2));
        GameRegistry.addShapelessRecipe(output, new ItemStack(singleWings, 1, singleWingMeta), new ItemStack(singleWings, 1, singleWingMeta));
    }

    public static ItemArmor.ArmorMaterial addArmorMaterialWithRepair(String name, int durability, int[] reductionAmounts, int enchantability, Item repairItem) {
        ItemArmor.ArmorMaterial material = EnumHelper.addArmorMaterial(name, durability, reductionAmounts, enchantability);
        material.customCraftingMaterial = repairItem;
        return material;
    }

    public static ItemArmor.ArmorMaterial addArmorMaterialWithRepair(String name, int durability, int[] reductionAmounts, int enchantability, ItemStack repairItem) {
        return addArmorMaterialWithRepair(name, durability, reductionAmounts, enchantability, repairItem.getItem());
    }

    public static ItemArmor.ArmorMaterial addArmorMaterialWithRepair(String name, int durability, int[] reductionAmounts, int enchantability, String repairItem) {
        return addArmorMaterialWithRepair(name, durability, reductionAmounts, enchantability, OreDictionary.getOres(repairItem).get(0).getItem());
    }

    public static void addArmorsToList() {
        for (Item item : GameData.getItemRegistry().typeSafeIterable()) {
            if (item instanceof ItemArmor && ((ItemArmor) item).armorType == 1) {
                IcarusUtil.armorList.add(new ItemStack(item));
            }
        }
    }
}
