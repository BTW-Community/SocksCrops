package btw.community.sockthing.sockscrops.block.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.IconRegister;

public class StrawBaleBlock extends BaleBlock {
    public StrawBaleBlock(int blockID, String name) {
        super(blockID, name);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        blockIcon = sideIcon = register.registerIcon("straw_bale_side");
        topIcon = register.registerIcon("straw_bale_top");
    }
}