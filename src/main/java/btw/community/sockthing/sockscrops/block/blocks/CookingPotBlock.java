package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.BTWBlocks;
import btw.block.tileentity.CampfireTileEntity;
import btw.client.fx.BTWEffectManager;
import btw.client.render.util.RenderUtils;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.tileentities.CookingPotTileEntity;
import btw.community.sockthing.sockscrops.recipes.CookingPotRecipeManager;
import btw.community.sockthing.sockscrops.utils.CookingPotUtils;
import btw.inventory.util.InventoryUtils;
import btw.item.util.ItemUtils;
import net.minecraft.src.*;

import java.util.Random;

public class CookingPotBlock extends BlockContainer {
    public CookingPotBlock(int blockID, String name) {
        super(blockID, BTWBlocks.miscMaterial);
        setUnlocalizedName(name);
        setCreativeTab(CreativeTabs.tabDecorations);
        setHardness(0.05F);
    }

    @Override
    public TileEntity createNewTileEntity(World var1) {
        return new CookingPotTileEntity();
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return 0;
    }

    @Override
    public boolean onBlockActivated( World world, int x, int y, int z, EntityPlayer player, int facing, float xClick, float yClick, float zClick )
    {
        CookingPotTileEntity pot = (CookingPotTileEntity)world.getBlockTileEntity( x, y, z );

        ItemStack[] cookStack = pot.getCookStacks().toArray(new ItemStack[0]);

        ItemStack heldStack = player.getCurrentEquippedItem();

        boolean emptyHand = heldStack == null;

        if (!pot.getLidOpen()) {
            return openOrCloseLid(pot, true, world, x, y, z);
        } else { //open lid
            if (emptyHand) {
                if (player.isSneaking()){
                    return openOrCloseLid(pot, false, world, x, y, z);
                }

                if (!pot.isFoodCooked()){
                    if (removeItem(world, x, y, z, player, pot, cookStack)) return true;
                }
            } else { //holding something
                if (isValidLiquidContainer(heldStack)){
                    if (!pot.isFoodCooked() && pot.getLiquidStack() == null) {
                        return addLiquidAndReturnContainer(world, x, y, z, player, pot, heldStack);
                    }
                }
                else {
                    if (pot.isFoodCooked()){
                        if (heldStack.itemID == Item.bowlEmpty.itemID) {
                            if (takeServing(world, x, y, z, player, pot, cookStack, heldStack)) return true;
                        }
                    }

                    if (!pot.isFoodCooked() && pot.getLiquidStack() != null) {
                        if (heldStack.itemID == Item.bucketEmpty.itemID){
                            return removeLiquidAndReturnItem(world, x, y, z, player, pot, heldStack);
                        }
                    }

                    if (heldStack.getItem() instanceof Item) {
                        if (addItem(world, x, y, z, player, pot, heldStack)) return true;
                    }
                }
            }
        }

        return false;
    }

    private static boolean addItem(World world, int i, int j, int k, EntityPlayer player, CookingPotTileEntity pot, ItemStack heldStack) {
        // Find first occupied stack in the pot (null if empty)
        ItemStack firstStack = pot.getCookStack(0);

        int slot = InventoryUtils.getFirstEmptyStackInSlotRange(pot, 0, 5);

        // No empty slot available
        if (slot == -1) return false;

        // If the pot has cooked food, only allow the same item to be added and not more than 4
        if (pot.isFoodCooked() && !firstStack.isItemEqual(heldStack) && slot > 4) return false;

        // If the pot doesn't have cookedFood, don't allow cooked food to be added
        if (firstStack != null && !pot.isFoodCooked() && CookingPotRecipeManager.isResult(heldStack) ) return false;

        // Add item to the pot
        pot.setCookStack(slot, new ItemStack(heldStack.itemID, 1, heldStack.getItemDamage()));
        heldStack.stackSize--;

        // Play effect on server side
        if (!world.isRemote) {
            world.playAuxSFX(BTWEffectManager.ITEM_COLLECTION_POP_EFFECT_ID, i, j, k, 0);
        }

        // Give empty bowl if the item can be cooked
        if (CookingPotRecipeManager.isResult(heldStack)) {
            ItemUtils.givePlayerStackOrEject(player, new ItemStack(Item.bowlEmpty));
        }

        return true;
    }
    private static boolean takeServing(World world, int i, int j, int k, EntityPlayer player, CookingPotTileEntity pot, ItemStack[] cookStack, ItemStack heldStack) {
        int slot = InventoryUtils.getFirstOccupiedStack(pot);
        if (slot != -1) {
            ItemUtils.givePlayerStackOrEject(player, cookStack[slot], i, j, k);
            pot.setCookStack(slot,null);

            if (world.isRemote) {
                world.playAuxSFX(BTWEffectManager.ITEM_COLLECTION_POP_EFFECT_ID, i, j, k, 0);
            }
            heldStack.stackSize--;
            return true;
        }
        return false;
    }

