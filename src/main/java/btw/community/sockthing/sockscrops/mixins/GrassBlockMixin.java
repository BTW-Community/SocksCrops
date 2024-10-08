package btw.community.sockthing.sockscrops.mixins;

import btw.block.blocks.GrassBlock;
import net.minecraft.src.BlockGrass;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GrassBlock.class)
public abstract class GrassBlockMixin extends BlockGrass {
//    @Shadow(remap = false)
//    public static boolean secondPass;
//    @Shadow(remap = false)
//    private boolean hasSnowOnTop;

    protected GrassBlockMixin(int blockID) {
        super(blockID);
    }


//    @Environment(EnvType.CLIENT)
//    private Icon iconGrassTopSparseDirt;

//    @Environment(EnvType.CLIENT)
//    private Icon iconGrassTopSparse;

//    @Environment(EnvType.CLIENT)
//    private Icon snowTop;

//    @Environment(EnvType.CLIENT)
//    private Icon iconGrassTop;

//    @Environment(EnvType.CLIENT)
//    private Icon iconGrassSideOverlay;

//    @Inject(method = "registerIcons", at = @At(value = "TAIL"))
//    public void registerIcons(IconRegister register, CallbackInfo ci) {
//        iconGrassTop = register.registerIcon("grass_top");
//        iconGrassSideOverlay = register.registerIcon("grass_side_overlay");
//
//        iconGrassTopSparseDirt = register.registerIcon("fcBlockGrassSparseDirt");
//        iconGrassTopSparse = register.registerIcon("fcBlockGrassSparse");
//
//        snowTop = register.registerIcon("snow");
//    }

//    @Environment(EnvType.CLIENT)
//    @Inject(method = "shouldSideBeRendered", at = @At(value = "HEAD"), cancellable = true)
//    public void shouldSideBeRendered(IBlockAccess blockAccess, int neighborX, int neighborY, int neighborZ, int side, CallbackInfoReturnable<Boolean> cir) {
//        GrassBlock thisObject = (GrassBlock) (Object) this;
//        BlockPos pos = new BlockPos(neighborX, neighborY, neighborZ, Facing.oppositeSide[side]);
//
//        if (secondPass) {
//            if (side >= 2 && hasSnowOnTop) {
//                if (RenderBlocksUtils.enableBetterGrass && thisObject.isSparse(blockAccess, pos.x, pos.y, pos.z)) {
//                    cir.setReturnValue(true);
//                }
//            }
//        }
//    }

//    @Environment(EnvType.CLIENT)
//    @Inject(method = "getBlockTexture", at = @At(value = "HEAD"), cancellable = true)
//    public void getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side, CallbackInfoReturnable<Icon> cir) {
//        GrassBlock thisObject = (GrassBlock) (Object) this;
//        if (RenderBlocksUtils.enableBetterGrass) {
//            if (thisObject.isSparse(blockAccess, x, y, z) && !secondPass) {
//                cir.setReturnValue(iconGrassTopSparseDirt);
//            }
//        }
//    }

    /*@Environment(EnvType.CLIENT)
    @Inject(method = "getBlockTextureSecondPass", at = @At(value = "HEAD"), cancellable = true)
    public void getBlockTextureSecondPass(IBlockAccess blockAccess, int x, int y, int z, int side, CallbackInfoReturnable<Icon> cir) {
        GrassBlock thisObject = (GrassBlock)(Object)this;

        Icon topIcon;

        if (thisObject.isSparse(blockAccess, x, y, z)) {
            topIcon = iconGrassTopSparse;
            if (SocksCropsAddon.enableBetterSnowyGrass && RenderBlocksUtils.enableBetterGrass && hasSnowOnTop)
            {
                topIcon = snowTopSparse;
            }
        }
        else {
            topIcon = iconGrassTop;
        }

        Icon betterGrassIcon = RenderBlocksUtils.getGrassTexture(this, blockAccess, x, y, z, side, topIcon);

        if (betterGrassIcon != null) {
            cir.setReturnValue( betterGrassIcon );
        }
        else if (side == 1) {
            cir.setReturnValue( topIcon );
        }
        else if (side > 1) {
            if (SocksCropsAddon.enableBetterSnowyGrass && RenderBlocksUtils.enableBetterGrass && hasSnowOnTop)
            {
                cir.setReturnValue(  snowTopSparse );
            }
            else cir.setReturnValue( iconGrassSideOverlay );
        }

        cir.setReturnValue( null );
    }*/
}
