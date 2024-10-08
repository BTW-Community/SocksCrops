package btw.community.sockthing.sockscrops.mixin;

import btw.community.sockthing.sockscrops.interfaces.BlockInterface;
import btw.community.sockthing.sockscrops.interfaces.ItemInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GuiIngame.class)
public abstract class GuiIngameMixin extends Gui {

    @Shadow private Minecraft mc;

    @Inject(method = "renderGameOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/src/GuiIngame;renderModSpecificPlayerSightEffects()V",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true)
    public void renderGameOverlay(float par1, boolean par2, int par3, int par4, CallbackInfo ci,
                                  ScaledResolution var5,
                                  int scaledWidth,
                                  int scaledHeight,
                                  FontRenderer var8,
                                  ItemStack itemStack)
    {
        GuiIngame thisObject = (GuiIngame)(Object)this;
        if (this.mc.gameSettings.thirdPersonView == 0 && itemStack != null )
        {
            Item item = itemStack.getItem();
            Block block = Block.blocksList[itemStack.itemID];

            if ( ((ItemInterface)item).isValidForArmorSlot(0, itemStack) )
            {
                if (((ItemInterface)item).getBlurOverlay(itemStack) != null )
                {
                    this.renderPumpkinBlur(scaledWidth, scaledHeight, ((ItemInterface)item).getBlurOverlay(itemStack));
                }

            }
            else if (((BlockInterface)block).isValidForArmorSlot(0, itemStack))
            {
                if ( ((BlockInterface)block).getBlurOverlay(itemStack) != null )
                {
                    this.renderPumpkinBlur(scaledWidth, scaledHeight, ((BlockInterface)block).getBlurOverlay(itemStack));
                }
            }
            else
            {
                // FCMOD: Added (client only)
                thisObject.renderModSpecificPlayerSightEffects();
                // END FCMOD
            }
        }
    }

    @Inject(method = "renderGameOverlayWithGuiDisabled",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/src/GuiIngame;renderModSpecificPlayerSightEffects()V",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true)
    public void renderGameOverlayWithGuiDisabled(float fSmoothCameraPartialTicks, boolean bScreenActive, int iMouseX, int iMouseY, CallbackInfo ci,
                                                ScaledResolution resolution,
                                                 int scaledWidth,
                                                 int scaledHeight,
                                                 FontRenderer fontRenderer,
                                                 ItemStack itemStack)
    {
        if (this.mc.gameSettings.thirdPersonView == 0 && itemStack != null )
        {
            GuiIngame thisObject = (GuiIngame)(Object)this;

            Item item = itemStack.getItem();
            Block block = Block.blocksList[itemStack.itemID];
            boolean blockGUIDisabled = false;
            boolean itemGUIDisabled = false ;

            if (itemStack.getItem() instanceof ItemBlock)
            {
                blockGUIDisabled = ((BlockInterface)block).showBlurOverlayWithGuiDisabled(itemStack);
            }
            else {
                itemGUIDisabled = ((ItemInterface)item).showBlurOverlayWithGuiDisabled(itemStack);
            }

            if (((ItemInterface)item).isValidForArmorSlot(0, itemStack) )
            {
                if (((ItemInterface)item).getBlurOverlay(itemStack) != null )
                {
                    if (itemGUIDisabled)
                    {
                        this.renderPumpkinBlur(scaledWidth, scaledHeight, ((ItemInterface)item).getBlurOverlay(itemStack));
                    }

                }
            }
            else if (((BlockInterface)block).isValidForArmorSlot(0, itemStack))
            {
                if (block != null  && ((BlockInterface)block).getBlurOverlay(itemStack) != null )
                {
                    if (blockGUIDisabled)
                    {
                        this.renderPumpkinBlur(scaledWidth, scaledHeight, ((BlockInterface)block).getBlurOverlay(itemStack));
                    }

                }
            }
            else
            {
                thisObject.renderModSpecificPlayerSightEffects();
            }
        }
    }
    private void renderPumpkinBlur(int par1, int par2, String blurOverlay)
    {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        // SAU: Replace this.mc.renderEngine.bindTexture("%blur%/misc/pumpkinblur.png");
        this.mc.renderEngine.bindTexture(blurOverlay);
        // END SAU
        Tessellator var3 = Tessellator.instance;
        var3.startDrawingQuads();
        var3.addVertexWithUV(0.0D, (double)par2, -90.0D, 0.0D, 1.0D);
        var3.addVertexWithUV((double)par1, (double)par2, -90.0D, 1.0D, 1.0D);
        var3.addVertexWithUV((double)par1, 0.0D, -90.0D, 1.0D, 0.0D);
        var3.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        var3.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
