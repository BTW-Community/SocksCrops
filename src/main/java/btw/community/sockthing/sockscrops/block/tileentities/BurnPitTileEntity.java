package btw.community.sockthing.sockscrops.block.tileentities;

import btw.block.tileentity.TileEntityDataPacketHandler;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import net.minecraft.src.*;

public class BurnPitTileEntity extends TileEntity implements TileEntityDataPacketHandler {
    private static final int COOK_TIME = 100;
    private boolean isSmoldering;
    private boolean isCooked;
    private int cookCounter;

    @Override
    public void updateEntity() {
        Block blockAbove = Block.blocksList[worldObj.getBlockId(xCoord,yCoord + 1,zCoord)];

        if (blockAbove != null && isSmoldering()) {
            if (cookCounter < COOK_TIME) cookCounter++;

            if (cookCounter >= COOK_TIME) {
                worldObj.setBlockMetadataWithNotify(xCoord,yCoord,zCoord, 0);
                setCooked(true);
                setSmoldering(false);
                cookCounter = 0;
            }
        }
        else {
            cookCounter = 0;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        if ( tag.hasKey( "cookCounter" ) )
        {
            cookCounter = tag.getInteger("cookCounter");
        }

        if ( tag.hasKey( "isSmoldering" ) )
        {
            isSmoldering = tag.getBoolean("isSmoldering");
        }

        if ( tag.hasKey( "isCooked" ) )
        {
            isCooked = tag.getBoolean("isCooked");
        }

        worldObj.markBlockForRenderUpdate(xCoord,yCoord,zCoord);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {

        tag.setInteger("cookCounter", cookCounter);
        tag.setBoolean("isSmoldering", isSmoldering);
        tag.setBoolean("isCooked", isCooked);

        worldObj.markBlockForRenderUpdate(xCoord,yCoord,zCoord);
    }

    @Override
    public void readNBTFromPacket(NBTTagCompound nbttagcompound) {
        this.readFromNBT(nbttagcompound);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tag = new NBTTagCompound();

        this.writeToNBT(tag);

        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, tag );
    }

    public boolean isCooked() {
        return isCooked;
    }

    public void setCooked(boolean boo) {
        this.isCooked = boo;
        worldObj.markBlockForRenderUpdate(xCoord,yCoord,zCoord);
    }

    public boolean isSmoldering() {
        return isSmoldering;
    }

    public void setSmoldering(boolean boo) {
        this.isSmoldering = boo;
        worldObj.markBlockForRenderUpdate(xCoord,yCoord,zCoord);
    }


}
