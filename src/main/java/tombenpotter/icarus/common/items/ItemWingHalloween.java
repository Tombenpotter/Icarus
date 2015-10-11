package tombenpotter.icarus.common.items;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import tombenpotter.icarus.api.wings.Wing;

import java.util.List;

public class ItemWingHalloween extends ItemWingVanilla {

    public double distanceFromEntity = 8.0D;

    public ItemWingHalloween(ArmorMaterial material, Wing wing) {
        super(material, wing);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.entityLiving instanceof EntityCreature) {
            EntityCreature living = (EntityCreature) event.entityLiving;
            List<EntityPlayer> list = (List<EntityPlayer>) living.worldObj.getEntitiesWithinAABB(EntityPlayer.class, living.boundingBox.expand(this.distanceFromEntity, 3.0D, this.distanceFromEntity));
            if (!list.isEmpty()) {
                for (EntityPlayer player : list) {
                    ItemStack wingStack = player.inventory.armorInventory[2];
                    if (wingStack != null && wingStack.getItem() instanceof ItemWingHalloween) {
                        Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(living, 16, 7, Vec3.createVectorHelper(player.posX, player.posY, player.posZ));
                        if (vec3 != null && player.getDistanceSq(vec3.xCoord, vec3.yCoord, vec3.zCoord) >= player.getDistanceSqToEntity(living)) {
                            PathNavigate pathNavigate = living.getNavigator();
                            PathEntity pathEntity = pathNavigate.getPathToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord);
                            if (pathEntity != null && pathEntity.isDestinationSame(vec3)) {
                                pathNavigate.setPath(pathEntity, 2.0D);
                            }
                        }
                    }
                }
            }
        }
    }
}