    private static boolean removeLiquidAndReturnItem(World world, int i, int j, int k, EntityPlayer player, CookingPotTileEntity pot, ItemStack heldStack) {
        if (CookingPotTileEntity.convertLiquidToItemStack(pot.getLiquidStack()) == null) return false;

        ItemUtils.givePlayerStackOrEject(player, CookingPotTileEntity.convertLiquidToItemStack(pot.getLiquidStack()));
        pot.setLiquidStack(null);
        heldStack.stackSize--;
        world.markBlockForUpdate(i, j, k);
        return true;
    }

    private static boolean addLiquidAndReturnContainer(World world, int i, int j, int k, EntityPlayer player, CookingPotTileEntity pot, ItemStack heldStack) {
        if (pot.getLiquidStack() != null) return false;

        pot.setLiquidStackAndConvert(new ItemStack(heldStack.itemID, heldStack.stackSize, heldStack.getItemDamage()));
        heldStack.stackSize--;

        if (world.isRemote) {
            world.playAuxSFX(BTWEffectManager.ITEM_COLLECTION_POP_EFFECT_ID, i, j, k, 0);
        }

        ItemUtils.givePlayerStackOrEject(player, new ItemStack(Item.bucketEmpty));

//                        world.markBlockForUpdate(i, j, k);
        return true;
    }

    private static boolean removeItem(World world, int i, int j, int k, EntityPlayer player, CookingPotTileEntity pot, ItemStack[] cookStack) {
        int slot = CookingPotUtils.getLastOccupiedStack(pot);
        if (slot != -1) {
            ItemUtils.givePlayerStackOrEject(player, cookStack[slot], i, j, k);
            pot.setCookStack(slot,null);

            if (world.isRemote) {
                world.playAuxSFX(BTWEffectManager.ITEM_COLLECTION_POP_EFFECT_ID, i, j, k, 0);
            }
            return true;
        }
        return false;
    }

    private static boolean openOrCloseLid(CookingPotTileEntity pot, boolean setOpen, World world, int i, int j, int k) {
        pot.setLidOpen(setOpen);
        world.markBlockForUpdate(i, j, k);
        return true;
    }

    public boolean isValidCookItem( ItemStack stack )
    {
        return CookingPotRecipeManager.isValidIngredients(stack);
    }

    public boolean isValidLiquidContainer( ItemStack stack )
    {
        return CookingPotTileEntity.convertItemStackToLiquid(stack) > 0;
    }

    @Override
    public void onBlockHarvested(World world, int i, int j, int k, int par5, EntityPlayer player) {

        CookingPotTileEntity pot = (CookingPotTileEntity) world.getBlockTileEntity(i, j, k);
        int fillHeight = 0;
        if (pot.getLiquidStack() != null){
            fillHeight = pot.getLiquidStack().stackSize * 2;
        }
        else {
            if (pot.isFoodCooked()){
                fillHeight = pot.getCookStacks().size();
            }
        }

        int packedItemDamage = CookingPotUtils.packItemData(false, fillHeight, pot.getCookType());
        ItemStack newStack = new ItemStack(SCBlocks.cookingPot.blockID, 1, packedItemDamage);

        if (pot != null ){
            NBTTagCompound root = newStack.hasTagCompound()
                    ? newStack.getTagCompound()
                    : new NBTTagCompound();

            if (pot.isFoodCooked()) {
                if (pot.isFoodCooked() && !pot.getCookStacks().isEmpty()) {
                    NBTTagList tagList = new NBTTagList();

                    for (int slot = 0; slot < pot.getCookStacks().size(); slot++) {
                        ItemStack stack = pot.getCookStacks().get(slot);
                        if (stack != null) {
                            NBTTagCompound itemTag = new NBTTagCompound();
                            itemTag.setByte("Slot", (byte) slot);
                            stack.writeToNBT(itemTag);
                            tagList.appendTag(itemTag);
                        }
                    }

                    root.setTag("Items", tagList);
                }
            }
            else {
                for (int tempSlot = 0; tempSlot < pot.getCookStacks().size(); tempSlot++) {
                    if (!world.isRemote && pot.getCookStack(tempSlot) != null) {
                        ItemUtils.ejectStackAroundBlock(world, i,j, k, pot.getCookStack(tempSlot).copy());
                    }
                }
            }


            if (pot.getLiquidStack() != null) {
                ItemStack stack = pot.getLiquidStack();
                NBTTagCompound liquidTag = new NBTTagCompound();
                stack.writeToNBT(liquidTag);
                root.setTag("liquidStack", liquidTag);
            }

            newStack.setTagCompound(root);
        }

        // Now drop the newStack like normal
        dropBlockAsItem_do(world, i, j, k, newStack);
    }

    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        int var6 = par1World.getBlockId(par2, par3 + 1, par4);
        TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);

        if (var6 == 0 || blocksList[var6].blockMaterial.isReplaceable())
        {
            return true;
        }
        if (te != null && te instanceof CampfireTileEntity)
        {
            return true;
        }
        else return false;
    }

    @Override
    public boolean rotateAroundJAxis( World world, int i, int j, int k, boolean bReverse )
    {
        TileEntity tileEnt = world.getBlockTileEntity( i, j, k );

        if ( tileEnt != null && tileEnt instanceof CookingPotTileEntity)
        {
            CookingPotTileEntity skullEnt = (CookingPotTileEntity)tileEnt;

            int iSkullFacing = skullEnt.GetSkullRotationServerSafe();

            if ( bReverse )
            {
                iSkullFacing += 2;

                if ( iSkullFacing > 7 )
                {
                    iSkullFacing -= 8;
                }
            }
            else
            {
                iSkullFacing -= 2;

                if ( iSkullFacing < 0 )
                {
                    iSkullFacing += 8;
                }
            }

            skullEnt.setSkullRotation( iSkullFacing );

            world.markBlockForUpdate( i, j, k );

            return true;
        }

        return false;
    }

    /**
     * The type of render function that is called for this block
     */
