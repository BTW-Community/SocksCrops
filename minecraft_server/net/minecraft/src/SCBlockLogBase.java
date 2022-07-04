package net.minecraft.src;

public abstract class SCBlockLogBase extends FCBlockLog {

	protected SCBlockLogBase(int iBlockID) {
		super(iBlockID);
	}
	
	@Override
    public boolean CanConvertBlock( ItemStack stack, World world, int i, int j, int k )
    {
    	return false;
    }
	
	@Override
    public boolean GetCanBlockBeIncinerated( World world, int i, int j, int k )
    {
    	return false;
    }
	

}
