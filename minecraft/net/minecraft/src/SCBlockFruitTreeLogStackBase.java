package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockFruitTreeLogStackBase extends Block {

	protected String type;
	
	//Types
	public final static int APPLE = 0;
	public final static int CHERRY = 1;
	public final static int LEMON = 2;
	public final static int OLIVE = 3;
	
	protected SCBlockFruitTreeLogStackBase(int par1, String type) {
		super(par1, Material.wood);
		
		this.type = type;
		
		InitBlockBounds(new AxisAlignedBB(4/16F,0,0/16F,12/16F,8/16F,16/16F));
		
	    setHardness( 1.25F ); // vanilla 2
	    setResistance( 3.33F );  // odd value to match vanilla resistance set through hardness of 2
        
		SetAxesEffectiveOn();
		
		setStepSound(soundWoodFootstep);
		
		setCreativeTab(null);

		setUnlocalizedName("SCBlockFruitLogStack");
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}
	
	@Override
	public int idDropped( int iMetadata, Random rand, int iFortuneMod )
	{
		return SCDefs.fruitLog.blockID;
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, x, y - 1, z, 1, true );
	}

    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
    }
    
    @Override
    public int damageDropped(int meta) {
    	return getType();
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighbourID) {
    	
    	int meta = world.getBlockMetadata(x, y, z);
    	if (!canBlockStay(world, x, y, z))
    	{
    		int size = getLogsStored(meta) + 1;
    		
    		for (int i = 0; i < size; i++)
    		{
    			dropBlockAsItem_do(world, x, y, z, new ItemStack(idDropped(meta, world.rand, 0), 1, damageDropped(meta)));
    		}
    		
    		world.setBlockToAir(x, y, z);
    	}
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
    	
    	return canBlockStay(world, x, y, z) && super.canPlaceBlockAt(world, x, y, z);
    }
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving player, ItemStack itemStack)
	{	
		
		int count = 0;
		
		if (itemStack.itemID == this.blockID)
		{
			int damage = itemStack.getItemDamage();
			
			count = damage & 3;
		}
		
		int playerRotation = ((MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 2;
		if (playerRotation == 1)
		{
			world.setBlockMetadataWithNotify(i, j, k, 4 + count);
		}
		else world.setBlockMetadataWithNotify(i, j, k, playerRotation + count);

	}
	
	@Override
	public void onBlockHarvested( World world, int x, int y, int z, int iMetadata, EntityPlayer player )
	{
		int size = getLogsStored(iMetadata) + 1;
		
		if (player.capabilities.isCreativeMode) return;
		
		for (int i = 0; i < size; i++)
		{
			dropBlockAsItem_do(world, x, y, z, new ItemStack(idDropped(iMetadata, world.rand, 0), 1, damageDropped(iMetadata)));
		}
	}
	
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
		ItemStack heldStack = player.getHeldItem();
		int meta = world.getBlockMetadata(i, j, k);
		
		if (!((EntityPlayer)player).isUsingSpecialKey()) return false;
		
		if (heldStack != null && heldStack.itemID == SCDefs.fruitLog.blockID && (heldStack.getItemDamage() == getType() ) && getLogsStored(meta) < 3) //Logs stored get counted 0, 1, 2, 3
		{
			world.setBlockAndMetadata(i, j, k, this.blockID, meta + 1);
			
			if(world.isRemote)
			{
				world.playAuxSFX( FCBetterThanWolves.m_iBlockPlaceAuxFXID, i, j, k, Block.wood.blockID );
			}
			
			if (!((EntityPlayer)player).capabilities.isCreativeMode)
			{
				player.getCurrentEquippedItem().stackSize--;
			}
			 
			return true;
		}
		
		return false;
    }
	
	protected int getLogsStored(int meta )
	{
		return meta & 3;
	}
	 
	protected int getType() {
		
		if (type == "apple" ) return APPLE;
		if (type == "cherry" ) return CHERRY;
		if (type == "lemon" ) return LEMON;
		else return OLIVE; //olive
	}
	
	protected boolean isRotated(int meta) {
		return (meta & 7) > 3;
	}
	
	@Override
	public boolean isOpaqueCube() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean IsNormalCube(IBlockAccess blockAccess, int i, int j, int k) {
		
		int meta = blockAccess.getBlockMetadata(i, j, k);
		
		if (getLogsStored(meta) == 3)
		{
			return true;
		}
		
		return super.IsNormalCube(blockAccess, i, j, k);
	}
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k)
	{
		int meta = blockAccess.getBlockMetadata(i, j, k);
		int size = getLogsStored(meta);
		boolean isRotated = isRotated(meta);
		
		float minx = 0;
		float miny = 0;
		float minz = 0;
		float maxx = 1;
		float maxy = 1;
		float maxz = 1;
		
		switch (size) {
		case 0:
			if (isRotated)
			{
				minz = 4/16F;
				maxz = 12/16F;
			}
			else
			{
				minx = 4/16F;
				maxx = 12/16F;
			}
			
			maxy = 8/16F;
			break;
			
		case 1:
			maxy = 8/16F;
			break;
			
		case 2:
			maxy = 16/16F;
			break;
			
		case 3:
			maxy = 16/16F;
			break;
			
		default:
			break;
		}
		AxisAlignedBB box = new AxisAlignedBB(minx,miny,minz,maxx,maxy,maxz);
		
		return box;
	}
	
	float center = 8/16F;
	float width = 8/16F;
	float height = 8/16F;
	float length = 16/16F;
	float xShift = 0;
	float yShift = 0;
	float zShift = 0;
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
		
		int meta = renderer.blockAccess.getBlockMetadata(i, j, k) & 7;

		if (meta > 3)
		{
			renderer.SetUvRotateTop(1);
			renderer.SetUvRotateBottom(1);
		}
		
		if ((meta&3) == 0)
		{
			yShift = -4/16F;
			
			width = 8/16F;
			height = 8/16F;
			length = 16/16F;
			
			if (meta == 4)
			{
				width = 16/16F;
				height = 8/16F;
				length = 8/16F;
			}
			
			renderer.setRenderBounds( new AxisAlignedBB(
					center - width/2 + xShift, center - height/2 + yShift, center - length/2 + zShift,
					center + width/2 + xShift, center + height/2 + yShift, center + length/2 + zShift
					));
			renderer.renderStandardBlock( this, i, j, k );
		}
		else if ((meta&3) == 1)
		{
			yShift = -4/16F;
			
			width = 16/16F;
			height = 8/16F;
			length = 16/16F;
						
			renderer.setRenderBounds( new AxisAlignedBB(
					center - width/2 + xShift, center - height/2 + yShift, center - length/2 + zShift,
					center + width/2 + xShift, center + height/2 + yShift, center + length/2 + zShift
					));
			renderer.renderStandardBlock( this, i, j, k );
		}
		else if ((meta&3) == 2)
		{
			yShift = -4/16F;
			
			width = 16/16F;
			height = 8/16F;
			length = 16/16F;
			
			renderer.setRenderBounds( new AxisAlignedBB(
					center - width/2 + xShift, center - height/2 + yShift, center - length/2 + zShift,
					center + width/2 + xShift, center + height/2 + yShift, center + length/2 + zShift
					));
			renderer.renderStandardBlock( this, i, j, k );
			
			yShift = 4/16F;
			
			width = 16/16F;
			height = 8/16F;
			length = 8/16F;
			
			if (meta == 6)
			{
				width = 8/16F;
				height = 8/16F;
				length = 16/16F;
			}
			
			renderer.setRenderBounds( new AxisAlignedBB(
					center - width/2 + xShift, center - height/2 + yShift, center - length/2 + zShift,
					center + width/2 + xShift, center + height/2 + yShift, center + length/2 + zShift
					));
			renderer.renderStandardBlock( this, i, j, k );
		}
		else if ((meta&3) == 3)
		{
			yShift = 0/16F;
			
			width = 16/16F;
			height = 16/16F;
			length = 16/16F;
			
			renderer.setRenderBounds( new AxisAlignedBB(
					center - width/2 + xShift, center - height/2 + yShift, center - length/2 + zShift,
					center + width/2 + xShift, center + height/2 + yShift, center + length/2 + zShift
					));
			renderer.renderStandardBlock( this, i, j, k );
		}


		renderer.ClearUvRotation();
		
		return true;
	}
	
	@Override
	public void RenderBlockAsItem(RenderBlocks renderer, int damage, float fBrightness) {
		
		xShift = 0;
		yShift = 0;
		zShift = 0;
		
		renderer.SetUvRotateTop(0);
		renderer.SetUvRotateBottom(0);
		
		if (damage == 0)
		{
			yShift = -4/16F;
			
			width=8/16F;
			length=16/16F;
			height=8/16F;

			renderer.setRenderBounds( new AxisAlignedBB(
					center - width/2 + xShift, center - height/2 + yShift, center - length/2 + zShift,
					center + width/2 + xShift, center + height/2 + yShift, center + length/2 + zShift
					));
			FCClientUtilsRender.RenderInvBlockWithMetadata(renderer, this, -0.5F, -0.5F, -0.5F, damage);

			
		}
		else if (damage == 1)
		{
			width=16/16F;
			length=16/16F;
			height=8/16F;
			
			yShift = -4/16F;
			
			renderer.setRenderBounds( new AxisAlignedBB(
					center - width/2 + xShift, center - height/2 + yShift, center - length/2 + zShift,
					center + width/2 + xShift, center + height/2 + yShift, center + length/2 + zShift
					));
			FCClientUtilsRender.RenderInvBlockWithMetadata(renderer, this, -0.5F, -0.5F, -0.5F, damage);

		}
		else if (damage == 2)
		{
			width=16/16F;
			length=16/16F;
			height=8/16F;
			
			yShift = -4/16F;
			
			renderer.setRenderBounds( new AxisAlignedBB(
					center - width/2 + xShift, center - height/2 + yShift, center - length/2 + zShift,
					center + width/2 + xShift, center + height/2 + yShift, center + length/2 + zShift
					));
			FCClientUtilsRender.RenderInvBlockWithMetadata(renderer, this, -0.5F, -0.5F, -0.5F, damage);
			
			//Top
			yShift = 4/16F;
			
			width=16/16F;
			length=8/16F;
			height=8/16F;
			
			renderer.setRenderBounds( new AxisAlignedBB(
					center - width/2 + xShift, center - height/2 + yShift, center - length/2 + zShift,
					center + width/2 + xShift, center + height/2 + yShift, center + length/2 + zShift
					));
			FCClientUtilsRender.RenderInvBlockWithMetadata(renderer, this, -0.5F, -0.5F, -0.5F, damage);

		}
		else if (damage == 3)
		{
			yShift = 0/16F;
			
			width=16/16F;
			length=16/16F;
			height=16/16F;
			
			renderer.setRenderBounds( new AxisAlignedBB(
					center - width/2 + xShift, center - height/2 + yShift, center - length/2 + zShift,
					center + width/2 + xShift, center + height/2 + yShift, center + length/2 + zShift
					));
			FCClientUtilsRender.RenderInvBlockWithMetadata(renderer, this, -0.5F, -0.5F, -0.5F, damage);

		}
		renderer.ClearUvRotation();
	}

}
