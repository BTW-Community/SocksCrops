// Made with Blockbench 4.5.0
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package net.minecraft.src;

public class SCModelCustomBowl extends ModelBase {
	private final ModelRenderer fish;
	private final ModelRenderer chicken;
	private final ModelRenderer mushroom;
	private final ModelRenderer stew;
	private final ModelRenderer bowl;
	private final ModelRenderer cube_r1;

	public SCModelCustomBowl() {
		textureWidth = 32;
		textureHeight = 32;

		fish = new ModelRenderer(this);
		fish.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.fish.setTextureOffset(12, 26).addBox(-3.0F, -3.0F, -3.0F, 6, 1, 6, 0.0F);

		chicken = new ModelRenderer(this);
		chicken.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.chicken.setTextureOffset(6, 26).addBox(-3.0F, -3.0F, -3.0F, 6, 1, 6, 0.0F);

		mushroom = new ModelRenderer(this);
		mushroom.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.mushroom.setTextureOffset(0, 26).addBox(-3.0F, -3.0F, -3.0F, 6, 1, 6, 0.0F);

		stew = new ModelRenderer(this);
		stew.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.stew.setTextureOffset(-6, 26).addBox(-3.0F, -3.0F, -3.0F, 6, 1, 6, 0.0F);

		bowl = new ModelRenderer(this);
		bowl.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bowl.setTextureOffset(0, 11).addBox(-3.0F, -1.0F, -3.0F, 6, 1, 6, 0.0F);
		this.bowl.setTextureOffset(18, 2).addBox(3.0F, -4.0F, -3.0F, 1, 3, 6, 0.0F);
		this.bowl.setTextureOffset(18, 2).addBox(-4.0F, -4.0F, -3.0F, 1, 3, 6, 0.0F);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, -2.5F, 0.0F);
		bowl.addChild(cube_r1);
		setRotation(cube_r1, 0.0F, -1.5708F, 0.0F);
		this.cube_r1.setTextureOffset(0, 0).addBox(-4.0F, -1.5F, -4.0F, 1, 3, 8, 0.0F);
		this.cube_r1.setTextureOffset(0, 0).addBox(3.0F, -1.5F, -4.0F, 1, 3, 8, 0.0F);
	}

	/**
	* Sets the models various rotation angles then renders the model.
	*/
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		bowl.render(f5);
		
		if ( f == Item.bowlSoup.itemID)
		{
			mushroom.render(f5);
		}
		
		if (f == FCBetterThanWolves.fcItemFishSoup.itemID)
		{
			fish.render(f5);
		}
		
		if (f == FCBetterThanWolves.fcItemChickenSoup.itemID)
		{
			chicken.render(f5);
		}
		
		if (f == FCBetterThanWolves.fcItemHeartyStew.itemID)
		{
			stew.render(f5);
		}
	}

	/**
	* Sets the model's various rotation angles. For bipeds, f and f1 are used for animating the movement of arms
	* and legs, where f represents the time(so that arms and legs swing back and forth) and f1 represents how
	* "far" arms and legs can swing at most.
	*/
	@Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.bowl.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.mushroom.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.fish.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.chicken.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.stew.rotateAngleY = f3 / (180F / (float)Math.PI);
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