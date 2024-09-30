package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.BTWBlocks;
import btw.block.blocks.*;
import btw.community.sockthing.sockscrops.block.SCBlockIDs;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.utils.NutritionUtils;
import btw.item.BTWItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.Random;

public abstract class FarmlandBaseBlock extends FarmlandBlock {
    protected FarmlandBaseBlock(int blockID, String name) {
        super(blockID);
        setUnlocalizedName(name);
        setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public int idPicked(World world, int x, int y, int z) {
        return this.blockID;
    }

    @Override
    public int damageDropped(int metadata) {

        if (this instanceof FarmlandFullNutritionBlock)
        {
            return NutritionUtils.FULL_NUTRITION_LEVEL;
        }
        else if (this instanceof FarmlandReducedNutritionBlock)
        {
            return NutritionUtils.REDUCED_NUTRITION_LEVEL;
        }
        else if (this instanceof FarmlandLowNutritionBlock)
        {
            return NutritionUtils.LOW_NUTRITION_LEVEL;
        }
        else if (this instanceof FarmlandDepletedNutritionBlock) {
            return NutritionUtils.DEPLETED_NUTRITION_LEVEL;
        }
        else return super.damageDropped(metadata);
    }

    @Override
    public boolean dropComponentItemsOnBadBreak(World world, int x, int y, int z, int metadata, float dropChance) {
        dropItemsIndividually(world, x, y, z, BTWItems.dirtPile.itemID, getDirtPileCountOnBadBreak(), 0, dropChance);
        return true;
    }

    protected abstract float getNutritionMultiplier();

    protected abstract float getWeedsGrowthChance();

    protected abstract void setLooseDirt(World world, int x, int y, int z);

    protected abstract void setHay(World world, int x, int y, int z);

    protected abstract void setDung(World world, int x, int y, int z);

    protected abstract int getDirtPileCountOnBadBreak();

    protected abstract void decreaseNutrition(World world, int x, int y, int z);

    protected int getFarmlandOnFullPlantGrowthWhenDunged() {
        return -1;
    }

    @Override
    protected boolean isFertilized(IBlockAccess blockAccess, int x, int y, int z) {
        return false;
    }

    //Dung
    protected boolean isDunged(IBlockAccess blockAccess, int x, int y, int z) {
        return false;
    }

    protected boolean isHayed(IBlockAccess blockAccess, int x, int y, int z) {
        return false;
    }

    @Override
    public float getPlantGrowthOnMultiplier(World world, int x, int y, int z, Block plantBlock) {
        return getNutritionMultiplier();
    }

    @Override
    public void notifyOfFullStagePlantGrowthOn(World world, int x, int y, int z, Block plantBlock) {
        // decrease nutrition of nutrient block
        attemptToConvertNutritionBlockAround(world, x, y, z, plantBlock);

        // decrease nutrition
        if (!isDunged(world, x, y, z)) {
            decreaseNutrition(world, x, y, z);
        } else {
            world.setBlockAndMetadataWithNotify(x, y, z,
                    getFarmlandOnFullPlantGrowthWhenDunged(), world.getBlockMetadata(x, y, z));
        }
    }


    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        //turn loose if tall grass has grown
        int timeOfDay = (int) (world.worldInfo.getWorldTime() % 24000L);

//        if ( timeOfDay > 14000 && timeOfDay < 22000 ) //night
//        {
//    		if ( rand.nextFloat() <= 0.5F )
//    		{
//    			if( world.getBlockId(x,  y + 1, z ) == Block.tallGrass.blockID )
//    			{
//    				setLooseDirt(world, x, y, z);
//    			}
//    		}
//        }

        //hydration from super super
        if (hasIrrigatingBlocks(world, x, y, z) || world.isRainingAtPos(x,  y + 1, z)) {
            setFullyHydrated(world, x, y, z);
        } else if (isHydrated(world, x, y, z)) {
            if (!hasNutritionBlockAround(world, x, y, z)) {
                dryIncrementally(world, x, y, z);
                //System.out.println("No wood, going to dry out");
            } else ; // System.out.println("I stay wet, I have wood nearby");
        } else {
            checkForSoilReversion(world, x, y, z);
        }
        //Weeds from super
        if (!checkForSnowReversion(world, x, y, z, rand)) {
            updateWeedGrowth(world, x, y, z, rand);
        }

    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int iNeighborBlockID) {

        if (world.getBlockMaterial(x, y + 1, z).isSolid() ||
                canFallIntoBlockAtPos(world, x, y - 1, z)) {
            setLooseDirt(world, x, y, z);
        } else if (getWeedsGrowthLevel(world, x, y, z) > 0 &&
                !canWeedsShareSpaceWithBlockAt(world, x, y + 1, z)) {
            // the weeds we had above us are no longer possible

            setWeedsGrowthLevel(world, x, y, z, 0);
        }
    }

