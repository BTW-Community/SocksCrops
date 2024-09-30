package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.BTWBlocks;
import btw.block.blocks.GrassBlock;
import btw.block.blocks.GroundCoverBlock;
import btw.client.render.util.RenderUtils;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.utils.NutritionUtils;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import btw.item.items.HoeItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Random;

public class PlanterGrassBlock extends PlanterBaseBlock {
    public static final float GRASS_SPREAD_CHANCE = 0.8F;
    public static final float GRASS_GROWTH_CHANCE = 1 / 12F;
    public static final float NUTRITION_GAIN_CHANCE = 0.25F;
    public static final float PLANTS_GROWTH_CHANGE = 0.25F;

    public PlanterGrassBlock(int blockID, String name) {
        super(blockID, name);
    }

    protected float getPlantGrowthChance(int metadata) {
        int nutritionLevel = NutritionUtils.getPlanterNutritionLevel(metadata);
        switch (nutritionLevel) {
            default:
            case NutritionUtils.FULL_NUTRITION_LEVEL:
                return 0.2F;
            case NutritionUtils.REDUCED_NUTRITION_LEVEL:
                return 0.4F;
            case NutritionUtils.LOW_NUTRITION_LEVEL:
                return 0.6F;
            case NutritionUtils.DEPLETED_NUTRITION_LEVEL:
                return 0.8F;
        }
    }

    @Override
    public void getSubBlocks(int blockID, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(blockID, 1, NutritionUtils.FULL_NUTRITION_LEVEL));
        list.add(new ItemStack(blockID, 1, NutritionUtils.REDUCED_NUTRITION_LEVEL));
        list.add(new ItemStack(blockID, 1, NutritionUtils.LOW_NUTRITION_LEVEL));
        list.add(new ItemStack(blockID, 1, NutritionUtils.DEPLETED_NUTRITION_LEVEL));

        //Sparse Grass
        /*
        list.add(new ItemStack(blockID, 1, 4));
        list.add(new ItemStack(blockID, 1, 5));
        list.add(new ItemStack(blockID, 1, 6));
        list.add(new ItemStack(blockID, 1, 7));
        */
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        int metadata = world.getBlockMetadata(x, y, z);

        //Turn Grass to Farmland
        Block blockAbove = Block.blocksList[world.getBlockId(x, y + 1, z)];

        if (blockAbove != null) {
            boolean isBlockAboveSolid = blockAbove.isNormalCube(world, x, y + 1, z);

            if (isBlockAboveSolid) {
                world.setBlockAndMetadata(x, y, z, SCBlocks.planterFarmland.blockID, NutritionUtils.getPlanterNutritionLevel(metadata));
            }
        }

