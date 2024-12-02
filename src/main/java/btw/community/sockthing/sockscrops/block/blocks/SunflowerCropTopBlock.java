package btw.community.sockthing.sockscrops.block.blocks;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.item.SCItems;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.World;

import java.util.Random;

public class SunflowerCropTopBlock extends SunflowerBaseBlock {
    public SunflowerCropTopBlock(int iBlockID, String name) {
        super(iBlockID, name);
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random rand )
    {
        int meta = world.getBlockMetadata(i, j, k);
        int rotation = damageToData(meta)[0];
        int growthLevel = damageToData(meta)[1];

        //System.out.println("Rot: " + rotation + " - GrowthLevel: " + growthLevel);

        if ( updateIfBlockStays( world, i, j, k ) )
        {
            // no plants can grow in the end

            if ( world.provider.dimensionId != 1 && !isFullyGrown( world, i, j, k ) )
            {
                setFlowerRotation(world, i, j, k);

                AttemptToGrow( world, i, j, k, rand );
            }
        }
    }

    protected void setFlowerRotation(World world, int i, int j, int k)
    {
        int meta = world.getBlockMetadata(i, j, k);
        int rotation = updateRotationForTime(world);
        int growthLevel = damageToData(meta)[1];

        int newMeta = dataToDamage(rotation, growthLevel);

        world.setBlockMetadataWithNotify(i, j, k, newMeta);
    }

    protected void AttemptToGrow(World world, int x, int y, int z, Random rand) {
        if (getWeedsGrowthLevel(world, x, y, z) == 0 && canGrowAtCurrentLightLevel(world, x, y, z)) {
            Block blockBelow = Block.blocksList[world.getBlockId(x, y - 1, z)];

            if (blockBelow != null && blockBelow.isBlockHydratedForPlantGrowthOn(world, x, y - 1, z)) {
                float fGrowthChance = getBaseGrowthChance(world, x, y, z) *
                        blockBelow.getPlantGrowthOnMultiplier(world, x, y - 2, z, this);

                if (rand.nextFloat() <= fGrowthChance) {
                    IncrementGrowthLevel(world, x, y, z);
                }
            }
        }
    }

    protected void IncrementGrowthLevel( World world, int i, int j, int k )
    {
        int meta = world.getBlockMetadata(i, j, k);
        int rotation = updateRotationForTime(world);
        int growthLevel = damageToData(meta)[1];

        int newMeta = dataToDamage(rotation, growthLevel + 1);

        world.setBlockMetadataWithNotify( i, j, k, newMeta);
    }

    @Override
    public int getWeedsGrowthLevel(IBlockAccess blockAccess, int i, int j, int k )
    {
        int iBlockBelowID = blockAccess.getBlockId( i, j - 2, k );
        Block blockBelow = Block.blocksList[iBlockBelowID];

        if ( blockBelow != null && iBlockBelowID != blockID )
        {
            return blockBelow.getWeedsGrowthLevel( blockAccess, i, j - 2, k );
        }

        return 0;
    }



    @Override
    protected int getMaxGrowthStage() {
        return 3;
    }

    @Override
    protected int getCropItemID() {
        return SCItems.sunflower.itemID;
    }

    @Override
    protected int getSeedItemID() {
        return 0;
    }

    @Override
    protected boolean isTopBlock() {
        return true;
    }

    protected int getGrowthLevel( int meta )
    {
        return damageToData(meta)[1];

//    	return meta & 7;
    }

    protected void setGrowthLevel( World world, int i, int j, int k, int iLevel )
    {
        int iMetadata = world.getBlockMetadata( i, j, k );

        world.setBlockMetadataWithNotify( i, j, k, iMetadata + 4);
    }

    protected boolean isFullyGrown( World world, int i, int j, int k )
    {
        return isFullyGrown( world.getBlockMetadata( i, j, k ) );
    }

    protected boolean isFullyGrown( int iMetadata )
    {
        return getGrowthLevel( iMetadata ) >= getMaxGrowthStage();
    }

    public static int dataToDamage(int rotation, int growthLevel)
    {
        return rotation | growthLevel << 2;
    }

    protected static int[] damageToData(int damage)
    {
        int rotation = damage & 3;
        int growthLevel = (damage >> 2) & 3;

        return new int[] { rotation, growthLevel };
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k)
    {
        int meta = renderer.blockAccess.getBlockMetadata(i, j, k);

        renderStem(renderer, i, j, k, meta);

        renderFlower(renderer, i, j, k, meta);

        return true;
    }

    protected void renderStem(RenderBlocks renderer, int i, int j, int k, int meta)
    {
        int growthLevel = damageToData(meta)[1];

        SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, i, j, k, crop[growthLevel], false);
    }

    private double getRotation(int meta) {
        int rotation = damageToData(meta)[0];

        if (rotation == 0) return -60;
        else if (rotation == 1) return -30;
        else if (rotation == 2) return 30;
        else return 60;
    }

    private void renderFlower(RenderBlocks renderer, int i, int j, int k, int meta)
    {
        int rotation = damageToData(meta)[0];
        int growthLevel = damageToData(meta)[1];
        float flowerHeight = - (3 - growthLevel) * 2/16F;

        double xShift = -1/16D;

        if (getRotation(meta) > 0) xShift = 1/16D;

        SCRenderUtils.renderHorizonzalPaneSunflower(renderer, this, i,j,k,
                xShift, 2/16D + flowerHeight, 0,
                0, 0, getRotation(meta),
                front[growthLevel], back[growthLevel],
                false, false, 0.0D);
    }
}
