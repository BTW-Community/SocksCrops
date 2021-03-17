/**
 * 
 */
package net.minecraft.src;

/**
 * @author Sockthing (@socklessthing)
 *
 */
public class SocksCropsMod extends AddonExt {
	private static SocksCropsMod instance;

	public SocksCropsMod() {
		super("Sock's Crops", "1.0.0", "SocksCrops");
	}

	public static SocksCropsMod getInstance() {
		if (instance == null) {
			instance = new SocksCropsMod();
		}

		return instance;
	}

	@Override
	public void Initialize() {
		FCAddOnHandler.LogMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
		SocksCropsDefs.addDefinitions();
		SocksCropsRecipes.addRecipes();
		FCAddOnHandler.LogMessage(this.getName() + " Initialized");
	}
	
	public String GetLanguageFilePrefix()
	{
		return "SocksCrops";
	}
} 