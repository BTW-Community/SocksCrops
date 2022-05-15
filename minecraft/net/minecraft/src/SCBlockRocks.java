package net.minecraft.src;

import java.util.List;
import java.util.Random;

import com.prupe.mcpatcher.cc.ColorizeBlock;
import com.prupe.mcpatcher.mal.block.RenderBlocksUtils;

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
		for (int i = 0; i < 2; i++) {
			par3List.add(new ItemStack(par1, 1, i));
			par3List.add(new ItemStack(par1, 1, i + 8));
		}
    }
	
	@Override
	public int PreBlockPlacedBy(World world, int i, int j, int k, int iMetadata, EntityLiving player)
	{
		//get the random number from coordinates
		int cash = cash( i,j,k );
		
		//get the last Digit and half it
		int lastDigit = ((cash % 10) / 2);
		
		//limit the "lastDigit" to 0 - 3
		if (lastDigit > 3) lastDigit = 3;
		
		//shift the "lastDigit" to get the correct meta for the model
		int randomSmallRock = lastDigit;
		int randomLargeRock = lastDigit + 4;
		int randomSmallRockMossy = lastDigit + 8;
		int randomLargeRockMossy = lastDigit + 12;
		
		int itemDamage = player.getHeldItem().getItemDamage();
		
		//set the model depending on the itemDamage
		if (itemDamage == 0) return randomSmallRock;
		else if (itemDamage == 1) return randomLargeRock;
		else if (itemDamage == 8) return randomSmallRockMossy;
		else if (itemDamage == 9) return randomLargeRockMossy;
		
		else return super.PreBlockPlacedBy(world, i, j, k, iMetadata, player);
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
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving player, ItemStack stack) {

		
	}
	
	@Override
    public boolean canPlaceBlockAt( World world, int i, int j, int k )
    {
        if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
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
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK,
			int iSide) {
		return true;
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

	private Icon textureIcon;
	private Icon mossTop;
	private Icon mossSide;
	
	private Icon snow;
	private Icon snowSide;
	
	
	@Override
	public void registerIcons(IconRegister register) {
		blockIcon = textureIcon = register.registerIcon(textureString);
		mossTop = register.registerIcon("grass_top");
		mossSide = register.registerIcon("SCBlockRockMossOverlay_side");
		
		snow = register.registerIcon("snow");
		snowSide = register.registerIcon("SCBlockRockMossOverlay_sideSnow");
	}
	
//	@Override
//	public Icon getIcon(int side, int meta) {
//		if (meta < 8)
//		{
//			return blockIcon;
//		}
//		else
//		{
//			if (side == 1)
//			{
//				return mossTop;
//			}
//			else if (side == 0)
//			{
//				return blockIcon;
//			}
//			else return mossSide;
//		}
//	}
	
	public boolean hasSnowOnTop; // temporary variable used by rendering
	public static boolean secondPass;
	    
	
	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		

		if ( hasSnowOnTop || !secondPass ) {
			return 16777215;
		}
		else {
			if ( blockAccess.getBlockMetadata(x, y, z) > 7)
			{
//		        int var5 = 0;
//		        int var6 = 0;
//		        int var7 = 0;
//
//		        for (int var8 = -1; var8 <= 1; ++var8)
//		        {
//		            for (int var9 = -1; var9 <= 1; ++var9)
//		            {
//		                int var10 = blockAccess.getBiomeGenForCoords(x + var9, z + var8).getBiomeGrassColor();
//		                var5 += (var10 & 16711680) >> 16;
//		                var6 += (var10 & 65280) >> 8;
//		                var7 += var10 & 255;
//		            }
//		        }
//
//		        return (var5 / 9 & 255) << 16 | (var6 / 9 & 255) << 8 | var7 / 9 & 255;
				
	        	double var1 = 1.0D;
	        	double var3 = 0.5D;
	        	//return 16777215;
	        	return ColorizerGrass.getGrassColor(var1, var3);
			}
			else return 16777215;
		}
	}
	
	@Override
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		if (!secondPass) {
			return textureIcon;
		}
		else {
			return getBlockTextureSecondPass(blockAccess, x, y, z, side);
		}
	}

	public Icon getBlockTextureSecondPass(IBlockAccess blockAccess, int i, int j, int k, int side) {		
		if (blockAccess.getBlockMetadata(i, j, k) > 7)
		{
			if (side == 1) {
				
				if (hasSnowOnTop)
				{
					return snow;
				}
				return mossTop;
				
			}
			else if (side > 1 ) {
				if (hasSnowOnTop)
				{
					return snowSide;
				}
				return mossSide;
			}

		}

		return textureIcon;
	}
	
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
		hasSnowOnTop = IsSnowCoveringTopSurface(renderer.blockAccess, i, j, k);
		return renderRocks(renderer,i,j,k);
	}
	

	@Override
	public void RenderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean firstPassResult) {
		secondPass = true;
		renderRocks(renderer,i,j,k);
		secondPass = false;
	}
	
	@Override
	public void RenderBlockAsItem(RenderBlocks renderBlocks, int iItemDamage, float fBrightness) {
		// TODO Auto-generated method stub
		super.RenderBlockAsItem(renderBlocks, iItemDamage, fBrightness);
	}
	
	private boolean renderRocks(RenderBlocks renderer, int i, int j, int k) {
		int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
		
		if (meta == 0 || meta == 8)
		{
			renderer.setRenderBounds(
					 6/16D, 0/16D,  7/16D,
					11/16D, 3/16D, 11/16D );
			renderer.renderStandardBlock(this, i, j, k);
			
			if (!hasSnowOnTop)
			{
				renderer.setRenderBounds(
						5/16D, 0/16D, 5/16D,
						8/16D, 2/16D, 8/16D	);
				renderer.renderStandardBlock(this, i, j, k);
			}
		}
		else if (meta == 1 || meta == 9)
		{
			renderer.setRenderBounds(
					 3/16D, 0/16D,  4/16D,
					9/16D, 3/16D, 9/16D );
			renderer.renderStandardBlock(this, i, j, k);
			
			if (!hasSnowOnTop)
			{
			renderer.setRenderBounds(
					11/16D, 0/16D, 5/16D,
					14/16D, 1/16D, 8/16D );
			renderer.renderStandardBlock(this, i, j, k);
			
			renderer.setRenderBounds(
					8/16D, 0/16D, 11/16D,
					11/16D, 1/16D, 14/16D );
			renderer.renderStandardBlock(this, i, j, k);
			}
		}
		else if (meta == 2 || meta == 10)
		{
			renderer.setRenderBounds(
					 5/16D, 0/16D,  5/16D,
					9/16D, 3/16D, 10/16D );
			renderer.renderStandardBlock(this, i, j, k);
			
			if (!hasSnowOnTop)
			{
			renderer.setRenderBounds(
					4/16D, 0/16D, 4/16D,
					6/16D, 1/16D, 6/16D );
			renderer.renderStandardBlock(this, i, j, k);
			
			renderer.setRenderBounds(
					6/16D, 0/16D, 12/16D,
					8/16D, 1/16D, 14/16D );
			renderer.renderStandardBlock(this, i, j, k);
			
			renderer.setRenderBounds(
					5/16D, 0/16D, 12/16D,
					8/16D, 1/16D, 14/16D );
			renderer.renderStandardBlock(this, i, j, k);
			}
		}
		else if (meta == 3 || meta == 11)
		{
			renderer.setRenderBounds(
					 6/16D, 0/16D,  4/16D,
					11/16D, 3/16D, 12/16D );
			renderer.renderStandardBlock(this, i, j, k);
			
			if (!hasSnowOnTop)
			{
			renderer.setRenderBounds(
					3/16D, 0/16D, 3/16D,
					7/16D, 2/16D, 7/16D );
			renderer.renderStandardBlock(this, i, j, k);
			
			renderer.setRenderBounds(
					11/16D, 0/16D, 7/16D,
					12/16D, 2/16D, 10/16D );
			renderer.renderStandardBlock(this, i, j, k);
			
			renderer.setRenderBounds(
					3/16D, 0/16D, 11/16D,
					5/16D, 1/16D, 14/16D );
			renderer.renderStandardBlock(this, i, j, k);
			}
		}
		else if (meta == 4 || meta == 12)
		{
			renderer.setRenderBounds(
					 6/16D, 0/16D,  3/16D,
					14/16D, 5/16D, 10/16D );
			renderer.renderStandardBlock(this, i, j, k);
			
			renderer.setRenderBounds(
					2/16D, 0/16D, 9/16D,
					8/16D, 3/16D, 13/16D );
			renderer.renderStandardBlock(this, i, j, k);
		}
		else if (meta == 5 || meta == 13)
		{
			renderer.setRenderBounds(
					 5/16D, 0/16D,  5/16D,
					11/16D, 7/16D, 13/16D );
			renderer.renderStandardBlock(this, i, j, k);
			
			renderer.setRenderBounds(
					3/16D, 0/16D, 3/16D,
					6/16D, 4/16D, 8/16D );
			renderer.renderStandardBlock(this, i, j, k);
			
			renderer.setRenderBounds(
					11/16D, 0/16D, 7/16D,
					13/16D, 3/16D, 12/16D );
			renderer.renderStandardBlock(this, i, j, k);
		}
		else if (meta == 6 || meta == 14)
		{
			renderer.setRenderBounds(
					 4/16D, 0/16D,  6/16D,
					12/16D, 5/16D, 10/16D );
			renderer.renderStandardBlock(this, i, j, k);
			
			if (!hasSnowOnTop)
			{
			renderer.setRenderBounds(
					8/16D, 0/16D, 3/16D,
					13/16D, 2/16D, 9/16D );
			renderer.renderStandardBlock(this, i, j, k);
			}
			
			renderer.setRenderBounds(
					6/16D, 0/16D, 9/16D,
					11/16D, 6/16D, 13/16D );
			renderer.renderStandardBlock(this, i, j, k);
			
			renderer.setRenderBounds(
					3/16D, 0/16D, 8/16D,
					7/16D, 3/16D, 12/16D );
			renderer.renderStandardBlock(this, i, j, k);
		}
		else if (meta == 7 || meta == 15)
		{
			renderer.setRenderBounds(
					 3/16D, 0/16D,  3/16D,
					11/16D, 8/16D, 11/16D );
			renderer.renderStandardBlock(this, i, j, k);
			
			renderer.setRenderBounds(
					2/16D, 0/16D, 2/16D,
					7/16D, 6/16D, 7/16D );
			renderer.renderStandardBlock(this, i, j, k);
			
			renderer.setRenderBounds(
					6/16D, 0/16D, 6/16D,
					14/16D, 5/16D, 14/16D );
			renderer.renderStandardBlock(this, i, j, k);
			
			if (!hasSnowOnTop)
			{
			renderer.setRenderBounds(
					12/16D, 0/16D, 1/16D,
					15/16D, 2/16D, 4/16D );
			renderer.renderStandardBlock(this, i, j, k);
			}
		}
		return true;
	}


}
