package tombenpotter.icarus.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import tombenpotter.icarus.IcarusBlocks;
import tombenpotter.icarus.api.IcarusConstants;
import tombenpotter.icarus.api.wings.ISpecialWing;
import tombenpotter.icarus.api.wings.Wing;
import tombenpotter.icarus.common.util.IcarusHelper;
import tombenpotter.icarus.common.util.cofh.StringHelper;

import java.util.List;

public class ItemWingVanilla extends ItemWing implements ISpecialWing {

    private static final int flapsToDamage = 10;

    public ItemWingVanilla(ArmorMaterial material, Wing wing) {
        super(material, wing);
    }

    @Override
    public void onWingFlap(ItemStack stack, EntityPlayer player) {
        IcarusHelper.checkNBT(stack);
        if (!player.onGround) {
            stack.stackTagCompound.setInteger(IcarusConstants.NBT_DAMAGE, stack.stackTagCompound.getInteger(IcarusConstants.NBT_DAMAGE) + 1);
        }
    }

    @Override
    public void onWingHover(ItemStack stack, EntityPlayer player) {
    }

    @Override
    public void onWingTick(ItemStack stack, EntityPlayer player) {
        IcarusHelper.checkNBT(stack);
        int nbtDamage = stack.stackTagCompound.getInteger(IcarusConstants.NBT_DAMAGE);
        if (nbtDamage >= flapsToDamage) {
            stack.stackTagCompound.setInteger(IcarusConstants.NBT_DAMAGE, nbtDamage - flapsToDamage);
            stack.damageItem(1, player);
        }
    }

    @Override
    public boolean canWingBeUsed(ItemStack stack, EntityPlayer player) {
        return true;
    }

    @Override
    public List<String> getDisplayString(World clientWorld, EntityPlayer clientPlayer, ItemStack stack) {
        List<String> list = super.getDisplayString(clientWorld, clientPlayer, stack);
        list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.durability") + StringHelper.END + StringHelper.LIGHT_GRAY + ": " + (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage() + StringHelper.END);
        return list;
    }

    /*
    * Custom wings that require no, or close to no modifications go here
     */

    public static class ItemWingCreeper extends ItemWingVanilla {
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

    public static class ItemWingRadioactive extends ItemWingVanilla {
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

    public static class ItemWingGlowstone extends ItemWingVanilla {
        public ItemWingGlowstone(ArmorMaterial material, Wing wing) {
            super(material, wing);
        }

        @Override
        public void onWingTick(ItemStack stack, EntityPlayer player) {
            super.onWingTick(stack, player);

            World world = player.worldObj;
            int x = (int) Math.floor(player.posX);
            int y = (int) player.posY + 1;
            int z = (int) Math.floor(player.posZ);
            if (!world.isRemote && world.getBlock(x, y, z) == Blocks.air) {
                world.setBlock(x, y, z, IcarusBlocks.invisiLight);
            }
        }
    }

    /* These ones exist just for clarity's sake */
    public static class ItemWingWitchery extends ItemWingVanilla {
        public ItemWingWitchery(ArmorMaterial material, Wing wing) {
            super(material, wing);
        }
    }

    public static class ItemWingErebus extends ItemWingVanilla {
        public ItemWingErebus(ArmorMaterial material, Wing wing) {
            super(material, wing);
        }
    }
}
