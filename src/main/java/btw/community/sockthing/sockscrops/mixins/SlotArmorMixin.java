package btw.community.sockthing.sockscrops.mixins;

import btw.community.sockthing.sockscrops.interfaces.BlockInterface;
import btw.community.sockthing.sockscrops.interfaces.ItemInterface;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlotArmor.class)
public abstract class SlotArmorMixin extends Slot {
    @Shadow private int armorType;

    public SlotArmorMixin(IInventory par1IInventory, int par2, int par3, int par4) {
        super(par1IInventory, par2, par3, par4);
    }

    @Inject(method = "isItemValid", at = @At(value = "HEAD"), cancellable = true)
    public void isItemValid(ItemStack par1ItemStack, CallbackInfoReturnable<Boolean> cir)
    {
        cir.setReturnValue(isValidItem(par1ItemStack));
    }

    private boolean isValidItem(ItemStack itemStack)
    {
        if (itemStack == null)
        {
            return false;
        }
        else {
            if (itemStack.getItem() instanceof ItemArmor)
            {
                return ((ItemArmor)itemStack.getItem()).armorType == this.armorType;
            }
            else {
                int itemID = itemStack.getItem().itemID;

                if (itemID == Block.pumpkin.blockID || itemID == Item.skull.itemID )
                {
                    return this.armorType == 0;
                }
                else {

                    if ( !(itemStack.getItem() instanceof ItemBlock))
                    {
                        Item item = itemStack.getItem();

                        if (((ItemInterface)item).isValidForArmorSlot(0, itemStack) )
                        {
                            return ((ItemInterface)item).isValidForArmorSlot(this.armorType, itemStack);
                        }

                    }
                    else if ( Block.blocksList[itemStack.itemID] != null )
                    {
                        Block block = Block.blocksList[itemStack.itemID];
                        if (((BlockInterface)block).isValidForArmorSlot(0, itemStack) )
                        {
                            return  ((BlockInterface)block).isValidForArmorSlot(this.armorType, itemStack);
                        }
                    }

                    return false;
                }
            }
        }
    }
}
