// FCMOD

package net.minecraft.src;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.prupe.mcpatcher.cc.ColorizeBlock;
import com.prupe.mcpatcher.mal.block.RenderBlocksUtils;

import net.minecraft.client.Minecraft; // client only

public class SCBlockGrassNutrition extends FCBlockGrass
{
    // global constants
	
    public static final int m_iGrassSpreadFromLightLevel = 11;
    public static final int m_iGrassSpreadToLightLevel = 11; // 7 previously, 4 vanilla
    public static final int m_iGrassSurviveMinimumLightLevel = 9; // 4 previously
    
    public SCBlockGrassNutrition( int iBlockID )
    {
    	super( iBlockID );
    	
    	setHardness( 0.6F );
    	SetShovelsEffectiveOn();
    	SetHoesEffectiveOn();
    	
    	setStepSound(soundGrassFootstep);
    	
    	setUnlocalizedName("grass_top");    	
    }
    
    private int getNutritionLevel( World world, int i, int j, int k) {
    	int meta = world.getBlockMetadata(i, j, k);
    	
    	if (meta == 0)
    	{
    		return 3;
    	}
    	else if (meta == 1) {
    		return 2;
    	}
    	else if (meta == 2 ) {
    		return 1;
    	}
    	else return 0;

	}

    @Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
    	//super.updateTick(world, i, j, k, rand);
    	int thisMeta = world.getBlockMetadata(i, j, k);
    	
    	int iBlockAboveID = world.getBlockId( i, j + 1, k );
    	Block blockAbove = Block.blocksList[iBlockAboveID];
    	int iBlockAboveMaxNaturalLight = world.GetBlockNaturalLightValueMaximum( i, j + 1, k );
    	int iBlockAboveCurrentNaturalLight = iBlockAboveMaxNaturalLight - world.skylightSubtracted;
    	
