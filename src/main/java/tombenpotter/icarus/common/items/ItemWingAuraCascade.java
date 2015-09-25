package tombenpotter.icarus.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tombenpotter.icarus.ConfigHandler;
import tombenpotter.icarus.api.wings.Wing;
import tombenpotter.icarus.common.util.IcarusWing;
import tombenpotter.icarus.common.util.cofh.StringHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemWingAuraCascade extends ItemWing {

    public static final String NBT_TIER = "Icarus_Tier";
    public static final int MAX_TIER = 11;
    public ArrayList<Wing> wingList = new ArrayList<Wing>();

    public ItemWingAuraCascade(ArmorMaterial material, Wing wing) {
        super(material, wing);

        wingList.add(wing);

        for (int i = 1; i < MAX_TIER; i++) {
            wingList.add(new IcarusWing(wing.name, wing.durability + i * wing.durability / MAX_TIER, wing.maxHeight + i * wing.maxHeight / MAX_TIER, wing.jumpBoost + i * wing.jumpBoost / MAX_TIER, wing.glideFactor - i * wing.glideFactor / MAX_TIER, wing.rainDrag - i * wing.rainDrag / MAX_TIER, wing.waterDrag - i * wing.waterDrag / MAX_TIER, wing.fallReductionFactor - i * wing.fallReductionFactor / MAX_TIER));
        }
    }

    @Override
    public Wing getWing(ItemStack stack) {
        checkNBT(stack);
        return wingList.get(stack.stackTagCompound.getInteger(NBT_TIER));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        checkNBT(stack);
        return wingList.get(stack.stackTagCompound.getInteger(NBT_TIER)).durability;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean held) {
        if (!StringHelper.isShiftKeyDown()) {
            checkNBT(stack);

            list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.tier") + StringHelper.END + ": " + (stack.stackTagCompound.getInteger(NBT_TIER) + 1));
            if (ConfigHandler.showWingsStats) {
                list.add(pressShiftForDetails());
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
        for (int i = 0; i < MAX_TIER; i++) {
            ItemStack stack = new ItemStack(this);
            stack.setTagCompound(new NBTTagCompound());
            stack.stackTagCompound.setInteger(NBT_TIER, i);
            list.add(stack);
        }
    }
}
