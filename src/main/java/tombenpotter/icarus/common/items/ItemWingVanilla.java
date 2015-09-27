package tombenpotter.icarus.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import tombenpotter.icarus.api.wings.ISpecialWing;
import tombenpotter.icarus.api.wings.Wing;
import tombenpotter.icarus.common.util.WingHelper;

public class ItemWingVanilla extends ItemWing implements ISpecialWing {

    public static final String NBT_DAMAGE = "Icarus_Damage";
    private static final int flapsToDamage = 10;

    public ItemWingVanilla(ArmorMaterial material, Wing wing) {
        super(material, wing);
    }

    @Override
    public void onWingFlap(ItemStack stack, EntityPlayer player) {
        WingHelper.checkNBT(stack);
        stack.stackTagCompound.setInteger(NBT_DAMAGE, stack.stackTagCompound.getInteger(NBT_DAMAGE) + 1);
    }

    @Override
    public void onWingHover(ItemStack stack, EntityPlayer player) {
    }

    @Override
    public void onWingTick(ItemStack stack, EntityPlayer player) {
        WingHelper.checkNBT(stack);
        int nbtDamage = stack.stackTagCompound.getInteger(NBT_DAMAGE);
        if (nbtDamage >= flapsToDamage) {
            stack.stackTagCompound.setInteger(NBT_DAMAGE, nbtDamage - flapsToDamage);
            stack.damageItem(1, player);
        }
    }

    @Override
    public boolean canWingBeUsed(ItemStack stack, EntityPlayer player) {
        return true;
    }
}
