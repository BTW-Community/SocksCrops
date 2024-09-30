package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.BTWBlocks;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.utils.NutritionUtils;
import net.minecraft.src.World;

public class FarmlandFullNutritionBlock extends FarmlandBaseBlock {
    public FarmlandFullNutritionBlock(int blockID, String name) {
        super(blockID, name);
    }

    public float getNutritionMultiplier() {
        return NutritionUtils.MULTIPLIER_FULL_NUTRITION;
    }

    public float getWeedsGrowthChance() {
        return NutritionUtils.WEEDS_GROWTH_CHANCE_FULL_NUTRITION;
    }

    @Override
    protected void setLooseDirt(World world, int x, int y, int z) {
        world.setBlockAndMetadataWithNotify(x, y, z,
                BTWBlocks.looseDirt.blockID, NutritionUtils.FULL_NUTRITION_LEVEL);
    }

    @Override
    protected void setFertilized(World world, int x, int y, int z) {
        world.setBlockAndMetadataWithNotify(x, y, z,
                SCBlocks.farmlandFertilizedFullNutrition.blockID, world.getBlockMetadata(x, y, z));
    }

    @Override
    protected void setDung(World world, int x, int y, int z) {
        world.setBlockAndMetadataWithNotify(x, y, z,
                SCBlocks.farmlandDungFullNutrition.blockID, world.getBlockMetadata(x, y, z));
    }

    @Override
    protected void setHay(World world, int x, int y, int z) {
        world.setBlockAndMetadataWithNotify(x, y, z,
                SCBlocks.farmlandMulchFullNutrition.blockID, world.getBlockMetadata(x, y, z));
    }

    @Override
    protected int getDirtPileCountOnBadBreak() {
        return 6;
    }

    @Override
    protected int getFarmlandOnFullPlantGrowthWhenDunged() {
        return this.blockID;
    }

    @Override
    protected void decreaseNutrition(World world, int x, int y, int z) {
        world.setBlockAndMetadataWithNotify(x, y, z,
                SCBlocks.farmlandReducedNutrition.blockID, world.getBlockMetadata(x, y, z));
    }

    //----------- Client Side Functionality -----------//
}
