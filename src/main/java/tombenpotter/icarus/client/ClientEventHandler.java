package tombenpotter.icarus.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.lwjgl.opengl.GL11;
import tombenpotter.icarus.Icarus;
import tombenpotter.icarus.common.items.ItemWing;

public class ClientEventHandler {

    private static float renderTicks;
    private static long tickTime = 0L;

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        renderTicks = event.renderTickTime;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        tickTime += 1L;
    }

    private float render() {
        return (float) tickTime + renderTicks;
    }

    @SubscribeEvent
    public void renderPlayerWings(RenderPlayerEvent.Specials.Post event) {
        if (event.entityPlayer.inventory.armorInventory[2] != null && event.entityPlayer.inventory.armorInventory[2].getItem() instanceof ItemWing) {
            ItemWing itemWing = (ItemWing) event.entityPlayer.inventory.armorInventory[2].getItem();
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Icarus.texturePath + ":textures/items/wings/" + itemWing.wing.name + ".png"));
            GL11.glTranslatef(0F, -0.3125F, 0.125F);
            Tessellator tesselator = Tessellator.instance;

            float flap = event.entityPlayer.onGround ? 0 : (1.0F + (float) Math.cos(render() / 4.0F)) * 13.0F;

            GL11.glPushMatrix();
            GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-flap, 0.0F, 1.0F, 0.0F);
            tesselator.startDrawingQuads();
            tesselator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
            tesselator.addVertexWithUV(0.0D, 1.0D, 0.0D, 0.0D, 1.0D);
            tesselator.addVertexWithUV(1.0D, 1.0D, 0.0D, 1.0D, 1.0D);
            tesselator.addVertexWithUV(1.0D, 0.0D, 0.0D, 1.0D, 0.0D);
            tesselator.draw();
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glRotatef(flap, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(20.0F, 0.0F, 1.0F, 0.0F);
            tesselator.startDrawingQuads();
            tesselator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
            tesselator.addVertexWithUV(0.0D, 1.0D, 0.0D, 0.0D, 1.0D);
            tesselator.addVertexWithUV(-1.0D, 1.0D, 0.0D, 1.0D, 1.0D);
            tesselator.addVertexWithUV(-1.0D, 0.0D, 0.0D, 1.0D, 0.0D);
            tesselator.draw();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
    }
}
