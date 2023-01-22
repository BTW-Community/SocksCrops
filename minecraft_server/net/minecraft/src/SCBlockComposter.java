package net.minecraft.src;

import java.awt.Color;
import java.util.Random;

public class SCBlockComposter extends BlockContainer {
	
	protected static final SCModelBlockComposter model = new SCModelBlockComposter();
	
	protected SCBlockComposter(int blockID) {
        super(blockID, Material.wood);
        
        this.setHardness( 1.0F );
        
        this.SetAxesEffectiveOn();
        
        this.SetBuoyant();
        
        this.setStepSound( soundWoodFootstep );
        
        SetFireProperties( FCEnumFlammability.PLANKS );
        
        this.setUnlocalizedName("SCBlockComposter");
        
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new SCTileEntityComposter();
	}
	
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
		ItemStack heldStack = player.getCurrentEquippedItem();
		SCTileEntityComposter composter = (SCTileEntityComposter)world.getBlockTileEntity( i, j, k );
		
		if (heldStack != null && composter.isValidItem(heldStack.itemID))
		{
			composter.addStackToComposter(heldStack);
			
			composter.markBlockForUpdate();
			
			composter.UpdateCooking();
			
			return true;
		}
		else if ( heldStack == null && world.getBlockMetadata(i, j, k) == 15 )
		{
			
			composter.setFillLevel(0);
			composter.setCookCounter(0);
							
			world.setBlockMetadata(i, j, k, 0);
			
			if (!world.isRemote)
			{
				FCUtilsItem.EjectStackFromBlockTowardsFacing(world, i, j, k, new ItemStack(SCDefs.compostBlock, 1), iFacing);
			}
			
			world.markBlockForUpdate(i, j, k);
			
			return true;
			
		}

		return false;
		
    }
			
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

	//----------- Client Side Functionality -----------//
	
}
