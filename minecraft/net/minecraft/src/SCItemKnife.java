package net.minecraft.src;

public class SCItemKnife extends FCItemTool {

	protected SCItemKnife(int itemID) {
		// the number of uses has to be less than roughly 15% of pick to prevent it being more 
    	// efficient when used to harvest diamonds
    	
        super( itemID, 50, EnumToolMaterial.STONE );
        setUnlocalizedName("SCItemKnife");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean IsToolTypeEfficientVsBlockType(Block block) {
		return false;
	}
	
	@Override
	protected boolean GetCanBePlacedAsBlock() {
		return true;
	}
	
	@Override
    protected boolean CanToolStickInBlock( ItemStack stack, Block block, World world, int i, int j, int k )
    {
		if ( block.blockMaterial == Material.wood )
		{
			// ensures chisel will stick in cobble, despite not being efficient vs. it
			
			return true;
		}
    	
		return super.CanToolStickInBlock( stack, block, world, i, j, k );
    }

}