        if ( iBlockAboveMaxNaturalLight < m_iGrassSurviveMinimumLightLevel || Block.lightOpacity[iBlockAboveID] > 2 ||
        	( blockAbove != null && !blockAbove.GetCanGrassGrowUnderBlock( world, i, j + 1, k, false ) ) )
        {
        	// convert back to dirt in low light
        	
            world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID , world.getBlockMetadata(i, j, k));
        }
        else if ( iBlockAboveCurrentNaturalLight >= m_iGrassSpreadFromLightLevel)
        {
        	CheckForGrassSpreadFromLocation( world, i, j, k );
        }

        if (thisMeta > 0)
        {
        	attemptToGrow(world, i, j, k, rand);
        }
    }
    
    @Override
    public int idDropped( int iMetadata, Random rand, int iFortuneModifier )
    {
    	return SCDefs.dirtLooseNutrition.blockID;
    }
    
    public int damageDropped(int meta)
    {
       	return meta;
    }
    
	@Override
	public boolean DropComponentItemsOnBadBreak( World world, int i, int j, int k, int iMetadata, float fChanceOfDrop )
	{
		DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemPileDirt.itemID, 6, 0, fChanceOfDrop );
		//XXX
		
		return true;
	}
	
    @Override
    public void OnBlockDestroyedWithImproperTool( World world, EntityPlayer player, int i, int j, int k, int iMetadata )
    {
    	super.OnBlockDestroyedWithImproperTool( world, player, i, j, k, iMetadata );
    	
    	OnDirtDugWithImproperTool( world, i, j, k );    	
    }
    
	@Override
    public void onBlockDestroyedByExplosion( World world, int i, int j, int k, Explosion explosion )
    {
		super.onBlockDestroyedByExplosion( world, i, j, k, explosion );
		
		OnDirtDugWithImproperTool( world, i, j, k );    	
    }
	
    @Override
    protected void OnNeighborDirtDugWithImproperTool( World world, int i, int j, int k, 
    	int iToFacing )
    {
    	// only disrupt grass/mycelium when block below is dug out
    	
		if ( iToFacing == 0 )
		{
			world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID , world.getBlockMetadata(i, j, k));
		}    		
    }
    
    @Override
    public boolean CanBePistonShoveled( World world, int i, int j, int k )
    {
    	return true;
    }
    
    @Override
    public boolean CanBeGrazedOn( IBlockAccess blockAccess, int i, int j, int k, 
    	EntityAnimal animal )
    {
    	return true;
    }
    
    @Override
    public void OnGrazed( World world, int i, int j, int k, EntityAnimal animal )
    {
        if ( !animal.GetDisruptsEarthOnGraze() )
        {
        	world.setBlockWithNotify( i, j, k, Block.dirt.blockID ); //TODO
        }
        else
        {
        	world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID , world.getBlockMetadata(i, j, k));
        	
        	NotifyNeighborsBlockDisrupted( world, i, j, k );
        }
    }
    
    @Override
	public void OnVegetationAboveGrazed( World world, int i, int j, int k, EntityAnimal animal )
	{
        if ( animal.GetDisruptsEarthOnGraze() )
        {
        	world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID , world.getBlockMetadata(i, j, k));
        	
        	NotifyNeighborsBlockDisrupted( world, i, j, k );
        }
	}
    
	@Override
    public boolean CanReedsGrowOnBlock( World world, int i, int j, int k )
    {
    	return true;
    }
    
	@Override
    public boolean CanSaplingsGrowOnBlock( World world, int i, int j, int k )
    {
    	return true;
    }
    
	@Override
    public boolean CanWildVegetationGrowOnBlock( World world, int i, int j, int k )
    {
    	return true;
    }
    
	@Override
    public boolean GetCanBlightSpreadToBlock( World world, int i, int j, int k, int iBlightLevel )
    {
		return true;
    }

	@Override
    public boolean CanConvertBlock( ItemStack stack, World world, int i, int j, int k )
    {
    	return stack != null && stack.getItem() instanceof FCItemHoe;
    }
	
    @Override
    public boolean ConvertBlock( ItemStack stack, World world, int i, int j, int k, int iFromSide )
    {
    	int nutrition = getNutritionLevel(world, i, j, k);
    	if (nutrition == 3)
    	{
    		world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID, 0 );
    	}
    	else if (nutrition == 2)
    	{
    		world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID, 1 );
    	}
    	else if (nutrition == 1)
    	{
    		world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID, 2 );
    	}
    	else if (nutrition == 0)
    	{
    		world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID, 3);
    	}
    	
    	

    	if ( !world.isRemote )
		{
            world.playAuxSFX( 2001, i, j, k, blockID ); // block break FX
            
            if ( world.rand.nextInt( 25 ) == 0 )
            {
	            FCUtilsItem.EjectStackFromBlockTowardsFacing( world, i, j, k, 
	            	new ItemStack( FCBetterThanWolves.fcItemHempSeeds ), iFromSide );
            }
		}
    	
    	return true;
    }
    

    public boolean attemptToGrow( World world, int i, int j, int k, Random rand)
    {
        int iTimeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );
		
		if ( world.isAirBlock( i, j + 1, k) && rand.nextFloat() <= 0.1F)
		{
	        if ( iTimeOfDay > 14000 && iTimeOfDay < 22000 )
	        {
		        // night
	
		    	int meta = world.getBlockMetadata(i, j, k);
		    	
		    	if (meta == 1)
		    	{
		    		world.setBlockMetadataWithNotify( i, j, k, 0);
		    		return true;
		    	}
		    	else if (meta == 2)
		    	{
		    		world.setBlockMetadataWithNotify( i, j, k, 1);
		    		return true;
		    	}
		    	else if (meta == 3)
		    	{
		    		world.setBlockMetadataWithNotify( i, j, k, 2);
		    		return true;
		    	}
		    	
	        }
		}
		return false;
    }
    
    
    //------------- Class Specific Methods ------------//    
    
    public static void CheckForGrassSpreadFromLocation( World world, int i, int j, int k )
    {
    	if ( world.provider.dimensionId != 1 &&
    		!FCBlockGroundCover.IsGroundCoverRestingOnBlock( world, i, j, k ) )    		
    	{
        	// check for grass spread
        	
            int iTargetI = i + world.rand.nextInt(3) - 1;
            int iTargetJ = j + world.rand.nextInt(5) - 3;
            int iTargetK = k + world.rand.nextInt(3) - 1;
                            
            Block targetBlock = Block.blocksList[world.getBlockId( iTargetI, iTargetJ, iTargetK )];
            
            if ( targetBlock != null )
            {
            	targetBlock.AttempToSpreadGrassToBlock( world, iTargetI, iTargetJ, iTargetK );
            }
    	}
    }
    
	//----------- Client Side Functionality -----------//    
    
    private boolean m_bTempHasSnowOnTop; // temporary variable used by rendering
    
    // duplicate variables to parent class to avoid base class modification
    
    private Icon iconGrassTop;
    private Icon[] iconSnowSide;
    private Icon iconGrassSideOverlay;
    
    public static String[] nutritionLevelTextures = new String[] {"dirt", "SCBlockDirtDry_1", "SCBlockDirtDry_2", "SCBlockDirtDry_3"};
    private Icon[] trunkIconArray;
    
    public void registerIcons(IconRegister par1IconRegister)
    {
    	super.registerIcons( par1IconRegister );
    	
        this.iconGrassTop = par1IconRegister.registerIcon("grass_top");
        //this.iconSnowSide = par1IconRegister.registerIcon("snow_side");
        this.iconGrassSideOverlay = par1IconRegister.registerIcon("grass_side_overlay");
        
        iconSnowSide = new Icon[4];

        for (int iTextureID = 0; iTextureID < iconSnowSide.length; iTextureID++ )
        {
        	iconSnowSide[iTextureID] = par1IconRegister.registerIcon("SCBlockDirtSnow_" + iTextureID);
        }
        
        trunkIconArray = new Icon[nutritionLevelTextures.length];

        for (int iTextureID = 0; iTextureID < trunkIconArray.length; iTextureID++ )
        {
        	trunkIconArray[iTextureID] = par1IconRegister.registerIcon(nutritionLevelTextures[iTextureID]);
        }
    }
    
	private int color( IBlockAccess blockAccess, int i, int j, int k, int r, int g, int b) {

         for (int var8 = -1; var8 <= 1; ++var8)
         {
         	for (int var9 = -1; var9 <= 1; ++var9)
             {
                 int var10 = blockAccess.getBiomeGenForCoords(i + var9, k + var8).getBiomeGrassColor();
                 r += (var10 & 16711680) >> 16;
                 g += (var10 & 65280) >> 8;
                 b += var10 & 255;
             }
         }
         
         return (r / 9 & 255) << 16 | (g / 9 & 255) << 8 | b / 9 & 255;
	}
    
    @Override
    public int colorMultiplier( IBlockAccess blockAccess, int i, int j, int k)
    {
    	int biomeGrassColor = blockAccess.getBiomeGenForCoords(i,k).getBiomeGrassColor();
    	
    	if ( m_bTempHasSnowOnTop )
    	{
            return 16777215;
    	}
    	//ADDON
    	else if (blockAccess.getBlockMetadata(i, j, k) == 0)
    	{
    		return color(blockAccess, i, j, k, 0 , 0 , 0);
    	}
    	else if (blockAccess.getBlockMetadata(i, j, k) == 1)
    	{
    		return color(blockAccess, i, j, k, 150 , -25 , 0);
    	}
    	else if (blockAccess.getBlockMetadata(i, j, k) == 2)
    	{
    		return color(blockAccess, i, j, k, 300 , -50 , 0);
    	}
    	else if (blockAccess.getBlockMetadata(i, j, k) == 3)
    	{
    		return color(blockAccess, i, j, k, 400 , -100 , 0);
    	}

    	else return super.colorMultiplier( blockAccess, i, j, k );
    }
    
    @Override
    public Icon getBlockTexture( IBlockAccess blockAccess, int i, int j, int k, int iSide )
    {
    	Icon betterGrassIcon = RenderBlocksUtils.getGrassTexture(this, blockAccess, i, j, k, iSide, this.iconGrassTop);

        if (betterGrassIcon != null)
        {
            return betterGrassIcon;
        }
        else if ( iSide == 1 )
        {
            return iconGrassTop;
        }
        else if ( iSide == 0 )
        {
            return SCDefs.dirtLooseNutrition.getIcon(iSide, blockAccess.getBlockMetadata(i, j, k));
        }
        else if ( m_bTempHasSnowOnTop )
    	{
    		return iconSnowSide[blockAccess.getBlockMetadata(i, j, k)];
    	}
        
        int meta = blockAccess.getBlockMetadata(i, j, k);
        
        for (int x = meta; x <= 3;) {
			return trunkIconArray[x];
			
		}
        
		return blockIcon;
    }
    
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
    }
    
    @Override
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
    	IBlockAccess blockAccess = renderer.blockAccess;
    	
        renderer.setRenderBounds( 0D, 0D, 0D, 1D, 1D, 1D );
        
        m_bTempHasSnowOnTop = IsSnowCoveringTopSurface( blockAccess, i, j, k ); 
        
        if ( m_bTempHasSnowOnTop )
        {
        	return renderer.renderStandardBlock( this, i, j, k );
        }
        else
        {
	        int var5 = colorMultiplier( blockAccess, i, j, k );
	        
	        float var6 = (float)(var5 >> 16 & 255) / 255.0F;
	        float var7 = (float)(var5 >> 8 & 255) / 255.0F;
	        float var8 = (float)(var5 & 255) / 255.0F;
	
	        if ( Minecraft.isAmbientOcclusionEnabled() )
	        {
	        	//return renderer.renderGrassBlockWithAmbientOcclusion( this, i, j, k, var6, var7, var8, BlockGrass.getIconSideOverlay() );
	        	return renderer.renderGrassBlockWithColorMultiplier( this, i, j, k, var6, var7, var8, BlockGrass.getIconSideOverlay() );
	        }
	        else
	        {
	        	return renderer.renderGrassBlockWithColorMultiplier( this, i, j, k, var6, var7, var8, BlockGrass.getIconSideOverlay() );
	        }
        }
    }    
}