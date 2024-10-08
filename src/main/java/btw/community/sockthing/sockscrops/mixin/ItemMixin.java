package btw.community.sockthing.sockscrops.mixin;

import btw.community.sockthing.sockscrops.interfaces.ItemInterface;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin implements ItemInterface {
    /**
     * Used to allow Blocks to be placed in a specific armorSlot
     * @param armorType 0: Helmet, 1: Chest, 2: Legs, 3: boots
     * @param itemStack
     */
    @Override
    public boolean isValidForArmorSlot(int armorType, ItemStack itemStack) {
        return false;
    }

    /**
     * Example Pumpkin: "%blur%/misc/pumpkinblur.png"
     * @return Returns the directory string of the blur overlay texture that should be used when this is worn in the helmet slot
     */
    @Override
    public String getBlurOverlay(ItemStack itemStack) {
        return null;
    }

    /**
     * Returns true or false depending if the blur overlay should be shown when the player disabled the GUI
     */
    @Override
    public boolean showBlurOverlayWithGuiDisabled(ItemStack itemStack) {
        return false;
    }
}
