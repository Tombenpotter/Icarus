package tombenpotter.icarus.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import tombenpotter.icarus.common.items.ItemWing;
import tombenpotter.icarus.common.util.cofh.StringHelper;

public class WingHelper {

    private WingHelper() {
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

    public static int getFallDistanceWithWings(EntityPlayer player, ItemWing itemWing, ItemStack stack){
        PotionEffect potionEffect = player.getActivePotionEffect(Potion.jump);
        float amplifier = potionEffect != null ? (float) (potionEffect.getAmplifier() + 1) : 0.0F;
        // players are 2 blocks high not 3
        return (int) (MathHelper.ceiling_float_int(player.fallDistance - 2.0F - amplifier) * itemWing.getWing(stack).fallReductionFactor);
    }
}
