package tombenpotter.icarus.common.util;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tombenpotter.icarus.Icarus;
import tombenpotter.icarus.common.items.ItemWing;

public class ArmorWingRecipe extends ShapedOreRecipe {

    static {
        RecipeSorter.register(Icarus.modid + ":ArmorWingRecipe", ArmorWingRecipe.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped");
    }

    public ArmorWingRecipe(Item result, Object... recipe) {
        super(result, recipe);
    }

    public ArmorWingRecipe(ItemStack result, Object... recipe) {
        super(result, recipe);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        ItemStack armorStack = inventoryCrafting.getStackInSlot(4);
        ItemStack wingStack = inventoryCrafting.getStackInSlot(7);
        ItemStack result = wingStack.copy();

        if (armorStack != null && armorStack.getItem() instanceof ItemArmor) {

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
}
