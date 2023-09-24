package net.minecraft.src;

import java.util.List;
import java.util.Random;

import com.prupe.mcpatcher.cc.ColorizeBlock;

public class SCBlockDoubleTallGrass extends BlockFlower {

	private static final double m_dHalfWidth = 0.3F;
	
	public static final int GRASS = 0;
	public static final int FERN = 1;
	public static final int SUNFLOWER = 2;
	
	protected SCBlockDoubleTallGrass(int par1) {
		super(par1);
		
    	setHardness( 0F );
    	
    	SetBuoyant();
    	
		SetFireProperties( FCEnumFlammability.GRASS );
		
        InitBlockBounds( 0.5D - m_dHalfWidth, 0D, 0.5D - m_dHalfWidth, 
        	0.5D + m_dHalfWidth, 0.8D, 0.7D + m_dHalfWidth);
		
		setCreativeTab(CreativeTabs.tabDecorations);
		
		setStepSound(soundGrassFootstep);

		setUnlocalizedName("SCBlockDoubleTallPlant");
		
	}

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World world, EntityPlayer player, int i, int j, int k, int meta)
    {
    	ItemStack heldStack = player.getCurrentEquippedItem();
    	
    	if (!world.isRemote && (meta&7) == SUNFLOWER)
    	{
    		this.dropBlockAsItem_do(world, i, j, k, new ItemStack(SCDefs.sunflower));
    		return;
    	}
    	
    	if (!world.isRemote && heldStack != null)
    	{
    		if (heldStack.getItem() instanceof FCItemShears)
    		{
                player.getHeldItem().damageItem(1, player);
                
                if (isTopBlock(meta))
                {
                	 this.dropBlockAsItem_do(world, i, j, k, new ItemStack(this.blockID, 1, meta - 8));
                }
                else this.dropBlockAsItem_do(world, i, j, k, new ItemStack(this.blockID, 1, meta));
    		}
            else if( heldStack.getItem() instanceof SCItemKnife && getType(meta) == GRASS)
    		{
            	player.getHeldItem().damageItem(1, player);
            	
    			if (world.rand.nextFloat() <= 1/8F)
    			{
    				this.dropBlockAsItem_do(world, i, j, k, new ItemStack(SCDefs.grassSeeds));
    			}
    		} 
            else if( heldStack.getItem() instanceof SCItemKnife && getType(meta) == FERN)
    		{
            	player.getHeldItem().damageItem(1, player);
            	
    			if (world.rand.nextFloat() <= 1/8F)
    			{
    				this.dropBlockAsItem_do(world, i, j, k, new ItemStack(SCDefs.hay));
    			}
    		} 
    	}
		else
		{
			super.harvestBlock( world, player, i, j, k, meta );
		}
    }
    
    private int getType (int meta)
    {
    	return meta & 7;
    }
    
	@Override
	public int idDropped(int iMetadata, Random rand, int iFortuneModifier) {

		if ((iMetadata&7) == SUNFLOWER)
		{
			return SCDefs.sunflower.itemID;
		}			
			
		return 0;
	}
	
	@Override
	public int damageDropped(int par1) {
		return 0;
	}
	
    @Override
    public boolean CanSpitWebReplaceBlock( World world, int i, int j, int k )
    {
    	return true;
    }
    
    @Override
    public boolean IsReplaceableVegetation( World world, int i, int j, int k )
    {
    	return true;
    }
	
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		par3List.add(new ItemStack(par1, 1, GRASS));
		par3List.add(new ItemStack(par1, 1, FERN));
    }
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving player,
			ItemStack itemStack) {
				
		if (world.getBlockId(i, j + 1, k) == 0)
		{
			world.setBlockAndMetadataWithNotify(i, j + 1, k, this.blockID, setTopBlock(world.getBlockMetadata(i, j, k)));
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int neighbourID) {
		int meta = world.getBlockMetadata(i, j, k);
		
		if ( isTopBlock(meta))
		{
			if (world.getBlockId(i, j -1, k) != this.blockID )
			{
				world.setBlockToAir(i, j, k);
				//FCUtilsItem.DropSingleItemAsIfBlockHarvested(world, i, j, k, this.blockID, meta - 8);
			}
		}
		else
		{
			if (world.getBlockId(i, j +1, k) != this.blockID )
			{
				world.setBlockToAir(i, j, k);
				//FCUtilsItem.DropSingleItemAsIfBlockHarvested(world, i, j, k, this.blockID, meta);
			}
		}
		
		super.onNeighborBlockChange(world, i, j, k, neighbourID);
	}
	
	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {

		int meta = world.getBlockMetadata(i, j, k);
		if (isTopBlock(meta))
		{
			return world.getBlockId(i, j - 1, k) == this.blockID;
		}
		else return super.canBlockStay(world, i, j, k);
	}
	
	@Override
	protected boolean CanGrowOnBlock(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);
		
		if (isTopBlock(meta))
		{
			return world.getBlockId(i, j - 1, k) == this.blockID;
		}
		else return super.CanGrowOnBlock(world, i, j, k);
	}
	
	public boolean isTopBlock(int meta) {
		if (meta > 7) {
			return true;
		}
		else return false;
	}
	
	protected int setTopBlock(int meta)
	{
		return meta + 8;
	}
	
    public int getBlockColor()
    {
    	if (ColorizeBlock.colorizeBlock(this))
        {
            return ColorizeBlock.blockColor;
        }
        else
        {
        	double var1 = 0.5D;
        	double var3 = 1.0D;
        	return ColorizerGrass.getGrassColor(var1, var3);
        }
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    public int getRenderColor(int par1)
    {    
    	if ((par1&7) == SUNFLOWER) return 0xFFFFFF;
    	
    	if ( ColorizeBlock.colorizeBlock(this, par1) )
    	{
    		return ColorizeBlock.blockColor;
    	}
    	else
    	{
    		return ColorizerFoliage.getFoliageColorBasic();
    	}
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
    	if (ColorizeBlock.colorizeBlock(this, par1IBlockAccess, par2, par3, par4))
        {
            return ColorizeBlock.blockColor;
        }
        else
        {
        	int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        	
        	if ((var5&7) == SUNFLOWER)
        	{
        		return 0xFFFFFF;
        	}
        	
        	return par1IBlockAccess.getBiomeGenForCoords(par2, par4).getBiomeGrassColor();
        }
    }
	
	private Icon topIcon[] = new Icon[8];
	private Icon bottomIcon[] = new Icon[8];
	private Icon sunflowerFace[] = new Icon[2];
	
	@Override
	public void registerIcons(IconRegister register)
	{
		
		topIcon[0] = register.registerIcon("SCBlockTallGrass_top");
		topIcon[1] = register.registerIcon("SCBlockTallFern_top");
		topIcon[2] = register.registerIcon("SCBlockFlowerSunflower_top_3");
		
		bottomIcon[0] = register.registerIcon("SCBlockTallGrass_bottom");
		bottomIcon[1] = register.registerIcon("SCBlockTallFern_bottom");
		bottomIcon[2] = register.registerIcon("SCBlockFlowerSunflower_bottom_4");
		
		sunflowerFace[0] = register.registerIcon("SCBlockFlowerSunflower_front_3");
		sunflowerFace[1] = register.registerIcon("SCBlockFlowerSunflower_back_3");
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		
		if ((meta&7) == SUNFLOWER) return bottomIcon[2];
		
		if (side == 0)
		{
			return bottomIcon[meta&7];
		}
		
		if (isTopBlock(meta))
		{
			return topIcon[meta - 8];
		}
		else return topIcon[meta];
	}
	
	@Override
	public Icon getBlockTexture(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		int meta = par1iBlockAccess.getBlockMetadata(par2, par3, par4);
		int metaBelow = par1iBlockAccess.getBlockMetadata(par2, par3 - 1, par4);
		int blockBelow = par1iBlockAccess.getBlockId(par2, par3 - 1, par4);
		
		if ((meta&7) == SUNFLOWER) return bottomIcon[2];
		
		if (isTopBlock(meta))
		{
			return topIcon[meta - 8];
		}
		else return bottomIcon[meta];
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{		
		int meta = renderer.blockAccess.getBlockMetadata(i, j, k); 
		
		if ((meta&7) == SUNFLOWER)
		{
			if (!isTopBlock(meta))
			{
				SCUtilsRender.RenderCrossedSquaresWithTextureAndOffset(renderer, this, i, j, k, bottomIcon[SUNFLOWER], false);
			}
			else 
			{
				SCUtilsRender.RenderCrossedSquaresWithTextureAndOffset(renderer, this, i, j, k, topIcon[SUNFLOWER], false);
				
				SCUtilsRender.renderSunflowerPlaneWithTexturesAndRotation(renderer, this, i, j, k, sunflowerFace[0], 0, 6);
				SCUtilsRender.renderBackSunflowerPlaneWithTexturesAndRotation(renderer, this, i, j, k, sunflowerFace[1], 0, 6);
			}
		}		
		else SCUtilsRender.RenderCrossedSquaresWithTextureAndOffset(renderer, this, i, j, k, getBlockTexture(renderer.blockAccess, i, j, k, meta), true);
		return true;
	}

}