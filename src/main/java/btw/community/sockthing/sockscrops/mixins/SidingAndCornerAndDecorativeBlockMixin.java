package btw.community.sockthing.sockscrops.mixins;

import btw.block.blocks.FenceBlock;
import btw.block.blocks.SidingAndCornerAndDecorativeBlock;
import btw.block.blocks.SidingAndCornerBlock;
import btw.client.render.util.RenderUtils;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.blocks.FenceRopeBlock;
import btw.community.sockthing.sockscrops.interfaces.RopeInterface;
import btw.item.BTWItems;
import btw.item.util.ItemUtils;
import btw.util.MiscUtils;
import btw.world.util.BlockPos;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SidingAndCornerAndDecorativeBlock.class)
public abstract class SidingAndCornerAndDecorativeBlockMixin extends SidingAndCornerBlock {
    @Shadow public abstract boolean renderBench(RenderBlocks renderBlocks, int i, int j, int k);

    private final double ROPE_KNOT_WIDTH = ( 2D / 16D ) * 1.25;
    public final double ROPE_HEIGHT = (2D / 16D);
    public final double ROPE_HALF_HEIGHT = (ROPE_HEIGHT / 2D);

    protected SidingAndCornerAndDecorativeBlockMixin(int iBlockID, Material material, String sTextureName, float fHardness, float fResistance, StepSound stepSound, String name) {
        super(iBlockID, material, sTextureName, fHardness, fResistance, stepSound, name);
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
        System.out.println("onBlockActivated");
        ItemStack equippedItem = player.getCurrentEquippedItem();

        int iTargetFacing = Block.getOppositeFacing( MiscUtils.convertPlacingEntityOrientationToBlockFacingReversed( player ) );

        if ( hasConnectedStringToFacing( world, i, j, k, iTargetFacing ) )
        {
            // remove the connected string and drop it at the player's feet

            if ( !world.isRemote )
            {
                int iStringCount = clearStringToFacingNoDrop( world, i, j, k, iTargetFacing );

                if ( iStringCount > 0 )
                {
                    ItemUtils.dropStackAsIfBlockHarvested( world, i, j, k, new ItemStack( BTWItems.rope.itemID, iStringCount, 0 ) );
                }

                world.playSoundAtEntity( player, "random.bow", 0.25F, world.rand.nextFloat() * 0.4F + 1.2F );
            }

            return true;
        }
        else if ( equippedItem != null && equippedItem.getItem().itemID == BTWItems.rope.itemID )
        {
            int iStringStackSize = equippedItem.stackSize;

            if ( iStringStackSize > 0 && iTargetFacing > 1 ) // iTargetFacing > 1 disables placing vertical rope between fences
            {
                //int iStakeFacing = GetFacing( world, i, j, k );

                // scan in the chosen direction for another stake

                int iDistanceToOtherStake = this.checkForValidConnectingStakeToFacing( world, i, j, k, iTargetFacing, iStringStackSize );

                if ( iDistanceToOtherStake <= 0 )
                {
                    // check alternate facings as we didn't find anything in the primary

                    int iYawOctant = MathHelper.floor_double( (double)( ( player.rotationYaw * 8F ) / 360F ) ) & 7;

                    if ( iYawOctant >= 0 && iYawOctant <= 3 )
                    {
                        iTargetFacing = 4;
                    }
                    else
                    {
                        iTargetFacing = 5;
                    }

                    iDistanceToOtherStake = this.checkForValidConnectingStakeToFacing( world, i, j, k, iTargetFacing, iStringStackSize );

                    if ( iDistanceToOtherStake <= 0 )
                    {
                        if ( iYawOctant >= 2 && iYawOctant <= 5 )
                        {
                            iTargetFacing = 2;
                        }
                        else
                        {
                            iTargetFacing = 3;
                        }
                    }

                    iDistanceToOtherStake = this.checkForValidConnectingStakeToFacing( world, i, j, k, iTargetFacing, iStringStackSize );

                    // scan alternate facings

//					if ( iDistanceToOtherStake <= 0 )
//					{
//						if ( player.rotationPitch > 0 )
//						{
//					    	iTargetFacing = 0;
//						}
//						else
//						{
//					    	iTargetFacing = 1;
//						}
//
//						iDistanceToOtherStake = this.CheckForValidConnectingStakeToFacing( world, i, j, k, iTargetFacing, iStringStackSize );
//					}
                }

                if ( iDistanceToOtherStake > 0 )
                {
                    if ( !world.isRemote )
                    {
                        // place the string blocks

                        FenceRopeBlock ropeBlock = (FenceRopeBlock)(SCBlocks.rope);

                        BlockPos tempPos = new BlockPos( i, j, k );

                        for ( int iTempDistance = 0; iTempDistance < iDistanceToOtherStake; iTempDistance++ )
                        {
                            tempPos.addFacingAsOffset( iTargetFacing );

                            int iTargetBlockID = world.getBlockId( tempPos.x, tempPos.y, tempPos.z );

                            if ( iTargetBlockID != ropeBlock.blockID )
                            {
                                // no notify here as it will disrupt the strings still being placed

                                world.setBlock( tempPos.x, tempPos.y, tempPos.z, ropeBlock.blockID, 0, 2 );
                            }

                            ropeBlock.setExtendsAlongFacing( world, tempPos.x, tempPos.y, tempPos.z, iTargetFacing, true, false );

                            if ( !player.capabilities.isCreativeMode )
                            {
                                equippedItem.stackSize--;
                            }
                        }

                        // cycle back through and give block change notifications

                        tempPos = new BlockPos( i, j, k );

                        for ( int iTempDistance = 0; iTempDistance < iDistanceToOtherStake; iTempDistance++ )
                        {
                            tempPos.addFacingAsOffset( iTargetFacing );

                            world.notifyBlockChange( tempPos.x, tempPos.y, tempPos.z, SCBlocks.rope.blockID );
                        }

                        world.playSoundAtEntity( player, "random.bow", 0.25F, world.rand.nextFloat() * 0.2F + 0.8F );
                    }
                    else
                    {
                        if ( !player.capabilities.isCreativeMode )
                        {
                            equippedItem.stackSize -= iDistanceToOtherStake;
                        }
                    }
                }

                return true;
            }
        }

        return false;
    }

