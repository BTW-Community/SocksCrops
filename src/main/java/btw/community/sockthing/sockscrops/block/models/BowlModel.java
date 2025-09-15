package btw.community.sockthing.sockscrops.block.models;

import net.minecraft.src.Entity;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class BowlModel extends ModelBase {
    private final ModelRenderer bowl;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;

    public BowlModel() {
        textureWidth = 32;
        textureHeight = 32;

        bowl = new ModelRenderer(this);
        bowl.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.bowl.setTextureOffset(0, 0).addBox(-2.0F, 1.0F, -2.0F, 4, 1, 4, 0.0F);
        this.bowl.setTextureOffset(0, 5).addBox(2.0F, -1.0F, -2.0F, 1, 2, 4, 0.0F);
        this.bowl.setTextureOffset(10, 5).addBox(-3.0F, -1.0F, -2.0F, 1, 2, 4, 0.0F);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(0.0F, 0.0F, 1.0F);
        bowl.addChild(cube_r1);
        setRotation(cube_r1, 0.0F, 1.5708F, 0.0F);
        this.cube_r1.setTextureOffset(10, 11).addBox(3.0F, -1.0F, -2.0F, 1, 2, 4, 0.0F);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(0.0F, 0.0F, -1.0F);
        bowl.addChild(cube_r2);
        setRotation(cube_r2, 0.0F, 1.5708F, 0.0F);
        this.cube_r2.setTextureOffset(0, 11).addBox(-4.0F, -1.0F, -2.0F, 1, 2, 4, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        bowl.render(f5);
    }

    /**
     *	Sets the rotation of a ModelRenderer. Only called if the ModelRenderer has a rotation
     */
    public void setRotation(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
