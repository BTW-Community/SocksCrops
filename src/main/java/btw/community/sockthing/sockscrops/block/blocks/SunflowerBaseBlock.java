package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.blocks.CropsBlock;
import btw.block.blocks.WeedsBlock;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.item.SCItems;
import btw.community.sockthing.sockscrops.item.items.KnifeItem;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import btw.item.items.ShearsItem;
import btw.item.util.ItemUtils;
import net.minecraft.src.*;

import java.util.Random;

public abstract class SunflowerBaseBlock extends CropsBlock {
    protected String name;

    public SunflowerBaseBlock(int iBlockID, String name) {
        super(iBlockID);
        setUnlocalizedName(name);

        this.name = name;
    }

    @Override
    public int idDropped(int iMetadata, Random rand, int iFortuneModifier) {

        return 0;
    }


    protected boolean canGrowOnBlock(World world, int i, int j, int k )
    {
        Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];

        if (isTopBlock())
        {
            return blockOn != null && blockOn instanceof SunflowerBaseBlock;
        }

        return blockOn != null && blockOn.canDomesticatedCropsGrowOnBlock( world, i, j, k );
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, int i, int j, int k, int iMetadata )
    {
        super.harvestBlock( world, player, i, j, k, iMetadata );

        if( !world.isRemote )
        {
            if ( world.getBlockId( i, j - 1, k ) == SCBlocks.sunflowerCrop.blockID )
            {
                if (isFullyGrown(iMetadata)) dropSunflower(world, player, i, j, k);
                world.setBlockToAir(i, j - 1, k);
            }

            //TODO: No idea why the below doesn't trigger
            if (world.getBlockId( i, j + 1, k ) == SCBlocks.sunflowerTopCrop.blockID){
                int metaAbove = world.getBlockMetadata(i,j + 1,k);
                if (getGrowthLevel( metaAbove ) >= getMaxGrowthStage()){
                    dropSunflower(world, player, i, j, k);
                }
            }
        }
    }

    static void dropSunflower(World world, EntityPlayer player, int i, int j, int k) {
        ItemStack equippedItem = player.getCurrentEquippedItem();
        if (  equippedItem!= null && (equippedItem.getItem() instanceof ShearsItem || equippedItem.getItem() instanceof KnifeItem))
        {
            ItemUtils.dropSingleItemAsIfBlockHarvested(world, i, j, k, SCItems.sunflower.itemID, 0);
            player.getCurrentEquippedItem().damageItem(1, player);
        }
    }

    protected abstract boolean isTopBlock();

    protected abstract int getMaxGrowthStage();

    protected void incrementGrowthLevel(World world, int i, int j, int k )
    {
        int iGrowthLevel = getGrowthLevel( world, i, j, k ) + 1;

        setGrowthLevel( world, i, j, k, iGrowthLevel );

        if ( isFullyGrown( world, i, j, k ) )
        {
            Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];

            if ( blockBelow != null )
            {
                blockBelow.notifyOfFullStagePlantGrowthOn( world, i, j - 1, k, this );

                if (!isTopBlock())
                {
                    int meta = updateRotationForTime(world);
                    world.setBlockAndMetadata(i, j + 1, k, SCBlocks.sunflowerTopCrop.blockID, meta);
                }
            }
        }
    }

    public static int updateRotationForTime( World world )
    {
        int timeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );

        boolean isMorning = timeOfDay >= 0 && timeOfDay < 3000;
        boolean isPreNoon = timeOfDay >= 3000 && timeOfDay < 6000;
        boolean isAfterNoon = timeOfDay >= 6000 && timeOfDay < 9000;
        boolean isEvening = timeOfDay >= 9000 && timeOfDay < 12000;

        boolean earlyNight = timeOfDay >= 12000 && timeOfDay < 15000;
        boolean preMoon = timeOfDay >= 15000 && timeOfDay < 18000;
        boolean afterMoon = timeOfDay >= 18000 && timeOfDay < 21000;
        boolean lateNight = timeOfDay >= 21000 && timeOfDay < 24000;

        int rotation = 0;

        if (isMorning) rotation = 0;
        else if (isPreNoon) rotation = 1;
        else if (isAfterNoon) rotation = 2;
        else if (isEvening) rotation = 3;
        else if (earlyNight) rotation = 3;
        else if (preMoon) rotation = 2;
        else if (afterMoon) rotation = 1;
        else if (lateNight) rotation = 0;

        return rotation;
    }

    @Override
    public boolean isBlockHydratedForPlantGrowthOn(World world, int i, int j, int k) {
        return !isTopBlock();
    }

    protected int getGrowthLevel(IBlockAccess blockAccess, int i, int j, int k )
    {
        return getGrowthLevel( blockAccess.getBlockMetadata( i, j, k ) );
    }

    protected int getGrowthLevel( int meta )
    {
        return meta & 7;
    }

    protected void setGrowthLevel( World world, int i, int j, int k, int iLevel )
    {
        int iMetadata = world.getBlockMetadata( i, j, k ) & (~7); // filter out old level

        world.setBlockMetadataWithNotify( i, j, k, iMetadata | iLevel );

    }

    protected void setGrowthLevelNoNotify( World world, int i, int j, int k, int iLevel )
    {
        int iMetadata = world.getBlockMetadata( i, j, k ) & (~7); // filter out old level

        world.setBlockMetadata( i, j, k, iMetadata | iLevel );
    }

    protected boolean isFullyGrown( World world, int i, int j, int k )
    {
        return isFullyGrown( world.getBlockMetadata( i, j, k ) );
    }

    protected boolean isFullyGrown( int iMetadata )
    {
        return getGrowthLevel( iMetadata ) >= getMaxGrowthStage();
    }

    @Override
    public AxisAlignedBB getBlockBoundsFromPoolBasedOnState(
            IBlockAccess blockAccess, int i, int j, int k )
    {
        float dVerticalOffset = 0;
        Block blockBelow = Block.blocksList[blockAccess.getBlockId( i, j - 1, k )];

//        if ( blockBelow != null )
//        {
//        	dVerticalOffset = blockBelow.GroundCoverRestingOnVisualOffset(
//        		blockAccess, i, j - 1, k );
//        }

        int iMetadata = blockAccess.getBlockMetadata( i, j, k );

        if ( isFullyGrown( iMetadata ) )
        {
            return AxisAlignedBB.getAABBPool().getAABB(
                    0.5D - BOUNDS_HALF_WIDTH, 0D + dVerticalOffset, 0.5D - BOUNDS_HALF_WIDTH,
                    0.5D + BOUNDS_HALF_WIDTH, 1D + dVerticalOffset, 0.5D + BOUNDS_HALF_WIDTH );
        }
        else
        {
            int iGrowthLevel = getGrowthLevel( iMetadata );
            double dBoundsHeight = ( 1 + iGrowthLevel ) / 8D;

            int iWeedsGrowthLevel = getWeedsGrowthLevel( blockAccess, i, j, k );

            if ( iWeedsGrowthLevel > 0 )
            {
                dBoundsHeight = Math.max( dBoundsHeight,
                        WeedsBlock.getWeedsBoundsHeight( iWeedsGrowthLevel ) );
            }

            return AxisAlignedBB.getAABBPool().getAABB(
                    0.5D - BOUNDS_HALF_WIDTH, 0F + dVerticalOffset, 0.5D - BOUNDS_HALF_WIDTH,
                    0.5D + BOUNDS_HALF_WIDTH, dBoundsHeight + dVerticalOffset,
                    0.5D + BOUNDS_HALF_WIDTH );
        }
    }

    protected Icon crop[] = new Icon[4];
    protected Icon front[] = new Icon[4];
    protected Icon back[] = new Icon[4];

    @Override
    public void registerIcons(IconRegister register)
    {
        for (int i = 0; i < crop.length; i++) {
            crop[i] = register.registerIcon(name + "_" + i);

            front[i] = register.registerIcon("sunflower_front_" + i);
            back[i] = register.registerIcon("sunflower_back_" + i);
        }

        blockIcon = crop[3];
    }

