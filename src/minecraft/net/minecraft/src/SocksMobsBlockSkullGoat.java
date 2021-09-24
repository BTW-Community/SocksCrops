package net.minecraft.src;

import java.util.Random;

public class SocksMobsBlockSkullGoat extends Block {
	private final FCModelBlock blockModel = new SocksMobsModelSkullGoat();
    
	protected SocksMobsBlockSkullGoat(int ID) {
		super(ID, Material.dragonEgg);
        this.setHardness(0.05F);
        this.setResistance(1.0F);
        this.SetBuoyant();
        this.setStepSound(soundWoodFootstep);
		this.setCreativeTab(CreativeTabs.tabAllSearch);
		this.setUnlocalizedName("SocksMobsBlockSkullGoat");
		this.InitBlockBounds(0, 0, 0, 1, ((SocksMobsModelSkullGoat) this.blockModel).blockHeight, 1);
	}
	/**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        float var6 = 0.0625F;
        float var7 = (float)(1 + var5 * 2) / 16.0F;
        float var8 = 0.5F;
        this.setBlockBounds(var7, 0.0F, var6, 1.0F - var6, var8, 1.0F - var6);
    }
    
    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

   
	//CLIENT ONLY
    private Icon icon_top;
    private Icon icon_bottom;
    private Icon icon_front;
    private Icon icon_back;
    private Icon icon_side_r;
    private Icon icon_side_l;
    
    public Icon getIcon(int side, int metadata) {
    	if (side == 0) {
    		return icon_top;
    	}
    	else if (side == 1) {
    		return icon_bottom;
    	}
    	else if (side == 2) {
    		return icon_back;
    	}
    	else if (side == 3) {
    		return icon_front;
    	}
    	else if (side == 4) {
    		return icon_side_l;
    	}
    	else if (side == 5) {
    		return icon_side_r;
    	}
    	else return icon_bottom;

    }
    
    public void registerIcons(IconRegister register) {
    	icon_top = register.registerIcon("SocksMobsBlockSkullGoat_top");
    	icon_bottom = register.registerIcon("SocksMobsBlockSkullGoat_top");
    	icon_front = register.registerIcon("SocksMobsBlockSkullGoat_top");
    	icon_back = register.registerIcon("SocksMobsBlockSkullGoat_back");
    	icon_side_r = register.registerIcon("SocksMobsBlockSkullGoat_left");
    	icon_side_l = register.registerIcon("SocksMobsBlockSkullGoat_left");
    }
    

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return true;
    }

    public boolean RenderBlock(RenderBlocks var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1.blockAccess, var2, var3, var4);
        FCModelBlock var6 = this.blockModel.MakeTemporaryCopy();
        var6.RotateAroundJToFacing(var5);
        return var6.RenderAsBlock(var1, this, var2, var3, var4);
    }

    public void RenderBlockAsItem(RenderBlocks var1, int var2, float var3)
    {
        this.blockModel.RenderAsItemBlock(var1, this, var2);
    }
    
    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        this.eatCakeSlice(par1World, par2, par3, par4, par5EntityPlayer);
        return true;
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        this.eatCakeSlice(par1World, par2, par3, par4, par5EntityPlayer);
    }

    /**
     * Heals the player and removes a slice from the cake.
     */
    private void eatCakeSlice(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        if (par5EntityPlayer.canEat(false))
        {
            par5EntityPlayer.getFoodStats().addStats(2, 0.1F);
            int var6 = par1World.getBlockMetadata(par2, par3, par4) + 1;

            if (var6 >= 6)
            {
                par1World.setBlockToAir(par2, par3, par4);
            }
            else
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
            }
        }
    }
    
}