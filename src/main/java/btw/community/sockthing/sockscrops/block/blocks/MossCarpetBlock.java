package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.blocks.GroundCoverBlock;
import btw.block.util.Flammability;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import net.minecraft.src.*;

import java.util.Random;

public class MossCarpetBlock extends GroundCoverBlock {

    public MossCarpetBlock(int blockID)
    {
        super(blockID, Material.plants);
        setFireProperties( Flammability.LEAVES );
        setStepSound(soundGrassFootstep);
        setUnlocalizedName("SCBlockMossCarpet");

        setTickRandomly(true);
    }

    @Override
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        //condition for leaves to drip:  if (world.isRainingAtPos(i, j + 1, k) && !world.doesBlockHaveSolidTopSurface(i, j - 1, k) && rand.nextInt(15) == 1) {
        //condition for water source to drip: if (par5Random.nextInt(10) == 0 && par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !par1World.getBlockMaterial(par2, par3 - 2, par4).blocksMovement()
        //and the BlockFluid is Material.water

        boolean hasLeaves = Block.blocksList[world.getBlockId(x,y + 2,z)] instanceof BlockLeavesBase;
        boolean canLeavesDrip = world.isRainingAtPos(x, y + 4, z) && !world.doesBlockHaveSolidTopSurface(x, y + 2, z);
        boolean hasWaterSource = Block.blocksList[world.getBlockId(x,y + 3,z)] instanceof BlockFluid && world.getBlockMaterial(x,y + 3,z) == Material.water;
        boolean canWaterDrip = world.doesBlockHaveSolidTopSurface(x, y + 2, z) && !world.getBlockMaterial(x, y + 1, z).blocksMovement();

//        System.out.println("hasLeaves: " + hasLeaves );
//        System.out.println("canLeavesDrip: " + canLeavesDrip );
//        System.out.println("hasWaterSource: " + hasWaterSource );
//        System.out.println("canWaterDrip: " + canWaterDrip );
        
        if (world.isAirBlock (x,y + 1,z) && ((hasLeaves && canLeavesDrip) || (hasWaterSource && canWaterDrip))){
//            System.out.println("Attempting to spread");
            attemptToSpreadMoss(world, x, y, z, rand);
        }
    }

    private float getSpreadChance() {
        return 0.5F; //0.04F;
    }

    protected boolean canSpreadToOrFromLocation(World world, int x, int y, int z)
    {
        return world.getFullBlockLightValue( x, y, z ) > 0 && !world.canBlockSeeTheSky(x,y,z);
    }

    private boolean hasAdjacentMoss(World world, int x, int y, int z) {
        for (int i = -1; i < 1; i++) {
            for (int k = -1; k < 1; k++) {
                if ( world.getBlockId(x + i, y, z + k) == this.blockID
                        || world.getBlockId(x + i, y - 1, z + k) == this.blockID
                        || world.getBlockId(x + i, y + 1, z + k) == this.blockID)
                {
                    return true;
                }
            }
        }

        return false;
    }

    protected void attemptToSpreadMoss(World world, int i, int j, int k, Random rand)
    {
        // basically a copy/paste of the BlockMushroom updateTick cleaned up and with additional requirements that brown mushrooms can only grow in complete darkness

        if ( rand.nextFloat() <= getSpreadChance() && canSpreadToOrFromLocation(world, i, j, k) )
        {
//            System.out.println("spreading");

            int iHorizontalSpreadRange = 4;
            int iNeighboringMushroomsCountdown = 9;

            for ( int iTempI = i - iHorizontalSpreadRange; iTempI <= i + iHorizontalSpreadRange; ++iTempI )
            {
                for ( int iTempK = k - iHorizontalSpreadRange; iTempK <= k + iHorizontalSpreadRange; ++iTempK )
                {
                    for ( int iTempJ = j - 1; iTempJ <= j + 1; ++iTempJ )
                    {
                        if ( world.getBlockId( iTempI, iTempJ, iTempK ) == blockID )
                        {
                            --iNeighboringMushroomsCountdown;

                            if (iNeighboringMushroomsCountdown <= 0)
                            {
                                return;
                            }
                        }
                    }
                }
            }

            int iSpreadI = i + rand.nextInt(5) - 2;
            int iSpreadK = j + rand.nextInt(2) - rand.nextInt(2);
            int iSpreadJ = k + rand.nextInt(5) - 2;

//            for ( int iTempCount = 0; iTempCount < 4; ++iTempCount )
//            {
//                if (world.isAirBlock( iSpreadI, iSpreadK, iSpreadJ ) && canBlockStay(world, iSpreadI, iSpreadK, iSpreadJ ) &&
//                        canSpreadToOrFromLocation(world, iSpreadI, iSpreadK, iSpreadJ) )
//                {
//                    i = iSpreadI;
//                    j = iSpreadK;
//                    k = iSpreadJ;
//                }
//
//                iSpreadI = i + rand.nextInt(3) - 1;
//                iSpreadK = j + rand.nextInt(2) - rand.nextInt(2);
//                iSpreadJ = k + rand.nextInt(3) - 1;
//            }

            if ( world.isAirBlock( iSpreadI, iSpreadK, iSpreadJ ) && canBlockStay( world, iSpreadI, iSpreadK, iSpreadJ ) &&
                    canSpreadToOrFromLocation(world, iSpreadI, iSpreadK, iSpreadJ) )
            {
                world.setBlock( iSpreadI, iSpreadK, iSpreadJ, blockID );
//                System.out.println("spread moss");
            }
        }
    }


    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        return canPlaceBlockAt(par1World, par2, par3, par4);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k )
    {
        int iBlockBelowID = world.getBlockId( i, j - 1, k );
        Block blockBelow = Block.blocksList[iBlockBelowID];

        if ( blockBelow != null )
        {
            return blockBelow.canGroundCoverRestOnBlock( world, i, j - 1, k ) || blockBelow.hasLargeCenterHardPointToFacing(world, i, j -1, k, 1);
        }

        return false;
    }

