package net.minecraft.src;

import java.util.Random;

public class SCBlockCoconutSapling extends SCBlockSapling {

    private static final double width = 0.8D;
	
    public static final String[] SAPLING_TYPES = new String[] {
			"coconut", "coconut", "coconut", "coconutMature",
	};
	
	protected SCBlockCoconutSapling(int id) {
		super(id);
        this.setCreativeTab(null);
        this.SetFurnaceBurnTime(FCEnumFurnaceBurnTime.KINDLING);
        this.SetFilterableProperties(0);
        this.InitBlockBounds(0.1D, 0.0D, 0.1D, width + 0.1D, width, width + 0.1D);
        this.SetBuoyant();
		this.setUnlocalizedName("SCBlockCoconutSapling");
		this.setStepSound(soundWoodFootstep);
        this.baseTextureNames = new String[] { "SCBlockCoconutSaplingGrowth_" };
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player,
			ItemStack itemStack) {
		
		int damage = itemStack.getItemDamage();
		int newMeta;
		
		if (damage == 0) newMeta =  0;
		else if (damage == 4) newMeta = 4;
		else if (damage == 8) newMeta = 8;
		else newMeta = 12;
		
		world.setBlockMetadata(x,y,z, newMeta);
	}
	
	
    /**
     * Attempts to grow a sapling into a tree
     */
    public void growTree(World world, int x, int y, int z, Random rand) {
    	
    	System.out.println("Grow Coconut");
    	
        int treeType = world.getBlockMetadata(x, y, z) & 3;
        boolean planter = Block.blocksList[world.getBlockId(x, y - 1, z)] instanceof FCBlockPlanterBase;
        
    	world.setBlock(x, y, z, 0);
    	
    	boolean success = SCUtilsTrees.generateCoconutTree(world, rand, x, y, z);
    	
    	if (!success) {
    		world.setBlockAndMetadataWithNotify(x, y, z, this.blockID, treeType | 3 << 2);
    	}
    	else if (planter) {
    		world.setBlockMetadata(x, y, z, treeType);

			//Block break sfx
			world.playAuxSFX(2001, x, y - 1, z, FCBetterThanWolves.fcBlockPlanterSoil.blockID);

			world.setBlockAndMetadata(x, y - 1, z, Block.wood.blockID, 15);
    	}
    }
    
    @Override
    public int idDropped(int meta, Random par2Random, int par3)
    {
    	return SCDefs.coconut.itemID;
    }
    
	@Override
    public void dropBlockAsItemWithChance( World world, int i, int j, int k, int iMetadata, float fChance, int iFortuneModifier )
    {
        if ( !world.isRemote )
        {
        	FCUtilsItem.DropSingleItemAsIfBlockHarvested( world, i, j, k, idDropped( iMetadata, world.rand, iFortuneModifier ), damageDropped( iMetadata ) );
        }
    }
    
    @Override
    public int idPicked(World par1World, int par2, int par3, int par4)
    {

    	return SCDefs.coconut.itemID;
    }
    
    private Icon[] coconut = new Icon[4];
    private Icon[] coconutTop = new Icon[4];
    
    @Override
    public void registerIcons(IconRegister register)
    {
    	super.registerIcons(register);
    	
    	for(int i = 0; i < coconut.length; i++)
    	{
    		blockIcon = coconut[i] = register.registerIcon("SCBlockCoconutSapling_" + i);
    		coconutTop[i] = register.registerIcon("SCBlockCoconutSapling_top_" + i);
    	}
    	
    }
    
    
    @Override
    public Icon getIcon(int side, int meta) {
    	
    	if (coconutPass)
    	{
    		if (side < 2)
    		{
    			return coconutTop[getSaplingGrowthStage(meta)];
    		}
    		else return coconut[getSaplingGrowthStage(meta)];
    	}
    	
    	return super.getIcon(side, meta);
    }
    
    boolean coconutPass;
    
    @Override
    public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
    
    	coconutPass = true;
    	renderer.setRenderBounds(getCoconutBounds(getSaplingGrowthStage(renderer.blockAccess, i, j, k)));
    	renderer.renderStandardBlock(this, i, j, k);
    	coconutPass = false;
    	
    	
    	return super.RenderBlock(renderer, i, j, k);
    }
    
    int coconutWidth;

	private AxisAlignedBB getCoconutBounds(int saplingGrowthStage) {
		switch (saplingGrowthStage) {

		case 2:
			
			coconutWidth = 4;
			break;
			
		case 3:
			
			coconutWidth = 3;
			break;

		default:
			coconutWidth = 5;
			break;
		}
		
		AxisAlignedBB bambooBounds = AxisAlignedBB.getAABBPool().getAABB( 
    			0.5F - coconutWidth /16D, 0/16D, 0.5F - coconutWidth /16D, 
    			0.5F + coconutWidth /16D, coconutWidth*2/16D, 0.5F + coconutWidth /16D );
		
		return bambooBounds;
	}
    
}
