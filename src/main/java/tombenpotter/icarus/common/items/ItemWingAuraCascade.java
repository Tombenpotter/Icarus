package tombenpotter.icarus.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import tombenpotter.icarus.ConfigHandler;
import tombenpotter.icarus.api.wings.Wing;
import tombenpotter.icarus.common.IcarusItems;
import tombenpotter.icarus.common.util.IcarusWing;
import tombenpotter.icarus.common.util.WingHelper;
import tombenpotter.icarus.common.util.cofh.StringHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemWingAuraCascade extends ItemWing implements ISpecialArmor {

    public static final String NBT_TIER = "Icarus_Tier";
    public static final int MAX_TIER = 11;
    public static ArrayList<ItemStack> angelsteelWings = new ArrayList<ItemStack>();

    public ArrayList<Wing> wingList = new ArrayList<Wing>();

    public ItemWingAuraCascade(ArmorMaterial material, Wing wing) {
        super(material, wing);

        wingList.add(wing);
        for (int i = 1; i < MAX_TIER; i++) {
            wingList.add(new IcarusWing(wing.name, wing.durability + i * wing.durability / MAX_TIER, wing.maxHeight + i * wing.maxHeight / MAX_TIER, wing.jumpBoost + i * wing.jumpBoost / MAX_TIER, wing.glideFactor - i * wing.glideFactor / MAX_TIER, wing.rainDrag - i * wing.rainDrag / MAX_TIER, wing.waterDrag - i * wing.waterDrag / MAX_TIER, wing.fallReductionFactor - i * wing.fallReductionFactor / MAX_TIER));
        }

        for (int i = 0; i < MAX_TIER; i++) {
            IcarusItems.singleWings.addTooltip(17 + i, StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.tier") + StringHelper.END + ": " + (i + 1));
            ItemStack stack = new ItemStack(this);
            stack.setTagCompound(new NBTTagCompound());
            stack.stackTagCompound.setInteger(NBT_TIER, i);
            angelsteelWings.add(stack);
        }
    }

    @Override
    public Wing getWing(ItemStack stack) {
        WingHelper.checkNBT(stack);
        return wingList.get(stack.stackTagCompound.getInteger(NBT_TIER));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        WingHelper.checkNBT(stack);
        return wingList.get(stack.stackTagCompound.getInteger(NBT_TIER)).durability;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        if (source.isUnblockable()) {
            return new ArmorProperties(0, 0, 0);
        }
        return new ArmorProperties(0, damageReduceAmount / 40D, armor.getMaxDamage() + 1 - armor.getItemDamage());
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return damageReduceAmount;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean held) {
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey(NBT_ITEMSTACK)) {
            list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.render.armor") + StringHelper.END + ": " + ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag(NBT_ITEMSTACK)).getDisplayName());
        }

        if (!StringHelper.isShiftKeyDown()) {
            WingHelper.checkNBT(stack);    
            list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.tier") + StringHelper.END + ": " + (stack.stackTagCompound.getInteger(NBT_TIER) + 1));
            if (ConfigHandler.showWingsStats) {
                list.add(WingHelper.pressShiftForDetails());
            }
        } else if (StringHelper.isShiftKeyDown()) {
            if (ConfigHandler.showWingsStats) {
                list.addAll(tooltip(stack));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.addAll(angelsteelWings);
    }
}
