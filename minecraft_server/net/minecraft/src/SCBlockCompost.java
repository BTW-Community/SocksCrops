package net.minecraft.src;

import java.util.Random;

public class SCBlockCompost extends FCBlockFalling {

	protected SCBlockCompost(int par1) {
        super(par1, Material.ground);
        
        this.setHardness( 0.5F );
        
        this.SetShovelsEffectiveOn();
        
        this.SetBuoyant();
        
        this.setStepSound( soundGravelFootstep );
        
        this.setUnlocalizedName("SCBlockCompost");
        
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

}