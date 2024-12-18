package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.util.Flammability;
import btw.community.sockthing.sockscrops.block.tileentities.FishTrapTileEntity;
import btw.community.sockthing.sockscrops.recipes.SCRecipes;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import btw.item.util.ItemUtils;
import btw.world.util.WorldUtils;
import net.minecraft.src.*;

import java.util.Random;

public class FishTrapBlock extends BlockContainer {

    public final static int NO_BAIT = 0;
    public final static int BAITED = 1;

    public FishTrapBlock(int blockID, String name) {
        super(blockID, Material.wood);

        setLightOpacity(1);
        setAxesEffectiveOn();
        setFireProperties( Flammability.WICKER );
        setBuoyant();

        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName(name);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new FishTrapTileEntity();
    }

    @Override
    public boolean onBlockActivated( World world, int x, int y, int z, EntityPlayer player, int targetFace, float xClick, float yClick, float zClick )
    {
        if ( !world.isRemote )
        {
            ItemStack heldStack = player.getCurrentEquippedItem();
            FishTrapTileEntity fishTrap = (FishTrapTileEntity)world.getBlockTileEntity( x, y, z );
            boolean isBaited = fishTrap.isBaited();
            boolean hasFish = fishTrap.hasFish();
            ItemStack fishStack = fishTrap.getFishStack();

            if (heldStack != null && !isBaited && !hasFish && fishTrap.isBodyOfWaterLargeEnoughForFishing() && fishTrap.isFishingBait(heldStack))
            {
                fishTrap.setBaited(true);
                world.setBlockMetadata(x, y, z, BAITED);

                fishTrap.markBlockForUpdate();
                heldStack.stackSize--;
                return true;
            }
            else if( hasFish && ( heldStack == null || heldStack.itemID == fishStack.itemID ) )
            {

                System.out.println("Biome: " + world.getBiomeGenForCoords(x, z).biomeName);

                fishTrap.setHasFish(false);

                ItemUtils.ejectStackFromBlockTowardsFacing(world, x, y, z, fishStack, targetFace);
                fishTrap.setFishStack(null);
                fishTrap.markBlockForUpdate();
                return true;
            }
            else if (heldStack != null && !isBaited && !hasFish && !fishTrap.isBodyOfWaterLargeEnoughForFishing() && fishTrap.isFishingBait(heldStack))
            {
                player.addChatMessage("There doesn't seem to be enough Water around the Fish Trap");
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {

        if ( !world.isRemote )
        {
            ItemStack heldStack = player.getCurrentEquippedItem();
            FishTrapTileEntity fishTrap = (FishTrapTileEntity)world.getBlockTileEntity( x, y, z );
            boolean hasFish = fishTrap.hasFish();
            ItemStack fishStack = fishTrap.getFishStack();

            if( hasFish )
            {
                ItemUtils.ejectStackWithRandomOffset(world, x, y, z, fishStack);
            }

        }
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        FishTrapTileEntity fishTrap = (FishTrapTileEntity)world.getBlockTileEntity( x, y, z );

        double xPos = x + 0.25F + rand.nextFloat() * 0.5F;
        double yPos = y + 1.0F + rand.nextFloat() * 0.25F;
        double zPos = z + 0.25F + rand.nextFloat() * 0.5F;

        if  (fishTrap.hasFish())
        {
            if (rand.nextInt(2) == 0)
            {
                double particleYPos = y + 1.0F;

                for ( int tempY = y; tempY <= y + 16; tempY++ )
                {
                    if ( !WorldUtils.isWaterSourceBlock( world, x, tempY, z ) && world.isAirBlock(x, tempY, z) )
                    {
                        particleYPos = tempY;
                        break;
                    }
                }

                for (int index=0; index < 4; index++) {

                    world.spawnParticle( "splash", xPos, particleYPos, zPos, 0.0D, 0.0D, 0.0D );
                }
            }
        }
        else if (fishTrap.isBaited() && fishTrap.isBodyOfWaterLargeEnoughForFishing() )
        {
            for (int i = 0; i < 2; i++) {

                world.spawnParticle( "bubble", xPos, y + 1.0F, zPos, 0.0D, 0.1D, 0.0D );
            }
        }
    }

    // --- Client Side --- //

    private Icon outside;
    private Icon rope;

    @Override
    public void registerIcons(IconRegister register)
    {
        blockIcon = outside = register.registerIcon("SCBlockFishTrap_outside");
        rope = register.registerIcon("SCBlockFishTrap_rope");
    }

    @Override
    public Icon getIcon(int side, int meta)
    {
        return outside;
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int x, int y, int z)
    {
        super.renderBlock(renderer, x, y, z); //Renders Normal Cube

        int meta = renderer.blockAccess.getBlockMetadata(x, y, z);

        if (meta == BAITED)
        {
            SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, x, y, z, rope, false);
        }

        return true;
    }


}
