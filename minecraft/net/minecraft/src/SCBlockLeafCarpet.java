package net.minecraft.src;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.prupe.mcpatcher.cc.ColorizeBlock;

public class SCBlockLeafCarpet extends FCBlockGroundCover {

	protected SCBlockLeafCarpet(int blockID) {
		super(blockID, Material.wood);
		InitBlockBounds( 0F, 0F, 0F, 1F, 2/16F, 1F);
		
        setHardness( 1/16F );
        SetShovelsEffectiveOn(false);
        SetAxesEffectiveOn();
 
		setCreativeTab(CreativeTabs.tabDecorations);
		setStepSound(soundGrassFootstep);
		setUnlocalizedName("SCBlockLeafCarpet");
	}
	
	@Override
	public void getSubBlocks(int blockID, CreativeTabs tab, List list) {
		for (int i = 0; i < carpetTypes.length; i++)
		{
			list.add(new ItemStack(blockID, 1, i));
		}
		
	}
	
    @Override
    public boolean canPlaceBlockAt( World world, int i, int j, int k )
    {
    	int iBlockBelowID = world.getBlockId( i, j - 1, k );
    	Block blockBelow = Block.blocksList[iBlockBelowID];
    	
    	if ( blockBelow != null )
    	{
    		return blockBelow.CanGroundCoverRestOnBlock( world, i, j - 1, k ) || blockBelow.HasLargeCenterHardPointToFacing(world, i, j -1, k, 1);
    	}
    	
    	return false;
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
	public int damageDropped(int meta) {
		return meta;
	}
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		return new AxisAlignedBB(
				0,0,0,
				1,2/16F,1);
	}
	
    @Override
    public boolean ShouldRenderNeighborFullFaceSide( IBlockAccess blockAccess, int i, int j, int k, int iNeighborSide )
    {
    	if (!Block.leaves.graphicsLevel)
    	{
        	if ( iNeighborSide == 1 )
        	{
    			int iBlockBelowID = blockAccess.getBlockId( i, j - 1, k );
    			
    			if ( iBlockBelowID != 0 && blocksList[iBlockBelowID].GroundCoverRestingOnVisualOffset( 
    				blockAccess, i, j - 1, k ) > -m_fVisualHeight )
    			{
    				return false;
    			}
        	}
        	
        	return true;
    	}

		return true;
    } 
    
    public boolean IsGroundCoverRestingOnBlockForRender( IBlockAccess blockAccess, int i, int j, int k )
    {
    	Block blockAbove = Block.blocksList[blockAccess.getBlockId( i, j + 1, k )];
    	
    	if ( blockAbove != null )
    	{
    		if ( blockAbove.IsGroundCover() )
    		{
    			return true;
    		}
    		else if ( blockAbove.GroundCoverRestingOnVisualOffset( blockAccess, i, j + 1, k ) < -0.99F )
    		{
    			Block block2Above = blockAbove = Block.blocksList[blockAccess.getBlockId( i, j + 2, k )];
    	    	
    	    	if ( block2Above != null && block2Above.IsGroundCover() )
    	    	{
	    			return true;
    	    	}
    		}
    	}
    	
    	return false;
    }
	
	public final static int OAK = 0;
	public final static int SPRUCE = 1;
	public final static int BIRCH = 2;
	public final static int JUNGLE = 3;
	public final static int BLOOD = 4;
	
	public final static int APPLE = 5;
	public final static int CHERRY = 6;
	public final static int LEMON = 7;
	public final static int OLIVE = 8;
	
	public final static int FLOWER_APPLE = 9;
	public final static int FLOWER_CHERRY = 10;
	public final static int FLOWER_LEMON = 11;
	public final static int FLOWER_OLIVE = 12;
	
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
	{
    	boolean isRestingOnBlock = IsGroundCoverRestingOnBlockForRender(blockAccess, x, y, z);
    	
    	if (isRestingOnBlock)
    	{
        	return getColorMultiplier(blockAccess, x, z, blockAccess.getBlockMetadata(x, y+1, z));
    	}
    	else return getColorMultiplier(blockAccess, x, z, blockAccess.getBlockMetadata(x, y, z));
	}

