package net.minecraft.src;

import java.util.List;
import java.util.Random;

import com.prupe.mcpatcher.cc.ColorizeBlock;

public class SCBlockShortPlant extends BlockFlower {

	private static final double m_dHalfWidth = 0.4F;
	
	public static final int SHORTGRASS = 0;
	public static final int TALLGRASS = 1;
	
	protected SCBlockShortPlant(int par1) {
		super(par1);
		
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
	
    public int getBlockColor()
    {
    	if (ColorizeBlock.colorizeBlock(this))
        {
            return ColorizeBlock.blockColor;
        }
        else
        {
        	double var1 = 0.5D;
        	double var3 = 1.0D;
        	return ColorizerGrass.getGrassColor(var1, var3);
        }
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    public int getRenderColor(int par1)
    {    	
    	if ( ColorizeBlock.colorizeBlock(this, par1) )
    	{
    		return ColorizeBlock.blockColor;
    	}
    	else
    	{
        	double var1 = 0.5D;
        	double var3 = 1.0D;
        	return ColorizerGrass.getGrassColor(var1, var3);
    	}
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
    	if (ColorizeBlock.colorizeBlock(this, par1IBlockAccess, par2, par3, par4))
        {
            return ColorizeBlock.blockColor;
        }
        else
        {
        	return par1IBlockAccess.getBiomeGenForCoords(par2, par4).getBiomeGrassColor();
        }
    }
	
	@Override
	public Icon getIcon(int side, int meta) {
		return icons[meta];
	}
	
	private Icon[] icons = new Icon[16];
	
	@Override
	public void registerIcons(IconRegister register)
	{
		icons[0] = register.registerIcon( "SCBlockPlants_smallGrass" ); 
		icons[1] = register.registerIcon( "tallgrass" );
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{
		int meta = renderer.blockAccess.getBlockMetadata(i, j, k); 
		SCUtilsRender.RenderCrossedSquaresWithTexture(renderer, this, i, j, k, icons[meta], true);
		return true;
	}
}
