package net.minecraft.src;

/**
 * @author Sockthing (@socklessthing)
 *
 */

public class SocksCropsAddon extends FCAddOn {
    public static SocksCropsAddon instance = new SocksCropsAddon();
    
    public static final String ADDON_NAME = "Sock's Crops";
    public static final String ADDON_VERSION = "0.1.dev";
    
    private SocksCropsAddon() {
        super(ADDON_NAME, ADDON_VERSION, "SC");
    }
    
    @Override
    public void Initialize() {
    	FCAddOnHandler.LogMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
		
    	SCDefs.addDefinitions();
    	SCRecipes.addRecipes();
		
    	FCAddOnHandler.LogMessage(this.getName() + " Initialized");
    }
    
    @Override
    public String GetLanguageFilePrefix() {
    	return "SC";
    }
}