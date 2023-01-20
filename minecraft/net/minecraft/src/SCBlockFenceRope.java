package net.minecraft.src;

import java.util.Random;

public class SCBlockFenceRope extends FCBlockStakeString implements SCIRope {

	static public final double height = (2D / 16D);
	static public final double halfHeight = (height / 2D);

	static public final double selectionBoxHeight = (2D / 16D);
	static public final double selectionBoxHalfHeight = (selectionBoxHeight / 2D);

	public SCBlockFenceRope(int iBlockID) {
		super(iBlockID);

		this.setUnlocalizedName("fcBlockRope_side");
	}

	@Override
	public int idDropped(int iMetadata, Random random, int iFortuneModifier)
	{
		return FCBetterThanWolves.fcItemRope.itemID;
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int i, int j, int k, int iMetadata, float fChance, int iFortuneModifier)
	{
		if (!world.isRemote) {
			for (int iAxis = 0; iAxis < 3; iAxis++) {
				if (GetExtendsAlongAxisFromMetadata(iMetadata, iAxis)) {
					FCUtilsItem.DropSingleItemAsIfBlockHarvested(world, i, j, k,
							idDropped(iMetadata, world.rand, iFortuneModifier), damageDropped(iMetadata));
				}
			}

		}
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick)
	{
		ItemStack equippedItem = player.getCurrentEquippedItem();
		
//		if (equippedItem.itemID == Item.blazeRod.itemID)
//		{
//			
//			int meta = world.getBlockMetadata(i, j, k);
//			
//			if (meta < 16)
//			{
//				world.setBlockMetadataWithNotify(i, j, k, meta + 1);
//			}
//			else world.setBlockMetadataWithNotify(i, j, k, 0);
//			
//			return true;
//		}
		
		//hanging items
		if (player.isUsingSpecialKey() )
		{
			System.out.println("special");
			if (equippedItem != null && equippedItem.getItem() instanceof FCItemArmor)
			{
				world.setBlockAndMetadata(i, j, k, SCDefs.ropeHangingItems.blockID, world.getBlockMetadata(i, j, k));
				
				TileEntity te = world.getBlockTileEntity(i, j, k);
				
				System.out.println("rope");
				
				if (te != null && te instanceof SCTileEntityRopeHangingItems)
				{
					System.out.println("setting storage stack");
					SCTileEntityRopeHangingItems rope = (SCTileEntityRopeHangingItems) te;
					rope.setStorageStack(equippedItem.copy());
					equippedItem.stackSize--;
					
					int rotation = MathHelper.floor_double((double)(player.rotationYaw * 16.0F / 360.0F) + 0.5D) & 15;
					
					rope.setItemRotation(0, rotation);
					
					return true;
				}
			}
			
			return false;
		}
		
		
		if (equippedItem == null || equippedItem.itemID != FCBetterThanWolves.fcItemRope.itemID)
		{
			return false;
		}
		
		if (!GetExtendsAlongAxis(world, i, j, k, 1))
        {
			System.out.println("setting this block");
			
        	SetExtendsAlongAxis(world, i, j, k, 1, true); //this block
        	
            world.playSoundEffect( (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, 
        		FCBetterThanWolves.fcRopeBlock.stepSound.getStepSound(), 
        		(FCBetterThanWolves.fcRopeBlock.stepSound.getVolume() + 1.0F) / 2.0F, 
        		FCBetterThanWolves.fcRopeBlock.stepSound.getPitch() * 0.8F);

            equippedItem.stackSize--;

            return true;
        }
		
		
		//copied from FCItemRope
		for ( int tempj = j - 1; tempj >= 0; tempj-- )
    	{
			System.out.println("Setting block below");
			
    		int iTempBlockID = world.getBlockId( i, tempj, k );
    		
    		if (iTempBlockID == this.blockID && !getExtendsAlongAxisFromMetadata(world.getBlockMetadata(i, tempj, k), 1))
            {
            	SetExtendsAlongAxis(world, i, tempj, k, 1, true); //the rope below
            	
                world.playSoundEffect( (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, 
            		FCBetterThanWolves.fcRopeBlock.stepSound.getStepSound(), 
            		(FCBetterThanWolves.fcRopeBlock.stepSound.getVolume() + 1.0F) / 2.0F, 
            		FCBetterThanWolves.fcRopeBlock.stepSound.getPitch() * 0.8F);

                equippedItem.stackSize--;

                return true;
            }
    		
    		if ( FCUtilsWorld.IsReplaceableBlock( world, i, tempj, k ) )
    		{
    			int iMetadata = this.onBlockPlaced( world, i, tempj, k, iFacing, 0F, 0F, 0F, 0 );
    			
                iMetadata = this.PreBlockPlacedBy( world, i, tempj, k, iMetadata, player );            

                if( world.setBlockAndMetadataWithNotify( i, tempj, k, this.blockID, 2 ) )
                {	        			
        			this.onBlockPlacedBy( world, i, tempj, k, player, equippedItem );
        			
        			this.onPostBlockPlaced( world, i, tempj, k, iMetadata );

                    world.playSoundEffect( (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, 
                		FCBetterThanWolves.fcRopeBlock.stepSound.getStepSound(), 
                		(FCBetterThanWolves.fcRopeBlock.stepSound.getVolume() + 1.0F) / 2.0F, 
                		FCBetterThanWolves.fcRopeBlock.stepSound.getPitch() * 0.8F);

                    equippedItem.stackSize--;
                    
                    return true;
                }
                
                
                return false;
    		}
    		else if ( iTempBlockID != this.blockID )
    		{
    			return false;
    		}
    	}
		
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int iNeighborBlockID)
	{
		this.ValidateState(world, i, j, k);
	}

	public void ValidateState(World world, int i, int j, int k)
	{
//		System.out.println("validating state");
		
		if (GetExtendsAlongAxis(world, i, j, k, 1) )
		{
			Block blockAbove = Block.blocksList[world.getBlockId(i, j+1, k)];
			
			if (!HasValidAttachmentPointsAlongAxis(world, i, j, k, 0) && !HasValidAttachmentPointsAlongAxis(world, i, j, k, 2))
			{
				
				if (!GetExtendsAlongAxis(world, i, j + 1, k, 1))
				{
					//kill rope if the block above isn't a rope and doesn't extend in other directions
					world.setBlockWithNotify(i, j, k, 0);
					
					FCUtilsItem.DropSingleItemAsIfBlockHarvested(world, i, j, k, FCBetterThanWolves.fcItemRope.itemID,0);
				}
				
			}

		}
		else
		{
			int iValidAxisCount = 0;

			for (int iTempAxis = 0; iTempAxis < 3; iTempAxis++) {
				if (GetExtendsAlongAxis(world, i, j, k, iTempAxis)) {
					if (this.HasValidAttachmentPointsAlongAxis(world, i, j, k, iTempAxis)) {
						iValidAxisCount++;
					} else {
						
						Block blockAbove = Block.blocksList[world.getBlockId(i, j + 1, k)];
						
						if (iTempAxis == 1 && blockAbove != null && blockAbove instanceof SCIRope) // && HasValidAttachmentPointsAlongAxis(world, i, j, k, 0) || HasValidAttachmentPointsAlongAxis(world, i, j, k, 2) )
						{
							SetExtendsAlongAxis(world, i, j, k, iTempAxis, true);
							iValidAxisCount++;
						}
						else
						{
							SetExtendsAlongAxis(world, i, j, k, iTempAxis, false);

							FCUtilsItem.DropSingleItemAsIfBlockHarvested(world, i, j, k, FCBetterThanWolves.fcItemRope.itemID, 0);
						}
					}
				}
			}

			if (iValidAxisCount <= 0) {
				// we no longer have any valid axis, destroy the block
				world.setBlockWithNotify(i, j, k, 0);

			}
		}
	}

	public boolean HasValidAttachmentPointsAlongAxis(World world, int i, int j, int k, int iAxis) {
		int iFacing1;
		int iFacing2;

		switch (iAxis) {
		case 0: // i

			iFacing1 = 4;
			iFacing2 = 5;

			break;

		case 1: // j

			iFacing1 = 0;
			iFacing2 = 1;

			break;

		default: // 2 k

			iFacing1 = 2;
			iFacing2 = 3;

			break;
		}

		return this.HasValidAttachmentPointToFacing(world, i, j, k, iFacing1)
				&& HasValidAttachmentPointToFacing(world, i, j, k, iFacing2);
	}
	
	public boolean GetExtendsAlongAxisFromMetadata( int meta, int axis )
	{
		//4 = North Axis, 1 = West Axis, 2 = vertical
		
		return ( meta & ( 1 << axis ) ) > 0;
	}
	
	public static boolean getExtendsAlongAxisFromMetadata( int meta, int axis )
	{
		//4 = North Axis, 1 = West Axis, 2 = vertical
		
		return ( meta & ( 1 << axis ) ) > 0;
	}

	public boolean HasValidAttachmentPointToFacing(World world, int i, int j, int k, int iFacing) {
		FCUtilsBlockPos targetPos = new FCUtilsBlockPos(i, j, k);

		targetPos.AddFacingAsOffset(iFacing);

		int iTargetBlockID = world.getBlockId(targetPos.i, targetPos.j, targetPos.k);
		Block targetBlock = Block.blocksList[iTargetBlockID];

		if (iTargetBlockID == blockID) {
			if (GetExtendsAlongFacing(world, targetPos.i, targetPos.j, targetPos.k, iFacing)) {
				return true;
			}
		}
		else if (targetBlock instanceof SCBlockFence) {
			return true;
		} else if (targetBlock instanceof SCIRope) {
			return true;
		}
		
		return false;
	}

	// BOX

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		double minXBox = (double) i + 0.5D - selectionBoxHalfHeight;
		double minYBox = (double) j + 0.5D - selectionBoxHalfHeight;
		double minZBox = (double) k + 0.5D - selectionBoxHalfHeight;
		double maxXBox = (double) i + 0.5D + selectionBoxHalfHeight;
		double maxYBox = (double) j + 0.5D + selectionBoxHalfHeight;
		double maxZBox = (double) k + 0.5D + selectionBoxHalfHeight;

		if (GetExtendsAlongAxis(world, i, j, k, 0)) {
			minXBox = (double) i;
			maxXBox = (double) i + 1D;
		}

		if (GetExtendsAlongAxis(world, i, j, k, 1)) {
			minYBox = (double) j;
			maxYBox = (double) j + 1D;
		}

		if (GetExtendsAlongAxis(world, i, j, k, 2)) {
			minZBox = (double) k;
			maxZBox = (double) k + 1D;
		}

		return AxisAlignedBB.getAABBPool().getAABB(minXBox, minYBox, minZBox, maxXBox, maxYBox, maxZBox);
	}

	private static void GetBlockBoundsForAxis(int iAxis, Vec3 min, Vec3 max, double dHalfHeight) {
		if (iAxis == 0) {
			min.setComponents(0F, 0.5F - dHalfHeight, 0.5F - dHalfHeight);
			max.setComponents(1F, 0.5F + dHalfHeight, 0.5F + dHalfHeight);
		} else if (iAxis == 1) {
			min.setComponents(0.5F - dHalfHeight, 0F, 0.5F - dHalfHeight);
			max.setComponents(0.5F + dHalfHeight, 1F, 0.5F + dHalfHeight);
		} else // 2
		{
			min.setComponents(0.5F - dHalfHeight, 0.5F - dHalfHeight, 0F);
			max.setComponents(0.5F + dHalfHeight, 0.5F + dHalfHeight, 1F);
		}
	}

	public static void SetRenderBoundsForAxis(RenderBlocks renderBlocks, int iAxis) {
		Vec3 min = Vec3.createVectorHelper(0, 0, 0);
		Vec3 max = Vec3.createVectorHelper(0, 0, 0);

		GetBlockBoundsForAxis(iAxis, min, max, halfHeight);

		renderBlocks.setRenderBounds((float) min.xCoord, (float) min.yCoord, (float) min.zCoord, (float) max.xCoord,
				(float) max.yCoord, (float) max.zCoord);
	}

	public static AxisAlignedBB GetVerticalRopeBounds(double length) {
		AxisAlignedBB stemBox = AxisAlignedBB.getAABBPool().getAABB(
				0.5D - 0.125 / 2, -0.25D - (length - 1D), 0.5D - 0.125 / 2,
				0.5D + 0.125 / 2, 0.5D, 0.5D + 0.125 / 2);

		return stemBox;
	}
	
	public static AxisAlignedBB GetVerticalRopeBounds2() {
		AxisAlignedBB stemBox = AxisAlignedBB.getAABBPool().getAABB(
				0.5D - 0.125 / 2, 0D, 0.5D - 0.125 / 2,
				0.5D + 0.125 / 2, 0.5D, 0.5D + 0.125 / 2);

		return stemBox;
	}

	public static AxisAlignedBB GetRopeKnotBounds() {
		AxisAlignedBB knotBox = AxisAlignedBB.getAABBPool().getAABB(0.5D - 0.125D, 0.25D + 0.125D, 0.5D - 0.125D,
				0.5D + 0.125D, 0.75D - 0.125D, 0.5F + 0.125D);

		return knotBox;
	}

	// RENDER

	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
		IBlockAccess blockAccess = renderer.blockAccess;

		Block blockAbove = Block.blocksList[blockAccess.getBlockId(i, j + 1, k)];
		
		for (int iAxis = 0; iAxis < 3; iAxis++) {
			if (this.GetExtendsAlongAxis(blockAccess, i, j, k, iAxis)) {
				
//				this.SetRenderBoundsForAxis(renderer, iAxis);

				this.SetRenderBoundsForAxis(renderer, iAxis);
				
				if (iAxis == 1 && (blockAbove instanceof SCBlockVineCropBase || !GetExtendsAlongAxis(blockAccess, i, j + 1, k, 1)))
				{
					renderer.setRenderBounds(
						0.5F - 1/16F, 0F, 0.5F - 1/16F,
						0.5F + 1/16F, 7/16F, 0.5F + 1/16F );
				}
				
				if (iAxis == 0) {
					renderer.SetUvRotateSouth(1);
					renderer.SetUvRotateNorth(1);
					renderer.SetUvRotateEast(1);
					renderer.SetUvRotateWest(1);

					renderer.SetUvRotateTop(1);
					renderer.SetUvRotateBottom(1);
				} else if (iAxis == 2) {
					renderer.SetUvRotateSouth(1);
					renderer.SetUvRotateNorth(1);
					renderer.SetUvRotateEast(1);
					renderer.SetUvRotateWest(1);

				}

				renderer.renderStandardBlock(this, i, j, k);

				renderer.ClearUvRotation();
			}
		}
		
//		if (HasStemBelow(blockAccess, i, j, k)) {
//			renderer.setRenderBounds(this.GetRopeKnotBounds());
//			renderer.renderStandardBlock(this, i, j, k);
//
//			renderer.setRenderBounds(this.GetVerticalRopeBounds(1.0D));
//			renderer.renderStandardBlock(this, i, j, k);
//
//		}
//		
//		if ( HasRopeBelow(blockAccess, i, j, k) && !HasRopeAbove(blockAccess, i, j, k)) {
//			renderer.setRenderBounds(this.GetRopeKnotBounds());
//			renderer.renderStandardBlock(this, i, j, k);
//
//			renderer.setRenderBounds(this.GetVerticalRopeBounds2());
//			renderer.renderStandardBlock(this, i, j, k);
//
//		}

		return true;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK,
			int iSide) {
		return true;
	}

//	public static boolean HasStemBelow(IBlockAccess blockAccess, int i, int j, int k) {
//		int blockBelow = blockAccess.getBlockId(i, j - 1, k);
//
//		if (blockBelow == SCDefs.redGrapeStem.blockID) {
//			return true;
//		}
//		return false;
//	}
	
	public static boolean HasRopeBelow(IBlockAccess blockAccess, int i, int j, int k) {
		int blockBelow = blockAccess.getBlockId(i, j - 1, k);
		int metaBelow = blockAccess.getBlockMetadata(i, j - 1, k);
		
		if (blockBelow == SCDefs.fenceRope.blockID ) { // || blockBelow == SCDefs.hopsLeaves.blockID  ) { TODO
			return true;
		}
		return false;
	}

	public static boolean HasRopeAbove(IBlockAccess blockAccess, int i, int j, int k) {
		int blockAbove = blockAccess.getBlockId(i, j + 1, k);
		int metaAbove = blockAccess.getBlockMetadata(i, j + 1, k);
		int meta = blockAccess.getBlockMetadata(i, j, k);

		if (blockAbove == SCDefs.fenceRope.blockID ) { // || blockAbove == SCDefs.hopsLeaves.blockID) { TODO
			return true;
		}
		return false;
	}

}
