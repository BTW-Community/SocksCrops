package net.minecraft.src;

import java.util.Random;

public class SCBlockWildPotatoCrop extends SCBlockWildCrop {

	
	protected SCBlockWildPotatoCrop(int iBlockID, String name) {
		super(iBlockID, name);
	}
	
	@Override
	protected int GetCropItemID() 
	{
		return SCDefs.sweetPotato.itemID;
	}

	@Override
	protected int getMaxGrowthLevel()
	{
		return 7; //ie 8 growthstages in total
	}

	protected int getSpreadChance()
	{
		return 25;
	}
	
	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		super.updateTick(world, i, j, k, rand);
		
		int meta = world.getBlockMetadata(i, j, k);
		
		if (GetGrowthLevel(meta) > 0 && GetGrowthLevel(meta) < 7)
		{
			spread(world, i,j,k,rand);
		}
	}    
	
	public void spread(World world, int x, int y, int z, Random rand)
    {
		System.out.println("trying to spread");
        if (rand.nextInt(getSpreadChance()) == 0)
        {
        	System.out.println("spreading...");
            byte range = 4;
            int maxNum = 5;
            int i;
            int j;
            int k;

            for (i = x - range; i <= x + range; ++i)
            {
                for (j = z - range; j <= z + range; ++j)
                {
                    for (k = y - 1; k <= y + 1; ++k)
                    {
                        if (world.getBlockId(i, k, j) == this.blockID)
                        {
                            --maxNum;

                            if (maxNum <= 0)
                            {
                            	System.out.println("ERROR: TOO MANY POTATOS AROUND");
                                return;
                            }
                        }
                    }
                }
            }

            i = x + rand.nextInt(3) - 1;
            j = y + rand.nextInt(2) - rand.nextInt(2);
            k = z + rand.nextInt(3) - 1;

            Block blockBelow = Block.blocksList[world.getBlockId(i, j - 1, k)];
            
            for (int var11 = 0; var11 < 4; ++var11)
            {
                if (world.isAirBlock(i, j, k) && blockBelow instanceof FCBlockFarmlandBase )
                {
                    x = i;
                    y = j;
                    z = k;
                    
                    System.out.println("first check: SUCCESS");
                }
                else System.out.println("first check: FAIL");

//                i = x + rand.nextInt(3) - 1;
//                j = y + rand.nextInt(2) - rand.nextInt(2);
//                k = z + rand.nextInt(3) - 1;
            }

            blockBelow = Block.blocksList[world.getBlockId(i, j - 1, k)];
            
            if (world.isAirBlock(i, j, k) && blockBelow instanceof FCBlockFarmlandBase )
            {
                world.setBlock(i, j, k, this.blockID);
                
                System.out.println("second check: SUCCESS");
            }
            else System.out.println("second check: FAIL");
        }
    }
}
