package tombenpotter.icarus.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tombenpotter.icarus.api.wings.ISpecialWing;
import tombenpotter.icarus.api.wings.Wing;
import tombenpotter.icarus.common.util.cofh.StringHelper;
import tombenpotter.icarus.common.util.WingHelper;

import java.util.List;

public class ItemWingVanilla extends ItemWing implements ISpecialWing {

    public ItemWingVanilla(ArmorMaterial material, Wing wing) {
        super(material, wing);
    }

    @Override
    public void onWingFlap(ItemStack stack, EntityPlayer player) {
        WingHelper.checkNBT(stack);
        stack.stackTagCompound.setInteger(ItemWing.NBT_DAMAGE, stack.stackTagCompound.getInteger(ItemWing.NBT_DAMAGE) + 1);
    }

    @Override
    public void onWingHover(ItemStack stack, EntityPlayer player) {
    }

    @Override
    public void onWingTick(ItemStack stack, EntityPlayer player) {
        WingHelper.checkNBT(stack);
        int nbtDamage = stack.stackTagCompound.getInteger(ItemWing.NBT_DAMAGE);
        if (nbtDamage >= ItemWing.flapsToDamage) {
            stack.stackTagCompound.setInteger(ItemWing.NBT_DAMAGE, nbtDamage - ItemWing.flapsToDamage);
            stack.damageItem(1, player);
        }
    }

    @Override
    public boolean canWingBeUsed(ItemStack stack, EntityPlayer player) {
        return true;
    }

    @Override
    public List<String> getDisplayString(World clientWorld, EntityPlayer clientPlayer, ItemStack stack) {
        List<String> list = super.getDisplayString(clientWorld, clientPlayer, stack);
        if ((stack.getMaxDamage() - stack.getItemDamage()) < stack.getMaxDamage()) {
            list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.durability") + StringHelper.END + StringHelper.LIGHT_GRAY + ": " + (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage() + StringHelper.END);
        }
        return list;
    }
}
