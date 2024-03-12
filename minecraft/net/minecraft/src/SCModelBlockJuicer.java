package net.minecraft.src;

public class SCModelBlockJuicer extends FCModelBlock {
	//TODO: COPY OF MILLSTON MODEL
	public static final double m_dBaseHeight = ( 9D / 16D );
	
	public static final double m_dMidMinY = ( 7D / 16D );
	public static final double m_dMidMaxY = 1D;
	public static final double m_dMidWidthGap = ( 1D / 16D );
	
	public static final double m_dTopMinY = ( 11D / 16D );
	public static final double m_dTopHeight = ( 4D / 16D );
	
	public AxisAlignedBB m_boxBase;
	public AxisAlignedBB m_boxSelection;
	
	@Override
    protected void InitModel()
    {
		// base
		
		m_boxBase = new AxisAlignedBB( 0D, 3/16D, 0D, 
			1D, m_dBaseHeight, 1D ); 
		
		//spout
		AddBox( 5/16D, 0D, 5/16D, 
				11/16D, 3/16D, 11/16D );
		
		// middle
		
		AddBox( m_dMidWidthGap, m_dMidMinY, m_dMidWidthGap, 
			1D - m_dMidWidthGap, m_dMidMaxY, 1D - m_dMidWidthGap );
		
		// top
		
		AddBox( 0D, m_dTopMinY, 0D, 
			1D, m_dTopMinY + m_dTopHeight, 1D );
		
		// selection
		
		m_boxSelection = new AxisAlignedBB( 0D, 0D, 0D, 
			1D, 1D - ( 1D / 16D ), 1D ); 
    }
}
