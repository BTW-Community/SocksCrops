package btw.community.sockthing.sockscrops.item.items;

import btw.community.sockthing.sockscrops.block.blocks.SideShroomBlock;
import btw.crafting.util.FurnaceBurnTime;
import btw.item.items.PlaceAsBlockItem;
import net.minecraft.src.*;

public class SideShroomItemBlock extends ItemMultiTextureTile {

    public SideShroomItemBlock(int par1, Block par2Block, String[] names) {
        super(par1, par2Block, names);
        hasSubtypes = true;

        setBuoyant();
        setBellowsBlowDistance( 2 );
        setfurnaceburntime( FurnaceBurnTime.SMALL_FUEL );
        setFilterableProperties( FILTERABLE_SMALL | FILTERABLE_THIN );
    }

    public static String[] names = {
            "white","white","white","white",
            "black","black","black","black",
            "brown","brown","brown","brown",
            "red","red","red","red"
    };

    private String[] types = {"white", "black", "brown", "red" };

    private Icon[] shroom = new Icon[4];

    @Override
    public Icon getIconFromDamage(int par1) {
        if (par1 <= 3)
            return shroom[0];
        else if (par1 < 8)
            return shroom[1];
        else if (par1 < 12)
            return shroom[2];
        else return shroom[3];
    }

    @Override
    public void registerIcons(IconRegister register) {
        for (int i = 0; i < shroom.length; i++) {
            shroom[i] = register.registerIcon("sideshroom_" + types[i]);
        }
    }

}