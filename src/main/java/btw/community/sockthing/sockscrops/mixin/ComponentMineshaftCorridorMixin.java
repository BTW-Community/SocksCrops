package btw.community.sockthing.sockscrops.mixin;

import btw.block.BTWBlocks;
import btw.community.sockthing.sockscrops.world.LootHandler;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Random;

@Mixin(ComponentMineshaftCorridor.class)
public abstract class ComponentMineshaftCorridorMixin extends StructureComponent {

    private static final ArrayList<WeightedRandomChestContent> minecartLoot = new ArrayList();
    @Shadow
    private int sectionCount;
    @Shadow
    private boolean hasSpiders;
    @Shadow
    private boolean spawnerPlaced;
    @Shadow
    private boolean hasRails;

    protected ComponentMineshaftCorridorMixin(int par1) {
        super(par1);
    }

    static {
        minecartLoot.add(new WeightedRandomChestContent(Item.ingotIron.itemID, 0, 1, 5, 10));
        minecartLoot.add(new WeightedRandomChestContent(Item.ingotGold.itemID, 0, 1, 3, 5));
        minecartLoot.add(new WeightedRandomChestContent(Item.redstone.itemID, 0, 4, 9, 5));
        minecartLoot.add(new WeightedRandomChestContent(Item.dyePowder.itemID, 4, 4, 9, 5));
        minecartLoot.add(new WeightedRandomChestContent(Item.diamond.itemID, 0, 1, 2, 3));
        minecartLoot.add(new WeightedRandomChestContent(Item.coal.itemID, 0, 3, 8, 10));
        minecartLoot.add(new WeightedRandomChestContent(Item.bread.itemID, 0, 1, 3, 15));
//		minecartLoot.add(new WeightedRandomChestContent(Item.pickaxeIron.itemID, 0, 1, 1, 1) );
        minecartLoot.add(new WeightedRandomChestContent(Block.rail.blockID, 0, 4, 8, 1));
        minecartLoot.add(new WeightedRandomChestContent(Item.melonSeeds.itemID, 0, 2, 4, 10));
//		minecartLoot.add(new WeightedRandomChestContent(Item.pumpkinSeeds.itemID, 0, 2, 4, 10) );

        LootHandler.addMinecartLoot(minecartLoot);
    }

    @Inject(method = "generateStructureChestContents", at = @At(value = "HEAD"), cancellable = true)
    public void generateStructureChestContents(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, WeightedRandomChestContent[] par7ArrayOfWeightedRandomChestContent, int par8, CallbackInfoReturnable<Boolean> cir) {
        int var9 = this.getXWithOffset(par4, par6);
        int var10 = this.getYWithOffset(par5);
        int var11 = this.getZWithOffset(par4, par6);

        if (par2StructureBoundingBox.isVecInside(var9, var10, var11) && par1World.getBlockId(var9, var10, var11) == 0) {
            par1World.setBlock(var9, var10, var11, Block.rail.blockID, this.getMetadataWithOffset(Block.rail.blockID, par3Random.nextBoolean() ? 1 : 0), 2);
            EntityMinecartChest var12 = (EntityMinecartChest) EntityList.createEntityOfType(EntityMinecartChest.class, par1World, (double) ((float) var9 + 0.5F), (double) ((float) var10 + 0.5F), (double) ((float) var11 + 0.5F));
            WeightedRandomChestContent.generateChestContents(par3Random, par7ArrayOfWeightedRandomChestContent, var12, par8);
            // FCMOD: Added
//            filterChestMinecartContents(var12);
            // END FCMOD

            LootHandler.filterMinecartContents(var12);

            par1World.spawnEntityInWorld(var12);
            cir.setReturnValue(true);
        } else {
            cir.setReturnValue(false);
        }
    }

    @Override
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
        ComponentMineshaftCorridor thisObject = (ComponentMineshaftCorridor) (Object) this;

