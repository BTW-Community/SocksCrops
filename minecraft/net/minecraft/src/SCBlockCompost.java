package net.minecraft.src;

import java.util.Random;

public class SCBlockCompost extends FCBlockFalling {

    //private static Material blockMaterial;

	private static Material blockMaterial;

	protected SCBlockCompost(int par1) {
        super(par1, Material.ground);
        
        this.setHardness( 0.5F );
        
        this.SetShovelsEffectiveOn();
        
        this.SetBuoyant();
        
        this.setStepSound( soundGravelFootstep );
        
        this.setUnlocalizedName("SCBlockCompost");
        
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    public int GetHarvestToolLevel(IBlockAccess var1, int var2, int var3, int var4)
    {
        return 2; //Iron+
    }
}