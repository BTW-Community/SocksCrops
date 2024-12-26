package btw.community.sockthing.sockscrops.item.items;

import btw.world.util.BlockPos;
import net.minecraft.src.*;

public class PlacableFoodItem extends ItemFood {
    private int blockID;
    private int blockMeta;

    public PlacableFoodItem(int itemID, int hunger, float sat, boolean wolf, int blockID, int blockMeta, String name) {
        super(itemID, hunger, sat, wolf);
        setUnlocalizedName(name);
        this.blockID = blockID;
        this.blockMeta = blockMeta;
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ )
    {
        int iNewBlockID = getBlockIDToPlace(itemStack.getItemDamage(), iFacing, fClickX, fClickY, fClickZ);

        if ( itemStack.stackSize == 0 ||
                ( player != null && !player.canPlayerEdit( i, j, k, iFacing, itemStack ) ) ||
                ( j == 255 && Block.blocksList[iNewBlockID].blockMaterial.isSolid() ) )
        {
            return false;
        }

        BlockPos targetPos = new BlockPos( i, j, k );

        int iOldBlockID = world.getBlockId( i, j, k );
        Block oldBlock = Block.blocksList[iOldBlockID];

        if ( oldBlock != null )
        {
            if ( oldBlock.isGroundCover() )
            {
                iFacing = 1;
            }
            else if ( !oldBlock.blockMaterial.isReplaceable() )
            {
                targetPos.addFacingAsOffset(iFacing);
            }
        }

        boolean requireNoEntitiesInTargetBlock = true;

        if ((!requireNoEntitiesInTargetBlock || isTargetFreeOfObstructingEntities(world, targetPos.x, targetPos.y, targetPos.z) ) &&
                world.canPlaceEntityOnSide(iNewBlockID, targetPos.x, targetPos.y, targetPos.z, false, iFacing, player, itemStack) )
        {
            Block newBlock = Block.blocksList[iNewBlockID];

            int iNewMetadata = getMetadata( itemStack.getItemDamage() );

            iNewMetadata = newBlock.onBlockPlaced(world, targetPos.x, targetPos.y, targetPos.z, iFacing, fClickX, fClickY, fClickZ, iNewMetadata);

            iNewMetadata = newBlock.preBlockPlacedBy(world, targetPos.x, targetPos.y, targetPos.z, iNewMetadata, player);

            if ( world.setBlockAndMetadataWithNotify(targetPos.x, targetPos.y,
                    targetPos.z, iNewBlockID, iNewMetadata) )
            {
                if (world.getBlockId(targetPos.x, targetPos.y, targetPos.z) == iNewBlockID )
                {
                    newBlock.onBlockPlacedBy(world, targetPos.x, targetPos.y,
                            targetPos.z, player, itemStack);

                    newBlock.onPostBlockPlaced(world, targetPos.x, targetPos.y, targetPos.z, iNewMetadata);

                    // Panick animals when blocks are placed near them
                    world.notifyNearbyAnimalsOfPlayerBlockAddOrRemove(player, newBlock, targetPos.x, targetPos.y, targetPos.z);
                }

                playPlaceSound(world, targetPos.x, targetPos.y, targetPos.z, newBlock);

                itemStack.stackSize--;
            }

            return true;
        }

        return super.onItemUse(itemStack, player, world, i, j, k, iFacing, fClickX, fClickY, fClickZ );
    }

    @Override
    public boolean canItemBeUsedByPlayer(World world, int i, int j, int k, int iFacing, EntityPlayer player, ItemStack stack)
    {
        return canPlaceItemBlockOnSide( world, i, j, k, iFacing, player, stack );
    }

    //------------- Class Specific Methods ------------//

    public boolean canPlaceItemBlockOnSide( World world, int i, int j, int k, int iFacing, EntityPlayer player, ItemStack stack )
    {
        int iTargetBlockID = world.getBlockId( i, j, k );
        Block iTargetBlock = Block.blocksList[iTargetBlockID];
        BlockPos targetPos = new BlockPos( i, j, k );

        if ( iTargetBlock != null )
        {
            if ( iTargetBlock.isGroundCover() )
            {
                iFacing = 1;
            }
            else if ( !iTargetBlock.blockMaterial.isReplaceable() )
            {
                targetPos.addFacingAsOffset(iFacing);
            }
        }

        // the following places the click spot right in the center, which while technically not correct, shouldn't
        // make much of a difference given vanilla ignores it entirely
        int iNewBlockID = getBlockIDToPlace(stack.getItemDamage(), iFacing, 0.5F, 0.5F, 0.5F);

        return world.canPlaceEntityOnSide(iNewBlockID, targetPos.x, targetPos.y, targetPos.z, false, iFacing, (Entity)null, stack);
    }

    public int getBlockIDToPlace(int iItemDamage, int iFacing, float fClickX, float fClickY, float fClickZ)
    {
        return this.blockID;
    }

    @Override
    public int getMetadata(int par1) {
        return blockMeta;
    }

    protected boolean isTargetFreeOfObstructingEntities(World world, int i, int j, int k)
    {
        AxisAlignedBB blockBounds = AxisAlignedBB.getAABBPool().getAABB(
                (double)i, (double)j, (double)k, (double)(i + 1), (double)( j + 1 ), (double)(k + 1) );

        return world.checkNoEntityCollision( blockBounds );
    }

    protected void playPlaceSound(World world, int i, int j, int k, Block block)
    {
        StepSound stepSound = block.getStepSound(world, i, j, k);

        world.playSoundEffect( (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, stepSound.getPlaceSound(),
                ( stepSound.getPlaceVolume() + 1F ) / 2F, stepSound.getPlacePitch() * 0.8F );
    }
}
