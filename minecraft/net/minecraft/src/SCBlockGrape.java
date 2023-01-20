package net.minecraft.src;

import java.util.Random;

public class SCBlockGrape extends FCBlockCrops {

	private String type;
	private Item cropItem;
	
	protected SCBlockGrape(int par1, Item cropItem, String type) {
		super(par1);
		this.type = type;
		this.cropItem = cropItem;
		
        setHardness( 0.2F );
        SetAxesEffectiveOn( true );        
        
        SetBuoyant();        
		SetFireProperties( FCEnumFlammability.LEAVES );
		
		this.setUnlocalizedName("SCBlockGrape");
		
		InitBlockBounds( 3/16D, 1/16D, 3/16D, 
						13/16D, 1D, 13/16D );
	}
	
	@Override
    public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 0.5F;
    }

	@Override
	public void updateTick( World world, int i, int j, int k, Random random )
	{
		int meta = world.getBlockMetadata(i, j, k);
		
		if (!IsFullyGrown(meta)) //ie not fully grown as meta 5 is the 6th growth stage
		{
			AttemptToGrow(world, i, j, k, random);
		}
	}
	
	@Override
    protected void AttemptToGrow( World world, int i, int j, int k, Random rand )
    {
    	if ( GetWeedsGrowthLevel( world, i, j, k ) == 0 )
	    {
	        Block blockAbove = Block.blocksList[world.getBlockId( i, j + 1, k )];
	        
	        if ( blockAbove != null && blockAbove instanceof SCBlockGrapeLeaves )
	        {
	    		float fGrowthChance = GetBaseGrowthChance( world, i, j, k );
	    		
	            if ( rand.nextFloat() <= fGrowthChance )
	            {
	            	IncrementGrowthLevel( world, i, j, k );
	            }
	        }
	    }
    }
	
	@Override
    protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) >= 7;
    }
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

	@Override
    public int idDropped( int meta, Random random, int iFortuneModifier )
    {
		if (meta == 7)
		{
			return GetCropItemID();
		}
		else return 0;
    }
	
	@Override
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return GetCropItemID();
	}
	
	@Override
	protected int GetCropItemID() {
		return cropItem.itemID;
	}

	@Override
	protected int GetSeedItemID() {
		return 0;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int  neighborBlockID)
	{
		int blockAbove = world.getBlockId(x, y + 1, z);
				
		if( !(Block.blocksList[blockAbove] instanceof SCBlockGrapeLeaves) )
		{
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
    	ItemStack playerEquippedItem = player.getCurrentEquippedItem();
    	
        if ( !world.isRemote && world.getBlockMetadata(i, j, k) == 7 ) // must have empty hand to rc
        {
        	if (playerEquippedItem == null || playerEquippedItem.itemID == GetCropItemID() )
        	{
    	        // click sound	        
                //world.playAuxSFX( 2001, i, j, k, blockID );
                FCUtilsItem.DropStackAsIfBlockHarvested( world, i, j, k, new ItemStack( GetCropItemID(), 1, 0) );
                world.setBlockToAir(i, j, k);
                
                return true;
        	}

        }
        
        return false;
    }
	
	private Icon[] grape = new Icon[8];
	
	@Override
	public void registerIcons(IconRegister register)
	{
		for (int i = 0; i < 8; i++)
		{
			grape[i] = register.registerIcon("SCBlockGrapes_" + type + "_" + i);
		}
		
	}
	
	@Override
    public Icon getIcon( int side, int meta )
    {
		return grape[meta&7];
    }
	
	@Override
	public boolean IsNormalCube(IBlockAccess blockAccess, int i, int j, int k) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
		renderer.renderCrossedSquares(this, i, j, k);
    
    	return true;
    }



}
