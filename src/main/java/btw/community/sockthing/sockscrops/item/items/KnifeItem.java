package btw.community.sockthing.sockscrops.item.items;

import btw.item.items.ToolItem;
import net.minecraft.src.*;

public class KnifeItem extends ToolItem {

    public KnifeItem(int itemID, EnumToolMaterial material, String name) {
        super(itemID, 3, material);
        setUnlocalizedName(name);
    }

    @Override
    public float getStrVsBlock(ItemStack toolStack, World world, Block block, int x, int y, int z) {
        int toolLevel = toolMaterial.getHarvestLevel();
        int blockToolLevel = block.getEfficientToolLevel(world, x, y, z);

        if (blockToolLevel > toolLevel) {
            return 1.0F;
        }

        return super.getStrVsBlock(toolStack, world, block, x, y, z);
    }

    @Override
    public boolean isEfficientVsBlock(ItemStack toolStack, World world, Block block, int x, int y, int z) {
        int toolLevel = toolMaterial.getHarvestLevel();
        int blockToolLevel = block.getEfficientToolLevel(world, x, y, z);

        if (blockToolLevel > toolLevel) {
            return false;
        }

        if (block.getIsProblemToRemove(toolStack, world, x, y, z)) {
            // stumps and such

            return false;
        }

        return super.isEfficientVsBlock(toolStack, world, block, x, y, z);
    }

    @Override
    public boolean isToolTypeEfficientVsBlockType(Block block) {
        return false;
    }

    @Override
    public boolean isConsumedInCrafting() {
        return true;
//    	return toolMaterial.getHarvestLevel() <= 2; // wood, stone, gold & iron
    }

    @Override
    public boolean isDamagedInCrafting() {
        return true;
//    	return toolMaterial.getHarvestLevel() <= 2; // wood, stone, gold & iron
    }

    @Override
    public void onUsedInCrafting(EntityPlayer player, ItemStack outputStack) {
        PlayChopSoundOnPlayer(player);
    }

    @Override
    public void onBrokenInCrafting(EntityPlayer player) {
        PlayBreakSoundOnPlayer(player);
    }

    @Override
    public boolean canToolStickInBlock(ItemStack stack, Block block, World world, int x, int y, int z) {
        return block.areShovelsEffectiveOn();
    }

    private static void PlayChopSoundOnPlayer(EntityPlayer player) {
        if (player.timesCraftedThisTick == 0) {
            // note: the playSound function for player both plays the sound locally on the client, and plays it remotely on the server without it being sent again to the same player

            //player.playSound( "mob.zombie.wood", 0.5F, 2.5F );
            player.playSound("mob.sheep.shear", 0.5F, 2.5F);
        }
    }

    private static void PlayBreakSoundOnPlayer(EntityPlayer player) {
        // note: the playSound function for player both plays the sound locally on the client, and plays it remotely on the server without it being sent again to the same player

        player.playSound("random.break", 0.8F, 0.8F + player.worldObj.rand.nextFloat() * 0.4F);
    }


}
