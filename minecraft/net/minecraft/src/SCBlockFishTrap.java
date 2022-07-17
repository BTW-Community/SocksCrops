package net.minecraft.src;

import java.util.Iterator;
import java.util.Random;

public class SCBlockFishTrap extends BlockContainer {

	protected SCBlockFishTrap(int blockID) {
		super(blockID, Material.wood);
		SetFireProperties( FCEnumFlammability.WICKER );
        SetBuoyant();
		setCreativeTab(CreativeTabs.tabBlock);
		setUnlocalizedName("SCBlockFishTrap");
	}
	
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
		if ( !world.isRemote )
		{
			ItemStack heldStack = player.getCurrentEquippedItem();
			SCTileEntityFishTrap fishTrap = (SCTileEntityFishTrap)world.getBlockTileEntity( i, j, k );
			
			if (heldStack != null && !fishTrap.isBaited() && !fishTrap.hasFish() && fishTrap.isBodyOfWaterLargeEnoughForFishing() && fishTrap.IsFishingBait(heldStack))
			{				
				fishTrap.setBaited(true);
				world.setBlockMetadata(i, j, k, 1);
				fishTrap.markBlockForUpdate();
				heldStack.stackSize--;
				return true;
			}
			else if(fishTrap.hasFish() && ( heldStack == null || heldStack.itemID ==fishTrap.getFishStack().itemID ) )
			{
				fishTrap.setHasFish(false);
				
				FCUtilsItem.EjectStackFromBlockTowardsFacing(world, i, j, k, fishTrap.getFishStack(), iFacing);
				fishTrap.setFishStack(null);
				fishTrap.markBlockForUpdate();
				return true;
			}
			else if (heldStack != null && !fishTrap.isBaited() && !fishTrap.hasFish() && !fishTrap.isBodyOfWaterLargeEnoughForFishing() && fishTrap.IsFishingBait(heldStack))
			{
				player.addChatMessage("There doesn't seem to be enough Water around the Fish Trap");
				return true;
			}
		}
		return false;
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new SCTileEntityFishTrap();
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}	
	
    public void randomDisplayTick(World world, int i, int j, int k, Random rand)
    {
        super.randomDisplayTick(world, i, j, k, rand);
        
        SCTileEntityFishTrap fishTrap = (SCTileEntityFishTrap)world.getBlockTileEntity( i, j, k );
        
        double xPos = i + 0.25F + rand.nextFloat() * 0.5F;
        double yPos = j + 1.0F + rand.nextFloat() * 0.25F;
        double zPos = k + 0.25F + rand.nextFloat() * 0.5F;
       
        if  (fishTrap.hasFish())
        {
            if (rand.nextInt(2) == 0)
            {   
            	double particleJ = j + 1.0F;
            	
                for ( int iTempJ = j; iTempJ <= j + 16; iTempJ++ )
                {
                	if ( !FCUtilsWorld.IsWaterSourceBlock( world, i, iTempJ, k ) && world.isAirBlock(i, iTempJ, k) )
            		{
                		
    					particleJ = iTempJ;
    					break;
            		}
                }
            	
            	for (int index=0; index < 4; index++) {
            		
            		world.spawnParticle( "splash", xPos, particleJ, zPos, 0.0D, 0.0D, 0.0D );
    			}
               
            }
        }
        
        else if (fishTrap.isBaited() && fishTrap.isBodyOfWaterLargeEnoughForFishing() )
        {
        	for (int index=0; index < 2; index++) {
        		
        		world.spawnParticle( "bubble", xPos, j + 1.0F, zPos, 0.0D, 0.1D, 0.0D );
			}
			
        }
        

    }
		
	private Icon outsideIcon;
	private Icon ropeIcon;
	@Override
	public void registerIcons(IconRegister register) {
		outsideIcon = register.registerIcon("SCBlockFishTrap_outside");
		ropeIcon = register.registerIcon("SCBlockFishTrap_rope");
	}
	
	@Override
	public Icon getIcon(int side, int meta)
	{
		return outsideIcon;
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{
		super.RenderBlock(renderer, i, j, k);
		
		int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
		
		if (meta == 1)
		{
			SCUtilsRender.RenderCrossedSquaresWithTexture(renderer, this, i, j, k, ropeIcon, false);
		}
		
		return true;
	}
	
}
