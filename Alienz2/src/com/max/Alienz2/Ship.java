package com.max.Alienz2;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Ship extends Rectangle {

	private static final long serialVersionUID = 1L;
	private Texture image;
	private float dir;
	private BitmapFont font;
	private float speed;
	private float decreaseSpeed;
	
	private float health;
	private float adrenalin;
	
	Rectangle healthBar;
	Rectangle adrenalinBar;
	
	Array<Bullet> bullets = new Array<Bullet>();
	
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
		decreaseSpeed = 0.25f;
		
		this.health = 100;
		healthBar = new Rectangle(580,450,(200 * (this.health/100)), 12);
		
		this.adrenalin = 0;
		adrenalinBar = new Rectangle(580, 435, (200 * (this.adrenalin/100)), 12);
	}
	
	public void update(OrthographicCamera camera) 
	{
		x += speed * MathUtils.cosDeg(dir);
		y += speed * MathUtils.sinDeg(dir);
		
		if (x < 0) x = 0;
		if (x > 800 - 48) x = 800 -48;
		if (y < 0 ) y = 0;
		if (y > 480 - 48 ) y = 480 - 48;
		
		
		healthBar.width = 200 * (this.health/100);
		adrenalinBar.width = 200 * (this.adrenalin/100);
		
		for(Bullet bullet: bullets) {
			bullet.update();
		}
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(image, x, y);
		font.draw(batch,  "Dir: " + dir, x - 32, y - 32);
		font.draw(batch, "Health:" + health , x - 32, y - 64);
		
		for(Bullet bullet: bullets) {
			bullet.render(batch);
		}
	}
	
	public void setSpeed(float speed){
		this.speed = speed;
	}
	
	public void slowSpeed()
	{
		if (speed > 0)
		{
			speed -= decreaseSpeed;
		}
		else
		{
			speed = 0;
		}
	}
	
	public void dispose() {
		image.dispose();
	}
	
	public void setDir(float newDir)
	{
		dir = newDir;
	}
	
	public void fire() {
		Bullet bullet = new Bullet(this.x, this.y, this.dir);		
		bullets.add(bullet);
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		if(this.health + health < 100 && this.health + health > 0) {
			this.health = health;
		}
		else if(this.health + health > 100) {
			this.health = 100;
		}
		else if(this.health + health < 0) {
			this.health = 0;
		}
	}
	
	public float getAdrenalin() {
		return adrenalin;
	}

	public void setAdrenalin(float adrenalin) {
		if(this.adrenalin + adrenalin < 100) {
			this.adrenalin = adrenalin;
		}
		else {
			this.adrenalin = 100;
		}
	}
	
	public void drawBars(ShapeRenderer shapes) {
		shapes.begin(ShapeType.FilledRectangle);
		shapes.setColor(0,255,255,0);
		shapes.filledRect(healthBar.x, healthBar.y, healthBar.width, healthBar.height);
		
		shapes.setColor(0,255,255,0);
		shapes.filledRect(adrenalinBar.x, adrenalinBar.y, adrenalinBar.width, adrenalinBar.height);
		shapes.end();
	}

}
