package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.blocks.PillarBlock;
import btw.block.util.Flammability;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;

import java.util.List;

public class PackedBlock extends PillarBlock {


    public static String[] types = new String[] { "sugarcane", "shafts" };

    public PackedBlock(int id, Material material, String name) {
        super(id, material, getTopTextureNames(name), getSideTextureNames(name));

        setHardness( 0.5F );

        setAxesEffectiveOn();

        setBuoyant();

        setFireProperties( Flammability.PLANKS );
        setCreativeTab(CreativeTabs.tabBlock);
        setStepSound(soundGrassFootstep);
        setUnlocalizedName(name);
    }

    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {

        for (int i = 0; i < types.length; i++) {
            par3List.add(new ItemStack(par1, 1, i));
        }

    }

    private static String[] getSideTextureNames(String name) {
        String[] sideNames = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            sideNames[i] = name + "_" + types[i] + "_side";
        }
        return sideNames;
    }

    private static String[] getTopTextureNames(String name) {
        String[] topNames = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            topNames[i] = name + "_" + types[i] + "_top";
        }
        return topNames;
    }
}
