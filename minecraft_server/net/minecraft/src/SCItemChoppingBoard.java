package net.minecraft.src;

public class SCItemChoppingBoard extends ItemMultiTextureTile {

	public static String[] woodTypes = new String [] {"oak", "spruce", "birch", "jungle", "blood", "bamboo", "strippedBamboo"};
	
	public SCItemChoppingBoard(int par1, Block par2Block, String[] par3ArrayOfStr) {
		super(par1, par2Block, par3ArrayOfStr);
	}
	
	private Icon[] wood = new Icon[woodTypes.length];
	
    public int getMetadata(int par1)
    {
        return 0;
    }
	
    public Icon getIconFromDamage(int damage)
    {
        return wood[damage];
    }
	
    public int getSpriteNumber()
    {
        return 1;
    }
}