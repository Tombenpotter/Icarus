package tombenpotter.icarus.common.items;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import tombenpotter.icarus.ConfigHandler;
import tombenpotter.icarus.Icarus;
import tombenpotter.icarus.api.wings.ISpecialWing;
import tombenpotter.icarus.api.wings.IWingHUD;
import tombenpotter.icarus.api.wings.Wing;
import tombenpotter.icarus.common.network.PacketHandler;
import tombenpotter.icarus.common.network.PacketJump;
import tombenpotter.icarus.common.util.EventHandler;
import tombenpotter.icarus.common.util.LogHelper;
import tombenpotter.icarus.common.util.TravellersHandler;
import tombenpotter.icarus.common.util.WingHelper;
import tombenpotter.icarus.common.util.cofh.StringHelper;
import travellersgear.api.ITravellersGear;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Optional.InterfaceList({
        @Optional.Interface(iface = "travellersgear.api.ITravellersGear", modid = "TravellersGear")
})
public abstract class ItemWing extends ItemArmor implements IWingHUD, ITravellersGear {

    private Wing wing;
    private ISpecialWing specialWing;
    public static final String NBT_ITEMSTACK = "Icarus_ItemStack";
    public static final String NBT_DAMAGE = "Icarus_Damage";
    public static final int flapsToDamage = 10;

    public ItemWing(ArmorMaterial material, Wing wing) {
        super(material, 3, 1);
        this.wing = wing;
        setUnlocalizedName(Icarus.name + ".wing." + getWing().name);
        setMaxDamage(getWing().durability);
        setCreativeTab(Icarus.creativeTab);
    }

    public Wing getWing() {
        return wing;
    }

    public Wing getWing(ItemStack stack) {
        return getWing();
    }

    public List tooltip(ItemStack stack) {
        List list = new ArrayList();
        list.add(" ");
        list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.height") + StringHelper.END + ": " + getWing(stack).maxHeight);
        list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.boost") + StringHelper.END + ": " + getWing(stack).jumpBoost);
        list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.glide") + StringHelper.END + ": " + getWing(stack).glideFactor);
        list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.drag.rain") + StringHelper.END + ": " + getWing(stack).rainDrag);
        list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.drag.water") + StringHelper.END + ": " + getWing(stack).waterDrag);
        list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.fall.reduction") + StringHelper.END + ": " + getWing(stack).fallReductionFactor);
        list.add(" ");
        return list;
    }

    public void handleJump(World world, EntityPlayer player, ItemStack stack) {
        if (world.isRemote) {
            if (Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed()) {
                double jumpBoost = getWing(stack).jumpBoost;
                int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(ConfigHandler.boostEnchantID, stack);
                if (enchantmentLevel > 0) {
                    jumpBoost += (double) enchantmentLevel / 20;
                }
                PacketHandler.INSTANCE.sendToServer(new PacketJump(jumpBoost, stack.getItem() instanceof ISpecialWing));
                if (stack.getItem() instanceof ISpecialWing) {
                    ISpecialWing specialWing = (ISpecialWing) stack.getItem();
                    if (!specialWing.canWingBeUsed(stack, player)) {
                        return;
                    }
                    specialWing.onWingFlap(stack, player);
                }
                player.motionY = jumpBoost;
                player.fallDistance = 0;
            }
        }
    }

    public void handleWater(World world, EntityPlayer player, ItemStack stack) {
        if (player.isInsideOfMaterial(Material.water)) {
            player.motionY = getWing(stack).waterDrag;
        }
    }