    private int clearStringToFacingNoDrop( World world, int i, int j, int k, int iTargetFacing )
    {
        int iStringCount = 0;

        BlockPos tempPos = new BlockPos( i, j, k );

        do
        {
            tempPos.addFacingAsOffset( iTargetFacing );

            if ( world.getBlockId( tempPos.x, tempPos.y, tempPos.z ) == SCBlocks.rope.blockID )
            {
                Block block = Block.blocksList[world.getBlockId( tempPos.x, tempPos.y, tempPos.z )];

                FenceRopeBlock ropeBlock = (FenceRopeBlock)(SCBlocks.rope);
//                SCBlockGrapeLeaves leavesBlock = (SCBlockGrapeLeaves)(SCDefs.redGrapeLeaves);

                if ( ropeBlock.getExtendsAlongFacing( world, tempPos.x, tempPos.y, tempPos.z, iTargetFacing ) ) // || leavesBlock.GetExtendsAlongFacing( world, tempPos.x, tempPos.y, tempPos.z, iTargetFacing ) )
                {
                    if ( ropeBlock.getExtendsAlongOtherFacing( world, tempPos.x, tempPos.y, tempPos.z, iTargetFacing ) )
                    {
                        ropeBlock.setExtendsAlongFacing( world, tempPos.x, tempPos.y, tempPos.z, iTargetFacing, false, false );
                    }
                    else
                    {
                        world.setBlock( tempPos.x, tempPos.y, tempPos.z, 0, 0, 2 );
                    }
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }

            iStringCount++;
        }
        while ( iStringCount < 64 );

        if ( iStringCount > 0 )
        {
            // cycle back through and provide notifications to surrounding blocks

            tempPos = new BlockPos( i, j, k );

            for ( int iTempCount = 0; iTempCount < iStringCount; iTempCount++ )
            {
                tempPos.addFacingAsOffset( iTargetFacing );

                world.notifyBlockChange( tempPos.x, tempPos.y, tempPos.z, SCBlocks.rope.blockID );
            }
        }

        return iStringCount;
    }

    public boolean hasConnectedStringToFacing( IBlockAccess blockAccess, int i, int j, int k, int iFacing )
    {
        FenceRopeBlock ropeBlock = (FenceRopeBlock)(SCBlocks.rope);
//		SCBlockGrapeLeaves leavesBlock = (SCBlockGrapeLeaves)(SCDefs.grapeLeaves);
        BlockPos targetPos = new BlockPos( i, j, k );

        targetPos.addFacingAsOffset( iFacing );

        int iTargetBlockID = blockAccess.getBlockId( targetPos.x, targetPos.y, targetPos.z );

        boolean isGrapeLeaves = false; //TODO //Block.blocksList[blockAccess.getBlockId( targetPos.i, targetPos.j, targetPos.k )] instanceof SCBlockGrapeLeaves;
        boolean isRope = Block.blocksList[blockAccess.getBlockId( targetPos.x, targetPos.y, targetPos.z )] instanceof RopeInterface;

        if ( isGrapeLeaves || isRope )
        {
            return ropeBlock.getExtendsAlongFacing( blockAccess, targetPos.x, targetPos.y, targetPos.z, iFacing );
        }

        return false;
    }

    /*
     * returns the distance to the valid stake in the direction, 0 otherwise
     */
    private int checkForValidConnectingStakeToFacing( World world, int i, int j, int k, int iFacing, int iMaxDistance )
    {
        FenceRopeBlock ropeBlock = (FenceRopeBlock)(SCBlocks.rope);
        BlockPos tempPos = new BlockPos( i, j, k );
        boolean bFoundOtherStake = false;

        for ( int iDistanceToOtherStake = 0; iDistanceToOtherStake <= iMaxDistance; iDistanceToOtherStake++ )
        {
            tempPos.addFacingAsOffset( iFacing );

            if ( !world.isAirBlock( tempPos.x, tempPos.y, tempPos.z ) )
            {
                int iTargetBlockID = world.getBlockId( tempPos.x, tempPos.y, tempPos.z );
                Block targetBlock = Block.blocksList[iTargetBlockID];

                if ( targetBlock instanceof FenceBlock || (targetBlock instanceof SidingAndCornerAndDecorativeBlock && world.getBlockMetadata(tempPos.x, tempPos.y, tempPos.z) == SidingAndCornerAndDecorativeBlock.SUBTYPE_FENCE))
                {
                    return iDistanceToOtherStake;
                }
                else if ( iTargetBlockID == ropeBlock.blockID )
                {
                    if ( ropeBlock.getExtendsAlongFacing( world, tempPos.x, tempPos.y, tempPos.z, iFacing ) )
                    {
                        return 0;
                    }
                }
                else
                {
                    Block tempBlock = Block.blocksList[iTargetBlockID];

                    if ( !tempBlock.blockMaterial.isReplaceable() || tempBlock.blockMaterial.isLiquid() )
                    {
                        return 0;
                    }
                }
            }
        }

        return 0;
    }

    private Icon rope;
    private Icon ropeTop;

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons( IconRegister register )
    {
        super.registerIcons(register);
        rope = register.registerIcon( "fcBlockRope_side" );
        ropeTop = register.registerIcon("fcBlockRope_top" );
    }

    @Inject(method = "renderBlock", at = @At(value = "HEAD"))
    public void renderRope(RenderBlocks renderer, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        renderRopeConnections(renderer, i, j, k);
        renderer.clearOverrideBlockTexture();
        renderer.clearUVRotation();
    }

    private void renderRopeConnections( RenderBlocks renderer, int i, int j, int k )
    {
        IBlockAccess blockAccess = renderer.blockAccess;

        int iFacing = getFacing( blockAccess, i, j, k );

        if ( iFacing == 0 )
        {
            renderer.setUVRotateSouth( 3 );
            renderer.setUVRotateNorth( 3 );
            renderer.setUVRotateEast( 3 );
            renderer.setUVRotateWest( 3 );

            renderer.setUVRotateTop( 3 );
            renderer.setUVRotateBottom( 3 );

        }
        else if ( iFacing == 2 )
        {
        }
        else if ( iFacing == 3 )
        {

        }
        else if ( iFacing == 4 )
        {

        }
        else if (  iFacing == 5 )
        {

        }

        renderer.setRenderBounds( getBlockBoundsFromPoolBasedOnState( renderer.blockAccess, i, j, k ) );

        //renderer.renderStandardBlock( this, i, j, k );

        // render any attached string

        Icon stringTexture = rope;

        for ( int iStringFacing = 0; iStringFacing < 6; iStringFacing++ )
        {
            if ( hasConnectedStringToFacing( blockAccess, i, j, k, iStringFacing ) )
            {
                renderer.setRenderBounds( getBoundsFromPoolForStringToFacing(
                        iStringFacing ) );

                if ( iStringFacing == 4)
                {
                    renderer.setUVRotateTop( 2 );


                }else if (iStringFacing == 5)
                {
                    renderer.setUVRotateTop( 1 );
                }


                renderer.setUVRotateEast( 1 ); //North South are swapped somehow
                renderer.setUVRotateWest( 1 );
                renderer.setUVRotateNorth( 1 );
                renderer.setUVRotateSouth( 1 );

                RenderUtils.renderStandardBlockWithTexture( renderer, this, i, j, k, stringTexture );

                renderer.clearUVRotation();

                renderer.setRenderBounds(getRopeKnotBounds());
                RenderUtils.renderStandardBlockWithTexture( renderer, this, i, j, k, stringTexture );
            }

        }
    }

    private AxisAlignedBB getRopeKnotBounds()
    {
        AxisAlignedBB knotBox = AxisAlignedBB.getAABBPool().getAABB(
                0.5D - ROPE_KNOT_WIDTH, 0.25D + 0.125D, 0.5D - ROPE_KNOT_WIDTH,
                0.5D + ROPE_KNOT_WIDTH, 0.75D - 0.125D, 0.5F + ROPE_KNOT_WIDTH);

        return knotBox;
    }

    private AxisAlignedBB getBoundsFromPoolForStringToFacing( int iFacing )
    {
        AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB(
                0.5D - ROPE_HALF_HEIGHT, 0.5D, 0.5D - ROPE_HALF_HEIGHT,
                0.5D + ROPE_HALF_HEIGHT, 1D, 0.5F + ROPE_HALF_HEIGHT );

        box.tiltToFacingAlongY( iFacing );

        return box;
    }
}
