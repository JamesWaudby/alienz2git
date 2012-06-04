package com.max.Alienz2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Ship extends Rectangle {

	private static final long serialVersionUID = 1L;
	private Texture image;
	private float dir;
	private BitmapFont font;
	private double speed;
	
	public Ship (int x, int y)
	{
		this.x = x;
		this.y = y;
		width = 48;
		height = 48;
		image  = new Texture(Gdx.files.internal("ship.png"));
		font = new BitmapFont(Gdx.files.internal("font32.fnt"),
		    	Gdx.files.internal("font32.png"), true);
		dir = 0;
	}
	
	public void update(OrthographicCamera camera) 
	{
		x += speed * MathUtils.cosDeg(dir);
		y += speed * MathUtils.sinDeg(dir);
		
		if (x < 0) x = 0;
		if (x > 800 - 48) x = 800 -48;
		if (y < 0 ) y = 0;
		if (y > 480 - 48 ) y = 480 - 48;
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(image, x, y);
		font.draw(batch,  "Dir: " + dir, x - 32, y - 32);
		
	}
	
	public void setSpeed(double speed){
		this.speed = speed;
	}
	
	public void dispose() {
		image.dispose();
	}
	
	public void setDir(float newDir)
	{
		dir = newDir;
	}

}
