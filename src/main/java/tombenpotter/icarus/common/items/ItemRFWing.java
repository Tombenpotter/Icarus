package tombenpotter.icarus.common.items;

import cofh.api.energy.IEnergyContainerItem;
import tombenpotter.icarus.common.ISpecialWing;
import tombenpotter.icarus.util.EnergyHelper;
import tombenpotter.icarus.util.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;

import java.util.List;

public class ItemRFWing extends ItemWing implements ISpecialArmor, IEnergyContainerItem, ISpecialWing {

    //Armor handling methods taken from Redstone Arsenal.

    int capacity;
    int receive = 2000;
    int send = 1000;
    int energyPerDamage = 160;
    public double absorbRatio = 0.9D;
    public static final ArmorProperties FLUX = new ArmorProperties(0, 0.20D, Integer.MAX_VALUE);

    public ItemRFWing(ArmorMaterial material, Wing wing) {
        super(material, wing);
        capacity = wing.durability;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean held) {
        if (StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown())
            list.add(StringHelper.shiftForDetails());

        if (stack.stackTagCompound == null)
            EnergyHelper.setDefaultEnergyTag(stack, 0);

        if (StringHelper.isShiftKeyDown()) {
            list.add(StringHelper.localize("info.cofh.charge") + ": " + stack.stackTagCompound.getInteger("Energy") + " / " + capacity + " RF");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.add(EnergyHelper.setDefaultEnergyTag(new ItemStack(item, 1, 0), 0));
        list.add(EnergyHelper.setDefaultEnergyTag(new ItemStack(item, 1, 0), getMaxEnergyStored(new ItemStack(item))));
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return true;
    }

    @Override
    public int getDisplayDamage(ItemStack stack) {
        if (stack.stackTagCompound == null) {
            EnergyHelper.setDefaultEnergyTag(stack, 0);
        }
        return capacity - stack.stackTagCompound.getInteger("Energy");
    }

    protected int getEnergyPerDamage(ItemStack stack) {
        return energyPerDamage;
    }

    //ISpecialArmor stuff
    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        if (source.damageType.equals("flux")) {
            return FLUX;
        } else if (source.isUnblockable()) {
            int absorbMax = getEnergyPerDamage(armor) > 0 ? 25 * getEnergyStored(armor) / getEnergyPerDamage(armor) : 0;
            return new ArmorProperties(0, absorbRatio * getArmorMaterial().getDamageReductionAmount(armorType) * 0.025, absorbMax);
        }
        int absorbMax = getEnergyPerDamage(armor) > 0 ? 25 * getEnergyStored(armor) / getEnergyPerDamage(armor) : 0;
        return new ArmorProperties(0, absorbRatio * getArmorMaterial().getDamageReductionAmount(armorType) * 0.05, absorbMax);
        // 0.05 = 1 / 20 (max armor)
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        if (getEnergyStored(armor) >= getEnergyPerDamage(armor)) {
            return 20 * 40 / 100;
        }
        return 0;
    }
    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack armor, DamageSource source, int damage, int slot) {
        if (source.damageType.equals("flux")) {
            boolean p = source.getEntity() == null;
            receiveEnergy(armor, damage * (p ? energyPerDamage / 2 : getEnergyPerDamage(armor)), false);
        } else {
            extractEnergy(armor, damage * getEnergyPerDamage(armor), false);
        }
    }

    //IEnergyContainerItem Stuff
    @Override
    public int receiveEnergy(ItemStack stack, int i, boolean simulate) {
        if (stack.stackTagCompound == null)
            EnergyHelper.setDefaultEnergyTag(stack, 0);

        int energy = stack.stackTagCompound.getInteger("Energy");
        int energyReceived = Math.min(i, Math.min(capacity - energy, receive));

        if (!simulate) {
            energy += energyReceived;
            stack.stackTagCompound.setInteger("Energy", energy);
        }

        return energyReceived;
    }

    @Override
    public int extractEnergy(ItemStack stack, int extract, boolean simulate) {
        if (stack.stackTagCompound == null)
            EnergyHelper.setDefaultEnergyTag(stack, 0);

        int energy = stack.stackTagCompound.getInteger("Energy");
        int energyExtracted = Math.min(extract, Math.min(energy, send));

        if (!simulate) {
            energy -= energyExtracted;
            stack.stackTagCompound.setInteger("Energy", energy);
        }

        return energyExtracted;
    }

    @Override
    public int getEnergyStored(ItemStack stack) {
        if (stack.stackTagCompound == null)
            EnergyHelper.setDefaultEnergyTag(stack, 0);

        return stack.stackTagCompound.getInteger("Energy");
    }

    @Override
    public int getMaxEnergyStored(ItemStack stack) {
        return capacity;
    }

    @Override
    public void onWingFlap(ItemStack stack) {
        extractEnergy(stack, energyPerDamage / 2, false);
    }

    @Override
    public void onWingHover(ItemStack stack) {
        extractEnergy(stack, energyPerDamage / 4, false);
    }
}
