package tombenpotter.icarus.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import tombenpotter.icarus.api.wings.Wing;

public class ItemWingRadioactive extends ItemWingVanilla {

    public ItemWingRadioactive(ArmorMaterial material, Wing wing) {
        super(material, wing);
    }

    @Override
    public void onWingFlap(ItemStack stack, EntityPlayer player) {
        super.onWingFlap(stack, player);

        player.addPotionEffect(new PotionEffect(Potion.poison.getId(), 40, 3));
    }

    @Override
    public void onWingTick(ItemStack stack, EntityPlayer player) {
        super.onWingTick(stack, player);

        player.addPotionEffect(new PotionEffect(Potion.hunger.getId(), 0, 1));
    }
}
