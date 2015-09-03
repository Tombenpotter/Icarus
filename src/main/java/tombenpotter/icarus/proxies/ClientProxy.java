package tombenpotter.icarus.proxies;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;
import tombenpotter.icarus.client.ClientEventHandler;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenders() {
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        FMLCommonHandler.instance().bus().register(new ClientEventHandler());
    }
}
