package btw.community.sockthing.sockscrops.mixins;

import btw.block.blocks.LeavesBlock;
import btw.community.sockthing.sockscrops.interfaces.BlockInterface;
import net.minecraft.src.BlockLeaves;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin extends BlockLeaves implements BlockInterface {
    protected LeavesBlockMixin(int par1) {
        super(par1);
    }

    @Override
    public boolean canBeReplacedByLeaves(int blockID) {
        return true;
    }
}
