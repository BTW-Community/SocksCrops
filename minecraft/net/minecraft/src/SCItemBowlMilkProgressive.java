package net.minecraft.src;

import java.util.List;

public class SCItemBowlMilkProgressive extends FCItemCraftingProgressive {
	
	static public final int milkMaxDamage = ( 6 * 20 / m_iProgressTimeInterval );
	
	public SCItemBowlMilkProgressive(int iItemID, String name) {
		super(iItemID);
		
        SetBuoyant();
        SetBellowsBlowDistance( 2 );
    	SetIncineratedInCrucible();
//    	SetFurnaceBurnTime( FCEnumFurnaceBurnTime.WICKER_PIECE );
        SetFilterableProperties( Item.m_iFilterable_Thin );
    	
		setUnlocalizedName(name);
	}
	
//	@Override
//	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
//		 par3List.add(new ItemStack(par1, 1, GetProgressiveCraftingMaxDamage()));
//	}
	
	private Item returnItem()
	{
		return Item.bowlEmpty;
	}

    @Override
    protected void PlayCraftingFX( ItemStack stack, World world, EntityPlayer player )
    {
    	
        player.playSound( "mob.slime.small", 
        	0.5F + 0.5F * (float)world.rand.nextInt( 2 ), 
        	( world.rand.nextFloat() * 0.25F ) + 1.25F );
        
        SpawnUseParticles( stack, world, player );
    }
    
    @Override
    public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
    {
        world.playSoundAtEntity( player, "mob.slime.small", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );       
        
        ItemStack newStack = new ItemStack (SCDefs.butterPiece);
        
        FCUtilsItem.GivePlayerStackOrEject(player, newStack);
        
        return new ItemStack( returnItem(), 1, 0 );
    }
    
    @Override
    public void onCreated( ItemStack stack, World world, EntityPlayer player ) 
    {
		if ( player.m_iTimesCraftedThisTick == 0 && world.isRemote )
		{
			player.playSound( "mob.slime.small", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );
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
    	return milkMaxDamage;
    }
    
    protected void SpawnUseParticles( ItemStack stack, World world, EntityPlayer player )
    {
	    if ( world.isRemote )
	    {
	        Vec3 velVec = world.getWorldVec3Pool().getVecFromPool(
	        	( world.rand.nextFloat() - 0.5D ) * 0.1D, Math.random() * 0.1D + 0.1D, 0D );
	        
	        velVec.rotateAroundX( -player.rotationPitch * (float)Math.PI / 180F );
	        velVec.rotateAroundY( -player.rotationYaw * (float)Math.PI / 180F );
	        
	        Vec3 posVec = world.getWorldVec3Pool().getVecFromPool(
	        	( world.rand.nextFloat() - 0.5D ) * 0.3D,
	        	( -world.rand.nextFloat()) * 0.6D - 0.3D, 0.6D );
	        
	        posVec.rotateAroundX( -player.rotationPitch * (float)Math.PI / 180F );
	        posVec.rotateAroundY( -player.rotationYaw * (float)Math.PI / 180F );
	        
	        posVec = posVec.addVector( player.posX, player.posY + player.getEyeHeight(), player.posZ );
	        
	        world.spawnParticle( "iconcrack_" + Item.snowball.itemID,//stack.getItem().itemID, 
	        	posVec.xCoord, posVec.yCoord, posVec.zCoord, 
	        	velVec.xCoord, velVec.yCoord + 0.05D, velVec.zCoord );
	    }
    }
}