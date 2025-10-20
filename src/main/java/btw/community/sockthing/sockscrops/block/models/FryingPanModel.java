// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package btw.community.sockthing.sockscrops.block.models;

import net.minecraft.src.Entity;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class FryingPanModel extends ModelBase {
    private final ModelRenderer bone2;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;

    public FryingPanModel() {
        textureWidth = 32;
        textureHeight = 32;

        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bone2.setTextureOffset(0, 0).addBox(-4.0F, -2.0F, -4.0F, 8, 2, 8, 0.0F);
        this.bone2.setTextureOffset(0, 10).addBox(4.0F, -4.0F, -4.0F, 1, 3, 9, 0.0F);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(0.0F, -3.0F, 8.0F);
        bone2.addChild(cube_r1);
        setRotation(cube_r1, 0.0F, -1.5708F, 0.0F);
        this.cube_r1.setTextureOffset(0, 22).addBox(-3.0F, -1.0F, -1.0F, 6, 2, 2, 0.0F);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(0.5F, -2.5F, -4.5F);
        bone2.addChild(cube_r2);
        setRotation(cube_r2, 0.0F, 1.5708F, 0.0F);
        this.cube_r2.setTextureOffset(0, 10).addBox(-0.5F, -1.5F, -4.5F, 1, 3, 9, 0.0F);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setRotationPoint(0.0F, -2.5F, 0.0F);
        bone2.addChild(cube_r3);
        setRotation(cube_r3, 0.0F, -1.5708F, 0.0F);
        this.cube_r3.setTextureOffset(0, 10).addBox(4.0F, -1.5F, -4.0F, 1, 3, 9, 0.0F);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setRotationPoint(-4.5F, -2.5F, -0.5F);
        bone2.addChild(cube_r4);
        setRotation(cube_r4, 0.0F, 3.1416F, 0.0F);
        this.cube_r4.setTextureOffset(0, 10).addBox(-0.5F, -1.5F, -4.5F, 1, 3, 9, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        bone2.render(f5);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, f and f1 are used for animating the movement of arms
     * and legs, where f represents the time(so that arms and legs swing back and forth) and f1 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {

    }

    /**
     * Sets the rotation of a ModelRenderer. Only called if the ModelRenderer has a rotation
     */
    public void setRotation(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}