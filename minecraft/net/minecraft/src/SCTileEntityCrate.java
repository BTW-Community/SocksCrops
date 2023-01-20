package net.minecraft.src;

public class SCTileEntityCrate extends TileEntity implements FCITileEntityDataPacketHandler {
	
	private int storageType = 0;
	private int woodType = 0;
	private int fillLevel = 0;
	
	public int getStorageType() {
		return storageType;
	}

	public void setStorageType(int storageType) {
		this.storageType = storageType;
		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}

	public int getWoodType() {
		return woodType;
	}

	public void setWoodType(int woodType) {
		this.woodType = woodType;
	}

	public int getFillLevel() {
		return fillLevel;
	}

	public void setFillLevel(int fillLevel) {
		this.fillLevel = fillLevel;
		
		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
		
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		if (tag.hasKey("storageType")) storageType = tag.getInteger("storageType");
		if (tag.hasKey("woodType")) woodType = ( tag.getInteger("woodType") );
		if (tag.hasKey("fillLevel")) fillLevel = ( tag.getInteger("fillLevel") );	
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
		tag.setInteger("storageType", storageType); 
		tag.setInteger("woodType", woodType); 
		tag.setInteger("fillLevel", fillLevel); 
	}
	
	@Override
	public void readNBTFromPacket(NBTTagCompound tag)
	{
		readFromNBT(tag);
		
		// force a visual update for the block since the above variables affect it
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);		
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
	}

}
