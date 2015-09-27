package tombenpotter.icarus.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import tombenpotter.icarus.Icarus;
import tombenpotter.icarus.common.IcarusItems;

import java.util.HashMap;
import java.util.List;

public class ItemSingleWing extends Item {

    public IIcon[] icon = new IIcon[50];
    private HashMap<Integer, String> tooltipForMeta = new HashMap<Integer, String>();

    public ItemSingleWing() {
        setCreativeTab(Icarus.creativeTab);
        setUnlocalizedName(Icarus.name + ".singleWing");
        setHasSubtypes(true);
    }

    public void addTooltip(int meta, String tooltip) {
        tooltipForMeta.put(meta, tooltip);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        for (int i = 0; i < IcarusItems.wingNames.size(); i++) {
            this.icon[i] = ri.registerIcon(Icarus.texturePath + ":wings/" + IcarusItems.wingNames.get(i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String name = IcarusItems.wingNames.get(stack.getItemDamage());
        return getUnlocalizedName() + "." + name;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return this.icon[meta];
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List list) {
        for (int i = 0; i < IcarusItems.wingNames.size(); i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean held) {
        for (int i : tooltipForMeta.keySet()) {
            if (i == stack.getItemDamage()) {
                list.add(tooltipForMeta.get(i));
            }
        }
    }
}
