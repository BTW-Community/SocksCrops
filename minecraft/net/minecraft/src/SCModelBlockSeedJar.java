package net.minecraft.src;

public class SCModelBlockSeedJar extends FCModelBlock
{
	
	public static final int m_iAssemblyIDBase = 0;
	
	public SCModelBlockSeedJar()
	{
		super();
	}
	
	@Override
    protected void InitModel()
    {
		FCModelBlock tempModel;
		
		// cork
		
		tempModel = new FCModelBlock( m_iAssemblyIDBase );
		
		tempModel.AddBox(
				8/16D - 3/16D, 9/16D, 8/16D - 3/16D,
				8/16D + 3/16D, 11/16D, 8/16D + 3/16D
		);
		
		AddPrimitive( tempModel );
		
    }
	
}
