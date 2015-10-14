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
    private static final int wingPos = 0;
    private static final int armorPos = 3;

    public ArmorWingRecipe(ItemWing wing) {
        super(wing, "X  ", "Y  ", "   ", 'X', "", 'Y', wing);
        this.itemWing = new ItemStack(wing);

        this.input = new Object[9];
        input[wingPos] = this.itemWing;
        input[armorPos] = IcarusHelper.armorList;
    }

    @Override
    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        ItemStack wingStack = inventoryCrafting.getStackInSlot(wingPos);
        ItemStack armorStack = inventoryCrafting.getStackInSlot(armorPos);

        return armorStack != null && wingStack != null && armorStack.getItem() instanceof ItemArmor && ((ItemArmor) armorStack.getItem()).armorType == 1 && wingStack.isItemEqual(itemWing);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        ItemStack wingStack = inventoryCrafting.getStackInSlot(wingPos);
        ItemStack armorStack = inventoryCrafting.getStackInSlot(armorPos);
        ItemStack result = null;

        if (armorStack != null && wingStack != null && armorStack.getItem() instanceof ItemArmor && ((ItemArmor) armorStack.getItem()).armorType == 1 && wingStack.isItemEqual(itemWing)) {
            result = wingStack.copy();

            if (wingStack.hasTagCompound()) {
                result.setTagCompound(wingStack.getTagCompound());
            } else if (!wingStack.hasTagCompound() && result.stackTagCompound == null) {
                result.setTagCompound(new NBTTagCompound());
            }

            NBTTagCompound tag = new NBTTagCompound();
            tag = armorStack.writeToNBT(tag);
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
