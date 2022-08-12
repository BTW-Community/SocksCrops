// FCMOD

package net.minecraft.src;

public class SCModelBlockHollowLog extends FCModelBlock
{
	public AxisAlignedBB[] m_boxCollisionCenter;
	public AxisAlignedBB collisionBoxXPos;
	public AxisAlignedBB collisionBoxXNeg;
	public AxisAlignedBB collisionBoxYBottom;
	public AxisAlignedBB collisionBoxYTop;
	public AxisAlignedBB collisionBoxZPos;
	public AxisAlignedBB collisionBoxZNeg;
	
	public AxisAlignedBB[] m_boxBoundsCenter;
	
	@Override
    protected void InitModel()
    {			
		// collision volumes 
		m_boxCollisionCenter = new AxisAlignedBB[2];
		//xPos
		m_boxCollisionCenter[0] = new AxisAlignedBB( 
			14/16D, 0D, 0/16D,
			16/16D, 1D, 2/16D );
		
		//xNeg
		m_boxCollisionCenter[1] = new AxisAlignedBB( 
				0/16D, 0D, 0/16D,
				2/16D, 1D, 2/16D );
		
		// selection volume
		m_boxBoundsCenter = new AxisAlignedBB[2];
		//xPos
		m_boxBoundsCenter[0] = new AxisAlignedBB( 
			14/16D, 0D, 0/16D,
			16/16D, 1D, 2/16D );
		
		//xNeg
		m_boxBoundsCenter[1] = new AxisAlignedBB( 
				0/16D, 0D, 0/16D,
				2/16D, 1D, 2/16D );
    }
}
