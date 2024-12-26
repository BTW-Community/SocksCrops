// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package btw.community.sockthing.sockscrops.block.models;

import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class CodEntityModel extends ModelBase {
    private final ModelRenderer bone;
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer leftFin;
    private final ModelRenderer rightFin;
    private final ModelRenderer tailfin;
    private final ModelRenderer waist;

    public CodEntityModel() {
        textureWidth = 32;
        textureHeight = 32;

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 0.0F, 0.0F);


        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, -2.0F, -5.0F);
        bone.addChild(body);
        this.body.setTextureOffset(0, 0).addBox(-1.0F, -2.0F, 1.0F, 2, 4, 7, 0.0F);
        this.body.setTextureOffset(20, -6).addBox(0.0F, -3.0F, 0.0F, 0, 1, 6, 0.0F);
        this.body.setTextureOffset(22, -1).addBox(0.0F, 2.0F, 3.0F, 0, 1, 2, 0.0F);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(head);
        this.head.setTextureOffset(0, 0).addBox(-0.9992F, -2.0008F, -3.0F, 2, 3, 1, 0.0F);
        this.head.setTextureOffset(11, 0).addBox(-1.0F, -2.0F, -2.0F, 2, 4, 3, 0.0F);

        leftFin = new ModelRenderer(this);
        leftFin.setRotationPoint(1.0F, 1.0F, 0.0F);
        body.addChild(leftFin);
        setRotation(leftFin, 0.0F, 0.0F, 0.6109F);
        this.leftFin.setTextureOffset(24, 4).addBox(0.0F, 0.0F, 0.0F, 2, 1, 2, 0.0F);

        rightFin = new ModelRenderer(this);
        rightFin.setRotationPoint(-1.0F, 1.0F, 0.0F);
        body.addChild(rightFin);
        setRotation(rightFin, 0.0F, 0.0F, -0.6109F);
        this.rightFin.setTextureOffset(24, 1).addBox(-2.0F, 0.0F, 0.0F, 2, 1, 2, 0.0F);

        tailfin = new ModelRenderer(this);
        tailfin.setRotationPoint(0.0F, 2.0F, 8.0F);
        body.addChild(tailfin);
        this.tailfin.setTextureOffset(20, 1).addBox(0.0F, -4.0F, 0.0F, 0, 4, 6, 0.0F);

        waist = new ModelRenderer(this);
        waist.setRotationPoint(0.0F, 2.0F, 0.0F);
        body.addChild(waist);

    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        bone.render(f5);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, f and f1 are used for animating the movement of arms
     * and legs, where f represents the time(so that arms and legs swing back and forth) and f1 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(float ticks, float playerDistance, float f2, float f3, float f4, float f5, Entity entity) {

        float speed = 0.3F;

        if (playerDistance != -1)
        {
            body.setRotationPoint(8.0F, -2.0F, -5.0F);
            this.bone.rotateAngleY = (float) Math.toRadians(ticks*4);

            this.head.rotateAngleY = MathHelper.sin(ticks * speed) * (float)Math.PI/8;
            this.body.rotateAngleY = -this.head.rotateAngleY;

            this.rightFin.rotateAngleZ = MathHelper.cos(ticks * speed) * (float)Math.PI/8;
            this.leftFin.rotateAngleZ = -this.rightFin.rotateAngleZ;

            this.tailfin.rotateAngleY = this.head.rotateAngleY;
        }
        else
        {
            body.setRotationPoint(0.0F, 0.0F, -3.0F);
            bone.setRotationPoint(0, -1F, 0);
            this.bone.rotateAngleZ = -(float)Math.PI/2;
            this.bone.rotateAngleX = -(f3 / (180F / (float)Math.PI)) - (float)Math.PI/2;


        }


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