package net.minecraft.src;

import java.util.List;
import java.util.Random;

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
            	
    			if (world.rand.nextFloat() <= 1/16F)
    			{
    				this.dropBlockAsItem_do(world, i, j, k, new ItemStack(SCDefs.grassSeeds));
    			}
    		} 
            else if( heldStack.getItem() instanceof SCItemKnife && getType(meta) == FERN)
    		{
            	player.getHeldItem().damageItem(1, player);
            	
    			if (world.rand.nextFloat() <= 1/16F)
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
}