    public void handleWeather(World world, EntityPlayer player, ItemStack stack) {
        if (player.worldObj.isRaining()) {
            Field enableRain = ReflectionHelper.findField(BiomeGenBase.class, "field_76765_S", "enableRain", "ax");
            try {
                if (enableRain.getBoolean(world.getBiomeGenForCoords((int) player.posX, (int) player.posZ)) &&
                        world.canBlockSeeTheSky((int) player.posX, (int) player.posY, (int) player.posZ)) {
                    player.motionY = getWing(stack).rainDrag;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (player.worldObj.isThundering()) {
            if (world.getBiomeGenForCoords((int) player.posX, (int) player.posZ).canSpawnLightningBolt() &&
                    world.canBlockSeeTheSky((int) player.posX, (int) player.posY, (int) player.posZ)) {
                player.motionY = getWing(stack).rainDrag;
            }

            if (!player.onGround && world.canBlockSeeTheSky((int) player.posX, (int) player.posY, (int) player.posZ) && world.rand.nextInt(500) == 0) {
                world.addWeatherEffect(new EntityLightningBolt(world, player.posX, player.posY, player.posZ));
                //player.attackEntityFrom(DamageSource.magic, player.getMaxHealth() / 2);
                player.motionY -= 1.5;
            }
        }
    }

    public void handleHover(World world, EntityPlayer player, ItemStack stack) {
        if (!player.onGround && player.motionY < 0) {
            if (stack.getItem() instanceof ISpecialWing) {
                if (!((ISpecialWing) stack.getItem()).canWingBeUsed(stack, player)) {
                    return;
                }
                ((ISpecialWing) stack.getItem()).onWingHover(stack, player);
            }
            double glideFactor = getWing(stack).glideFactor;
            int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(ConfigHandler.hoverEnchantID, stack);
            if (enchantmentLevel > 0) {
                glideFactor -= 0.03 * enchantmentLevel;
            }
            player.motionY *= glideFactor;
        }
    }

    public void handleHeight(World world, EntityPlayer player, ItemStack stack) {
        if (player.posY > getWing(stack).maxHeight && world.isDaytime() && world.canBlockSeeTheSky((int) player.posX, (int) player.posY, (int) player.posZ)) {
            player.setFire(2);
            player.attackEntityFrom(DamageSource.inFire, 1.0F);
        }
    }

    public void handleTick(World world, EntityPlayer player, ItemStack stack) {
        if (stack.getItem() instanceof ISpecialWing) {
            ISpecialWing specialWing = (ISpecialWing) stack.getItem();
            if (!specialWing.canWingBeUsed(stack, player)) {
                return;
            }
            specialWing.onWingTick(stack, player);
        }
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        doArmorMethods(world, player, stack);
    }

    private void doArmorMethods(World world, EntityPlayer player, ItemStack stack) {
        handleTick(world, player, stack);
        if (player.capabilities.isCreativeMode || ConfigHandler.dimensionWingsDisabled.contains(world.provider.dimensionId)) {
            return;
        }
        handleJump(world, player, stack);
        handleWater(world, player, stack);
        handleWeather(world, player, stack);
        if (player.isSneaking() == EventHandler.getHoldSneakToHover(player)) {
            handleHover(world, player, stack);
        }
        if (!ConfigHandler.dimensionNoWingBurn.contains(world.provider.dimensionId)){
            handleHeight(world, player, stack);
        }
    }

    
    /** @return the Equipment slot or -1 if it isn't supposed to go into one.<br>
     * 0: Cloak
     * 1: Shoulders
     * 2: Vambraces
     * 3: Title
     */
    @Optional.Method(modid = "TravellersGear")
    @Override
    public int getSlot(ItemStack stack) {
        //prevent compounded wings from being added to travellers inv to fix derpy potential dual armor rendering
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey(NBT_ITEMSTACK)) {
            return -1;
        } else {
            return 0;
        }
    }

    //pretty much a word for word of the armortick but with a special method for handling the world object
    @Optional.Method(modid = "TravellersGear")
    @Override
    public void onTravelGearTick(EntityPlayer player, ItemStack stack) {
        World world = player.worldObj;
        doArmorMethods(world, player, stack);
    }
    
    @Optional.Method(modid = "TravellersGear")
    @Override
    public void onTravelGearEquip(EntityPlayer player, ItemStack stack) {
    }
    
    @Optional.Method(modid = "TravellersGear")
    @Override
    public void onTravelGearUnequip(EntityPlayer player, ItemStack stack) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Icarus.texturePath + ":doubleWings/" + getWing().name + "s");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey(NBT_ITEMSTACK)) {
            ItemStack armorStack = ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag(NBT_ITEMSTACK));
            Item armor = armorStack.getItem();

            //Because vanilla is stupid.
            if (armor == Items.leather_chestplate) {
                return "textures/models/armor/leather_layer_1.png";
            } else if (armor == Items.chainmail_chestplate) {
                return "textures/models/armor/chainmail_layer_1.png";
            } else if (armor == Items.iron_chestplate) {
                return "textures/models/armor/iron_layer_1.png";
            } else if (armor == Items.golden_chestplate) {
                return "textures/models/armor/gold_layer_1.png";
            } else if (armor == Items.diamond_chestplate) {
                return "textures/models/armor/diamond_layer_1.png";
            } else {
                return armor.getArmorTexture(armorStack, entity, slot, type);
            }
        }

        return Icarus.texturePath + ":textures/items/EmptyArmor.png";
    }

    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack, int armorSlot) {
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey(NBT_ITEMSTACK)) {
            ItemStack armorStack = ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag(NBT_ITEMSTACK));
            return armorStack.getItem().getArmorModel(entityLiving, stack, armorSlot);
        }

        return super.getArmorModel(entityLiving, stack, armorSlot);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean held) {
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey(NBT_ITEMSTACK)) {
            list.add(StringHelper.LIGHT_BLUE + StringHelper.localize("tooltip.icarus.render.armor") + StringHelper.END + ": " + ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag(NBT_ITEMSTACK)).getDisplayName());
        }

        if (ConfigHandler.showWingsStats) {
            if (!StringHelper.isShiftKeyDown()) {
                list.add(WingHelper.pressShiftForDetails());
            } else if (StringHelper.isShiftKeyDown()) {
                list.addAll(tooltip(stack));
            }
        }
    }

    @Override
    public List<String> getDisplayString(World clientWorld, EntityPlayer clientPlayer, ItemStack stack) {
        List<String> list = new ArrayList<String>();

        if (clientPlayer.fallDistance > 0.0F) {
            int fall = WingHelper.getFallDistanceWithWings(clientPlayer, this, stack);
            String str = StringHelper.localize("tooltip.icarus.fall.distance") + ": " + fall + StringHelper.END;
            if (fall > 0) {
                str = StringHelper.BOLD + StringHelper.ITALIC + StringHelper.LIGHT_RED + str;
            } else {
                str = StringHelper.LIGHT_GRAY + str;
            }
            list.add(str);
        }

        return list;
    }
}
