package gameObjects;

import java.awt.image.BufferedImage;
import graphics.Assets;

public enum Size {

	BIG(2,Assets.meds), MED(2,Assets.smalls), SMALL(2,Assets.tinies), TINY(0,null);
	public int quanity;
	
	public BufferedImage[] texture;

	private Size(int quanity, BufferedImage[] texture) {
		this.quanity = quanity;
		this.texture = texture;
	}
	
	
}
