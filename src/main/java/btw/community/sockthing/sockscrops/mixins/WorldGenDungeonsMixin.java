package btw.community.sockthing.sockscrops.mixins;

import btw.block.BTWBlocks;
import btw.community.sockthing.sockscrops.world.LootHandler;
import btw.item.util.RandomItemStack;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Random;

@Mixin(WorldGenDungeons.class)
public abstract class WorldGenDungeonsMixin extends WorldGenerator {
    @Shadow
    protected abstract ItemStack pickCheckLootItem(Random par1Random);

    private static final ArrayList<RandomItemStack> chestLoot = new ArrayList();

    static {
        chestLoot.add(new RandomItemStack(Item.saddle.itemID, 0, 1, 1, 1));
        chestLoot.add(new RandomItemStack(Item.ingotIron.itemID, 0, 1, 4, 1));
        chestLoot.add(new RandomItemStack(Item.bread.itemID, 0, 1, 1, 1));
        chestLoot.add(new RandomItemStack(Item.wheat.itemID, 0, 1, 4, 1));
        chestLoot.add(new RandomItemStack(Item.gunpowder.itemID, 0, 1, 4, 1));
        chestLoot.add(new RandomItemStack(Item.silk.itemID, 0, 1, 4, 1));
        chestLoot.add(new RandomItemStack(Item.bucketEmpty.itemID, 0, 1, 1, 1));

        LootHandler.addDungeonChestLoot(chestLoot);
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        WorldGenDungeons thisObject = (WorldGenDungeons) (Object) this;

        byte var6 = 3;
        int var7 = par2Random.nextInt(2) + 2;
        int var8 = par2Random.nextInt(2) + 2;
        int var9 = 0;
        int var10;
        int var11;
        int var12;

        for (var10 = par3 - var7 - 1; var10 <= par3 + var7 + 1; ++var10) {
            for (var11 = par4 - 1; var11 <= par4 + var6 + 1; ++var11) {
                for (var12 = par5 - var8 - 1; var12 <= par5 + var8 + 1; ++var12) {
                    Material var13 = par1World.getBlockMaterial(var10, var11, var12);

                    if (var11 == par4 - 1 && !var13.isSolid()) {
                        return false;
                    }

                    if (var11 == par4 + var6 + 1 && !var13.isSolid()) {
                        return false;
                    }

                    if ((var10 == par3 - var7 - 1 || var10 == par3 + var7 + 1 || var12 == par5 - var8 - 1 || var12 == par5 + var8 + 1) && var11 == par4 && par1World.isAirBlock(var10, var11, var12) && par1World.isAirBlock(var10, var11 + 1, var12)) {
                        ++var9;
                    }
                }
            }
        }

        if (var9 >= 1 && var9 <= 5) {
            for (var10 = par3 - var7 - 1; var10 <= par3 + var7 + 1; ++var10) {
                for (var11 = par4 + var6; var11 >= par4 - 1; --var11) {
                    for (var12 = par5 - var8 - 1; var12 <= par5 + var8 + 1; ++var12) {
                        if (var10 != par3 - var7 - 1 && var11 != par4 - 1 && var12 != par5 - var8 - 1 && var10 != par3 + var7 + 1 && var11 != par4 + var6 + 1 && var12 != par5 + var8 + 1) {
                            par1World.setBlockToAir(var10, var11, var12);
                        } else if (var11 >= 0 && !par1World.getBlockMaterial(var10, var11 - 1, var12).isSolid()) {
                            par1World.setBlockToAir(var10, var11, var12);
                        } else if (par1World.getBlockMaterial(var10, var11, var12).isSolid()) {
                            if (var11 == par4 - 1 && par2Random.nextInt(4) != 0) {
                                par1World.setBlock(var10, var11, var12, Block.cobblestoneMossy.blockID, 0, 2);
                            } else {
                                par1World.setBlock(var10, var11, var12, Block.cobblestone.blockID, 0, 2);
                            }
                        }
                    }
                }
            }

            var10 = 0;

            while (var10 < 2) {
                var11 = 0;

                while (true) {
                    if (var11 < 3) {
                        label210:
                        {
                            var12 = par3 + par2Random.nextInt(var7 * 2 + 1) - var7;
                            int var14 = par5 + par2Random.nextInt(var8 * 2 + 1) - var8;

                            if (par1World.isAirBlock(var12, par4, var14)) {
                                int var15 = 0;

                                if (par1World.getBlockMaterial(var12 - 1, par4, var14).isSolid()) {
                                    ++var15;
                                }

                                if (par1World.getBlockMaterial(var12 + 1, par4, var14).isSolid()) {
                                    ++var15;
                                }

                                if (par1World.getBlockMaterial(var12, par4, var14 - 1).isSolid()) {
                                    ++var15;
                                }

                                if (par1World.getBlockMaterial(var12, par4, var14 + 1).isSolid()) {
                                    ++var15;
                                }

                                if (var15 == 1) {
                                    par1World.setBlock(var12, par4, var14, BTWBlocks.chest.blockID, 0, 2);
                                    TileEntityChest var16 = (TileEntityChest) par1World.getBlockTileEntity(var12, par4, var14);

                                    if (var16 != null) {
                                        for (int var17 = 0; var17 < 8; ++var17) {
                                            ItemStack var18 = this.pickCheckLootItem(par2Random);

                                            if (var18 != null) {
                                                var16.setInventorySlotContents(par2Random.nextInt(var16.getSizeInventory()), var18);
                                            }
                                        }
                                    }

                                    // FCMOD: Code added
                                    //SC REPLACED
//                                    filterChestContentsForDepth(par1World, var12, par4, var14);
                                    // END FCMOD

                                    //SC ADDED
                                    filterDungeonChestContents(par1World, var12, par4, var14);

                                    break label210;
                                }
                            }

                            ++var11;
                            continue;
                        }
                    }

                    ++var10;
                    break;
                }
            }

            par1World.setBlock(par3, par4, par5, Block.mobSpawner.blockID, 0, 2);
            TileEntityMobSpawner var19 = (TileEntityMobSpawner) par1World.getBlockTileEntity(par3, par4, par5);

            if (var19 != null) {
                var19.func_98049_a().setMobID(thisObject.pickMobSpawner(par2Random));
            } else {
                System.err.println("Failed to fetch mob spawner entity at (" + par3 + ", " + par4 + ", " + par5 + ")");
            }

            return true;
        } else {
            return false;
        }
    }

    private ItemStack pickLootItem(Random random) {
        int rand = random.nextInt(12);
        RandomItemStack[] arr = new RandomItemStack[chestLoot.size()];
        ItemStack itemStack = RandomItemStack.getRandomStack(random, chestLoot.toArray(arr));

        if (rand > 6) {
            if (rand == 7 && random.nextInt(100) == 0) {
                itemStack = new ItemStack(Item.appleGold);
            } else if (rand == 8 && random.nextInt(2) == 0) {
                itemStack = new ItemStack(Item.redstone, random.nextInt(4) + 1);
            } else if (rand == 9 && random.nextInt(10) == 0) {
                itemStack = new ItemStack(Item.itemsList[Item.record13.itemID + random.nextInt(2)]);
            } else if (rand == 10 && random.nextInt(2) == 0) {
                itemStack = new ItemStack(Item.dyePowder, 1, 3);
            } else if (rand == 11) {
                itemStack = Item.enchantedBook.func_92109_a(random);
            }
        }

        return itemStack;
    }

    private void filterDungeonChestContents(World world, int chestPosX, int chestPosY, int chestPosZ) {
        LootHandler.filterDungeonChestContents(world, chestPosX, chestPosY, chestPosZ);
    }

}
