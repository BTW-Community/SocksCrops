package btw.community.sockthing.sockscrops;

import btw.AddonHandler;
import btw.BTWAddon;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.item.SCItems;
import btw.community.sockthing.sockscrops.recipes.SCRecipes;
import btw.community.sockthing.sockscrops.world.WorldDecorator;
import btw.world.biome.BiomeDecoratorBase;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.World;

import java.util.Random;

public class SocksCropsAddon extends BTWAddon {
    private static SocksCropsAddon instance;

    public SocksCropsAddon() {
        super("@NAME@", "@VERSION@", "@PREFIX@");
    }

    @Override
    public void preInitialize() {
        registerConfigProperties();
    }

    @Override
    public void initialize() {
        AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");

        SCBlocks.initBlocks();
        SCItems.initItems();

        SCRecipes.initRecipes();
    }

    public void registerConfigProperties() {
        //super.registerProperty("EnableBetterSnowyGrass", "False", "Set the following to True to enable Better Snowy Grass");
    }

    @Override
    public void decorateWorld(BiomeDecoratorBase decorator, World world, Random rand, int x, int z, BiomeGenBase biome) {
        WorldDecorator.decorateWorld(decorator, world, rand, x, z, biome);

        //DEBUG: Generate Blocks to visualise different Rivers
        //WorldDecorator.debugRivers(world,x,z);
    }

    public static SocksCropsAddon getInstance() {
        if (instance == null)
            instance = new SocksCropsAddon();
        return instance;
    }

    public static boolean isDecoInstalled() {
        return AddonHandler.isModInstalled("Deco Addon");
    }

    public static boolean isBTAInstalled() {
        return AddonHandler.isModInstalled("Better Terrain");
    }

    public static boolean isBBInstalled() {
        return AddonHandler.isModInstalled("Better Biomes");
    }


}
