package net.minecraft.src;

import java.util.Random;

public class SCBlockPieCooked extends SCBlockPieBase {

	public static final int subtypePumpkin = 0;
	
	protected SCBlockPieCooked(int blockID) {
		super(blockID);
		setUnlocalizedName("SCBlockPieCooked");
	}
	
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {        
    	ItemStack heldStack = player.getHeldItem();
        
        if (heldStack == null)
        {
        	 EatCakeSliceLocal( world, i, j, k, player );
        	 return true;
        }
        else if (heldStack.getItem() instanceof SCItemKnife)
        {
        	cutPie(world, i, j, k, player);
        	world.playSoundAtEntity( player, this.stepSound.getStepSound(), this.stepSound.getVolume() * 0.5F, this.stepSound.getPitch() * 0.3F );
        	
            heldStack.attemptDamageItem(1, world.rand);
            
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
    
    private void cutPie(World world, int i, int j, int k, EntityPlayer player)
    {
        int iEatState = GetEatState( world, i, j, k ) + 1; 

        if ( iEatState >= 4 )
        {
            world.setBlockWithNotify( i, j, k, 0 );
        }
        else
        {
            SetEatState( world, i, j, k, iEatState );                
        }
        
        if(!world.isRemote)
        {
        	int sliceID = 0;
        	 
        	if (getType(world.getBlockMetadata(i, j, k)) == 0) sliceID = SCDefs.pumpkinPieSlice.itemID;
        	
        	FCUtilsItem.DropSingleItemAsIfBlockHarvested(world, i, j, k, sliceID, 0);
        }
	}

	protected void EatCakeSliceLocal( World world, int i, int j, int k, EntityPlayer player )
    {
    	// this function is necessary due to eatCakeSlice() in parent being private
    	
        if ( player.canEat( true ) || player.capabilities.isCreativeMode )
        {
        	// food value adjusted for increased hunger meter resolution
        	
//            player.getFoodStats().addStats( 2, 2.5F );
            player.getFoodStats().addStats( 1, 4F );
            
            int iEatState = GetEatState( world, i, j, k ) + 1; 

            if ( iEatState >= 4 )
            {
                world.setBlockWithNotify( i, j, k, 0 );
            }
            else
            {
                SetEatState( world, i, j, k, iEatState );                
            }
        }
    	else
    	{
    		player.OnCantConsume();
    	}
    }
    
    public int GetEatState( int meta )
    {
    	return meta & 3;
    }
    
    
    public int getType( int meta )
    {
    	if (meta < 4) return 0;
    	else if (meta < 8) return 1;
    	else if (meta < 12) return 2;
    	else return 3;
    }
    
    public int GetEatState( IBlockAccess iBlockAccess, int i, int j, int k )
    {
    	return GetEatState( iBlockAccess.getBlockMetadata( i, j, k ) );
    }
    
    public void SetEatState( World world, int i, int j, int k, int state )
    {
    	int iMetaData = world.getBlockMetadata( i, j, k );
    	
		iMetaData = state;
		
        world.setBlockMetadataWithNotify( i, j, k, iMetaData );
    }
	
	@Override
	public int idDropped( int meta, Random random, int fortuneModifier) {
		
		if (GetEatState(meta) == 0)
			if (getType(meta) == 0) return Item.pumpkinPie.itemID;
			else return 0;
		else return 0;
	}
	
	//----------- Client Side Functionality -----------//

    private Icon cookedPie;
    private Icon cookedPumpkinTop;
    private Icon cookedPumpkinInside;
    private Icon cookedPumpkinInside2;
    private Icon cookedPumpkinInsideHalf;
    private Icon cookedPumpkinInsideHalfRight;
    private Icon[] cookedPieInside = new Icon[4];
   
    protected boolean secondPass;
    
	@Override
    public void registerIcons( IconRegister register )
    {
		cookedPie = register.registerIcon( "SCBlockPieCooked" );
        cookedPumpkinTop = register.registerIcon( "SCBlockPiePumpkinCooked_top" );
        cookedPumpkinInside = register.registerIcon( "SCBlockPiePumpkinCooked_inside" );
        cookedPumpkinInside2 = register.registerIcon( "SCBlockPiePumpkinCooked_inside2" );
        cookedPumpkinInsideHalf = register.registerIcon( "SCBlockPiePumpkinCooked_insideHalf" );
        cookedPumpkinInsideHalfRight = register.registerIcon( "SCBlockPiePumpkinCooked_insideHalfRight" );
       
        cookedPieInside[0] = register.registerIcon( "SCBlockPiePumpkinCooked_insidePumpkin" );
    }
	
	
	@Override
	public Icon getIcon(int side, int meta) {
		
		if (secondPass) {
			return getIconSecondPass(side, meta);
		}
		return cookedPieInside[getType(meta)];
	}

    public Icon getIconSecondPass( int side, int meta )
    {
    	int eatState = GetEatState(meta);
    	
    	if (side == 1) return cookedPumpkinTop;
    	
    	if (eatState == 1)
    	{
    		if (side == 3) return cookedPumpkinInsideHalf;
    		else if (side == 5) return cookedPumpkinInsideHalfRight;
    	}
    	else if (eatState == 2)
    	{
    		if (side == 3) return cookedPumpkinInside;
    	}
    	else if (eatState == 3)
    	{
    		if (side == 3) return cookedPumpkinInside;
    		else if (side == 5) return cookedPumpkinInside2;
    	}
    	else
    	{
    		if (side == 3) return cookedPie;
    	}
    	
    	return cookedPie;
    }
	
    protected double border = 2/16D;
    protected double height = 4/16D;
    protected double width = 12/16D;
    
    @Override
    public void RenderBlockSecondPass(RenderBlocks renderBlocks, int i, int j, int k, boolean bFirstPassResult) {
    	secondPass = true;
    	RenderBlock(renderBlocks, i, j, k);
    	secondPass = false;
    }
    
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{
		int eatState = GetEatState(renderer.blockAccess, i, j, k);
		
		if (eatState == 3)
		{
			renderer.setRenderBounds(
					border, 0 , border,
					border + width/2, height, border + width/2 );			
			
		}
		else {
			renderer.setRenderBounds(
					border, 0 , border,
					border + width, height, border + width/2 );
		}
		
		renderer.renderStandardBlock(this, i, j, k);
		
		if (eatState < 2)
		{
			if (eatState == 0)
			{
				renderer.setRenderBounds(
						border, 0 , border + width/2,
						border + width, height, border + width );
			}
			else
			{
				renderer.setRenderBounds(
						border, 0 , border + width/2,
						border + width/2, height, border + width );
			}

			
			renderer.renderStandardBlock(this, i, j, k);
		}
		

		return true;
	}
}
