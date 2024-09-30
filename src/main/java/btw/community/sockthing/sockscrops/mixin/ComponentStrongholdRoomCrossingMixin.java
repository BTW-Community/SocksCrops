package btw.community.sockthing.sockscrops.mixin;

import btw.block.BTWBlocks;
import btw.community.sockthing.sockscrops.world.LootHandler;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Random;

@Mixin(ComponentStrongholdRoomCrossing.class)
public abstract class ComponentStrongholdRoomCrossingMixin extends ComponentStronghold {

    private static final ArrayList<WeightedRandomChestContent> chestLoot = new ArrayList();
    @Shadow
    private EnumDoor doorType;
    @Shadow
    private int roomType;

    protected ComponentStrongholdRoomCrossingMixin(int par1) {
        super(par1);
    }

    static {

        chestLoot.add(new WeightedRandomChestContent(Item.ingotIron.itemID, 0, 1, 5, 10));
        chestLoot.add(new WeightedRandomChestContent(Item.ingotGold.itemID, 0, 1, 3, 5));
        chestLoot.add(new WeightedRandomChestContent(Item.redstone.itemID, 0, 4, 9, 5));
        chestLoot.add(new WeightedRandomChestContent(Item.coal.itemID, 0, 3, 8, 10));
        chestLoot.add(new WeightedRandomChestContent(Item.bread.itemID, 0, 1, 3, 15));
        chestLoot.add(new WeightedRandomChestContent(Item.appleRed.itemID, 0, 1, 3, 15));
        chestLoot.add(new WeightedRandomChestContent(Item.pickaxeIron.itemID, 0, 1, 1, 1));

        LootHandler.addStrongholdCrossingChestLoot(chestLoot);
    }

    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
        if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
            return false;
        } else {
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 10, 6, 10, true, par2Random, StructureStrongholdPieces.getStrongholdStones());
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, this.doorType, 4, 1, 0);
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 10, 6, 3, 10, 0, 0, false);
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 4, 0, 3, 6, 0, 0, false);
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 10, 1, 4, 10, 3, 6, 0, 0, false);
            int var4;

            switch (this.roomType) {
                case 0:
                    this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 5, 1, 5, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 5, 2, 5, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 5, 3, 5, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 4, 3, 5, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 6, 3, 5, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 5, 3, 4, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 5, 3, 6, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 0, 4, 1, 4, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 0, 4, 1, 5, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 0, 4, 1, 6, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 0, 6, 1, 4, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 0, 6, 1, 5, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 0, 6, 1, 6, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 0, 5, 1, 4, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 0, 5, 1, 6, par3StructureBoundingBox);
                    break;

                case 1:
                    for (var4 = 0; var4 < 5; ++var4) {
                        this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 3, 1, 3 + var4, par3StructureBoundingBox);
                        this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 7, 1, 3 + var4, par3StructureBoundingBox);
                        this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 3 + var4, 1, 3, par3StructureBoundingBox);
                        this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 3 + var4, 1, 7, par3StructureBoundingBox);
                    }

                    this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 5, 1, 5, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 5, 2, 5, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 5, 3, 5, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.waterMoving.blockID, 0, 5, 4, 5, par3StructureBoundingBox);
                    break;

                case 2:
                    for (var4 = 1; var4 <= 9; ++var4) {
                        this.placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 1, 3, var4, par3StructureBoundingBox);
                        this.placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 9, 3, var4, par3StructureBoundingBox);
                    }

                    for (var4 = 1; var4 <= 9; ++var4) {
                        this.placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, var4, 3, 1, par3StructureBoundingBox);
                        this.placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, var4, 3, 9, par3StructureBoundingBox);
                    }

                    this.placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 5, 1, 4, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 5, 1, 6, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 5, 3, 4, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 5, 3, 6, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 4, 1, 5, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 6, 1, 5, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 4, 3, 5, par3StructureBoundingBox);
                    this.placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 6, 3, 5, par3StructureBoundingBox);

                    for (var4 = 1; var4 <= 3; ++var4) {
                        this.placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 4, var4, 4, par3StructureBoundingBox);
                        this.placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 6, var4, 4, par3StructureBoundingBox);
                        this.placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 4, var4, 6, par3StructureBoundingBox);
                        this.placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 6, var4, 6, par3StructureBoundingBox);
                    }

                    this.placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 5, 3, 5, par3StructureBoundingBox);

                    for (var4 = 2; var4 <= 8; ++var4) {
                        this.placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 2, 3, var4, par3StructureBoundingBox);
                        this.placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 3, 3, var4, par3StructureBoundingBox);

                        if (var4 <= 3 || var4 >= 7) {
                            this.placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 4, 3, var4, par3StructureBoundingBox);
                            this.placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 5, 3, var4, par3StructureBoundingBox);
                            this.placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 6, 3, var4, par3StructureBoundingBox);
                        }

                        this.placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 7, 3, var4, par3StructureBoundingBox);
                        this.placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 8, 3, var4, par3StructureBoundingBox);
                    }

                    int iFacing = getMetadataWithOffset(Block.ladder.blockID, 4); // legacy ladder metadata is straight facing
                    int iMetadata = BTWBlocks.ladder.setFacing(0, iFacing);

                    placeBlockAtCurrentPosition(par1World, BTWBlocks.ladder.blockID, iMetadata, 9, 1, 3, par3StructureBoundingBox);
                    placeBlockAtCurrentPosition(par1World, BTWBlocks.ladder.blockID, iMetadata, 9, 2, 3, par3StructureBoundingBox);
                    placeBlockAtCurrentPosition(par1World, BTWBlocks.ladder.blockID, iMetadata, 9, 3, 3, par3StructureBoundingBox);
//                    this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 4, 8, WeightedRandomChestContent.func_92080_a(strongholdRoomCrossingChestContents, new WeightedRandomChestContent[] {Item.enchantedBook.func_92114_b(par2Random)}), 1 + par2Random.nextInt(4));

                    WeightedRandomChestContent[] arr = new WeightedRandomChestContent[chestLoot.size()];
                    this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 4, 8, WeightedRandomChestContent.func_92080_a(chestLoot.toArray(arr), Item.enchantedBook.func_92114_b(par2Random)), 1 + par2Random.nextInt(4));
            }

            return true;
        }
    }
}
