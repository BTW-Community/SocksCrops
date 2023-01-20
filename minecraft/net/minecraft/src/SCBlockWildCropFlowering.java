package net.minecraft.src;

public abstract class SCBlockWildCropFlowering extends SCBlockWildCrop {

	protected SCBlockWildCropFlowering(int iBlockID, String name) {
		super(iBlockID, name);
	}
	
	@Override
	protected abstract int GetSeedItemID();
	
	//------------ Client Side Functionality ----------//
	
	@Override
    public boolean RenderBlock(RenderBlocks renderer, int x, int y, int z) {
        renderer.setRenderBounds(GetBlockBoundsFromPoolBasedOnState(renderer.blockAccess, x, y, z));
        
        FCBetterThanWolves.fcBlockWeeds.RenderWeeds( this, renderer, x, y, z );

    	return renderer.renderCrossedSquares(this, x, y, z);
    }

}
