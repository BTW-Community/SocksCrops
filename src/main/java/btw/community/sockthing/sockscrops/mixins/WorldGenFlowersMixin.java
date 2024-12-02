package btw.community.sockthing.sockscrops.mixins;

import btw.community.sockthing.sockscrops.block.blocks.FarmlandBaseBlock;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenFlowers;
import net.minecraft.src.WorldGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(WorldGenFlowers.class)
public abstract class WorldGenFlowersMixin extends WorldGenerator {

    @Shadow private int plantBlockId;

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int var6 = 0; var6 < 64; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (par1World.isAirBlock(var7, var8, var9) && (!par1World.provider.hasNoSky || var8 < 127) &&
                    Block.blocksList[this.plantBlockId].canBlockStayDuringGenerate(par1World, var7, var8, var9))
            {
                if (this.plantBlockId == Block.plantYellow.blockID){
                    if (par2Random.nextFloat() <= 1/8F) {
                        par1World.setBlock(var7, var8, var9, this.plantBlockId, 1, 2);
                    }
                    else par1World.setBlock(var7, var8, var9, this.plantBlockId, 0, 2);
                }
                else par1World.setBlock(var7, var8, var9, this.plantBlockId, 0, 2);
            }
        }

        return true;
    }


}
