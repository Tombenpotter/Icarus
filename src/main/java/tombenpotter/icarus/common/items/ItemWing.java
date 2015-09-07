package tombenpotter.icarus.common.items;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import tombenpotter.icarus.Icarus;
import tombenpotter.icarus.api.ISpecialWing;
import tombenpotter.icarus.api.Wing;
import tombenpotter.icarus.common.network.PacketHandler;
import tombenpotter.icarus.common.network.PacketJump;
import tombenpotter.icarus.util.EventHandler;
import tombenpotter.icarus.util.IcarusWing;

public class ItemWing extends ItemArmor {

    public Wing wing;

    public ItemWing(ArmorMaterial material, IcarusWing wing) {
        super(material, 3, 1);
        setUnlocalizedName(Icarus.name + ".wing." + wing.name);
        setMaxDamage(wing.durability);
        setCreativeTab(Icarus.creativeTab);
        MinecraftForge.EVENT_BUS.register(this);

        this.wing = wing;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Icarus.texturePath + ":doubleWings/" + wing.name + "s");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return Icarus.texturePath + ":textures/items/EmptyArmor.png";
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (world.isRemote) {
            boolean isJumping = Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed();
            if (isJumping) {
                PacketHandler.INSTANCE.sendToServer(new PacketJump(wing.jumpBoost, itemStack.getItem() instanceof ISpecialWing));

                if(itemStack.getItem() instanceof ISpecialWing){
                    ISpecialWing specialWing = (ISpecialWing) itemStack.getItem();
                    if (!specialWing.canWingBeUsed(itemStack)) {
                        return;
                    }
                    specialWing.onWingFlap(itemStack);
                }

                player.motionY = wing.jumpBoost;
                player.fallDistance = 0;
            }
        }

        if (player.isInWater()) {
            player.motionY = wing.waterDrag;
        }

        if (player.worldObj.isRaining()) {
            if (world.canBlockSeeTheSky((int) player.posX, (int) player.posY, (int) player.posZ)) {
                player.motionY = wing.rainDrag;
            }
        }

        if (player.worldObj.isThundering()) {
            if (world.canBlockSeeTheSky((int) player.posX, (int) player.posY, (int) player.posZ)) {
                player.motionY = wing.rainDrag;
            }
            if (!player.onGround && world.rand.nextInt(250) == 0) {
                world.addWeatherEffect(new EntityLightningBolt(world, player.posX, player.posY, player.posZ));
                player.setHealth(5.0F);
                player.motionY -= 1.5;
            }
        }

        if (player.isSneaking() == EventHandler.holdShiftToHoverForPlayer.get(player.getCommandSenderName()) && !player.onGround && player.motionY < 0) {
            if (itemStack.getItem() instanceof ISpecialWing) {
                if (!((ISpecialWing) itemStack.getItem()).canWingBeUsed(itemStack)) {
                    return;
                }
                ((ISpecialWing) itemStack.getItem()).onWingHover(itemStack);
            }
            player.motionY *= wing.glideFactor;
        }

        if (player.posY > wing.maxHeight) {
            player.setFire(1);
            player.attackEntityFrom(DamageSource.inFire, 1.0F);
        }
    }

    @SubscribeEvent
    public void onFall(LivingFallEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer entity = (EntityPlayer) event.entity;
            if (entity.inventory.armorInventory[2] != null && entity.inventory.armorInventory[2].getItem() instanceof ItemWing) {
                event.distance *= wing.fallReductionFactor;
            }
        }
    }
}
