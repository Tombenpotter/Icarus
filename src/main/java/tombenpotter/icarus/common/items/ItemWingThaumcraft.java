package tombenpotter.icarus.common.items;

import net.minecraft.item.ItemStack;
import thaumcraft.api.IRepairable;
import tombenpotter.icarus.api.ISpecialWing;
import tombenpotter.icarus.common.util.IcarusWing;

public class ItemWingThaumcraft extends ItemWing implements IRepairable {

    public ItemWingThaumcraft(ArmorMaterial material, IcarusWing icarusWing) {
        super(material, icarusWing);
    }

    public static class ItemWingVoidMetal extends ItemWingThaumcraft implements ISpecialWing {

        public ItemWingVoidMetal(ArmorMaterial material, IcarusWing icarusWing) {
            super(material, icarusWing);
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

        @Override
        public boolean canWingBeUsed(ItemStack stack) {
            return true;
        }
    }
}
