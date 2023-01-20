// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package net.minecraft.src;

public class SCModelCustomDung extends ModelBase {
	private final ModelRenderer gold;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer dung;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;

	public SCModelCustomDung() {
		textureWidth = 32;
		textureHeight = 32;

		gold = new ModelRenderer(this);
		gold.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.gold.setTextureOffset(1, 17).addBox(-2.5F, -2.0F, -1.5F, 2, 2, 4, 0.0F);
		this.gold.setTextureOffset(5, 27).addBox(-0.5F, -2.0F, 0.5F, 3, 2, 2, 0.0F);
		this.gold.setTextureOffset(21, 26).addBox(0.5F, -2.0F, -2.5F, 2, 2, 3, 0.0F);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(-1.5F, -3.0F, -0.5F);
		gold.addChild(cube_r1);
		setRotation(cube_r1, 0.0F, 0.0F, 0.3927F);
		this.cube_r1.setTextureOffset(1, 23).addBox(0.0F, 0.0F, -2.0F, 3, 2, 2, 0.0F);
		this.cube_r1.setTextureOffset(17, 21).addBox(0.0F, -1.0F, 0.0F, 2, 3, 2, 0.0F);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-2.5F, -2.0F, -1.5F);
		gold.addChild(cube_r2);
		setRotation(cube_r2, 0.5236F, 0.0F, 0.0F);
		this.cube_r2.setTextureOffset(21, 17).addBox(0.0F, 0.0F, -3.0F, 2, 2, 3, 0.0F);

		dung = new ModelRenderer(this);
		dung.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.dung.setTextureOffset(1, 1).addBox(-2.5F, -2.0F, -1.5F, 2, 2, 4, 0.0F);
		this.dung.setTextureOffset(5, 11).addBox(-0.5F, -2.0F, 0.5F, 3, 2, 2, 0.0F);
		this.dung.setTextureOffset(21, 10).addBox(0.5F, -2.0F, -2.5F, 2, 2, 3, 0.0F);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(-1.5F, -3.0F, -0.5F);
		dung.addChild(cube_r3);
		setRotation(cube_r3, 0.0F, 0.0F, 0.3927F);
		this.cube_r3.setTextureOffset(1, 7).addBox(0.0F, 0.0F, -2.0F, 3, 2, 2, 0.0F);
		this.cube_r3.setTextureOffset(17, 5).addBox(0.0F, -1.0F, 0.0F, 2, 3, 2, 0.0F);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(-2.5F, -2.0F, -1.5F);
		dung.addChild(cube_r4);
		setRotation(cube_r4, 0.5236F, 0.0F, 0.0F);
		this.cube_r4.setTextureOffset(21, 1).addBox(0.0F, 0.0F, -3.0F, 2, 2, 3, 0.0F);
	}

	/**
	* Sets the models various rotation angles then renders the model.
	*/
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		if ( f == FCBetterThanWolves.fcItemDung.itemID)
		{
			dung.render(f5);
		}
		
		if (f == FCBetterThanWolves.fcItemGoldenDung.itemID)
		{
			gold.render(f5);
		}
	}

	/**
	* Sets the model's various rotation angles. For bipeds, f and f1 are used for animating the movement of arms
	* and legs, where f represents the time(so that arms and legs swing back and forth) and f1 represents how
	* "far" arms and legs can swing at most.
	*/
	@Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.dung.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.gold.rotateAngleY = f3 / (180F / (float)Math.PI);
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