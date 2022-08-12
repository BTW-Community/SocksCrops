package net.minecraft.src;

public class SCBlockMossyLog extends SCBlockLogBase {

	protected SCBlockMossyLog(int iBlockID) {
		super(iBlockID);
		this.setUnlocalizedName("SCBlockMossyLog");
	}

    public static final String[] damagedTextureTypes = new String[] {"SCBlockMossyLogOak_side", "SCBlockMossyLogSpruce_side", "SCBlockMossyLogBirch_side", "SCBlockMossyLogJungle_side"};
    public static final String[] damagedTopTextures = new String[] {"SCBlockLogOak_top", "SCBlockLogSpruce_top", "SCBlockLogBirch_top", "SCBlockLogJungle_top"};
    public static final String[] treeTextures = new String[] {"tree_side", "tree_spruce", "tree_birch", "tree_jungle"};
    
	
}
