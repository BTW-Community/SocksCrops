package net.minecraft.src;

public class SCBlockDamagedLog extends SCBlockLogBase {

	protected SCBlockDamagedLog(int iBlockID) {
		super(iBlockID);
		this.setUnlocalizedName("SCBlockDamagedLog");
	}

    public static final String[] damagedTextureTypes = new String[] {"SCBlockDamagedLogOak_side", "SCBlockDamagedLogSpruce_side", "SCBlockDamagedLogBirch_side", "SCBlockDamagedLogJungle_side"};
    public static final String[] damagedTopTextures = new String[] {"SCBlockLogOak_top", "SCBlockLogSpruce_top", "SCBlockLogBirch_top", "SCBlockLogJungle_top"};
    public static final String[] treeTextures = new String[] {"tree_side", "tree_spruce", "tree_birch", "tree_jungle"};
    
}
