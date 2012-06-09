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
import com.badlogic.gdx.utils.TimeUtils;

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
	
	private long powerupTime;
	private long powerupStartTime;
	private boolean poweredUp;
	
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
		
		// Rate of fire
		this.fireTime = 250;
		
		// How long the adrenalin lasts
		this.powerupTime = 5000;
		this.poweredUp = false;
		
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
		
		// Check for full adrenalin
		if(this.adrenalin == 100 && this.poweredUp == false) {
			this.poweredUp = true;
			this.powerupStartTime = TimeUtils.millis();
		}
		
		// Increase fire rate
		if(this.poweredUp) {
			if(TimeUtils.millis() - this.powerupStartTime < this.powerupTime) {
				this.fireTime = 10;
			}
			else {
				this.fireTime = 250;
				this.poweredUp = false;
				this.adrenalin = 0;
			}
		}
		
		Iterator<Bullet> iter = bullets.iterator();
		
		while (iter.hasNext()) {
			Bullet bullet = iter.next();
			bullet.update();
		}
		
	}
	
	public void render(SpriteBatch batch) {
		
		this.sprite.draw(batch);
		
		font.draw(batch, "X: " + this.x, x - 32, y - 32);
		font.draw(batch, "Y: " + this.y, x - 32, y - 64);
		font.draw(batch,  "Ad: " + this.adrenalin, this.x-32, this.y - 96);
		
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

	/**
	 * @return the poweredUp
	 */
	public boolean isPoweredUp() {
		return poweredUp;
	}

	/**
	 * @param poweredUp the poweredUp to set
	 */
	public void setPoweredUp(boolean poweredUp) {
		this.poweredUp = poweredUp;
	}

	/**
	 * @return the powerupTime
	 */
	public long getPowerupTime() {
		return powerupTime;
	}

	/**
	 * @param powerupTime the powerupTime to set
	 */
	public void setPowerupTime(long powerupTime) {
		this.powerupTime = powerupTime;
	}
	
	
	
}
