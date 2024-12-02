package btw.community.sockthing.sockscrops.item.items;

import net.minecraft.src.*;

public class WaterPlantItem extends ItemMultiTextureTile {
    private Block waterPlant;

    public WaterPlantItem(int var1, Block block, String[] array) {
        super(var1, block, array);
        this.waterPlant = block;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);

        if (var4 == null)
        {
            return par1ItemStack;
        }
        else
        {
            if (var4.typeOfHit == EnumMovingObjectType.TILE)
            {
                int var5 = var4.blockX;
                int var6 = var4.blockY;
                int var7 = var4.blockZ;

                if (!par2World.canMineBlock(par3EntityPlayer, var5, var6, var7))
                {
                    return par1ItemStack;
                }

                if (!par3EntityPlayer.canPlayerEdit(var5, var6, var7, var4.sideHit, par1ItemStack))
                {
                    return par1ItemStack;
                }

                if (par2World.getBlockMaterial(var5, var6, var7) == Material.water && par2World.getBlockMetadata(var5, var6, var7) == 0 && par2World.isAirBlock(var5, var6 + 1, var7))
                {
                    if (Block.blocksList[par2World.getBlockId(var5, var6 - 1, var7) ].isBlockSolid(par2World,var5,var6 - 1,var7,1))
                    {
                        par2World.setBlockAndMetadata(var5, var6 + 1, var7, waterPlant.blockID, par1ItemStack.getItemDamage());

                        if (!par3EntityPlayer.capabilities.isCreativeMode)
                        {
                            --par1ItemStack.stackSize;
                        }
                    }

                }
            }

            return par1ItemStack;
        }
    }
}