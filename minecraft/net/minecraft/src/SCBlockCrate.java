package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SCBlockCrate extends BlockContainer {

	protected static final SCModelBlockCrate model = new SCModelBlockCrate();

	public static String[] woods = {"oak", "spruce", "birch", "jungle", "blood"};
	
	private String name;
	
	public static final ArrayList<Item> items = new ArrayList();
	
	static {
		items.add(null);
		items.add(FCBetterThanWolves.fcItemWheat);
		items.add(FCBetterThanWolves.fcItemHemp);
		items.add(Item.potato);
		items.add(Item.carrot);
		items.add(Item.fishRaw);
		items.add(SCDefs.cod);
		items.add(SCDefs.salmon);
		items.add(SCDefs.tropical);
		items.add(SCDefs.puffer);	
		items.add(Item.appleRed);
		items.add(SCDefs.cherry);
		items.add(SCDefs.lemon);
		items.add(SCDefs.olive);
		items.add(SCDefs.wildCarrot);
		items.add(SCDefs.sweetPotato);
		items.add(SCDefs.wildOnion);
		items.add(SCDefs.wildLettuce);
		items.add(SCDefs.redGrapes);
		items.add(SCDefs.whiteGrapes);
		items.add(SCDefs.tomato);
		items.add(SCDefs.hops);
		items.add(SCDefs.riceBundle);
		items.add(SCDefs.sweetberry);
		items.add(SCDefs.blueberry);
	}
	
	public static final String[] types = {
			"empty",
			"wheat", "hemp", "potato", "carrot",
			"fish", "cod", "salmon", "tropical", "puffer",
			"apple", "cherry", "lemon", "olive",
			"wildCarrot", "sweetPotato", "wildOnion", "wildLettuce",
			"redGrapes", "whiteGrapes", "tomato", "hops",
			"riceBundle",
			"sweetberry", "blueberry"
	};
	
	public static final int EMPTY = 0;
	public static final int WHEAT = 1;
	public static final int HEMP = 2;
	public static final int POTATO = 3;
	public static final int CARROT = 4;
	public static final int FISH = 5;
	public static final int COD = 6;
	public static final int SALMON = 7;
	public static final int TROPICAL = 8;
	public static final int PUFFER = 9;
	public static final int APPLE = 10;
	public static final int CHERRY = 11;
	public static final int LEMON = 12;
	public static final int OLIVE = 13;
	public static final int WILD_CARROT = 14;
	public static final int SWEET_POTATO = 15;
	public static final int ONION = 16;
	public static final int LETTUCE = 17;
	public static final int RED_GRAPES = 18;
	public static final int WHITE_GRAPES = 19;
	public static final int TOMATO = 20;
	public static final int HOPS = 21;
	public static final int RICE_BUNDLE = 22;
	public static final int SWEETBERRY = 23;
	public static final int BLUEBERRY = 24;
	
	public static final int OAK = 0;
	public static final int SPRUCE = 1;
	public static final int BIRCH = 2;
	public static final int JUNGLE = 3;
	public static final int BLOOD = 4;

	protected SCBlockCrate(int blockID, String name) {
		super(blockID, FCBetterThanWolves.fcMaterialPlanks);
		
		this.name = name;		

		setLightValue(0.125F);
		
        SetAxesEffectiveOn();
        
        setHardness( 1F );
        setResistance( 5F );
        
		SetFireProperties( FCEnumFlammability.PLANKS );
		
        SetBuoyant();
        
        setStepSound( soundWoodFootstep );
        
		setCreativeTab(CreativeTabs.tabDecorations);
		setUnlocalizedName(name);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		
		return new SCTileEntityCrate();
	}
	
	//----------- Creative Menu -----------//
	
	@Override
	public void getSubBlocks(int id, CreativeTabs tab, List list) {
		int empty = 0;
		int full = 7;
		
		//EMPTY
		list.add(new ItemStack(id, 1, dataToDamage(OAK, empty, EMPTY)));
		list.add(new ItemStack(id, 1, dataToDamage(SPRUCE, empty, EMPTY)));
		list.add(new ItemStack(id, 1, dataToDamage(BIRCH, empty, EMPTY)));
		list.add(new ItemStack(id, 1, dataToDamage(JUNGLE, empty, EMPTY)));
		list.add(new ItemStack(id, 1, dataToDamage(BLOOD, empty, EMPTY)));

		for (int wood = OAK; wood <= BLOOD; wood++)
		{
			for (int type = WHEAT; type <= BLUEBERRY; type++)
			{
				list.add(new ItemStack(id, 1, dataToDamage(wood, full, type)));
			}
		}
	}
	
	//----------- Interaction -----------//

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
	{
		
		int type = 0;
		int fill = 0;
		ItemStack heldStack = player.getCurrentEquippedItem();
		
		TileEntity te = world.getBlockTileEntity(x, y, z);
		
		if (te != null && te instanceof SCTileEntityCrate)
		{
			SCTileEntityCrate crate = (SCTileEntityCrate) te;
			type = crate.getStorageType();
			fill = crate.getFillLevel();
			
			if (heldStack == null || !items.contains(heldStack.getItem()) )
			{
				if (fill > -1 && type != EMPTY)
				{
					ItemStack ejectStack = new ItemStack(items.get(type), 1);
					
					if (!world.isRemote)
					{
						FCUtilsItem.EjectStackFromBlockTowardsFacing(world, x, y, z, ejectStack, iFacing);
					}
					
					crate.setFillLevel(crate.getFillLevel() - 1);
					
					if (crate.getFillLevel() == -1)
					{
						System.out.println("Converting to empty");
						crate.setStorageType(EMPTY);
						crate.setFillLevel(-1);
					}
					
					return true;
				}

			}
			
			if (heldStack != null && items.contains(heldStack.getItem()))
			{
				if (type == EMPTY || type == items.indexOf(heldStack.getItem()))
				{
					addItemsToCrate(crate, heldStack);
					
					crate.setStorageType(items.indexOf(heldStack.getItem()));
					
					return true;
				}
			}
			
		}

		return false;
		
		
	}
	
	private void addItemsToCrate(SCTileEntityCrate crate, ItemStack heldStack)
	{
		int maxFillLevel = 7;
		
		int sizeOfOne = 1;
		int howMuchSpace = maxFillLevel - crate.getFillLevel();
		int howManyFit = howMuchSpace / sizeOfOne;
		int howManyToInsert = Math.min(howManyFit, heldStack.stackSize); // can't insert more than we hold

		crate.setFillLevel(crate.getFillLevel() + sizeOfOne * howManyToInsert);
		heldStack.stackSize =  heldStack.stackSize - howManyToInsert;
		
		
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player,	ItemStack heldStack) {
		
		TileEntity te = world.getBlockTileEntity(x, y, z);
		
		if (heldStack != null && te != null && te instanceof SCTileEntityCrate)
		{
			SCTileEntityCrate crate = (SCTileEntityCrate) te;
			int damage = heldStack.getItemDamage();
			
			int wood = damageToData(damage)[0];
			int fill = damageToData(damage)[1];
			int type = damageToData(damage)[2];
			
			crate.setWoodType(wood);
			crate.setStorageType(type);
			
			if (type == 0 && fill == 0)
			{
				crate.setFillLevel(-1);
				
			}
			else
			{
				crate.setFillLevel(fill);
			}
		}
		
	}
	
	//----------- Block Destroy -----------//
	
	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
				
		int damage = this.damageDropped(world, x, y, z);
		
		ItemStack heldStack = player.getCurrentEquippedItem();
		
		if (player.capabilities.isCreativeMode) return;
		
		if (heldStack.getItem() != null && heldStack.getItem().canHarvestBlock(heldStack, world, this, x, y, z))
		{
			this.dropBlockAsItem_do(world, x, y, z, new ItemStack(this.blockID, 1, damage));
		}
		else
		{
			DropComponentItemsOnBadBreak(world, x, y, z, meta, 1);
		}	
	}
	
	public int GetHarvestToolLevel(IBlockAccess blockAccess, int x, int y, int z)
    {
        return 2;
    }

    public boolean DropComponentItemsOnBadBreak(World world, int x, int y, int z, int var5, float var6)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	
		TileEntity te = world.getBlockTileEntity(x, y, z);

		if (te != null && te instanceof SCTileEntityCrate)
		{
			SCTileEntityCrate crate = (SCTileEntityCrate) te;
			
			int type = crate.getStorageType();
			int count = crate.getFillLevel() + 1;
			
			this.DropItemsIndividualy(world, x, y, z, FCBetterThanWolves.fcItemSawDust.itemID, 2, 0, var6);        
	        
			if (type != EMPTY)
			{
				this.DropItemsIndividualy(world, x, y, z, items.get(type).itemID, count, 0, var6);
			}

	        return true;
		}
        
        return false;
    }
    
	
	//----------- Item Drop -----------//

    private int damageDropped(World world, int x, int y, int z)
    {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		
		if (te != null && te instanceof SCTileEntityCrate)
		{
			SCTileEntityCrate crate = (SCTileEntityCrate) te;
			
			int wood = crate.getWoodType();
			int fill = crate.getFillLevel();
			int type = crate.getStorageType();
			
			return dataToDamage(wood, fill, type);
		}
    	
    	return 0;
    }
    
    /**
     * Get the block's damage value (for use with pick block).
     */
    @Override
    public int getDamageValue(World par1World, int par2, int par3, int par4)
    {
        return this.damageDropped(par1World, par2, par3, par4);
    }
    
    @Override
    public int quantityDropped(Random par1Random) {
    	return 0;
    }
    
	@Override
    public int damageDropped(int meta)
    {
        return 0;
    } 

    //----------- Render -----------//
    
    @Override
    public boolean isOpaqueCube() {
    	return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
    	return false;
    }
    
    @Override
    public boolean IsNormalCube(IBlockAccess blockAccess, int x, int y, int z) {
		TileEntity te = blockAccess.getBlockTileEntity(x, y, z);
		
		if (te != null && te instanceof SCTileEntityCrate)
		{
			SCTileEntityCrate crate = (SCTileEntityCrate) te;

			int fill = crate.getFillLevel();
			
			if (fill == 7) return true;
		}
		
    	return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK,
    		int iSide) {
    	return true;
    }
    
    @Override
    public boolean HasLargeCenterHardPointToFacing(IBlockAccess blockAccess, int x, int y, int z, int iFacing) {
		
    	TileEntity te = blockAccess.getBlockTileEntity(x, y, z);
		
		if (te != null && te instanceof SCTileEntityCrate)
		{
			SCTileEntityCrate crate = (SCTileEntityCrate) te;

			int fill = crate.getFillLevel();
			
			if (fill < 7 )
			{
				return iFacing > 1;
			}
			else
			{
				return true;
			}
		}
		
    	return super.HasLargeCenterHardPointToFacing(blockAccess, x, y, z, iFacing);
    }
    
    //----------- Render -----------//
    
	public Icon[] woodIcon = new Icon[5];
	public Icon[] contentIcons = new Icon[types.length];
	
	private float floorHeight = 1/16F;
	private float floorWidth = 14/16F;
	
	private float slatsGap = 1/16F;
	private float slatsHeight = 3/16F;
	private float slatsWidth = 12/16F;
	private float slatsDepth = 1/16F;
	
	private float height = 1F;
	private float width = 1F;
	
	private double mindTheGap = 0.001D;
		
	@Override
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		
		int woodType = 0;
		
		TileEntity te = blockAccess.getBlockTileEntity(x, y, z);
		
		if (te != null && te instanceof SCTileEntityCrate)
		{
			SCTileEntityCrate crate = (SCTileEntityCrate) te;
			
			woodType = crate.getWoodType();
			
		}
		
		return blockIcon = woodIcon[woodType];
	}
	
	@Override
	public void registerIcons(IconRegister register) 
	{
		woodIcon[0] = register.registerIcon("wood");
		woodIcon[1] = register.registerIcon("wood_spruce");
		woodIcon[2] = register.registerIcon("wood_birch");
		woodIcon[3] = register.registerIcon("wood_jungle");
		woodIcon[4] = register.registerIcon("fcBlockPlanks_blood");
		
		for (int i = 1; i < types.length; i++)
		{
			contentIcons[i] = register.registerIcon(name + "_" + types[i]);
		}		
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int x, int y, int z) {

		return model.RenderAsBlock(renderer, this, x, y, z);		
	}

	@Override
	public void RenderBlockSecondPass(RenderBlocks renderer, int x, int y, int z, boolean bFirstPassResult) {
		float contentsHeight = 0/16F;
		int contents = 0;
		
		IBlockAccess blockAccess = renderer.blockAccess;
		
		TileEntity te = blockAccess.getBlockTileEntity(x, y, z);
		
		if (te != null && te instanceof SCTileEntityCrate)
		{
			SCTileEntityCrate crate = (SCTileEntityCrate) te;
			
			contentsHeight = crate.getFillLevel()/8F + 1/8F;
			contents = crate.getStorageType();
			
			if (contents != EMPTY)
			{
				if (crate.getFillLevel() == 0)
				{
					contentsHeight += 2 * mindTheGap;
				}
				
				renderer.setRenderBounds(
						0 + mindTheGap, mindTheGap, 0 + mindTheGap,
						1 - mindTheGap, contentsHeight - mindTheGap, 1 - mindTheGap
						);
				renderer.setOverrideBlockTexture(contentIcons[contents]);
				renderer.renderStandardBlock(this, x, y, z);
				renderer.clearOverrideBlockTexture();
			}
		}
	}
	
	private AxisAlignedBB getSlatsBounds(float centerX, float centerY, float centerZ, float width, float height, float depth) {
			
		return new AxisAlignedBB(
				centerX - width/2, centerY - height/2, centerZ - depth/2, 
				centerX + width/2, centerY + height/2, centerZ + depth/2
		);
	}

	@Override
	public void RenderBlockAsItem(RenderBlocks renderer, int damage, float fBrightness) {
		
		int wood = damageToData(damage)[0];
		int fill = damageToData(damage)[1];
		int type = damageToData(damage)[2];
		
		renderer.setOverrideBlockTexture(woodIcon[wood]);
		model.RenderAsItemBlock(renderer, this, damage);
		renderer.clearOverrideBlockTexture();
		
		if (type != EMPTY)
		{
			float fillHeight = fill/8F + 1/8F;
			
			if (fill == 0)
			{
				fillHeight += 2 * mindTheGap;
			}
			
			renderer.setRenderBounds(0 + mindTheGap, 1/16F + mindTheGap, 0 + mindTheGap, 1 - mindTheGap, fillHeight - mindTheGap, 1 - mindTheGap);
			FCClientUtilsRender.RenderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, contentIcons[type]);
		}

	}
	
	// --- BLOCK AND ITEM DATA CONVERSION --- //
	
    public static int dataToDamage(int woodType, int fillLevel, int storedType)
    {
		return woodType | fillLevel << 4 | storedType << 8;
    }

	protected static int[] damageToData(int damage)
	{
		int wood = damage & 15;
		int fill = (damage >> 4) & 15;
		int stored = (damage >> 8);
		
		return new int[] { wood, fill, stored };
	}	
}
