package tombenpotter.icarus.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import tombenpotter.icarus.Icarus;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Icarus.channel);

    public static void registerPackets() {
        INSTANCE.registerMessage(PacketJump.class, PacketJump.class, 0, Side.SERVER);
        INSTANCE.registerMessage(PacketClientConfig.class, PacketClientConfig.class, 1, Side.SERVER);
    }
}
