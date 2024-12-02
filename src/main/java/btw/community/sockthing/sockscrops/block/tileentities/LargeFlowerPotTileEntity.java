package btw.community.sockthing.sockscrops.block.tileentities;

import btw.block.BTWBlocks;
import btw.block.blocks.AestheticVegetationBlock;
import btw.block.tileentity.TileEntityDataPacketHandler;
import btw.community.sockthing.sockscrops.SocksCropsAddon;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.blocks.DoubleTallPlantBlock;
import btw.community.sockthing.sockscrops.item.SCItems;
import btw.item.BTWItems;
import deco.block.DecoBlockIDs;
import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.List;

public class LargeFlowerPotTileEntity extends TileEntity implements TileEntityDataPacketHandler {

    private ItemStack[] contents = new ItemStack[3];

    public LargeFlowerPotTileEntity() {
        super();
    }

    public ItemStack[] getContents() {
        return contents;
    }

    public ItemStack getStackInSlot(int slot){
        if (slot > 2) return null;
        return contents[slot];
    }

    public void setStackInSlot(ItemStack stack, int slot){
        if (stack == null) contents[slot] = null;
        else {
            contents[slot] = new ItemStack(stack.itemID, 1, stack.getItemDamage());
        }
        worldObj.markBlockRangeForRenderUpdate(xCoord,yCoord,zCoord,xCoord,yCoord,zCoord);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {

        super.writeToNBT(tag);

        NBTTagList tagList = new NBTTagList();

        for (int iTempIndex = 0; iTempIndex < contents.length; iTempIndex++ )
        {
            if (contents[iTempIndex] != null )
            {
                NBTTagCompound tempTag = new NBTTagCompound();

                tempTag.setByte( "Slot", (byte)iTempIndex );

                contents[iTempIndex].writeToNBT(tempTag);

                tagList.appendTag( tempTag );
            }
        }

        tag.setTag( "Items", tagList );
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT( tag );

        NBTTagList tagList = tag.getTagList( "Items" );

        contents = new ItemStack[3];

        for ( int iTempIndex = 0; iTempIndex < tagList.tagCount(); iTempIndex++ )
        {
            NBTTagCompound tempTag = (NBTTagCompound)tagList.tagAt( iTempIndex );

            int tempSlot = tempTag.getByte( "Slot" ) & 0xff;

            if ( tempSlot >= 0 && tempSlot < contents.length )
            {
                contents[tempSlot] = ItemStack.loadItemStackFromNBT(tempTag);
            }
        }
    }


    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();

        this.writeToNBT(tag);

        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void readNBTFromPacket(NBTTagCompound tag) {
        this.readFromNBT(tag);
        // force a visual update for the block since the above variables affect it

        worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }

    private static ArrayList<ItemStack> validPlants = new ArrayList<>();

    public boolean isValidStack(ItemStack heldStack) {


//        return validPlants.contains(new ItemStack(heldStack.itemID, 1, heldStack.getItemDamage()));
        for(ItemStack stack : validPlants)
        {
//            System.out.println("hand ID: " + heldStack.itemID);
//            System.out.println("list ID: " + stack.itemID);
//            System.out.println("hand meta: " + heldStack.getItemDamage());
//            System.out.println("list meta: " + stack.getItemDamage());
            if (heldStack.isItemEqual(stack)) return true;
        }

        return false;
    }

    public static void addValidPlant(int itemID, int damage){
        validPlants.add(new ItemStack(itemID, 1, damage));
    }

    public static void addValidPlant(int itemID){
        addValidPlant(itemID, 0);
    }

    static {
        addValidPlant(Block.tallGrass.blockID, 1);
        addValidPlant(Block.tallGrass.blockID, 2);
        addValidPlant(Block.deadBush.blockID, 0);
        addValidPlant(BTWItems.brownMushroom.itemID, 0);
        addValidPlant(BTWItems.redMushroom.itemID, 0);
        addValidPlant(Block.plantRed.blockID, 0);
        addValidPlant(Block.plantYellow.blockID, 0);
        addValidPlant(Block.plantYellow.blockID, 1);
        addValidPlant(Block.cactus.blockID, 0);
        addValidPlant(BTWBlocks.oakSapling.blockID, 0);
        addValidPlant(BTWBlocks.oakSapling.blockID, 7);
        addValidPlant(BTWBlocks.spruceSapling.blockID, 0);
        addValidPlant(BTWBlocks.spruceSapling.blockID, 7);
        addValidPlant(BTWBlocks.birchSapling.blockID, 0);
        addValidPlant(BTWBlocks.birchSapling.blockID, 7);
        addValidPlant(BTWBlocks.jungleSapling.blockID, 0);
        addValidPlant(BTWBlocks.jungleSapling.blockID, 7);
        addValidPlant(BTWBlocks.aestheticVegetation.blockID, AestheticVegetationBlock.SUBTYPE_BLOOD_WOOD_SAPLING);

        addValidPlant(SCBlocks.shortGrass.blockID, 1);
//        addValidPlant(SCBlocks.doubleTallPlant.blockID, DoubleTallPlantBlock.GRASS);
//        addValidPlant(SCBlocks.doubleTallPlant.blockID, DoubleTallPlantBlock.FERN);
//        addValidPlant(SCItems.sunflower.itemID, 0);
        addValidPlant(SCItems.blueberryRoots.itemID, 0);
        addValidPlant(SCItems.sweetberryRoots.itemID, 0);

        if (SocksCropsAddon.isDecoInstalled()){
            addValidPlant(DecoBlockIDs.FLOWER_ID);
            addValidPlant(DecoBlockIDs.FLOWER_2_ID);
            addValidPlant(DecoBlockIDs.TULIP_ID);
            addValidPlant(DecoBlockIDs.LEGACY_AUTUMN_SAPLING_ID);
            addValidPlant(DecoBlockIDs.LEGACY_CHERRY_SAPLING_ID);
            addValidPlant(DecoBlockIDs.LEGACY_ACACIA_SAPLING_ID);
            addValidPlant(DecoBlockIDs.LEGACY_MAHOGANY_SAPLING_ID);
            addValidPlant(DecoBlockIDs.LEGACY_MANGROVE_SAPLING_ID);
            addValidPlant(DecoBlockIDs.LEGACY_HAZEL_SAPLING_ID);
            addValidPlant(DecoBlockIDs.LEGACY_FIR_SAPLING_ID);

            addValidPlant(DecoBlockIDs.RED_AUTUMN_SAPLING_ID);
            addValidPlant(DecoBlockIDs.RED_AUTUMN_SAPLING_ID, 7);
            addValidPlant(DecoBlockIDs.ORANGE_AUTUMN_SAPLING_ID);
            addValidPlant(DecoBlockIDs.ORANGE_AUTUMN_SAPLING_ID, 7);
            addValidPlant(DecoBlockIDs.YELLOW_AUTUMN_SAPLING_ID);
            addValidPlant(DecoBlockIDs.YELLOW_AUTUMN_SAPLING_ID, 7);
            addValidPlant(DecoBlockIDs.ACACIA_SAPLING_ID);
            addValidPlant(DecoBlockIDs.ACACIA_SAPLING_ID, 7);
            addValidPlant(DecoBlockIDs.CHERRY_SAPLING_ID);
            addValidPlant(DecoBlockIDs.CHERRY_SAPLING_ID, 7);
            addValidPlant(DecoBlockIDs.MAHOGANY_SAPLING_ID);
            addValidPlant(DecoBlockIDs.MAHOGANY_SAPLING_ID, 7);
            addValidPlant(DecoBlockIDs.MANGROVE_SAPLING_ID);
            addValidPlant(DecoBlockIDs.MANGROVE_SAPLING_ID, 7);
            addValidPlant(DecoBlockIDs.HAZEL_SAPLING_ID);
            addValidPlant(DecoBlockIDs.HAZEL_SAPLING_ID, 7);
            addValidPlant(DecoBlockIDs.FIR_SAPLING_ID);
            addValidPlant(DecoBlockIDs.FIR_SAPLING_ID, 7);
            addValidPlant(DecoBlockIDs.REDWOOD_SAPLING_ID);
            addValidPlant(DecoBlockIDs.REDWOOD_SAPLING_ID, 7);
            addValidPlant(DecoBlockIDs.DARK_OAK_SAPLING_ID);
            addValidPlant(DecoBlockIDs.DARK_OAK_SAPLING_ID, 7);
            addValidPlant(DecoBlockIDs.WILLOW_SAPLING_ID);
            addValidPlant(DecoBlockIDs.WILLOW_SAPLING_ID, 7);
            addValidPlant(DecoBlockIDs.ASPEN_SAPLING_ID);
            addValidPlant(DecoBlockIDs.ASPEN_SAPLING_ID, 7);
        }

    }
}
