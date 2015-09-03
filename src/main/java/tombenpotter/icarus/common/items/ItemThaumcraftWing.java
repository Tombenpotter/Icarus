package tombenpotter.icarus.common.items;

import net.minecraft.item.ItemStack;
import thaumcraft.api.IRepairable;
import tombenpotter.icarus.common.ISpecialWing;

public class ItemThaumcraftWing extends ItemWing implements IRepairable {

    public ItemThaumcraftWing(ArmorMaterial material, Wing wing) {
        super(material, wing);
    }

    public static class ItemVoidMetalWing extends ItemThaumcraftWing implements ISpecialWing {

        public ItemVoidMetalWing(ArmorMaterial material, Wing wing) {
            super(material, wing);
        }

        @Override
        public void onWingFlap(ItemStack stack) {
            if (stack.getItemDamage() > 0) {
                stack.setItemDamage(stack.getItemDamage() + 1);
            }
        }

        @Override
        public void onWingHover(ItemStack stack) {
        }
    }
}
