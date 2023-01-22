package net.minecraft.src;

public class SCBlockChurn extends BlockContainer {

	protected SCBlockChurn(int par1) {
		super(par1, Material.wood);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("SCBlockChurn");
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick)
	{
		if (player.getCurrentEquippedItem() == null)
		{
			
			SCTileEntityChurn churn = (SCTileEntityChurn) world.getBlockTileEntity(i, j, k);
			
			if (churn != null)
			{
				if (churn.getCounter() == 0)
				{
					churn.setCounter(36);
				}				
				
			}
			
			return true;
		}
		
		return false;
	}
	@Override
	public boolean CanBePistonShoveled(World world, int i, int j, int k) {
		return true;
	}
	
	@Override
	public boolean CanBlockBePushedByPiston(World world, int i, int j, int k, int iToFacing) {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new SCTileEntityChurn();
	}
	
	
	
	private float wallWidth = 2/16F;
	private float wallLength = 16/16F; 
	private float wallLength2 = 12/16F; 
	
}
