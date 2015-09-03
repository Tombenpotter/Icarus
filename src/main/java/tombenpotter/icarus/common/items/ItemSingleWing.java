package tombenpotter.icarus.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import tombenpotter.icarus.Icarus;
import tombenpotter.icarus.common.IcarusItems;

import java.util.List;

public class ItemSingleWing extends Item {

    public IIcon[] icon = new IIcon[50];

    public ItemSingleWing(){
        setCreativeTab(Icarus.creativeTab);
        setUnlocalizedName(Icarus.name + ".singleWing");
        setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        for(int i = 0; i < IcarusItems.wingNames.size(); i++) {
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
}
