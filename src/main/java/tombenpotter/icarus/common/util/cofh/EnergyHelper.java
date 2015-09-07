package tombenpotter.icarus.common.util.cofh;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * This class contains helper functions related to Redstone Flux, the basis of the CoFH Energy System.
 *
 * @author King Lemming
 *         <p/>
 *         Stolen from COFH Lib as it is one of the only two classes I need. Removed functions I will never use.
 */
public class EnergyHelper {

    public static final int RF_PER_MJ = 10; // Official Ratio of RF to MJ (BuildCraft)
    public static final int RF_PER_EU = 4; // Official Ratio of RF to EU (IndustrialCraft)

    private EnergyHelper() {
    }

    /* NBT TAG HELPER */
    public static void addEnergyInformation(ItemStack stack, List<String> list) {

        if (stack.getItem() instanceof IEnergyContainerItem) {
            list.add(StringHelper.localize("info.cofh.charge") + ": " + StringHelper.getScaledNumber(stack.stackTagCompound.getInteger("Energy")) + " / "
                    + StringHelper.getScaledNumber(((IEnergyContainerItem) stack.getItem()).getMaxEnergyStored(stack)) + " RF");
        }
    }

    /* IEnergyContainer Interaction */
    public static int extractEnergyFromContainer(ItemStack container, int maxExtract, boolean simulate) {

        return isEnergyContainerItem(container) ? ((IEnergyContainerItem) container.getItem()).extractEnergy(container, maxExtract, simulate) : 0;
    }

    public static int insertEnergyIntoContainer(ItemStack container, int maxReceive, boolean simulate) {

        return isEnergyContainerItem(container) ? ((IEnergyContainerItem) container.getItem()).receiveEnergy(container, maxReceive, simulate) : 0;
    }

    public static int extractEnergyFromHeldContainer(EntityPlayer player, int maxExtract, boolean simulate) {

        ItemStack container = player.getCurrentEquippedItem();

        return isEnergyContainerItem(container) ? ((IEnergyContainerItem) container.getItem()).extractEnergy(container, maxExtract, simulate) : 0;
    }

    public static int insertEnergyIntoHeldContainer(EntityPlayer player, int maxReceive, boolean simulate) {

        ItemStack container = player.getCurrentEquippedItem();

        return isEnergyContainerItem(container) ? ((IEnergyContainerItem) container.getItem()).receiveEnergy(container, maxReceive, simulate) : 0;
    }

    public static boolean isPlayerHoldingEnergyContainerItem(EntityPlayer player) {

        return isEnergyContainerItem(player.getCurrentEquippedItem());
    }

    public static boolean isEnergyContainerItem(ItemStack container) {

        return container != null && container.getItem() instanceof IEnergyContainerItem;
    }

    public static ItemStack setDefaultEnergyTag(ItemStack container, int energy) {

        if (container.stackTagCompound == null) {
            container.setTagCompound(new NBTTagCompound());
        }
        container.stackTagCompound.setInteger("Energy", energy);

        return container;
    }
}
