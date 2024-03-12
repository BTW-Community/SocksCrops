//package net.minecraft.src;
//
//import java.util.List;
//import java.util.Random;
//
//import com.prupe.mcpatcher.cc.ColorizeBlock;
//import com.prupe.mcpatcher.cc.Colorizer;
//
//public class SCBlockCauldron extends FCBlockCookingVessel {
//
////	private final int EMPTY = 0;
////	private final int HALF_EMPTY = 1;
////	private final int HALF_FULL = 2;
////	private final int FULL = 3;
//	
//	public SCBlockCauldron(int iBlockID) {
//		super(iBlockID, Material.iron);
//		setHardness( 3.5F );
//        setResistance( 10F );
//        
//        setStepSound( soundMetalFootstep );
//	}
//
//	@Override
//	public TileEntity createNewTileEntity(World var1) {
//		// TODO Auto-generated method stub
//		return new SCTileEntityCauldron();
//	}
//	
//	@Override
//	public int idPicked(World par1World, int par2, int par3, int par4) {
//		// TODO Auto-generated method stub
//		return Item.cauldron.itemID;
//	}
//	
//	
//
//	//------------- FCBlockCookingVessel -------------//
//
//	@Override
//	protected void ValidateFireUnderState( World world, int i, int j, int k )
//	{
//		// FCTODO: Move this to parent class
//		
//		if ( !world.isRemote )
//		{
//			TileEntity tileEnt = world.getBlockTileEntity( i, j, k );
//			
//			if ( tileEnt instanceof FCTileEntityCrucible )
//			{
//				FCTileEntityCrucible tileEntityCrucible = 
//	            	(FCTileEntityCrucible)tileEnt;
//	            
//	            tileEntityCrucible.ValidateFireUnderType();            
//			}
//		}
//	}
//	
//	@Override
//	protected int GetContainerID()
//	{
//		return FCBetterThanWolves.fcCrucibleContainerID;
//	}
//	
//	//------------- Class Specific Methods -------------//
//	
//	@Override
//    public void onEntityCollidedWithBlock( World world, int i, int j, int k, Entity entity )
//    {
//		if ( world.isRemote )
//		{
//			// don't collect items on the client, as it's dependent on the state of the inventory
//			
//			return;
//		}
//		
//        List collisionList = null;
//        
//        if ( GetMechanicallyPoweredFlag( world, i, j, k ) )
//        {
//        	// tilted blocks can't collect
//        	
//        	return;
//        }
//        
//        // check for items within the collection zone       
//        
//        collisionList = world.getEntitiesWithinAABB( EntityItem.class, 
//    		AxisAlignedBB.getAABBPool().getAABB( (float)i, (float)j + m_dCollisionBoxHeight, (float)k, 
//				(float)(i + 1), (float)j + m_dCollisionBoxHeight + 0.05F, (float)(k + 1)) );
//
//    	if ( collisionList != null && collisionList.size() > 0 )
//    	{
//    		TileEntity tileEnt = world.getBlockTileEntity( i, j, k );
//    		
//    		if ( !( tileEnt instanceof IInventory ) )
//    		{
//    			return;
//    		}
//    	
//            IInventory inventoryEntity = (IInventory)tileEnt;
//            
//            for ( int listIndex = 0; listIndex < collisionList.size(); listIndex++ )
//            {
//	    		EntityItem targetEntityItem = (EntityItem)collisionList.get( listIndex );
//	    		
//		        if ( !targetEntityItem.isDead )
//		        {
//        			if ( FCUtilsInventory.AddItemStackToInventory( inventoryEntity, targetEntityItem.getEntityItem() ) )
//        			{
//			            world.playSoundEffect( (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, 
//			            		"random.pop", 0.25F, 
//			            		((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
//			            
//			            targetEntityItem.setDead();			            
//        			}
//		        }		        
//            }
//    	}
//    }
//	
//	//----------- Client Side Functionality -----------//
//    
//	private Icon cauldronInner;
//    private Icon cauldronTopIcon;
//    private Icon cauldronBottomIcon;
//
//    @Override
//	public Icon getIcon(int side, int meta)
//    {
//        return side == 1 ? this.cauldronTopIcon :
//        	(side == 0 ? this.cauldronBottomIcon :
//        		this.blockIcon);
//    }
//	
//    private Icon barrelTop;
//    private Icon barrelSide;
//    private Icon juice;
//    private Icon water;
//    private Icon milk;
//    
//	@Override
//	public void registerIcons(IconRegister register)
//    {
//        this.cauldronInner = register.registerIcon("cauldron_inner");
//        this.cauldronTopIcon = register.registerIcon("cauldron_top");
//        this.cauldronBottomIcon = register.registerIcon("cauldron_bottom");
//        this.blockIcon = register.registerIcon("cauldron_side");
//        
//        barrelTop = register.registerIcon( "fcBlockBarrel_top" );
//        barrelSide = register.registerIcon( "fcBlockBarrel_side" );
//        juice = register.registerIcon("SCJuiceApple");
//        water = register.registerIcon("water");
//        milk = register.registerIcon("fcBlockMilk");
//    }
//	
//	private Icon getLiquidTypeIcon(RenderBlocks renderer, int i, int j, int k)
//	{
//		Icon liquidIcon;
//		int liquidType = ((SCTileEntityCauldron) renderer.blockAccess.getBlockTileEntity(i, j, k)).liquidType;
//		
//		
//		if (liquidType == SCTileEntityCauldron.MILK)
//		{
//			return liquidIcon = milk;
//		}
//		else if (liquidType == SCTileEntityCauldron.WATER) return liquidIcon = Block.waterStill.blockIcon;
//		
//		else return Block.waterStill.blockIcon;
//	}
//
//    public Icon getTexture(String string)
//    {
//        return string == "cauldron_inner" ? cauldronInner : (string == "cauldron_bottom" ? cauldronBottomIcon : null);
//    }
//
//    @Override
//    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
//    {
//    	renderer.setRenderBounds( GetBlockBoundsFromPoolBasedOnState( 
//    			renderer.blockAccess, i, j, k ) );
//    	
//    	renderer.renderStandardBlock(this, i, j, k);
//    	
//    	IBlockAccess blockAccess = renderer.blockAccess;
//    	
//    	if ( GetFacing( blockAccess, i, j, k ) == 1 )
//    	{
//	        // render contents if upright
//	        
//    		TileEntity tileEntity = blockAccess.getBlockTileEntity( i, j, k );
//    		
//    		if ( tileEntity instanceof SCTileEntityCauldron )
//    		{    
//    			
//    			int fillLevel = ((SCTileEntityCauldron) renderer.blockAccess.getBlockTileEntity(i, j, k)).liquidFillLevel;
//    			
//    			if (fillLevel > 0)
//    			{
//    		    	renderer.setOverrideBlockTexture( getLiquidTypeIcon(renderer, i, j, k) );
//    		    	renderer.setRenderBounds(2/16D, 2/16D, 2/16D, 14/16D, fillLevel/16D, 14/16D);
//    		    	renderer.renderStandardBlock(this, i, j, k);
//    		    	renderer.clearOverrideBlockTexture();
//    			}
//    			
//    			
//    			SCTileEntityCauldron cisternEntity = (SCTileEntityCauldron)blockAccess.getBlockTileEntity( i, j, k );
//    	        
//    	        short iItemCount = cisternEntity.m_sStorageSlotsOccupied;    	        
//		        
//		        if ( iItemCount > 0 )
//		        {        
//		        	float fHeightRatio = (float)iItemCount / 27.0F;
//		        	
//		            float fBottom = 3.0F / 16F;
//		            
//		            float fTop = fBottom + ( 1.0F / 16F ) + 
//		            	( ( ( 1.0F - ( 2.0F / 16.0F ) ) - ( fBottom + ( 1.0F / 16F ) ) ) * fHeightRatio );
//		
//		            renderer.setRenderBounds( 0.125F, fBottom, 0.125F, 
//		    	    		0.875F, fTop, 0.875F );
//		            
//		            if ( blockAccess.getBlockId( i, j - 1, k ) == FCBetterThanWolves.fcBlockFireStoked.blockID )
//		            {
//		                FCClientUtilsRender.RenderStandardBlockWithTexture( renderer, this, i, j, k, Block.gravel.blockIcon ); // lava texture
////		                FCClientUtilsRender.RenderStandardBlockWithTexture( renderer, this, i, j, k, m_IconContentsHeated ); // lava texture
//		            }
//		            else
//		            {
//		            	FCClientUtilsRender.RenderStandardBlockWithTexture( renderer, this, i, j, k, Block.gravel.blockIcon );            
////		                FCClientUtilsRender.RenderStandardBlockWithTexture( renderer, this, i, j, k, m_IconContents );            
//		            }            
//		        }
//    		}
//    	}
//    	return true;
//
////        
////    	return this.renderBlockCauldron( renderer, i, j, k );
//    }
//    
//    public boolean renderBlockCauldron(RenderBlocks renderer, int x, int y, int z)
//    {
//    	renderer.renderStandardBlock(this, x, y, z);
//        Tessellator tess = Tessellator.instance;
//        tess.setBrightness(this.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
//        float var6 = 1.0F;
//        int var7 = this.colorMultiplier(renderer.blockAccess, x, y, z);
//        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
//        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
//        float var10 = (float)(var7 & 255) / 255.0F;
//        float var12;
//
//        tess.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
//        Icon icon = this.getBlockTextureFromSide(2);
//        ColorizeBlock.computeWaterColor();
//        tess.setColorOpaque_F(Colorizer.setColor[0], Colorizer.setColor[1], Colorizer.setColor[2]);
//        // FCMOD: Changed to eliminate visual gaps around edge
//        //var12 = 0.125F;
//        var12 = 0.124F;
//        // END FCMOD
//        renderer.renderFaceXPos(this, (double)((float)x - 1.0F + var12), (double)y, (double)z, icon);
//        renderer.renderFaceXNeg(this, (double)((float)x + 1.0F - var12), (double)y, (double)z, icon);
//        renderer.renderFaceZPos(this, (double)x, (double)y, (double)((float)z - 1.0F + var12), icon);
//        renderer.renderFaceZNeg(this, (double)x, (double)y, (double)((float)z + 1.0F - var12), icon);
//        Icon var17 = this.getTexture("cauldron_inner");
//        renderer.renderFaceYPos(this, (double)x, (double)((float)y - 1.0F + 0.25F), (double)z, var17);
//        renderer.renderFaceYNeg(this, (double)x, (double)((float)y + 1.0F - 0.75F), (double)z, var17);
//        int var14 = renderer.blockAccess.getBlockMetadata(x, y, z);
//
////        if (var14 > 0)
////        {
////            Icon var15 = BlockFluid.func_94424_b("water");
////
////            if (var14 > 3)
////            {
////                var14 = 3;
////            }
////
////            renderer.renderFaceYPos(this, (double)par2, (double)((float)par3 - 1.0F + (6.0F + (float)var14 * 3.0F) / 16.0F), (double)par4, var15);
////        }
//
//        return true;
//    }
//
//
////	public void randomDisplayTick(World world, int x, int y, int z, Random random)
////    {
////		if ( world.isAirBlock(x, y + 1, z) && world.getBlockId(x, y - 1, z) == Block.fire.blockID && world.getBlockMetadata(x, y, z) == FULL)
////    	{
////			double xPos = (double)((float)x + 0.4F + random.nextFloat() * 0.2F);
////	        double yPos = (double)((float)y + 0.7F + random.nextFloat() * 0.3F);
////	        double zPos = (double)((float)z + 0.4F + random.nextFloat() * 0.2F);
////	        world.spawnParticle("fcwhitesmoke", xPos, yPos, zPos, 0.0D, 0.0D, 0.0D);
////	        
////	        xPos = (double)((float)x + 0.4F + random.nextFloat() * 0.2F);
////	        yPos = (double)((float)y + 0.1F + random.nextFloat() * 0.3F);
////	        zPos = (double)((float)z + 0.4F + random.nextFloat() * 0.2F);
////	        world.spawnParticle("bubble", xPos, yPos + 1 , zPos, 0.0D, 0.0D, 0.0D);
////    	}
////        
////    }
//	
//}
