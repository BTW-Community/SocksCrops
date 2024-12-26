package btw.community.sockthing.sockscrops.block.blocks;

import btw.client.render.util.RenderUtils;
import btw.community.sockthing.sockscrops.item.SCItems;
import btw.community.sockthing.sockscrops.item.items.KnifeItem;
import net.minecraft.src.*;

import java.util.Random;

public class CookedPieBlock extends PieBaseBlock {

    public static final int PUMPKIN = 0;
    public static final int SWEETBERRY = 4;
    public static final int BLUEBERRY = 8;

    public CookedPieBlock(int blockID, String name) {
        super(blockID);
        setUnlocalizedName(name);
    }

    @Override
    public int idDropped(int meta, Random rand, int fortuneModifier)
    {
        if (GetEatState(meta) == 0)
        {
            if (getType(meta) == PUMPKIN) return Item.pumpkinPie.itemID;
//            else if (getType(meta) == SWEETBERRY) return SCDefs.sweetberryPieCooked.itemID;
//            else if (getType(meta) == BLUEBERRY) return SCDefs.blueberryPieCooked.itemID;
            else return 0;
        }
        return 0;
    }

    @Override
    public int idPicked(World world, int x, int y, int z) {

        int meta = world.getBlockMetadata(x, y, z);

        if (getType(meta) == PUMPKIN) return Item.pumpkinPie.itemID;
//        else if (getType(meta) == SWEETBERRY) return SCDefs.sweetberryPieCooked.itemID;
//        else if (getType(meta) == BLUEBERRY) return SCDefs.blueberryPieCooked.itemID;
        else return 0;
    }

    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
        ItemStack heldStack = player.getHeldItem();

