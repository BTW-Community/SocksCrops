package btw.community.sockthing.sockscrops.block.tileentities;

import btw.block.BTWBlocks;
import btw.block.tileentity.TileEntityDataPacketHandler;
import btw.client.fx.BTWEffectManager;
import btw.community.sockthing.sockscrops.SocksCropsAddon;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.item.SCItems;
import btw.item.BTWItems;
import btw.item.util.ItemUtils;
import deco.block.DecoBlockIDs;
import deco.block.DecoBlocks;
import net.minecraft.src.*;

import java.util.HashSet;
import java.util.Set;

public class FlowerPotTileEntity extends TileEntity implements TileEntityDataPacketHandler {
    private boolean hasItem = false;
    public static Set<Integer> validItemList = new HashSet<Integer>();

    private int currentBlockID;
    private int currentBlockMetadata;

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("StoredID", this.currentBlockID);
        nbtTagCompound.setInteger("StoredMetadata", this.currentBlockMetadata);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        this.currentBlockID = nbtTagCompound.getInteger("StoredID");
        this.currentBlockMetadata = nbtTagCompound.getInteger("StoredMetadata");
    }

    /**
     * Overriden in a sign to provide the text.
     */
    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setInteger("StoredID", this.currentBlockID);
        nbtTagCompound.setInteger("StoredMetadata", this.currentBlockMetadata);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
    }

    @Override
    public void readNBTFromPacket(NBTTagCompound nbtTagCompound) {
        this.currentBlockID = nbtTagCompound.getInteger("StoredID");
        this.currentBlockMetadata = nbtTagCompound.getInteger("StoredMetadata");
        if (currentBlockID != 0) hasItem = true;
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
    }

    public int getStoredBlockID() {
        return currentBlockID;
    }

    public int getStoredBlockMetadata() {
        return currentBlockMetadata;
    }

    public boolean hasItem() {
        return hasItem;
    }

    public boolean isValidItemForPot(int id, int meta) {
        //Special case for blood wood sapling
        if (id == BTWBlocks.aestheticVegetation.blockID)
            return meta == 2;
        if (id == SCItems.blueberryRoots.itemID || id == SCItems.sweetberryRoots.itemID) return true;
        return validItemList.contains(id);
    }

    public void placeItemInPot(int itemID, int metadata) {
        hasItem = true;

        currentBlockID = itemID;
        currentBlockMetadata = metadata;

        if (itemID == BTWItems.brownMushroom.itemID)
            currentBlockID = Block.mushroomBrown.blockID;
        else if (itemID == BTWItems.redMushroom.itemID)
            currentBlockID = Block.mushroomRed.blockID;
        else if (itemID == SCItems.blueberryRoots.itemID){
            currentBlockID = SCBlocks.blueberryBush.blockID;
            currentBlockMetadata = 5;
        }
        else if (itemID == SCItems.sweetberryRoots.itemID){
            currentBlockID = SCBlocks.sweetberryBush.blockID;
            currentBlockMetadata = 5;
        }

        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);

        this.worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "step.grass", 1.0F, 1.0F);
    }

    //Removes item and gives it to the player
    public void retrieveItemFromPot(EntityPlayer player, int side) {
        if (currentBlockID == 0)
            return;
        else if (currentBlockID == SCBlocks.blueberryBush.blockID){
            currentBlockID = SCItems.blueberryRoots.itemID;
            currentBlockMetadata = 0;
        }
        else if (currentBlockID == SCBlocks.sweetberryBush.blockID){
            currentBlockID = SCItems.sweetberryRoots.itemID;
            currentBlockMetadata = 0;
        }

        if (!worldObj.isRemote) ItemUtils.ejectStackFromBlockTowardsFacing(worldObj, xCoord, yCoord, zCoord, new ItemStack(Item.itemsList[currentBlockID], 1, currentBlockMetadata), side);
//       if (!player.capabilities.isCreativeMode) ItemUtils.givePlayerStackOrEjectFavorEmptyHand(player, new ItemStack(Item.itemsList[currentBlockID], 1, currentBlockMetadata), this.xCoord, this.yCoord, this.zCoord);


        hasItem = false;
        currentBlockID = 0;

        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);

        if (!this.worldObj.isRemote)
            this.worldObj.playAuxSFX(BTWEffectManager.ITEM_COLLECTION_POP_EFFECT_ID, this.xCoord, this.yCoord, this.zCoord, 0);
    }

    static {
        validItemList.add(Block.plantRed.blockID);
        validItemList.add(Block.plantYellow.blockID);
        validItemList.add(Block.mushroomBrown.blockID);
        validItemList.add(Block.mushroomRed.blockID);
        validItemList.add(Block.cactus.blockID);
        validItemList.add(Block.sapling.blockID);
        validItemList.add(Block.tallGrass.blockID);
        validItemList.add(BTWBlocks.aestheticVegetation.blockID);
        validItemList.add(BTWBlocks.oakSapling.blockID);
        validItemList.add(BTWBlocks.spruceSapling.blockID);
        validItemList.add(BTWBlocks.birchSapling.blockID);
        validItemList.add(BTWBlocks.jungleSapling.blockID);
        validItemList.add(Block.deadBush.blockID);

        validItemList.add(SCBlocks.shortGrass.blockID);
//        validItemList.add(SCBlocks.doubleTallPlant.blockID);
//        validItemList.add(SCBlocks.doubleTallPlant.blockID);
//        validItemList.add(SCItems.sunflower.itemID, 0);
        validItemList.add(SCBlocks.blueberryBush.blockID);
        validItemList.add(SCBlocks.sweetberryBush.blockID);

        if (SocksCropsAddon.isDecoInstalled()){
            validItemList.add(DecoBlockIDs.FLOWER_ID);
            validItemList.add(DecoBlockIDs.FLOWER_2_ID);
            validItemList.add(DecoBlockIDs.TULIP_ID);
            validItemList.add(DecoBlockIDs.LEGACY_AUTUMN_SAPLING_ID);
            validItemList.add(DecoBlockIDs.LEGACY_CHERRY_SAPLING_ID);
            validItemList.add(DecoBlockIDs.LEGACY_ACACIA_SAPLING_ID);
            validItemList.add(DecoBlockIDs.LEGACY_MAHOGANY_SAPLING_ID);
            validItemList.add(DecoBlockIDs.LEGACY_MANGROVE_SAPLING_ID);
            validItemList.add(DecoBlockIDs.LEGACY_HAZEL_SAPLING_ID);
            validItemList.add(DecoBlockIDs.LEGACY_FIR_SAPLING_ID);

            validItemList.add(DecoBlockIDs.RED_AUTUMN_SAPLING_ID);
            validItemList.add(DecoBlockIDs.ORANGE_AUTUMN_SAPLING_ID);
            validItemList.add(DecoBlockIDs.YELLOW_AUTUMN_SAPLING_ID);
            validItemList.add(DecoBlockIDs.ACACIA_SAPLING_ID);
            validItemList.add(DecoBlockIDs.CHERRY_SAPLING_ID);
            validItemList.add(DecoBlockIDs.MAHOGANY_SAPLING_ID);
            validItemList.add(DecoBlockIDs.MANGROVE_SAPLING_ID);
            validItemList.add(DecoBlockIDs.HAZEL_SAPLING_ID);
            validItemList.add(DecoBlockIDs.FIR_SAPLING_ID);
            validItemList.add(DecoBlockIDs.REDWOOD_SAPLING_ID);
            validItemList.add(DecoBlockIDs.DARK_OAK_SAPLING_ID);
            validItemList.add(DecoBlockIDs.WILLOW_SAPLING_ID);
            validItemList.add(DecoBlockIDs.ASPEN_SAPLING_ID);
        }

    }
}