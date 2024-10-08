package btw.community.sockthing.sockscrops.mixins;

import btw.block.BTWBlocks;
import btw.block.blocks.AestheticOpaqueBlock;
import btw.community.sockthing.sockscrops.world.LootHandler;
import btw.util.hardcorespawn.HardcoreSpawnUtils;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Random;

@Mixin(ComponentScatteredFeatureJunglePyramid.class)
public abstract class ComponentScatteredFeatureJunglePyramidMixin extends ComponentScatteredFeature {

    @Shadow
    private static StructureScatteredFeatureStones junglePyramidsRandomScatteredStones = new StructureScatteredFeatureStones(null);
    @Shadow
    private boolean field_74947_h;
    @Shadow
    private boolean field_74948_i;

    @Shadow
    public void fillVesselWithExperience(World world, StructureBoundingBox boundingBox, int iRelativeI, int iRelativeJ, int iRelativeK) {
    }

    private static final ArrayList<WeightedRandomChestContent> chestLoot = new ArrayList();
    private static final ArrayList<WeightedRandomChestContent> lootedChestLoot = new ArrayList();

    protected ComponentScatteredFeatureJunglePyramidMixin(Random par1Random, int par2, int par3, int par4, int par5, int par6, int par7) {
        super(par1Random, par2, par3, par4, par5, par6, par7);
    }

    static {
        chestLoot.add(new WeightedRandomChestContent(Item.pickaxeGold.itemID, 0, 1, 1, 2));
        chestLoot.add(new WeightedRandomChestContent(Item.shovelGold.itemID, 0, 1, 1, 5));
        chestLoot.add(new WeightedRandomChestContent(Item.axeGold.itemID, 0, 1, 1, 2));
        chestLoot.add(new WeightedRandomChestContent(Item.hoeGold.itemID, 0, 1, 1, 5));
        chestLoot.add(new WeightedRandomChestContent(BTWBlocks.lightningRod.blockID, 0, 1, 1, 5));
        chestLoot.add(new WeightedRandomChestContent(Item.emerald.itemID, 0, 1, 5, 15));
        chestLoot.add(new WeightedRandomChestContent(Item.bowlEmpty.itemID, 0, 1, 1, 10));
        chestLoot.add(new WeightedRandomChestContent(Item.book.itemID, 0, 2, 4, 25));

        lootedChestLoot.add(new WeightedRandomChestContent(Item.bowlEmpty.itemID, 0, 1, 1, 10));
        lootedChestLoot.add(new WeightedRandomChestContent(Item.book.itemID, 0, 2, 4, 25));

        LootHandler.addJunglePyramidLoot(chestLoot, lootedChestLoot);
    }

