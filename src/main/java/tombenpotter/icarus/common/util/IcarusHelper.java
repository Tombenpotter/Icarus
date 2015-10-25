package tombenpotter.icarus.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import tombenpotter.icarus.api.IcarusConstants;
import tombenpotter.icarus.common.items.ItemWing;
import tombenpotter.icarus.common.util.cofh.StringHelper;

import java.util.ArrayList;

public class IcarusHelper {

    public static ArrayList<ItemStack> wingList = new ArrayList<ItemStack>();
    public static ArrayList<ItemStack> armorList = new ArrayList<ItemStack>();

    private IcarusHelper() {
    }

    public static String pressShiftForDetails() {
        return StringHelper.LIGHT_GRAY + StringHelper.localize("tooltip.icarus.press") + " " +
                StringHelper.LIGHT_BLUE + StringHelper.ITALIC + StringHelper.localize("tooltip.icarus.shift") + StringHelper.END + " " +
                StringHelper.LIGHT_GRAY + StringHelper.localize("tooltip.icarus.details") + StringHelper.END;
    }

    public static void checkNBT(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
    }

    public static int getFallDistanceWithWings(EntityPlayer player, ItemWing itemWing, ItemStack stack) {
        PotionEffect potionEffect = player.getActivePotionEffect(Potion.jump);
        float amplifier = potionEffect != null ? (float) (potionEffect.getAmplifier() + 1) : 0.0F;

        return (int) (MathHelper.ceiling_float_int(player.fallDistance - 3.0F - amplifier) * itemWing.getWing(stack).fallReductionFactor);
    }

    public static int getWingColor(ItemStack stack) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound == null || !tagCompound.hasKey(IcarusConstants.NBT_COLOR)) {
            return 16777215;
        } else {
            return tagCompound.getInteger(IcarusConstants.NBT_COLOR);
        }
    }

    public static void setWingColor(ItemStack stack, int color) {
        checkNBT(stack);
        stack.stackTagCompound.setInteger(IcarusConstants.NBT_COLOR, color);
    }
}
