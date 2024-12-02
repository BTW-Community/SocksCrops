package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.BTWBlocks;
import btw.block.blocks.PaneBlock;
import btw.block.util.Flammability;
import btw.client.render.util.RenderUtils;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import btw.item.BTWItems;
import net.minecraft.src.*;

import java.util.Random;

public class GrownPaneBlock extends PaneBlock {

    private int returnItemID;
    private int returnMetadata;

    public static final int VINES = 0;

    public GrownPaneBlock(int blockID, String name, String textureName, String sideTextureName, int returnItemID, int returnMetadata) {
        super( blockID, textureName, sideTextureName,
                Material.wood, false );

        setHardness( 0.5F );
        setAxesEffectiveOn();

        setBuoyant();

        setFireProperties(Flammability.PLANKS);

        setLightOpacity( 2 );
        Block.useNeighborBrightness[blockID] = true;

        setStepSound( Block.soundWoodFootstep );

        setUnlocalizedName( name );
        this.returnItemID = returnItemID;
        this.returnMetadata = returnMetadata;
    }

    @Override
    public int idPicked(World par1World, int par2, int par3, int par4) {
        return returnItemID;
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return returnItemID;
    }

    @Override
    public int damageDropped(int par1) {
        return returnMetadata;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
        ItemStack heldStack = player.getHeldItem();

        if (heldStack == null)
        {
            world.setBlockAndMetadataWithNotify(x,y,z, returnItemID, returnMetadata);

            dropItemsIndividually(world, x, y, z, Block.vine.blockID,
                    1, 0, 1);

            return true;
        }
        else {
            if (heldStack.itemID == Block.plantYellow.blockID){
                if (heldStack.getItemDamage() == 0)
                {
                    world.setBlockAndMetadataWithNotify(x,y,z, this.blockID, 1);
                }
                else world.setBlockAndMetadataWithNotify(x,y,z, this.blockID, 2);
            }
            else if (heldStack.itemID == Block.plantRed.blockID){
                world.setBlockAndMetadataWithNotify(x,y,z, this.blockID, 3);
            }
        }

        return super.onBlockActivated(world, x, y, z, player, iFacing, fXClick, fYClick, fZClick);
    }

    @Override
    public boolean doesBlockBreakSaw(World world, int i, int j, int k )
    {
        return false;
    }

    @Override
    public boolean dropComponentItemsOnBadBreak(World world, int i, int j, int k,
                                                int iMetadata, float fChanceOfDrop)
    {
        dropItemsIndividually(world, i, j, k, Item.stick.itemID,
                2, 0, fChanceOfDrop);

        dropItemsIndividually(world, i, j, k, BTWItems.sawDust.itemID,
                2, 0, fChanceOfDrop);

        dropItemsIndividually(world, i, j, k, Block.vine.blockID,
                1, 0, 1);

        return true;
    }

    private Icon[] overlay = new Icon[16];
    private boolean secondPass;
    @Override
    public Icon getIcon(int par1, int par2) {
        if (secondPass && par2 > 0)
        {
            return overlay[par2];
        }
        return super.getIcon(par1, par2);
    }

    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        overlay[1] = par1IconRegister.registerIcon("vine_rose");
        overlay[2] = par1IconRegister.registerIcon("vine_yellow");
        overlay[3] = par1IconRegister.registerIcon("vine_dandilion");
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int x, int y, int z) {
        boolean north = canConnectToBlockToFacing(renderer.blockAccess, x, y, z, 2);
        boolean south = canConnectToBlockToFacing(renderer.blockAccess, x, y, z, 3);
        boolean east = canConnectToBlockToFacing(renderer.blockAccess, x, y, z, 4);
        boolean west = canConnectToBlockToFacing(renderer.blockAccess, x, y, z, 5);

        renderer.renderCrossedSquares(Block.vine, x, y, z);

        return super.renderBlock(renderer, x, y, z);
    }

    @Override
    public void renderBlockSecondPass(RenderBlocks renderer, int x, int y, int z, boolean bFirstPassResult) {
        secondPass = true;
        if (renderer.blockAccess.getBlockMetadata(x,y,z) > 0)
        {
            renderer.setOverrideBlockTexture(overlay[renderer.blockAccess.getBlockMetadata(x,y,z)]);
            renderer.renderCrossedSquares(this,x,y,z);
            renderer.clearOverrideBlockTexture();
        }
        secondPass = false;
    }
}
