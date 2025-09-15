package btw.community.sockthing.sockscrops.mixins;

import btw.block.BTWBlocks;
import btw.client.fx.BTWEffectManager;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.tileentities.CookingPotTileEntity;
import btw.community.sockthing.sockscrops.utils.CookingPotUtils;
import btw.entity.mob.CowEntity;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin {

    @Shadow(remap = false) public abstract boolean gotMilk();

    @Shadow(remap = false) protected abstract void setGotMilk(boolean bGotMilk);

    @Inject(method = "interact", at = @At(value = "HEAD"), cancellable = true)
    public void fillCookingPotWithMilk(EntityPlayer player, CallbackInfoReturnable<Boolean> cir){
        CowEntity thisCow = (CowEntity)(Object)this;

        ItemStack stack = player.inventory.getCurrentItem();

        if ( stack != null && stack.itemID == SCBlocks.cookingPot.blockID )
        {
            if ( gotMilk() )
            {

                CookingPotUtils.setLiquidStack(stack, new ItemStack(BTWBlocks.milkFluid, 3));
                stack.setItemDamage(CookingPotTileEntity.MILK);

                thisCow.attackEntityFrom( DamageSource.generic, 0 );

                if ( !thisCow.worldObj.isRemote )
                {
                    setGotMilk(false);

                    thisCow.worldObj.playAuxSFX( BTWEffectManager.COW_MILKING_EFFECT_ID,
                            MathHelper.floor_double( thisCow.posX ), (int)thisCow.posY,
                            MathHelper.floor_double( thisCow.posZ ), 0 );
                }
            }
            else if (thisCow.worldObj.getDifficulty().canMilkingStartleCows())
            {
                thisCow.attackEntityFrom( DamageSource.causePlayerDamage( player ), 0 );
            }

            cir.setReturnValue(true);
        }
    }
}
