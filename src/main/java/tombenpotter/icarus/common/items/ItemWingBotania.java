package tombenpotter.icarus.common.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import tombenpotter.icarus.api.IcarusConstants;
import tombenpotter.icarus.api.wings.ISpecialWing;
import tombenpotter.icarus.common.IcarusItems;
import tombenpotter.icarus.common.util.IcarusWing;
import tombenpotter.icarus.common.util.cofh.StringHelper;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;

import java.util.List;

public class ItemWingBotania extends ItemWing implements ISpecialArmor, IManaUsingItem, ISpecialWing {

    public int manaPerDamage = 70;

    public ItemWingBotania(ArmorMaterial material, IcarusWing wing) {
        super(material, wing);
    }

    public static void damageItem(ItemStack stack, int damage, EntityLivingBase entity, int manaPerDamage) {
        if (entity instanceof EntityPlayer && !ManaItemHandler.requestManaExact(stack, (EntityPlayer) entity, damage * manaPerDamage, true)) {
            stack.damageItem(damage, entity);
        }
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        if (source.isUnblockable()) {
            return new ArmorProperties(0, 0, 0);
        }
        return new ArmorProperties(0, damageReduceAmount / 25D, armor.getMaxDamage() + 1 - armor.getItemDamage());
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack stack, int slot) {
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey(IcarusConstants.NBT_ITEMSTACK)) {
            ItemStack armorStack = ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag(IcarusConstants.NBT_ITEMSTACK));
            Item armor = armorStack.getItem();

            if (armor instanceof ISpecialArmor) {
                return ((ISpecialArmor) armor).getArmorDisplay(player, stack, slot);
            }
        }
        return damageReduceAmount;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        damageItem(stack, damage, entity, manaPerDamage);

        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey(IcarusConstants.NBT_ITEMSTACK)) {
            ItemStack armorStack = ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag(IcarusConstants.NBT_ITEMSTACK));
            Item armor = armorStack.getItem();

            if (armor instanceof ISpecialArmor) {
                ((ISpecialArmor) armor).damageArmor(entity, stack, source, damage, slot);
            }
        }
    }

    @Override
    public void onWingFlap(ItemStack stack, EntityPlayer player) {
        if(!getArmorMaterial().name().equals(IcarusItems.CLOTH.name())){
            ManaItemHandler.requestManaExact(stack, player, manaPerDamage / 2, true);
        }
    }

    @Override
    public void onWingHover(ItemStack stack, EntityPlayer player) {
        if(!getArmorMaterial().name().equals(IcarusItems.CLOTH.name())){
            ManaItemHandler.requestManaExact(stack, player, manaPerDamage / 4, true);
        }
    }

    @Override
    public void onWingTick(ItemStack stack, EntityPlayer player) {
        if (stack.getItemDamage() > 0 && ManaItemHandler.requestManaExact(stack, player, manaPerDamage, true)) {
                stack.setItemDamage(stack.getItemDamage() - 1);
        }
    }

    @Override
    public boolean canWingBeUsed(ItemStack stack, EntityPlayer player) {
        return getArmorMaterial().name().equals(IcarusItems.CLOTH.name()) || ManaItemHandler.requestManaExact(stack, player, manaPerDamage, false);
    }

    @Override
    public List<String> getDisplayString(World clientWorld, EntityPlayer clientPlayer, ItemStack stack) {
        List<String> list = super.getDisplayString(clientWorld, clientPlayer, stack);
        list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.durability") + StringHelper.END + StringHelper.LIGHT_GRAY + ": " + (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage() + StringHelper.END);
        return list;
    }
}
