package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.blocks.*;
import btw.block.model.BlockModel;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.models.LargeFlowerpotModel;
import btw.community.sockthing.sockscrops.block.tileentities.LargeFlowerPotTileEntity;
import btw.community.sockthing.sockscrops.item.SCItems;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import btw.item.BTWItems;
import btw.item.util.ItemUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.Random;

public class LargeFlowerpotBlock extends BlockContainer {

    protected static final LargeFlowerpotModel model = new LargeFlowerpotModel();
    protected static final double HEIGHT = (6 / 16D );
    protected static final double WIDTH = (6 / 16D );
    protected static final double HALF_WIDTH = (WIDTH / 2D );
    protected static final double DEPTH = (6 / 2D );

    public LargeFlowerpotBlock(int blockID, String name) {
        super(blockID, Material.circuits);

        setUnlocalizedName(name);
    }

    @Override
    public TileEntity createNewTileEntity(World var1) {
        return new LargeFlowerPotTileEntity();
    }

    public int idDropped(int par1, Random par2Random, int par3)
    {
        return SCItems.largeFlowerpot.itemID;
    }

    @Override
    public int idPicked(World par1World, int par2, int par3, int par4) {
        return SCItems.largeFlowerpot.itemID;
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int side, float clickX, float clickY, float clickZ )
    {
        ItemStack heldStack = player.inventory.getCurrentItem();
        TileEntity tileEntity = world.getBlockTileEntity(i,j,k);
        LargeFlowerPotTileEntity potTile = (LargeFlowerPotTileEntity) tileEntity;

        int slot = getTargetedSlot(world, i, j, k, clickX, clickZ);

        if (heldStack != null){
            if (potTile.getStackInSlot(slot) == null && potTile.isValidStack(heldStack)){
                ItemStack stackToPlace = heldStack.copy();
                stackToPlace.stackSize = 1;

                potTile.setStackInSlot(heldStack, slot);

                heldStack.stackSize--;
                return true;
            }
        }
        else {

            if (potTile.getStackInSlot(slot) != null)
            {
                if (!world.isRemote) ItemUtils.ejectStackFromBlockTowardsFacing(world, i, j, k, potTile.getStackInSlot(slot), side);
                potTile.setStackInSlot(null, slot);
                return true;
            }
        }

        return false;
    }

