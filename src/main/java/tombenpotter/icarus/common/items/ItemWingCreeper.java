package tombenpotter.icarus.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import tombenpotter.icarus.api.wings.Wing;

public class ItemWingCreeper extends ItemWingVanilla {

    public ItemWingCreeper(ArmorMaterial material, Wing wing) {
        super(material, wing);
    }

    @Override
    public void onWingFlap(ItemStack stack, EntityPlayer player) {
        super.onWingFlap(stack, player);

        player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "random.explode", 1.0F, 1.0F);
        if (player.worldObj.isRemote) {
            player.worldObj.spawnParticle("largeexplode", player.posX, player.posY, player.posZ, 1, 1, 1);
        }
    }
}
