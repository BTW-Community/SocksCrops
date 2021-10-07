package net.minecraft.src;

public class SCBlockGrapeVine extends BlockFlower {

	protected SCBlockGrapeVine(int par1) {
		super(par1);
		this.setUnlocalizedName("SCBlockGrapeVine");
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public boolean RenderBlock( RenderBlocks renderBlocks, int i, int j, int k )
    {
		renderBlocks.renderCrossedSquares(this, i, j, k);
		return true;
    }

}
