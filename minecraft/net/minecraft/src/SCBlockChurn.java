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
	
	private Icon barrelSide;
	private Icon barrelTop;
	private Icon milk;
	@Override
	public void registerIcons(IconRegister register) {
		blockIcon = barrelSide = register.registerIcon("fcBlockBarrel_side");
		barrelTop = register.registerIcon("fcBlockBarrel_top");
		milk = register.registerIcon("fcBlockMilk");
	}
	
	@Override
	public Icon getIcon(int side, int meta) {

		if (side < 2)
		{
			return barrelTop;
		}
		else return barrelSide;
	}
	
	private float wallWidth = 2/16F;
	private float wallLength = 16/16F; 
	private float wallLength2 = 12/16F; 
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
		
		SCTileEntityChurn churn = (SCTileEntityChurn) renderer.blockAccess.getBlockTileEntity(i, j, k);
		
		int fillLevel;
		
		if (churn != null)
		{
			fillLevel = churn.getFillLevel();
			
			if (fillLevel > 0)
			{
				renderer.setOverrideBlockTexture(milk);
				renderer.setRenderBounds(
						2/16F,fillLevel/16F, 2/16F,
						14/16F, fillLevel/16F, 14/16F );
				
				renderer.renderStandardBlock(this, i, j, k);
				renderer.clearOverrideBlockTexture();
			}
		}
		
		renderer.setRenderBounds(
				2/16F, 0, 2/16F,
				14/16F, 2/16F, 14/16F );
		renderer.renderStandardBlock(this, i, j, k);
		


		
		float centerX;
		float centerZ;
		
		for (int index = 0; index < 2; index++)
		{
			switch (index) {
			default:
			case 0:
				centerX = 1/16F;
				centerZ = 8/16F;
				break;

			case 1:
				centerX = 15/16F;
				centerZ = 8/16F;
				break;
			}
			
			renderer.setRenderBounds(
					centerX - wallWidth/2, 0, centerZ - wallLength/2,
					centerX + wallWidth/2, 1, centerZ + wallLength/2 );
			renderer.renderStandardBlock(this, i, j, k);
		}
		
		for (int index = 0; index < 2; index++)
		{
			switch (index) {
			default:
			case 0:
				centerX = 8/16F;
				centerZ = 1/16F;
				break;

			case 1:
				centerX = 8/16F;
				centerZ = 15/16F;
				break;
			}
			
			renderer.setRenderBounds(
					centerX - wallLength2/2, 0, centerZ - wallWidth/2,
					centerX + wallLength2/2, 1, centerZ + wallWidth/2 );
			renderer.renderStandardBlock(this, i, j, k);
		}
		
		return true;
	}
}
