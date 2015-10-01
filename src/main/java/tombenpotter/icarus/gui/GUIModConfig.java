package tombenpotter.icarus.gui;

import tombenpotter.icarus.Icarus;
import tombenpotter.icarus.ConfigHandler;
import tombenpotter.icarus.reference.Reference;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;

public class GUIModConfig extends GuiConfig {
    public GUIModConfig(GuiScreen guiScreen) {
        super(guiScreen,
                ConfigHandler.getConfigElements(),
                Reference.ID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(ConfigHandler.config.toString()));
    }
}