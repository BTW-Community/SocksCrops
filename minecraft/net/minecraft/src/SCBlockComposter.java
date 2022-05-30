package net.minecraft.src;

import java.awt.Color;
import java.util.Random;

import com.prupe.mcpatcher.cc.ColorizeBlock;

public class SCBlockComposter extends BlockContainer {
	
	protected static final SCModelBlockComposter model = new SCModelBlockComposter();
	
	protected SCBlockComposter(int blockID) {
        super(blockID, Material.wood);
        
        this.setHardness( 1.0F );
        
        this.SetAxesEffectiveOn();
        
        this.SetBuoyant();
        
        this.setStepSound( soundWoodFootstep );
        
        this.setUnlocalizedName("SCBlockComposter");
        
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new SCTileEntityComposter();
	}
	
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
		ItemStack heldStack = player.getCurrentEquippedItem();
		SCTileEntityComposter composter = (SCTileEntityComposter)world.getBlockTileEntity( i, j, k );
		
		if (heldStack != null && composter.isValidItem(heldStack.itemID))
		{
			composter.addStackToComposter(heldStack);
			
			composter.markBlockForUpdate();
			
			return true;
		}
		else if ( heldStack == null && world.getBlockMetadata(i, j, k) == 15 )
		{
			
			composter.setFillLevel(0);
			composter.setCookCounter(0);
							
			world.setBlockMetadata(i, j, k, 0);
			
			FCUtilsItem.EjectStackFromBlockTowardsFacing(world, i, j, k, new ItemStack(SCDefs.compostBlock, 1), iFacing);
			
			return true;
			
		}

		return false;
		
    }
	
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.randomDisplayTick(par1World, par2, par3, par4, par5Random);
        
        SCTileEntityComposter composter = (SCTileEntityComposter)par1World.getBlockTileEntity( par2, par3, par4 );
        
        if (composter.getFillLevel() == composter.maxFillLevel)
        {
            if (par5Random.nextInt(5) == 0)
            {
                par1World.spawnParticle("townaura", (double)((float)par2 + par5Random.nextFloat()), (double)((float)par3 + 1.1F), (double)((float)par4 + par5Random.nextFloat()), 0.0D, 0.0D, 0.0D);
            }
        }
        

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
		
	
	//----------- Client Side Functionality -----------//
	
	public static boolean secondPass;
	
	
	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int i, int j, int k) 
	{
		SCTileEntityComposter composter = (SCTileEntityComposter)blockAccess.getBlockTileEntity( i, j, k );
		int counter = composter.getCookCounter();
		
    	if ( !secondPass )
    	{
            return 16777215;
    	}
    	else if (blockAccess.getBlockMetadata(i, j, k) < 15)
    	{
        	for (int meta = 0; meta < 16; meta++) {
        		if (blockAccess.getBlockMetadata(i, j, k) == meta)
            	{
            		return color(blockAccess, i, j, k, meta * 80 , 0 , 0);
            	}
    		}
    	}    	
    	return super.colorMultiplier( blockAccess, i, j, k );
		
		
//		if ( !secondPass ) {
//			return 16777215; //0xffffff
//		}
//		else {
//			int var5 = 0;
//            int var6 = 0;
//            int var7 = 0;
//
//            for (int var8 = -1; var8 <= 1; ++var8)
//            {
//            	for (int var9 = -1; var9 <= 1; ++var9)
//                {
//                    int var10 = 7864072; //7864072 = 0x77ff08;
//                    
//                    if (blockAccess.getBlockMetadata(i, j, k) == 1)
//                    {
//                    	var10 = 16777215;
//                    }
//                    else var10 = 7864072;
//                    
//                    var5 += (var10 & 16711680) >> 16;
//                    var6 += (var10 & 65280) >> 8;
//                    var7 += var10 & 255;
//
//                }
//            }
//            
//            return (var5 / 9 & 255) << 16 | (var6 / 9 & 255) << 8 | var7 / 9 & 255;
//		}
	}
	
	private int color( IBlockAccess blockAccess, int i, int j, int k, int r, int g, int b) {

        for (int var8 = -1; var8 <= 1; ++var8)
        {
        	for (int var9 = -1; var9 <= 1; ++var9)
            {
                int var10 = 0x77ff08;
                r += (var10 & 16711680) >> 16;
                g += (var10 & 65280) >> 8;
                b += var10 & 255;
            }
        }
        
        return (r / 9 & 255) << 16 | (g / 9 & 255) << 8 | b / 9 & 255;
	}
	
    private Icon bottomIcon;
    private Icon sideIcon;
    private Icon topIcon;
    private Icon contentsIcon;

    @Override
    public void registerIcons( IconRegister iconRegister )
    {    
    	bottomIcon = iconRegister.registerIcon("SCBlockComposter_bottom");
    	sideIcon = iconRegister.registerIcon("SCBlockComposter_side");
    	//topIcon = iconRegister.registerIcon("SCBlockComposter_top");
    	contentsIcon = iconRegister.registerIcon("SCBlockComposter_contents");
        
    }
    
    @Override
    public Icon getIcon( int side, int meta )
    {
    	Icon icon = blockIcon;
    	
    	if (side == 0)
    	{
    		return bottomIcon;
    	}
    	else if (side == 1 )
    	{
    		return bottomIcon;
    	}
    	else return sideIcon;
    	
    }
    
    
    @Override
    public boolean RenderBlock( RenderBlocks renderBlocks, int i, int j, int k )
    {	
    	return model.RenderAsBlock( renderBlocks, this, i, j, k );	
    }
    

	@Override
	public void RenderBlockSecondPass(RenderBlocks renderBlocks, int i, int j, int k, boolean firstPassResult) {
		
		secondPass = true;
		
		double fillHeight = 0;
		SCTileEntityComposter composter = (SCTileEntityComposter)renderBlocks.blockAccess.getBlockTileEntity( i, j, k );
		if(composter != null)
		{
			fillHeight = composter.getFillLevel();
		}
		
		if (fillHeight != 0)
		{
	    	renderBlocks.setRenderBounds(3/32D, 2/16D, 3/32D, 
					29/32D,  2/16D + (fillHeight/19.69)/16D, 29/32D);
	    	FCClientUtilsRender.RenderStandardBlockWithTexture(renderBlocks, this, i, j, k, contentsIcon);
		}

		secondPass = false;
	}
	
	@Override
	public void RenderBlockAsItem(RenderBlocks renderBlocks, int iItemDamage, float fBrightness) {
		model.RenderAsItemBlock(renderBlocks, this, iItemDamage);
	}
}
