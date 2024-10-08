package btw.community.sockthing.sockscrops.mixin;

import btw.block.BTWBlocks;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.blocks.*;
import btw.community.sockthing.sockscrops.interfaces.BlockInterface;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin implements BlockInterface {


    /**
     * Used to allow Blocks to be placed in a specific armorSlot
     * @param armorType 0: Helmet, 1: Chest, 2: Legs, 3: boots
     * @param itemStack
     */
    @Override
    public boolean isValidForArmorSlot(int armorType, ItemStack itemStack) {
        return false;
    }

    /**
     * Example Pumpkin: "%blur%/misc/pumpkinblur.png"
     * @return Returns the directory string of the blur overlay texture that should be used when this is worn in the helmet slot
     */
    @Override
    public String getBlurOverlay(ItemStack itemStack) {
        return null;
    }

    /**
     * Returns true or false depending if the blur overlay should be shown when the player disabled the GUI
     */
    @Override
    public boolean showBlurOverlayWithGuiDisabled(ItemStack itemStack) {
        return false;
    }

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
