package btw.community.sockthing.sockscrops.block.blocks;

import btw.community.sockthing.sockscrops.interfaces.BlockInterface;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.IconRegister;
import net.minecraft.src.ItemStack;

public class StrawBaleBlock extends BaleBlock implements BlockInterface {
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