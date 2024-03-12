package net.minecraft.src;

import java.util.Random;

public class SCBlockWickerBasketFireProof extends FCBlockBasketWicker {

	public SCBlockWickerBasketFireProof(int iBlockID) {
		super(iBlockID);
		
		SetFireProperties( FCEnumFlammability.NONE );
	}
	
	@Override
    public TileEntity createNewTileEntity( World world )
    {
        return new SCTileEntityWickerBasket();
    }
	
	@Override
	public int idDropped(int par1, Random random, int par3) {
		
		return FCBetterThanWolves.fcBlockBasketWicker.blockID;
	}
	
	@Override
	public int idPicked(World world, int x, int y, int z) {
		return FCBetterThanWolves.fcBlockBasketWicker.blockID;
	}
	
	@Override
    public void breakBlock( World world, int i, int j, int k, int iBlockID, int iMetadata )
    {
		SCTileEntityWickerBasket tileEntity = (SCTileEntityWickerBasket)world.getBlockTileEntity( i, j, k );
        
        tileEntity.EjectContents();
        
        super.breakBlock( world, i, j, k, iBlockID, iMetadata );	        
    }
	
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
		int iMetadata = world.getBlockMetadata( i, j, k );
		
		if ( !GetIsOpen( iMetadata ) )
		{
			if ( !world.isRemote )
			{
				SetIsOpen( world, i, j, k, true );
			}
			else
			{
				player.playSound( "step.gravel", 
		    		0.25F + ( world.rand.nextFloat() * 0.1F ), 
		    		0.5F + ( world.rand.nextFloat() * 0.1F ) );
			}
			
			return true;
		}
		else if ( IsClickingOnLid ( world, i, j, k, iFacing, fXClick, fYClick, fZClick ) ) 
		{
			SCTileEntityWickerBasket tileEntity = (SCTileEntityWickerBasket)world.getBlockTileEntity( i, j, k );
	        
			if ( !tileEntity.m_bClosing )
			{
				if ( !world.isRemote )
				{
					tileEntity.StartClosingServerSide();
				}
				
				return true;
			}
		}
		else if ( GetHasContents( iMetadata ) )
		{
			if ( world.isRemote )
			{
				player.playSound( "step.gravel", 
		    		0.5F + ( world.rand.nextFloat() * 0.25F ), 
		    		1F + ( world.rand.nextFloat() * 0.25F ) );
			}
			else
			{
				EjectStorageStack( world, i, j, k );
			}
			
			SetHasContents( world, i, j, k, false );			
			
			return true;
		}
		else 
    	{
	    	ItemStack heldStack = player.getCurrentEquippedItem();
	    	
			if ( heldStack != null )
			{
				if ( world.isRemote )
				{
					player.playSound( "step.gravel", 
			    		0.5F + ( world.rand.nextFloat() * 0.25F ), 
			    		0.5F + ( world.rand.nextFloat() * 0.25F ) );
				}
				else
				{				
			        SCTileEntityWickerBasket tileEntity = (SCTileEntityWickerBasket)world.getBlockTileEntity( i, j, k );
			        
		        	tileEntity.SetStorageStack( heldStack );
				}
				
				//SC CHANGE
//    			heldStack.stackSize = 0;
				heldStack.stackSize--;
    			
    			SetHasContents( world, i, j, k, true );			
    			
    			return true;
			}   
			
    	}
		
		return false;
    }
	
	@Override
	protected void EjectStorageStack( World world, int i, int j, int k )
    {    	
		SCTileEntityWickerBasket tileEntity = (SCTileEntityWickerBasket)world.getBlockTileEntity( i, j, k );
        
        ItemStack storageStack = tileEntity.GetStorageStack();

        if ( storageStack != null )
        {
	        float xOffset = 0.5F;
	        float yOffset = 0.4F;
	        float zOffset = 0.5F;
	        
	        double xPos = (float)i + xOffset;
	        double yPos = (float)j + yOffset;
	        double zPos = (float)k + zOffset;
	        
            EntityItem entityitem = 
            		(EntityItem) EntityList.createEntityOfType(EntityItem.class, world, xPos, yPos, zPos, storageStack );

            entityitem.motionY = 0.2D;
            
            double fFacingFactor = 0.15D;
            double fRandomFactor = 0.05D;
            
            int iFacing = GetFacing( world, i, j, k );

            if ( iFacing <= 3 )
            {
                entityitem.motionX = ( world.rand.nextDouble() * 2D - 1D ) * fRandomFactor;
                
                if ( iFacing == 2 )
                {
                	entityitem.motionZ = -fFacingFactor;
                }
                else // 3
                {
                	entityitem.motionZ = fFacingFactor;
                }
            }
            else
            {
                entityitem.motionZ = ( world.rand.nextDouble() * 2D - 1D )  * fRandomFactor;
                
	        	if ( iFacing == 4 )
	            {
	            	entityitem.motionX = -fFacingFactor;
	            }
	            else // 5
	            {
	            	entityitem.motionX = fFacingFactor;
	            }
            }
            
            entityitem.delayBeforeCanPickup = 10;
            
            world.spawnEntityInWorld( entityitem );
            
			tileEntity.SetStorageStack( null );
        }
    }
	
	@Override
    public MovingObjectPosition collisionRayTrace( World world, int i, int j, int k, Vec3 startRay, Vec3 endRay )
    {
    	FCUtilsRayTraceVsComplexBlock rayTrace = new FCUtilsRayTraceVsComplexBlock( world, i, j, k, startRay, endRay );
    	
    	int iMetadata = world.getBlockMetadata( i, j, k );
    	int iFacing = GetFacing( iMetadata );
    	
    	FCModelBlock tempBaseModel;
    	
    	if ( !GetIsOpen( iMetadata ) )
		{ 
        	tempBaseModel = m_blockModelBase.MakeTemporaryCopy();
        	
	    	FCModelBlock tempLidModel;
	    	
	    	if ( GetHasContents( iMetadata ) )
	    	{
	    		tempLidModel = m_blockModelLidFull.MakeTemporaryCopy();
	    	}
	    	else
	    	{
	    		tempLidModel = m_blockModelLid.MakeTemporaryCopy();
	    	}
	    	
	    	tempLidModel.RotateAroundJToFacing( iFacing );
	    	
	    	tempLidModel.AddToRayTrace( rayTrace );
		}
    	else
    	{
        	tempBaseModel = m_blockModelBaseOpenCollision.MakeTemporaryCopy();
        	
        	SCTileEntityWickerBasket tileEntity = (SCTileEntityWickerBasket)world.getBlockTileEntity( i, j, k );
            
            if ( tileEntity.m_fLidOpenRatio > 0.95F )
            {
    	    	AxisAlignedBB tempLidBox = m_boxCollisionLidOpenLip.MakeTemporaryCopy();
    	    	
    	    	tempLidBox.RotateAroundJToFacing( iFacing );
    	    	
    	    	rayTrace.AddBoxWithLocalCoordsToIntersectionList( tempLidBox );
            }	            
    	}
		
    	tempBaseModel.RotateAroundJToFacing( iFacing );
    	
    	tempBaseModel.AddToRayTrace( rayTrace );
		
        return rayTrace.GetFirstIntersection();
    }

}