    public boolean addComponentParts(World world, Random generatorRand,
                                     StructureBoundingBox boundingBox) {
        if (!func_74935_a(world, boundingBox, 0)) {
            return false;
        }

        boolean bIsLooted = HardcoreSpawnUtils.isInLootedTempleRadius(world,
                boundingBox.getCenterX(), boundingBox.getCenterZ());

        int var4 = getMetadataWithOffset(Block.stairsCobblestone.blockID, 3);
        int var5 = getMetadataWithOffset(Block.stairsCobblestone.blockID, 2);
        int var6 = getMetadataWithOffset(Block.stairsCobblestone.blockID, 0);
        int var7 = getMetadataWithOffset(Block.stairsCobblestone.blockID, 1);

        fillWithRandomizedBlocks(world, boundingBox, 0, -4, 0, scatteredFeatureSizeX - 1, 0, scatteredFeatureSizeZ - 1, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 2, 1, 2, 9, 2, 2, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 2, 1, 12, 9, 2, 12, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 2, 1, 3, 2, 2, 11, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 9, 1, 3, 9, 2, 11, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 1, 3, 1, 10, 6, 1, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 1, 3, 13, 10, 6, 13, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 1, 3, 2, 1, 6, 12, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 10, 3, 2, 10, 6, 12, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 2, 3, 2, 9, 3, 12, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 2, 6, 2, 9, 6, 12, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 3, 7, 3, 8, 7, 11, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 4, 8, 4, 7, 8, 10, false, generatorRand, junglePyramidsRandomScatteredStones);

        fillWithAir(world, boundingBox, 3, 1, 3, 8, 2, 11);
        fillWithAir(world, boundingBox, 4, 3, 6, 7, 3, 9);
        fillWithAir(world, boundingBox, 2, 4, 2, 9, 5, 12);
        fillWithAir(world, boundingBox, 4, 6, 5, 7, 6, 9);
        fillWithAir(world, boundingBox, 5, 7, 6, 6, 7, 8);
        fillWithAir(world, boundingBox, 5, 1, 2, 6, 2, 2);
        fillWithAir(world, boundingBox, 5, 2, 12, 6, 2, 12);
        fillWithAir(world, boundingBox, 5, 5, 1, 6, 5, 1);
        fillWithAir(world, boundingBox, 5, 5, 13, 6, 5, 13);

        placeBlockAtCurrentPosition(world, 0, 0, 1, 5, 5, boundingBox);
        placeBlockAtCurrentPosition(world, 0, 0, 10, 5, 5, boundingBox);
        placeBlockAtCurrentPosition(world, 0, 0, 1, 5, 9, boundingBox);
        placeBlockAtCurrentPosition(world, 0, 0, 10, 5, 9, boundingBox);

        int var8;

        for (var8 = 0; var8 <= 14; var8 += 14) {
            fillWithRandomizedBlocks(world, boundingBox, 2, 4, var8, 2, 5, var8, false, generatorRand, junglePyramidsRandomScatteredStones);
            fillWithRandomizedBlocks(world, boundingBox, 4, 4, var8, 4, 5, var8, false, generatorRand, junglePyramidsRandomScatteredStones);
            fillWithRandomizedBlocks(world, boundingBox, 7, 4, var8, 7, 5, var8, false, generatorRand, junglePyramidsRandomScatteredStones);
            fillWithRandomizedBlocks(world, boundingBox, 9, 4, var8, 9, 5, var8, false, generatorRand, junglePyramidsRandomScatteredStones);
        }

        this.fillWithRandomizedBlocks(world, boundingBox, 5, 6, 0, 6, 6, 0, false, generatorRand, junglePyramidsRandomScatteredStones);

        for (var8 = 0; var8 <= 11; var8 += 11) {
            for (int var9 = 2; var9 <= 12; var9 += 2) {
                fillWithRandomizedBlocks(world, boundingBox, var8, 4, var9, var8, 5, var9, false, generatorRand, junglePyramidsRandomScatteredStones);
            }

            fillWithRandomizedBlocks(world, boundingBox, var8, 6, 5, var8, 6, 5, false, generatorRand, junglePyramidsRandomScatteredStones);
            fillWithRandomizedBlocks(world, boundingBox, var8, 6, 9, var8, 6, 9, false, generatorRand, junglePyramidsRandomScatteredStones);
        }

        fillWithRandomizedBlocks(world, boundingBox, 2, 7, 2, 2, 9, 2, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 9, 7, 2, 9, 9, 2, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 2, 7, 12, 2, 9, 12, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 9, 7, 12, 9, 9, 12, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 4, 9, 4, 4, 9, 4, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 7, 9, 4, 7, 9, 4, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 4, 9, 10, 4, 9, 10, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 7, 9, 10, 7, 9, 10, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 5, 9, 7, 6, 9, 7, false, generatorRand, junglePyramidsRandomScatteredStones);

        placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var4, 5, 9, 6, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var4, 6, 9, 6, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var5, 5, 9, 8, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var5, 6, 9, 8, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var4, 4, 0, 0, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var4, 5, 0, 0, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var4, 6, 0, 0, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var4, 7, 0, 0, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var4, 4, 1, 8, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var4, 4, 2, 9, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var4, 4, 3, 10, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var4, 7, 1, 8, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var4, 7, 2, 9, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var4, 7, 3, 10, boundingBox);
        fillWithRandomizedBlocks(world, boundingBox, 4, 1, 9, 4, 1, 9, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 7, 1, 9, 7, 1, 9, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 4, 1, 10, 7, 2, 10, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 5, 4, 5, 6, 4, 5, false, generatorRand, junglePyramidsRandomScatteredStones);

        placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var6, 4, 4, 5, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var7, 7, 4, 5, boundingBox);

        for (var8 = 0; var8 < 4; ++var8) {
            placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var5, 5, -var8, 6 + var8, boundingBox);
            placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, var5, 6, -var8, 6 + var8, boundingBox);
            fillWithAir(world, boundingBox, 5, -var8, 7 + var8, 6, -var8, 9 + var8);
        }

        fillWithAir(world, boundingBox, 1, -3, 12, 10, -1, 13);
        fillWithAir(world, boundingBox, 1, -3, 1, 3, -1, 13);
        fillWithAir(world, boundingBox, 1, -3, 1, 9, -1, 5);

        for (var8 = 1; var8 <= 13; var8 += 2) {
            this.fillWithRandomizedBlocks(world, boundingBox, 1, -3, var8, 1, -2, var8, false, generatorRand, junglePyramidsRandomScatteredStones);
        }

        for (var8 = 2; var8 <= 12; var8 += 2) {
            this.fillWithRandomizedBlocks(world, boundingBox, 1, -1, var8, 3, -1, var8, false, generatorRand, junglePyramidsRandomScatteredStones);
        }

        fillWithRandomizedBlocks(world, boundingBox, 2, -2, 1, 5, -2, 1, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 7, -2, 1, 9, -2, 1, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 6, -3, 1, 6, -3, 1, false, generatorRand, junglePyramidsRandomScatteredStones);
        fillWithRandomizedBlocks(world, boundingBox, 6, -1, 1, 6, -1, 1, false, generatorRand, junglePyramidsRandomScatteredStones);

        placeBlockAtCurrentPosition(world, Block.cobblestoneMossy.blockID, 0, 3, -3, 1, boundingBox);

        placeBlockAtCurrentPosition(world, Block.vine.blockID, 15, 3, -2, 2, boundingBox);

        placeBlockAtCurrentPosition(world, Block.cobblestoneMossy.blockID, 0, 9, -3, 4, boundingBox);

        placeBlockAtCurrentPosition(world, Block.vine.blockID, 15, 8, -1, 3, boundingBox);
        placeBlockAtCurrentPosition(world, Block.vine.blockID, 15, 8, -2, 3, boundingBox);

        if (!field_74947_h) {
            // creates a modified loot list that includes instantiated items with complex data
            // such as the following enchanted book with a random enchantment

            //SC: Changed
//            WeightedRandomChestContent[] lootList = lootListArray;
            WeightedRandomChestContent[] lootList;

            int iNumItems = 2 + generatorRand.nextInt(5);

            //SC: ADDED
            WeightedRandomChestContent[] arrChestLoot = new WeightedRandomChestContent[chestLoot.size()];
            WeightedRandomChestContent[] arrLootedChestLoot = new WeightedRandomChestContent[lootedChestLoot.size()];
            lootList = chestLoot.toArray(arrChestLoot);

            //SC: CHANGED
//            if ( bIsLooted )
//            {
//                lootList = lootedLootListArray;
//                iNumItems /= 2;
//            }
            if (bIsLooted) {
                lootList = lootedChestLoot.toArray(arrLootedChestLoot);
                iNumItems /= 2;
            }

            // creates a modified loot list that includes instantiated items with complex data
            // such as the following enchanted book with a random enchantment

            WeightedRandomChestContent[] moddedLootList =
                    WeightedRandomChestContent.func_92080_a(lootList,
                            Item.enchantedBook.func_92114_b(generatorRand));

            field_74947_h = generateStructureChestContents(world, boundingBox,
                    generatorRand, 8, -3, 3,
                    moddedLootList, iNumItems);
        }

        this.placeBlockAtCurrentPosition(world, Block.cobblestoneMossy.blockID, 0, 9, -3, 2, boundingBox);
        this.placeBlockAtCurrentPosition(world, Block.cobblestoneMossy.blockID, 0, 8, -3, 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, Block.cobblestoneMossy.blockID, 0, 4, -3, 5, boundingBox);
        this.placeBlockAtCurrentPosition(world, Block.cobblestoneMossy.blockID, 0, 5, -2, 5, boundingBox);
        this.placeBlockAtCurrentPosition(world, Block.cobblestoneMossy.blockID, 0, 5, -1, 5, boundingBox);
        this.placeBlockAtCurrentPosition(world, Block.cobblestoneMossy.blockID, 0, 6, -3, 5, boundingBox);
        this.placeBlockAtCurrentPosition(world, Block.cobblestoneMossy.blockID, 0, 7, -2, 5, boundingBox);
        this.placeBlockAtCurrentPosition(world, Block.cobblestoneMossy.blockID, 0, 7, -1, 5, boundingBox);
        this.placeBlockAtCurrentPosition(world, Block.cobblestoneMossy.blockID, 0, 8, -3, 5, boundingBox);
        this.fillWithRandomizedBlocks(world, boundingBox, 9, -1, 1, 9, -1, 5, false, generatorRand, junglePyramidsRandomScatteredStones);
        this.fillWithAir(world, boundingBox, 8, -3, 8, 10, -1, 10);
        this.placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 3, 8, -2, 11, boundingBox);
        this.placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 3, 9, -2, 11, boundingBox);
        this.placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 3, 10, -2, 11, boundingBox);

        if (bIsLooted) {
            // knock a hole in the wall where the levers used to be

            this.placeBlockAtCurrentPosition(world, 0, 0, 9, -2, 11, boundingBox);
            this.placeBlockAtCurrentPosition(world, 0, 0, 9, -3, 11, boundingBox);
        }

        this.fillWithRandomizedBlocks(world, boundingBox, 8, -3, 8, 8, -3, 10, false, generatorRand, junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(world, boundingBox, 10, -3, 8, 10, -3, 10, false, generatorRand, junglePyramidsRandomScatteredStones);

        this.placeBlockAtCurrentPosition(world, Block.cobblestoneMossy.blockID, 0, 10, -2, 9, boundingBox);

        if (!field_74948_i) {
            // creates a modified loot list that includes instantiated items with complex data
            // such as the following enchanted book with a random enchantment

            //SC: CHANGED
//            WeightedRandomChestContent[] lootList = lootListArray;
            WeightedRandomChestContent[] lootList;

            int iNumItems = 2 + generatorRand.nextInt(5);

            //SC: ADDED
            WeightedRandomChestContent[] arrChestLoot = new WeightedRandomChestContent[chestLoot.size()];
            WeightedRandomChestContent[] arrLootedChestLoot = new WeightedRandomChestContent[lootedChestLoot.size()];
            lootList = chestLoot.toArray(arrChestLoot);

//            if ( bIsLooted )
//            {
//                lootList = lootedLootListArray;
//                iNumItems /= 2;
//            }

            if (bIsLooted) {
                lootList = lootedChestLoot.toArray(arrLootedChestLoot);
                iNumItems /= 2;
            }

            // creates a modified loot list that includes instantiated items with complex data
            // such as the following enchanted book with a random enchantment

            WeightedRandomChestContent[] moddedLootList =
                    WeightedRandomChestContent.func_92080_a(lootList,
                            Item.enchantedBook.func_92114_b(generatorRand));

            field_74948_i = generateStructureChestContents(world, boundingBox,
                    generatorRand, 9, -3, 10,
                    moddedLootList, iNumItems);
        }

        // Sacrificial altar

        placeBlockAtCurrentPosition(world, BTWBlocks.aestheticOpaque.blockID, AestheticOpaqueBlock.SUBTYPE_CHOPPING_BLOCK_DIRTY, 5, 4, 11, boundingBox);
        placeBlockAtCurrentPosition(world, BTWBlocks.aestheticOpaque.blockID, AestheticOpaqueBlock.SUBTYPE_CHOPPING_BLOCK_DIRTY, 6, 4, 11, boundingBox);

        if (!bIsLooted) {
            if (coordBaseMode != 3 && coordBaseMode != 2) {
                placeBlockAtCurrentPosition(world, BTWBlocks.arcaneVessel.blockID, 0, 5, 3, 10, boundingBox);
                placeBlockAtCurrentPosition(world, BTWBlocks.handCrank.blockID, 0, 6, 3, 10, boundingBox);

                fillVesselWithExperience(world, boundingBox, 5, 3, 10);
            } else {
                placeBlockAtCurrentPosition(world, BTWBlocks.arcaneVessel.blockID, 0, 6, 3, 10, boundingBox);
                placeBlockAtCurrentPosition(world, BTWBlocks.handCrank.blockID, 0, 5, 3, 10, boundingBox);

                fillVesselWithExperience(world, boundingBox, 6, 3, 10);
            }
        } else {
            placeBlockAtCurrentPosition(world, 0, 0, 5, 3, 10, boundingBox);
            placeBlockAtCurrentPosition(world, 0, 0, 6, 3, 10, boundingBox);
        }

        return true;
    }


}
