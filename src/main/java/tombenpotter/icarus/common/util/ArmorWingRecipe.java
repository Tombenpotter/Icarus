package tombenpotter.icarus.common.util;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tombenpotter.icarus.Icarus;
import tombenpotter.icarus.api.IcarusConstants;
import tombenpotter.icarus.common.items.ItemWing;

public class ArmorWingRecipe extends ShapedOreRecipe {

    static {
        RecipeSorter.register(Icarus.modid + ":ArmorWingRecipe", ArmorWingRecipe.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped");
    }

    public ItemStack itemWing;
    public Object[] input;

    public ArmorWingRecipe(ItemWing wing) {
        super(wing, "   ", " X ", " Y ", 'X', "", 'Y', wing);
        this.itemWing = new ItemStack(wing);

        this.input = new Object[9];
        input[4] = this.itemWing;
        input[7] = IcarusHelper.armorList;
    }

    @Override
    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        ItemStack wingStack = inventoryCrafting.getStackInSlot(4);
        ItemStack armorStack = inventoryCrafting.getStackInSlot(7);

        return armorStack != null && wingStack != null && armorStack.getItem() instanceof ItemArmor && wingStack.isItemEqual(itemWing);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        ItemStack wingStack = inventoryCrafting.getStackInSlot(4);
        ItemStack armorStack = inventoryCrafting.getStackInSlot(7);
        ItemStack result = null;

        if (armorStack != null && wingStack != null && armorStack.getItem() instanceof ItemArmor && wingStack.isItemEqual(itemWing)) {
            result = wingStack.copy();

            if (wingStack.stackTagCompound != null) {
                result.setTagCompound(wingStack.getTagCompound());
            }
            if (result.stackTagCompound == null) {
                result.setTagCompound(new NBTTagCompound());
            }

            NBTTagCompound tag = new NBTTagCompound();
            armorStack.writeToNBT(tag);
            result.stackTagCompound.setTag(IcarusConstants.NBT_ITEMSTACK, tag);
        }
        return result;
    }

    @Override
    public int getRecipeSize() {
        return input.length;
    }

    @Override
    public Object[] getInput() {
        return this.input;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return itemWing.copy();
    }
}
