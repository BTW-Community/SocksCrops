package btw.community.sockthing.sockscrops.item.items;

import btw.community.sockthing.sockscrops.item.SCItems;
import btw.crafting.util.FurnaceBurnTime;
import btw.item.items.ProgressiveCraftingItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class BambooProgressiveItem extends ProgressiveCraftingItem {

    public static final int BAMBOO_WEAVING_MAX_DAMAGE = ( 6 * 20 / PROGRESS_TIME_INTERVAL );
    public BambooProgressiveItem(int itemID, String name) {
        super(itemID);

        setBuoyant();
        setBellowsBlowDistance( 2 );
        setIncineratedInCrucible();
        setfurnaceburntime( FurnaceBurnTime.WICKER_PIECE );
        setFilterableProperties( Item.FILTERABLE_THIN );

        setUnlocalizedName(name);
    }

    private Item returnItem()
    {
        return SCItems.bambooWeave;
    }

    @Override
    protected void playCraftingFX(ItemStack stack, World world, EntityPlayer player )
    {
        player.playSound( "step.grass",
                0.25F + 0.25F * (float)world.rand.nextInt( 2 ),
                ( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.25F + 1.75F );
    }

    @Override
    public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
    {
        world.playSoundAtEntity( player, "step.grass", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );

        return new ItemStack( returnItem(), 1, 0 );
    }

    @Override
    public void onCreated( ItemStack stack, World world, EntityPlayer player )
    {
        if ( player.timesCraftedThisTick == 0 && world.isRemote )
        {
            player.playSound( "step.grass", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );
        }

        super.onCreated( stack, world, player );
    }

    @Override
    public boolean getCanBeFedDirectlyIntoCampfire( int iItemDamage )
    {
        return false;
    }

    @Override
    public boolean getCanBeFedDirectlyIntoBrickOven( int iItemDamage )
    {
        return false;
    }

    @Override
    protected int getProgressiveCraftingMaxDamage()
    {
        return BAMBOO_WEAVING_MAX_DAMAGE;
    }
}
