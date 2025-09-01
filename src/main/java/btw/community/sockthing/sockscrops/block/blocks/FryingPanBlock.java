package btw.community.sockthing.sockscrops.block.blocks;

import net.minecraft.src.*;

public class FryingPanBlock extends PrimitiveCookingContainer {
    public FryingPanBlock(int blockID, String name) {
        super(blockID, Material.iron);
        setUnlocalizedName(name);

        setHardness( 1F );

        initBlockBounds( 3/16D, 0D, 3/16D, 1D - 3/16D, 3/16D, 1D - 3/16D );

        setStepSound( soundMetalFootstep );
    }

    @Override
    public TileEntity createNewTileEntity(World var1) {
        return null;
    }
}
