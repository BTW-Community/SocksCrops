package net.minecraft.src;

public class SCItemRice extends FCItemPlacesAsBlock
{
    public SCItemRice(int itemID)
    {        
        super( itemID, SCDefs.riceCrop.blockID );
        
        this.setCreativeTab(CreativeTabs.tabFood);
        this.SetFilterableProperties( Item.m_iFilterable_Fine );
        this.setUnlocalizedName("SCItemRice");
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

                if (par2World.getBlockMaterial(var5, var6, var7) == Material.water && par2World.getBlockMaterial(var5, var6 - 1, var7) == Material.ground && par2World.getBlockMetadata(var5, var6, var7) == 0 && par2World.isAirBlock(var5, var6 + 1, var7))
                {
                    par2World.setBlock(var5, var6 + 1, var7, SCDefs.riceCrop.blockID);
                    
                    par2World.playSoundEffect( var5 + 0.5D, var6 + 0.5D, var7 + 0.5D, 
		                	Block.soundGrassFootstep.getPlaceSound(), 
		                	( Block.soundGrassFootstep.getPlaceVolume() + 1F ) / 2F, 
		                	Block.soundGrassFootstep.getPlacePitch() * 0.8F );
                    
                    if (!par3EntityPlayer.capabilities.isCreativeMode)
                    {
                        --par1ItemStack.stackSize;
                    }
                }
            }

            return par1ItemStack;
        }
    }
}