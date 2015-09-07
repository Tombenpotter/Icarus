package tombenpotter.icarus.util;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import tombenpotter.icarus.common.network.PacketClientConfig;
import tombenpotter.icarus.common.network.PacketHandler;

import java.util.HashSet;
import java.util.UUID;

public class EventHandler {

    public static HashSet<UUID> holdSneakToHoverForPlayer = new HashSet<UUID>();

    public static boolean getHoldSneakToHover(EntityPlayer player) {
        return holdSneakToHoverForPlayer.contains(player.getUniqueID());
    }

    @SubscribeEvent
    public void onPlayerJoin(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPlayer && !holdSneakToHoverForPlayer.contains(event.entity.getUniqueID())) {
            if (event.world.isRemote) {
                if (ConfigHandler.holdSneakToHover) {
                    PacketHandler.INSTANCE.sendToServer(new PacketClientConfig());
                    holdSneakToHoverForPlayer.add(event.entity.getUniqueID());
                }
            }
        }
    }
}
