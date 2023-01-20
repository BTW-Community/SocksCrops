package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockBerryBush extends SCBlockBushBase {
	
	private int berryID;
	private int saplingID;
	private String textureName;
	
	protected SCBlockBerryBush(int blockID, int berryID, int saplingID, String name)
	{
		super(blockID);
		this.textureName = name;
		this.berryID = berryID;
		this.saplingID = saplingID;
		setUnlocalizedName(name);
	}
	
	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
		par3List.add(new ItemStack(par1, 1, 3));
		par3List.add(new ItemStack(par1, 1, 4));
		par3List.add(new ItemStack(par1, 1, 5));
	}

	@Override
	protected int getBerryID()
	{
		return berryID;
	}

	@Override
	protected int getSaplingID()
	{
		return saplingID;
	}
	
	private Icon[] bushIcon = new Icon[6];
    private Icon[] snowOverlay = new Icon[6];
    
    @Override
    public void registerIcons( IconRegister register )
    {    	
    	for (int i = 0; i < bushIcon.length; i++) {
    		bushIcon[i] = register.registerIcon( textureName + "_" + i );
		}
    	
    	for (int i = 0; i < snowOverlay.length; i++)
    	{
    		snowOverlay[i] = register.registerIcon("SCBlockBushOverlay_snow_" + i);
    	}
    }
    
    public Icon getIcon(int side, int meta)
    {
    	return bushIcon[meta];
    }
    
	//----------- Client Side Functionality -----------//


    @Override
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
    	int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
    	
    	renderer.setRenderBounds( GetBlockBoundsFromPoolBasedOnState( 
    		renderer.blockAccess, i, j, k ) );
        
    	SCUtilsRender.renderCrossedSquaresWithTexture(renderer, this, i, j, k, bushIcon[meta], false);
    	
    	FCBetterThanWolves.fcBlockWeeds.RenderWeeds( this, renderer, i, j, k );
		
		return true;
    } 
    
    @Override
    public void RenderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean bFirstPassResult) {
    	
    	int meta = renderer.blockAccess.getBlockMetadata(i, j, k);

    	if (renderer.blockAccess.getBlockId(i, j + 1, k) == Block.snow.blockID)
    	{
        	renderer.setRenderBounds( GetBlockBoundsFromPoolBasedOnState( 
            		renderer.blockAccess, i, j, k ) );
            
            SCUtilsRender.renderCrossedSquaresWithTexture(renderer, this, i, j, k, snowOverlay[meta], false);
    	}
    }
    
    @Override
    public boolean DoesItemRenderAsBlock(int iItemDamage) {
    	return false;
    }
    
}
