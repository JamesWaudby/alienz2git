package com.max.Alienz2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Bullet extends Rectangle {

	private Texture image;
	private float dir;
	private float speed;
	
	public Bullet(float x, float y, float dir, float speed) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.speed = speed;
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
	
	public void update() {
		x += speed * MathUtils.cosDeg(dir);
		y += speed * MathUtils.sinDeg(dir);
	}
}
