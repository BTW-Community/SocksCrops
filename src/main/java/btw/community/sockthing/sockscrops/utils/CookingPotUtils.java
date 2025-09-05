package btw.community.sockthing.sockscrops.utils;

import btw.community.sockthing.sockscrops.block.blocks.CookingPotBlock;
import btw.community.sockthing.sockscrops.block.tileentities.CookingPotTileEntity;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

public class CookingPotUtils {

    public static boolean setLiquidStack(ItemStack itemStack, ItemStack liquidStack) {
        if (itemStack != null) {

            NBTTagCompound root = itemStack.hasTagCompound()
                    ? itemStack.getTagCompound()
                    : new NBTTagCompound();

            //Don't fill the cookingPot if it already has a liquid
            if ( root.hasKey("liquidStack") ) return false;

            if ( !root.hasKey("liquidStack")){
                NBTTagCompound liquidTag = new NBTTagCompound();
                liquidStack.writeToNBT(liquidTag);
                root.setTag("liquidStack", liquidTag);
                itemStack.setTagCompound(root);
                return true;
            }
        }
        return false;
    }

    // Pack all three values into itemDamage
    public static int packItemData(boolean hasLabel, int fillHeight, int fillType) {
        fillHeight -= 1; //-1 since we save 1-4 as 0-3

        int damage = 0;
        damage |= (hasLabel ? 1 : 0);           // bit 0
        damage |= (fillHeight & 0b111) << 1;    // bits 1-3
        damage |= (fillType & 0b11111) << 4;    // bits 4-8
        return damage;
    }

    // Unpack hasLabel
    public static boolean unpackHasLabel(int damage) {
        return (damage & 0b1) != 0;
    }

    // Unpack fillHeight (0-3)
    public static int unpackFillHeight(int damage) {
        return ((damage >> 1) & 0b111) + 1; //+1 since height is 1-4
    }

    // Unpack fillType (0-31)
    public static int unpackFillType(int damage) {
        return (damage >> 4) & 0b11111;
    }

}
