package btw.community.sockthing.sockscrops.mixins;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BiomeGenJungle.class)
public abstract class BiomeGenJungleMixin extends BiomeGenBase {

    protected BiomeGenJungleMixin(int par1) {
        super(par1);
    }

//    @Inject(method = "decorate", at = @At(value = "TAIL"))
//    public void decorate(World world, Random random, int x, int z, CallbackInfo ci) {
//        int xPos = x + random.nextInt(16) + 8;
//        int zPos = z + random.nextInt(16) + 8;
//        int yPos = world.getHeightValue(xPos, zPos) + 1;
//
//        WorldGenerator generator = new FallenLogWorldGen(this);
//        generator.generate(world, random, xPos, yPos, zPos);
//    }
}
