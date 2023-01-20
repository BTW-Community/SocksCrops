package net.minecraft.src;

import java.util.Random;

public class SCBlockGrapeSapling extends SCBlockSaplingBase {

	public static final String[] SAPLING_TYPES = new String[] {
			"oak", "spruce", "birch", "jungle",
			"oak", "spruce", "birch", "jungle",
			"oak", "spruce", "birch", "jungle",
			"oakMature", "spruceMature", "birchMature", "jungleMature",
	};
	
	
	public static final int RED = 0;
	public static final int WHITE = 1;
	
	private int type;
	
	protected SCBlockGrapeSapling(int iBlockID, String name, int type) {
		super(iBlockID, name);
		this.type = type;
	}

	@Override
	protected float getGrowthChance() {
		return 0.5F;
	}

	@Override
	protected boolean generateTree(World world, Random random, int x, int y, int z, int treeType) {
		
		if ( world.setBlockAndMetadata(x, y, z, SCDefs.grapeStem.blockID, type) )
		{
			return true;
		}
		
		return false;
	}

	@Override
	protected float getBigTreeGrowthChance() {
		return 0;
	}

	@Override
	protected boolean grows2x2Tree(int treeType) {
		return false;
	}
	
	private Icon[][] m_IconArray = new Icon[4][4]; 
	public static final String[] m_sBaseTextureNames = new String[] { "fcBlockSaplingOak_0", "fcBlockSaplingSpruce_0", "fcBlockSaplingBirch_0", "fcBlockSaplingJungle_0" };

	public void registerIcons(IconRegister register)
	{
		for (int iTempSaplingType = 0; iTempSaplingType < 4; iTempSaplingType++)
		{
			for (int iTempGrowthStage = 0; iTempGrowthStage < 4; iTempGrowthStage++)
			{
				m_IconArray[iTempSaplingType][iTempGrowthStage] = register.registerIcon(m_sBaseTextureNames[iTempSaplingType] + iTempGrowthStage);
			}
		}
	}
	
	public Icon getIcon(int iSide, int iMetadata)
	{
		int iSaplingType = iMetadata & 3;
		int iGrowthStage = (iMetadata & (~3)) >> 2;

			return m_IconArray[iSaplingType][iGrowthStage];
	}

}
