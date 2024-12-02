package btw.community.sockthing.sockscrops.mixins;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.world.generators.GrassWorldGen;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.WorldGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(BiomeGenBase.class)
public class BiomeGenBaseMixin {

    @Inject(at = @At("HEAD"), method = "getRandomWorldGenForGrass", cancellable = true)
    public void getRandomWorldGenForGrass(Random rand, CallbackInfoReturnable<WorldGenerator> cir)
    {
        cir.setReturnValue(
                new GrassWorldGen(new int[][]{
                        {Block.tallGrass.blockID, 1},
                        {SCBlocks.shortGrass.blockID, 1}
                })
        );

//        if (rand.nextInt(4) == 0){
//
//        }
//        else cir.setReturnValue( new WorldGenTallGrass(Block.tallGrass.blockID, 1));
    }
}
