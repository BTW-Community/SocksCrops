// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package btw.community.sockthing.sockscrops.block.models;

import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class PufferFishEntityModel extends ModelBase {
    private final ModelRenderer bone_large;
    private final ModelRenderer body_large;
    private final ModelRenderer leftFin2;
    private final ModelRenderer rightFin2;
    private final ModelRenderer spines_top_front2;
    private final ModelRenderer spines_top_mid;
    private final ModelRenderer spines_top_back2;
    private final ModelRenderer spines_bottom_front2;
    private final ModelRenderer spines_bottom_mid;
    private final ModelRenderer spines_bottom_back2;
    private final ModelRenderer spines_left_front2;
    private final ModelRenderer spines_left_mid;
    private final ModelRenderer spines_left_back2;
    private final ModelRenderer spines_right_front2;
    private final ModelRenderer spines_right_mid;
    private final ModelRenderer spines_right_back2;
    private final ModelRenderer bone_mid;
    private final ModelRenderer body_mid;
    private final ModelRenderer leftFin3;
    private final ModelRenderer rightFin3;
    private final ModelRenderer spines_top_front;
    private final ModelRenderer spines_top_back;
    private final ModelRenderer spines_bottom_front;
    private final ModelRenderer spines_bottom_back;
    private final ModelRenderer spines_left_front;
    private final ModelRenderer spines_left_back;
    private final ModelRenderer spines_right_front;
    private final ModelRenderer spines_right_back;
    private final ModelRenderer bone_small;
    private final ModelRenderer body_small;
    private final ModelRenderer tailfin;
    private final ModelRenderer leftFin;
    private final ModelRenderer rightFin;

    public PufferFishEntityModel() {
        textureWidth = 32;
        textureHeight = 32;

        bone_large = new ModelRenderer(this);
        bone_large.setRotationPoint(0.0F, 0.0F, 0.0F);


        body_large = new ModelRenderer(this);
        body_large.setRotationPoint(0.0F, -4.0F, 0.0F);
        bone_large.addChild(body_large);
        setRotation(body_large, 0.0F, 0, 0.0F);
        this.body_large.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);

        leftFin2 = new ModelRenderer(this);
        leftFin2.setRotationPoint(4.0F, -3.0F, -2.0F);
        body_large.addChild(leftFin2);
        this.leftFin2.setTextureOffset(24, 3).addBox(0.0F, 0.0F, -0.9904F, 2, 1, 2, 0.0F);

        rightFin2 = new ModelRenderer(this);
        rightFin2.setRotationPoint(-4.0F, -3.0F, -2.0F);
        body_large.addChild(rightFin2);
        this.rightFin2.setTextureOffset(24, 0).addBox(-1.9968F, 0.0F, -0.992F, 2, 1, 2, 0.0F);

        spines_top_front2 = new ModelRenderer(this);
        spines_top_front2.setRotationPoint(-4.0F, -4.0F, -4.0F);
        body_large.addChild(spines_top_front2);
        setRotation(spines_top_front2, 0.7854F, 0.0F, 0.0F);
        this.spines_top_front2.setTextureOffset(14, 16).addBox(0.0F, -1.0F, 0.0F, 8, 1, 1, 0.0F);

        spines_top_mid = new ModelRenderer(this);
        spines_top_mid.setRotationPoint(0.0F, -4.0F, 0.0F);
        body_large.addChild(spines_top_mid);
        this.spines_top_mid.setTextureOffset(14, 16).addBox(-4.0F, -1.0F, 0.0F, 8, 1, 1, 0.0F);

        spines_top_back2 = new ModelRenderer(this);
        spines_top_back2.setRotationPoint(0.0F, -4.0F, 4.0F);
        body_large.addChild(spines_top_back2);
        setRotation(spines_top_back2, -0.7854F, 0.0F, 0.0F);
        this.spines_top_back2.setTextureOffset(14, 16).addBox(-4.0F, -1.0F, 0.0F, 8, 1, 1, 0.0F);

        spines_bottom_front2 = new ModelRenderer(this);
        spines_bottom_front2.setRotationPoint(0.0F, 4.0F, -4.0F);
        body_large.addChild(spines_bottom_front2);
        setRotation(spines_bottom_front2, -0.7854F, 0.0F, 0.0F);
        this.spines_bottom_front2.setTextureOffset(14, 19).addBox(-4.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);

        spines_bottom_mid = new ModelRenderer(this);
        spines_bottom_mid.setRotationPoint(0.0F, 5.0F, 0.0F);
        body_large.addChild(spines_bottom_mid);
        this.spines_bottom_mid.setTextureOffset(14, 19).addBox(-4.0F, -1.0F, 0.0F, 8, 1, 1, 0.0F);

        spines_bottom_back2 = new ModelRenderer(this);
        spines_bottom_back2.setRotationPoint(0.0F, 4.0F, 4.0F);
        body_large.addChild(spines_bottom_back2);
        setRotation(spines_bottom_back2, 0.7854F, 0.0F, 0.0F);
        this.spines_bottom_back2.setTextureOffset(14, 19).addBox(-4.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);

        spines_left_front2 = new ModelRenderer(this);
        spines_left_front2.setRotationPoint(4.0F, 4.0F, -4.0F);
        body_large.addChild(spines_left_front2);
        setRotation(spines_left_front2, 0.0F, 0.7854F, 0.0F);
        this.spines_left_front2.setTextureOffset(0, 16).addBox(0.0F, -8.0F, 0.0F, 1, 8, 1, 0.0F);

        spines_left_mid = new ModelRenderer(this);
        spines_left_mid.setRotationPoint(4.0F, 4.0F, 0.0F);
        body_large.addChild(spines_left_mid);
        this.spines_left_mid.setTextureOffset(4, 16).addBox(0.0F, -8.0F, 0.0F, 1, 8, 1, 0.0F);

        spines_left_back2 = new ModelRenderer(this);
        spines_left_back2.setRotationPoint(4.0F, 4.0F, 4.0F);
        body_large.addChild(spines_left_back2);
        setRotation(spines_left_back2, 0.0F, -0.7854F, 0.0F);
        this.spines_left_back2.setTextureOffset(8, 16).addBox(0.0F, -8.0F, 0.0F, 1, 8, 1, 0.0F);

        spines_right_front2 = new ModelRenderer(this);
        spines_right_front2.setRotationPoint(-4.0F, 4.0F, -4.0F);
        body_large.addChild(spines_right_front2);
        setRotation(spines_right_front2, 0.0F, -0.7854F, 0.0F);
        this.spines_right_front2.setTextureOffset(4, 16).addBox(-1.0F, -8.0F, 0.0F, 1, 8, 1, 0.0F);

        spines_right_mid = new ModelRenderer(this);
        spines_right_mid.setRotationPoint(-4.0F, 4.0F, 0.0F);
        body_large.addChild(spines_right_mid);
        this.spines_right_mid.setTextureOffset(8, 16).addBox(-1.0F, -8.0F, 0.0F, 1, 8, 1, 0.0F);

        spines_right_back2 = new ModelRenderer(this);
        spines_right_back2.setRotationPoint(-4.0F, 4.0F, 4.0F);
        body_large.addChild(spines_right_back2);
        setRotation(spines_right_back2, 0.0F, 0.7854F, 0.0F);
        this.spines_right_back2.setTextureOffset(8, 16).addBox(-1.0F, -8.0F, 0.0F, 1, 8, 1, 0.0F);

        bone_mid = new ModelRenderer(this);
        bone_mid.setRotationPoint(0.0F, 0.0F, 0.0F);


        body_mid = new ModelRenderer(this);
        body_mid.setRotationPoint(0.0F, -2.5F, 0.0F);
        bone_mid.addChild(body_mid);
        this.body_mid.setTextureOffset(12, 22).addBox(-2.5F, -3.5F, -2.5F, 5, 5, 5, 0.0F);

        leftFin3 = new ModelRenderer(this);
        leftFin3.setRotationPoint(2.5F, -2.5F, -1.5F);
        body_mid.addChild(leftFin3);
        this.leftFin3.setTextureOffset(24, 3).addBox(0.0F, 0.0F, 0.0F, 2, 1, 2, 0.0F);

        rightFin3 = new ModelRenderer(this);
        rightFin3.setRotationPoint(-2.5F, -2.5F, -1.5F);
        body_mid.addChild(rightFin3);
        this.rightFin3.setTextureOffset(24, 0).addBox(-2.0F, 0.0F, 0.0F, 2, 1, 2, 0.0F);

        spines_top_front = new ModelRenderer(this);
        spines_top_front.setRotationPoint(0.0F, -3.5F, -2.5F);
        body_mid.addChild(spines_top_front);
        this.spines_top_front.setTextureOffset(19, 17).addBox(-2.5F, -1.0F, 0.0F, 5, 1, 0, 0.0F);

        spines_top_back = new ModelRenderer(this);
        spines_top_back.setRotationPoint(0.0F, -3.5F, 2.5F);
        body_mid.addChild(spines_top_back);
        this.spines_top_back.setTextureOffset(11, 17).addBox(-2.5F, -1.0F, 0.0F, 5, 1, 0, 0.0F);

        spines_bottom_front = new ModelRenderer(this);
        spines_bottom_front.setRotationPoint(0.0F, 1.5F, -2.5F);
        body_mid.addChild(spines_bottom_front);
        this.spines_bottom_front.setTextureOffset(18, 20).addBox(-2.5F, 0.0F, 0.0F, 5, 1, 0, 0.0F);

        spines_bottom_back = new ModelRenderer(this);
        spines_bottom_back.setRotationPoint(0.0F, 1.5F, 2.5F);
        body_mid.addChild(spines_bottom_back);
        setRotation(spines_bottom_back, 0.7854F, 0.0F, 0.0F);
        this.spines_bottom_back.setTextureOffset(18, 20).addBox(-2.5F, 0.0F, 0.0F, 5, 1, 0, 0.0F);

        spines_left_front = new ModelRenderer(this);
        spines_left_front.setRotationPoint(2.5F, 2.5F, -2.5F);
        body_mid.addChild(spines_left_front);
        setRotation(spines_left_front, 0.0F, 0.7854F, 0.0F);
        this.spines_left_front.setTextureOffset(1, 17).addBox(0.0F, -6.0F, 0.0F, 1, 5, 0, 0.0F);

        spines_left_back = new ModelRenderer(this);
        spines_left_back.setRotationPoint(2.5F, 2.5F, 2.5F);
        body_mid.addChild(spines_left_back);
        setRotation(spines_left_back, 0.0F, -0.7854F, 0.0F);
        this.spines_left_back.setTextureOffset(1, 17).addBox(0.0F, -6.0F, 0.0F, 1, 5, 0, 0.0F);

        spines_right_front = new ModelRenderer(this);
        spines_right_front.setRotationPoint(-2.5F, 2.5F, -2.5F);
        body_mid.addChild(spines_right_front);
        setRotation(spines_right_front, 0.0F, -0.7854F, 0.0F);
        this.spines_right_front.setTextureOffset(5, 17).addBox(-1.0F, -6.0F, 0.0F, 1, 5, 0, 0.0F);

        spines_right_back = new ModelRenderer(this);
        spines_right_back.setRotationPoint(-2.5F, 2.5F, 2.5F);
        body_mid.addChild(spines_right_back);
        setRotation(spines_right_back, 0.0F, 0.7854F, 0.0F);
        this.spines_right_back.setTextureOffset(9, 17).addBox(-1.0F, -6.0F, 0.0F, 1, 5, 0, 0.0F);

        bone_small = new ModelRenderer(this);
        bone_small.setRotationPoint(0.0F, 0.0F, 0.0F);


        body_small = new ModelRenderer(this);
        body_small.setRotationPoint(0.0F, -1.0F, 0.0F);
        bone_small.addChild(body_small);
        this.body_small.setTextureOffset(0, 27).addBox(-1.5F, -1.0F, -1.5F, 3, 2, 3, 0.0F);
        this.body_small.setTextureOffset(24, 6).addBox(0.5F, -2.0F, -1.5F, 1, 1, 1, 0.0F);
        this.body_small.setTextureOffset(28, 6).addBox(-1.5F, -2.0F, -1.5F, 1, 1, 1, 0.0F);

        tailfin = new ModelRenderer(this);
        tailfin.setRotationPoint(0.0F, -0.001F, 1.5F);
        body_small.addChild(tailfin);
        this.tailfin.setTextureOffset(-3, 0).addBox(-1.5F, 0.001F, 0.0F, 3, 0, 3, 0.0F);

        leftFin = new ModelRenderer(this);
        leftFin.setRotationPoint(1.5F, 0.0F, -1.5F);
        body_small.addChild(leftFin);
        this.leftFin.setTextureOffset(25, 0).addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);

        rightFin = new ModelRenderer(this);
        rightFin.setRotationPoint(-1.5F, 0.0F, -1.5F);
        body_small.addChild(rightFin);
        this.rightFin.setTextureOffset(25, 0).addBox(-1.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(Entity entity, float ticks, float playerDistance, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(ticks, playerDistance, f2, f3, f4, f5, entity);

        if (playerDistance > 0)
        {
            if (playerDistance < 3)
            {
                bone_large.render(f5);
            }
            else if (playerDistance < 4)
            {
                bone_mid.render(f5);
            }
            else
            {
                bone_small.render(f5);
            }
        }
        else
        {
            bone_large.render(f5);
        }
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
            body_large.setRotationPoint(8.0F, -4.0F, 0.0F);
            body_mid.setRotationPoint(8.0F, -2.5F, 0.0F);
            body_small.setRotationPoint(8.0F, -1.0F, 0.0F);

            this.bone_large.rotateAngleY = (float) Math.toRadians(ticks*4);
            this.bone_mid.rotateAngleY = (float) Math.toRadians(ticks*4);
            this.bone_small.rotateAngleY = (float) Math.toRadians(ticks*4);

            this.body_large.rotateAngleY = MathHelper.sin(ticks * speed) * (float)Math.PI/8;
            this.body_mid.rotateAngleY = this.body_large.rotateAngleY;
            this.body_small.rotateAngleY = this.body_large.rotateAngleY;

            this.rightFin2.rotateAngleZ = MathHelper.cos(ticks * speed) * (float)Math.PI/8;
            this.leftFin2.rotateAngleZ = -this.rightFin2.rotateAngleZ;

            this.rightFin3.rotateAngleZ = this.rightFin2.rotateAngleZ;
            this.leftFin3.rotateAngleZ = -this.rightFin2.rotateAngleZ;

            this.rightFin.rotateAngleZ = this.rightFin2.rotateAngleZ;
            this.leftFin.rotateAngleZ = -this.rightFin2.rotateAngleZ;

            this.tailfin.rotateAngleX = MathHelper.sin(ticks * speed) * (float)Math.PI/8;
        }
        else
        {
            bone_large.setRotationPoint(0, 0, 0);
            this.bone_large.rotateAngleY = (f3 / (180F / (float)Math.PI));
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