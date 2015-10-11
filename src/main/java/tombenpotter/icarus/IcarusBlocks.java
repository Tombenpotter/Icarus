package tombenpotter.icarus;

import cpw.mods.fml.common.registry.GameRegistry;
import tombenpotter.icarus.common.blocks.BlockInvisiLight;

public class IcarusBlocks {

    public static BlockInvisiLight invisiLight = new BlockInvisiLight();

    public static void registerBlocks() {
        GameRegistry.registerBlock(invisiLight, "BlockInvisiLight");
    }

    public static void registerTiles() {
        GameRegistry.registerTileEntity(BlockInvisiLight.TileInvisibleLight.class, Icarus.modid + ":TileInvisiLight");
    }
}