	protected int getColorMultiplier(IBlockAccess blockAccess, int x, int z, int meta)
	{
		if (meta == OAK)
		{
			int var6 = 0;
		    int var7 = 0;
		    int var8 = 0;

		    for (int var9 = -1; var9 <= 1; ++var9)
		    {
		        for (int var10 = -1; var10 <= 1; ++var10)
		        {
		            int var11 = blockAccess.getBiomeGenForCoords(x + var10, z + var9).getBiomeFoliageColor();
		            var6 += (var11 & 16711680) >> 16;
		            var7 += (var11 & 65280) >> 8;
		            var8 += var11 & 255;
		        }
		    }

		    return (var6 / 9 & 255) << 16 | (var7 / 9 & 255) << 8 | var8 / 9 & 255;
		}
		else if (meta == SPRUCE) 
		{
			return ColorizerFoliage.getFoliageColorPine();
		}
		else if (meta == BIRCH)
		{
			return ColorizerFoliage.getFoliageColorBirch();
		}
		else if (meta == JUNGLE)
		{
			return -11285961;
		}
		else if (meta == BLOOD)
		{
			return 0xD81F1F;
		}
		else
		{
			if (meta > BLOOD) return 0xFFFFFF;
			else return -7226023;
		}
	}
    
    public static int colorMultiplierForItemRender(int meta)
	{
		if (meta == SPRUCE) 
		{
			return ColorizerFoliage.getFoliageColorPine();
		}
		else if (meta == BIRCH)
		{
			return ColorizerFoliage.getFoliageColorBirch();
		}
		else if (meta == JUNGLE)
		{
			return -11285961;
		}
		else if (meta == BLOOD)
		{
			return 0xD81F1F;
		}
		else
		{
			if (meta > BLOOD) return 0xFFFFFF;
			else return -7226023;
		}
	}
	
	public static String[] carpetTypes = {
			"oak", "spruce", "birch", "jungle",
			"blood", 
			"apple", "cherry", "lemon", "olive",
			"flower_apple", "flower_cherry", "flower_lemon", "flower_olive"};
		
    Icon[] leaves = new Icon[16];
    private String[] leavesTex = {
    		"leaves", "leaves_spruce", "leaves", "leaves_jungle",
    		"leaves", 
    		"SCBlockFruitTreeLeaves_apple", "SCBlockFruitTreeLeaves_cherry", "SCBlockFruitTreeLeaves_lemon", "SCBlockFruitTreeLeaves_olive",
    		"SCBlockFruitTreeLeaves_apple", "SCBlockFruitTreeLeaves_cherry", "SCBlockFruitTreeLeaves_lemon", "SCBlockFruitTreeLeaves_olive" };
    
    Icon[] flower = new Icon[4];
    private String[] flowerTex = {
    	"SCBlockFruitTreeLeavesFlowers_apple", "SCBlockFruitTreeLeavesFlowers_cherry", "SCBlockFruitTreeLeavesFlowers_lemon", "SCBlockFruitTreeLeavesFlowers_olive"	};
    
	
    Icon[] opaqueLeaves = new Icon[16];
    private String[] opaqueLeavesTex = {
    		"leaves_opaque", "leaves_spruce_opaque", "leaves_opaque", "leaves_jungle_opaque",
    		"leaves_opaque",
    		"SCBlockFruitTreeLeaves_appleOpaque", "SCBlockFruitTreeLeaves_cherryOpaque", "SCBlockFruitTreeLeaves_lemonOpaque", "SCBlockFruitTreeLeaves_oliveOpaque",
    		"SCBlockFruitTreeLeaves_appleOpaque", "SCBlockFruitTreeLeaves_cherryOpaque", "SCBlockFruitTreeLeaves_lemonOpaque", "SCBlockFruitTreeLeaves_oliveOpaque"};
    
    
	@Override
	public void registerIcons(IconRegister register)
	{
    	for (int i = 0; i < carpetTypes.length; i++)
    	{
    		leaves[i] = register.registerIcon(leavesTex[i]);
    		opaqueLeaves[i] = register.registerIcon(opaqueLeavesTex[i]);
    	}
    	
    	for (int i=0; i < flower.length; i++)
    	{
    		flower[i] = register.registerIcon(flowerTex[i]);
    	}
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		
		if (!Block.leaves.graphicsLevel) //fast graphics
		{
			return opaqueLeaves[meta];
		}
		
		return leaves[meta];
	}
	
