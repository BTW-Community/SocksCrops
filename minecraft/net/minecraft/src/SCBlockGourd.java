// FCMOD

package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public abstract class SCBlockGourd extends FCBlockFalling
{
	private static final double m_dArrowSpeedSquaredToExplode = 1.10D;
	
    protected SCBlockGourd( int iBlockID )
    {
        super( iBlockID, Material.pumpkin );
        
        SetAxesEffectiveOn( true );
        SetBuoyant();
        
        setTickRandomly( true );        
        
        setCreativeTab( CreativeTabs.tabBlock );
    }
    
    @Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
    	super.updateTick( world, i, j, k, rand );
    	
    	// necessary to check blockID because super.updateTick may cause it to fall
    	
//        if ( world.getBlockId( i, j, k ) == blockID )
//        {
//        	ValidateConnectionState( world, i, j, k );
//        }
    }
    
    @Override
    public int getMobilityFlag()
    {
    	// allow gourds to be pushed by pistons
    	
    	return 0;
    }
    
    @Override
    public void OnArrowImpact( World world, int i, int j, int k, EntityArrow arrow )
    {
    	if ( !world.isRemote )
    	{
    		double dArrowSpeedSq = arrow.motionX * arrow.motionX + arrow.motionY * arrow.motionY + arrow.motionZ * arrow.motionZ;
    		
    		if ( dArrowSpeedSq >= m_dArrowSpeedSquaredToExplode )
    		{
	    		world.setBlockWithNotify( i, j, k, 0 );
	    		
	    		Explode( world, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D );
    		}
    		else
    		{    		
    			world.playAuxSFX( FCBetterThanWolves.m_iMelonImpactSoundAuxFXID, i, j, k, 0 );
    		}
    	}
    }
    
    @Override
    public void OnBlockDestroyedWithImproperTool( World world, EntityPlayer player, int i, int j, int k, int iMetadata )
    {
    	// there's no improper tool to harvest gourds, but this also happens if the block is deleted after falling due to sitting on an
    	// improper block
    	
        world.playAuxSFX( AuxFXIDOnExplode(), i, j, k, 0 );
    }
    
    @Override
    public boolean OnFinishedFalling( EntityFallingSand entity, float fFallDistance )
    {
    	//entity.metadata = 0; // reset stem connection
    	
    	if ( !entity.worldObj.isRemote )
    	{
	        int i = MathHelper.floor_double( entity.posX );
	        int j = MathHelper.floor_double( entity.posY );
	        int k = MathHelper.floor_double( entity.posZ );
	        
	        int iFallDistance = MathHelper.ceiling_float_int( entity.fallDistance - 5.0F );
	        
	    	if ( iFallDistance >= 0 )
	    	{	    		
	    		DamageCollidingEntitiesOnFall( entity, fFallDistance );
	    		
	    		if ( !Material.water.equals( entity.worldObj.getBlockMaterial( i, j, k ) ) )
	    		{	    			
		    		if ( entity.rand.nextInt( 10 ) < iFallDistance )
		    		{
		    			Explode( entity.worldObj, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D );
		    			
		    			return false;
		    		}
	    		}
	    	}
	    	
			entity.worldObj.playAuxSFX( FCBetterThanWolves.m_iMelonImpactSoundAuxFXID, i, j, k, 0 );
    	}
        
    	return true;
    }    
    
	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iBlockID )
    {
		super.onNeighborBlockChange( world, i, j, k, iBlockID );
		
		//ValidateConnectionState( world, i, j, k );
    }
	
    @Override
    public boolean CanBeGrazedOn( IBlockAccess access, int i, int j, int k, EntityAnimal animal )
    {
		return animal.CanGrazeOnRoughVegetation();
    }

    //------------- Class Specific Methods ------------//
    
	abstract protected Item ItemToDropOnExplode();
	
	abstract protected int ItemCountToDropOnExplode();
	
	abstract protected int AuxFXIDOnExplode();
	
	abstract protected DamageSource GetFallDamageSource();	
	
    private void Explode( World world, double posX, double posY, double posZ )
    {
    	Item itemToDrop = ItemToDropOnExplode();
    	
    	if ( itemToDrop != null )
    	{
	        for (int iTempCount = 0; iTempCount < ItemCountToDropOnExplode(); iTempCount++)
	        {
	    		ItemStack itemStack = new ItemStack( itemToDrop, 1, 0 );
	
	            EntityItem entityItem = (EntityItem) EntityList.createEntityOfType(EntityItem.class, world, posX, posY+0.5, posZ, itemStack );
	            
	            entityItem.motionX = ( world.rand.nextDouble() - 0.5D ) * 0.5D;
	            entityItem.motionY = 0.2D + world.rand.nextDouble() * 0.3D;
	            entityItem.motionZ = ( world.rand.nextDouble() - 0.5D ) * 0.5D;
	            
	            entityItem.delayBeforeCanPickup = 10;
	            
	            world.spawnEntityInWorld( entityItem );
	        }
    	}
    	
    	NotifyNearbyAnimalsFinishedFalling( world, MathHelper.floor_double( posX ), MathHelper.floor_double( posY ), MathHelper.floor_double( posZ ) );
        
        world.playAuxSFX( AuxFXIDOnExplode(), 
    		MathHelper.floor_double( posX ), MathHelper.floor_double( posY ), MathHelper.floor_double( posZ ), 
    		0 );
    }
    
    private void DamageCollidingEntitiesOnFall( EntityFallingSand entity, float fFallDistance )
    {
        int var2 = MathHelper.ceiling_float_int( fFallDistance - 1.0F );

        if (var2 > 0)
        {
            ArrayList collisionList = new ArrayList( entity.worldObj.getEntitiesWithinAABBExcludingEntity( entity, entity.boundingBox ) );
            
            DamageSource source = GetFallDamageSource();
            
            Iterator iterator = collisionList.iterator();

            while ( iterator.hasNext() )
            {
                Entity tempEntity = (Entity)iterator.next();
                
                tempEntity.attackEntityFrom( source, 1 );
            }

        }
    }
    
