package btw.community.sockthing.sockscrops.block.models;

import btw.community.sockthing.sockscrops.block.tileentities.CookingPotTileEntity;
import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class CookingPotModel extends ModelBase {
    private final ModelRenderer lid;
    private final ModelRenderer pot;
    private final ModelRenderer burned;
    private final ModelRenderer spoiled;
    private final ModelRenderer water;
    private final ModelRenderer milk;
    private final ModelRenderer chocolateMilk;
    private final ModelRenderer chicken;
    private final ModelRenderer mushroom;
    private final ModelRenderer beef;
    private final ModelRenderer chowder;

    public CookingPotModel() {
        textureWidth = 64;
        textureHeight = 64;

        lid = new ModelRenderer(this);
        lid.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.lid.setTextureOffset(0, 0).addBox(-4.0F, -1.0F, -4.0F, 8, 1, 8, 0.0F);
        this.lid.setTextureOffset(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2, 1, 2, 0.0F);

        pot = new ModelRenderer(this);
        pot.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.pot.setTextureOffset(31, 1).addBox(-5.0F, -7.0F, -4.0F, 2, 6, 8, 0.0F);
        this.pot.setTextureOffset(31, 1).addBox(3.0F, -7.0F, -4.0F, 2, 6, 8, 0.0F);
        this.pot.setTextureOffset(44, 0).addBox(-4.0F, -7.0F, -5.0F, 8, 6, 2, 0.0F);
        this.pot.setTextureOffset(24, 0).addBox(-6.0F, -6.0F, -2.0F, 1, 1, 4, 0.0F);
        this.pot.setTextureOffset(24, 0).addBox(5.0F, -6.0F, -2.0F, 1, 1, 4, 0.0F);
        this.pot.setTextureOffset(44, 0).addBox(-4.0F, -7.0F, 3.0F, 8, 6, 2, 0.0F);
        this.pot.setTextureOffset(0, 0).addBox(-4.0F, -1.0F, -4.0F, 8, 1, 8, 0.0F);

        burned = new ModelRenderer(this);
        burned.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.burned.setTextureOffset(-6, 16).addBox(-3.0F, -6.0F, -3.0F, 6, 1, 6, 0.0F);

        spoiled = new ModelRenderer(this);
        spoiled.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.spoiled.setTextureOffset(0, 16).addBox(-3.0F, -6.0F, -3.0F, 6, 1, 6, 0.0F);

        water = new ModelRenderer(this);
        water.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.water.setTextureOffset(6, 16).addBox(-3.0F, -6.0F, -3.0F, 6, 1, 6, 0.0F);

        milk = new ModelRenderer(this);
        milk.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.milk.setTextureOffset(12, 16).addBox(-3.0F, -6.0F, -3.0F, 6, 1, 6, 0.0F);

        chocolateMilk = new ModelRenderer(this);
        chocolateMilk.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.chocolateMilk.setTextureOffset(18, 16).addBox(-3.0F, -6.0F, -3.0F, 6, 1, 6, 0.0F);

        mushroom = new ModelRenderer(this);
        mushroom.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.mushroom.setTextureOffset(24, 16).addBox(-3.0F, -6.0F, -3.0F, 6, 1, 6, 0.0F);

        beef = new ModelRenderer(this);
        beef.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.beef.setTextureOffset(30, 15).addBox(-3.0F, -6.0F, -3.0F, 6, 1, 6, 0.0F);

        chicken = new ModelRenderer(this);
        chicken.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.chicken.setTextureOffset(30, 16).addBox(-3.0F, -6.0F, -3.0F, 6, 1, 6, 0.0F);

        chowder = new ModelRenderer(this);
        chowder.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.chowder.setTextureOffset(36, 16).addBox(-3.0F, -6.0F, -3.0F, 6, 1, 6, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        pot.render(f5);
    }

    public void renderLid(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        //this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        lid.render(f5);
    }

    public void renderContents(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int type) {
        //this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        switch (type) {
            case CookingPotTileEntity.SPOILED:
                spoiled.render(f5);
                break;

            case CookingPotTileEntity.BURNED:
                burned.render(f5);
                break;

            case CookingPotTileEntity.WATER:
                water.render(f5);
                break;

            case CookingPotTileEntity.MILK:
                milk.render(f5);
                break;

            case CookingPotTileEntity.CHOCOLATE_MILK:
                chocolateMilk.render(f5);
                break;

            case CookingPotTileEntity.MUSHROOM_SOUP:
                mushroom.render(f5);
                break;

            case CookingPotTileEntity.HEARTY_STEW:
                beef.render(f5);
                break;

            case CookingPotTileEntity.CHICKEN_SOUP:
                chicken.render(f5);
                break;

            case CookingPotTileEntity.CHOWDER:
                chowder.render(f5);
                break;

            default:
                break;
        }

    }


    /**
     * Sets the model's various rotation angles. For bipeds, f and f1 are used for animating the movement of arms
     * and legs, where f represents the time(so that arms and legs swing back and forth) and f1 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.pot.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.lid.rotateAngleY = f3 / (180F / (float)Math.PI);
        spoiled.rotateAngleY = f3 / (180F / (float)Math.PI);
        burned.rotateAngleY = f3 / (180F / (float)Math.PI);
        water.rotateAngleY = f3 / (180F / (float)Math.PI);
        milk.rotateAngleY = f3 / (180F / (float)Math.PI);
        chocolateMilk.rotateAngleY = f3 / (180F / (float)Math.PI);
        mushroom.rotateAngleY = f3 / (180F / (float)Math.PI);
        beef.rotateAngleY = f3 / (180F / (float)Math.PI);
        chicken.rotateAngleY = f3 / (180F / (float)Math.PI);
        chowder.rotateAngleY = f3 / (180F / (float)Math.PI);

        float var8 = (MathHelper.sin(f * 0.02F) * 0.1F + 1.25F) * f4;

        if (f4 != 0)
        {
            this.lid.rotateAngleZ = MathHelper.sin(var8) * 0.15F;
            this.lid.rotateAngleX = -MathHelper.cos(var8) * 0.15F;
            lid.setRotationPoint(0.0F, -8.0F, 0.0F);
        }
        else
        {
            this.lid.rotateAngleZ = 0;
            this.lid.rotateAngleX = 0;
            lid.setRotationPoint(0.0F, -7.0F, 0.0F);
        }
    }
}
