package net.minecraft.src;

import java.util.Random;

import com.prupe.mcpatcher.cc.ColorizeBlock;

public class SCBlockTallGrass extends FCBlockTallGrass {
	
	private static final double m_dHalfWidth = 0.4F;
	public static final String[] grassTypes = new String[] {"deadbush", "tallgrass", "fern"};
	
	protected SCBlockTallGrass(int par1) {
		super(par1);
    	setHardness( 0F );
    	
    	SetBuoyant();
    	
		SetFireProperties( FCEnumFlammability.GRASS );
		
        InitBlockBounds( 0.5D - m_dHalfWidth, 0D, 0.5D - m_dHalfWidth, 
        	0.5D + m_dHalfWidth, 0.8D, 0.5D + m_dHalfWidth);
        
		setStepSound( soundGrassFootstep );
		
		setUnlocalizedName( "tallgrass" );
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
    	return ColorizerFoliage.getFoliageColorBasic();
//    	return ColorizeBlock.colorizeBlock(this, par1) ? ColorizeBlock.blockColor : (par1 == 0 ? 16777215 : ColorizerFoliage.getFoliageColorBasic());
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
    		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
    		return var5 == 0 ? 16777215 : par1IBlockAccess.getBiomeGenForCoords(par2, par4).getBiomeGrassColor();
    	}
    }
	
	private Icon[] iconArray;
	
    public Icon getIcon(int par1, int par2)
    {
        if (par2 >= this.iconArray.length)
        {
            par2 = 0;
        }

        return this.iconArray[par2];
    }
    
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconArray = new Icon[grassTypes.length];

        for (int var2 = 0; var2 < this.iconArray.length; ++var2)
        {
            this.iconArray[var2] = par1IconRegister.registerIcon(grassTypes[var2]);
        }
    }

}
