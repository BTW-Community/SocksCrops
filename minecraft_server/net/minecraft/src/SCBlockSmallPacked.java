package net.minecraft.src;

import java.util.Iterator;
import java.util.Random;

public class SCBlockSmallPacked extends SCBlockBambooPacked {

	public static final int REEDS = 0;
	public static final int SHAFTS = 1;
	
	protected SCBlockSmallPacked(int id, Material material, String[] topTextures, String[] sideTextures) {
		super(id, material, topTextures, sideTextures);
		setStepSound(soundGrassFootstep);
	}
}
