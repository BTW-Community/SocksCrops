package net.minecraft.src;

import java.util.Random;

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
}
