/**
 * @author Sockthing (sockthinggaming@gmail.com)
 */

package net.minecraft.src;

import java.util.Random;

import net.minecraft.client.Minecraft;
import uristqwerty.CraftGuide.api.Util;
import uristqwerty.CraftGuide.recipes.SocksCropsRecipes;

public class SocksCropsAddon extends FCAddOn {

    public static SocksCropsAddon instance = new SocksCropsAddon();
    
    private static final String ADDON_NAME = "Sock's Crops";
    private static final String ADDON_VERSION = "1.0.0_beta_22w29";
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
    	SCDefs.addTileEntityDefinitions();
    	SCRecipes.addRecipes();
    	
    	SCDecoIntegration.init();
       	
    	FCAddOnHandler.LogMessage(this.getName() + " Initialized");
    }
    
    @Override
    public void serverPlayerConnectionInitialized(NetServerHandler serverHandler, EntityPlayerMP playerMP)
    {
    	FCAddOnHandler.LogMessage(this.getName() + " Version " + this.getVersionString() + " CG Initializing...");
    	
       	//CraftGuide
    	if (Util.instance != null)
    	{
    		  new SocksCropsRecipes();
    		  Util.instance.reloadRecipes();
    		  
    		  FCAddOnHandler.LogMessage(this.getName() + " CG Initialized");
    	}
    	else
    	{
    		FCAddOnHandler.LogMessage(this.getName() + " ERROR, Craftguide not initialized yet");
    	}
       	
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
    	SCCustomAuxFX.playChoppingBoardAuxFX(mcInstance, world, player, iFXID, i,j,k, iFXSpecificData);
    	
    	return true;
	}
}
