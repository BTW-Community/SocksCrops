package btw.community.sockthing.sockscrops.block.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.IconRegister;

public class HayBaleBlock extends BaleBlock {

    public HayBaleBlock(int blockID, String name) {
        super(blockID, name);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        blockIcon = sideIcon = register.registerIcon("hay_bale_side");
        topIcon = register.registerIcon("hay_bale_top");
    }


}