//    public int getRenderType()
//    {
//        return -1;
//    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
    public void setBlockBoundsBasedOnState( IBlockAccess blockAccess, int i, int j, int k )
    {
        // override to deprecate parent
    }

    @Override
    public AxisAlignedBB getBlockBoundsFromPoolBasedOnState( IBlockAccess blockAccess, int i, int j, int k )
    {
        int meta = blockAccess.getBlockMetadata(i, j, k);

        switch ( meta )
        {
            case 2:

                return AxisAlignedBB.getAABBPool().getAABB(
                        3/16D, 0D - 8/16D, 3/16D, 1D - 3/16D, 0D - 8/16D + 8/16D, 1D - 3/16D );

            default:

                return AxisAlignedBB.getAABBPool().getAABB(
                        3/16D, 0D, 3/16D, 1D - 3/16D, 8/16D, 1D - 3/16D );
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int i, int j, int k )
    {
        return getBlockBoundsFromPoolBasedOnState( world, i, j, k ).offset( i, j, k );
    }

    @Override
    public boolean isBlockRestingOnThatBelow( IBlockAccess blockAccess, int i, int j, int k )
    {
        int iMetadata = blockAccess.getBlockMetadata( i, j, k );

        return iMetadata == 1 || iMetadata == 2;
    }

    @Override
    public boolean canRotateOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
    {
        return true;
    }


    //----------- Client Side Functionality -----------//

    private Icon charredClayIcon;
    private Icon charredClaySideIcon;
    private Icon waterIcon;
    private Icon milkIcon;
    private Icon chocolateMilkIcon;

    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        blockIcon = par1IconRegister.registerIcon("pottery_clay_dry");
        charredClayIcon = par1IconRegister.registerIcon("pottery_clay_dry_charred");
        charredClaySideIcon = par1IconRegister.registerIcon("pottery_clay_dry_charred_side");

        waterIcon = par1IconRegister.registerIcon("water");
        milkIcon = par1IconRegister.registerIcon("fcBlockMilk");
        chocolateMilkIcon = par1IconRegister.registerIcon("fcBlockMilkChocolate");
    }

    @Override
    public Icon getBlockTexture(IBlockAccess blockAccess, int i, int j, int k, int side) {
        CookingPotTileEntity pan = (CookingPotTileEntity)blockAccess.getBlockTileEntity( i, j, k );

        if (pan != null && pan.isFoodCooked()){
            if (side == 0) return charredClayIcon;
            if (side > 1) return charredClaySideIcon;
        }

        return blockIcon;
    }

    @Override
    public boolean renderBlock( RenderBlocks renderBlocks, int i, int j, int k )
    {
        return false;
    }

    @Override
    public void renderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness) {
        Icon sideIcon = blockIcon;
        Icon bottomIcon = blockIcon;
        int fillType = CookingPotUtils.unpackFillType(iItemDamage);
        if (fillType > 5) {
            sideIcon = charredClaySideIcon;
            bottomIcon = charredClayIcon;
        }
        //Base
        renderer.setRenderBounds(
                4/16D,0,4/16D,
                1D - 4/16D, 2/16D, 1D - 4/16D
        );
        RenderUtils.renderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, bottomIcon);

        //contents
