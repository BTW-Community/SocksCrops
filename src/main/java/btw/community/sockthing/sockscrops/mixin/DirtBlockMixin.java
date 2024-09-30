package btw.community.sockthing.sockscrops.mixin;

import btw.block.BTWBlocks;
import btw.block.blocks.DirtBlock;
import btw.block.blocks.FullBlock;
import btw.block.blocks.GrassBlock;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.blocks.GrassNutritionBlock;
import btw.community.sockthing.sockscrops.utils.NutritionUtils;
import btw.item.BTWItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DirtBlock.class)
public abstract class DirtBlockMixin extends FullBlock {

    public DirtBlockMixin(int iBlockID, Material material) {
        super(iBlockID, material);
    }

    @Override
    public void notifyOfFullStagePlantGrowthOn(World world, int i, int j, int k, Block plantBlock) {
        int meta = world.getBlockMetadata(i, j, k);

        if (meta < 3) {
            // revert back to soil
            world.setBlockAndMetadataWithNotify(i, j, k, this.blockID, meta + 1);
        }
    }

    @Override
    public float getPlantGrowthOnMultiplier(World world, int x, int y, int z, Block plantBlock) {
        return NutritionUtils.getNutritionMultiplier(world.getBlockMetadata(x, y, z));
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata & 3;
    }

    @Inject(method = "dropComponentItemsOnBadBreak", at = @At(value = "HEAD"), cancellable = true)
    public void dropComponentItemsOnBadBreak(World world, int x, int y, int z, int metadata, float fChanceOfDrop, CallbackInfoReturnable<Boolean> cir) {

        int pileCount = 6 - (metadata * 2); //0:6, 1:4, 2:2, 3:0
        dropItemsIndividually(world, x, y, z, BTWItems.dirtPile.itemID, pileCount, 0, fChanceOfDrop);

        cir.setReturnValue(true);
    }

    @Inject(method = "spreadGrassToBlock", at = @At(value = "HEAD"), cancellable = true)
    public void spreadGrassToBlock(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {

        int nutritionLevel = NutritionUtils.getNutritionLevel(world.getBlockMetadata(x, y, z));

        if (nutritionLevel == NutritionUtils.FULL_NUTRITION_LEVEL) {
            world.setBlockWithNotify(x, y, z, Block.grass.blockID);
            ((GrassBlock) Block.grass).setSparse(world, x, y, z);
        } else if (nutritionLevel == NutritionUtils.REDUCED_NUTRITION_LEVEL) {
            world.setBlockAndMetadataWithNotify(x, y, z, SCBlocks.grassNutrition.blockID, 2);
            ((GrassNutritionBlock) SCBlocks.grassNutrition).setSparse(world, x, y, z);
        } else if (nutritionLevel == NutritionUtils.LOW_NUTRITION_LEVEL) {
            world.setBlockAndMetadataWithNotify(x, y, z, SCBlocks.grassNutrition.blockID, 4);
            ((GrassNutritionBlock) SCBlocks.grassNutrition).setSparse(world, x, y, z);
        } else if (nutritionLevel == NutritionUtils.DEPLETED_NUTRITION_LEVEL) {
            world.setBlockAndMetadataWithNotify(x, y, z, SCBlocks.grassNutrition.blockID, 5);
            ((GrassNutritionBlock) SCBlocks.grassNutrition).setSparse(world, x, y, z);
        }

        cir.setReturnValue(true);
    }

    @Inject(method = "onNeighborDirtDugWithImproperTool", at = @At(value = "HEAD"), cancellable = true)
    public void onNeighborDirtDugWithImproperTool(World world, int x, int y, int z, int iToFacing, CallbackInfo ci) {
        int metadata = world.getBlockMetadata(x, y, z);
        world.setBlockAndMetadataWithNotify(x, y, z, BTWBlocks.looseDirt.blockID, metadata);

        ci.cancel();
    }

    @Inject(method = "onVegetationAboveGrazed",
            at = @At(
                    value = "INVOKE",
                    target = "Lbtw/block/blocks/DirtBlock;notifyNeighborsBlockDisrupted(Lnet/minecraft/src/World;III)V",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            ),
            cancellable = true)
    public void onVegetationAboveGrazed(World world, int x, int y, int z, EntityAnimal animal, CallbackInfo ci) {
        int metadata = world.getBlockMetadata(x, y, z);
        world.setBlockAndMetadataWithNotify(x, y, z, BTWBlocks.looseDirt.blockID, metadata);
    }

    @Inject(method = "convertBlock", at = @At(value = "HEAD"), cancellable = true)
    public void convertBlock(ItemStack stack, World world, int x, int y, int z, int iFromSide, CallbackInfoReturnable<Boolean> cir) {
        int metadata = world.getBlockMetadata(x, y, z);
        world.setBlockAndMetadataWithNotify(x, y, z, BTWBlocks.looseDirt.blockID, metadata);

        if (!world.isRemote) {
            world.playAuxSFX(2001, x, y, z, blockID); // block break FX
        }

        cir.setReturnValue(true);
    }

    //----------- Client Side Functionality -----------//

    @Environment(EnvType.CLIENT)
    private final Icon[] nutritionIcons = new Icon[4];

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);

        blockIcon = register.registerIcon(NutritionUtils.DIRT_TEXTURE_NAMES[NutritionUtils.FULL_NUTRITION_LEVEL]);

        for (int i = 0; i < nutritionIcons.length; i++) {
            nutritionIcons[i] = register.registerIcon(NutritionUtils.DIRT_TEXTURE_NAMES[i]);
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Icon getIcon(int side, int metadata) {
        return nutritionIcons[metadata & 3];
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void getSubBlocks(int id, CreativeTabs tabs, List list) {
        list.add(new ItemStack(id, 1, 0));
        list.add(new ItemStack(id, 1, 1));
        list.add(new ItemStack(id, 1, 2));
        list.add(new ItemStack(id, 1, 3));
    }
}
