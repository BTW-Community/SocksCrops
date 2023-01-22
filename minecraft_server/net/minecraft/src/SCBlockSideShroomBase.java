package net.minecraft.src;

import java.util.List;

public abstract class SCBlockSideShroomBase extends BlockFlower {

	private String[] types = {"brown","white","red","black"};
	
	protected SCBlockSideShroomBase(int par1) {
		super(par1, Material.plants);
	}

	@Override
	public int damageDropped(int meta) {
		if (getType(meta) == 1)
		{
			return 4;
		}
		if (getType(meta) == 2)
		{
			return 8;
		}
		if (getType(meta) == 3)
		{
			return 12;
		}
		else return 0;
	}
    
	//Copied from BlockButton
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        return side == 2 && world.isBlockNormalCube(x, y, z + 1) ? true : (side == 3 && world.isBlockNormalCube(x, y, z - 1) ? true : (side == 4 && world.isBlockNormalCube(x + 1, y, z) ? true : side == 5 && world.isBlockNormalCube(x - 1, y, z)));
    }

    //Copied from BlockButton
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return world.isBlockNormalCube(x - 1, y, z) ? true : (world.isBlockNormalCube(x + 1, y, z) ? true : (world.isBlockNormalCube(x, y, z - 1) ? true : world.isBlockNormalCube(x, y, z + 1)));
    }
    
    @Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
        return canPlaceBlockAt(world, i, j, k);
    }

    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 4));
        par3List.add(new ItemStack(par1, 1, 8));
        par3List.add(new ItemStack(par1, 1, 12));
    }
	//Direction
	private final int SOUTH = 0;
	private final int WEST = 1;
	private final int NORTH = 2;
	private final int EAST = 3;
    
	@Override
    public int onBlockPlaced( World world, int i, int j, int k, int targetFace, float fClickX, float fClickY, float fClickZ, int iMetadata )
    {
		//System.out.println("TargetFace: " + targetFace);
		
		if (targetFace > 1)
		{
			if (targetFace == 2)
				return SOUTH;
			else if (targetFace == 3)
				return NORTH;
			else if (targetFace == 4)
				return EAST;
			else 
				return WEST;
		}
		else 
		{
			return iMetadata;
		}
    }
    
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving player, ItemStack stack) {

		//int playerRotation = ((MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
		int itemDamage = stack.getItemDamage();
		int meta = world.getBlockMetadata(i, j, k);
//		System.out.println(playerRotation);
		
		world.setBlockAndMetadata(i, j, k, this.blockID, meta + (itemDamage) );
	}
	
	//Copied from BlockButton and modified
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighbour) {
		boolean var7 = false;
		int meta = world.getBlockMetadata(x, y, z);
		int dir = getDirection(meta);
		
		if (!world.isBlockNormalCube(x - 1, y, z) && dir == WEST)
        {
            var7 = true;
        }

        if (!world.isBlockNormalCube(x + 1, y, z) && dir == EAST)
        {
            var7 = true;
        }

        if (!world.isBlockNormalCube(x, y, z - 1) && dir == NORTH)
        {
            var7 = true;
        }

        if (!world.isBlockNormalCube(x, y, z + 1) && dir == SOUTH)
        {
            var7 = true;
        }

        if (var7)
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
	}
	
	private int getDirection(int meta) {
		return meta & 3;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	private Icon[] top = new Icon[4];
	private Icon[] middle = new Icon[4];
	private Icon[] bottom = new Icon[4];
	
	private Icon[] shroom = new Icon[4];
	
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		
		float centerX = 8/16F;
		float centerY = 8/16F;
		float centerZ = 8/16F;
		
		float width = 14/16F;
		float height = 8/16F;
		float depth = 6/16F;
	
		int meta = blockAccess.getBlockMetadata(i, j, k);
		int dir = getDirection(meta);
		
		if (dir == NORTH )
		{
			centerZ = centerZ - 5/16F;
		}
		else if (dir == SOUTH)
		{
			centerZ = centerZ + 5/16F;
		}
		else if (dir == EAST)
		{
			width = 6/16F;
			depth = 14/16F;
			
			centerX = centerX + 5/16F;
		}
		else if (dir == WEST)
		{
			width = 6/16F;
			depth = 14/16F;
			
			centerX = centerX - 5/16F;
		}
		
		
		AxisAlignedBB box = new AxisAlignedBB(
				centerX - width/2, centerY - height/2, centerZ - depth/2,
				centerX + width/2, centerY + height/2, centerZ + depth/2
		);
	
		return box;
	}
	
//	private final int SOUTH = 0;
//	private final int WEST = 1;
//	private final int NORTH = 2;
//	private final int EAST = 3;
	
	private int getType(int meta) {
		if (meta < 4)
		{
			return 0;
		}
		else if (meta < 8)
		{
			return 1;
		}
		else if (meta < 12)
		{
			return 2;
		}
		else return 3;
	}

}
