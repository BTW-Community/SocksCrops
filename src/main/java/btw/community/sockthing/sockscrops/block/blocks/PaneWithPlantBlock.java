package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.BTWBlocks;
import btw.block.blocks.GrateBlock;
import btw.block.blocks.PaneBlock;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.item.BTWItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

public class PaneWithPlantBlock extends PaneBlock {
    private final Block paneBlock;
    private final int paneMetadata;

    public PaneWithPlantBlock(int blockID, Material material, String name, Block paneBlock, int paneMetadata, String topTexture, String sideTexture) {
        super( blockID, topTexture, sideTexture, material, false );

        setUnlocalizedName(name);

        this.paneBlock = paneBlock;
        this.paneMetadata = paneMetadata;
    }

    public static Block getPaneBlock(Block block) {
        if (block.blockID == BTWBlocks.gratePane.blockID) return SCBlocks.grownGrate;
        return null;
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iSide, float fClickX, float fClickY, float fClickZ )
    {
        ItemStack playerStack = player.inventory.getCurrentItem();

        if ( playerStack != null && world.getBlockMetadata(i, j, k) == 0 )
        {
            int iMetadataForStack = getMetadataForItemStack(playerStack);

            if ( iMetadataForStack > 0 )
            {
                world.SetBlockMetadataWithNotify( i, j, k, iMetadataForStack, 2 );

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

        return false;
    }

    public static int getMetadataForItemStack(ItemStack stack)
    {
        int itemID = stack.getItem().itemID;

        if ( itemID == BTWItems.redMushroom.itemID )
        {
            return 7;
        }
        else if ( itemID == BTWItems.brownMushroom.itemID )
        {
            return 8;
        }
        else
        {
            return getMetaForPlant( stack );
        }
    }

    public static ItemStack getPlantForMeta(int par0)
    {
        switch (par0)
        {
            case 1:
                return new ItemStack(Block.plantRed);

            case 2:
                return new ItemStack(Block.plantYellow);

            case 3:
                return new ItemStack(BTWBlocks.oakSapling);

            case 4:
                return new ItemStack(BTWBlocks.spruceSapling);

            case 5:
                return new ItemStack(BTWBlocks.birchSapling);

            case 6:
                return new ItemStack(BTWBlocks.jungleSapling);

            case 7:
                return new ItemStack(Block.mushroomRed);

            case 8:
                return new ItemStack(Block.mushroomBrown);

            case 9:
                return new ItemStack(Block.cactus);

            case 10:
                return new ItemStack(Block.deadBush);

            case 11:
                return new ItemStack(Block.tallGrass, 1, 2);

            default:
                return null;
        }
    }

    public static int getMetaForPlant(ItemStack par0ItemStack)
    {
        int var1 = par0ItemStack.getItem().itemID;

        if (var1 == Block.plantRed.blockID)
        {
            return 1;
        }
        else if (var1 == Block.plantYellow.blockID)
        {
            return 2;
        }
        else if (var1 == Block.cactus.blockID)
        {
            return 9;
        }
        else if (var1 == Block.mushroomBrown.blockID)
        {
            return 8;
        }
        else if (var1 == Block.mushroomRed.blockID)
        {
            return 7;
        }
        else if (var1 == Block.deadBush.blockID)
        {
            return 10;
        }
        else if (var1 == BTWBlocks.oakSapling.blockID) {
            return 3;
        }
        else if (var1 == BTWBlocks.spruceSapling.blockID) {
            return 4;
        }
        else if (var1 == BTWBlocks.birchSapling.blockID) {
            return 5;
        }
        else if (var1 == BTWBlocks.jungleSapling.blockID) {
            return 6;
        }
        else
        {
            if (var1 == Block.tallGrass.blockID)
            {
                switch (par0ItemStack.getItemDamage())
                {
                    case 2:
                        return 11;
                }
            }

            return 0;
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean renderBlock(RenderBlocks renderer, int x, int y, int z) {
        int meta = renderer.blockAccess.getBlockMetadata(x,y,z);
        if (getPlantForMeta(meta) != null && Block.blocksList[getPlantForMeta(meta).itemID] != null) {
            renderer.renderCrossedSquares(Block.blocksList[getPlantForMeta(meta).itemID], x, y, z);
        }
        return super.renderBlock(renderer, x, y, z);
    }
}
