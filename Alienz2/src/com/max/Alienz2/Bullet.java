package com.max.Alienz2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Bullet extends Rectangle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Texture image;
	private float dir;
	private float speed;
	
	public Bullet(float x, float y, float dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.speed = 8;
		this.image  = new Texture(Gdx.files.internal("dot3.png"));
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
	
	public void update() {
		x += speed * MathUtils.cosDeg(dir);
		y += speed * MathUtils.sinDeg(dir);
		
	}
}
