// FCMOD

package btw.community.sockthing.sockscrops.block.tileentities;

import btw.block.tileentity.TileEntityDataPacketHandler;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.blocks.HayDryingBlock;
import net.minecraft.src.*;

public class HayDryingTileEntity extends TileEntity implements TileEntityDataPacketHandler {

    private final int TIME_TO_DRY = 10 * 60 * 20;
    private final int RAIN_DRY_DECAY = 10;

    private int dryCounter = 0;

    private boolean isDrying = false;

    public HayDryingTileEntity() {
        super();
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        if (tag.hasKey("dryCounter")) {
            dryCounter = tag.getInteger("dryCounter");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setInteger("dryCounter", dryCounter);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();


        if (!worldObj.isRemote) {
            updateDrying();
        } else {
            if (isDrying) {
                if (worldObj.rand.nextInt(20) == 0) {
                    double xPos = xCoord + 0.25F + worldObj.rand.nextFloat() * 0.5F;
                    double yPos = yCoord + 0.5F + worldObj.rand.nextFloat() * 0.25F;
                    double zPos = zCoord + 0.25F + worldObj.rand.nextFloat() * 0.5F;

                    worldObj.spawnParticle("fcwhitesmoke", xPos, yPos, zPos, 0.0D, 0.0D, 0.0D);
                }
            }
        }

        //DEBUG
//        int sizeXZ=8;
//        int sizeY=8;
//
//        for ( int iTempI = xCoord - sizeXZ; iTempI <= xCoord + sizeXZ; iTempI++ )
//        {
//            for ( int iTempJ = yCoord - sizeY; iTempJ <= yCoord + sizeY; iTempJ++ )
//            {
//                for ( int iTempK = zCoord - sizeXZ; iTempK <= zCoord + sizeXZ; iTempK++ )
//                {
//                    int blockID =worldObj.getBlockId(iTempI, iTempJ, iTempK);
//                    if (blockID==0)
//                    {
//                        if(worldObj.rand.nextInt(200)==0)
//                        {
//                            worldObj.spawnParticle("reddust", iTempI+0.5D, iTempJ+0.5D, iTempK+0.5D, 0, 0, 0);
//                        }
//                    }
//
//                    else if( !worldObj.isUpdateScheduledForBlock(iTempI, iTempJ, iTempK, blockID))
//                    {
//                        worldObj.scheduleBlockUpdate(iTempI, iTempJ, iTempK, blockID, 10);
//                    }
//                }
//            }
//        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();

        tag.setBoolean("x", isDrying);

        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
    }

    //------------- TileEntityDataPacketHandler ------------//

    @Override
    public void readNBTFromPacket(NBTTagCompound tag) {
        isDrying = tag.getBoolean("x");

        // force a visual update for the block since the above variables affect it

        worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }

    //------------- Class Specific Methods ------------//

    public void updateDrying() {
        boolean newDrying;
        int blockMaxNaturalLight = worldObj.getBlockNaturalLightValueMaximum(xCoord, yCoord, zCoord);
        int blockCurrentNaturalLight = blockMaxNaturalLight - worldObj.skylightSubtracted;

        newDrying = blockCurrentNaturalLight >= 15;

        int blockAboveID = worldObj.getBlockId(xCoord, yCoord + 1, zCoord);
        Block blockAbove = Block.blocksList[blockAboveID];

        if (blockAbove != null && blockAbove.isGroundCover()) {
            newDrying = false;
        }

        if (newDrying != isDrying) {
            isDrying = newDrying;

            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }

        HayDryingBlock hayBlock = (HayDryingBlock) SCBlocks.dryingHay;

        if (isDrying) {
            dryCounter++;

            if (dryCounter >= TIME_TO_DRY) {
                hayBlock.onFinishedDrying(worldObj, xCoord, yCoord, zCoord);

                return;
            }
        } else {
            if (isRainingOnBrick(worldObj, xCoord, yCoord, zCoord)) {
                dryCounter -= RAIN_DRY_DECAY;

                if (dryCounter < 0) {
                    dryCounter = 0;
                }
            }
        }

        int displayedDryLevel = hayBlock.getDryLevel(worldObj, xCoord, yCoord, zCoord);
        int currentDryLevel = computeDryLevel();

        if (displayedDryLevel != currentDryLevel) {
            hayBlock.setDryLevel(worldObj, xCoord, yCoord, zCoord, currentDryLevel);
        }
    }

    public boolean isRainingOnBrick(World world, int x, int y, int z) {
        return world.isRaining() && world.isRainingAtPos(x, y, z);
    }

    private int computeDryLevel() {
        if (dryCounter > 0) {
            int dryLevel = (int) (((float) dryCounter / (float) TIME_TO_DRY) * 7F) + 1;

            return MathHelper.clamp_int(dryLevel, 0, 7);
        }

        return 0;
    }

    //----------- Client Side Functionality -----------//
}