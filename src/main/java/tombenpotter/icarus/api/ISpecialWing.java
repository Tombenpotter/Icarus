package tombenpotter.icarus.api;

import net.minecraft.item.ItemStack;

public interface ISpecialWing {
    public void onWingFlap(ItemStack stack);
    public void onWingHover(ItemStack stack);
    public boolean canWingBeUsed(ItemStack stack);
}
