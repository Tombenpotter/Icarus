package tombenpotter.icarus.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import tombenpotter.icarus.api.wings.ISpecialWing;
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
        ItemStack wingStack = player.inventory.armorInventory[2];

        if (wingStack == null || !(wingStack.getItem() instanceof ItemWing)) {
            return null;
        }

        ItemWing itemWing = (ItemWing) wingStack.getItem();

        if (message.isSpecialWing) {
            ISpecialWing specialWing = (ISpecialWing) itemWing;
            if (!specialWing.canWingBeUsed(player.inventory.armorInventory[2], player)) {
                return null;
            }
            specialWing.onWingFlap(player.inventory.armorInventory[2], player);
        }

        player.motionY = message.jump;
        player.fallDistance = 0;

        itemWing.handleExhaustion(player.worldObj, player, wingStack);

        return null;
    }
}
