package btw.community.sockthing.sockscrops.mixins;

import btw.block.blocks.GrateBlock;
import btw.block.blocks.PaneBlock;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.blocks.GrownPaneBlock;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GrateBlock.class)
public abstract class GrateBlockMixin extends PaneBlock {
    public GrateBlockMixin(int iBlockID, String sTextureName, String sSideTextureName, Material material, boolean bCanDropItself) {
        super(iBlockID, sTextureName, sSideTextureName, material, bCanDropItself);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
        ItemStack heldStack = player.getHeldItem();

        if (heldStack != null && heldStack.itemID == Block.vine.blockID)
        {
            world.setBlockAndMetadataWithNotify(x,y,z, SCBlocks.grownGrate.blockID, GrownPaneBlock.VINES);
            heldStack.stackSize--;
            return true;
        }

        return super.onBlockActivated(world, x, y, z, player, iFacing, fXClick, fYClick, fZClick);
    }
}
