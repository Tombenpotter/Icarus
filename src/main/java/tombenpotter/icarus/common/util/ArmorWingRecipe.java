package tombenpotter.icarus.common.util;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.RecipeSorter;
import tombenpotter.icarus.Icarus;
import tombenpotter.icarus.common.IcarusItems;
import tombenpotter.icarus.common.items.ItemWing;

public class ArmorWingRecipe implements IRecipe {

    static {
        RecipeSorter.register(Icarus.modid + ":ArmorWingRecipe", ArmorWingRecipe.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped");
    }

    @Override
    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        ItemStack armorStack = inventoryCrafting.getStackInSlot(4);
        ItemStack wingStack = inventoryCrafting.getStackInSlot(7);

        if (armorStack != null && wingStack != null && armorStack.getItem() instanceof ItemArmor && wingStack.getItem() instanceof ItemWing) {
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        ItemStack armorStack = inventoryCrafting.getStackInSlot(4);
        ItemStack wingStack = inventoryCrafting.getStackInSlot(7);
        ItemStack result = null;

        if (armorStack != null && wingStack != null && armorStack.getItem() instanceof ItemArmor && wingStack.getItem() instanceof ItemWing) {
            result = wingStack.copy();

            if (wingStack.stackTagCompound == null) {
                wingStack.setTagCompound(new NBTTagCompound());
            }
            result.setTagCompound(wingStack.getTagCompound());

            NBTTagCompound tag = new NBTTagCompound();
            armorStack.writeToNBT(tag);
            wingStack.stackTagCompound.setTag(ItemWing.NBT_ITEMSTACK, tag);
        }
        return result;
    }

    @Override
    public int getRecipeSize() {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(IcarusItems.cardboardWings);
    }
}