//	@Override
//	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
//	{
////        int var5 = 0;
////        int var6 = 0;
////        int var7 = 0;
////
////        for (int var8 = -1; var8 <= 1; ++var8)
////        {
////            for (int var9 = -1; var9 <= 1; ++var9)
////            {
////                int var10 = blockAccess.getBiomeGenForCoords(x + var9, z + var8).getBiomeGrassColor();
////                var5 += (var10 & 16711680) >> 16;
////                var6 += (var10 & 65280) >> 8;
////                var7 += var10 & 255;
////            }
////        }
////
////        return (var5 / 9 & 255) << 16 | (var6 / 9 & 255) << 8 | var7 / 9 & 255;
//
//    	double var1 = 1.0D;
//    	double var3 = 0.5D;
//    	//return 16777215;
//    	return ColorizerGrass.getGrassColor(var1, var3);
//	}
//
//	@Override
//	public int getRenderColor(int par1)
//	{
//    	double var1 = 1.0D;
//    	double var3 = 0.5D;
//    	return ColorizerGrass.getGrassColor(var1, var3);
//	}

    private Icon mossSide;

    public static boolean secondPass;
    @Override
    public void registerIcons( IconRegister register )
    {
        blockIcon = register.registerIcon( "moss" );
        mossSide = register.registerIcon("moss_carpet_side");
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int neighborI, int neighborJ, int neighborK, int side)
    {
        if (secondPass)
        {
            if (side == 0) return false;
            if (side > 1 && blockAccess.getBlockId(neighborI, neighborJ, neighborK) == SCBlocks.hollowLog.blockID) return false;
        }
        return true;
    }

    @Override
    public void renderBlockSecondPass(RenderBlocks renderBlocks, int i, int j, int k, boolean firstPassResult)
    {
        secondPass = true;

        float visualOffset = 0F;

        int iBlockBelowID = renderBlocks.blockAccess.getBlockId( i, j - 1, k );
        Block blockBelow = Block.blocksList[iBlockBelowID];

        if (blockBelow != null) {
            visualOffset = blockBelow.groundCoverRestingOnVisualOffset(renderBlocks.blockAccess, i, j - 1, k);

            if (visualOffset < 0.0F)
            {
                j -= 1;

                visualOffset += 1F;
            }

        }

        renderBlocks.setOverrideBlockTexture(mossSide);
        renderBlocks.setRenderBounds(
                0 - 1/1024D,  0  + visualOffset, 0 - 1/1024D,
                1 + 1/1024D, 1 + visualOffset, 1 + 1/1024D
        );
        renderBlocks.renderStandardBlock(this, i, j - 1, k);

        renderBlocks.clearOverrideBlockTexture();

        secondPass = false;
    }

}