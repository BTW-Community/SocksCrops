package net.minecraft.src;

import java.util.Random;
	
public class SCBlockBambooShoot extends BlockFlower {

	protected SCBlockBambooShoot(int par1) {
		super(par1, Material.vine);
		this.setTickRandomly(true);
		setUnlocalizedName("SCBlockBambooShoot");
		setHardness( 0.1F );  
		this.setStepSound(soundGrassFootstep);
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        if (!par1World.isRemote && par2EntityPlayer.getCurrentEquippedItem() != null && par2EntityPlayer.getCurrentEquippedItem().itemID == Item.shears.itemID)
        {
            par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
            this.dropBlockAsItem_do(par1World, par3, par4, par5, new ItemStack(SCDefs.bambooShoot, 1, par6));
        }
        else
        {
            super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
        }
    }

	/**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (!world.isRemote)
        {
           checkFlowerChange(world, x, y, z);

            if (world.getBlockLightValue(x, y + 1, z) >= 9 && random.nextFloat() <= 0.1) //growth chance of carrots
            {
            	int growthStage = world.getBlockMetadata(x, y, z);
            	
            	if (growthStage < 3) {
            		this.IncreaseGrowthStage(world, x, y, z, random);
            		//System.out.println("bambooshoot growing");
            	}
            	
            	if (growthStage == 3) {
            		if ( random.nextFloat() <= 0.25) {
            			this.growBambooRoot(world,x,y,z,random);
            		}
            		
            	}
            	
            }
            
            //spreading code from mushroom
            if (random.nextFloat() <= 0.2) //1 in 25
            {
            	
            	
            	byte radius = 5;
                int famSize = 7;
                int var8;
                int var9;
                int var10;
                
                // check area of radius var6 = 4 and height of -1 to 1 for this block
                // for each this block remove one from var7 our max number of blocks in the specified area
                
                for (var8 = x - radius; var8 <= x + radius; ++var8)
                {
                    for (var9 = z - radius; var9 <= z + radius; ++var9)
                    {
                        for (var10 = y - 1; var10 <= y + 1; ++var10)
                        {
                            if (world.getBlockId(var8, var10, var9) == this.blockID || world.getBlockId(var8, var10, var9) == SCDefs.bambooRoot.blockID )
                            {
                                --famSize;
                                
                                if (famSize <= 0)
                                {
                                    return;
                                }
                            }
                        }
                    }
                }
                
                //placement of shoots
                
                var8 = x + random.nextInt(2) - 1;
                var9 = y + random.nextInt(2) - random.nextInt(2);
                var10 = z + random.nextInt(2) - 1;

                for (int var11 = 0; var11 < 2; ++var11)
                {
                    if (world.isAirBlock(var8, var9, var10) && this.canBlockStay(world, var8, var9, var10))
                    {
                        x = var8;
                        y = var9;
                        z = var10;
                    }

                    var8 = x + random.nextInt(2) - 1;
                    var9 = y + random.nextInt(2) - random.nextInt(2);
                    var10 = z + random.nextInt(2) - 1;
                }

                if (world.isAirBlock(var8, var9, var10) && this.canBlockStay(world, var8, var9, var10))
                {
                	world.setBlock(var8, var9, var10, this.blockID);
                	//System.out.println("spreading");
                }
            }
        }
    }
    
	private void growBambooRoot(World world, int x, int y, int z, Random random) {
		world.setBlockAndMetadataWithNotify(x, y, z, SCDefs.bambooRoot.blockID, randomMetaForHeight(random));
		//System.out.println("bamboo root conversion");
		
	}

	private int randomMetaForHeight(Random random) {
		
		int minHeight = 3;
		int randomHeight =  minHeight + random.nextInt(7) + random.nextInt(5); //sets the metadata to a random int between 8 & 14
		System.out.println("using meta: " + randomHeight);
		return randomHeight;
	}

	private void IncreaseGrowthStage(World world, int x, int y, int z, Random random) {
		int meta = world.getBlockMetadata(x, y, z);
		
		world.setBlockMetadataWithNotify(x, y, z, meta + 1);

	}
	
	private Icon[] m_iconArray;

    @Override
    public void registerIcons( IconRegister register )
    {
        m_iconArray = new Icon[4];

        for ( int iTempIndex = 0; iTempIndex < m_iconArray.length; iTempIndex++ )
        {
            m_iconArray[iTempIndex] = register.registerIcon( "SCBlockBambooShoot_" + iTempIndex );
        }
        
        blockIcon = m_iconArray[3]; // for block hit effects and item render
   
    }

	@Override
    public Icon getBlockTexture( IBlockAccess blockAccess, int i, int j, int k, int iSide )
    {
    	int meta = blockAccess.getBlockMetadata(i, j, k);    	

        return m_iconArray[meta];
    }
    
}
