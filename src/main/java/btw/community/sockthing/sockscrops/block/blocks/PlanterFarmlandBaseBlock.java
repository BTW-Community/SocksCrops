package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.blocks.GrassBlock;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.utils.NutritionUtils;
import btw.item.BTWItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.List;
import java.util.Random;

public abstract class PlanterFarmlandBaseBlock extends PlanterBaseBlock {

    public PlanterFarmlandBaseBlock(int blockID, String name) {
        super(blockID, name);
    }

    @Override
    public int damageDropped(int par1) {
        return par1 & 3;
    }

    @Override
    public void getSubBlocks(int blockID, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(blockID, 1, 0));
        list.add(new ItemStack(blockID, 1, 1));
        list.add(new ItemStack(blockID, 1, 2));
        list.add(new ItemStack(blockID, 1, 3));

        /*
        list.add(new ItemStack(blockID, 1, 4));
        list.add(new ItemStack(blockID, 1, 5));
        list.add(new ItemStack(blockID, 1, 6));
        list.add(new ItemStack(blockID, 1, 7));
        */
    }

    @Override
    public boolean onBlockActivated( World world, int x, int y, int z, EntityPlayer player, int targetFace, float xClick, float yClick, float zClick )
    {
        ItemStack heldStack = player.getHeldItem();

        if ( heldStack.itemID == BTWItems.dung.itemID ) // bone meal
        {
            if ( !getIsDunged(world, x,y,z) && !getIsFertilized(world,x,y,z) )
            {
                setIsDunged(world, x, y, z, true);
                heldStack.stackSize--;
                return true;
            }
        }

        return false;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity )
    {
        if ( !world.isRemote && entity.isEntityAlive() && entity instanceof EntityItem )
        {
            EntityItem entityItem = (EntityItem)entity;
            ItemStack stack = entityItem.getEntityItem();

            if ( stack.getItem().itemID == Item.dyePowder.itemID &&
                    stack.getItemDamage() == 15 ) // bone meal
            {
                if ( attemptToApplyFertilizerTo(world, i, j, k) )
                {
                    stack.stackSize--;

                    if ( stack.stackSize <= 0 )
                    {
                        entityItem.setDead();
                    }

                    world.playSoundEffect( i + 0.5D, j + 0.5D, k + 0.5D, "random.pop", 0.25F,
                            ( ( world.rand.nextFloat() - world.rand.nextFloat() ) *
                                    0.7F + 1F ) * 2F );
                }
            }
            else if ( stack.getItem().itemID == BTWItems.dung.itemID ) // dung
            {
                if ( attemptToApplyDungTo(world, i, j, k) )
                {
                    stack.stackSize--;

                    if ( stack.stackSize <= 0 )
                    {
                        entityItem.setDead();
                    }

                    world.playSoundEffect( i + 0.5D, j + 0.5D, k + 0.5D, "random.pop", 0.25F,
                            ( ( world.rand.nextFloat() - world.rand.nextFloat() ) *
                                    0.7F + 1F ) * 2F );
                }
            }
        }
    }

    public boolean attemptToApplyDungTo(World world, int x, int y, int z) {
        if (!getIsDunged(world, x, y, z) && !getIsFertilized(world, x,y,z)) {
            setIsDunged(world, x, y, z, true);

            return true;
        }

        return false;
    }

    //------------- Copy and Edit of PlanterBlockSoil ------------//
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        boolean hasIrrigation = hasIrrigatingBlocks(world, x, y, z) ||
                world.isRainingAtPos(x, y + 1, z);

        if (hasIrrigation != getIsHydrated(world, x, y, z)) {
            setIsHydrated(world, x, y, z, hasIrrigation);
        }
    }

    @Override
    public boolean attemptToApplyFertilizerTo(World world, int x, int y, int z) {
        if (!getIsFertilized(world, x, y, z) && !getIsDunged(world,x,y,z)) {
            setIsFertilized(world, x, y, z, true);

            return true;
        }

        return false;
    }

    @Override
    public boolean canDomesticatedCropsGrowOnBlock(World world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean canReedsGrowOnBlock(World world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean canSaplingsGrowOnBlock(World world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean canWildVegetationGrowOnBlock(World world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean canCactusGrowOnBlock(World world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean isBlockHydratedForPlantGrowthOn(World world, int x, int y, int z) {
        return getIsHydrated(world, x, y, z);
    }

    @Override
    public boolean isConsideredNeighbouringWaterForReedGrowthOn(World world, int x, int y, int z) {
        return getIsHydrated(world, x, y, z);
    }

    @Override
    public boolean getIsFertilizedForPlantGrowth(World world, int x, int y, int z) {
        return getIsFertilized(world, x, y, z);
    }

    @Override
    public void notifyOfFullStagePlantGrowthOn(World world, int x, int y, int z, Block plantBlock) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int nutrition = NutritionUtils.getPlanterNutritionLevel(oldMetadata);
        int newMetadata = nutrition + 1;

        if (getIsFertilized(world, x, y, z)) {
            if (getIsHydrated(oldMetadata)){
                world.setBlockMetadata(x,y,z, setIsHydrated(newMetadata, true));
            }
            setIsFertilized(world, x, y, z, false);
        }
        else if (getIsDunged(world, x,y,z)){
            setIsDunged(world,x,y,z, false);
        }
    }

    protected boolean hasIrrigatingBlocks(World world, int x, int y, int z) {
        // planters can only be irrigated by direct neighbors

        return world.getBlockMaterial(x, y - 1, z) == Material.water ||
                world.getBlockMaterial(x, y + 1, z) == Material.water ||
                world.getBlockMaterial(x, y, z - 1) == Material.water ||
                world.getBlockMaterial(x, y, z + 1) == Material.water ||
                world.getBlockMaterial(x - 1, y, z) == Material.water ||
                world.getBlockMaterial(x + 1, y, z) == Material.water;
    }

    //------------- Class Specific Methods ------------//


    @Override
    protected boolean isHydrated(int metadata) {
        return getIsHydrated(metadata);
    }

    protected boolean getIsHydrated(IBlockAccess blockAccess, int x, int y, int z) {
        return getIsHydrated(blockAccess.getBlockMetadata(x, y, z));
    }

    protected boolean getIsHydrated(int metadata) {
        return metadata > 3;
    }

    protected void setIsHydrated(World world, int x, int y, int z, boolean isHydrated) {
        int metadata = setIsHydrated(world.getBlockMetadata(x, y, z), isHydrated);

        world.setBlockMetadataWithNotify(x, y, z, metadata);
    }

    protected int setIsHydrated(int metadata, boolean isHydrated) {
        if (isHydrated) return metadata + 4;
        return metadata - 4;
    }

    protected boolean getIsFertilized(IBlockAccess blockAccess, int x, int y, int z) {
        return getIsFertilized(blockAccess.getBlockMetadata(x, y, z));
    }

    protected boolean getIsFertilized(int metadata) {
        return false;
    }

    protected void setIsFertilized(World world, int x, int y, int z, boolean isFertilized) {
        int metadata = world.getBlockMetadata(x, y, z);

        if (isFertilized){
            world.setBlockAndMetadataWithNotify(x, y, z, SCBlocks.planterFarmlandFertilized.blockID, metadata);
        }
        else  world.setBlockAndMetadataWithNotify(x, y, z, SCBlocks.planterFarmland.blockID, metadata);
    }

    protected boolean getIsDunged(IBlockAccess blockAccess, int x, int y, int z) {
        return getIsDunged(blockAccess.getBlockMetadata(x, y, z));
    }

    protected boolean getIsDunged(int metadata) {
        return false;
    }

    protected void setIsDunged(World world, int x, int y, int z, boolean isFertilized) {
        int metadata = world.getBlockMetadata(x, y, z);

        if (isFertilized){
            world.setBlockAndMetadataWithNotify(x, y, z, SCBlocks.planterFarmlandDung.blockID, metadata);
        }
        else  world.setBlockAndMetadataWithNotify(x, y, z, SCBlocks.planterFarmland.blockID, metadata);
    }

    //------------ Grass Spread ----------//

    @Override
    public boolean getCanGrassSpreadToBlock(World world, int x, int y, int z) {
        Block blockAbove = Block.blocksList[world.getBlockId(x, y + 1, z)];

        if (blockAbove == null || blockAbove.getCanGrassGrowUnderBlock(world, x, y + 1, z, false)) {
            return hasGrassAdjacent(world, x, y, z);
        }

        return false;
    }

    private boolean hasGrassAdjacent(World world, int x, int y, int z) {
        Block[] validGrassPos = new Block[]{
                Block.blocksList[world.getBlockId(x - 1, y, z)],
                Block.blocksList[world.getBlockId(x - 1, y + 1, z)],
                Block.blocksList[world.getBlockId(x + 1, y, z)],
                Block.blocksList[world.getBlockId(x + 1, y + 1, z)],
                Block.blocksList[world.getBlockId(x, y, z - 1)],
                Block.blocksList[world.getBlockId(x, y + 1, z - 1)],
                Block.blocksList[world.getBlockId(x, y, z + 1)],
                Block.blocksList[world.getBlockId(x, y + 1, z + 1)]
        };

        for (Block block : validGrassPos) {
            if (block instanceof GrassBlock || block instanceof PlanterGrassBlock) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean spreadGrassToBlock(World world, int x, int y, int z) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int nutrition = NutritionUtils.getPlanterNutritionLevel(oldMetadata);
        int newMetadata = nutrition + 4;

        world.setBlockAndMetadataWithNotify(x, y, z, SCBlocks.planterGrass.blockID, newMetadata);

        return true;
    }

    //------------- Abstract ------------//

    @Override
    @Environment(EnvType.CLIENT)
    protected Icon getContentsTexture(int metadata) {
        if (isHydrated(metadata)) {
            return farmlandHydrated[NutritionUtils.getPlanterNutritionLevel(metadata)];
        }
        return farmland[NutritionUtils.getPlanterNutritionLevel(metadata)];
    }

    @Override
    @Environment(EnvType.CLIENT)
    protected Icon getOverlayTexture(int metadata) {
        return null;
    }

    //------------- Client ------------//

    @Environment(EnvType.CLIENT)
    protected Icon[] farmland = new Icon[4];

    @Environment(EnvType.CLIENT)
    protected Icon[] farmlandHydrated = new Icon[4];

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);

        for (int nutritionLevel = 0; nutritionLevel < farmland.length; nutritionLevel++) {
            farmland[nutritionLevel] = register.registerIcon(NutritionUtils.DRY_FARMLAND_TEXTURE_NAMES[nutritionLevel]);
            farmlandHydrated[nutritionLevel] = register.registerIcon(NutritionUtils.WET_FARMLAND_TEXTURE_NAMES[nutritionLevel]);
        }
    }
}
