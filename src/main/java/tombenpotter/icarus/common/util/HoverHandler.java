package tombenpotter.icarus.common.util;

import net.minecraft.entity.player.EntityPlayer;
import tombenpotter.icarus.common.network.PacketHandler;
import tombenpotter.icarus.common.network.PacketHoverSync;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class HoverHandler {

    private static HashMap<UUID, Boolean> hoverStates = new HashMap<UUID, Boolean>();
    private static HashSet<UUID> holdKeyToHoverForPlayer = new HashSet<UUID>();

    public static boolean getHover(EntityPlayer player) {
        if (!hoverStates.containsKey(player.getUniqueID()) || hoverStates.get(player.getUniqueID()) == null) {
            hoverStates.put(player.getUniqueID(), false);
        }
        return hoverStates.get(player.getUniqueID());
    }

    public static void setHover(EntityPlayer player, boolean hoverState) {
        hoverStates.put(player.getUniqueID(), hoverState);
        sync(player);
    }

    public static void sync(EntityPlayer player) {
        if (player.worldObj.isRemote) {
            PacketHandler.INSTANCE.sendToServer(new PacketHoverSync(getHover(player)));
        }
    }

    public static boolean getHoldKeyToHover(EntityPlayer player) {
        return holdKeyToHoverForPlayer.contains(player.getUniqueID());
    }

    public static void addHoldKeyToHover(EntityPlayer player) {
        holdKeyToHoverForPlayer.add(player.getUniqueID());
    }
}
