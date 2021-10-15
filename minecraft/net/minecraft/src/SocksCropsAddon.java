package net.minecraft.src;

/**
 * @author Sockthing (@socklessthing)
 *
 */

public class SocksCropsAddon extends FCAddOn {
    public static SocksCropsAddon instance = new SocksCropsAddon();
    

    private SocksCropsAddon() {
        super("Sock's Crops", "0.0.dev", "SC");
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