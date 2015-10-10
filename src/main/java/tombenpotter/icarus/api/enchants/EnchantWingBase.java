package tombenpotter.icarus.api.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import tombenpotter.icarus.common.items.ItemWing;

public class EnchantWingBase extends Enchantment {

    public String name;
    public int maxLevel;

    public EnchantWingBase(String name, int maxLevel, int id, int weight) {
        super(id, weight, EnumEnchantmentType.armor_torso);
        setName("icarus." + name);
        this.maxLevel = maxLevel;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ItemWing;
    }
}
