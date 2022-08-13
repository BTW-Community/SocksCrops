package net.minecraft.src;

public class SCDecoIntegration {
	private static boolean isDecoInstalled = false;
	private static FCAddOn decoManager = null;
	
	public static final String DECOADDON = "Deco Addon";
	
	public static Block flower;
	public static Block flower2;
	public static Block tulip;
	public static Block cherrySapling;
	
//	public static Item pileRedSand;
	
	public static void init() {
		try {
			if (FCAddOnHandler.isModInstalled(DECOADDON)) {
				decoManager = FCAddOnHandler.getModByName(DECOADDON);
			}
			
			if (decoManager != null) {
				isDecoInstalled = true;
				
				flower = (Block) getDecoField("flower");
				flower2 = (Block) getDecoField("flower2");
				tulip = (Block) getDecoField("tulip");
				cherrySapling = (Block) getDecoField("cherrySapling");

				
//				pileRedSand = (Item) getDecoField("pileRedSand");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	private static Object getDecoField(String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		if (isDecoInstalled) {
			return ((DecoManager) decoManager).decoDefs.getClass().getDeclaredField(fieldName).get(null);
		}
		
		return null;
	}
	
	public static boolean isDecoInstalled() {
		return isDecoInstalled;
	}
}