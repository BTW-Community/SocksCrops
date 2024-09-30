package btw.community.sockthing.sockscrops.mixin;

import btw.block.BTWBlocks;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.blocks.*;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Inject(method = "spreadGrassToBlock", at = @At(value = "HEAD"), cancellable = true)
    public void spreadGrassToBlock(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {

        int farmlandID =
                world.getBlockId(x, y, z);

        Block farmland = Block.blocksList[farmlandID];

        if (farmland instanceof FarmlandFullNutritionBlock) {
            world.setBlockAndMetadataWithNotify(x, y, z, BTWBlocks.looseDirt.blockID, 0);
            cir.setReturnValue(true);
        } else if (farmland instanceof FarmlandReducedNutritionBlock) {
            world.setBlockAndMetadataWithNotify(x, y, z, BTWBlocks.looseDirt.blockID, 1);
            cir.setReturnValue(true);
        } else if (farmland instanceof FarmlandLowNutritionBlock) {
            world.setBlockAndMetadataWithNotify(x, y, z, BTWBlocks.looseDirt.blockID, 2);
            ((GrassNutritionBlock) SCBlocks.grassNutrition).setSparse(world, x, y, z);
            cir.setReturnValue(true);
        } else if (farmland instanceof FarmlandDepletedNutritionBlock) {
            world.setBlockAndMetadataWithNotify(x, y, z, BTWBlocks.looseDirt.blockID, 3);
            cir.setReturnValue(true);
        }
    }
}
