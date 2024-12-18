package btw.community.sockthing.sockscrops.mixins;

import btw.block.blocks.GrateBlock;
import btw.block.blocks.PaneBlock;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.blocks.PaneWithPlantBlock;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PaneBlock.class)
public abstract class GrateBlockMixin extends BlockPane {
    public GrateBlockMixin(int iBlockID, String sTextureName, String sSideTextureName, Material material, boolean bCanDropItself) {
        super(iBlockID, sTextureName, sSideTextureName, material, bCanDropItself);
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iSide, float fClickX, float fClickY, float fClickZ )
    {
        ItemStack playerStack = player.inventory.getCurrentItem();

        if ( playerStack != null && world.getBlockMetadata(i, j, k) == 0 )
        {
            int iMetadataForStack = PaneWithPlantBlock.getMetadataForItemStack(playerStack);

            if ( iMetadataForStack > 0 )
            {
                Block targetBlock = Block.blocksList[world.getBlockId(i,j,k)];
                Block paneWithPlantBlock = PaneWithPlantBlock.getPaneBlock(targetBlock);

                if (paneWithPlantBlock != null){
                    world.setBlockAndMetadataWithNotify( i, j, k, paneWithPlantBlock.blockID, iMetadataForStack);

                    if ( !player.capabilities.isCreativeMode )
                    {
                        playerStack.stackSize--;

                        if ( playerStack.stackSize <= 0 )
                        {
                            player.inventory.setInventorySlotContents( player.inventory.currentItem, null );
                        }
                    }

                    return true;
                }

            }
        }

        return false;
    }
}
