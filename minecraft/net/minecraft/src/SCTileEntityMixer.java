package net.minecraft.src;

public class SCTileEntityMixer extends TileEntity implements FCITileEntityDataPacketHandler {

	public int moveCount;
	
	public SCTileEntityMixer()
	{
		moveCount = 0;
	}
	
	public int getMoveCount() {
		return moveCount;
	}

	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}
	
	@Override
    public void updateEntity()
    {
        int iBlockID = worldObj.getBlockId( xCoord, yCoord, zCoord );
        
        SCBlockMixer mixer = (SCBlockMixer)Block.blocksList[iBlockID];    
        
        if (!worldObj.isRemote)
        {
            if( mixer.IsInputtingMechanicalPower(worldObj, xCoord, yCoord, zCoord) )
            {
                if ( worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 1)
                {
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1);
                }

            }
            else
            {
                if ( worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 0)
                {
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0);
                }
            }    
            
            //System.out.println(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
        }
        
        if( worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 1 )
        {
            if (moveCount < 100)
            {
            	moveCount++;
            }
        }
        else if( worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 0 )
        {
        	if (moveCount > 0)
        	{
        		moveCount--;
        	}
        	
        }
        
    }
	
	@Override
    public void readFromNBT( NBTTagCompound tag )
    {
        super.readFromNBT( tag );
        
        if ( tag.hasKey( "moveCount" ) )
        {
        	moveCount = tag.getInteger( "moveCount" );
        }
        
    }
    
    @Override
    public void writeToNBT( NBTTagCompound tag )
    {
        super.writeToNBT(tag);
                
        tag.setInteger( "moveCount", moveCount );   
        
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 
        
    }
    
    @Override
    public void readNBTFromPacket( NBTTagCompound tag )
    {
        
        if ( tag.hasKey( "moveCount" ) )
        {
        	moveCount = tag.getInteger( "moveCount" );
        }
        
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
    	NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        
        nbttagcompound1.setInteger( "moveCount", moveCount );
        
        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, nbttagcompound1 );
    }


}
