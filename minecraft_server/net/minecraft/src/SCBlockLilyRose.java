package net.minecraft.src;

public class SCBlockLilyRose extends FCBlockLilyPad {

	public static String[] types = {"pink","white"};
	
	public static final int PINK = 0;
	public static final int WHITE = 1;
	
	protected SCBlockLilyRose(int iBlockID) {
		super(iBlockID);
		setHardness(0.0F);
		setStepSound(soundGrassFootstep);
		setUnlocalizedName("SCBlockLilyRose");
	}

	private boolean secondRenderpass = false;
	
	private Icon[] rose = new Icon[16];
	private Icon[] flower = new Icon[16];
	private Icon lily;
	private Icon roots;
	private Icon[] icon = new Icon[16];


}