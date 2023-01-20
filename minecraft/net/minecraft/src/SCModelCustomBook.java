// Made with Blockbench 4.5.0
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class SCModelCustomBook extends ModelBase {
	private final ModelRenderer book;
	private final ModelRenderer manuscript;
	private final ModelRenderer quill;
	private final ModelRenderer feather_r1;

	public SCModelCustomBook() {
		textureWidth = 64;
		textureHeight = 32;

		book = new ModelRenderer(this);
		book.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.book.setTextureOffset(0, 0).addBox(-4.0F, -1.0F, -5.0F, 8, 1, 10, 0.0F);
		this.book.setTextureOffset(0, 14).addBox(-4.0F, -4.0F, -5.0F, 1, 3, 10, 0.0F);
		this.book.setTextureOffset(0, 0).addBox(-4.0F, -5.0F, -5.0F, 8, 1, 10, 0.0F);
		this.book.setTextureOffset(0, 2).addBox(-3.5F, -4.0F, -4.5F, 7, 3, 9, 0.0F);

		manuscript = new ModelRenderer(this);
		manuscript.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.manuscript.setTextureOffset(26, 0).addBox(-4.0F, -5.0F, -1.0F, 8, 5, 2, 0.25F);
		this.manuscript.setTextureOffset(0, 0).addBox(0.75F, -5.5F, -1.0F, 2, 1, 2, 0.25F);

		quill = new ModelRenderer(this);
		quill.setRotationPoint(0.0F, 0.0F, 0.0F);
		

		feather_r1 = new ModelRenderer(this);
		feather_r1.setRotationPoint(6.0F, 0.0F, 0.5F);
		quill.addChild(feather_r1);
		setRotation(feather_r1, 0.0F, 0.0F, -0.3491F);
		this.feather_r1.setTextureOffset(22, 7).addBox(0.0F, -13.0F, -3.5F, 0, 13, 7, 0.0F);
	}

	/**
	* Sets the models various rotation angles then renders the model.
	*/
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		book.render(f5);
		
		if ( f == Item.enchantedBook.itemID)
		{
			manuscript.render(f5);
		}
		
		if (f == Item.writableBook.itemID)
		{
			quill.render(f5);
		}
	}

	/**
	* Sets the model's various rotation angles. For bipeds, f and f1 are used for animating the movement of arms
	* and legs, where f represents the time(so that arms and legs swing back and forth) and f1 represents how
	* "far" arms and legs can swing at most.
	*/
	@Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.book.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.manuscript.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.quill.rotateAngleY = f3 / (180F / (float)Math.PI);
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