//    @Override
//    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k)
//    {
//        int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
//
//        renderStem(renderer, i, j, k, meta);
//
//        if (isTopBlock())
//        {
//            renderFlower(renderer, i, j, k, meta);
//        }
//
//        return true;
//    }

//    private double getRotation(int meta) {
//        int rotation = meta & 3;
//
//        if (rotation == 0) return -30;
//        else if (rotation == 1) return -60;
//        else if (rotation == 2) return 60;
//        else return 30;
//    }
//
//    private void renderFlower(RenderBlocks renderer, int i, int j, int k, int meta)
//    {
//        float flowerHeight = -4/16F + (1/16F * getGrowthLevel(meta) );
//        int growthLevel = getGrowthLevel(meta);
//
//        SCRenderUtils.renderHorizonzalPaneSunflower(renderer, this, i,j,k,
//                -1/16D, 2/16D + flowerHeight, 0,
//                0, 0, getRotation(meta),
//                front[getGrowthLevel(meta)], back[getGrowthLevel(meta)],
//                false, false, 0.0D);
//    }
//
//    protected void renderStem(RenderBlocks renderer, int i, int j, int k, int meta)
//    {
//        int growthLevel = getGrowthLevel(meta);
//
//        SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, i, j, k, crop[growthLevel], false);
//    }
}
