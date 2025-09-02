package btw.community.sockthing.sockscrops.item.items;

import btw.block.tileentity.CampfireTileEntity;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.tileentities.CookingPotTileEntity;
import btw.community.sockthing.sockscrops.utils.CookingPotUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

public class CookingPotItemBlock extends ItemBlock {
    public CookingPotItemBlock(int itemID) {
        super(itemID);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
    }


    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int meta, float par8, float par9, float par10)
    {
        if (meta == 0)
        {
            return false;
        }
        else
        {
            TileEntity campfire = world.getBlockTileEntity(x, y, z);

            // Code added to prevent the player from placing blocks while in mid air
            if (!player.canPlayerEdit(x, y, z, meta, itemStack))
            {
                return false;
            }
            else if (!SCBlocks.cookingPot.canPlaceBlockAt(world, x, y, z) )
            {
                return false;
            }
            else
            {

                int rot = MathHelper.floor_double((double)(player.rotationYaw * 8.0F / 360.0F) + 0.5D) & 7;
                TileEntity te;

                System.out.println(rot);

                if (campfire != null && campfire instanceof CampfireTileEntity)
                {
                    world.setBlock(x, y + 1, z, SCBlocks.cookingPot.blockID, 2, 3);

                    te = world.getBlockTileEntity(x, y + 1, z);
                    ((CookingPotTileEntity)te).setOnCampfire(true);
                }
                // FCMOD: Changed to notify neighbors
                //par3World.setBlock(par4, par5, par6, Block.skull.blockID, par7, 2);
                else
                {
                    world.setBlock(x, y + 1, z, SCBlocks.cookingPot.blockID, 1, 3);

                    te = world.getBlockTileEntity(x, y + 1, z);
                    ((CookingPotTileEntity)te).setOnCampfire(false);
                }

                if (te instanceof CookingPotTileEntity) {
                    CookingPotTileEntity potTE = (CookingPotTileEntity) te;
                    potTE.setSkullRotation(rot);

                    if (itemStack.hasTagCompound() && itemStack.stackTagCompound.hasKey("Items")) {
                        NBTTagList tagList = itemStack.stackTagCompound.getTagList("Items");

                        for (int i = 0; i < tagList.tagCount(); i++) {
                            NBTTagCompound itemTag = (NBTTagCompound) tagList.tagAt(i);
                            int slot = itemTag.getByte("Slot") & 0xFF;

                            if (slot >= 0 && slot < 6) {
                                ItemStack stack = ItemStack.loadItemStackFromNBT(itemTag);
                                if (stack != null) {
                                    potTE.setCookStack(slot, stack);
                                }
                            }
                        }
                    }
                }

                --itemStack.stackSize;
                return true;
            }
        }
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {

        if (itemStack.stackTagCompound != null) {
            int count = getCount(itemStack);  // however you calculate servings
            String name = getName(itemStack);

            if (count > 0) {
                list.add("Contains " + count + " Servings of " + name);
            } else {
                list.add("Contains nothing");
            }
        }
    }

    private int getCount(ItemStack stack) {
        if (stack == null || stack.stackTagCompound == null) return 0;

        int total = 0;

        if (stack.stackTagCompound.hasKey("Items")) {
            NBTTagList tagList = stack.stackTagCompound.getTagList("Items");

            for (int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound itemTag = (NBTTagCompound) tagList.tagAt(i);
                ItemStack innerStack = ItemStack.loadItemStackFromNBT(itemTag);
                if (innerStack != null) {
                    total += innerStack.stackSize;
                }
            }
        }

        return total;
    }

    private String getName(ItemStack stack)
    {

        if (stack.stackTagCompound != null)
        {
            int id = 0;
            int count = 0;
            int damage = 0;

            if (stack.stackTagCompound.hasKey("id") )
            {
                id = stack.stackTagCompound.getInteger("id");
            }

            if (stack.stackTagCompound.hasKey("Count") )
            {
                count = stack.stackTagCompound.getInteger("Count");

            }

            if (stack.stackTagCompound.hasKey("Damage") )
            {
                damage = stack.stackTagCompound.getInteger("Damage");
            }


            if (id != 0) return new ItemStack(id, count, damage).getDisplayName();

        }

        return "N/A";
    }

    private String getNames(ItemStack stack) {
        if (stack == null || stack.stackTagCompound == null) {
            return "N/A";
        }

        if (stack.stackTagCompound.hasKey("Items")) {
            NBTTagList tagList = stack.stackTagCompound.getTagList("Items");

            List<String> names = new ArrayList<String>();

            for (int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound itemTag = (NBTTagCompound) tagList.tagAt(i);
                ItemStack innerStack = ItemStack.loadItemStackFromNBT(itemTag);

                if (innerStack != null) {
                    names.add(innerStack.getDisplayName());
                }
            }

            if (!names.isEmpty()) {
                // Join names with commas
                return String.join(", ", names);
            }
        }

        return "N/A";
    }
}
