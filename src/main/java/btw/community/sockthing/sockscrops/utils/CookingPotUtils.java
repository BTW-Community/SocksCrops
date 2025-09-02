package btw.community.sockthing.sockscrops.utils;

import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

public class CookingPotUtils {

    public static void setSoupStack(ItemStack stack, ItemStack cookStack) {
        if ( cookStack != null)
        {
            NBTTagCompound fishTag = new NBTTagCompound();

            cookStack.writeToNBT( fishTag );

            stack.stackTagCompound.setCompoundTag( "fishStack", fishTag );
        }
    }

    public static ItemStack getSoupStack(ItemStack stack){
        if (stack != null && stack.hasTagCompound()) {
            NBTTagCompound fishTag = stack.stackTagCompound.getCompoundTag( "fishStack" );

            if ( fishTag != null )
            {
                return ItemStack.loadItemStackFromNBT( fishTag );
            }
        }

        return null;
    }
}
