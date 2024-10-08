package btw.community.sockthing.sockscrops.mixins;

import btw.block.blocks.PlantsBlock;
import btw.block.blocks.WeedsBlock;
import btw.community.sockthing.sockscrops.block.blocks.FarmlandBaseBlock;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WeedsBlock.class)
public abstract class WeedsBlockMixin extends PlantsBlock {
    protected WeedsBlockMixin(int blockID, Material material) {
        super(blockID, material);
    }

    @Inject(method = "canGrowOnBlock", at = @At(value = "HEAD"), cancellable = true)
    protected void canGrowOnBlock(World world, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        int blockOnID = world.getBlockId(i, j, k);

        cir.setReturnValue(Block.blocksList[blockOnID] instanceof FarmlandBaseBlock);
    }

}