//    protected void ValidateConnectionState( World world, int i, int j, int k )
//    {
//    	int iMetadata = world.getBlockMetadata( i, j, k );
//    	
//    	if ( iMetadata > 0 )
//    	{
//            FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
//
//            if ( iMetadata >= 2 && iMetadata <= 5 )
//            {
//	            targetPos.AddFacingAsOffset( iMetadata );
//	            
//	            int iTargetBlockID = world.getBlockId( targetPos.i, targetPos.j, targetPos.k );
//	            
//	            // FCTODO: Hacky
//	            if ( Block.blocksList[iTargetBlockID] == null || 
//	            	!( Block.blocksList[iTargetBlockID] instanceof FCBlockStem ) ||
//	            	world.getBlockMetadata( targetPos.i, targetPos.j, targetPos.k ) != 15 )
//	            {            	
//	                world.setBlockMetadata( i, j, k, 0 ); // no notify                
//	            }
//            }
//            else
//            {
//                // There may be old gourds laying about that have invalid metadata
//                
//                world.setBlockMetadata( i, j, k, 0 ); // no notify                
//            }
//    	}
//    }
    
	//----------- Client Side Functionality -----------//
    
//    protected Icon m_IconTop;
//	
//    @Override
//    public Icon getIcon( int iSide, int iMetadata )
//    {
//    	if ( iSide == 1 || iSide == 0 )
//    	{
//    		return m_IconTop;
//    	}
//    	
//    	return blockIcon;
//    }
	//----------- Client Side Functionality -----------//
	
	private AxisAlignedBB GetPumpkinBounds(double size, double height)
	{
    	AxisAlignedBB pumpkinBox = null;
    	
    	pumpkinBox = AxisAlignedBB.getAABBPool().getAABB( 
    			8/16D - size, 0.0D, 8/16D - size, 
    			8/16D + size, height, 8/16D + size);
    	
    		return pumpkinBox;
		
	}
	
	@Override
	public void RenderFallingBlock(RenderBlocks renderer, int i, int j, int k, int iMetadata) {
		
		int meta =iMetadata;
		
		//Orange
		if (meta == 0) {
			renderer.setRenderBounds(GetPumpkinBounds(3/16D, 6/16D)); // stage 0 = 6x6x6
			
		}else if (meta == 1){
			renderer.setRenderBounds(GetPumpkinBounds(4/16D, 8/16D)); // stage 1 = 8x8x8
			//renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 2){
			renderer.setRenderBounds(GetPumpkinBounds(6/16D, 12/16D)); // stage 2 = 12x12x12
			//renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 3){
			renderer.setRenderBounds(GetPumpkinBounds(8/16D, 16/16D)); // stage 2 = 14x14x14
			//renderer.renderStandardBlock( this, i, j, k );
		}
		//Green
		else if (meta == 4){
			renderer.setRenderBounds(GetPumpkinBounds(3/16D, 4/16D)); // stage 1 = 8x8x8
			//renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 5){
			renderer.setRenderBounds(GetPumpkinBounds(4/16D, 5/16D)); // stage 2 = 12x12x12
			//renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 6){
			renderer.setRenderBounds(GetPumpkinBounds(6/16D, 6/16D)); // stage 2 = 14x14x14
			//renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 7){
			renderer.setRenderBounds(GetPumpkinBounds(8/16D, 8/16D)); // stage 2 = 14x14x14
			//renderer.renderStandardBlock( this, i, j, k );
		}
		//Yellow
		else if (meta == 8){
			renderer.setRenderBounds(GetPumpkinBounds(2/16D, 4/16D)); // stage 1 = 8x8x8
			//renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 9){
			renderer.setRenderBounds(GetPumpkinBounds(3/16D, 6/16D)); // stage 2 = 12x12x12
			//renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 10){
			renderer.setRenderBounds(GetPumpkinBounds(4/16D, 8/16D)); // stage 2 = 14x14x14
			//renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 11){
			renderer.setRenderBounds(GetPumpkinBounds(6/16D, 12/16D)); // stage 2 = 14x14x14
			//renderer.renderStandardBlock( this, i, j, k );
		}
		//White
		else if (meta == 12){
			renderer.setRenderBounds(GetPumpkinBounds(2/16D, 3/16D)); // stage 1 = 8x8x8
			//renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 13){
			renderer.setRenderBounds(GetPumpkinBounds(3/16D, 4/16D)); // stage 2 = 12x12x12
			//renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 14){
			renderer.setRenderBounds(GetPumpkinBounds(4/16D, 5/16D)); // stage 2 = 14x14x14
			//renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 15){
			renderer.setRenderBounds(GetPumpkinBounds(5/16D, 6/16D)); // stage 2 = 14x14x14
			//renderer.renderStandardBlock( this, i, j, k );
		}
		
		renderer.RenderStandardFallingBlock( this, i, j, k, iMetadata );
	}
	
