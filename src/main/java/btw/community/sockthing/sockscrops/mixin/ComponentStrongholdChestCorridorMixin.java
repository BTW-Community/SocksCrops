package btw.community.sockthing.sockscrops.mixin;

import btw.community.sockthing.sockscrops.world.LootHandler;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Random;

@Mixin(ComponentStrongholdChestCorridor.class)
public abstract class ComponentStrongholdChestCorridorMixin extends ComponentStronghold {

    private static final ArrayList<WeightedRandomChestContent> chestLoot = new ArrayList();
    @Shadow
    private EnumDoor doorType;
    @Shadow
    private boolean hasMadeChest;

    protected ComponentStrongholdChestCorridorMixin(int par1) {
        super(par1);
    }

    static {
        chestLoot.add(new WeightedRandomChestContent(Item.enderPearl.itemID, 0, 1, 1, 10));
        chestLoot.add(new WeightedRandomChestContent(Item.diamond.itemID, 0, 1, 3, 3));
        chestLoot.add(new WeightedRandomChestContent(Item.ingotIron.itemID, 0, 1, 5, 10));
        chestLoot.add(new WeightedRandomChestContent(Item.ingotGold.itemID, 0, 1, 3, 5));
        chestLoot.add(new WeightedRandomChestContent(Item.redstone.itemID, 0, 4, 9, 5));
        chestLoot.add(new WeightedRandomChestContent(Item.bread.itemID, 0, 1, 3, 15));
        chestLoot.add(new WeightedRandomChestContent(Item.appleRed.itemID, 0, 1, 3, 15));
        chestLoot.add(new WeightedRandomChestContent(Item.pickaxeIron.itemID, 0, 1, 1, 5));
        chestLoot.add(new WeightedRandomChestContent(Item.swordIron.itemID, 0, 1, 1, 5));
        chestLoot.add(new WeightedRandomChestContent(Item.plateIron.itemID, 0, 1, 1, 5));
        chestLoot.add(new WeightedRandomChestContent(Item.helmetIron.itemID, 0, 1, 1, 5));
        chestLoot.add(new WeightedRandomChestContent(Item.legsIron.itemID, 0, 1, 1, 5));
        chestLoot.add(new WeightedRandomChestContent(Item.bootsIron.itemID, 0, 1, 1, 5));
        chestLoot.add(new WeightedRandomChestContent(Item.appleGold.itemID, 0, 1, 1, 1));

        LootHandler.addStrongholdChestLoot(chestLoot);
    }

    @Override
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
        if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
            return false;
        } else {
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 6, true, par2Random, StructureStrongholdPieces.getStrongholdStones());
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, this.doorType, 1, 1, 0);
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, EnumDoor.OPENING, 1, 1, 6);
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 2, 3, 1, 4, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
            this.placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 5, 3, 1, 1, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 5, 3, 1, 5, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 5, 3, 2, 2, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 5, 3, 2, 4, par3StructureBoundingBox);
            int var4;

            for (var4 = 2; var4 <= 4; ++var4) {
                this.placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 5, 2, 1, var4, par3StructureBoundingBox);
            }

            if (!this.hasMadeChest) {
                var4 = this.getYWithOffset(2);
                int var5 = this.getXWithOffset(3, 3);
                int var6 = this.getZWithOffset(3, 3);

                if (par3StructureBoundingBox.isVecInside(var5, var4, var6)) {
                    this.hasMadeChest = true;

                    //SAU: ADDED
                    WeightedRandomChestContent[] arr = new WeightedRandomChestContent[chestLoot.size()];
                    this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 2, 3, WeightedRandomChestContent.func_92080_a(chestLoot.toArray(arr), Item.enchantedBook.func_92114_b(par2Random)), 2 + par2Random.nextInt(2));

//                    this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 2, 3, WeightedRandomChestContent.func_92080_a(strongholdChestContents, new WeightedRandomChestContent[] {Item.enchantedBook.func_92114_b(par2Random)}), 2 + par2Random.nextInt(2));
                }
            }

            return true;
        }
    }
}
