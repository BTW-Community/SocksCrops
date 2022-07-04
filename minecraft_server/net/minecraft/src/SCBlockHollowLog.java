package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockHollowLog extends SCBlockLogBase {
	
	public static final String[] treeNames = new String[] {"oak", "spruce", "birch", "jungle"};
	public static final String[] treeTextureTypes = new String[] {"tree_side", "tree_spruce", "tree_birch", "tree_jungle"};
	public static final String[] treeTextureTop = new String[] {"SCBlockLogHollowOak_top", "SCBlockLogHollowSpruce_top", "SCBlockLogHollowBirch_top", "SCBlockLogHollowJungle_top"};
	public static final String[] treeTextureInner = new String[] {"SCBlockLogHollowOak_inside", "SCBlockLogHollowSpruce_inside", "SCBlockLogHollowBirch_inside", "SCBlockLogHollowJungle_inside"};
	
	protected static final SCModelBlockHollowLog model = new SCModelBlockHollowLog();
	
	protected SCBlockHollowLog(int iBlockID) {
		super(iBlockID);
		
	    setHardness( 1.25F ); // vanilla 2
	    setResistance( 3.33F );  // odd value to match vanilla resistance set through hardness of 2
        
		SetAxesEffectiveOn();
		SetChiselsEffectiveOn(false);
		
        SetBuoyant();
		
		SetFireProperties( FCEnumFlammability.LOGS );
		
        setStepSound( soundWoodFootstep );
		
		this.setUnlocalizedName("SCBlockHollowLog");
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return this.blockID;
	}
	
	@Override
	public boolean DropComponentItemsOnBadBreak( World world, int i, int j, int k, int iMetadata, float fChanceOfDrop )
	{
		DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemSawDust.itemID, 2, 0, fChanceOfDrop );
		DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemBark.itemID, 1, iMetadata & 3, fChanceOfDrop );
		
		return true;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
}
