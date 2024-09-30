package btw.community.sockthing.sockscrops.block.blocks;

import net.minecraft.src.IconRegister;

public class StrawBaleBlock extends BaleBlock {
    public StrawBaleBlock(int id, String name) {
        super(id, name);
    }

    @Override
    public void registerIcons(IconRegister r) {
        blockIcon = sideIcon = r.registerIcon("straw_bale_side");
        topIcon = r.registerIcon("straw_bale_top");
    }
}