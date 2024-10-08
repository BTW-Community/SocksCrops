package btw.community.sockthing.sockscrops.mixins;

import btw.block.blocks.FarmlandBlock;
import btw.block.blocks.FarmlandBlockBase;
import btw.community.sockthing.sockscrops.utils.NutritionUtils;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FarmlandBlock.class)
public abstract class FarmlandBlockMixin extends FarmlandBlockBase {

    protected FarmlandBlockMixin(int blockID) {
        super(blockID);
    }

    @Override
    public boolean getCanGrassSpreadToBlock(World world, int i, int j, int k) {
        boolean hasGrassAbove = NutritionUtils.isValidPlantAbove(world, i, j + 1, k);

        if (hasGrassAbove) {
            return true;
        }

        return super.getCanGrassSpreadToBlock(world, i, j, k);
    }
}
