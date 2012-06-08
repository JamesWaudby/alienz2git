package com.max.Alienz2;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Ship extends Rectangle {

	private static final long serialVersionUID = 1L;
	private Texture image;
	private Sprite sprite;
	private float dir;
	private float fireDir;
	
	private BitmapFont font;
	private float speed;
	private float decreaseSpeed;
	
	private float health;
	private float adrenalin;
	
	Rectangle healthBar;
	Rectangle adrenalinBar;
	
	Array<Bullet> bullets = new Array<Bullet>();
	private long lastFire;
	private long fireTime;
	
	
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
		healthBar = new Rectangle(580,30,(200 * (this.health/100)), 12);
		
		this.adrenalin = 0;
		adrenalinBar = new Rectangle(580, 60, (200 * (this.adrenalin/100)), 12);
		
		this.fireTime = 100000000;
		
		this.setSprite(new Sprite(image));
		
		this.sprite.setX(x);
		this.sprite.setY(y);
		this.sprite.setOrigin(48, 24);
	}
	
	public void update(OrthographicCamera camera) 
	{
		
		x += speed * MathUtils.cosDeg(dir);
		y += speed * MathUtils.sinDeg(dir);
		
		if (x < 0) x = 0;
		if (x > 800 - 48) x = 800 -48;
		if (y < 0 ) y = 0;
		if (y > 480 - 48 ) y = 480 - 48;
		
		this.sprite.setX(x);
		this.sprite.setY(y);
		
		healthBar.width = 200 * (this.health/100);
		adrenalinBar.width = 200 * (this.adrenalin/100);
		
		//this.sprite.rotate(this.rotation);
		
		Iterator<Bullet> iter = bullets.iterator();
		
		
		while (iter.hasNext()) {
			Bullet bullet = iter.next();
			
			bullet.update();
			
			if (bullet.y < 0  || bullet.x < 0 || bullet.y > 480 || bullet.x > 800) {
				iter.remove();
			}
		}
		
	}
	
	public void render(SpriteBatch batch) {
		
		//this.sprite.rotate(this.rotation);
		this.sprite.draw(batch);
		//batch.draw(this.getSprite(), x, y);
		font.draw(batch,  "Dir: " + dir, x - 32, y - 32);
		font.draw(batch, "Health:" + health , x - 32, y - 64);
		font.draw(batch, "Rotation:" + this.sprite.getRotation() , x - 32, y - 96);
		
		font.draw(batch, "Bullets:" + (this.bullets.size), 24,64);
		
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
	
	public float getFireDir() {
		return fireDir;
	}

	public void setFireDir(float fireDir) {
		this.fireDir = fireDir;
	}
	
	public void fire() {
		Bullet bullet = new Bullet((this.x + this.sprite.getOriginX()), (this.y + this.sprite.getOriginY()), this.fireDir);		
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
	
	public void drawBars(ShapeRenderer shapes, OrthographicCamera camera ) {
		shapes.setProjectionMatrix(camera.combined);
		shapes.begin(ShapeType.FilledRectangle);
		shapes.setColor(0,255,255,0);
		shapes.filledRect(healthBar.x, healthBar.y, healthBar.width, healthBar.height);
		
		shapes.setColor(0,255,255,0);
		shapes.filledRect(adrenalinBar.x, adrenalinBar.y, adrenalinBar.width, adrenalinBar.height);
		shapes.end();
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public void setRotation(float angle) {
		this.sprite.setRotation(angle);
	}

	public long getLastFire() {
		return lastFire;
	}

	public void setLastFire(long lastFire) {
		this.lastFire = lastFire;
	}

	public long getFireTime() {
		return fireTime;
	}

	public void setFireTime(long fireTime) {
		this.fireTime = fireTime;
	}
	
	
}
