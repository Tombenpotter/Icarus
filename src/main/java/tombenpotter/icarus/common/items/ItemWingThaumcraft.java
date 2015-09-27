package tombenpotter.icarus.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;
import tombenpotter.icarus.api.wings.ISpecialWing;
import tombenpotter.icarus.common.util.IcarusWing;

public class ItemWingThaumcraft extends ItemWing implements IRepairable {

    public ItemWingThaumcraft(ArmorMaterial material, IcarusWing icarusWing) {
        super(material, icarusWing);
    }

    public static class ItemWingVoidMetal extends ItemWingThaumcraft implements ISpecialWing, IWarpingGear {

        public ItemWingVoidMetal(ArmorMaterial material, IcarusWing icarusWing) {
            super(material, icarusWing);
        }

        @Override
        public void onWingFlap(ItemStack stack, EntityPlayer player) {
        }

        @Override
        public void onWingTick(ItemStack stack, EntityPlayer player) {
            if (stack.getItemDamage() > 0 && player.ticksExisted % 20 == 0 && player instanceof EntityPlayer) {
                stack.setItemDamage(stack.getItemDamage() - 1);
            }
        }

        @Override
        public void onWingHover(ItemStack stack, EntityPlayer player) {
        }

        @Override
        public boolean canWingBeUsed(ItemStack stack, EntityPlayer player) {
            return true;
        }

        @Override
        public int getWarp(ItemStack itemstack, EntityPlayer player) {
            return 2;
        }
    }
}
