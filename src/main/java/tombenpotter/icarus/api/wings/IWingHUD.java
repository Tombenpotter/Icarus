package tombenpotter.icarus.api.wings;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public interface IWingHUD {

    public List<String> getDisplayString(World clientWorld, EntityPlayer clientPlayer, ItemStack stack);
}
