package tombenpotter.icarus.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import tombenpotter.icarus.common.ISpecialWing;
import tombenpotter.icarus.common.items.ItemWing;

public class PacketJump implements IMessage, IMessageHandler<PacketJump, IMessage> {

    public double jump;
    public boolean isSpecialWing;

    public PacketJump() {
    }

    public PacketJump(double jump, boolean isSpecialWing) {
        this.jump = jump;
        this.isSpecialWing = isSpecialWing;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        jump = buf.readDouble();
        isSpecialWing = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(jump);
        buf.writeBoolean(isSpecialWing);
    }

    @Override
    public IMessage onMessage(PacketJump message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        player.motionY = message.jump;
        player.fallDistance = 0;
        if (message.isSpecialWing && player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].getItem() instanceof ItemWing) {
            ISpecialWing specialWing = (ISpecialWing) player.inventory.armorInventory[2].getItem();
            specialWing.onWingFlap(player.inventory.armorInventory[2]);
        }

        return null;
    }
}
