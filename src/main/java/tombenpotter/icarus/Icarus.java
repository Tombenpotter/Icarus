package tombenpotter.icarus;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import tombenpotter.icarus.common.IcarusItems;
import tombenpotter.icarus.common.network.PacketHandler;
import tombenpotter.icarus.proxies.CommonProxy;
import tombenpotter.icarus.util.ConfigHandler;
import tombenpotter.icarus.util.EventHandler;

@Mod(modid = Icarus.modid, name = Icarus.name, version = Icarus.version, dependencies = Icarus.depend)
public class Icarus {

    public static final String modid = "TIcarus";
    public static final String name = "Icarus";
    public static final String version = "1.0.1";
    public static final String texturePath = "icarus";
    public static final String channel = "Icarus";
    public static final String depend = "after:AWWayofTime;after:Thaumcraft;after:ThermalExpansion";
    public static final String clientProxy = "tombenpotter.icarus.proxies.ClientProxy";
    public static final String commonProxy = "tombenpotter.icarus.proxies.CommonProxy";
    public static CreativeTabs creativeTab = new CreativeTabs("tab" + name) {
        @Override
        public Item getTabIconItem() {
            return IcarusItems.goldDiamondWings;
        }
    };

    @SidedProxy(serverSide = Icarus.commonProxy, clientSide = Icarus.clientProxy)
    public static CommonProxy proxy;

    @Mod.Instance(Icarus.modid)
    public static Icarus instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        IcarusItems.registerItems();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.registerRenders();
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        FMLCommonHandler.instance().bus().register(new EventHandler());
        PacketHandler.registerPackets();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}
