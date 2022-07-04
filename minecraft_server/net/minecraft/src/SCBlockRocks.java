package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockRocks extends Block {
	
	private String textureString;
	
	/**
	 * 
	 * @param blockID the block ID
	 * @param textureName the base texture to be used from /textures/blocks/
	 * @param unlocalisedName the name for lang
	 */
	protected SCBlockRocks(int par1, String textureString, String name) {
		super(par1, Material.rock);
		
		this.textureString = textureString;
		
		setUnlocalizedName(name);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		for (int i = 0; i < 4; i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
    }
	
	@Override
	public int PreBlockPlacedBy(World world, int i, int j, int k, int iMetadata, EntityLiving player)
	{
		//Thanks to zero318 for help on this
		int cash = cash(i,j,k) & 3;
		cash |= player.getHeldItem().getItemDamage() << 2;
		
		return cash;
	}
	
	// https://stackoverflow.com/a/37221804
	// cash stands for chaos hash
	private static int cash(int x, int y, int z)
    {   
    	int h = y + x*374761393 + z*668265263; //all constants are prime
    	h = (h^(h >> 13))*1274126177;
       return h^(h >> 16);
	}

	@Override
    public boolean canPlaceBlockAt( World world, int i, int j, int k )
    {
        if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
		{
			return false;
		}
        
        if (world.getBlockId(i, j-1, k) == Block.waterStill.blockID ||
        		world.getBlockId(i, j-1, k) == Block.leaves.blockID ||
        		world.getBlockId(i, j-1, k) == Block.ice.blockID)
        {
        	return false;
        }
		
        return super.canPlaceBlockAt( world, i, j, k );
    }
	
	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iBlockID )
    {
    	if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
    	{
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
            world.setBlockWithNotify(i, j, k, 0);
    	}
    }
	
    @Override
    public boolean CanGroundCoverRestOnBlock( World world, int i, int j, int k )
    {
    	return world.doesBlockHaveSolidTopSurface( i, j - 1, k );
    }
    
    @Override
    public float GroundCoverRestingOnVisualOffset( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return -1F;        
    }
	
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		double minX = 0;
		double minY = 0;
		double minZ = 0;
		double maxX = 1;
		double maxY = 1;
		double maxZ = 1;
		
		int meta = blockAccess.getBlockMetadata(i, j, k);
		
		if (meta <= 3 || (meta >= 8 && meta <= 11) )
		{
			maxY = 3/16D;
			
			if (meta == 0 || meta == 8)
			{
				minX = 5/16D; maxX= 11/16D;
				minZ = 5/16D; maxZ= 11/16D;
			}
			else if (meta == 1 || meta == 9)
			{
				minX = 3/16D; maxX= 1 - 2/16D;
				minZ = 4/16D; maxZ= 1 - 2/16D;
			}
			else if (meta == 2 || meta == 10)
			{
				minX = 4/16D; maxX= 1 - 7/16D;
				minZ = 4/16D; maxZ= 1 - 2/16D;
			}
			else if (meta == 3 || meta == 11)
			{
				minX = 3/16D; maxX= 1 - 4/16D;
				minZ = 3/16D; maxZ= 1 - 2/16D;
			}
		}
		else if ((meta >= 4 && meta <= 7) || (meta >= 12 && meta <= 15) )
		{
			maxY = 8/16D;
			
			if (meta == 4 || meta == 12)
			{
				maxY = 5/16D;
				minX = 2/16D; maxX= 1 - 2/16D;
				minZ = 3/16D; maxZ= 1 - 3/16D;
			}
			else if (meta == 5 || meta == 13)
			{
				maxY = 7/16D;
				minX = 3/16D; maxX= 1 - 3/16D;
				minZ = 3/16D; maxZ= 1 - 3/16D;
			}
			else if (meta == 6 || meta == 14)
			{
				maxY = 6/16D;
				minX = 3/16D; maxX= 1 - 3/16D;
				minZ = 3/16D; maxZ= 1 - 3/16D;
			}
			else if (meta == 7 || meta == 15)
			{
				maxY = 8/16D;
				minX = 2/16D; maxX= 1 - 1/16D;
				minZ = 1/16D; maxZ= 1 - 2/16D;
			}
		}

		return new AxisAlignedBB( minX, minY, minZ, maxX, maxY, maxZ );
	}
}
