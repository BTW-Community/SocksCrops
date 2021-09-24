/**
 * Sock's Addon
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
		this.CreateModEntityMappings();
		FCAddOnHandler.LogMessage(this.getName() + " Initialized");
	}
	

	public String GetLanguageFilePrefix()
	{
		return "SocksCrops";
	}
	
	private void CreateModEntityMappings()
    {
		
		TileEntity.addMapping(SocksCropsTileEntityCrockPot.class, "CrockPot");
		
		EntityList.addMapping(EntityGoat.class,"Goat" , 201,15771042, 14377823);
		EntityList.addMapping(EntityFox.class,"Fox", 202,16167425, 16775294);
		EntityList.addMapping(EntityButterfly.class, "Butterfly", 203, 4996656, 986895);
		
        EntityList.ReplaceExistingMapping(SockEntitySquid.class, "Squid");
        RenderManager.AddEntityRenderer(SockEntitySquid.class, new SockRenderSquid(new FCClientModelSquid(), (ModelBase)null));
        
        BiomeGenBase.extremeHills.getSpawnableList(EnumCreatureType.creature).add(new SpawnListEntry(EntityGoat.class, 8, 4,4));
        BiomeGenBase.extremeHillsEdge.getSpawnableList(EnumCreatureType.creature).add(new SpawnListEntry(EntityGoat.class, 8, 4,4));
        BiomeGenBase.iceMountains.getSpawnableList(EnumCreatureType.creature).add(new SpawnListEntry(EntityGoat.class, 8, 4,4));
        BiomeGenBase.icePlains.getSpawnableList(EnumCreatureType.creature).add(new SpawnListEntry(EntityGoat.class, 8, 4,4));
        BiomeGenBase.frozenRiver.getSpawnableList(EnumCreatureType.creature).add(new SpawnListEntry(EntityGoat.class, 4, 4,4));
        //<biome>.getSpawnableList(EnumCreatureType.<type>).add(new SpawnListEntry(<yourEntity>.class, <weight>, <maxNumber>, <minNumber>));
    } 



} 