        if (heldStack == null)
        {
            EatCakeSliceLocal( world, i, j, k, player );
            return true;
        }
        else if (heldStack.getItem() instanceof KnifeItem)
        {
            cutPie(world, i, j, k, player);
            world.playSoundAtEntity( player, this.stepSound.getStepSound(), this.stepSound.getVolume() * 0.5F, this.stepSound.getPitch() * 0.3F );

            heldStack.attemptDamageItem(1, world.rand);

            int maxDamage = heldStack.getMaxDamage();
            if (heldStack.getItemDamage() >= maxDamage)
            {
                //break tool
                heldStack.stackSize = 0;
                player.playSound( "random.break", 0.8F, 0.8F + player.worldObj.rand.nextFloat() * 0.4F );
            }
            return true;
        }
        return false;
    }

    protected void cutPie(World world, int i, int j, int k, EntityPlayer player)
    {
        int iEatState = GetEatState( world, i, j, k ) + 1;

        int sliceID = 0;
        int meta =  world.getBlockMetadata(i, j, k);

        sliceID = getSliceItem(sliceID, meta);

        dropItemsIndividually(world, i,j,k, sliceID, 1, 0, 1F);

        if ( iEatState >= 4 )
        {
            world.setBlockWithNotify( i, j, k, 0 );
        }
        else
        {
            SetEatState( world, i, j, k, iEatState );
        }
    }

    protected int getSliceItem(int sliceID, int meta) {
        if (getType(meta) == PUMPKIN)
            sliceID = SCItems.pumpkinPieSlice.itemID;

//        else if (getType(meta) == SWEETBERRY)
//            sliceID = SCDefs.sweetberryPieSlice.itemID;
//
//        else if (getType(meta) == BLUEBERRY)
//            sliceID = SCDefs.blueberryPieSlice.itemID;
        return sliceID;
    }

    protected void EatCakeSliceLocal( World world, int i, int j, int k, EntityPlayer player )
    {
        // this function is necessary due to eatCakeSlice() in parent being private

        if ( player.canEat( true ) || player.capabilities.isCreativeMode )
        {
            // food value adjusted for increased hunger meter resolution

            player.getFoodStats().addStats( 3, 1.25F);

            int newEatState = GetEatState( world, i, j, k ) + 1 ;

            if ( newEatState >= 4 )
            {
                world.setBlockWithNotify( i, j, k, 0 );
            }
            else
            {
                SetEatState( world, i, j, k, newEatState );
            }
        }
        else
        {
            player.onCantConsume();
        }
    }

    public int GetEatState( int meta )
    {
        return meta & 3;
    }


    public int getType( int meta )
    {
        if (meta < 4) return PUMPKIN; //Pumpkin
        else if (meta >= 4 && meta < 8) return SWEETBERRY; //Sweetberry
        else if (meta >= 8 && meta < 12) return BLUEBERRY; //Blueberry
        else return 12;
    }

    public int getTypeForRender( int meta )
    {
        if (meta < 4) return 0; //Pumpkin
        else if (meta < 8) return 1; //Sweetberry
        else if (meta < 12) return 2; //Blueberry
        else return 3;
    }

    public int GetEatState( IBlockAccess iBlockAccess, int i, int j, int k )
    {
        return GetEatState( iBlockAccess.getBlockMetadata( i, j, k ) );
    }

    public void SetEatState( World world, int i, int j, int k, int newState )
    {
        int iMetaData = world.getBlockMetadata( i, j, k );

        iMetaData = iMetaData + 1;

        world.setBlockMetadataWithNotify( i, j, k, iMetaData );
    }


    //----------- Client Side Functionality -----------//

    protected Icon cookedPie;
    protected Icon[] cookedTop = new Icon[4];
    protected Icon[] cookedCrustInside = new Icon[4];
    protected Icon[] cookedPieInside = new Icon[4];

    protected boolean secondPass;

    @Override
    public void registerIcons( IconRegister register )
    {
        cookedPie = register.registerIcon( "cooked_pie" );

        cookedTop[0] = register.registerIcon( "cooked_pie_top_pumpkin" );
//        cookedTop[1] = register.registerIcon( "cooked_pie_top_sweetberry" );
//        cookedTop[2] = register.registerIcon( "cooked_pie_top_blueberry" );

        cookedCrustInside[0] = register.registerIcon( "cooked_pie_overlay_0" );
        cookedCrustInside[1] = register.registerIcon( "cooked_pie_overlay_1" );
        cookedCrustInside[2] = register.registerIcon( "cooked_pie_overlay_2" );
        cookedCrustInside[3] = register.registerIcon( "cooked_pie_overlay_3" );

        cookedPieInside[0] = register.registerIcon( "cooked_pie_inside_pumpkin" );
//        cookedPieInside[1] = register.registerIcon( "cooked_pie_inside_sweetberry" );
//        cookedPieInside[2] = register.registerIcon( "cooked_pie_inside_blueberry" );
    }


    @Override
    public Icon getIcon(int side, int meta) {

        if (secondPass) {
            return getIconSecondPass(side, meta);
        }
        return cookedPieInside[getTypeForRender(meta)];
    }

    public Icon getIconSecondPass( int side, int meta )
    {
        int eatState = GetEatState(meta);

        if (side == 1) return cookedTop[getTypeForRender(meta)];

        if (eatState == 1)
        {
            if (side == 3) return cookedCrustInside[1];
            else if (side == 5) return cookedCrustInside[0];
        }
        else if (eatState == 2)
        {
            if (side == 3) return cookedCrustInside[2];
        }
        else if (eatState == 3)
        {
            if (side == 3) return cookedCrustInside[2];
            else if (side == 5) return cookedCrustInside[3];
        }
        else
        {
            if (side == 3) return cookedPie;
        }

        return cookedPie;
    }

    protected double border = 2/16D;
    protected double height = 4/16D;
    protected double width = 12/16D;

    @Override
    public void renderBlockSecondPass(RenderBlocks renderBlocks, int i, int j, int k, boolean bFirstPassResult) {
        secondPass = true;
        renderBlock(renderBlocks, i, j, k);
        secondPass = false;
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k)
    {
        int eatState = GetEatState(renderer.blockAccess, i, j, k);

        if (eatState == 3)
        {
            renderer.setRenderBounds(
                    border, 0 , border,
                    border + width/2, height, border + width/2 );

        }
        else {
            renderer.setRenderBounds(
                    border, 0 , border,
                    border + width, height, border + width/2 );
        }

        renderer.renderStandardBlock(this, i, j, k);

        if (eatState < 2)
        {
            if (eatState == 0)
            {
                renderer.setRenderBounds(
                        border, 0 , border + width/2,
                        border + width, height, border + width );
            }
            else
            {
                renderer.setRenderBounds(
                        border, 0 , border + width/2,
                        border + width/2, height, border + width );
            }


            renderer.renderStandardBlock(this, i, j, k);
        }


        return true;
    }

    @Override
    public void renderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness) {
        int eatState = GetEatState(iItemDamage);

        if (eatState == 3)
        {
            renderer.setRenderBounds(
                    border, 0 , border,
                    border + width/2, height, border + width/2 );

        }
        else {
            renderer.setRenderBounds(
                    border, 0 , border,
                    border + width, height, border + width/2 );
        }
        RenderUtils.renderInvBlockWithMetadata(renderer, this, -0.5F, -0.5F,-0.5F, iItemDamage);

        if (eatState < 2)
        {
            if (eatState == 0)
            {
                renderer.setRenderBounds(
                        border, 0 , border + width/2,
                        border + width, height, border + width );
            }
            else
            {
                renderer.setRenderBounds(
                        border, 0 , border + width/2,
                        border + width/2, height, border + width );
            }


            RenderUtils.renderInvBlockWithMetadata(renderer, this, -0.5F, -0.5F,-0.5F, iItemDamage);
        }

        secondPass = true;
        this.RenderBlockAsItem2(renderer, iItemDamage, fBrightness);
        secondPass = false;
    }

    public void RenderBlockAsItem2(RenderBlocks renderer, int iItemDamage, float fBrightness) {
        int eatState = GetEatState(iItemDamage);

        if (eatState == 3)
        {
            renderer.setRenderBounds(
                    border, 0 , border,
                    border + width/2, height, border + width/2 );

        }
        else {
            renderer.setRenderBounds(
                    border, 0 , border,
                    border + width, height, border + width/2 );
        }
        RenderUtils.renderInvBlockWithMetadata(renderer, this, -0.5F, -0.5F,-0.5F, iItemDamage);

        if (eatState < 2)
        {
            if (eatState == 0)
            {
                renderer.setRenderBounds(
                        border, 0 , border + width/2,
                        border + width, height, border + width );
            }
            else
            {
                renderer.setRenderBounds(
                        border, 0 , border + width/2,
                        border + width/2, height, border + width );
            }


            RenderUtils.renderInvBlockWithMetadata(renderer, this, -0.5F, -0.5F,-0.5F, iItemDamage);
        }
    }
}
