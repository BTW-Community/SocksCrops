package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockPumpkinJack extends FCBlockJackOLantern {

	protected SCBlockPumpkinJack(int iBlockID) {
		super(iBlockID);
		setTickRandomly(true);
        
        setHardness(1.0F);
        SetBuoyant();
        
        setStepSound(soundWoodFootstep);
        setLightValue(1.0F);
        setUnlocalizedName("SCBlockPumpkinJack");
        
        setCreativeTab(CreativeTabs.tabBlock);
	}
	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
		CheckForExtinguish( world, i, j, k );
    }
	
	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iNeighborBlockID )
    {
		CheckForExtinguish( world, i, j, k );
    }
	
	@Override
	public int damageDropped(int meta)
	{
		if (meta < 4) return 3;
		if (meta >= 4 && meta < 8) return 7;
		if (meta >= 8 && meta < 12) return 11;
		else return 15;

	}
	
    @Override
    public boolean GetCanBlockLightItemOnFire( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return true;
    }
    
    @Override
    public boolean CanBeGrazedOn( IBlockAccess blockAccess, int i, int j, int k, EntityAnimal animal )
    {
		return animal.CanGrazeOnRoughVegetation();
    }
    
    private void CheckForExtinguish( World world, int i, int j, int k )
	{		
		if ( HasWaterToSidesOrTop( world, i, j, k ) )
		{
			ExtinguishLantern( world, i, j, k );
		}
	}
    
	private void ExtinguishLantern( World world, int i, int j, int k )
	{
		int meta = world.getBlockMetadata( i, j, k );
		
		world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.pumpkinCarved.blockID, meta);
		
        world.playAuxSFX( FCBetterThanWolves.m_iFireFizzSoundAuxFXID, i, j, k, 0 );							        
	}
	
	

    public boolean IsNormalCube(IBlockAccess blockAccess, int i, int j, int k) {
        
        if (blockAccess.getBlockMetadata(i, j, k) <= 3) //mature pumpkin
        {
            return true;
        }
        else return false;
    }
    
    
    
	//SC
	
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		par3List.add(new ItemStack(par1, 1, 3));
		par3List.add(new ItemStack(par1, 1, 7));
		par3List.add(new ItemStack(par1, 1, 11));
		par3List.add(new ItemStack(par1, 1, 15));

    }	
	
	public void onBlockPlacedBy(World world, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack stack)
    {
        int playerRotation = ((MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
        
        int damage = stack.getItemDamage();
        
        if(damage >= 4 && damage < 8)
        {
        	playerRotation = playerRotation + 4;
        }
        else if(damage >= 8 && damage < 12)
        {
        	playerRotation = playerRotation + 8;
        }
        else if(damage >= 12)
        {
        	playerRotation = playerRotation + 12;
        } 
        
        world.setBlockMetadataWithNotify(par2, par3, par4, playerRotation);
    }

	private AxisAlignedBB GetPumpkinBounds(double size, double height)
	{
    	AxisAlignedBB pumpkinBox = null;
    	
    	pumpkinBox = AxisAlignedBB.getAABBPool().getAABB( 
    			8/16D - size, 0.0D, 8/16D - size, 
    			8/16D + size, height, 8/16D + size);
    	
    		return pumpkinBox;
		
	}
	
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

	private AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(int meta) {
		
		//Orange
		if (meta == 0 || meta == 1 || meta == 2 || meta == 3){
			return GetPumpkinBounds(8/16D, 16/16D);
		}
		//Green
		else if (meta == 4 || meta == 5 || meta == 6 || meta == 7){
			return GetPumpkinBounds(8/16D, 8/16D);
		}
		//Yellow
		else if (meta == 8 || meta == 9 || meta == 10 || meta == 11){
			return GetPumpkinBounds(6/16D, 12/16D);
		}
		//White
		else return GetPumpkinBounds(5/16D, 6/16D);
	}
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k)
	{
		int meta = blockAccess.getBlockMetadata(i, j, k);
		return this.GetBlockBoundsFromPoolBasedOnState(meta);
	
	}
	
	
}
