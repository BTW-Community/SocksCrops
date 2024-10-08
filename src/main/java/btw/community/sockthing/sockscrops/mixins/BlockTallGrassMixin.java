package btw.community.sockthing.sockscrops.mixins;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.blocks.PlanterBaseBlock;
import btw.community.sockthing.sockscrops.item.SCItems;
import btw.community.sockthing.sockscrops.item.items.KnifeItem;
import btw.community.sockthing.sockscrops.utils.NutritionUtils;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockTallGrass.class)
public abstract class BlockTallGrassMixin extends BlockFlower {

    public BlockTallGrassMixin(int blockID, Material material) {
        super(blockID, material);
    }

    @Inject(method = "harvestBlock", at = @At(value = "HEAD"), cancellable = true)
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int metadata, CallbackInfo ci) {
        if (!world.isRemote && player.getCurrentEquippedItem() != null
                && player.getCurrentEquippedItem().getItem() instanceof KnifeItem) {
            this.dropBlockAsItem_do(world, x, y, z, new ItemStack(SCItems.cuttings, 1, metadata));
        }
    }

    @Inject(method = "colorMultiplier", at = @At(value = "HEAD"), cancellable = true)
    public void colorMultiplier(IBlockAccess blockAccess, int x, int y, int z, CallbackInfoReturnable<Integer> cir) {

        int metaBelow = blockAccess.getBlockMetadata(x, y - 1, z);
        int nutritionLevel;

        if (blockAccess.getBlockId(x, y - 1, z) == Block.grass.blockID
                || blockAccess.getBlockId(x, y - 1, z) == SCBlocks.grassNutrition.blockID) {
            nutritionLevel = NutritionUtils.getGrassNutritionLevel(metaBelow);
        } else if (Block.blocksList[blockAccess.getBlockId(x, y - 1, z)] instanceof PlanterBaseBlock) {
            nutritionLevel = NutritionUtils.getPlanterNutritionLevel(metaBelow);
        } else if (blockAccess.getBlockId(x, y - 1, z) == SCBlocks.farmlandFullNutrition.blockID
                || blockAccess.getBlockId(x, y - 1, z) == SCBlocks.farmlandFertilizedFullNutrition.blockID
                || blockAccess.getBlockId(x, y - 1, z) == SCBlocks.farmlandDungFullNutrition.blockID
                || blockAccess.getBlockId(x, y - 1, z) == SCBlocks.farmlandMulchFullNutrition.blockID) {
            nutritionLevel = NutritionUtils.FULL_NUTRITION_LEVEL;
        } else if (blockAccess.getBlockId(x, y - 1, z) == SCBlocks.farmlandReducedNutrition.blockID
                || blockAccess.getBlockId(x, y - 1, z) == SCBlocks.farmlandFertilizedReducedNutrition.blockID
                || blockAccess.getBlockId(x, y - 1, z) == SCBlocks.farmlandDungReducedNutrition.blockID
                || blockAccess.getBlockId(x, y - 1, z) == SCBlocks.farmlandMulchReducedNutrition.blockID) {
            nutritionLevel = NutritionUtils.REDUCED_NUTRITION_LEVEL;
        } else if (blockAccess.getBlockId(x, y - 1, z) == SCBlocks.farmlandLowNutrition.blockID
                || blockAccess.getBlockId(x, y - 1, z) == SCBlocks.farmlandFertilizedLowNutrition.blockID
                || blockAccess.getBlockId(x, y - 1, z) == SCBlocks.farmlandDungLowNutrition.blockID
                || blockAccess.getBlockId(x, y - 1, z) == SCBlocks.farmlandMulchLowNutrition.blockID) {
            nutritionLevel = NutritionUtils.LOW_NUTRITION_LEVEL;
        } else if (blockAccess.getBlockId(x, y - 1, z) == SCBlocks.farmlandDepletedNutrition.blockID
                || blockAccess.getBlockId(x, y - 1, z) == SCBlocks.farmlandFertilizedDepletedNutrition.blockID
                || blockAccess.getBlockId(x, y - 1, z) == SCBlocks.farmlandDungDepletedNutrition.blockID
                || blockAccess.getBlockId(x, y - 1, z) == SCBlocks.farmlandMulchDepletedNutrition.blockID) {
            nutritionLevel = NutritionUtils.DEPLETED_NUTRITION_LEVEL;
        } else nutritionLevel = NutritionUtils.getNutritionLevel(metaBelow);

        if (nutritionLevel == NutritionUtils.REDUCED_NUTRITION_LEVEL) {
            cir.setReturnValue(SCRenderUtils.color(blockAccess, x, y, z, 150, -25, 0));
        } else if (nutritionLevel == NutritionUtils.LOW_NUTRITION_LEVEL) {
            cir.setReturnValue(SCRenderUtils.color(blockAccess, x, y, z, 300, -50, 0));
        } else if (nutritionLevel == NutritionUtils.DEPLETED_NUTRITION_LEVEL) {
            cir.setReturnValue(SCRenderUtils.color(blockAccess, x, y, z, 400, -100, 0));
        } else
            cir.setReturnValue(SCRenderUtils.color(blockAccess, x, y, z, 0, 0, 0)); //NutritionUtils.FULL_NUTRITION_LEVEL
    }

}
