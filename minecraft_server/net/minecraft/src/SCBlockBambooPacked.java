package net.minecraft.src;

import java.util.List;

public class SCBlockBambooPacked extends FCBlockDirectional {

	public final static int BAMBOO = 0;
	public final static int STRIPPED_BAMBOO = 1;
	
	protected SCBlockBambooPacked(int id, Material material, String[] topTextures, String[] sideTextures) {
		super(id, material, topTextures, sideTextures);
		
	    setHardness( 0.5F );	  
        
		SetAxesEffectiveOn();
		
        SetBuoyant();
		
		SetFireProperties( FCEnumFlammability.PLANKS );
		
        setStepSound( soundWoodFootstep );
        
        setUnlocalizedName( "log" );    
	}
	
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }

}
