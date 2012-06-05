package com.max.Alienz2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends Rectangle {

	private Texture image;
	
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		this.image  = new Texture(Gdx.files.internal("ship.png"));
	}

	public Texture getImage() {
		return image;
	}

	public void setImage(Texture image) {
		this.image = image;
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(image, x, y);
	}
}