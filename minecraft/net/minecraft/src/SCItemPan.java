package net.minecraft.src;

import java.util.List;

public class SCItemPan extends Item
{    

    public SCItemPan(int par1)
    {
        super(par1);
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
            else if (!SCDefs.pan.canPlaceBlockAt(world, x, y, z) )
            {
                return false;
            }
            else
            {

                int rot = MathHelper.floor_double((double)(player.rotationYaw * 8.0F / 360.0F) + 0.5D) & 7;
                TileEntity pan;
                

            	
            	if (campfire != null && campfire instanceof FCTileEntityCampfire)
                {
            		world.setBlock(x, y + 1, z, SCDefs.pan.blockID, 2, 3);
            		
            		pan = world.getBlockTileEntity(x, y + 1, z);
                }
            	// FCMOD: Changed to notify neighbors
                //par3World.setBlock(par4, par5, par6, Block.skull.blockID, par7, 2);
            	else
            	{
            		 world.setBlock(x, y + 1, z, SCDefs.pan.blockID, 1, 3);
            		 
            		 pan = world.getBlockTileEntity(x, y + 1, z);
            	}
                
                if (pan != null && pan instanceof SCTileEntityPan)
                {
                	((SCTileEntityPan)pan).setSkullRotation(rot);
                }
                
            	// END FCMOD

                


                --itemStack.stackSize;
                return true;
            }
        }
    }

}
