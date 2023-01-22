package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockCakeBase extends FCBlockCake {

	protected SCBlockCakeBase(int blockID) {
		super(blockID);
		setStepSound(soundClothFootstep);
	}
	
	protected abstract int getSliceItem();
	protected abstract int getCake();
	
	@Override
	public int idDropped(int meta, Random rand, int fortuneModifier)
	{
		if (GetEatState(meta) == 0)
		{
			return getCake();
		}
		
		return 0;
	}
	
	
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }
	
    @Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iNeigborBlockID )
    {
        if ( !canBlockStay( world, i, j, k ) )
        {
            dropBlockAsItem( world, i, j, k, world.getBlockMetadata(i, j, k), GetEatState(world.getBlockMetadata(i, j, k)) );
            world.setBlockWithNotify( i, j, k, 0 );
        }
        else
        {
        	boolean bOn = IsRedstoneOn( world, i, j, k );
        	boolean bReceivingRedstone = world.isBlockGettingPowered( i, j, k ); 
        	
        	if ( bOn != bReceivingRedstone )
        	{
        		SetRedstoneOn( world, i, j, k, bReceivingRedstone );
        		
        		if ( bReceivingRedstone )
        		{
	                world.playAuxSFX( FCBetterThanWolves.m_iGhastScreamSoundAuxFXID, i, j, k, 0 );            
        		}
        	}
        }
    }
	
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        return FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, x, y - 1, z, 1, true );
    }
    
    @Override
    public void onBlockAdded( World world, int i, int j, int k )
    {
        super.onBlockAdded( world, i, j, k );
        
    	boolean bReceivingRedstone = world.isBlockGettingPowered( i, j, k ); 
    	
		if ( bReceivingRedstone )
		{
			SetRedstoneOn( world, i, j, k, true );
			
            world.playAuxSFX( FCBetterThanWolves.m_iGhastScreamSoundAuxFXID, i, j, k, 0 );            
		}
    }
    
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {

		if ( !canBlockStay(world,x,y,z))
		{
			return false;
		}
		else return super.canPlaceBlockAt(world, x, y, z);
	}
	
    @Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
    	ItemStack heldStack = player.getHeldItem();
        
        if (heldStack == null)
        {
        	 super.onBlockActivated(world, i, j, k, player, iFacing, fXClick, fYClick, fZClick);
        	 return true;
        }
        else if (heldStack.getItem() instanceof SCItemKnife)
        {
        	cutCake(world, i, j, k, player);
        	world.playSoundAtEntity( player, this.stepSound.getStepSound(), this.stepSound.getVolume() * 0.5F, this.stepSound.getPitch() * 0.3F );
        	
            heldStack.func_96631_a(1, world.rand); //attemptDamageItem
            
            int maxDamage = heldStack.getMaxDamage();
            if (heldStack.getItemDamage() >= maxDamage)
            {
            	//break tool
            	heldStack.stackSize = 0;
            	player.playSound( "random.break", 0.8F, 0.8F + player.worldObj.rand.nextFloat() * 0.4F );            	            	
            }
        	return true;
        }
        return false;
    }

	private void cutCake(World world, int i, int j, int k, EntityPlayer player) {
        int iEatState = GetEatState( world, i, j, k ) + 1; 

        if ( iEatState >= 6 )
        {
            world.setBlockWithNotify( i, j, k, 0 );
        }
        else
        {
            SetEatState( world, i, j, k, iEatState );                
        }
        
        if(!world.isRemote)
        {
        	
        	FCUtilsItem.DropSingleItemAsIfBlockHarvested(world, i, j, k, getSliceItem(), 0);
        }
		
	}
	
    public int GetEatState( int meta )
    {
    	return ( meta & 7 );
    }
	
    public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( int meta)
    {
        int iEatState = GetEatState( meta );
        
        float fWidth = (float)( 1 + iEatState * 2 ) / 16.0F;
        
        return AxisAlignedBB.getAABBPool().getAABB(         
        	fWidth, 0.0F, m_fBorderWidth, 
        	1.0F - m_fBorderWidth, m_fHeight, 1.0F - m_fBorderWidth );
    }
    
    protected Icon cakeTopIcon;
    protected Icon cakeBottomIcon;
    protected Icon cakeInsideIcon; //inside
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.cakeTopIcon : (par1 == 0 ? this.cakeBottomIcon : (par2 > 0 && par1 == 4 ? this.cakeInsideIcon : this.blockIcon));
    }
    
    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        float var1 = 0.0625F;
        float var2 = 0.5F;
        this.setBlockBounds(var1, 0.0F, var1, 1.0F - var1, var2, 1.0F - var1);
    }

}