package tombenpotter.icarus.common.util;

import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import tombenpotter.icarus.api.IcarusConstants;
import tombenpotter.icarus.common.items.ItemWing;

import java.util.ArrayList;

public class DyeWingRecipe implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        ItemStack stack = null;
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();

        for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
            ItemStack craftingStack = inventoryCrafting.getStackInSlot(i);

            if (craftingStack != null) {
                if (craftingStack.getItem() instanceof ItemWing) {
                    stack = craftingStack;
                } else if (craftingStack.getItem() instanceof ItemDye) {
                    arrayList.add(craftingStack);
                }
            }
        }

        return stack != null && !arrayList.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        ItemStack wingStack = null;
        int[] colors = {0, 0, 0};
        int dyeCount = 0;
        int calculated = 0;

        for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
            ItemStack craftingStack = inventoryCrafting.getStackInSlot(i);

            if (craftingStack != null) {
                if (craftingStack.getItem() instanceof ItemWing) {
                    if (wingStack != null) {
                        return null;
                    }

                    wingStack = craftingStack.copy();

                    if (wingStack.hasTagCompound() && wingStack.stackTagCompound.hasKey(IcarusConstants.NBT_COLOR)) {
                        int currentColor = IcarusHelper.getWingColor(wingStack);
                        float red = (float) (currentColor >> 16 & 255) / 255.0F;
                        float green = (float) (currentColor >> 8 & 255) / 255.0F;
                        float blue = (float) (currentColor & 255) / 255.0F;

                        calculated = (int) ((float) calculated + Math.max(red, Math.max(green, blue)) * 255.0F);
                        colors[0] += (int) (red * 255.0F);
                        colors[1] += (int) (green * 255.0F);
                        colors[2] += (int) (blue * 255.0F);
                        dyeCount++;
                    }
                } else {
                    if (craftingStack.getItem() != Items.dye) {
                        return null;
                    }

                    float[] dyeColors = EntitySheep.fleeceColorTable[BlockColored.func_150032_b(craftingStack.getItemDamage())];
                    int red = (int) (dyeColors[0] * 255.0F);
                    int green = (int) (dyeColors[1] * 255.0F);
                    int blue = (int) (dyeColors[2] * 255.0F);

                    i += Math.max(red, Math.max(green, blue));
                    colors[0] += red;
                    colors[1] += green;
                    colors[2] += blue;
                    dyeCount++;
                }
            }
        }

        int i = colors[0] / dyeCount;
        int j = colors[1] / dyeCount;
        int k = colors[2] / dyeCount;
        int color = (i << 8) + j;
        color = (color << 8) + k;

        if (wingStack != null) {
            IcarusHelper.setWingColor(wingStack, color);
        }

        return wingStack;
    }

    @Override
    public int getRecipeSize() {
        return 10;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}
