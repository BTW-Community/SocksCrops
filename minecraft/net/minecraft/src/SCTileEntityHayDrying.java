// FCMOD

package net.minecraft.src;

import java.util.List;

public class SCTileEntityHayDrying extends TileEntity implements FCITileEntityDataPacketHandler
{
    private final int timeToCook = ( 10 * 60 ); //( 10 * 60 * 20 );
    private final int m_iRainCookDecay = 10;
    
	private int cookCounter = 0;
	
	private boolean isDrying = false;
	
    public SCTileEntityHayDrying()
    {
    	super();
    }
    
    @Override
    public void readFromNBT( NBTTagCompound tag )
    {
        super.readFromNBT( tag );
        
        if( tag.hasKey( "cookCounter" ) )
        {
        	cookCounter = tag.getInteger( "cookCounter" );
        }
    }
    
    @Override
    public void writeToNBT( NBTTagCompound tag)
    {
        super.writeToNBT( tag );
        
        tag.setInteger( "cookCounter", cookCounter );
    }
        
    @Override
    public void updateEntity()
    {
    	super.updateEntity();   

    	if ( !worldObj.isRemote )
    	{
    		UpdateCooking();
    	}
    	else 
    	{    
    		if ( isDrying )
    		{
				if ( worldObj.rand.nextInt( 20 ) == 0 )
				{
	                double xPos = xCoord + 0.25F + worldObj.rand.nextFloat() * 0.5F;
	                double yPos = yCoord + 0.5F + worldObj.rand.nextFloat() * 0.25F;
	                double zPos = zCoord + 0.25F + worldObj.rand.nextFloat() * 0.5F;
	                
	                worldObj.spawnParticle( "fcwhitesmoke", xPos, yPos, zPos, 0.0D, 0.0D, 0.0D );
	            }
    		}
    	}
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tag = new NBTTagCompound();
        
        tag.setBoolean( "x", isDrying );
        
        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, tag );
    }
    
    //------------- FCITileEntityDataPacketHandler ------------//
    
    @Override
    public void readNBTFromPacket( NBTTagCompound tag )
    {
        isDrying = tag.getBoolean( "x" );

        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord );        
    }
    
    //------------- Class Specific Methods ------------//
    
    public void UpdateCooking()
    {
		boolean newDrying;
    	int blockMaxNaturalLight = worldObj.GetBlockNaturalLightValueMaximum( xCoord, yCoord, zCoord );
    	int blockCurrentNaturalLight = blockMaxNaturalLight - worldObj.skylightSubtracted;
    	
    	newDrying = blockCurrentNaturalLight >= 15;
    	
    	int blockAboveID = worldObj.getBlockId( xCoord, yCoord + 1, zCoord );
    	Block blockAbove = Block.blocksList[blockAboveID];
    	
    	if ( blockAbove != null && blockAbove.IsGroundCover( ) )
    	{
    		newDrying = false;
    	}
    	
    	if ( newDrying != isDrying )
    	{			
    		isDrying = newDrying;
		
    		worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
    	}
    	
    	SCBlockHayDrying hayBlock = (SCBlockHayDrying) SCDefs.dryingHay;
    	
    	if ( isDrying )
    	{
    		cookCounter++;
    		
    		if ( cookCounter >= timeToCook )
    		{
    			hayBlock.OnFinishedCooking( worldObj, xCoord, yCoord, zCoord );
    			
    			return;
    		}
    	}
    	else
    	{
    		if ( IsRainingOnBrick( worldObj, xCoord, yCoord, zCoord ) )
    		{
    			cookCounter -= m_iRainCookDecay;
    			
    			if ( cookCounter < 0 )
    			{
    				cookCounter = 0;
    			}
    		}    		
    	}
    	
    	int iDisplayedCookLevel = hayBlock.GetCookLevel( worldObj, xCoord, yCoord, zCoord );
    	int iCurrentCookLevel = ComputeCookLevel();
		
    	if ( iDisplayedCookLevel != iCurrentCookLevel )
    	{
    		hayBlock.SetCookLevel( worldObj, xCoord, yCoord, zCoord, iCurrentCookLevel );
    	}
    }
    
    public boolean IsRainingOnBrick( World world, int i, int j, int k )
    {
    	return world.isRaining() && world.IsRainingAtPos( i, j, k );
    }
    
    private int ComputeCookLevel()
    {
    	if ( cookCounter > 0 )
		{
			int iCookLevel= (int)( ( (float)cookCounter / (float)timeToCook ) * 7F ) + 1;
			
			return MathHelper.clamp_int( iCookLevel, 0, 7 );
		}
    	
    	return 0;
    }
    
    //----------- Client Side Functionality -----------//
}
