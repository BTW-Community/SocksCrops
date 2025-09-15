package btw.community.sockthing.sockscrops.block.tileentities;

import btw.block.tileentity.TileEntityDataPacketHandler;
import net.minecraft.src.*;

public class BowlStackTileEntity extends TileEntity implements TileEntityDataPacketHandler {

    public int[] centerPositions;
    public int[] squarePositions;
    public int[] diamondPositions;

    public BowlStackTileEntity(){
        centerPositions = new int[1];
        squarePositions = new int[4];
        diamondPositions = new int[4];
    }

    private boolean incrementCount(int[] positions, int slot) {
        if (positions[slot] < 4) {
            positions[slot]++;
            return true;
        }

        return false;
    }

    private boolean decrementCount(int[] positions, int slot) {
        if (positions[slot] > 0) {
            positions[slot]--;
            return true;
        }
        return false;
    }

    public boolean setCenterPositions(int slot, int count) {
        for (int i = 0; i < 4; i++) {
            if (squarePositions[i] > 0 || diamondPositions[i] > 0) {
                return false; // cannot place in center if others occupied
            }
        }

        if (count > 0) return incrementCount(centerPositions, slot);
        else if (count < 0) return decrementCount(centerPositions, slot);
        //count 0 ignored
        return true;
    }

    public boolean setSquarePositions(int slot, int count) {
        // prevent square if center occupied
        if (centerPositions[0] > 0) return false;

        // prevent overlaps with diamonds
        if (slot == 0) { // top-left
            if (diamondPositions[0] > 0 || diamondPositions[1] > 0) return false;
        }
        else if (slot == 1) { // top-right
            if (diamondPositions[0] > 0 || diamondPositions[2] > 0) return false;
        }
        else if (slot == 2) { // bottom-left
            if (diamondPositions[1] > 0 || diamondPositions[3] > 0) return false;
        }
        else if (slot == 3) { // bottom-right
            if (diamondPositions[2] > 0 || diamondPositions[3] > 0) return false;
        }

        if (count > 0) return incrementCount(squarePositions, slot);
        else if (count < 0) return decrementCount(squarePositions, slot);
        return true;
    }

    public boolean setDiamondPositions(int slot, int count) {
        // prevent if center is occupied
        if (centerPositions[0] > 0) return false;

        switch (slot) {
            case 0: // N
                if (squarePositions[0] > 0 || squarePositions[1] > 0) return false; // NW, NE
                break;
            case 1: // W
                if (squarePositions[0] > 0 || squarePositions[2] > 0) return false; // NW, SW
                break;
            case 2: // E
                if (squarePositions[1] > 0 || squarePositions[3] > 0) return false; // NE, SE
                break;
            case 3: // S
                if (squarePositions[2] > 0 || squarePositions[3] > 0) return false; // SW, SE
                break;
        }

        if (count > 0) return incrementCount(diamondPositions, slot);
        else if (count < 0) return decrementCount(diamondPositions, slot);
        return true;
    }

    //------------- NBT ------------//

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);

        NBTTagList tagList0 = new NBTTagList();

        if (centerPositions[0] >= 0)
        {
            NBTTagCompound tempTag = new NBTTagCompound();

            tempTag.setByte( "Slot", (byte)0 );
            tempTag.setInteger( "Count", centerPositions[0] );

            tagList0.appendTag( tempTag );
        }

        tag.setTag( "CenterPos", tagList0 );

        NBTTagList tagList = new NBTTagList();

        for (int iTempIndex = 0; iTempIndex < squarePositions.length; iTempIndex++ )
        {
            if (squarePositions[iTempIndex] >= 0)
            {
                NBTTagCompound tempTag = new NBTTagCompound();

                tempTag.setByte( "Slot", (byte)iTempIndex );
                tempTag.setInteger( "Count", squarePositions[iTempIndex] );

                tagList.appendTag( tempTag );
            }
        }

        tag.setTag( "SquarePos", tagList );

        NBTTagList tagList2 = new NBTTagList();

        for (int iTempIndex = 0; iTempIndex < diamondPositions.length; iTempIndex++ )
        {
            if (diamondPositions[iTempIndex] >= 0)
            {
                NBTTagCompound tempTag = new NBTTagCompound();

                tempTag.setByte( "Slot", (byte)iTempIndex );
                tempTag.setInteger( "Count", diamondPositions[iTempIndex] );

                tagList2.appendTag( tempTag );
            }
        }

        tag.setTag( "DiamondPos", tagList2 );
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        NBTTagList tagList = tag.getTagList( "CenterPos" );

        NBTTagCompound tempTag = (NBTTagCompound)tagList.tagAt( 0 );

        int tempSlot = tempTag.getByte( "Slot" ) & 0xff;

        if ( tempSlot >= 0 && tempSlot < centerPositions.length )
        {
            centerPositions[tempSlot] = tempTag.getInteger("Count");
        }

        NBTTagList tagList1 = tag.getTagList( "SquarePos" );

        for ( int iTempIndex = 0; iTempIndex < tagList1.tagCount(); iTempIndex++ )
        {
            tempTag = (NBTTagCompound)tagList1.tagAt( iTempIndex );

            tempSlot = tempTag.getByte( "Slot" ) & 0xff;

            if ( tempSlot >= 0 && tempSlot < squarePositions.length )
            {
                squarePositions[tempSlot] = tempTag.getInteger("Count");
            }
        }

        NBTTagList tagList2 = tag.getTagList( "DiamondPos" );

        for ( int iTempIndex = 0; iTempIndex < tagList2.tagCount(); iTempIndex++ )
        {
            tempTag = (NBTTagCompound)tagList2.tagAt( iTempIndex );

            tempSlot = tempTag.getByte( "Slot" ) & 0xff;

            if ( tempSlot >= 0 && tempSlot < diamondPositions.length )
            {
                diamondPositions[tempSlot] = tempTag.getInteger("Count");
            }
        }
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
    }


    @Override
    public void readNBTFromPacket(NBTTagCompound nbttagcompound) {
        this.readFromNBT(nbttagcompound);
    }
}
