package tombenpotter.icarus.api.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import tombenpotter.icarus.common.items.ItemWing;

public class EnchantWingBase extends Enchantment {

    public String name;

    public EnchantWingBase(String name, int id, int weight) {
        super(id, weight, EnumEnchantmentType.armor_torso);
        setName("icarus." + name);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ItemWing;
    }
}
