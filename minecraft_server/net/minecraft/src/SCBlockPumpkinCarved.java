package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockPumpkinCarved extends FCBlockPumpkinCarved {
	
    /** Boolean used to seperate different states of blocks */
    private boolean blockType;
    private Icon topIcon;
    private Icon faceIcon;
	
	protected SCBlockPumpkinCarved(int iBlockID) {
		super(iBlockID);
		
		setHardness( 1F );
    	SetAxesEffectiveOn( true );
    	
    	SetBuoyant();
    	
    	setStepSound( soundWoodFootstep );
    	
    	setUnlocalizedName( "SCBlockPumpkinCarved" );
    	setCreativeTab(CreativeTabs.tabBlock);
	}
	
    @Override
    public int idDropped( int iMetadata, Random rand, int iFortuneModifier )
    {
        return this.blockID;
    }
    
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int meta)
    {
		//Orange
		if (meta <= 3){
			return 3;
		}
		//Green
		else if (meta <= 7){
			return 7;
		}
		//Yellow
		else if (meta <= 11){
			return 11;
		}
		//White
		else return 15;
    }
    
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        if (!par1World.isRemote)
        {
            int var8 = this.quantityDroppedWithBonus(par7, par1World.rand);

            for (int var9 = 0; var9 < var8; ++var9)
            {
                if (par1World.rand.nextFloat() <= par6)
                {
                    int var10 = this.idDropped(par5, par1World.rand, par7);

                    if (var10 > 0)
                    {
                        this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(var10, 1, this.damageDropped(par5)));
                    }
                }
            }
        }
    }
	
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		par3List.add(new ItemStack(par1, 1, 3));
		par3List.add(new ItemStack(par1, 1, 7));
		par3List.add(new ItemStack(par1, 1, 11));
		par3List.add(new ItemStack(par1, 1, 15));

    }
	
    @Override
    public boolean IsNormalCube(IBlockAccess blockAccess, int i, int j, int k) {
        
        if (blockAccess.getBlockMetadata(i, j, k) <= 3) //mature pumpkin
        {
            return true;
        }
        else return false;
    }
	
    public boolean canPlaceBlockAt( World world, int i, int j, int k )
    {

        if ( world.getBlockId(i, j - 1, k) == Block.fence.blockID )
		{
			return true;
		}
        
        if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true )) 
		{
			return false;
		}
		
        return super.canPlaceBlockAt( world, i, j, k );
    }
	
    @Override
    public boolean canBlockStay(World par1World, int i, int j, int k) {
    	
        if ( par1World.getBlockId(i, j - 1, k) == Block.fence.blockID || FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( par1World, i, j - 1, k, 1, true )) //|| world.getBlockId(i, j - 1, k) != Block.fence.blockID
		{
			return true;
		}
        
        return false;
    }
    
    //from campfire
    public void onNeighborBlockChange( World world, int i, int j, int k, int iBlockID )
    {    	
        if ( !canBlockStay(world, i, j, k) ) //|| world.getBlockId(i, j - 1, k) != Block.fence.blockID
		{
        	world.setBlockToAir(i, j, k);
        	
        	Explode(world, i, j, k, world.getBlockMetadata(i, j, k));
        	
		}
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

    private void Explode( World world, double posX, double posY, double posZ, int entityMeta )
    {
    	NotifyNearbyAnimalsFinishedFalling( world, MathHelper.floor_double( posX ), MathHelper.floor_double( posY ), MathHelper.floor_double( posZ ) );
        
        world.playAuxSFX( AuxFXIDOnExplode(world, (int)posX, (int)posY, (int)posZ, entityMeta), 
    		MathHelper.floor_double( posX ), MathHelper.floor_double( posY ), MathHelper.floor_double( posZ ), 
    		0 );
    }
	
	protected int AuxFXIDOnExplode(World world, int i, int j, int k, int meta)
	{
		return FCBetterThanWolves.m_iPumpkinExplodeAuxFXID;
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
