package tombenpotter.icarus.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import tombenpotter.icarus.ConfigHandler;
import tombenpotter.icarus.Icarus;
import tombenpotter.icarus.api.IcarusConstants;
import tombenpotter.icarus.api.wings.IWingHUD;
import tombenpotter.icarus.common.items.ItemWing;
import tombenpotter.icarus.common.util.HoverHandler;
import tombenpotter.icarus.common.util.IcarusHelper;

public class ClientEventHandler {

    private static final Minecraft minecraft = Minecraft.getMinecraft();
    private static float renderTicks;
    private static long tickTime = 0L;
    private static KeyBinding keyHover = new KeyBinding("key.icarus.hover", Keyboard.KEY_M, Icarus.name);

    public static void registerKeybindings() {
        ClientRegistry.registerKeyBinding(keyHover);
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        renderTicks = event.renderTickTime;

        if (ConfigHandler.showWingsHUD && event.phase == TickEvent.Phase.END && minecraft.currentScreen == null) {
            ItemStack stack = minecraft.thePlayer.inventory.armorInventory[2];
            if (stack != null && stack.getItem() instanceof IWingHUD) {
                IWingHUD hud = (IWingHUD) stack.getItem();

                int line = ConfigHandler.wingsHUDCoords[1];
                for (String string : hud.getDisplayString(minecraft.theWorld, minecraft.thePlayer, stack)) {
                    minecraft.fontRenderer.drawString(string, ConfigHandler.wingsHUDCoords[0], line++ * 10, 0, true);
                }
            }
        }
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
        ItemStack stack = event.entityPlayer.inventory.armorInventory[2];
        EntityPlayer player = event.entityPlayer;
        if (stack != null && stack.getItem() instanceof ItemWing && player != null) {
            ItemWing itemWing = (ItemWing) stack.getItem();
            Tessellator tesselator = Tessellator.instance;
            float flap = player.onGround ? 0 : player.motionY < 0.0 ? player.isSneaking() == ConfigHandler.holdKeyToHover ? (1.0F + (float) Math.cos(render() / 2.0F)) * 13.0F : 0 : (1.0F + (float) Math.cos(render() / 4.0F)) * 13.0F;

            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            int color = IcarusHelper.getWingColor(stack);
            float f1 = (float) (color >> 16 & 255) / 255.0F;
            float f2 = (float) (color >> 8 & 255) / 255.0F;
            float f3 = (float) (color & 255) / 255.0F;
            GL11.glColor4f(f1, f2, f3, 1.0F);

            minecraft.renderEngine.bindTexture(new ResourceLocation(Icarus.texturePath + ":textures/items/wings/" + itemWing.getWing(stack).name + ".png"));

            if (player.isSneaking()) {
                GL11.glRotatef(28.64789F, 1.0F, 0.0F, 0.0F);
            }

            if (stack.hasTagCompound() && stack.stackTagCompound.hasKey(IcarusConstants.NBT_ITEMSTACK)) {
                GL11.glTranslatef(0F, -0.3125F, 0.225F);
            } else {
                GL11.glTranslatef(0F, -0.3125F, 0.125F);
            }

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

    @SubscribeEvent
    public void onKeyPressed(InputEvent.KeyInputEvent event) {
        boolean hoverStatus = keyHover.getIsKeyPressed();
        boolean lastHoverState = HoverHandler.getHover(minecraft.thePlayer);
        if (hoverStatus != lastHoverState) {
            HoverHandler.setHover(minecraft.thePlayer, hoverStatus);
        }
    }
}
