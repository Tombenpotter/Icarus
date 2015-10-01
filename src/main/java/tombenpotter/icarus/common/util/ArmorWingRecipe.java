package tombenpotter.icarus.common.util;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.RecipeSorter;
import tombenpotter.icarus.Icarus;
import tombenpotter.icarus.ConfigHandler;
import tombenpotter.icarus.common.IcarusItems;
import tombenpotter.icarus.common.items.ItemWing;
import tombenpotter.icarus.common.util.cofh.StringHelper;
import tombenpotter.icarus.common.util.LogHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static net.minecraftforge.oredict.RecipeSorter.Category.SHAPED;


public class ArmorWingRecipe implements IRecipe {

    private Date date = new Date();
    private String formattedDate = new SimpleDateFormat("dd:MM:yyyy_HH:mm:ss:SSS").format(Calendar.getInstance().getTime());

    static {
        RecipeSorter.register(Icarus.modid + ":ArmorWingRecipe", ArmorWingRecipe.class, SHAPED, "after:minecraft:shapeless");
    }

    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        boolean hasArmor = false;
        boolean hasWings = false;
        ItemStack wingStack = null;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack item = inventory.getStackInSlot(i);
            for (ItemStack wings : IcarusItems.wingArray)
                if (item != null) {
                    if (item.isItemEqual(wings)) {
                        if (item.getItem() instanceof ItemWing) {
                            hasWings = true;
                            wingStack = item;
                        }
                    }
            } 
            for (ItemStack armors : IcarusItems.armorArray) {
                if (item != null) {
                    if (item.isItemEqual(armors)) {
                        if (item.getItem() instanceof ItemArmor) {
                            hasArmor = true;
                        }
                    } 
                } 
            }
        }
        if (hasArmor && hasWings) {
            return hasArmor && hasWings;
        } else if (wingStack != null && wingStack.getItem() instanceof ItemWing && !hasArmor && ConfigHandler.shapelessWings) {
            if (wingStack.stackTagCompound == null) {
                return false;
            }
            if (wingStack.stackTagCompound.hasKey(ItemWing.NBT_ITEMSTACK) == true) {
                return hasWings;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        boolean hasArmor = false;
        boolean hasWings = false;
        ItemStack armorStack = null;
        ItemStack wingStack = null;
        ItemStack result = null;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack item = inventory.getStackInSlot(i);
            for (ItemStack wings : IcarusItems.wingArray)
                if (item != null) {
                    if (item.isItemEqual(wings)) {
                        if (item.getItem() instanceof ItemWing) {
                            hasWings = true;
                            wingStack = item;
                        }
                    }
            } 
            for (ItemStack armors : IcarusItems.armorArray) {
                if (item != null) {
                    if (item.isItemEqual(armors)) {
                        if (item.getItem() instanceof ItemArmor) {
                            hasArmor = true;
                            armorStack = item;
                        }
                    } 
                } 
            }
        }
        
        if (wingStack != null && wingStack.getItem() instanceof ItemWing && !hasArmor  && ConfigHandler.shapelessWings) {
            if (wingStack.stackTagCompound.hasKey(ItemWing.NBT_ITEMSTACK)) {
                result = wingStack.copy();
                if (result.hasTagCompound() && result.stackTagCompound.hasKey(ItemWing.NBT_ITEMSTACK)) {
                    LogHelper.info("[Icarus] compound was " + result.stackTagCompound + " " + formattedDate);
                    LogHelper.info("[Icarus] removing tag from compound " + formattedDate);
                    result.stackTagCompound.removeTag(ItemWing.NBT_ITEMSTACK);
                }
                LogHelper.info("[Icarus] compound is: " + result.stackTagCompound + " " + formattedDate);
                LogHelper.info("[Icarus] result is: " + result + " " + formattedDate);
                return result;
            } else {
                result = null;
            }
        }
        if (!hasWings && !hasArmor) {
            return result;
        } 
        result = wingStack.copy();
        if (armorStack != null && armorStack.getItem() instanceof ItemArmor) {
            if (result.stackTagCompound == null) {
                result.setTagCompound(new NBTTagCompound());
            }
            result.setTagCompound(result.getTagCompound());
            NBTTagCompound tag = new NBTTagCompound();
            armorStack.writeToNBT(tag);
            result.stackTagCompound.setTag(ItemWing.NBT_ITEMSTACK, tag);
        }
        return result;
    }

    @Override
    public int getRecipeSize()
    {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return null;
    }
}