    private int getTargetedSlot(World world, int i, int j, int k, float clickX, float clickZ) {
        int meta = world.getBlockMetadata(i, j, k);

        if ( isRotated(meta) ) {
            if (clickZ < 6/16F){
                return 0;
            }
            else if (clickZ > 10/16F){
                return 2;
            }
            else {
                return 1;
            }
        }
        else {
            if (clickX < 6/16F){
                return 0;
            }
            else if (clickX > 10/16F){
                return 2;
            }
            else {
                return 1;
            }
        }
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int facing, float clickX, float clickY, float clickZ, int metadata) {
        if (facing > 1)  return facing;
        return 0;
    }

    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving player, ItemStack heldStack){
        int rotation = ((MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3)) % 2;
        int metadata = world.getBlockMetadata(i,j,k);
        if (metadata < 2)
        {
            world.setBlockMetadata(i,j,k, rotation);
        }

//        System.out.println("meta: " + world.getBlockMetadata(i,j,k));
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    private boolean isValidBlock(World world, int x, int y, int z)
    {
        Block blockBelow = Block.blocksList[world.getBlockId(x,y,z)];
        if (blockBelow != null)
        {
            if (blockBelow instanceof MouldingBlock )
            {
                int metaBelow = world.getBlockMetadata(x,y,z);
                if (metaBelow >=8 && metaBelow <= 11) return true;
                else return false;
            }
            else if (blockBelow instanceof SidingAndCornerBlock)
            {
                int metaBelow = world.getBlockMetadata(x,y,z);
                if (metaBelow == 4 || metaBelow == 6 || metaBelow == 8 || metaBelow == 10) return true;
                else return false;
            }
            else if (blockBelow instanceof StairsBlock)
            {
                int metaBelow = world.getBlockMetadata(x,y,z);
                if (metaBelow >= 0 && metaBelow <= 3) return true;
                else return false;
            }
        }


        return false;
    }

    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return super.canPlaceBlockAt(par1World, par2, par3, par4) && (isValidBlock(par1World, par2, par3 - 1, par4) || par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4));
    }


    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
        dropContents(par1World, par2, par3, par4);
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !isValidBlock(par1World, par2, par3 - 1, par4))
        {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);

            dropContents(par1World, par2, par3, par4);

            par1World.setBlockToAir(par2, par3, par4);
        }
    }

    private void dropContents(World world, int i, int j, int k) {
        TileEntity tileEntity = world.getBlockTileEntity(i,j,k);
        LargeFlowerPotTileEntity potTile = (LargeFlowerPotTileEntity) tileEntity;

        ItemStack[] contents = potTile.getContents();

        for (int slot = 0; slot < 3; slot++)
        {
            if (contents[slot] != null)
            {
                if (!world.isRemote)
                {
                    this.dropBlockAsItem_do(world, i, j, k, contents[slot]);
                }

                potTile.setStackInSlot(null, slot);
            }
        }
    }

    @Override
    public boolean canGroundCoverRestOnBlock(World world, int i, int j, int k)
    {
        return world.doesBlockHaveSolidTopSurface( i, j - 1, k );
    }

    @Override
    public float groundCoverRestingOnVisualOffset(IBlockAccess blockAccess, int i, int j, int k)
    {
        return -1F;
    }

    public boolean isRotated(int meta) {
        return meta == 1 || meta == 4 || meta == 5;
    }

    protected BlockModel rotateModel(IBlockAccess blockAccess, int i, int j, int k, BlockModel tempModel) {

        int meta = blockAccess.getBlockMetadata(i,j,k);

        if (isRotated( meta )) {
            tempModel.rotateAroundYToFacing(4);
        }

        if (meta == 2) tempModel.translate(0,0,4/16D);
        if (meta == 3) tempModel.translate(0,0,-4/16D);
        if (meta == 4) tempModel.translate(4/16D,0,0);
        if (meta == 5) tempModel.translate(-4/16D,0,0);

        return tempModel;
    }

    @Override
    public AxisAlignedBB getBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
        AxisAlignedBB tempBounds = model.bounds.makeTemporaryCopy();
        rotateBounds(blockAccess, i, j, k, tempBounds);
        return tempBounds;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        AxisAlignedBB tempBounds = model.bounds.makeTemporaryCopy();
        rotateBounds(world, i, j, k, tempBounds);
        return tempBounds;
    }

    private AxisAlignedBB rotateBounds(IBlockAccess blockAccess, int i, int j, int k, AxisAlignedBB tempBounds) {
        int meta = blockAccess.getBlockMetadata(i, j, k);

        if (isRotated( meta )) {
            tempBounds.rotateAroundYToFacing(4);
        }

        if (meta == 2) tempBounds.translate(0,0,4/16D);
        if (meta == 3) tempBounds.translate(0,0,-4/16D);
        if (meta == 4) tempBounds.translate(4/16D,0,0);
        if (meta == 5) tempBounds.translate(-4/16D,0,0);

        return tempBounds;
    }



    //----------- Client Side Functionality -----------//


    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        if (multiPass) {
            double var1 = 0.5D;
            double var3 = 1.0D;
            return ColorizerGrass.getGrassColor(var1, var3);
        } else {
            return super.colorMultiplier(par1IBlockAccess, par2, par3, par4);
        }
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        if (dirtPass)
        {
            return blockIcon = dirt;
        }
        return blockIcon = pot;
    }

    private Icon pot;
    private Icon dirt;

    private boolean multiPass;
    private boolean dirtPass;

    @Override
    public void registerIcons(IconRegister register) {
        pot = register.registerIcon("large_flowerpot");
        dirt = register.registerIcon("dirt");
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k)
    {

        BlockModel potModel = rotateModel(renderer.blockAccess, i, j, k, model.makeTemporaryCopy());
        potModel.renderAsBlock(renderer, this, i, j, k);

        dirtPass = true;
        BlockModel dirt = rotateModel(renderer.blockAccess, i, j, k, model.dirt.makeTemporaryCopy());
        dirt.renderAsBlock(renderer, this, i, j, k);
        dirtPass = false;

        return true;
    }

    @Override
    public void renderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean bFirstPassResult) {

        TileEntity tileEntity = renderer.blockAccess.getBlockTileEntity(i,j,k);
        LargeFlowerPotTileEntity potTile = (LargeFlowerPotTileEntity) tileEntity;

        ItemStack[] contents = potTile.getContents();

        for (int slot = 0; slot < 3; slot++)
        {
            if (contents[slot] != null)
            {
                renderSlotContents(renderer, i,j,k, slot, contents[slot]);
            }
        }
    }

    private void renderSlotContents(RenderBlocks renderer, int i, int j, int k, int slot, ItemStack stack) {

        if (stack != null){
            if (shouldRenderSpecial(stack)){
                renderSpecial(renderer,i,j,k,slot,stack);
            }
            else {
                renderPlant(renderer, i, j, k, slot, 0F, getBlock(stack), getMetadata(stack, false));
            }
        }
    }

    private void renderSpecial(RenderBlocks renderer, int i, int j, int k, int slot, ItemStack stack) {
        if (stack.itemID == Block.cactus.blockID)
        {
            renderCactus(renderer, i, j, k, renderer.blockAccess.getBlockMetadata(i,j,k), slot);
        }
//        else if (getIsDoubleTall(stack)){
//            renderPlant(renderer, i, j, k, slot,0F, getBlock(stack), getMetadata(stack, false));
//            renderPlant(renderer, i, j, k, slot, 12/16F, getBlock(stack), getMetadata(stack, true));
//
//            if (stack.itemID == SCItems.sunflower.itemID){
//                renderSunflowerFace(renderer, i, j, k, slot);
//            }
//
//        }
    }

    private void renderSunflowerFace(RenderBlocks renderer, int i, int j, int k, int slot) {
        float xShift = 0;
        float zShift = 0;
        int rotationAngleX = 0;
        int potMeta = renderer.blockAccess.getBlockMetadata(i, j, k);

        if (potMeta == 2) {
            zShift -= 4/16F;
            rotationAngleX = 90;
        }
        if (potMeta == 3) {
            zShift += 4/16F;
            rotationAngleX = -90;
        }
        if (potMeta == 4) {
            xShift -= 4/16F;
            rotationAngleX = 180;
        }
        if (potMeta == 5) {
            xShift += 4/16F;
            rotationAngleX = 0;
        }


        if (isRotated(potMeta)){
            if (slot == 0){
                zShift += 4/16F;
            }
            else if (slot == 2){
                zShift -= 4/16F;
            }
        }
        else {
            if (slot == 0){
                xShift += 4/16F;
            }
            else if (slot == 2){
                xShift -= 4/16F;
            }
        }

        SCRenderUtils.renderHorizonzalPaneSunflower(renderer, this, i, j, k,
                xShift + -1/16D, 1F + 1/32F, zShift,
                rotationAngleX, 0, -45,
                DoubleTallPlantBlock.getSunflowerFace(0), DoubleTallPlantBlock.getSunflowerFace(1),
                false, false, 0.0D);
    }

    private boolean shouldRenderSpecial(ItemStack stack) {
        if (getIsDoubleTall(stack)) return true;
        if (stack.itemID == Block.cactus.blockID) return true;
        return false;
    }

    private void renderCactus(RenderBlocks render, int x, int y, int z, int meta, int slot) {
        render.setRenderAllFaces(true);

        float minX = 6/16F + 1/32F;
        float maxX = 10/16F - 1/32F;

        float minY = 4/16F;
        float maxY = 16/16F;

        float minZ = 6/16F + 1/32F;
        float maxZ = 10/16F - 1/32F;

        if (slot == 0)
        {
            maxY = 14/16F;
        }
        else if (slot == 1)
        {
            maxY = 16/16F;
        }
        else if (slot == 2)
        {
            maxY = 15/16F;
        }

        double xShift = 0D;
        double zShift = 0D;


        if (meta == 0)
        {
            if (slot == 0)
            {
                xShift = -4/16D;
                zShift = 0;
            }
            else if (slot == 1)
            {
                xShift = 0;
                zShift = 0;
            }
            else if (slot == 2)
            {
                xShift = 4/16D;
                zShift = 0;
            }
        }
        else if (meta == 1)
        {
            if (slot == 0)
            {
                xShift = 0;
                zShift = -4/16D;
            }
            else if (slot == 1)
            {
                xShift = 0;
                zShift = 0;
            }
            else if (slot == 2)
            {
                xShift = 0;
                zShift = 4/16D;
            }
        }
        else if (meta == 2)
        {
            if (slot == 0)
            {
                xShift = -4/16D;
                zShift = +5/16D;
            }
            else if (slot == 1)
            {
                xShift = 0;
                zShift = +5/16D;
            }
            else if (slot == 2)
            {
                xShift = 4/16D;
                zShift = 5/16D;
            }
        }
        else if (meta == 3)
        {
            if (slot == 0)
            {
                xShift = -4/16D;
                zShift = -5/16D;
            }
            else if (slot == 1)
            {
                xShift = 0;
                zShift = -5/16D;
            }
            else if (slot == 2)
            {
                xShift = 4/16D;
                zShift = -5/16D;
            }
        }
        else if (meta == 4)
        {
            if (slot == 0)
            {
                xShift = 5/16D;
                zShift = -4/16D;
            }
            else if (slot == 1)
            {
                xShift = 5/15D;
                zShift = 0;
            }
            else if (slot == 2)
            {
                xShift = 5/16D;
                zShift = 4/16D;
            }
        }
        else if (meta == 5)
        {
            if (slot == 0)
            {
                xShift = -5/16D;
                zShift = -4/16D;
            }
            else if (slot == 1)
            {
                xShift = -5/15D;
                zShift = 0;
            }
            else if (slot == 2)
            {
                xShift = -5/16D;
                zShift = 4/16D;
            }
        }

        render.setRenderBounds(minX + xShift, minY, minZ + zShift, maxX + xShift, maxY, maxZ + zShift);
        render.renderStandardBlock(Block.cactus, x, y, z);

        render.setRenderAllFaces(false);
        render.setRenderBounds(0, 0, 0, 1, 1, 1);
    }

    private boolean getIsDoubleTall(ItemStack stack) {
        if (stack == null) return false;

//        if (stack.itemID == SCBlocks.doubleTallPlant.blockID) return true;
//        if (stack.itemID == SCItems.sunflower.itemID) return true;

        return false;
    }

    private int getMetadata(ItemStack stack, boolean isTopBlock) {

        if (stack == null) return 0;

        if (stack.itemID == Block.tallGrass.blockID) return 1;
//        else if (stack.itemID == SCBlocks.doubleTallPlant.blockID && stack.getItemDamage() == DoubleTallPlantBlock.GRASS){
//            if (isTopBlock) return DoubleTallPlantBlock.GRASS + 8;
//            else return DoubleTallPlantBlock.GRASS;
//        }
//        else if (stack.itemID == SCBlocks.doubleTallPlant.blockID && stack.getItemDamage() == DoubleTallPlantBlock.FERN){
//            if (isTopBlock) return DoubleTallPlantBlock.FERN + 8;
//            else return DoubleTallPlantBlock.FERN;
//        }
//        else if (stack.itemID == SCItems.sunflower.itemID) {
//            if (isTopBlock) return DoubleTallPlantBlock.SUNFLOWER + 8;
//            else return DoubleTallPlantBlock.SUNFLOWER;
//        }
        else if (stack.itemID == SCItems.blueberryRoots.itemID) return 5;
        else if (stack.itemID == SCItems.sweetberryRoots.itemID) return 5;

        return stack.getItemDamage();
    }

    private Block getBlock(ItemStack stack) {
        if (stack.itemID == BTWItems.brownMushroom.itemID) return Block.mushroomBrown;
        else if (stack.itemID == BTWItems.redMushroom.itemID) return Block.mushroomRed;
        else if (stack.itemID == SCItems.sunflower.itemID) return SCBlocks.doubleTallPlant;
        else if (stack.itemID == SCItems.blueberryRoots.itemID) return SCBlocks.blueberryBush;
        else if (stack.itemID == SCItems.sweetberryRoots.itemID) return SCBlocks.sweetberryBush;
        else {
            Block block = Block.blocksList[stack.itemID];
            if (block != null) return block;
        }


        return null;
    }

    private void renderPlant(RenderBlocks renderer, int i, int j, int k, int slot, float yShift, Block block, int metadata) {

        float xShift = 0;
        float zShift = 0;

        int potMeta = renderer.blockAccess.getBlockMetadata(i,j,k);

        if (potMeta == 2) zShift += 4/16F;
        if (potMeta == 3) zShift -= 4/16F;
        if (potMeta == 4) xShift += 4/16F;
        if (potMeta == 5) xShift -= 4/16F;


        if (isRotated(potMeta)){
            if (slot == 0){
                zShift -= 4/16F;
            }
            else if (slot == 2){
                zShift += 4/16F;
            }
        }
        else {
            if (slot == 0){
                xShift -= 4/16F;
            }
            else if (slot == 2){
                xShift += 4/16F;
            }
        }

//        renderer.setOverrideBlockTexture(icon);
        SCRenderUtils.renderCrossedSquares(renderer, block, metadata, i, j, k,
                xShift, 5/16F + yShift, zShift,
                0.75F,
                false);
//        renderer.clearOverrideBlockTexture();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldSideBeRendered( IBlockAccess blockAccess, int i, int j, int k, int iSide )
    {
        if ( iSide == 0 )
        {
            return !blockAccess.isBlockOpaqueCube(i, j, k);
        }

        return true;
    }

    @Override
    public void renderBlockAsItem(RenderBlocks renderBlocks, int iItemDamage, float fBrightness) {
        model.renderAsItemBlock(renderBlocks, this, iItemDamage);
    }


}
