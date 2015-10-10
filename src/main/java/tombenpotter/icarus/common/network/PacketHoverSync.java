package tombenpotter.icarus.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import tombenpotter.icarus.common.util.HoverHandler;

public class PacketHoverSync implements IMessage, IMessageHandler<PacketHoverSync, IMessage> {

    public boolean hoverState;

    public PacketHoverSync() {
    }

    public PacketHoverSync(boolean hoverState) {
        this.hoverState = hoverState;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        hoverState = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(hoverState);
    }

    @Override
    public IMessage onMessage(PacketHoverSync message, MessageContext ctx) {
        HoverHandler.setHover(ctx.getServerHandler().playerEntity, message.hoverState);
        return null;
    }
}
