package net.minecraft.src;

public abstract class SCBlockWildCropFlowering extends SCBlockWildCrop {

	protected SCBlockWildCropFlowering(int iBlockID, String name) {
		super(iBlockID, name);
	}
	
	@Override
	protected abstract int GetSeedItemID();
	
	//------------ Client Side Functionality ----------//

}
