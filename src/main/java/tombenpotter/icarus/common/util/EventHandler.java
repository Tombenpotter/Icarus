package tombenpotter.icarus.common.util;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import tombenpotter.icarus.ConfigHandler;
import tombenpotter.icarus.common.items.ItemWing;
import tombenpotter.icarus.common.network.PacketClientConfig;
import tombenpotter.icarus.common.network.PacketHandler;

public class EventHandler {

    @SubscribeEvent
    public void onPlayerJoin(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPlayer && !HoverHandler.getHoldKeyToHover((EntityPlayer) event.entity)) {
            if (event.world.isRemote) {
                if (ConfigHandler.holdKeyToHover) {
                    PacketHandler.INSTANCE.sendToServer(new PacketClientConfig());
                    HoverHandler.addHoldKeyToHover((EntityPlayer) event.entity);
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
