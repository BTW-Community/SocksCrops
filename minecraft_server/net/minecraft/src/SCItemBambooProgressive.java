package net.minecraft.src;

public class SCItemBambooProgressive extends FCItemCraftingProgressive {
	
	static public final int bambooWeavingMaxDamage = ( 6 * 20 / m_iProgressTimeInterval );
	
	public SCItemBambooProgressive(int iItemID) {
		super(iItemID);
		
        SetBuoyant();
        SetBellowsBlowDistance( 2 );
    	SetIncineratedInCrucible();
    	SetFurnaceBurnTime( FCEnumFurnaceBurnTime.WICKER_PIECE );
        SetFilterableProperties( Item.m_iFilterable_Thin );
    	
		setUnlocalizedName("SCItemBambooProgressive");
	}
	
	private Item returnItem()
	{
		return SCDefs.bambooWeave;
	}

    @Override
    protected void PlayCraftingFX( ItemStack stack, World world, EntityPlayer player )
    {
        player.playSound( "step.grass", 
        	0.25F + 0.25F * (float)world.rand.nextInt( 2 ), 
        	( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.25F + 1.75F );
    }
    
    @Override
    public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
    {
        world.playSoundAtEntity( player, "step.grass", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );
        
        return new ItemStack( returnItem(), 1, 0 );
    }
    
    @Override
    public void onCreated( ItemStack stack, World world, EntityPlayer player ) 
    {
		if ( player.m_iTimesCraftedThisTick == 0 && world.isRemote )
		{
			player.playSound( "step.grass", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );
		}
		
    	super.onCreated( stack, world, player );
    }
    
    @Override
    public boolean GetCanBeFedDirectlyIntoCampfire( int iItemDamage )
    {
    	return false;
    }
    
    @Override
    public boolean GetCanBeFedDirectlyIntoBrickOven( int iItemDamage )
    {
    	return false;
    }
    
    @Override
    protected int GetProgressiveCraftingMaxDamage()
    {
    	return bambooWeavingMaxDamage;
    }

}
