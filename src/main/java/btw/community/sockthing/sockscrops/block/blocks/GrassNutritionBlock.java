package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.BTWBlocks;
import btw.block.blocks.GrassBlock;
import btw.community.sockthing.sockscrops.utils.NutritionUtils;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import btw.item.BTWItems;
import btw.item.util.ItemUtils;
import btw.world.util.BlockPos;
import com.prupe.mcpatcher.mal.block.RenderBlocksUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.List;
import java.util.Random;

public class GrassNutritionBlock extends GrassBlock {
    public GrassNutritionBlock(int blocKID, String name) {
        super(blocKID);

        setTickRandomly(true);

        setHardness(0.6F);
        setShovelsEffectiveOn();
        setHoesEffectiveOn();

        setStepSound(soundGrassFootstep);
        setUnlocalizedName(name);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        int nutritionLevel = NutritionUtils.getGrassNutritionLevel(world.getBlockMetadata(x, y, z));

        if (!canGrassSurviveAtLocation(world, x, y, z)) {
            // convert back to dirt in low light
            world.setBlockAndMetadataWithNotify(x, y, z, Block.dirt.blockID, nutritionLevel);

        } else if (canGrassSpreadFromLocation(world, x, y, z)) {
            if (rand.nextFloat() <= GROWTH_CHANCE) {
                checkForGrassSpreadFromLocation(world, x, y, z);
            }

            if (isSparse(world, x, y, z) && rand.nextInt(SELF_GROWTH_CHANCE) == 0) {
                this.setFullyGrown(world, x, y, z);
            }
        }

        if (nutritionLevel > NutritionUtils.FULL_NUTRITION_LEVEL && !isSparse(world, x, y, z)) {
            attemptToIncreaseNutrition(world, x, y, z, rand);
        }

    }

    @Override
    public void notifyOfFullStagePlantGrowthOn(World world, int x, int y, int z, Block plantBlock) {
        int metadata = world.getBlockMetadata(x, y, z);

        if (metadata > 0 && metadata < 6) {
            // revert back to soil
            world.setBlockAndMetadataWithNotify(x, y, z, this.blockID, metadata + 2);
        }

    }

    @Override
    public float getPlantGrowthOnMultiplier(World world, int x, int y, int z, Block plantBlock) {
        return NutritionUtils.getGrassNutritionMultiplier(world.getBlockMetadata(x, y, z));
    }

    private void attemptToIncreaseNutrition(World world, int x, int y, int z, Random rand) {
        int iTimeOfDay = (int) (world.worldInfo.getWorldTime() % 24000L);
        int metadata = world.getBlockMetadata(x, y, z);

        if (rand.nextFloat() <= NutritionUtils.GRASS_NUTRITION_GROWTH_CHANCE) {
            if (iTimeOfDay > 14000 && iTimeOfDay < 22000) // at night
            {
                if (NutritionUtils.getGrassNutritionLevel(metadata) > 1) {
                    world.setBlockAndMetadataWithNotify(x, y, z, this.blockID, metadata - 2);
                } else world.setBlockWithNotify(x, y, z, Block.grass.blockID);
            }
        }
    }

    @Override
    public int idDropped(int metadata, Random rand, int fortuneModifier) {
        return BTWBlocks.looseDirt.blockID;
    }

    @Override
    public int damageDropped(int meta) {
        return NutritionUtils.getGrassNutritionLevel(meta);
    }

    @Override
    public boolean dropComponentItemsOnBadBreak(World world, int x, int y, int z, int metadata, float chanceOfDrop) {
        int pileCount = 6 - (metadata * 2); //0:6, 1:4, 2:2, 3:0
        dropItemsIndividually(world, x, y, z, BTWItems.dirtPile.itemID, pileCount, 0, chanceOfDrop);

        return true;
    }

    @Override
    protected void onNeighborDirtDugWithImproperTool(World world, int x, int y, int z, int toFacing) {
        int nutritionLevel = NutritionUtils.getGrassNutritionLevel(world.getBlockMetadata(x, y, z));

        // only disrupt grass/mycelium when block below is dug out
        if (toFacing == 0) {
            world.setBlockAndMetadataWithNotify(x, y, z, BTWBlocks.looseDirt.blockID, nutritionLevel);
        }
    }

