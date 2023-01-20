// Made with Blockbench 4.5.0
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package net.minecraft.src;

public class SCModelCustomBucket extends ModelBase
{
	private final ModelRenderer bucket;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer water;
	private final ModelRenderer milk;
	private final ModelRenderer chocolate;
	private final ModelRenderer cement;
	
	public SCModelCustomBucket() {
		textureWidth = 64;
		textureHeight = 32;

		bucket = new ModelRenderer(this);
		bucket.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bucket.setTextureOffset(0, 0).addBox(-3.5F, -7.0F, -3.5F, 7, 6, 7, 0.0F);
		this.bucket.setTextureOffset(0, 13).addBox(-3.0F, -1.0F, -3.0F, 6, 1, 6, 0.0F);
		this.bucket.setTextureOffset(21, 0).addBox(-4.0F, -8.0F, -4.0F, 7, 1, 1, 0.0F);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, -7.0F, 0.0F);
		bucket.addChild(cube_r1);
		setRotation(cube_r1, 0.0F, -1.5708F, 0.0F);
		this.cube_r1.setTextureOffset(21, 0).addBox(-4.0F, -1.0F, -4.0F, 7, 1, 1, 0.0F);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, -7.0F, 0.0F);
		bucket.addChild(cube_r2);
		setRotation(cube_r2, 0.0F, 3.1416F, 0.0F);
		this.cube_r2.setTextureOffset(21, 0).addBox(-4.0F, -1.0F, -4.0F, 7, 1, 1, 0.0F);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, -7.0F, 0.0F);
		bucket.addChild(cube_r3);
		setRotation(cube_r3, 0.0F, 1.5708F, 0.0F);
		this.cube_r3.setTextureOffset(21, 0).addBox(-4.0F, -1.0F, -4.0F, 7, 1, 1, 0.0F);

		water = new ModelRenderer(this);
		water.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.water.setTextureOffset(-6, 26).addBox(-3.0F, -7.5F, -3.0F, 6, 1, 6, 0.0F);

		milk = new ModelRenderer(this);
		milk.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.milk.setTextureOffset(0, 26).addBox(-3.0F, -7.5F, -3.0F, 6, 1, 6, 0.0F);

		chocolate = new ModelRenderer(this);
		chocolate.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.chocolate.setTextureOffset(6, 26).addBox(-3.0F, -7.5F, -3.0F, 6, 1, 6, 0.0F);

		cement = new ModelRenderer(this);
		cement.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.cement.setTextureOffset(12, 26).addBox(-3.0F, -7.5F, -3.0F, 6, 1, 6, 0.0F);
	}

	/**
	* Sets the models various rotation angles then renders the model.
	*/
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		bucket.render(f5);
		
		if ( f == Item.bucketWater.itemID)
		{
			water.render(f5);
		}
		
		if (f == Item.bucketMilk.itemID)
		{
			milk.render(f5);
		}
		
		if (f == FCBetterThanWolves.fcItemBucketMilkChocolate.itemID)
		{
			chocolate.render(f5);
		}
		
		if (f == FCBetterThanWolves.fcItemBucketCement.itemID)
		{
			cement.render(f5);
		}
	}

	/**
	* Sets the model's various rotation angles. For bipeds, f and f1 are used for animating the movement of arms
	* and legs, where f represents the time(so that arms and legs swing back and forth) and f1 represents how
	* "far" arms and legs can swing at most.
	*/
	@Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.bucket.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.water.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.milk.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.chocolate.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.cement.rotateAngleY = f3 / (180F / (float)Math.PI);
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