package tombenpotter.icarus.reference;

public class Reference {
    // User friendly version of our mods name.
    public static final String NAME = "Icarus";

    // Internal mod name used for reference purposes and resource gathering.
    public static final String ID = "TIcarus";
    public static final String RESOURCE_ID = NAME.toLowerCase();

    // Main version information that will be displayed in mod listing and for other purposes.
    public static final String VERSION = "@VERSION@";

    // proxy info
    public static final String CLIENT_PROXY = "tombenpotter.icarus.proxies.ClientProxy";
    public static final String COMMON_PROXY = "tombenpotter.icarus.proxies.CommonProxy";
    public static final String MOD_GUI_FACTORY = "tombenpotter.icarus.gui.GUIModFactory";

    // misc stuff
    public static final String TEXTURE_PATH = "icarus";
    public static final String PACKET_CHANNEL = "Icarus";
    public static final String DEPENDENCIES = "after:Thaumcraft;after:ThermalExpansion;after:Botania;after:EnderIO;after:aura";

}