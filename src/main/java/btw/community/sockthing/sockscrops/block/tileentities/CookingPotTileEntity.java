package btw.community.sockthing.sockscrops.block.tileentities;

import btw.block.BTWBlocks;
import btw.block.tileentity.TileEntityDataPacketHandler;
import btw.community.sockthing.sockscrops.recipes.CookingPotRecipe;
import btw.community.sockthing.sockscrops.recipes.CookingPotRecipeManager;
import btw.inventory.util.InventoryUtils;
import btw.item.BTWItems;
import btw.item.util.ItemUtils;
import net.minecraft.src.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CookingPotTileEntity extends TileEntity implements TileEntityDataPacketHandler, IInventory
{

    public float lidProgress;   // 0 = closed, 1 = fully open
    public boolean lidOpen;


    /** The pan's rotation. */
    private int potRotation;

    private ItemStack[] cookStacks = new ItemStack[6];
    private ItemStack liquidStack;

    private boolean hasLid;
    private boolean onCampfire;

    public int cookCounter = 0;

    private final int panBurnTimeMultiplier = 4; //campfireBurnTimeMultiplier = 8

    private final int timeToCook = ( TileEntityFurnace.DEFAULT_COOK_TIME *
            panBurnTimeMultiplier *
            3 / 2 ); // this line represents efficiency relative to furnace cooking

    private final int timeToBurnFood = ( timeToCook / 2 );

    private int cookBurningCounter = 0;

    public float tickCount;

    public static final int EMPTY = 0;
    public static final int SPOILED = 1;
    public static final int BURNED = 2;
    public static final int WATER = 3;
    public static final int MILK = 4;
    public static final int CHOCOLATE_MILK = 5;
    public static final int MUSHROOM_SOUP = 6;
    public static final int HEARTY_STEW = 7;
    public static final int CHICKEN_SOUP = 8;
    public static final int CHOWDER = 9;



    //------------- Cooking ------------//

    @Override
    public void updateEntity()
    {
        if (lidOpen && lidProgress < 1.0F) {
            lidProgress += 0.05F; // open speed
            if (lidProgress > 1.0F) lidProgress = 1.0F;
        }
        else if (!lidOpen && lidProgress > 0.0F) {
            lidProgress -= 0.05F; // close speed
            if (lidProgress < 0.0F) lidProgress = 0.0F;
        }


        if (isFoodCooked() && getFireLevel() >= 2)
        {
            ++this.tickCount;
        }
        else this.tickCount = 0;

        int fireLevel = getFireLevel();
        if ( fireLevel > 0 )
        {
            updateCookState();
        }

    }


    private void updateCookState()
    {
        if (canCook()) {
            cookCounter++;
            System.out.println("cook: " + cookCounter);
            if (cookCounter >= 200) { //timeToCook) {
                cookCounter = 0;
                makeSoup();
            }
        } else {
            cookCounter = 0;
        }


//        if ( cookStack != null )
//        {
//
//            int fireLevel = getFireLevel();
//
//            if ( fireLevel >= 2 )
//            {
//                SCCraftingManagerPanCookingRecipe recipe = SCCraftingManagerPanCooking.instance.getRecipe( cookStack );
//
//                if ( recipe != null )
//                {
//                    cookCounter++;
//
//                    if ( cookCounter >= timeToCook )
//                    {
//                        setCookStack( recipe.getOutput() );
//
//                        cookCounter = 0;
//
//                        // don't reset burn counter here, as the food can still burn after cooking
//                    }
//                }
//
//                if ( fireLevel >= 3 && cookStack.itemID != FCBetterThanWolves.fcItemMeatBurned.itemID )
//                {
//                    cookBurningCounter++;
//
//                    if ( cookBurningCounter >= timeToBurnFood )
//                    {
//                        setCookStack( recipe.getBurnedOutput() );
//
//                        cookCounter = 0;
//                        cookBurningCounter = 0;
//                    }
//                }
//            }
//        }
    }

    private boolean canCook() {
//        System.out.println("recipe ingredients: ");
//        for (int i = 0; i < CookingPotRecipeManager.getRecipes().get(0).getIngredients().length; i++) {
//            System.out.println("- " + CookingPotRecipeManager.getRecipes().get(0).getIngredients()[i].getDisplayName() + CookingPotRecipeManager.getRecipes().get(0).getIngredients()[i].stackSize);
//        }
//        System.out.println("- " + CookingPotRecipeManager.getRecipes().get(0).getRequiredLiquid().getItemName());
//        System.out.println("recipe result: " + CookingPotRecipeManager.getRecipes().get(0).getResult().getDisplayName() + CookingPotRecipeManager.getRecipes().get(0).getResult().stackSize);

//        if (CookingPotRecipeManager.instance.getCraftingResult(this) != null ) {
//            return true;
//        }

        CookingPotRecipe recipe = CookingPotRecipeManager.findMatchingRecipe(cookStacks, liquidStack);
        if (recipe != null) return true;

        return false;
    }

    private boolean attemptToCookRecipe(){
        if (CookingPotRecipeManager.instance.getCraftingResult(this) != null ) {
            ItemStack output = CookingPotRecipeManager.instance.consumeIngredientsAndReturnResult(this);

            assert (output != null);

            int remaining = output.stackSize;
            for (int i = 0; i < cookStacks.length && remaining > 0; i++) {
                cookStacks[i] = new ItemStack(output.itemID, 1, output.getItemDamage());
                remaining--;
            }
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            return true;
        }

        return false;
    }

    private boolean makeSoup(){
        // Check if any recipe matches this inventory
        CookingPotRecipe matchingRecipe = null;
        for (CookingPotRecipe recipe : CookingPotRecipeManager.getRecipes()) {
            if (recipe.doesInventoryContainIngredients(this)
                    && recipe.doesInventoryContainLiquidIngredients(this)) {
                matchingRecipe = recipe;
                break;
            }
        }

        if (matchingRecipe == null) return false; // no recipe matched

        // Consume ingredients
        consumeIngredients(matchingRecipe);

        // Consume liquid
        consumeLiquid(matchingRecipe);

        // Get the resulting ItemStack(s)
        ItemStack resultStack = matchingRecipe.getResult();
        if (resultStack == null) return false;

        // Try to add result to inventory; eject if full
        if (!InventoryUtils.addItemStackToInventory(this, resultStack.copy())) {
            ItemUtils.ejectStackWithRandomOffset(worldObj, xCoord, yCoord + 1, zCoord, resultStack.copy());
        }

        return true;
    }

    // --- Consume the required items from the inventory ---
    private void consumeIngredients(CookingPotRecipe recipe) {
        // Count how many of each item is required
        Map<Integer, Integer> requiredCounts = new HashMap<>();
        for (ItemStack item : recipe.getIngredients()) {
            if (item != null)
                requiredCounts.put(item.itemID, requiredCounts.getOrDefault(item.itemID, 0) + 1);
        }

        // Remove items from inventory
        for (int slot = 0; slot < 6; slot++) {
            ItemStack stack = getStackInSlot(slot);
            if (stack == null) continue;

            int id = stack.itemID;
            if (requiredCounts.containsKey(id)) {
                requiredCounts.put(id, requiredCounts.get(id) - 1);
                setInventorySlotContents(slot, null); // remove the item from the slot

                if (requiredCounts.get(id) <= 0) {
                    requiredCounts.remove(id);
                }
            }
        }
    }

    // --- Consume the required liquid from the liquid slot ---
    private void consumeLiquid(CookingPotRecipe recipe) {
        ItemStack requiredLiquid = recipe.getRequiredLiquid();
        if (requiredLiquid == null) return; // no liquid required

        if (!(this instanceof CookingPotTileEntity)) return;

        ItemStack liquidStack = ((CookingPotTileEntity) this).getLiquidStack();
        if (liquidStack != null && liquidStack.isItemEqual(requiredLiquid)) {
            liquidStack.stackSize -= requiredLiquid.stackSize;
            if (liquidStack.stackSize <= 0) {
                ((CookingPotTileEntity) this).setLiquidStack(null);
            }
        }
    }

    private void cookRecipe() {
        System.out.println("trying to cook");
        CookingPotRecipe recipe = CookingPotRecipeManager.findMatchingRecipe(cookStacks, liquidStack);
        if (recipe == null) return;

        // Consume liquid
        liquidStack.stackSize -= recipe.getRequiredLiquid().stackSize;
        if (liquidStack.stackSize <= 0) {
            liquidStack = null;
        }

        // Clear ingredient slots (they must all belong to the recipe)
        for (int i = 0; i < cookStacks.length; i++) {
            cookStacks[i] = null;
        }

        // Place result into slots
        ItemStack result = recipe.getResult().copy();
        int remaining = result.stackSize;

        for (int i = 0; i < cookStacks.length && remaining > 0; i++) {
            cookStacks[i] = new ItemStack(result.itemID, 1, result.getItemDamage());
            remaining--;
        }
        System.out.println("cooked!");

        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

        // Drop overflow
//        while (remaining > 0) {
//            ItemStack drop = new ItemStack(result.itemID, 1, result.getItemDamage());
//            worldObj.spawnEntityInWorld(new EntityItem(worldObj,
//                    xCoord + 0.5, yCoord + 1.0, zCoord + 0.5, drop));
//            remaining--;
//        }
    }

    private boolean canFitResult(ItemStack result) {
        if (result == null) return false;

        int servingsNeeded = result.stackSize; // how many bowls/servings to fit

        // Count how many slots are free
        int freeSlots = 0;
        for (int i = 0; i < cookStacks.length; i++) {
            if (cookStacks[i] == null) {
                freeSlots++;
            } else if (cookStacks[i].isItemEqual(result) && cookStacks[i].stackSize < cookStacks[i].getMaxStackSize()) {
                // Optional: allow stacking if result is same item (but you said stack size = 1 per slot)
                // So this branch usually won’t matter
                freeSlots++;
            }
        }

        return servingsNeeded <= freeSlots;
    }


    public int getFireLevel() {

        int blockBelow = worldObj.getBlockId(xCoord, yCoord - 1 , zCoord);

        if (blockBelow == BTWBlocks.smallCampfire.blockID) return 1;
        else if (blockBelow == BTWBlocks.mediumCampfire.blockID) return 2;
        else if (blockBelow == BTWBlocks.largeCampfire.blockID) return 3;

        else if (blockBelow == BTWBlocks.burningOven.blockID) return 2;

        else return 0;
    }

    public boolean isFoodCooking()
    {
        return getFireLevel() > 0 && cookCounter > 0 && !isLidOpen();
//        // Check fire source below
//        int belowId = worldObj.getBlockId(xCoord, yCoord - 1, zCoord);
//        boolean hasFire = getFireLevel() > 1;
//
//        // Lid closed
//        if (lidProgress == 1F) return false;
//
//        // Valid recipe
//        CookingPotRecipe recipe = CookingPotRecipeManager.findMatchingRecipe(cookStacks, liquidStack);
//        if (recipe == null) return false;
//
//        // Space for result
//        if (!canFitResult(recipe.getResult())) return false;
//
//        // All conditions met
//        return hasFire;
    }

    public boolean isFoodBurning()
    {

//        if ( cookStack != null && getFireLevel() >= 3)
//        {
//            SCCraftingManagerPanCookingRecipe recipe = SCCraftingManagerPanCooking.instance.getRecipe( cookStack ) ;
//
//            if (recipe != null && cookStack.itemID != recipe.getBurnedOutput().itemID )
//            {
//                System.out.println("burning");
//                return true;
//            }
//
//        }

        return false;
    }

    public boolean isFoodCooked()
    {
        // Check if any slot contains an item that is a valid recipe output
        for (int i = 0; i < cookStacks.length; i++) {
            ItemStack stack = cookStacks[i];
            if (stack != null) {
                // You could either check against all recipe outputs or use a "cookedFood" tag/item property
                if (CookingPotRecipeManager.isResult(stack)) {
                    return true;
                }
            }
        }
        return false;
    }


    public int getCookType() {

        if (isFoodCooked()){
            if (CookingPotRecipeManager.isResult(getCookStack(InventoryUtils.getFirstOccupiedStack(this)))){
                int soupID = getCookStack(InventoryUtils.getFirstOccupiedStack(this)).itemID;

                if (soupID == Item.bowlSoup.itemID) return MUSHROOM_SOUP;
                if (soupID == BTWItems.chowder.itemID) return CHOWDER;
                if (soupID == BTWItems.chickenSoup.itemID) return CHICKEN_SOUP;
                if (soupID == BTWItems.heartyStew.itemID) return HEARTY_STEW;
            }
        }
        else {
            int liquidID = getLiquidStack().itemID;

            if (liquidID == Block.waterStill.blockID) return WATER;
            if (liquidID == BTWBlocks.milkFluid.blockID) return MILK;
            if (liquidID == BTWBlocks.chocolateMilkFluid.blockID) return CHOCOLATE_MILK;
        }

        return EMPTY;
    }

    public ItemStack getCookStack(int slot) {
        return cookStacks[slot];
    }

    public void setCookStack(int slot, ItemStack stack) {
        this.cookStacks[slot] = stack;
    }

    public List<ItemStack> getCookStacks() {
        return Arrays.asList(cookStacks);
    }

    public ItemStack getLiquidStack() {
        return liquidStack;
    }

    public void setLiquidStack(ItemStack stack) {
        this.liquidStack = stack;
    }

    public void setLiquidStackAndConvert(ItemStack stack) {
        //currently only buckets
        int convertedLiquid = convertItemStackToLiquid(stack);
        if (convertedLiquid > 0){
            this.liquidStack = new ItemStack(convertedLiquid, 3, 0);
        }
    }

    public static ItemStack convertLiquidToItemStack(ItemStack liquidStack) {
        if (liquidStack.itemID == Block.waterStill.blockID) return new ItemStack(Item.bucketWater);
        if (liquidStack.itemID == BTWBlocks.milkFluid.blockID) return new ItemStack(Item.bucketMilk);
        if (liquidStack.itemID == BTWBlocks.chocolateMilkFluid.blockID) return new ItemStack(BTWItems.milkChocolateBucket);
        else return null;
    }

    public static int convertItemStackToLiquid(ItemStack stack) {
        if (stack.itemID == Item.bucketWater.itemID) return Block.waterStill.blockID;
        if (stack.itemID == Item.bucketMilk.itemID) return BTWBlocks.milkFluid.blockID;
        if (stack.itemID == BTWItems.milkChocolateBucket.itemID) return BTWBlocks.chocolateMilkFluid.blockID;
        else return -1;
    }

    //------------- IInventory ------------//

    @Override
    public int getSizeInventory() {
        return cookStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return cookStacks[slot];
    }

    @Override
    public ItemStack decrStackSize( int iSlot, int iAmount )
    {
        return InventoryUtils.decreaseStackSize( this, iSlot, iAmount );
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        setCookStack(slot, stack);
    }

    @Override
    public String getInvName() {
        return null;
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        if ( worldObj.getBlockTileEntity( xCoord, yCoord, zCoord ) == this )
        {
            return ( entityplayer.getDistanceSq( (double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D )
                    <= 64D );
        }

        return false;
    }

    @Override
    public void openChest() {

    }

    @Override
    public void closeChest() {

    }

    @Override
    public boolean isStackValidForSlot(int slot, ItemStack stack) {

        if (isValidCookItem(stack))
        {
            return true;
        }
        return false;
    }

    public boolean isValidCookItem( ItemStack stack )
    {
//        if ( SCCraftingManagerPanCooking.instance.getRecipe( stack ) != null )
//        {
//            return true;
//        }

        return false;
    }

    //------------- NBT ------------//

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setByte("potRotation", (byte)(this.potRotation & 255));

        if ( liquidStack != null)
        {
            NBTTagCompound liquidTag = new NBTTagCompound();

            liquidStack.writeToNBT( liquidTag );

            tag.setCompoundTag( "liquidStack", liquidTag );
        }

        NBTTagList tagList = new NBTTagList();

        for (int iTempIndex = 0; iTempIndex < cookStacks.length; iTempIndex++ )
        {
            if (cookStacks[iTempIndex] != null )
            {
                NBTTagCompound tempTag = new NBTTagCompound();

                tempTag.setByte( "Slot", (byte)iTempIndex );

                cookStacks[iTempIndex].writeToNBT(tempTag);

                tagList.appendTag( tempTag );
            }
        }

        tag.setTag( "Items", tagList );

        tag.setInteger( "cookCounter", cookCounter );
        tag.setInteger( "cookBurning", cookBurningCounter );
        tag.setBoolean( "hasLid", hasLid );
        tag.setBoolean( "onCampfire", onCampfire );


        tag.setBoolean("lidOpen", lidOpen);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        this.potRotation = tag.getByte("potRotation");


        if (tag.hasKey("liquidStack")) {
            NBTTagCompound liquidTag = tag.getCompoundTag("liquidStack");
            liquidStack = ItemStack.loadItemStackFromNBT(liquidTag);
        }

        NBTTagList tagList = tag.getTagList( "Items" );

        cookStacks = new ItemStack[getSizeInventory()];

        for ( int iTempIndex = 0; iTempIndex < tagList.tagCount(); iTempIndex++ )
        {
            NBTTagCompound tempTag = (NBTTagCompound)tagList.tagAt( iTempIndex );

            int tempSlot = tempTag.getByte( "Slot" ) & 0xff;

            if ( tempSlot >= 0 && tempSlot < cookStacks.length )
            {
                cookStacks[tempSlot] = ItemStack.loadItemStackFromNBT(tempTag);
            }
        }

        if ( tag.hasKey( "cookCounter" ) )
        {
            cookCounter = tag.getInteger( "cookCounter" );
        }

        if ( tag.hasKey( "cookBurning" ) )
        {
            cookBurningCounter = tag.getInteger( "cookBurning" );
        }

        if ( tag.hasKey( "hasLid" ) )
        {
            hasLid = tag.getBoolean( "hasLid" );
        }

        if ( tag.hasKey( "onCampfire" ) )
        {
            onCampfire = tag.getBoolean( "onCampfire" );
        }

        if ( tag.hasKey( "lidOpen" ) )
        {
            lidOpen = tag.getBoolean( "lidOpen" );
        }
    }

    /**
     * Overriden in a sign to provide the text.
     */
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


    public int getSkullRotation()
    {
        return this.potRotation;
    }

    public void setSkullRotation(int par1)
    {
        this.potRotation = par1;
    }


    public int GetSkullRotationServerSafe()
    {
        return this.potRotation;
    }

    public boolean isLidOpen() {
        return this.lidOpen;
    }


    public void setLidOpen(boolean boo) {
        this.lidOpen = boo;
    }

    public boolean isOnCampfire() {
        return this.onCampfire;
    }


    public void setOnCampfire(boolean boo) {
        this.onCampfire = boo;
    }

}