//----------- Client Side Functionality -----------//
    
    private Icon[] orangeIcon;
    private Icon[] orangeIconTop;
    private Icon[] greenIcon;
    private Icon[] greenIconTop;
    private Icon[] yellowIcon;
    private Icon[] yellowIconTop;
    private Icon[] whiteIcon;
    private Icon[] whiteIconTop;

    @Override
    public void registerIcons( IconRegister register )
    {
//		blockIcon = register.registerIcon( "pumpkin_side" );
//		
//		m_IconTop = register.registerIcon( "pumpkin_top" );
		
		//Orange
    	orangeIcon = new Icon[4];
		
		for ( int iTempIndex = 0; iTempIndex < orangeIcon.length; iTempIndex++ )
		{
			orangeIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinSide_" + iTempIndex );
		}
		
		orangeIconTop = new Icon[4];
		
		for ( int iTempIndex = 0; iTempIndex < orangeIconTop.length; iTempIndex++ )
		{
			orangeIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinTop_" + iTempIndex );
		}
		

		//Green
		greenIcon = new Icon[4];
		
        for ( int iTempIndex = 0; iTempIndex < greenIcon.length; iTempIndex++ )
        {
        	greenIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinGreenSide_" + iTempIndex );
        }
        
        greenIconTop = new Icon[4];

        for ( int iTempIndex = 0; iTempIndex < greenIconTop.length; iTempIndex++ )
        {
        	greenIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinGreenTop_" + iTempIndex );
        }
        
        //Yellow
        yellowIcon = new Icon[4];
		
		for ( int iTempIndex = 0; iTempIndex < yellowIcon.length; iTempIndex++ )
		{
			yellowIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinYellowSide_" + iTempIndex );
		}
		
		yellowIconTop = new Icon[4];
		
		for ( int iTempIndex = 0; iTempIndex < yellowIconTop.length; iTempIndex++ )
		{
			yellowIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinYellowTop_" + iTempIndex );
		}
		
        //White
		whiteIcon = new Icon[4];
		
		for ( int iTempIndex = 0; iTempIndex < whiteIcon.length; iTempIndex++ )
		{
			whiteIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinWhiteSide_" + iTempIndex );
		}
		
		whiteIconTop = new Icon[4];
		
		for ( int iTempIndex = 0; iTempIndex < whiteIconTop.length; iTempIndex++ )
		{
			whiteIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinWhiteTop_" + iTempIndex );
		}
    }
    
    @Override
    public Icon getIcon( int iSide, int iMetadata )
    {
    	if (iMetadata == 1)
    	{
			if ( iSide == 1 || iSide == 0 )
        	{
        		return orangeIconTop[1];
        	}
        	
    		else return orangeIcon[1];
    	}
    	else if (iMetadata == 2)
    	{
			if ( iSide == 1 || iSide == 0 )
        	{
        		return orangeIconTop[2];
        	}
        	
    		else return orangeIcon[2];
    	}
    	else if (iMetadata == 3)
    	{
			if ( iSide == 1 || iSide == 0 )
        	{
        		return orangeIconTop[3];
        	}
        	
    		else return orangeIcon[3];
    	}
    	else if (iMetadata == 4)
    	{
			if ( iSide == 1 || iSide == 0 )
        	{
        		return greenIconTop[0];
        	}
        	
    		else return greenIcon[0];
    	}
    	else if (iMetadata == 5)
    	{
			if ( iSide == 1 || iSide == 0 )
        	{
        		return greenIconTop[1];
        	}
        	
    		else return greenIcon[1];
    	}
    	else if (iMetadata == 6)
    	{
			if ( iSide == 1 || iSide == 0 )
        	{
        		return greenIconTop[2];
        	}
        	
    		else return greenIcon[2];
    	}
    	else if (iMetadata == 7)
    	{
			if ( iSide == 1 || iSide == 0 )
        	{
        		return greenIconTop[3];
        	}
        	
    		else return greenIcon[3];
    	}
    	else if (iMetadata == 8)
    	{
			if ( iSide == 1 || iSide == 0 )
        	{
        		return yellowIconTop[0];
        	}
        	
    		else return yellowIcon[0];
    	}
    	else if (iMetadata == 9)
    	{
			if ( iSide == 1 || iSide == 0 )
        	{
        		return yellowIconTop[1];
        	}
        	
    		else return yellowIcon[1];
    	}
    	else if (iMetadata == 10)
    	{
			if ( iSide == 1 || iSide == 0 )
        	{
        		return yellowIconTop[2];
        	}
        	
    		else return yellowIcon[2];
    	}
    	else if (iMetadata == 11)
    	{
			if ( iSide == 1 || iSide == 0 )
        	{
        		return yellowIconTop[3];
        	}
        	
    		else return yellowIcon[3];
    	}
    	else if (iMetadata == 12)
    	{
			if ( iSide == 1 || iSide == 0 )
        	{
        		return whiteIconTop[0];
        	}
        	
    		else return whiteIcon[0];
    	}
    	else if (iMetadata == 13)
    	{
			if ( iSide == 1 || iSide == 0 )
        	{
        		return whiteIconTop[1];
        	}
        	
    		else return whiteIcon[1];
    	}
    	else if (iMetadata == 14)
    	{
			if ( iSide == 1 || iSide == 0 )
        	{
        		return whiteIconTop[2];
        	}
        	
    		else return whiteIcon[2];
    	}
    	else if (iMetadata == 15)
    	{
			if ( iSide == 1 || iSide == 0 )
        	{
        		return whiteIconTop[3];
        	}
        	
    		else return whiteIcon[3];
    	}
    	else //meta 0
    	{
			if ( iSide == 1 || iSide == 0 )
        	{
        		return orangeIconTop[0];
        	}
        	
    		else return orangeIcon[0];
    	}
    	
    	
    	
    }
	
}