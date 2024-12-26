package btw.community.sockthing.sockscrops.block.blocks;

import btw.client.render.util.RenderUtils;
import btw.world.util.WorldUtils;
import net.minecraft.src.*;

import java.util.Random;

public abstract class PieBaseBlock extends Block {

    public static final float pieHeight = ( 4F / 16F );
    public static final float pieWidth = ( 12F / 16F );
    public static final float pieHalfWidth = ( pieWidth / 2F );
    public static final float pieLength = ( 12F / 16F );
    public static final float pieHalfLength = ( pieLength / 2F );

    protected PieBaseBlock(int blockID) {
        super(blockID, Material.cake);

        setHardness( 0.6F );
        setShovelsEffectiveOn( true );
    }

    @Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
        if ( !WorldUtils.doesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
        {
            dropBlockAsItem( world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );

            world.setBlockToAir( i, j, k );
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {

        if ( !canBlockStay(world,x,y,z))
        {
            return false;
        }
        else return super.canPlaceBlockAt(world, x, y, z);
    }

    @Override
    public void onBlockAdded( World world, int i, int j, int k )
    {
        // check beneath for valid block due to piston pushing not sending a notify
        if ( !WorldUtils.doesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
            world.setBlockWithNotify(i, j, k, 0);
        }
    }

    //Copied from FCBlockUnfiredPottery
    @Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iBlockID )
    {
        if ( !WorldUtils.doesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
        {
            // Unfired pottery can both be pushed by pistons and needs to rest on a block, which can create weird
            // interactions if the block below is pushed at the same time as this one,
            // creating a ghost block on the client. Delay the popping of the block to next tick prevent this.

            if( !world.isUpdatePendingThisTickForBlock( i, j, k, blockID ) )
            {
                world.scheduleBlockUpdate( i, j, k, blockID, 1 );
            }
        }
    }

    public boolean canBlockStay(World world, int x, int y, int z)
    {
        return WorldUtils.doesBlockHaveLargeCenterHardpointToFacing( world, x, y - 1, z, 1, true );
    }

    @Override
    public boolean canBePistonShoveled( World world, int i, int j, int k )
    {
        return true;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered( IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide )
    {
        return currentBlockRenderer.shouldSideBeRenderedBasedOnCurrentBounds( iNeighborI, iNeighborJ, iNeighborK, iSide );
    }

    @Override
    public AxisAlignedBB getBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k )
    {
        return AxisAlignedBB.getAABBPool().getAABB(
                ( 0.5F - pieHalfWidth ), 0.0F, ( 0.5F - pieHalfLength ),
                ( 0.5F + pieHalfWidth ), pieHeight, ( 0.5F + pieHalfLength ) );
    }

    public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( )
    {
        return AxisAlignedBB.getAABBPool().getAABB(
                ( 0.5F - pieHalfWidth ), 0.0F, ( 0.5F - pieHalfLength ),
                ( 0.5F + pieHalfWidth ), pieHeight, ( 0.5F + pieHalfLength ) );
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
        renderer.setRenderBounds(GetBlockBoundsFromPoolBasedOnState());
        renderer.renderStandardBlock(this, i, j, k);
        return true;
    }


    @Override
    public void renderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness) {
        renderer.setRenderBounds(GetBlockBoundsFromPoolBasedOnState());
        RenderUtils.renderInvBlockWithMetadata(renderer, this, -0.5F, -0.5F, -0.5F, iItemDamage);
    }


}
