package btw.community.sockthing.sockscrops.utils;

import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

public class CookingPotUtils {

    public static void setLiquidStack(ItemStack itemStack, ItemStack liquidStack) {
        if (itemStack != null) {
            if ( !itemStack.stackTagCompound.hasKey("liquidStack")){
                //is empty
                NBTTagCompound root = itemStack.hasTagCompound()
                        ? itemStack.getTagCompound()
                        : new NBTTagCompound();

                NBTTagCompound liquidTag = new NBTTagCompound();
                liquidStack.writeToNBT(liquidTag);
                root.setTag("liquidStack", liquidTag);


                itemStack.setTagCompound(root);
            }
        }
    }
}
