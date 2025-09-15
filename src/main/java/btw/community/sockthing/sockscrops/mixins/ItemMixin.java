package btw.community.sockthing.sockscrops.mixins;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.blocks.BowlStackBlock;
import btw.community.sockthing.sockscrops.block.tileentities.BowlStackTileEntity;
import btw.community.sockthing.sockscrops.interfaces.ItemInterface;
import btw.item.items.PlaceAsBlockItem;
import btw.world.util.BlockPos;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin implements ItemInterface {
    @Inject(method = "onItemUse", at = @At(value = "HEAD"), cancellable = true)
    public void onItemUseonItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int facing, float clickX, float clickY, float clickZ, CallbackInfoReturnable<Boolean> cir) {
        if (itemStack.itemID == Item.bowlEmpty.itemID) {
            attemptToPlaceBowl(itemStack, player, world, x, y, z, facing, clickX, clickY, clickZ, cir);
        }
    }

    private void attemptToPlaceBowl(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int facing, float clickX, float clickY, float clickZ, CallbackInfoReturnable<Boolean> cir) {
        if (player.isUsingSpecialKey()){

            int iNewBlockID = SCBlocks.bowlStack.blockID;

            if ( itemStack.stackSize == 0 ||
                    ( player != null && !player.canPlayerEdit( x, y, z, facing, itemStack ) ) ||
                    ( y == 255 && Block.blocksList[iNewBlockID].blockMaterial.isSolid() ) )
            {
                cir.setReturnValue(false);
                cir.cancel();
            }

            BlockPos targetPos = new BlockPos( x, y, z );

            int iOldBlockID = world.getBlockId( x, y, z );
            Block oldBlock = Block.blocksList[iOldBlockID];

            if ( oldBlock != null )
            {
                if ( oldBlock.isGroundCover() )
                {
                    facing = 1;
                }
                else if ( !oldBlock.blockMaterial.isReplaceable() )
                {
                    targetPos.addFacingAsOffset(facing);
                }
            }

//            if ((!requireNoEntitiesInTargetBlock || isTargetFreeOfObstructingEntities(world, targetPos.x, targetPos.y, targetPos.z) ) &&
            if (isTargetFreeOfObstructingEntities(world, targetPos.x, targetPos.y, targetPos.z) &&
                    world.canPlaceEntityOnSide(iNewBlockID, targetPos.x, targetPos.y, targetPos.z, false, facing, player, itemStack) )
            {
                Block newBlock = Block.blocksList[iNewBlockID];

                int iNewMetadata = 0;

                iNewMetadata = newBlock.onBlockPlaced(world, targetPos.x, targetPos.y, targetPos.z, facing, clickX, clickY, clickZ, iNewMetadata);

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

                BowlStackBlock.addBowlToSlot(world, targetPos.x, targetPos.y, targetPos.z, clickX, clickZ, (BowlStackTileEntity) world.getBlockTileEntity(targetPos.x, targetPos.y, targetPos.z), 1);

                cir.setReturnValue(true);
            }

            cir.setReturnValue(false);
        }
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

    // -- ItemInterface -- //

    /**
     * Used to allow Blocks to be placed in a specific armorSlot
     * @param armorType 0: Helmet, 1: Chest, 2: Legs, 3: boots
     * @param itemStack
     */
    @Override
    public boolean isValidForArmorSlot(int armorType, ItemStack itemStack) {
        return false;
    }

    /**
     * Example Pumpkin: "%blur%/misc/pumpkinblur.png"
     * @return Returns the directory string of the blur overlay texture that should be used when this is worn in the helmet slot
     */
    @Override
    public String getBlurOverlay(ItemStack itemStack) {
        return null;
    }

    /**
     * Returns true or false depending if the blur overlay should be shown when the player disabled the GUI
     */
    @Override
    public boolean showBlurOverlayWithGuiDisabled(ItemStack itemStack) {
        return false;
    }
}
