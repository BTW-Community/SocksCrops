// FCMOD
package net.minecraft.src;

import java.util.Iterator;

public class SCModelBlockLargeFlowerPot extends FCModelBlock
{
	public static final int assemblyIDBase = 0;
	public static final int assemblyIDWalls = 1;
	public static final int assemblyIDContents = 2;
		
	private static final double m_dMindTheGap = 0.001D; // slight offset used to avoid visual seams
	
	public static final double height = ( 6 / 16D );
	public static final double width = ( 14 / 16D );
	public static final double halfWidth = ( width / 2 );
	public static final double length = (6 / 16D);
	public static final double halfLength =  ( length / 2 );
	
	public static final double baseHeight = ( 1 / 16D );
	public static final double baseWidth = ( 14 / 16D );
	public static final double baseHalfWidth = ( baseWidth / 2 );
	public static final double baseLength = (6 / 16D);
	public static final double halfBaseLength =  ( baseLength / 2 );
	
	
	public static final double wallsHeight = ( 5 / 16D );
	public static final double wallsWidth = ( 14 / 16D );
	public static final double wallsHalfWidth = ( wallsWidth / 2 );
	public static final double wallsLength = (1 / 16D);
	public static final double halfwallsLength =  ( wallsLength / 2 );
	
	public static final double walls2Height = ( 5 / 16D );
	public static final double walls2Width = ( 1 / 16D );
	public static final double walls2HalfWidth = ( walls2Width / 2 );
	public static final double walls2Length =  (4 / 16D);
	public static final double halfwalls2Length =  ( walls2Length / 2 );
	
	public static final double contentsHeight = ( 3 / 16D );
	public static final double contentsWidth = ( 12 / 16D );
	public static final double contentsHalfWidth = ( contentsWidth / 2 );
	public static final double contentsLength = (4 / 16D);
	public static final double halfContentsLength =  ( contentsLength / 2 );
	
	public SCModelBlockLargeFlowerPot()
	{
		super();
	}
	
	@Override
    protected void InitModel()
    {
		FCModelBlock tempModel;
		FCUtilsPrimitiveAABBWithBenefits tempBox;
		
		// base
		
		tempModel = new FCModelBlock( assemblyIDBase );

		tempModel.AddBox( 0.5D - baseHalfWidth, 0D, 0.5D - halfBaseLength,
				0.5D + baseHalfWidth, baseHeight, 0.5D + halfBaseLength );

		AddPrimitive( tempModel );
		
		// body
		
//		tempModel = new FCModelBlock( assemblyIDBody );
//		
//		tempModel.AddBox( 0.5D - bodyHalfWidth, m_dMindTheGap, 0.5D - bodyHalfWidth,
//			0.5D + bodyHalfWidth, bodyHeight, 0.5D + bodyHalfWidth );
//
//		AddPrimitive( tempModel );
		
		// Rim
		
		tempBox = new FCUtilsPrimitiveAABBWithBenefits( 
				1/16D, baseHeight,  5/16D,	
				1/16D + wallsWidth, baseHeight + wallsHeight, 5/16D + wallsLength,			
				assemblyIDWalls );
			
		AddPrimitive( tempBox );
		
		tempBox = new FCUtilsPrimitiveAABBWithBenefits( 
				1/16D, baseHeight,  10/16D,	
				1/16D + wallsWidth, baseHeight + wallsHeight, 10/16D + wallsLength,			
				assemblyIDWalls );
			
		AddPrimitive( tempBox );
		
		tempBox = new FCUtilsPrimitiveAABBWithBenefits( 
				1/16D, baseHeight, 6/16D,	
				1/16D + 1/16D, baseHeight + wallsHeight, 6/16D + 4/16D,			
				assemblyIDWalls );
			
		AddPrimitive( tempBox );
		
		tempBox = new FCUtilsPrimitiveAABBWithBenefits( 
				14/16D, baseHeight, 6/16D,
				14/16D + 1/16D, baseHeight + wallsHeight, 6/16D + 4/16D,			
				assemblyIDWalls );
			
		AddPrimitive( tempBox );

		

		
		// Interior
		tempModel = new FCModelBlock( assemblyIDContents );

		tempModel.AddBox( 
				0.5D - contentsHalfWidth, baseHeight, 0.5D - halfContentsLength, 
				0.5D + contentsHalfWidth, baseHeight + contentsHeight, 0.5D + halfContentsLength
				);

		AddPrimitive( tempModel );


		
//		tempBox = new FCUtilsPrimitiveAABBWithBenefits( 
//			0.5D + contentsHalfWidth + m_dMindTheGap, 
//			height, 
//			0.5D + contentsHalfWidth + m_dMindTheGap,
//			0.5D - contentsHalfWidth - m_dMindTheGap, 
//			height - contentsHalfWidth, 
//			0.5D - contentsHalfWidth - m_dMindTheGap,
//			assemblyIDInterior );
//		
//		tempBox.SetForceRenderWithColorMultiplier( true );
//
//		AddPrimitive( tempBox );
    }
	
	//------------- Class Specific Methods ------------//
    
    public static void OffsetModelForFacing( FCModelBlock model, int iFacing )
    {
    	if ( iFacing > 1 )
    	{
			Vec3 offset = GetOffsetForFacing( iFacing );
			
			model.Translate( offset.xCoord, offset.yCoord, offset.zCoord );
    	}
    }

    /**
     * Assumes facing is not 1, where there should be no offset
     */
    public static Vec3 GetOffsetForFacing( int iFacing )
    {
//    	if ( iFacing == 0 )
//    	{
//    		return Vec3.createVectorHelper( 0D, -1D + height, 0D );
//    	}
//    	else
//    	{    		
    		Vec3 offset = Vec3.createVectorHelper( 
    				0D, 0D, 5/16D );
			
			offset.RotateAsVectorAroundJToFacing( iFacing );
			
			return offset;
//    	}
    }
	
	//----------- Client Side Functionality -----------//
}
