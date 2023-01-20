package net.minecraft.src;

public class SCDecoIntegration {
	private static boolean isDecoInstalled = false;
	public static FCAddOn decoManager = null;
	
	public static final String DECOADDON = "Deco Addon";
	
	public static Block flower;
	public static Block flower2;
	public static Block tulip;
	public static Block cherrySapling;
	public static Block acaciaSapling;
	public static Block autumnSapling;
	public static Block pumpkin;
	public static Block pumpkinLit;
	
	public static Block carpet;
	public static Block cherryLeaves;
	public static Block acaciaLeaves;
	public static Block autumnLeaves;
	
	public static Block acaciaLog;
	
	public static Item fertilizer;
	public static Item amethystShard;
	
	public static Item prismarineShard;
	public static Item prismarineCrystal;
	
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
				acaciaSapling = (Block) getDecoField("acaciaSapling");
				autumnSapling = (Block) getDecoField("autumnSapling");
				pumpkin = (Block) getDecoField("pumpkin");
				pumpkinLit = (Block) getDecoField("pumpkinLit");

				carpet = (Block) getDecoField("carpet");
				cherryLeaves = (Block) getDecoField("cherryLeaves");
				acaciaLeaves = (Block) getDecoField("acaciaLeaves");
				autumnLeaves = (Block) getDecoField("autumnLeaves");

				acaciaLog = (Block) getDecoField("acaciaLog");
				
				fertilizer = (Item) getDecoField("fertilizer");
				amethystShard = (Item) getDecoField("amethystShard");
				
				prismarineShard = (Item) getDecoField("prismarineShard");
				prismarineCrystal = (Item) getDecoField("prismarineCrystal");
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