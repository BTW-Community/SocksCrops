package btw.community.sockthing.sockscrops.mixins;

import btw.community.sockthing.sockscrops.utils.WorldGenUtils;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenTrees;
import net.minecraft.src.WorldGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(WorldGenTrees.class)
public abstract class WorldGenTreesMixin extends WorldGenerator {
    @Redirect(
            method = "generate",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/src/World;getBlockId(III)I", // Target the getBlockId method
                    ordinal = 2
            )
    )
    private int redirectGetBlockIdForLeafPlacement(World par1World, int var11, int var8, int var13) {
        // Instead of checking Block.opaqueCubeLookup, use the WorldGenUtils method
        if (WorldGenUtils.canLeavesBePlaced(par1World, var11, var8, var13)) {
            return 0;  // Simulate an air block if the leaves can be placed
        }
        return par1World.getBlockId(var11, var8, var13);  // Otherwise return the original block ID
    }
}