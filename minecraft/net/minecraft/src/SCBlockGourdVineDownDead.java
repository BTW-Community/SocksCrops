package net.minecraft.src;

import java.util.Random;

public class SCBlockGourdVineDownDead extends SCBlockGourdVineDown {

	protected SCBlockGourdVineDownDead(int iBlockID, int stemBlock) {
		super(iBlockID, stemBlock, 0, 0, 0, leavesTex, topTex, bottomTex);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		if (!this.canBlockStay(world, i, j, k))
		{
			world.setBlock(i, j, k, 0);
		}
	}
	
	@Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
		if ( IsFullyGrown(world, i, j, k) )
		{
			return CanGrowOnBlock( world, i, j - 1, k );
		}
		else return true;
    }
	
    @Override
    public void registerIcons( IconRegister register )
    {
//    	super.registerIcons(register);
    	
    	vineIcons = new Icon[4];
    	
        for ( int iTempIndex = 0; iTempIndex < vineIcons.length; iTempIndex++ )
        {
        	vineIcons[iTempIndex] = register.registerIcon( "SCBlockDeadVineDownLeaves_bottom_" + iTempIndex );
//        	vineIcons[iTempIndex] = register.registerIcon( "stone" );
        }

        
        blockIcon = vineIcons[3]; // for block hit effects and item render

        for ( int iTempIndex = 0; iTempIndex < top.length; iTempIndex++ )
        {

        	top[iTempIndex] = register.registerIcon("SCBlockDeadVineDown_top_" + iTempIndex);
        	bottom[iTempIndex] = register.registerIcon("SCBlockDeadVineDown_bottom_" + iTempIndex);
        }   
    }
	
	
	
}
