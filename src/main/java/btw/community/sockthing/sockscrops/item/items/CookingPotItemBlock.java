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

                if (te != null && te instanceof CookingPotTileEntity)
                {
                    ((CookingPotTileEntity)te).setSkullRotation(rot);

                    if ( itemStack.hasTagCompound() )
                    {
                        int id = 0;
                        int count = 0;
                        int damage = 0;

                        if (itemStack.stackTagCompound.hasKey("id") )
                        {
                            id = itemStack.stackTagCompound.getInteger("id");
                        }

                        if (itemStack.stackTagCompound.hasKey("Count") )
                        {
                            count = itemStack.stackTagCompound.getInteger("Count");

                        }

                        if (itemStack.stackTagCompound.hasKey("Damage") )
                        {
                            damage = itemStack.stackTagCompound.getInteger("Damage");
                        }

                        if (id != 0) ((CookingPotTileEntity) te).setCookStack(new ItemStack(id, count, damage));
                    }

//                    System.out.println( ((CookingPotTileEntity)te).getSkullRotation() );
                }

                // END FCMOD




                --itemStack.stackSize;
                return true;
            }
        }
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {

        if (itemStack.stackTagCompound != null)
        {
            int count = getCount(itemStack);
            String name = getName(itemStack);
            if (count != 0) list.add("Contains " + count + " Servings of " + name);
            else list.add("Contains nothing");
        }
    }

    private int getCount(ItemStack stack)
    {
        if (stack.stackTagCompound != null  && stack.stackTagCompound.hasKey("Count"))
        {
            return stack.stackTagCompound.getInteger("Count");
        }

        else return 0;
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
}
