package net.minecraft.src;

import java.util.List;

public class SocksCropsBlockLilyPad extends SocksCropsBlockWaterFlower
{
    protected SocksCropsBlockLilyPad(int par1)
    {
        super(par1);
        float var2 = 0.5F;
        float var3 = 0.015625F;
        this.setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var3, 0.5F + var2);
        this.SetBuoyant();
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.InitBlockBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.015625D, 1.0D);
        this.setHardness(0.0F);
        this.setStepSound(soundGrassFootstep);
        this.setUnlocalizedName("waterlily");
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 23;
    }

    /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
     */
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        if (par7Entity == null || !(par7Entity instanceof EntityBoat))
        {
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        return this.GetBlockBoundsFromPoolBasedOnState(var1, var2, var3, var4).offset((double)var2, (double)var3, (double)var4);
    }



    /**
     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
     * blockID passed in. Args: blockID
     */

    protected boolean CanGrowOnBlock(World var1, int var2, int var3, int var4)
    {
        return var1.getBlockId(var2, var3, var4) == Block.waterStill.blockID;
    }
    
    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        return par3 >= 0 && par3 < 256 ? par1World.getBlockMaterial(par2, par3 - 1, par4) == Material.water && par1World.getBlockMetadata(par2, par3 - 1, par4) == 0 : false;
    }
    
    public boolean RenderBlock(RenderBlocks var1, int var2, int var3, int var4)
    {
        var1.setRenderBounds(this.GetBlockBoundsFromPoolBasedOnState(var1.blockAccess, var2, var3, var4));
        var1.renderBlockLilyPad(Block.waterlily, var2, var3, var4);
        var1.renderCrossedSquares(Block.vine, var2, var3 - 1, var4);
        //var1.renderLilyFlower(Block.plantYellow, var2, var3, var4);
        return true;
    }
}