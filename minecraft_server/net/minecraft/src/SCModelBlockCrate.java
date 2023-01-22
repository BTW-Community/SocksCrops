package net.minecraft.src;

import java.util.Iterator;

public class SCModelBlockCrate extends FCModelBlock
{
	public static final int assemblyIDBase = 0;
	public static final int assemblyIDSlats = 1;
	public static final int assemblyIDCorners = 2;
	public static final int assemblyIDConnectors = 3;
	public static final int assemblyIDContents = 4;
	
	public static final double floorHeight = 1/16D;
	public static final double floorWidth = 14/16D;
	
	public static final double slatsGap = 1/16D;
	public static final double slatsHeight = 3/16D;
	public static final double slatsWidth = 14/16D;
	public static final double slatsDepth = 1/16D;
	
	public static final double height = 1D;
	public static final double width = 1D;
	
	public static final double mindTheGap = 0.001D;

	public static final double centerX = 0.5D;
	public static final double centerZ = 0.5D;
	
	
	public SCModelBlockCrate() {
		super();
	}
	
	@Override
    protected void InitModel()
    {
		FCModelBlock tempModel;
		FCUtilsPrimitiveAABBWithBenefits tempBox;
		
		// floor		
		tempModel = new FCModelBlock( assemblyIDBase );
		
		tempModel.AddBox( 
				0/16D, 0/16D, 0/16D,
				2/16D, 1/16D, 16/16D
				);
		
		AddPrimitive( tempModel );

		tempModel.AddBox( 
				14/16D, 0/16D, 0/16D,
				16/16D, 1/16D, 16/16D
				);
		
		AddPrimitive( tempModel );
		
		double zShift = 0;
		
		for (int i=0; i<4; i++)
		{	
			int depth = 3;
			
			if (i == 3) depth = 4;
			
			tempModel.AddBox( 
					2/16D, 0/16D, 0/16D + zShift,
					14/16D, 1/16D, depth/16D + zShift
					);
			
			AddPrimitive( tempModel );
			
			zShift += 4/16D;
		}		
		
		// Corners
		
		double yShift = 0D;
		
		for (int i=0; i<3; i++)
		{
			tempBox = new FCUtilsPrimitiveAABBWithBenefits( 
					0/16D, 4/16D + yShift, 0/16D,
					2/16D, 5/16D + yShift, 1/16D,
					assemblyIDConnectors );
				
			AddPrimitive( tempBox );
			
			tempBox = new FCUtilsPrimitiveAABBWithBenefits( 
					0/16D, 4/16D + yShift, 1/16D,
					1/16D, 5/16D + yShift, 2/16D,
					assemblyIDConnectors );
				
			AddPrimitive( tempBox );
			
			yShift += 4/16D;
		}
		
		yShift = 0D;
		
		for (int i=0; i<3; i++)
		{
			tempBox = new FCUtilsPrimitiveAABBWithBenefits( 
					14/16D, 4/16D + yShift, 0/16D,
					16/16D, 5/16D + yShift, 1/16D,
					assemblyIDConnectors );
				
			AddPrimitive( tempBox );
			
			tempBox = new FCUtilsPrimitiveAABBWithBenefits( 
					15/16D, 4/16D + yShift, 1/16D,
					16/16D, 5/16D + yShift, 2/16D,
					assemblyIDConnectors );
				
			AddPrimitive( tempBox );
			
			yShift += 4/16D;
		}
		
		yShift = 0D;
		
		for (int i=0; i<3; i++)
		{
			tempBox = new FCUtilsPrimitiveAABBWithBenefits( 
					0/16D, 4/16D + yShift, 15/16D,
					2/16D, 5/16D + yShift, 16/16D,
					assemblyIDConnectors );
				
			AddPrimitive( tempBox );
			
			tempBox = new FCUtilsPrimitiveAABBWithBenefits( 
					0/16D, 4/16D + yShift, 14/16D,
					1/16D, 5/16D + yShift, 15/16D,
					assemblyIDConnectors );
				
			AddPrimitive( tempBox );
			
			yShift += 4/16D;
		}
		
		yShift = 0D;
		
		for (int i=0; i<3; i++)
		{
			tempBox = new FCUtilsPrimitiveAABBWithBenefits( 
					14/16D, 4/16D + yShift, 15/16D,
					16/16D, 5/16D + yShift, 16/16D,
					assemblyIDConnectors );
				
			AddPrimitive( tempBox );
			
			tempBox = new FCUtilsPrimitiveAABBWithBenefits( 
					15/16D, 4/16D + yShift, 14/16D,
					16/16D, 5/16D + yShift, 15/16D,
					assemblyIDConnectors );
				
			AddPrimitive( tempBox );
			
			yShift += 4/16D;
		}	
		// Slats
		
		float xPos = 0.5F;
		float yPos = 3/32F;
		float zPos = 1/32F;
		
		for (int side = 0; side < 2; side++) {
			
			yPos = 3/32F;
			
			for (int height = 0; height < 4; height++) {
				
				yPos += slatsGap;

				double tempWidth = 1D;
				
				tempBox = new FCUtilsPrimitiveAABBWithBenefits( 
						xPos - tempWidth/2, yPos - slatsHeight/2, zPos - slatsDepth/2, 
						xPos + tempWidth/2, yPos + slatsHeight/2, zPos + slatsDepth/2,
						assemblyIDSlats );
							
				AddPrimitive( tempBox );

				yPos += slatsHeight;
			}
			
			zPos = 31/32F;
			
		}
		
		xPos = 1/32F;
		zPos = 8/16F;
		
		for (int side = 0; side < 2; side++) {
			
			yPos = 3/32F;
			
			for (int height = 0; height < 4; height++) {
				
				yPos += slatsGap;

				tempBox = new FCUtilsPrimitiveAABBWithBenefits( 
						xPos - slatsDepth/2, yPos - slatsHeight/2, zPos - slatsWidth/2, 
						xPos + slatsDepth/2, yPos + slatsHeight/2, zPos + slatsWidth/2,
						assemblyIDSlats );
							
				AddPrimitive( tempBox );

				yPos += slatsHeight;
			}
			
			xPos = 31/32F;
		}
		
		
    }	
}
