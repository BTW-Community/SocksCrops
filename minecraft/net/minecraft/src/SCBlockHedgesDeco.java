package net.minecraft.src;

import java.util.List;

import org.lwjgl.opengl.GL11;

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
	
	@Override
	public void getSubBlocks(int blockID, CreativeTabs tab, List list) {
		
		for(int i = 0; i < hedgeTypes.length; i++)
		{
			list.add(new ItemStack(blockID, 1, i));
		}
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
    
    @Override
    public void registerIcons(IconRegister register) {
    	for (int i = 0; i < hedgeTypes.length; i++)
    	{
    		woodSide[i] = register.registerIcon(woodSideTex[i]);
    		leaves[i] = register.registerIcon(leavesTex[i]);
    		opaqueLeaves[i] = register.registerIcon(opaqueLeavesTex[i]);
    	}
    }
    

    @Override
    public void RenderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean bFirstPassResult) {
    	//no flower pls
    }
    @Override
    public void RenderBlockAsItem(RenderBlocks renderer, int damage, float brightness) {
    	
    	//wood
    	renderer.setRenderBounds(
    			center - woodWidth/2, 0, center - woodWidth/2,
    			center + woodWidth/2, woodHeight, center + woodWidth/2);
    	FCClientUtilsRender.RenderInvBlockWithMetadata(renderer, this, -center, -center, -center, damage);
    	
        int color = colorMultiplierForItemRender(damage);
        
        float var8 = (float)(color >> 16 & 255) / 255.0F;
        float var9 = (float)(color >> 8 & 255) / 255.0F;
        float var10 = (float)(color & 255) / 255.0F;
        
        GL11.glColor4f(var8 * brightness, var9 * brightness, var10 * brightness, 1.0F);
    	
    	//leaves
    	renderer.setRenderBounds(
    			center - leavesWidth/2, 0, center - leavesWidth/2,
    			center + leavesWidth/2, leavesHeight, center + leavesWidth/2);
    	FCClientUtilsRender.RenderInvBlockWithTexture(renderer, this, -center, -center, -center, leaves[damage]);
    	
    }
}