//        if (iItemDamage > 0){
//            double height = CookingPotUtils.unpackFillHeight(iItemDamage)/16D;
//            int fillType = CookingPotUtils.unpackFillType(iItemDamage);
//            Icon contentsIcon = getContentsIconFromFillType(fillType);
//
//            renderer.setRenderBounds(
//                    4/16D, height,4/16D,
//                    1D - 4/16D, height, 1D - 4/16D
//            );
//            RenderUtils.renderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, contentsIcon);
//        }

        //Lid
        renderer.setRenderBounds(
                4/16D,7/16D,4/16D,
                1D - 4/16D, 8/16D, 1D - 4/16D
        );
        RenderUtils.renderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, blockIcon);

        renderer.setRenderBounds(
                7/16D,8/16D,7/16D,
                1D - 7/16D, 9/16D, 1D - 7/16D
        );
        RenderUtils.renderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, blockIcon);


        //walls
        renderer.setRenderBounds(
                3/16D,1/16D,4/16D,
                5/16D, 7/16D, 12/16D
        );
        RenderUtils.renderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, sideIcon);

        renderer.setRenderBounds(
                11/16D,1/16D,4/16D,
                13/16D, 7/16D, 12/16D
        );
        RenderUtils.renderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, sideIcon);

        renderer.setRenderBounds(
                4/16D,1/16D,3/16D,
                12/16D, 7/16D, 5/16D
        );
        RenderUtils.renderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, sideIcon);

        renderer.setRenderBounds(
                4/16D,1/16D,11/16D,
                12/16D, 7/16D, 13/16D
        );
        RenderUtils.renderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, sideIcon);

        //handles
        renderer.setRenderBounds(
                2/16D,5/16D,6/16D,
                3/16D, 6/16D, 10/16D
        );
        RenderUtils.renderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, blockIcon);

        renderer.setRenderBounds(
                13/16D,5/16D,6/16D,
                14/16D, 6/16D, 10/16D
        );
        RenderUtils.renderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, blockIcon);
    }

    private Icon getContentsIconFromFillType(int fillType) {
        if (fillType > 0) {
            if (fillType == CookingPotTileEntity.WATER) return waterIcon;
            if (fillType == CookingPotTileEntity.MILK) return milkIcon;
            if (fillType == CookingPotTileEntity.CHOCOLATE_MILK) return chocolateMilkIcon;
        }

        return Block.gravel.blockIcon;
    }

    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random rand)
    {
        CookingPotTileEntity pan = (CookingPotTileEntity)world.getBlockTileEntity( i, j, k );

        if ( pan.isFoodBurning() )
        {
            for ( int iTempCount = 0; iTempCount < 1; ++iTempCount )
            {
                double xPos = i + 0.375F + rand.nextFloat() * 0.25F;
                double yPos = j - 0.5F + rand.nextFloat() * 0.5F;
                double zPos = k + 0.375F + rand.nextFloat() * 0.25F;

                world.spawnParticle( "largesmoke", xPos, yPos, zPos, 0D, 0D, 0D );

//    	        if ( rand.nextInt(2) == 0 )
//    	        {
//    	        	float volume = 0.75F + rand.nextFloat();
//
//    	        	float pitch = rand.nextFloat() * 0.5F + 0.5F;
//
//    	            playSound(world, i, j, k, rand, volume, pitch, "fire.fire");
//    	        }
            }
        }
        else if ( pan.isFoodCooking() )
        {
            for ( int iTempCount = 0; iTempCount < 2; ++iTempCount )
            {
                double xPos = i + 0.375F + rand.nextFloat() * 0.25F;
                double yPos = j - 0.5F + rand.nextFloat() * 0.5F;
                double zPos = k + 0.375F + rand.nextFloat() * 0.25F;

                if (rand.nextInt(2) == 0) {
                    world.spawnParticle( "fcwhitesmoke", xPos, yPos, zPos, 0D, 0D, 0D );
                }

            }
        }
//        else if (pan.isFoodCooked() && pan.hasLid())
//        {
//            if ( rand.nextInt(1) == 0 )
//            {
//                float volume = 0.05F;
//                float pitch = rand.nextFloat() * 0.05F - 2F;
//
//                playSound(world, i, j, k, rand, volume, pitch, "random.anvil_use");
//            }
//
//        }
        else if (pan.isFoodCooking() || pan.isFoodCooked())
        {
            if ( rand.nextInt(2) == 0 )
            {
                float volume = 0.25F * rand.nextFloat();

                float pitch = rand.nextFloat() * 0.25F - 1F;

                playSound(world, i, j, k, rand, volume, pitch, "liquid.lavapop");
            }
        }
    }

    private void playSound(World world, int i, int j, int k, Random rand, float volume, float pitch, String sound)
    {
        world.playSound( i + 0.5D, j - 6/16D, k + 0.5D, sound,
                volume, pitch, false );

    }
}
