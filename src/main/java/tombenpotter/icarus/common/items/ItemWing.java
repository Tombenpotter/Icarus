package tombenpotter.icarus.common.items;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import tombenpotter.icarus.ConfigHandler;
import tombenpotter.icarus.Icarus;
import tombenpotter.icarus.api.wings.ISpecialWing;
import tombenpotter.icarus.api.wings.Wing;
import tombenpotter.icarus.common.network.PacketHandler;
import tombenpotter.icarus.common.network.PacketJump;
import tombenpotter.icarus.common.util.EventHandler;
import tombenpotter.icarus.common.util.IcarusWing;
import tombenpotter.icarus.common.util.cofh.StringHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

    public static String pressShiftForDetails() {
        return StringHelper.LIGHT_GRAY + StringHelper.localize("tooltip.icarus.press") + " " +
                StringHelper.LIGHT_BLUE + StringHelper.ITALIC + StringHelper.localize("tooltip.icarus.shift") + StringHelper.END + " " +
                StringHelper.LIGHT_GRAY + StringHelper.localize("tooltip.icarus.details") + StringHelper.END;
    }

    public List tooltip() {
        List list = new ArrayList();
        list.add(" ");
        list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.height") + StringHelper.END + ": " + wing.maxHeight);
        list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.boost") + StringHelper.END + ": " + wing.jumpBoost);
        list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.glide") + StringHelper.END + ": " + wing.glideFactor);
        list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.drag.rain") + StringHelper.END + ": " + wing.rainDrag);
        list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.drag.water") + StringHelper.END + ": " + wing.waterDrag);
        list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.fall.reduction") + StringHelper.END + ": " + wing.fallReductionFactor);
        list.add(" ");
        return list;
    }

    @SideOnly(Side.CLIENT)
    public void handleJump(World world, EntityPlayer player, ItemStack stack) {
        if (world.isRemote) {
            if (Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed()) {
                double jumpBoost = wing.jumpBoost;
                int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(ConfigHandler.boostEnchantID, stack);
                if (enchantmentLevel > 0) {
                    jumpBoost += (double) enchantmentLevel / 20;
                }

                PacketHandler.INSTANCE.sendToServer(new PacketJump(jumpBoost, stack.getItem() instanceof ISpecialWing));

                if (stack.getItem() instanceof ISpecialWing) {
                    ISpecialWing specialWing = (ISpecialWing) stack.getItem();
                    if (!specialWing.canWingBeUsed(stack)) {
                        return;
                    }
                    specialWing.onWingFlap(stack);
                }

                player.motionY = jumpBoost;
                player.fallDistance = 0;
            }
        }
    }

    public void handleWater(World world, EntityPlayer player, ItemStack stack) {
        if (player.isInWater()) {
            player.motionY = wing.waterDrag;
        }
    }

    public void handleWeather(World world, EntityPlayer player, ItemStack stack) {
        if (player.worldObj.isRaining()) {
            Field enableRain = ReflectionHelper.findField(BiomeGenBase.class, "enableRain");
            try {
                if (enableRain.getBoolean(world.getBiomeGenForCoords((int) player.posX, (int) player.posZ)) &&
                        world.canBlockSeeTheSky((int) player.posX, (int) player.posY, (int) player.posZ)) {
                    player.motionY = wing.rainDrag;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (player.worldObj.isThundering()) {
            if (world.getBiomeGenForCoords((int) player.posX, (int) player.posZ).canSpawnLightningBolt() &&
                    world.canBlockSeeTheSky((int) player.posX, (int) player.posY, (int) player.posZ)) {
                player.motionY = wing.rainDrag;
            }

            if (!player.onGround && world.rand.nextInt(250) == 0) {
                world.addWeatherEffect(new EntityLightningBolt(world, player.posX, player.posY, player.posZ));
                player.setHealth(5.0F);
                player.motionY -= 1.5;
            }
        }
    }

    public void handleHover(World world, EntityPlayer player, ItemStack stack) {
        if (player.isSneaking() == EventHandler.getHoldSneakToHover(player) && !player.onGround && player.motionY < 0) {
            if (stack.getItem() instanceof ISpecialWing) {
                if (!((ISpecialWing) stack.getItem()).canWingBeUsed(stack)) {
                    return;
                }
                ((ISpecialWing) stack.getItem()).onWingHover(stack);
            }

            double glideFactor = wing.glideFactor;
            int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(ConfigHandler.hoverEnchantID, stack);
            if (enchantmentLevel > 0) {
                glideFactor -= 0.03 * enchantmentLevel;
            }
            player.motionY *= glideFactor;
        }
    }

    public void handleHeight(World world, EntityPlayer player, ItemStack stack) {
        if (player.posY > wing.maxHeight) {
            player.setFire(1);
            player.attackEntityFrom(DamageSource.inFire, 1.0F);
        }
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        handleJump(world, player, stack);
        handleWater(world, player, stack);
        handleWeather(world, player, stack);
        handleHover(world, player, stack);
        handleHeight(world, player, stack);
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
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean held) {
        if (!StringHelper.isShiftKeyDown()) {
            list.add(pressShiftForDetails());
        } else if (StringHelper.isShiftKeyDown()) {
            list.addAll(tooltip());
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