    @Override
    public void onGrazed(World world, int x, int y, int z, EntityAnimal animal) {
        int nutritionLevel = NutritionUtils.getGrassNutritionLevel(world.getBlockMetadata(x, y, z));

        if (!animal.getDisruptsEarthOnGraze()) {
            if (isSparse(world, x, y, z)) {
                world.setBlockAndMetadataWithNotify(x, y, z, Block.dirt.blockID, nutritionLevel);
            } else {
                setSparse(world, x, y, z);
            }
        } else {
            world.setBlockAndMetadataWithNotify(x, y, z, BTWBlocks.looseDirt.blockID, nutritionLevel);

            notifyNeighborsBlockDisrupted(world, x, y, z);
        }
    }

    @Override
    public void onVegetationAboveGrazed(World world, int x, int y, int z, EntityAnimal animal) {
        int nutritionLevel = NutritionUtils.getGrassNutritionLevel(world.getBlockMetadata(x, y, z));

        if (animal.getDisruptsEarthOnGraze()) {
            world.setBlockAndMetadataWithNotify(x, y, z, BTWBlocks.looseDirt.blockID, nutritionLevel);

            notifyNeighborsBlockDisrupted(world, x, y, z);
        }
    }

    @Override
    public boolean convertBlock(ItemStack stack, World world, int x, int y, int z, int fromSide) {
        int nutritionLevel = NutritionUtils.getGrassNutritionLevel(world.getBlockMetadata(x, y, z));

        world.setBlockAndMetadataWithNotify(x, y, z, BTWBlocks.looseDirt.blockID, nutritionLevel);

        //only drop hemp seeds at full Nutrition
        if (!world.isRemote && nutritionLevel == NutritionUtils.FULL_NUTRITION_LEVEL) {
            if (world.rand.nextInt(25) == 0) {
                ItemUtils.ejectStackFromBlockTowardsFacing(world, x, y, z, new ItemStack(BTWItems.hempSeeds), fromSide);
            }
        }

        return true;
    }

    @Override
    public boolean isSparse(int metadata) {
        return metadata == 1 || metadata == 3 || metadata == 5 || metadata == 7;
    }

    @Override
    public boolean isSparse(IBlockAccess blockAccess, int x, int y, int z) {
        return isSparse(blockAccess.getBlockMetadata(x, y, z));
    }

