package tombenpotter.icarus.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import tombenpotter.icarus.Icarus;
import tombenpotter.icarus.common.util.EventHandler;

public class PacketClientConfig implements IMessage, IMessageHandler<PacketClientConfig, IMessage> {

    public PacketClientConfig() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    @Override
    public IMessage onMessage(PacketClientConfig message, MessageContext ctx) {
        EventHandler.holdSneakToHoverForPlayer.add(ctx.getServerHandler().playerEntity.getUniqueID());
        Icarus.logger.info("Clientside config info received from player " + ctx.getServerHandler().playerEntity.getDisplayName() + " with UUID " + ctx.getServerHandler().playerEntity.getUniqueID());
        return null;
    }
}
