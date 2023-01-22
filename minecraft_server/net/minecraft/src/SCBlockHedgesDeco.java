package net.minecraft.src;

public class SCBlockHedgesDeco extends SCBlockHedges {

	public final static int ACACIA = 0;
	public final static int AUTUMN_RED = 1;
	public final static int AUTUMN_ORANGE = 2;
	public final static int AUTUMN_YELLOW = 3;
	
    public static String[] hedgeTypes = {
    		"acaia",
    		"autumnRed", "autumnOrange", "autumnYellow"
    };
	
	protected SCBlockHedgesDeco(int blockID) {
		super(blockID);
		setCreativeTab( CreativeTabs.tabDecorations );
		setUnlocalizedName("SCBlockHedgesDeco");
	}
	
    Icon[] woodSide = new Icon[16];
    private String[] woodSideTex = {
    		"decoBlockLogAcacia_side", "tree_side", "tree_side", "tree_side", 
    };
    
    Icon[] leaves = new Icon[16];
    private String[] leavesTex = {
    		"decoBlockLeavesAcacia", "decoBlockLeavesAutumnRed", "decoBlockLeavesAutumnOrange", "decoBlockLeavesAutumnYellow",
    };
    
    Icon[] opaqueLeaves = new Icon[16];
    private String[] opaqueLeavesTex = {
    		"decoBlockLeavesAcacia", "decoBlockLeavesAutumnRed", "decoBlockLeavesAutumnOrange", "decoBlockLeavesAutumnYellow",
    };

}
