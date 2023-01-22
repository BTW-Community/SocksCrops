package net.minecraft.src;

public class SCTileEntityChurn extends TileEntity implements FCITileEntityDataPacketHandler {
    private int counter = 0;
	private int fillLevel = 15;


	public int getFillLevel() {
		return fillLevel;
	}

	public void setFillLevel(int fillLevel) {
		this.fillLevel = fillLevel;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	@Override
	public void updateEntity() {
		if (this.counter > 0)
		{
			counter--;
			
			System.out.println(counter);
		}
		
		
		if (getFillLevel() > 0 && getCounter() == 1)
		{
			setFillLevel(getFillLevel() - 1);
			
			markBlockForUpdate();
		}
	}

	@Override
    public void readFromNBT(NBTTagCompound tag) {
    	super.readFromNBT(tag);
    	
        if ( tag.hasKey( "counter" ) )
        {
        	counter   = tag.getInteger( "counter" );
        }
        
        if ( tag.hasKey( "counter" ) )
        {
        	fillLevel    = tag.getInteger( "fillLevel" );
        }
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tag) {
    	super.writeToNBT(tag);
    	
        tag.setInteger( "counter", counter );
        tag.setInteger( "fillLevel", fillLevel );
                
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
    	NBTTagCompound tag = new NBTTagCompound();

    	tag.setInteger( "counter", counter );
    	tag.setInteger( "fillLevel", fillLevel );
        
        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, tag );
    }
	
    
    @Override
    public void readNBTFromPacket( NBTTagCompound tag )
    {
        if ( tag.hasKey( "counter" ) )
        {
        	counter   = tag.getInteger( "counter" );
        }
        
        if ( tag.hasKey( "fillLevel" ) )
        {
        	counter   = tag.getInteger( "fillLevel" );
        }
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord );        
    }

	public void markBlockForUpdate() {
		worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord );    
	} 
}
