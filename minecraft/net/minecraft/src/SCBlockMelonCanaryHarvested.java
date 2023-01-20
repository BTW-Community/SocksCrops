package net.minecraft.src;

import java.util.List;

public class SCBlockMelonCanaryHarvested extends SCBlockGourdHarvested {

	protected SCBlockMelonCanaryHarvested(int iBlockID) {
		super(iBlockID);
		
		setHardness(1.0F);
        
        setStepSound(soundWoodFootstep);
        
        setUnlocalizedName("SCBlockMelonCanaryHarvested");
		
	}
	
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
		
	@Override
	protected Item ItemToDropOnExplode()
	{
		return FCBetterThanWolves.fcItemMelonMashed;
	}
	
	@Override
	protected int ItemCountToDropOnExplode(World world, int i, int j, int k, int meta)
	{
		if (this.GetGrowthLevel(meta) == 3)
		{
			return 1;
		}
		else return 0;
	}
	
	
	@Override
	protected int AuxFXIDOnExplode(World world, int i, int j, int k, int meta)
	{
		return SCCustomAuxFX.melonCanaryExplodeAuxFXID;
	}
		
	protected DamageSource GetFallDamageSource()
	{
		return FCDamageSourceCustom.m_DamageSourceMelon;
	}
	
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 4));
		par3List.add(new ItemStack(par1, 1, 8));
		par3List.add(new ItemStack(par1, 1, 12));
    }
	
	
	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	@Override
	public int damageDropped(int meta)
	{
		int growthLevel = this.GetGrowthLevel(meta);
		
		if (growthLevel == 0)
		{
			return 0;
		}
		else if (growthLevel == 1)
		{
			return 4;
		}
		else if (growthLevel == 2)
		{
			return 8;
		}
		return 12;
	}
	
    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack itemStack)
    {
        int dir = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        int shiftGrowthLevel = itemStack.getItemDamage();
        
        par1World.setBlockMetadataWithNotify(par2, par3, par4, dir + shiftGrowthLevel, 2);
    }

	public static int GetGrowthLevel(int meta)
    {
		if (meta < 4)
		{
			return 0;
		}
		else if (meta >= 4 && meta < 8) 
		{
			return 1;
		}
		else if (meta >= 8 && meta < 12) 
		{
			return 2;
		} 
		else return 3;
        
    }
	
	public static int GetDirection( int meta)
	{
		return meta & 3;
	}

		
	//----------- Render/Icon Functionality -----------//
	
	private Icon[] yellowIconSide;
	private Icon[] yellowIconSideMirrored;
	private Icon[] yellowIconTop;
	private Icon[] yellowIconFront;
	private Icon[] yellowIconEnd;
	
	@Override
  	public void registerIcons( IconRegister register )
  	{

		//Yellow
		yellowIconSide = new Icon[4];
		
  		for ( int iTempIndex = 0; iTempIndex < yellowIconSide.length; iTempIndex++ )
		{
  			yellowIconSide[iTempIndex] = register.registerIcon( "SCBlockMelonYellowSide_" + iTempIndex );
		}
  		
  		yellowIconSideMirrored = new Icon[4];
		
  		for ( int iTempIndex = 0; iTempIndex < yellowIconSideMirrored.length; iTempIndex++ )
		{
  			yellowIconSideMirrored[iTempIndex] = register.registerIcon( "SCBlockMelonYellowSide_mirrored_" + iTempIndex );
		}
	
  		yellowIconTop = new Icon[4];
	
		for ( int iTempIndex = 0; iTempIndex < yellowIconTop.length; iTempIndex++ )
		{
			yellowIconTop[iTempIndex] = register.registerIcon( "SCBlockMelonYellowTop_" + iTempIndex );
		}
		
		yellowIconFront = new Icon[4];
 		
		for ( int iTempIndex = 0; iTempIndex < yellowIconFront.length; iTempIndex++ )
		{
			yellowIconFront[iTempIndex] = register.registerIcon( "SCBlockMelonYellowFront_" + iTempIndex );
		}
		
		yellowIconEnd = new Icon[4];
 		
		for ( int iTempIndex = 0; iTempIndex < yellowIconEnd.length; iTempIndex++ )
		{
			yellowIconEnd[iTempIndex] = register.registerIcon( "SCBlockMelonYellowBack_" + iTempIndex );
		}
		
	}
	
	@Override
    public Icon getIcon( int side, int iMetadata )
    {
    	int growthLevel = GetGrowthLevel(iMetadata);
    	int dir = getDirection(iMetadata);
    	if (dir == 0)
    	{
        	if (side == 0 || side == 1)
        	{
        		return yellowIconTop[growthLevel];
        	}
        	else if (side == 3)
        	{
        		return yellowIconEnd[growthLevel];
        	}
        	else if (side == 2)
        	{
        		return yellowIconFront[growthLevel];
        	}
        	else
        	{
        		if (side == 4)
        		{
        			return yellowIconSideMirrored[growthLevel];
        		}
        		return yellowIconSide[growthLevel];
        	}
    	}
    	else if (dir == 2)
    	{
        	if (side == 0 || side == 1)
        	{
        		return yellowIconTop[growthLevel];
        	}
        	else if (side == 2)
        	{
        		return yellowIconEnd[growthLevel];
        	}
        	else if (side == 3)
        	{
        		return yellowIconFront[growthLevel];
        	}
        	else
        	{
        		if (side == 5)
        		{
        			return yellowIconSideMirrored[growthLevel];
        		}
        		return yellowIconSide[growthLevel];
        	}
    	}
    	else if (dir == 3)
    	{
        	if (side == 0 || side == 1)
        	{
        		return yellowIconTop[growthLevel];
        	}
        	else if (side == 5)
        	{
        		return yellowIconEnd[growthLevel];
        	}
        	else if (side == 4)
        	{
        		return yellowIconFront[growthLevel];
        	}
        	else
        	{
        		if (side == 3)
        		{
        			return yellowIconSideMirrored[growthLevel];
        		}
        		return yellowIconSide[growthLevel];
        	}
    	}
    	else
    	{

        	if (side == 0 || side == 1)
        	{
        		return yellowIconTop[growthLevel];
        	}
        	else if (side == 4)
        	{
        		return yellowIconEnd[growthLevel];
        	}
        	else if (side == 5)
        	{
        		return yellowIconFront[growthLevel];
        	}
        	else
        	{
        		if (side == 2)
        		{
        			return yellowIconSideMirrored[growthLevel];
        		}
        		return yellowIconSide[growthLevel];
        	}
    	}
    	
//    	
//    	if (iSide == 2 || iSide == 3 )
//    	{
//    		if (dir == 0 || dir == 2)
//    		{
//    			return yellowIconTop[growthLevel];
//    		}
//    		else return yellowIconSide[growthLevel];
//    	}
//    	else if (iSide == 4 || iSide == 5 )
//    	{
//    		if (dir == 1 || dir == 3)
//    		{
//    			return yellowIconTop[growthLevel];
//    		}
//    		else return yellowIconSide[growthLevel];
//    	}
//    	else return yellowIconSide[growthLevel];
    }
	
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( int meta )
	{
		int growthLevel = this.GetGrowthLevel(meta);
		int dir = this.getDirection(meta);
		//init BB
		AxisAlignedBB pumpkinBounds;
		
		//young
		if (growthLevel == 0)
		{
			if (dir == 1 || dir == 3)
			{
				return GetGourdBounds(6, 4, 4);
			}
			else return GetGourdBounds(4, 4, 6);
		}
		//teen
		else if (growthLevel == 1)
		{
			if (dir == 1 || dir == 3)
			{
				return GetGourdBounds(10, 6, 6);
			}
			else return GetGourdBounds(6, 6, 10);
		}
		//adult
		else if (growthLevel == 2)
		{
			if (dir == 1 || dir == 3)
			{
				return GetGourdBounds(12, 8, 8);
			}
			else return GetGourdBounds(8, 8, 12);
		}
		//mature
		else if (dir == 1 || dir == 3)
		{
			return GetGourdBounds(16, 10, 10);
		}
		else return GetGourdBounds(10, 10, 16);
	}
	
	private void setTextureRotations(RenderBlocks renderer, int meta)
	{
		int dir = this.getDirection(meta);
		
		if (dir == 0)
		{
			renderer.SetUvRotateTop(2);
			renderer.SetUvRotateBottom(2);
		}
		else if (dir == 2)
		{
			renderer.SetUvRotateTop(1);
			renderer.SetUvRotateBottom(1);
		}
		else if (dir == 3)
		{
			renderer.SetUvRotateTop(3);
			renderer.SetUvRotateBottom(3);
		}
			
		
		
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
		
		this.setTextureRotations(renderer, meta);
		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(blockAccess, i, j, k) );
		renderer.renderStandardBlock( this, i, j, k );
		renderer.ClearUvRotation();

		return true;
	}

	@Override
	public void RenderFallingBlock(RenderBlocks renderer, int i, int j, int k, int meta)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		
		this.setTextureRotations(renderer, meta);		
		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(meta) );		
		renderer.RenderStandardFallingBlock( this, i, j, k, meta);
		renderer.ClearUvRotation();
	}
	
	@Override
	public void RenderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		
		this.setTextureRotations(renderer, iItemDamage);
		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(iItemDamage) );
		FCClientUtilsRender.RenderInvBlockWithMetadata( renderer, this, -0.5F, -0.5F, -0.5F, iItemDamage);
		renderer.ClearUvRotation();
	}
	
	
	

}
