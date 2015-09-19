package tombenpotter.icarus.api.wings;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ISpecialWing {
    public void onWingFlap(ItemStack stack, EntityPlayer player);

    public void onWingHover(ItemStack stack, EntityPlayer player);

    public void onWingTick(ItemStack stack, EntityPlayer player);

    public boolean canWingBeUsed(ItemStack stack, EntityPlayer player);
}