    @Override
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
    	IBlockAccess blockAccess = renderer.blockAccess;
    	int meta = blockAccess.getBlockMetadata(i, j, k);
    	
        Tessellator tess = Tessellator.instance;
        tess.setBrightness(this.getMixedBrightnessForBlock(renderer.blockAccess, i, j, k));
        float var6 = 1.0F;
        int var7 = this.colorMultiplier(blockAccess, i, j, k);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;
    	tess.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
    	
		Icon icon = getIcon(0,meta);
		
    	// test to prevent momentary display above partial blocks that have just been destroyed
    	
    	renderCarpet(renderer, i, j, k, blockAccess, icon);
    
    	return true;
    }
    
    @Override
    public void RenderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean bFirstPassResult) {
    	IBlockAccess blockAccess = renderer.blockAccess;
    	int meta = blockAccess.getBlockMetadata(i, j, k);
    	
    	if (meta > OLIVE)
    	{
    		if (meta == FLOWER_APPLE) renderCarpet(renderer, i, j, k, blockAccess, flower[0]);
    		if (meta == FLOWER_CHERRY) renderCarpet(renderer, i, j, k, blockAccess, flower[1]);
    		if (meta == FLOWER_LEMON) renderCarpet(renderer, i, j, k, blockAccess, flower[2]);
    		if (meta == FLOWER_OLIVE) renderCarpet(renderer, i, j, k, blockAccess, flower[3]);
    	}
    }

	protected void renderCarpet(RenderBlocks renderer, int i, int j, int k, IBlockAccess blockAccess, Icon icon) {
		if ( blockAccess.getBlockId( i, j - 1, k ) != 0 )
    	{
	    	float fVisualOffset = 0F;
	    	
	    	int iBlockBelowID = blockAccess.getBlockId( i, j - 1, k );
	    	Block blockBelow = Block.blocksList[iBlockBelowID];
	    	
	    	int iBlockHeight = 1;
	    	
	    	if ( blockBelow != null )
	    	{
	    		fVisualOffset = blockBelow.GroundCoverRestingOnVisualOffset( blockAccess, i, j - 1, k );
	    		
	    		if ( fVisualOffset < 0.0F )
	    		{
	    			j -= 1;
	    			
	    			fVisualOffset += 1F;
	    		}
	    		
	    		
	    	}
	    	
	    	float fHeight = m_fVisualHeight * iBlockHeight;
	    	
	    	renderer.setRenderBounds( 0F, fVisualOffset, 0F, 
	    		1F, fHeight + fVisualOffset, 1F );
	    


	    	
	    	FCClientUtilsRender.RenderStandardBlockWithTexture( renderer, SCDefs.leafCarpet , i, j, k, icon );
    	}
	}
	
	@Override
	public void RenderBlockAsItem(RenderBlocks renderer, int damage, float brightness) {
        int color = colorMultiplierForItemRender(damage);
        
        float var8 = (float)(color >> 16 & 255) / 255.0F;
        float var9 = (float)(color >> 8 & 255) / 255.0F;
        float var10 = (float)(color & 255) / 255.0F;
        
        GL11.glColor4f(var8 * brightness, var9 * brightness, var10 * brightness, 1.0F);
		
		renderer.setRenderBounds(
        		0, 0, 0,
        		1, 2/16F,1);
		FCClientUtilsRender.RenderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, getIcon(0,damage));
	}

}
