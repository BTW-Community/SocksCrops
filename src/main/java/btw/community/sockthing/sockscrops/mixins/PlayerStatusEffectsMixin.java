package btw.community.sockthing.sockscrops.mixins;

import btw.util.status.BTWStatusCategory;
import btw.util.status.PlayerStatusEffects;
import btw.util.status.StatusEffectBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static btw.util.status.PlayerStatusEffects.*;

@Mixin(PlayerStatusEffects.class)
public abstract class PlayerStatusEffectsMixin {

    @Inject(method = "createFatEffect", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private static void fixFatEffect(int level, float effectivenessMultiplier, String name, CallbackInfoReturnable<StatusEffectBuilder> cir) {
        cir.setReturnValue( createExhaustionEffect(level, BTWStatusCategory.FAT, effectivenessMultiplier, name)
                .setEvaluator(player -> {
                    if (player.capabilities.isCreativeMode) {
                        return false;
                    }

                    float fatStart = FAT_EFFECT_START + player.worldObj.getDifficulty().getStatusEffectOffset();

                    float minFat = fatStart + player.worldObj.getDifficulty().getStatusEffectStageGap() * FAT_SCALAR * (level - 1);
                    float maxFat = fatStart + player.worldObj.getDifficulty().getStatusEffectStageGap() * FAT_SCALAR * level;

                    float fatLevel = player.foodStats.getSaturationLevel();


                    return maxFat >= fatLevel && minFat < fatLevel;
                })
        );
    }


}