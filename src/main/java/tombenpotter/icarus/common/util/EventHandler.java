package tombenpotter.icarus.common.util;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import tombenpotter.icarus.ConfigHandler;
import tombenpotter.icarus.Icarus;
import tombenpotter.icarus.common.items.ItemWing;
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
                    Icarus.logger.info("Sending clientside config info from Client to Server");
                    PacketHandler.INSTANCE.sendToServer(new PacketClientConfig());
                    holdSneakToHoverForPlayer.add(event.entity.getUniqueID());
                }
            }
        }
    }

    @SubscribeEvent
    public void onFall(LivingFallEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer entity = (EntityPlayer) event.entity;
            ItemStack stack = entity.inventory.armorInventory[2];
            if (stack != null && stack.getItem() instanceof ItemWing) {
                event.distance *= ((ItemWing) stack.getItem()).getWing(stack).fallReductionFactor;
            }
        }
    }
}
