package net.minecraft.src;

public class SCModelBlockBarrel extends FCModelBlock {

	public FCModelBlock connector;
	public FCModelBlock connectorInside;

	@Override
	protected void InitModel() {
		//feet clockwise starting NW
		addBox( 0, 0, 0,
				2, 3, 4);
		addBox( 2, 0, 0,
				4, 3, 2);
		
		addBox( 14, 0, 0,
				16, 3, 4);
		addBox( 12, 0, 0,
				14, 3, 2);
		
		addBox( 12, 0, 14,
				14, 3, 16);
		addBox( 14, 0, 12,
				16, 3, 16);		
		
		addBox( 0, 0, 14,
				4, 3, 16);
		addBox( 0, 0, 12,
				2, 3, 14);
		
		//walls
		addBox( 0,  3,  0,
				2, 16, 16);
		
		addBox( 14,  3, 0,
				16, 16, 16);
		
		addBox(  2,  3, 0,
				14, 16, 2);
		
		addBox(  2,  3, 14,
				14, 16, 16);
		
		//floor
//		addBox(  2, 3,  2,
//				14, 5, 14);
		
	}

	protected void addBox(int x, int y, int z, int x2, int y2,int z2) {
		AddBox( x / 16D,  y / 16D,  z / 16D,
				x2 / 16D, y2 / 16D, z2 / 16D);
	}
	
	
}
