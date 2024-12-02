package btw.community.sockthing.sockscrops.interfaces;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;

public interface BlockInterface {

    public boolean[] replaceableByLeavesLookup = new boolean[4096];
    /**
     * Used to allow Blocks to be placed in a specific armorSlot
     * @param armorType 0: Helmet, 1: Chest, 2: Legs, 3: boots
     * @param itemStack
     */
    default boolean isValidForArmorSlot(int armorType, ItemStack itemStack)  {
        return false;
    }

    /**
     * Example Pumpkin: "%blur%/misc/pumpkinblur.png"
     * @return Returns the directory string of the blur overlay texture that should be used when this is worn in the helmet slot
     */
    default String getBlurOverlay(ItemStack itemStack) {
        return null;
    }

    /**
     * Returns true or false depending if the blur overlay should be shown when the player disabled the GUI
     */
    default boolean showBlurOverlayWithGuiDisabled(ItemStack itemStack) {
        return false;
    }

    /**
     * If leaves can replace this Block. Used when generating leaves for trees on world gen and tree growth
     */
    default boolean canBeReplacedByLeaves(int blockID)
    {
        return !Block.opaqueCubeLookup[blockID];
    }
}
