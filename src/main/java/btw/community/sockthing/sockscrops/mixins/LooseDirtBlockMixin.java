package btw.community.sockthing.sockscrops.mixins;

import btw.block.BTWBlocks;
import btw.block.blocks.FallingFullBlock;
import btw.block.blocks.GrassBlock;
import btw.block.blocks.LooseDirtBlock;
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

@Mixin(LooseDirtBlock.class)
public abstract class LooseDirtBlockMixin extends FallingFullBlock {

    public LooseDirtBlockMixin(int iBlockID, Material material) {
        super(iBlockID, material);
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

    @Inject(method = "onVegetationAboveGrazed",
            at = @At(
                    value = "INVOKE",
                    target = "Lbtw/block/blocks/LooseDirtBlock;notifyNeighborsBlockDisrupted(Lnet/minecraft/src/World;III)V",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            ),
            cancellable = true)
    public void onVegetationAboveGrazed(World world, int x, int y, int z, EntityAnimal animal, CallbackInfo ci) {
        int nutritionLevel = world.getBlockMetadata(x, y, z);
        world.setBlockAndMetadataWithNotify(x, y, z, BTWBlocks.looseDirt.blockID, nutritionLevel);
    }

    @Inject(method = "convertBlock", at = @At(value = "HEAD"), cancellable = true)
    public void convertBlock(ItemStack stack, World world, int x, int y, int z, int iFromSide, CallbackInfoReturnable<Boolean> cir) {
        int nutritionLevel = world.getBlockMetadata(x, y, z);

        if (nutritionLevel == NutritionUtils.FULL_NUTRITION_LEVEL)
            world.setBlockAndMetadataWithNotify(x, y, z, SCBlocks.farmlandFullNutrition.blockID, 0);
        else if (nutritionLevel == NutritionUtils.REDUCED_NUTRITION_LEVEL)
            world.setBlockAndMetadataWithNotify(x, y, z, SCBlocks.farmlandReducedNutrition.blockID, 0);
        else if (nutritionLevel == NutritionUtils.LOW_NUTRITION_LEVEL)
            world.setBlockAndMetadataWithNotify(x, y, z, SCBlocks.farmlandLowNutrition.blockID, 0);
        else world.setBlockAndMetadataWithNotify(x, y, z, SCBlocks.farmlandDepletedNutrition.blockID, 0);

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

        blockIcon = register.registerIcon(NutritionUtils.LOOSE_DIRT_TEXTURE_NAMES[NutritionUtils.FULL_NUTRITION_LEVEL]);

        for (int i = 0; i < nutritionIcons.length; i++) {
            nutritionIcons[i] = register.registerIcon(NutritionUtils.LOOSE_DIRT_TEXTURE_NAMES[i]);
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
