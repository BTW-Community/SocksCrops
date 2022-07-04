package net.minecraft.src;

import java.util.Iterator;

public class SCModelBlockComposter extends FCModelBlock
{
	public FCModelBlock modelItem;
	public FCModelBlock slats;
	
	@Override
    protected void InitModel()
    {
		// center
		
		double center = 8/16D;
		
		AddBox( center - 7/16D, 0/16D, center - 7/16D,
				center + 7/16D, 2/16D, center + 7/16D );
		
		for (int i = 0; i < 4; i++) {
			
			//slats
			AddBox( center - 8/16D, (i/16D * 4), center - 8/16D,
					center + 8/16D, (i/16D * 4) + 3/16D, center - 7/16D );
			
			AddBox( center - 8/16D, (i/16D * 4), center + 7/16D,
					center + 8/16D, (i/16D * 4) + 3/16D, center + 8/16D );
			
			AddBox( center - 8/16D, (i/16D * 4), center - 7/16D,
					center - 7/16D, (i/16D * 4) + 3/16D, center + 7/16D );
			
			AddBox( center + 7/16D, (i/16D * 4), center - 7/16D,
					center + 8/16D, (i/16D * 4) + 3/16D, center + 7/16D );
	
		}
		
		//
		AddBox( center - 7/16D, 2/16D, center - 7/16D,
				center - 5/16D, 15/16D, center - 6/16D);
		
		AddBox( center - 7/16D, 2/16D, center - 6/16D,
				center - 6/16D, 15/16D, center - 5/16D);
		
		AddBox( center + 5/16D, 2/16D, center - 7/16D,
				center + 7/16D, 15/16D, center - 6/16D );
		
		AddBox( center + 6/16D, 2/16D, center - 6/16D,
				center + 7/16D, 15/16D, center - 5/16D );
		
		AddBox( center - 7/16D, 2/16D, center + 6/16D,
				center - 5/16D, 15/16D, center + 7/16D);
		
		AddBox( center - 7/16D, 2/16D, center + 5/16D,
				center - 6/16D, 15/16D, center + 6/16D);
		
		AddBox( center + 5/16D, 2/16D, center + 6/16D,
				center + 7/16D, 15/16D, center + 7/16D );
		
		AddBox( center + 6/16D, 2/16D, center + 5/16D,
				center + 7/16D, 15/16D, center + 6/16D );

		//top
		AddBox( center - 8/16D, 15/16D, center + 6/16D,
				center + 8/16D, 16/16D, center + 8/16D );
		
		AddBox( center - 8/16D, 15/16D, center - 8/16D,
				center + 8/16D, 16/16D, center - 6/16D );
		
		AddBox( center - 8/16D, 15/16D, center - 6/16D,
				center - 6/16D, 16/16D, center + 6/16D );
		
		AddBox( center + 6/16D, 15/16D, center - 6/16D,
				center + 8/16D, 16/16D, center + 6/16D );
		
		
		
		

		
    }
	
	//----------- Client Side Functionality -----------//

	
}
