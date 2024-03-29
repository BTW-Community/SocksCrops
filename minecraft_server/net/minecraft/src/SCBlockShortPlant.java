package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockShortPlant extends BlockFlower {

	private static final double m_dHalfWidth = 0.4F;
	
	public static final int SHORTGRASS = 0;
	public static final int TALLGRASS = 1;
	
	protected SCBlockShortPlant(int par1) {
		super(par1, Material.vine);
		
    	setHardness( 0F );
    	
    	SetBuoyant();
    	
		SetFireProperties( FCEnumFlammability.GRASS );
		
        InitBlockBounds( 0.5D - m_dHalfWidth, 0D, 0.5D - m_dHalfWidth, 
        	0.5D + m_dHalfWidth, 0.4D, 0.5D + m_dHalfWidth);
        
        setCreativeTab(CreativeTabs.tabDecorations);
        
		setStepSound(soundGrassFootstep);
		
		setUnlocalizedName("SCBlockShortPlant");
	}

	
    @Override
    public int idDropped( int iMetadata, Random rand, int iFortuneModifier )
    {
        return -1;
    }
    
    @Override
    public boolean CanSpitWebReplaceBlock( World world, int i, int j, int k )
    {
    	return true;
    }
    
    @Override
    public boolean IsReplaceableVegetation( World world, int i, int j, int k )
    {
    	return true;
    }
	
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		par3List.add(new ItemStack(par1, 1, SHORTGRASS));
		//par3List.add(new ItemStack(par1, 1, TALLGRASS));
    }
	
    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World world, EntityPlayer player, int i, int j, int k, int meta)
    {
        if (!world.isRemote && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof FCItemShears && meta != TALLGRASS)
        {
            player.addStat(StatList.mineBlockStatArray[this.blockID], 1);
            player.getHeldItem().damageItem(1, player);
            
            this.dropBlockAsItem_do(world, i, j, k, new ItemStack(this.blockID, 1, meta));
        }
        else
        {
            super.harvestBlock(world, player, i, j, k, meta);
        }
    }
}
