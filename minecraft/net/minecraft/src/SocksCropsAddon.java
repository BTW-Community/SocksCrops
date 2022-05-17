/**
 * @author Sockthing (sockthinggaming@gmail.com)
 */

package net.minecraft.src;

import java.util.Random;

import net.minecraft.client.Minecraft;

public class SocksCropsAddon extends FCAddOn {

    public static SocksCropsAddon instance = new SocksCropsAddon();
    
    private static final String ADDON_NAME = "Sock's Crops";
    private static final String ADDON_VERSION = "1.0.dev";
    private static final String LANGUAGE_PREFIX = "SC";
	
    private SocksCropsAddon()
    {
        super(ADDON_NAME, ADDON_VERSION, LANGUAGE_PREFIX);
    }
    
    @Override
    public void Initialize()
    {
    	FCAddOnHandler.LogMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
		
    	SCDefs.addDefinitions();
    	SCRecipes.addRecipes();
		
    	FCAddOnHandler.LogMessage(this.getName() + " Initialized");

    }
    
    @Override
    public void decorateWorld(FCIBiomeDecorator decorator, World world, Random random, int x, int z, BiomeGenBase biome)
    {
    	SCBiomeDecorator.decorateWorld(decorator, world, random, x, z, biome);
    }
    
    @Override
    public boolean ClientPlayCustomAuxFX(Minecraft mcInstance, World world, EntityPlayer player, int iFXID, int i, int j, int k, int iFXSpecificData)
    {
    	SCCustomAuxFX.playGourdAuxFX(mcInstance, world, player, iFXID, i,j,k, iFXSpecificData);
    	
    	return true;
	}
}
