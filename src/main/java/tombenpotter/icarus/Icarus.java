package tombenpotter.icarus;


import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;
import tombenpotter.icarus.ConfigHandler;
import tombenpotter.icarus.common.IcarusEnchants;
import tombenpotter.icarus.common.IcarusItems;
import tombenpotter.icarus.common.network.PacketHandler;
import tombenpotter.icarus.common.util.EventHandler;
import tombenpotter.icarus.common.util.LogHelper;
import tombenpotter.icarus.proxies.CommonProxy;
import tombenpotter.icarus.reference.Metadata;
import tombenpotter.icarus.reference.Reference;

@Mod(modid = Reference.ID, name = Reference.NAME, version = Reference.VERSION, guiFactory = Reference.MOD_GUI_FACTORY)
public class Icarus {
    @Mod.Instance
    public Icarus instance;

    @Mod.Metadata(Reference.ID)
    public static ModMetadata metadata;

    // point legacy placeholders to reference until main mod is cleaned
    public static final String modid = Reference.ID;
    public static final String name = Reference.NAME;
    public static final String version = Reference.VERSION;
    public static final String texturePath = Reference.TEXTURE_PATH;
    public static final String channel = Reference.PACKET_CHANNEL;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
    public static CommonProxy proxy;

    public static Logger logger;

    public static CreativeTabs creativeTab = new CreativeTabs("tab" + Reference.NAME) {
        @Override
        public Item getTabIconItem() {
            return IcarusItems.goldDiamondWings;
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        logger = LogHelper.getLogger();
        metadata = Metadata.init(metadata);
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigHandler());
        IcarusEnchants.registerEnchants();
        IcarusItems.registerItems();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.registerRenders();
        IcarusItems.registerItemsInInitBecausePixlepix();
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        FMLCommonHandler.instance().bus().register(new EventHandler());
        PacketHandler.registerPackets();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        //this should be better handled somehow
        if (Loader.isModLoaded("Thaumcraft")) {
            ConfigHandler.dimensionWingsDisabled.add(-42);
        }
    }
}