        if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
            return false;
        } else {
            int var8 = this.sectionCount * 5 - 1;
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 2, 1, var8, 0, 0, false);
            this.randomlyFillWithBlocks(par1World, par3StructureBoundingBox, par2Random, 0.8F, 0, 2, 0, 2, 2, var8, 0, 0, false);

            if (this.hasSpiders) {
                this.randomlyFillWithBlocks(par1World, par3StructureBoundingBox, par2Random, 0.6F, 0, 0, 0, 2, 1, var8,
                        BTWBlocks.web.blockID, 0, false);
            }

            int var9;
            int var10;
            int var11;

            for (var9 = 0; var9 < this.sectionCount; ++var9) {
                var10 = 2 + var9 * 5;

                this.fillWithMetadataBlocks(par1World, par3StructureBoundingBox, 0, 0, var10, 0, 1, var10, Block.wood.blockID, 0, 0, 0, false);
                this.fillWithMetadataBlocks(par1World, par3StructureBoundingBox, 2, 0, var10, 2, 1, var10, Block.wood.blockID, 0, 0, 0, false);

                // FCMOD: Code added
                int iHorizontalLogMetadata = getMetadataWithOffset(Block.wood.blockID, 4);
                // END FCMOD

                if (par2Random.nextInt(4) == 0) {
                    this.fillWithMetadataBlocks(par1World, par3StructureBoundingBox, 0, 2, var10, 0, 2, var10, Block.wood.blockID, iHorizontalLogMetadata, 0, 0, false);
                    this.fillWithMetadataBlocks(par1World, par3StructureBoundingBox, 2, 2, var10, 2, 2, var10, Block.wood.blockID, iHorizontalLogMetadata, 0, 0, false);

                } else {
                    this.fillWithMetadataBlocks(par1World, par3StructureBoundingBox, 0, 2, var10, 2, 2, var10, Block.wood.blockID, iHorizontalLogMetadata, 0, 0, false);
                }
                this.randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.1F, 0, 2, var10 - 1, BTWBlocks.web.blockID, 0);
                this.randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.1F, 2, 2, var10 - 1, BTWBlocks.web.blockID, 0);
                this.randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.1F, 0, 2, var10 + 1, BTWBlocks.web.blockID, 0);
                this.randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.1F, 2, 2, var10 + 1, BTWBlocks.web.blockID, 0);
                this.randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.05F, 0, 2, var10 - 2, BTWBlocks.web.blockID, 0);
                this.randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.05F, 2, 2, var10 - 2, BTWBlocks.web.blockID, 0);
                this.randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.05F, 0, 2, var10 + 2, BTWBlocks.web.blockID, 0);
                this.randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.05F, 2, 2, var10 + 2, BTWBlocks.web.blockID, 0);
                this.randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.05F, 1, 2, var10 - 1, BTWBlocks.finiteUnlitTorch.blockID, 8);
                this.randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.05F, 1, 2, var10 + 1, BTWBlocks.finiteUnlitTorch.blockID, 8);

                //SC: ADDED
                WeightedRandomChestContent[] arr = new WeightedRandomChestContent[minecartLoot.size()];

                if (par2Random.nextInt(100) == 0) {
//                    this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 2, 0, var10 - 1, WeightedRandomChestContent.func_92080_a(StructureMineshaftPieces.func_78816_a(), new WeightedRandomChestContent[] {Item.enchantedBook.func_92114_b(par2Random)}), 3 + par2Random.nextInt(4));
                    this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 2, 0, var10 - 1, WeightedRandomChestContent.func_92080_a(minecartLoot.toArray(arr), Item.enchantedBook.func_92114_b(par2Random)), 3 + par2Random.nextInt(4));
                }

                if (par2Random.nextInt(100) == 0) {
//                    this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 0, 0, var10 + 1, WeightedRandomChestContent.func_92080_a(StructureMineshaftPieces.func_78816_a(), new WeightedRandomChestContent[] {Item.enchantedBook.func_92114_b(par2Random)}), 3 + par2Random.nextInt(4));
                    this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 0, 0, var10 + 1, WeightedRandomChestContent.func_92080_a(minecartLoot.toArray(arr), Item.enchantedBook.func_92114_b(par2Random)), 3 + par2Random.nextInt(4));
                }

                if (this.hasSpiders && !this.spawnerPlaced) {
                    var11 = this.getYWithOffset(0);
                    int var12 = var10 - 1 + par2Random.nextInt(3);
                    int var13 = this.getXWithOffset(1, var12);
                    var12 = this.getZWithOffset(1, var12);

                    if (par3StructureBoundingBox.isVecInside(var13, var11, var12)) {
                        this.spawnerPlaced = true;
                        par1World.setBlock(var13, var11, var12, Block.mobSpawner.blockID, 0, 2);
                        TileEntityMobSpawner var14 = (TileEntityMobSpawner) par1World.getBlockTileEntity(var13, var11, var12);

                        if (var14 != null) {
                            var14.func_98049_a().setMobID("CaveSpider");
                        }
                    }
                }
            }

            for (var9 = 0; var9 <= 2; ++var9) {
                for (var10 = 0; var10 <= var8; ++var10) {
                    var11 = this.getBlockIdAtCurrentPosition(par1World, var9, -1, var10, par3StructureBoundingBox);

                    if (var11 == 0) {
                        this.placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, var9, -1, var10, par3StructureBoundingBox);
                    }
                }
            }

            if (this.hasRails) {
                for (var9 = 0; var9 <= var8; ++var9) {
                    var10 = this.getBlockIdAtCurrentPosition(par1World, 1, -1, var9, par3StructureBoundingBox);

                    if (var10 > 0 && Block.opaqueCubeLookup[var10]) {
                        this.randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.7F, 1, 0, var9, Block.rail.blockID, this.getMetadataWithOffset(Block.rail.blockID, 0));
                    }
                }
            }

            return true;
        }
    }
}
