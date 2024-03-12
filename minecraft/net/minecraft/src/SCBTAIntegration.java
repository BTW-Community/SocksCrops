package net.minecraft.src;

public class SCBTAIntegration {
	private static boolean isBTAInstalled = false;
	public static FCAddOn btaAddon = null;
	
	private static boolean isBBInstalled = false;
	public static FCAddOn bbAddon = null;
	
	public static final String BTAADDON = "Better Terrain";
	public static final String BBADDON = "Better Biomes";
	
	public static void init() {
		try {
			if (FCAddOnHandler.isModInstalled(BTAADDON)) {
				btaAddon = FCAddOnHandler.getModByName(BTAADDON);
			}
			
			if (FCAddOnHandler.isModInstalled(BBADDON)) {
				bbAddon = FCAddOnHandler.getModByName(BBADDON);
			}
			
			if (btaAddon != null) {
				isBTAInstalled = true;
			}
			
			if (bbAddon != null) {
				isBBInstalled = true;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
		
	public static boolean isBTAInstalled() {
		return isBTAInstalled;
	}
	
	public static boolean isBBInstalled() {
		return isBTAInstalled;
	}
}