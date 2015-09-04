package tombenpotter.icarus.util;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import tombenpotter.icarus.common.network.PacketHandler;
import tombenpotter.icarus.common.network.PacketClientConfig;

import java.util.HashMap;

public class EventHandler {

    public static HashMap<String, Boolean> holdShiftToHoverForPlayer = new HashMap<String, Boolean>();

    @SubscribeEvent
    public void onPlayerJoin(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPlayer && !holdShiftToHoverForPlayer.containsKey(event.entity.getCommandSenderName())) {
            if (event.world.isRemote) {
                PacketHandler.INSTANCE.sendToServer(new PacketClientConfig());
                holdShiftToHoverForPlayer.put(event.entity.getCommandSenderName(), ConfigHandler.holdSneakToHover);
            }
        }
    }
}
