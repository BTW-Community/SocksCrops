// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package net.minecraft.src;

public class SCModelCustomApple extends ModelBase {
	private final ModelRenderer appleRed;
	private final ModelRenderer appleRedSlice;
	private final ModelRenderer appleGold;
	private final ModelRenderer appleGoldSlice;

	public SCModelCustomApple() {
		textureWidth = 32;
		textureHeight = 32;

		appleRed = new ModelRenderer(this);
		appleRed.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.appleRed.setTextureOffset(0, 0).addBox(-2.5F, -5.0F, -2.5F, 5, 5, 5, 0.0F);
		this.appleRed.setTextureOffset(22, 0).addBox(-2.5F, -10.0F, 0.0F, 5, 5, 0, 0.0F);

		appleRedSlice = new ModelRenderer(this);
		appleRedSlice.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.appleRedSlice.setTextureOffset(16, 24).addBox(-2.5F, -5.0F, -0.5F, 5, 5, 3, 0.0F);
		this.appleRedSlice.setTextureOffset(22, 0).addBox(-2.5F, -10.0F, 0.0F, 5, 5, 0, 0.0F);

		appleGold = new ModelRenderer(this);
		appleGold.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.appleGold.setTextureOffset(0, 10).addBox(-2.5F, -5.0F, -2.5F, 5, 5, 5, 0.0F);
		this.appleGold.setTextureOffset(22, 10).addBox(-2.5F, -10.0F, 0.0F, 5, 5, 0, 0.0F);

		appleGoldSlice = new ModelRenderer(this);
		appleGoldSlice.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.appleGoldSlice.setTextureOffset(16, 24).addBox(-2.5F, -5.0F, -0.5F, 5, 5, 3, 0.0F);
		this.appleGoldSlice.setTextureOffset(22, 10).addBox(-2.5F, -10.0F, 0.0F, 5, 5, 0, 0.0F);
	}

	/**
	* Sets the models various rotation angles then renders the model.
	*/
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		if (f == Item.appleRed.itemID)
		{
			appleRed.render(f5);
		}
		else if (f == Item.appleGold.itemID)
		{
			appleGold.render(f5);
		}
	}

	/**
	* Sets the model's various rotation angles. For bipeds, f and f1 are used for animating the movement of arms
	* and legs, where f represents the time(so that arms and legs swing back and forth) and f1 represents how
	* "far" arms and legs can swing at most.
	*/
	@Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.appleRed.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.appleRedSlice.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.appleGold.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.appleGoldSlice.rotateAngleY = f3 / (180F / (float)Math.PI);
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