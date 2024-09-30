package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.BTWBlocks;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.utils.NutritionUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.IconRegister;
import net.minecraft.src.World;

public class FarmlandDepletedNutritionBlock extends FarmlandBaseBlock {
    public FarmlandDepletedNutritionBlock(int blockID, String name) {
        super(blockID, name);
    }

    @Override
    public float getNutritionMultiplier() {
        return NutritionUtils.MULTIPLIER_DEPLETED_NUTRITION;
    }

    @Override
    public float getWeedsGrowthChance() {
        return NutritionUtils.WEEDS_GROWTH_CHANCE_DEPLETED_NUTRITION;
    }

    @Override
    protected void setLooseDirt(World world, int x, int y, int z) {
        world.setBlockAndMetadataWithNotify(x, y, z,
                BTWBlocks.looseDirt.blockID, NutritionUtils.DEPLETED_NUTRITION_LEVEL);
    }

    @Override
    protected void setFertilized(World world, int x, int y, int z) {
        world.setBlockAndMetadataWithNotify(x, y, z,
                SCBlocks.farmlandFertilizedDepletedNutrition.blockID, world.getBlockMetadata(x, y, z));
    }

    @Override
    protected void setDung(World world, int x, int y, int z) {
        world.setBlockAndMetadataWithNotify(x, y, z,
                SCBlocks.farmlandDungDepletedNutrition.blockID, world.getBlockMetadata(x, y, z));
    }

    @Override
    protected void setHay(World world, int x, int y, int z) {
        world.setBlockAndMetadataWithNotify(x, y, z,
                SCBlocks.farmlandMulchDepletedNutrition.blockID, world.getBlockMetadata(x, y, z));
    }

    @Override
    protected int getDirtPileCountOnBadBreak() {
        return 0;
    }

    @Override
    protected int getFarmlandOnFullPlantGrowthWhenDunged() {
        return this.blockID;
    }

    @Override
    protected void decreaseNutrition(World world, int x, int y, int z) {
        world.setBlockAndMetadataWithNotify(x, y, z,
                SCBlocks.farmlandDepletedNutrition.blockID, world.getBlockMetadata(x, y, z));
    }

    //----------- Client Side Functionality -----------//

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        blockIcon = register.registerIcon(NutritionUtils.LOOSE_DIRT_TEXTURE_NAMES[NutritionUtils.DEPLETED_NUTRITION_LEVEL]);
        wetDirt = register.registerIcon(NutritionUtils.WET_LOOSE_DIRT_TEXTURE_NAMES[NutritionUtils.DEPLETED_NUTRITION_LEVEL]);

        iconTopWet = register.registerIcon(NutritionUtils.WET_FARMLAND_TEXTURE_NAMES[NutritionUtils.DEPLETED_NUTRITION_LEVEL]);
        iconTopDry = register.registerIcon(NutritionUtils.DRY_FARMLAND_TEXTURE_NAMES[NutritionUtils.DEPLETED_NUTRITION_LEVEL]);
    }
}