    @Override
    public void setSparse(World world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, metadata + 1);
    }

    @Override
    public void setFullyGrown(World world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, metadata - 1);
    }


    public void getSubBlocks(int par1, CreativeTabs tab, List list) {
//        list.add(new ItemStack(par1, 1, 0));
        list.add(new ItemStack(par1, 1, 2));
        list.add(new ItemStack(par1, 1, 4));
        list.add(new ItemStack(par1, 1, 6));

        //Sparse
        list.add(new ItemStack(par1, 1, 1));
        list.add(new ItemStack(par1, 1, 3));
        list.add(new ItemStack(par1, 1, 5));
        list.add(new ItemStack(par1, 1, 7));
    }

    //----------- Client Side Functionality -----------//
    @Environment(EnvType.CLIENT)
    private boolean hasSnowOnTop; // temporary variable used by rendering
    @Environment(EnvType.CLIENT)
    public static boolean secondPass;
    @Environment(EnvType.CLIENT)
    private final Icon[] iconSnowSideArray = new Icon[4];
    @Environment(EnvType.CLIENT)
    private final Icon[] iconDirtArray = new Icon[4];
    @Environment(EnvType.CLIENT)
    private final Icon[] iconGrassTopSparseDirtArray = new Icon[4]; //SCADDON: Changed to protected

    // duplicate variables to parent class to avoid base class modification
    @Environment(EnvType.CLIENT)
    private Icon iconGrassTop;
    @Environment(EnvType.CLIENT)
    private Icon iconGrassTopSparse;
    @Environment(EnvType.CLIENT)
    private Icon iconGrassSideOverlay;

    @Environment(EnvType.CLIENT)
    private Icon iconGrassTopSparseDirt;
    @Environment(EnvType.CLIENT)
    private Icon iconSnowSide;

    @Environment(EnvType.CLIENT)
    private Icon snowTop;

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);

        blockIcon = register.registerIcon(NutritionUtils.DIRT_TEXTURE_NAMES[NutritionUtils.FULL_NUTRITION_LEVEL]);

        iconSnowSideArray[0] = register.registerIcon("snow_side");
        iconSnowSideArray[1] = register.registerIcon("dirt_snow_reduced");
        iconSnowSideArray[2] = register.registerIcon("dirt_snow_low");
        iconSnowSideArray[3] = register.registerIcon("dirt_snow_depleted");

        iconDirtArray[0] = register.registerIcon(NutritionUtils.DIRT_TEXTURE_NAMES[NutritionUtils.FULL_NUTRITION_LEVEL]);
        iconDirtArray[1] = register.registerIcon(NutritionUtils.DIRT_TEXTURE_NAMES[NutritionUtils.REDUCED_NUTRITION_LEVEL]);
        iconDirtArray[2] = register.registerIcon(NutritionUtils.DIRT_TEXTURE_NAMES[NutritionUtils.LOW_NUTRITION_LEVEL]);
        iconDirtArray[3] = register.registerIcon(NutritionUtils.DIRT_TEXTURE_NAMES[NutritionUtils.DEPLETED_NUTRITION_LEVEL]);

        iconGrassTopSparseDirtArray[0] = register.registerIcon(NutritionUtils.SPARSE_DIRT_TEXTURE_NAMES[NutritionUtils.FULL_NUTRITION_LEVEL]);
        iconGrassTopSparseDirtArray[1] = register.registerIcon(NutritionUtils.SPARSE_DIRT_TEXTURE_NAMES[NutritionUtils.REDUCED_NUTRITION_LEVEL]);
        iconGrassTopSparseDirtArray[2] = register.registerIcon(NutritionUtils.SPARSE_DIRT_TEXTURE_NAMES[NutritionUtils.LOW_NUTRITION_LEVEL]);
        iconGrassTopSparseDirtArray[3] = register.registerIcon(NutritionUtils.SPARSE_DIRT_TEXTURE_NAMES[NutritionUtils.DEPLETED_NUTRITION_LEVEL]);

        this.iconGrassTop = register.registerIcon("grass_top");
        this.iconSnowSide = register.registerIcon("snow_side");
        this.iconGrassSideOverlay = register.registerIcon("grass_side_overlay");

        iconGrassTopSparse = register.registerIcon("fcBlockGrassSparse");
        iconGrassTopSparseDirt = register.registerIcon("fcBlockGrassSparseDirt");

        snowTop = register.registerIcon("snow");
    }

    @Override
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if (!secondPass) {
            if (side == 1 && this.isSparse(blockAccess, x, y, z)) {
                return this.iconGrassTopSparseDirtArray[NutritionUtils.getGrassNutritionLevel(blockAccess.getBlockMetadata(x, y, z))];
            } else if (side > 1 && hasSnowOnTop) {
                Icon betterGrassIcon = RenderBlocksUtils.getGrassTexture(this, blockAccess, x, y, z, side, iconGrassTop);

                if (betterGrassIcon != null && betterGrassIcon != iconGrassTop && betterGrassIcon != iconGrassTopSparse) {
                    return betterGrassIcon;
                } else {
                    if (RenderBlocksUtils.enableBetterGrass) {
                        if (isSparse(blockAccess, x, y, z)) {
                            return iconGrassTopSparseDirtArray[NutritionUtils.getGrassNutritionLevel(blockAccess.getBlockMetadata(x, y, z))];
                        } else return snowTop;
                    } else
                        return iconSnowSideArray[NutritionUtils.getGrassNutritionLevel(blockAccess.getBlockMetadata(x, y, z))];
                }
            } else {
                if (side == 0) {
                    return iconDirtArray[NutritionUtils.getGrassNutritionLevel(blockAccess.getBlockMetadata(x, y, z))];
                } else if (side > 1 && RenderBlocksUtils.enableBetterGrass) {
                    return iconGrassTopSparseDirtArray[NutritionUtils.getGrassNutritionLevel(blockAccess.getBlockMetadata(x, y, z))];
                } else
                    return iconSnowSideArray[NutritionUtils.getGrassNutritionLevel(blockAccess.getBlockMetadata(x, y, z))];
            }
        } else {
            return getBlockTextureSecondPass(blockAccess, x, y, z, side);
        }
    }

    public Icon getBlockTextureSecondPass(IBlockAccess blockAccess, int x, int y, int z, int side) {
        Icon topIcon;

        if (isSparse(blockAccess, x, y, z)) {
            topIcon = iconGrassTopSparse;
        } else {
            topIcon = iconGrassTop;
        }

        Icon betterGrassIcon = RenderBlocksUtils.getGrassTexture(this, blockAccess, x, y, z, side, topIcon);

        if (betterGrassIcon != null) {
            return betterGrassIcon;
        } else if (side == 1) {
            return topIcon;
        } else if (side > 1) {
            return this.iconGrassSideOverlay;
        }

        return null;
    }


    @Override
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
        int metadata = blockAccess.getBlockMetadata(x, y, z);

        if (hasSnowOnTop || !secondPass) {
            return 16777215;
        } else {
            if (NutritionUtils.getGrassNutritionLevel(metadata) == NutritionUtils.REDUCED_NUTRITION_LEVEL) {
                return SCRenderUtils.color(blockAccess, x, y, z, 150, -25, 0);
            } else if (NutritionUtils.getGrassNutritionLevel(metadata) == NutritionUtils.LOW_NUTRITION_LEVEL) {
                return SCRenderUtils.color(blockAccess, x, y, z, 300, -50, 0);
            } else if (NutritionUtils.getGrassNutritionLevel(metadata) == NutritionUtils.DEPLETED_NUTRITION_LEVEL) {
                return SCRenderUtils.color(blockAccess, x, y, z, 400, -100, 0);
            } else return SCRenderUtils.color(blockAccess, x, y, z, 0, 0, 0); //NutritionUtils.FULL_NUTRITION_LEVEL
        }
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int neighborX, int neighborY, int neighborZ, int side) {
        BlockPos pos = new BlockPos(neighborX, neighborY, neighborZ, Facing.oppositeSide[side]);

        if (!secondPass) {
            //Don't render dirt under normal grass
            if (side == 1 && !isSparse(blockAccess, pos.x, pos.y, pos.z) && !hasSnowOnTop) {
                //return false;
            }
        } else {
            //Bottom never has a second pass texture
            if (side == 0) {
                return false;
            }
            //Snow has its own texture and should not render the second pass
            else if (side >= 2 && hasSnowOnTop) {
                return RenderBlocksUtils.enableBetterGrass && isSparse(blockAccess, pos.x, pos.y, pos.z);
            }
        }

        Block neighborBlock = Block.blocksList[blockAccess.getBlockId(neighborX, neighborY, neighborZ)];

        if (neighborBlock != null) {
            return neighborBlock.shouldRenderNeighborFullFaceSide(blockAccess, neighborX, neighborY, neighborZ, side);
        }

        return true;
    }

    @Override
    public void renderBlockAsItem(RenderBlocks renderer, int itemDamage, float brightness) {
        SCRenderUtils.renderGrassBlockAsItem(renderer, this, itemDamage, brightness, iconDirtArray, iconSnowSideArray, iconGrassTop, iconGrassSideOverlay);
    }

    @Override
    public boolean renderBlock(RenderBlocks render, int x, int y, int z) {
        hasSnowOnTop = isSnowCoveringTopSurface(render.blockAccess, x, y, z);
        render.setRenderBounds(0, 0, 0, 1, 1, 1);
        return render.renderStandardBlock(this, x, y, z);
    }

    @Override
    public void renderBlockSecondPass(RenderBlocks renderer, int x, int y, int z, boolean firstPassResult) {
        secondPass = true;
        renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
        renderer.renderStandardBlock(this, x, y, z);
        secondPass = false;
    }

}