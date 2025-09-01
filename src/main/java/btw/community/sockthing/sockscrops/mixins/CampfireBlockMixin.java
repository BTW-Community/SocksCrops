package btw.community.sockthing.sockscrops.mixins;

import btw.block.blocks.CampfireBlock;
import btw.block.tileentity.CampfireTileEntity;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin {

    @Shadow public abstract boolean getCanBeSetOnFireDirectly(IBlockAccess blockAccess, int i, int j, int k);

    @Shadow public abstract boolean isRainingOnCampfire(World world, int i, int j, int k);

    @Shadow public abstract void changeFireLevel(World world, int i, int j, int k, int iFireLevel, int iMetadata);

    @Inject(method = "setOnFireDirectly", at = @At(value = "HEAD"), cancellable = true)
    public void setOnFireDirectly(World world, int i, int j, int k, CallbackInfoReturnable<Boolean> cir){
        if ( getCanBeSetOnFireDirectly(world, i, j, k) ) {
            if (!isRainingOnCampfire(world, i, j, k)) {
                changeFireLevel(world, i, j, k, 1, world.getBlockMetadata(i, j, k));

                CampfireTileEntity tileEntity = (CampfireTileEntity)world.getBlockTileEntity(
                        i, j, k );

                tileEntity.onFirstLit();

                world.playSoundEffect( i + 0.5D, j + 0.5D, k + 0.5D,
                        "mob.ghast.fireball", 1F, world.rand.nextFloat() * 0.4F + 0.8F );

                if ( !Block.portal.tryToCreatePortal( world, i, j, k ) )
                {
                    int blockBelow = world.getBlockId( i, j - 1, k );
                    if (blockBelow == SCBlocks.burnPit.blockID) {
                        world.setBlockWithNotify(i, j, k, Block.fire.blockID);
                    }
                }

            }
        }
    }
}
