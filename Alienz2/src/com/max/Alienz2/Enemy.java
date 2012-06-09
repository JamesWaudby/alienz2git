package com.max.Alienz2;

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
import com.badlogic.gdx.math.Vector3;

public class Enemy extends Rectangle {
	
	private Texture image;
	private Sprite sprite;
	private int type, time, dirPath;
	
	private float desX, desY, difX, difY, desLine;
	private float vel, velX, velY;
	private boolean atDes;
	
	private float health;
	Rectangle healthBar;
	
	private BitmapFont font;
	
	public Enemy(int type, Vector3 startPos, Vector3 endPos) {
		
		
		// What type of enemy is it?
		this.type = type;
		
		switch(type) {
			case 0:
				time = MathUtils.random(30,90);
				this.health = 10;
				
				// Sort sprite and image
				this.image  = new Texture(Gdx.files.internal("enemy.png"));
				
				break;
			case 1:
				time = MathUtils.random(30,90);
				this.health = 20;
				
				// Sort sprite and image
				this.image  = new Texture(Gdx.files.internal("enemy1.png"));
				
				break;
			case 2:
				time = MathUtils.random(30,90);
				this.health = 30;
				
				// Sort sprite and image
				this.image  = new Texture(Gdx.files.internal("enemy2.png"));
				
				break;
		}
		
		this.sprite = new Sprite(this.image);
		
		// Enemy start position
		this.x = startPos.x;
		this.y = startPos.y;
		
		// The destination of the enemy
		this.desX = endPos.x;
		this.desY = endPos.y;
		this.atDes = false;
		
		// Calculate the path of the enemy
		this.difX = desX - x;
		this.difY = desY - y;
		
		// Straight line distance
		this.desLine = (float) Math.sqrt((this.difX * this.difX) + (this.difY * this.difY));
		
		// Get the velocity needed
		this.vel = 3;
		this.velX = (this.vel * this.difX) / this.desLine;
		this.velY = (this.vel * this.difY) / this.desLine;
		
		this.sprite.setX(x);
		this.sprite.setY(y);
		
		this.healthBar = new Rectangle(this.x + 10,this.y - 10,(35 * (this.health/100)), 5);
		
		font = new BitmapFont(Gdx.files.internal("font32.fnt"),
		    	Gdx.files.internal("font32.png"), true);
		
	}

	public Texture getImage() {
		return image;
	}

	public void setImage(Texture image) {
		this.image = image;
	}
	
	public void render(SpriteBatch batch) {
		this.sprite.draw(batch);
		
		font.draw(batch, "HP: " + this.health, this.x-32, this.y-32);
	}
	
	public void update(ShapeRenderer shapes, OrthographicCamera camera) {
		
		this.healthBar.width = 35 * (this.health/100);
		drawBars(shapes, camera);
		
		if(this.atDes == false) {
			if(this.time > 0) {
				this.x += this.velX;
				this.y += this.velY;
				--this.time;
			}
			else {
				this.time = 0;
				this.atDes = true;
			}
		}
		else {
			
		}
		
		this.sprite.setX(this.x);
		this.sprite.setY(this.y);
		
		
	}

	public void movePath() {
		switch(this.dirPath) {
			case 0:
				break;
		}
	}
	
	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}
	
	public void drawBars(ShapeRenderer shapes, OrthographicCamera camera) {
		shapes.setProjectionMatrix(camera.combined);
		shapes.begin(ShapeType.FilledRectangle);
		shapes.setColor(0,0,0,0);
		shapes.filledRect(healthBar.x, healthBar.y, healthBar.width, healthBar.height);	
		shapes.end();
	}

	
	/**
	 * @return Returns the sprite of an Enemy
	 */
	public Sprite getSprite() {
		return sprite;
	}

	
	/**
	 * @param sprite the sprite to set
	 */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}


}