    protected void checkForSoilReversion(World world, int x, int y, int z) {
        if (!doesBlockAbovePreventSoilReversion(world, x, y, z)) {
            setLooseDirt(world, x, y, z);
        }
    }

    @Override
    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fFallDist) {
        // min fall dist of 0.75 (previously 0.5) so that the player can safely
        // step off slabs without potentially ruining crops

        if (!world.isRemote && world.rand.nextFloat() < fFallDist - 0.75F) {
            setLooseDirt(world, x, y, z);
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        //From FCBlockFarmland
        if (!world.isRemote && entity.isEntityAlive() && entity instanceof EntityItem) {
            EntityItem entityItem = (EntityItem) entity;
            ItemStack stack = entityItem.getEntityItem();

            if (!isFertilized(world, x, y, z)) {
                if (stack.itemID == Item.dyePowder.itemID && stack.getItemDamage() == 15  // bone meal
                        && !isFertilized(world, x, y, z)
                        && !isDunged(world, x, y, z)) {
                    stack.stackSize--;

                    if (stack.stackSize <= 0) {
                        entityItem.setDead();
                    }

                    setFertilized(world, x, y, z);

                    world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.pop", 0.25F,
                            ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1F) * 2F);
                } else if (stack.itemID == BTWItems.dung.itemID
                        && !isFertilized(world, x, y, z)
                        && !isDunged(world, x, y, z)) {
                    stack.stackSize--;

                    if (stack.stackSize <= 0) {
                        entityItem.setDead();
                    }

                    setDung(world, x, y, z);

                    world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.pop", 0.25F,
                            ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1F) * 2F);
                }
            }

            if (stack.itemID == BTWItems.straw.itemID) {
                stack.stackSize--;

                if (stack.stackSize <= 0) {
                    entityItem.setDead();
                }

                setHay(world, x, y, z);

                world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.pop", 0.25F,
                        ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1F) * 2F);
            }
        }
    }


    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer,
                                    int par6, float par7, float par8, float par9) {

        EntityPlayer entityItem = par5EntityPlayer;
        ItemStack heldStack = entityItem.getCurrentEquippedItem();

        if (heldStack != null) {
            if (heldStack.itemID == Item.dyePowder.itemID && heldStack.getItemDamage() == 15) {
                if (!isFertilized(world, x, y, z) && !isDunged(world, x, y, z)) {
                    heldStack.stackSize--;

                    setFertilized(world, x, y, z);

                    world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.pop", 0.25F,
                            ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1F) * 2F);
                    return true;
                }
            }
            // dung dat shit
            if (heldStack.itemID == BTWItems.dung.itemID) {
                if (!isFertilized(world, x, y, z) && !isDunged(world, x, y, z)) {
                    heldStack.stackSize--;

                    setDung(world, x, y, z);

                    world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.pop", 0.25F,
                            ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1F) * 2F);
                    return true;
                }
            }
            //always be able to apply hay, but it removes the other fertilizers
            if (heldStack.itemID == BTWItems.straw.itemID) {
                heldStack.stackSize--;

                setHay(world, x, y, z);

                world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.pop", 0.25F,
                        ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1F) * 2F);
                return true;
            }

            return false;
        }

        return false;
    }


    protected boolean doesBlockAbovePreventSoilReversion(World world, int x, int y, int z) {
        // this approach sucks, but given this is a deprecated functionality that won't
        // apply to additional block types, just leave it alone.

        int iBlockAboveID = world.getBlockId(x, y + 1, z);
        Block blockAbove = Block.blocksList[iBlockAboveID];

        if (blockAbove instanceof PlantsBlock) {
            return true;
        }

        return iBlockAboveID == Block.tallGrass.blockID ||
                iBlockAboveID == Block.crops.blockID ||
                iBlockAboveID == Block.melonStem.blockID ||
                iBlockAboveID == Block.pumpkinStem.blockID ||
                iBlockAboveID == Block.potato.blockID ||
                iBlockAboveID == Block.carrot.blockID;
    }

    public void updateWeedGrowth(World world, int x, int y, int z, Random rand) {
        if (world.getBlockId(x, y, z) == blockID) {
            int iWeedsLevel = getWeedsGrowthLevel(world, x, y, z);
            int iTimeOfDay = (int) (world.worldInfo.getWorldTime() % 24000L);

            if (world.isAirBlock(x,  y + 1, z)) {
                if (iTimeOfDay > 14000 && iTimeOfDay < 22000) {
                    // night

                    if (rand.nextInt(20) == 0 &&
                            world.getBlockNaturalLightValueMaximum(x,  y + 1, z) >= FarmlandBlock.LIGHT_LEVEL_FOR_WEED_GROWTH) {
                        // only start to grow on empty earth if there's potential for natural light
                        // to avoid weirdness with weeds popping up deep underground and such

                        world.setBlockWithNotify(x,  y + 1, z,
                                BTWBlocks.weeds.blockID);

                        this.setWeedsGrowthLevel(world, x, y, z, 1);
                    }
                }
            } else if (this.canWeedsShareSpaceWithBlockAt(world, x,  y + 1, z)) {
                if (iTimeOfDay > 14000 && iTimeOfDay < 22000) {
                    // night

                    if (iWeedsLevel == 0) {
                        if (rand.nextInt(20) == 0) {
                            this.setWeedsGrowthLevel(world, x, y, z, 1);
                        }
                    } else if (iWeedsLevel % 2 == 0) {
                        // weeds are only allowed to grow one stage per day, so this flags
                        // them for the next day's growth.

                        this.setWeedsGrowthLevel(world, x, y, z, iWeedsLevel + 1);
                    }
                } else {
                    // day(ish)

                    if (world.getBlockNaturalLightValue(x,  y + 1, z) >= FarmlandBlock.LIGHT_LEVEL_FOR_WEED_GROWTH) {
                        if (iWeedsLevel == 7 && world.getDifficulty().canWeedsKillPlants()) {
                            if (world.getBlockId(x, y, z) == SCBlocks.farmlandMulchFullNutrition.blockID
                                    || world.getBlockId(x, y, z) == SCBlocks.farmlandMulchReducedNutrition.blockID
                                    || world.getBlockId(x, y, z) == SCBlocks.farmlandMulchLowNutrition.blockID
                                    || world.getBlockId(x, y, z) == SCBlocks.farmlandMulchDepletedNutrition.blockID) {
                            } else {
                                this.setWeedsGrowthLevel(world, x, y, z, 0);

                                reduceNutrition(world, x, y, z);
                                removeFertilizerExceptHay(world, x, y, z);
                                world.setBlockAndMetadataWithNotify(x,  y + 1, z, Block.tallGrass.blockID,
                                        1);
                            }
                        } else if (iWeedsLevel % 2 == 1) {
                            this.setWeedsGrowthLevel(world, x, y, z, iWeedsLevel + 1);
                        }
                    }
                }
            } else if (iWeedsLevel > 0) {
                this.setWeedsGrowthLevel(world, x, y, z, 0);
            }
        }
    }

    private static void removeFertilizerExceptHay(World world, int x, int y, int z) {
        int farmlandID = world.getBlockId(x, y, z);
        if (farmlandID == SCBlocks.farmlandFertilizedFullNutrition.blockID
                || farmlandID == SCBlocks.farmlandDungFullNutrition.blockID) {
            world.setBlockWithNotify(x, y, z, SCBlocks.farmlandFullNutrition.blockID);
        } else if (farmlandID == SCBlocks.farmlandFertilizedReducedNutrition.blockID
                || farmlandID == SCBlocks.farmlandDungReducedNutrition.blockID) {
            world.setBlockWithNotify(x, y, z, SCBlocks.farmlandReducedNutrition.blockID);
        } else if (farmlandID == SCBlocks.farmlandFertilizedLowNutrition.blockID
                || farmlandID == SCBlocks.farmlandDungLowNutrition.blockID) {
            world.setBlockWithNotify(x, y, z, SCBlocks.farmlandLowNutrition.blockID);
        } else if (farmlandID == SCBlocks.farmlandFertilizedDepletedNutrition.blockID
                || farmlandID == SCBlocks.farmlandDungDepletedNutrition.blockID) {
            world.setBlockWithNotify(x, y, z, SCBlocks.farmlandDepletedNutrition.blockID);
        }
    }

    private void reduceNutrition(World world, int x, int y, int z) {
        int blockID = world.getBlockId(x, y, z);
        if (blockID != SCBlockIDs.FARMLAND_DEPLETED_NUTRITION_ID
                && blockID != SCBlockIDs.FARMLAND_FERTILIZED_DEPLETED_NUTRITION_ID
                && blockID != SCBlockIDs.FARMLAND_DUNG_DEPLETED_NUTRITION_ID) {
            world.setBlockWithNotify(x, y, z, blockID - 1);
        }
    }

    public static void attemptToConvertNutritionBlockAround(World world, int x, int y, int z, Block plantBlock) {
        Random random = new Random();

        if (random.nextFloat() <= 0.5 && hasNutritionBlockAround(world, x, y, z)) {
            convertNutritionBlockAround(world, x, y, z);
        }
    }


    protected static void convertNutritionBlockAround(World world, int x, int y, int z) {
        //return super.hasNutritionBlockAround(world, x, y, z);
        //System.out.println("trying to find nutrition block");

        int horizontalrange = 1;

        for (int tempI = x - horizontalrange; tempI <= x + horizontalrange; tempI++) {
            for (int tempY = y - horizontalrange; tempY <= y; tempY++) {
                for (int tempZ = z - horizontalrange; tempZ <= z + horizontalrange; tempZ++) {
//                    if ( isValidNutritionBlock(world, tempI, tempY, tempZ) )
//                    {
                    int metadata = world.getBlockMetadata(tempI, tempY, tempZ);
                    //TODO
/*                    if (world.getBlockId(tempI, tempY, tempZ) == Block.wood.blockID && world.getBlockMetadata(tempI, tempY, tempZ) >= 12) //only stump
                    {
                        world.setBlockAndMetadataWithNotify(tempI, tempY, tempZ,
                                SCBlocks.damagedLog.blockID, metadata);
                        //System.out.println("converting stump to damaged log");
                    } else if (world.getBlockId(tempI, tempY, tempZ) == SCBlocks.damagedLog.blockID) {
                        world.setBlockAndMetadataWithNotify(tempI, tempY, tempZ,
                                SCBlocks.mossyLog.blockID, metadata);
                        //System.out.println("converting damaged log to mossy log");
                    } else if (world.getBlockId(tempI, tempY, tempZ) == SCBlocks.mossyLog.blockID) {
                        world.setBlockAndMetadataWithNotify(tempI, tempY, tempZ,
                                SCBlocks.compostBlock.blockID, 0);
                        //System.out.println("converting mossy into compost");
                    } else if (world.getBlockId(tempI, tempY, tempZ) == SCBlocks.compostBlock.blockID) {
                        world.setBlockAndMetadataWithNotify(tempI, tempY, tempZ,
                                BTWBlocks.looseDirt.blockID, 0);
                        //System.out.println("converting compost into dirt");
                    }
                    //return true;*/
//                    }
                }
            }
        }

        //return false;
    }

    @Override
    public void notifyOfPlantAboveRemoved(World world, int x, int y, int z, Block plantBlock) {
        // don't untill on weed growth

        if (world.getBlockId(x,  y + 1, z) != Block.tallGrass.blockID) {
            setLooseDirt(world, x, y, z);
        }

    }


    protected static boolean hasNutritionBlockAround(World world, int x, int y, int z) {
        int horizontalRange = 1;

        for (int tempX = x - horizontalRange; tempX <= x + horizontalRange; tempX++) {
            for (int tempZ = y - horizontalRange; tempZ <= y; tempZ++) {
                for (int tempY = z - horizontalRange; tempY <= z + horizontalRange; tempY++) {
                    //System.out.println(world.getBlockId(tempX, tempZ, tempY));
                    if (hasValidNutrition(world, tempX, tempZ, tempY)) {
                        //System.out.println("true");
                        return true;
                    }
                }
            }
        }

        return false;
    }

    protected static boolean hasValidNutrition(World world, int x, int y, int z) {

        int blockAboveID = world.getBlockId(x, y + 1, z);
        Block blockAbove = Block.blocksList[blockAboveID];


        if (isValidNutritionBlock(world, x, y, z)) {

            if (blockAbove != null && (blockAbove instanceof FarmlandBaseBlock) || (blockAbove instanceof LooseDirtBlock) || (blockAbove instanceof DirtBlock)) {
                //Check for fullblock next to and below
                if (hasValidBlockNextToNutritionBlock(world, x, y, z)) ;
                {
                    return true;
                }
            } else return false;

        } else return false;
    }


    private static boolean hasValidBlockNextToNutritionBlock(World world, int iTempI, int iTempJ, int iTempK) {

        //boolean blockAbove = world.isBlockNormalCube(iTempI, iTempJ + 1, iTempK); we are already checking if above is farmland
        boolean blockBelow = world.isBlockNormalCube(iTempI, iTempJ - 1, iTempK);
        boolean blockN = world.isBlockNormalCube(iTempI, iTempJ, iTempK - 1);
        boolean blockS = world.isBlockNormalCube(iTempI, iTempJ, iTempK + 1);
        boolean blockW = world.isBlockNormalCube(iTempI - 1, iTempJ, iTempK);
        boolean blockE = world.isBlockNormalCube(iTempI + 1, iTempJ, iTempK);

        boolean isNormal = blockBelow || blockN || blockS || blockW || blockE;

        int blockBelowID = world.getBlockId(iTempI, iTempJ - 1, iTempK);
        int blockNID = world.getBlockId(iTempI, iTempJ, iTempK - 1);
        int blockSID = world.getBlockId(iTempI, iTempJ, iTempK + 1);
        int blockWID = world.getBlockId(iTempI - 1, iTempJ, iTempK);
        int blockEID = world.getBlockId(iTempI + 1, iTempJ, iTempK);

        boolean isFarmland = Block.blocksList[blockBelowID] instanceof FarmlandBaseBlock ||
                Block.blocksList[blockNID] instanceof FarmlandBaseBlock ||
                Block.blocksList[blockSID] instanceof FarmlandBaseBlock ||
                Block.blocksList[blockWID] instanceof FarmlandBaseBlock ||
                Block.blocksList[blockEID] instanceof FarmlandBaseBlock;

        return isNormal || isFarmland;
    }

    protected static boolean isValidNutritionBlock(World world, int x, int y, int z) {
        boolean dungId = world.getBlockId(x, y, z) == BTWBlocks.aestheticEarth.blockID;
        boolean dungMetadata = world.getBlockMetadata(x, y, z) == AestheticOpaqueEarthBlock.SUBTYPE_DUNG;

        boolean woodID = world.getBlockId(x, y, z) == Block.wood.blockID;
        boolean stumpMetadata = world.getBlockMetadata(x, y, z) >= 12;

        boolean isMossyLog = world.getBlockId(x, y, z) == -1; //TODO: SCBlocks.damagedLog.blockID;
        boolean isCompost = world.getBlockId(x, y, z) == -1; //TODO: SCBlocks.compostBlock.blockID;
        boolean isDung = dungId && dungMetadata;
        boolean isStump = woodID && stumpMetadata;

        return isStump || isDung || isMossyLog || isCompost;
    }

    @Environment(EnvType.CLIENT)
    protected Icon wetDirt;

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister register) {

        blockIcon = register.registerIcon(NutritionUtils.LOOSE_DIRT_TEXTURE_NAMES[NutritionUtils.FULL_NUTRITION_LEVEL]);
        wetDirt = register.registerIcon(NutritionUtils.WET_LOOSE_DIRT_TEXTURE_NAMES[NutritionUtils.FULL_NUTRITION_LEVEL]);

        iconTopWet = register.registerIcon(NutritionUtils.WET_FARMLAND_TEXTURE_NAMES[NutritionUtils.FULL_NUTRITION_LEVEL]);
        iconTopDry = register.registerIcon(NutritionUtils.DRY_FARMLAND_TEXTURE_NAMES[NutritionUtils.FULL_NUTRITION_LEVEL]);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Icon getIcon(int side, int metadata) {
        if (side == 1) {
            if (isHydrated(metadata)) {
                return iconTopWet;
            }

            return iconTopDry;
        }

        if (isHydrated(metadata)) {
            return wetDirt;
        } else return blockIcon;
    }
}