        if (isSparse(world, x, y, z)) {
            //Grass Growth
            if (rand.nextFloat() <= GRASS_GROWTH_CHANCE) {
                world.setBlockAndMetadata(x, y, z, this.blockID, metadata - 4);
            }
        } else {
            //Grass Spread
            if (GrassBlock.canGrassSpreadFromLocation(world, x, y, z)) {
                if (rand.nextFloat() <= GRASS_SPREAD_CHANCE) {
                    checkForGrassSpreadFromLocation(world, x, y, z);
                }
            }

            //Nutrition gain
            if (rand.nextFloat() == NUTRITION_GAIN_CHANCE && NutritionUtils.getPlanterNutritionLevel(metadata) > NutritionUtils.FULL_NUTRITION_LEVEL) {
                world.setBlockAndMetadata(x, y, z, this.blockID, metadata - 1);
            }

            //grow plants
            if (rand.nextFloat() <= PLANTS_GROWTH_CHANGE && rand.nextFloat() <= getPlantGrowthChance(metadata)) {
                growPlantsAbovePlanter(world, x, y, z, rand);
            }
        }
    }

    @Override
    public float getPlantGrowthOnMultiplier(World world, int x, int y, int z, Block plantBlock) {
        if (getIsFertilizedForPlantGrowth(world, x, y, z)) {
            return 2F * NutritionUtils.getPlanterNutritionLevel(world.getBlockMetadata(x, y, z));
        }

        return 1F * NutritionUtils.getPlanterNutritionLevel(world.getBlockMetadata(x, y, z));
    }

    @Override
    public boolean canConvertBlock(ItemStack stack, World world, int x, int y, int z) {
        return stack != null && stack.getItem() instanceof HoeItem;
    }

    @Override
    public boolean convertBlock(ItemStack stack, World world, int x, int y, int z, int fromSide) {
        world.setBlockWithNotify(x, y, z, BTWBlocks.planterWithSoil.blockID);
        return true;
    }

    @Override
    public boolean canReedsGrowOnBlock(World world, int i, int j, int k) {
        return true;
    }

    @Override
    public boolean canSaplingsGrowOnBlock(World world, int i, int j, int k) {
        return true;
    }

    @Override
    public boolean canWildVegetationGrowOnBlock(World world, int i, int j, int k) {
        return true;
    }

    @Override
    public boolean canCactusGrowOnBlock(World world, int i, int j, int k) {
        return true;
    }


    //------------ Grass Related Methods ----------//

    private boolean isSparse(World world, int x, int y, int z) {
        return isSparse(world.getBlockMetadata(x, y, z));
    }

    private boolean isSparse(int metadata) {
        return metadata > 3;
    }

    public static void checkForGrassSpreadFromLocation(World world, int x, int y, int z) {
        if (world.provider.dimensionId != 1 && !GroundCoverBlock.isGroundCoverRestingOnBlock(world, x, y, z)) {
            // check for grass nearby
            int i = x + world.rand.nextInt(3) - 1;
            int j = y + world.rand.nextInt(4) - 2;
            int k = z + world.rand.nextInt(3) - 1;

            Block targetBlock = Block.blocksList[world.getBlockId(i, j, k)];

            if (targetBlock != null) {
                attempToSpreadGrassToLocation(world, i, j, k);
            }
        }
    }

    public static boolean attempToSpreadGrassToLocation(World world, int x, int y, int z) {
        int targetBlockID = world.getBlockId(x, y, z);
        Block targetBlock = Block.blocksList[targetBlockID];

        if (targetBlock.getCanGrassSpreadToBlock(world, x, y, z) &&
                Block.lightOpacity[world.getBlockId(x, y + 1, z)] <= 2 &&
                !GroundCoverBlock.isGroundCoverRestingOnBlock(world, x, y, z)) {
            return targetBlock.spreadGrassToBlock(world, x, y, z);
        }

        return false;
    }


    //------------ Growth Related Methods ----------//

    private int getGrowthLevel(int meta) {
        if (meta > 11) return 0;
        else if (meta > 7) return 1;
        else if (meta > 3) return 2;
        else return 3;
    }

    private void growPlantsAbovePlanter(World world, int x, int y, int z, Random rand) {
        if (world.isAirBlock(x, y + 1, z)) {
            // grass planters with nothing in them will eventually sprout flowers or tall grass

            if (world.getBlockLightValue(x, y + 1, z) >= 8) {
                int plantType = rand.nextInt(10);
                if (plantType == 0) {
                    if (rand.nextInt(2) == 0) {
                        world.setBlockWithNotify(x, y + 1, z, Block.plantRed.blockID);
                    } else world.setBlockWithNotify(x, y + 1, z, Block.plantYellow.blockID);
                } else if (plantType < 8) {
                    if (rand.nextInt(4) == 0) {
                        world.setBlockAndMetadataWithNotify(x, y + 1, z, Block.tallGrass.blockID, 2); //Fern
                    } else world.setBlockAndMetadataWithNotify(x, y + 1, z, Block.tallGrass.blockID, 1); //Grass
                } else {
                    if (world.isAirBlock(x, y + 2, z)) {
                        if (rand.nextInt(2) == 0) {
                            world.setBlockAndMetadataWithNotify(x, y + 1, z, SCBlocks.doubleTallGrass.blockID, DoubleTallGrassBlock.FERN);
                            world.setBlockAndMetadataWithNotify(x, y + 2, z, SCBlocks.doubleTallGrass.blockID, DoubleTallGrassBlock.setTopBlock(DoubleTallGrassBlock.FERN));
                        } else {
                            world.setBlockAndMetadataWithNotify(x, y + 1, z, SCBlocks.doubleTallGrass.blockID, DoubleTallGrassBlock.GRASS);
                            world.setBlockAndMetadataWithNotify(x, y + 2, z, SCBlocks.doubleTallGrass.blockID, DoubleTallGrassBlock.setTopBlock(DoubleTallGrassBlock.GRASS));
                        }
                    }
                }
            }
        }
    }

    //------------ Abstract Methods ----------//

    @Override
    protected boolean isHydrated(int metadata) {
        return false;
    }

    @Override
    protected boolean isFertilized(int metadata) {
        return false;
    }

    @Override
    protected void setFertilized(World world, int x, int y, int z) {
    }

    //------------ Client ----------//

    @Override
    @Environment(EnvType.CLIENT)
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
        if (!grassPass) {
            return 16777215;
        } else {
            return getGrassColor(blockAccess, x, y, z);
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);

        topGrass[0] = register.registerIcon("fcBlockGrassSparse");
        topGrass[1] = register.registerIcon("grass_top");

        for (int nutritionLevel = 0; nutritionLevel < 4; nutritionLevel++) {
            dirt[nutritionLevel] = register.registerIcon(NutritionUtils.SPARSE_DIRT_TEXTURE_NAMES[nutritionLevel]);
        }

    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean renderBlock(RenderBlocks renderer, int x, int y, int z) {
        renderFilledPlanterBlock(renderer, x, y, z);

        grassPass = true;
        renderOverlayTexture(renderer, x, y, z, getOverlayTexture(renderer.blockAccess.getBlockMetadata(x, y, z)), null);
        grassPass = false;
        return true;
    }

    //------------ Addon Client ---------//

    @Environment(EnvType.CLIENT)
    protected boolean grassPass = false;

    @Environment(EnvType.CLIENT)
    private final Icon[] topGrass = new Icon[2];

    @Environment(EnvType.CLIENT)
    private final Icon[] dirt = new Icon[4];

    @Override
    @Environment(EnvType.CLIENT)
    protected Icon getContentsTexture(int metadata) {
        return dirt[NutritionUtils.getPlanterNutritionLevel(metadata)];
    }

    @Override
    @Environment(EnvType.CLIENT)
    protected Icon getOverlayTexture(int metadata) {
        return topGrass[getGrowthLevel(metadata) & 1];
    }

    @Override
    @Environment(EnvType.CLIENT)
    protected void renderOverlayForItemRender(RenderBlocks renderer, int itemDamage, float brightness, Icon overlay) {
        overlay = getOverlayTexture(itemDamage);
        int var14 = getGrassColorForItemRender(itemDamage);

        float var8 = (float) (var14 >> 16 & 255) / 255.0F;
        float var9 = (float) (var14 >> 8 & 255) / 255.0F;
        float var10 = (float) (var14 & 255) / 255.0F;
        GL11.glColor4f(var8 * brightness, var9 * brightness, var10 * brightness, 1.0F);

        renderer.setOverrideBlockTexture(overlay);
        renderer.setRenderBounds(0, 0.998, 0, 1, 1, 1);
        RenderUtils.renderInvBlockWithMetadata(renderer, this, -0.5F, -0.5F, -0.5F, itemDamage);
        renderer.clearOverrideBlockTexture();
    }

    @Environment(EnvType.CLIENT)
    protected int getGrassColorForItemRender(int metadata) {
        switch (NutritionUtils.getPlanterNutritionLevel(metadata)) {
            default:
            case NutritionUtils.FULL_NUTRITION_LEVEL:
                return SCRenderUtils.color(SCRenderUtils.PLAINS_BIOME_COLOR, 0, 0, 0);
            case NutritionUtils.REDUCED_NUTRITION_LEVEL:
                return SCRenderUtils.color(SCRenderUtils.PLAINS_BIOME_COLOR, 150, -25, 0);
            case NutritionUtils.LOW_NUTRITION_LEVEL:
                return SCRenderUtils.color(SCRenderUtils.PLAINS_BIOME_COLOR, 300, -50, 0);
            case NutritionUtils.DEPLETED_NUTRITION_LEVEL:
                return SCRenderUtils.color(SCRenderUtils.PLAINS_BIOME_COLOR, 400, -100, 0);
        }
    }
}
