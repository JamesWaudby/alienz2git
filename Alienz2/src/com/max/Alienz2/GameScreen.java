package com.max.Alienz2;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {

	Alienz game;
	private BitmapFont font;
	private OrthographicCamera camera;
	SpriteBatch batch;
	ShapeRenderer shapes;
	
	FPSLogger log;
	
	Ship ship;
	Enemy test;
	
	OnScreenJoystick joyStick, joyStick2;
	InputMultiplexer multiplexerTest;
	MyInputProcessor inputProcessor, inputProcessor2;
	
	// Controls the actions from the joysticks
	Controller controller;
	
	// Contains information about waves
	Array<Wave> waves;
	
	private long lastSpawn;
	private long spawnTime;
	
	
	// constructor to keep a reference to the main Game class
    public GameScreen(Alienz game)
    {
            this.game = game;
            create();
    }
    
    private void create()
    {
    	log = new FPSLogger();
    	
    	camera = new OrthographicCamera();
		camera.setToOrtho(true, 800, 480);
    	
    	font = new BitmapFont(Gdx.files.internal("font32.fnt"),
    	Gdx.files.internal("font32.png"), true);
    
    	ship = new Ship(352, 400);
    		
    	joyStick2 = new OnScreenJoystick(700,370, 48, 32);
    	joyStick = new OnScreenJoystick(100,370, 48, 32);
    	
    	this.controller = new Controller(ship, joyStick, joyStick2);
    	
    	MyInputProcessor inputProcessor = new MyInputProcessor(ship, joyStick,joyStick2, camera, controller);
    	Gdx.input.setInputProcessor(inputProcessor);
    	
		batch = new SpriteBatch();
		shapes = new ShapeRenderer();
		
		waves = new Array<Wave>();
		this.spawnTime = 3000;
    }

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		log.log();
		
		
		// Do all the updating...
		checkSpawn();
		camera.update();
		controller.update();
		ship.update(camera);
		
		checkShip();

		// Draw the enemy's health bars and update
		for(Wave wave: waves) {
			wave.update(shapes, camera);
			//wave.drawHealthBars(shapes, camera);
		}
		
		// Do all the drawing
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
				
		// Render the waves of enemies
		for(Wave wave: waves) wave.render(batch);
		
		ship.render(batch);
		
		joyStick.render(batch);
		joyStick2.render(batch);
		
		// Draw the UI elements
		drawUI();
		
		batch.end();
		
		
		checkCollisions();
		
		// Draw the player's health bars
		ship.drawBars(shapes, camera);
		
	}
	
	// Draw all of the text elements to screen
	public void drawUI() {
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.setScale(1);
		font.draw(batch,  "Score: ", 24, 32);
		
		font.draw(batch, "Left:" + controller.isLeftJoystickDown(), 24, 96);
		font.draw(batch,  "Right: " + controller.isRightJoystickDown(), 24, 128);
		
		font.draw(batch,  "Waves: " + waves.size, 24, 156);
	}
	
	public void checkShip() {
		if (joyStick.getActive()) ship.setSpeed((float)joyStick.getDistance() / 8);
		else ship.slowSpeed();
	}
	
	public void checkSpawn() {
		// Spawn new waves
		if(TimeUtils.millis() - this.lastSpawn > this.spawnTime) {
			waves.add(new Wave(3, MathUtils.random(0, 3), ship));
			this.lastSpawn = TimeUtils.millis();
		}	
	}
	
	public void checkCollisions() {
		
		Iterator<Bullet> iter = ship.bullets.iterator();
		
		// Iterate through the bullets
		while(iter.hasNext()) {
			Bullet bullet = iter.next();
			
			if (bullet.y < 0  || bullet.x < 0 || bullet.y > 480 || bullet.x > 800) {
				iter.remove();
			}
			
			// Iterate through the waves
			Iterator<Wave> waveIter = waves.iterator();
			
			while(waveIter.hasNext()) {
				Wave wave = waveIter.next();
	
				// Iterate through the enemies of the waves
				Iterator<Enemy> enemyIter = wave.enemies.iterator();
				
				while(enemyIter.hasNext()) {
					Enemy enemy = enemyIter.next();					
					Rectangle enemyRect = enemy.getSprite().getBoundingRectangle();
					Rectangle shipRect = ship.getSprite().getBoundingRectangle();
					
					if(enemy.x > 0 && enemy.x < 800 && enemy.y > 0 && enemy.y < 480) {
						// Check if the bullet has hit an enemy
						if(enemyRect.overlaps(bullet)) {
							
							// Delete the bullet on collide
							iter.remove();
							
							// Reduce enemy health
							enemy.setHealth(enemy.getHealth()-10);
							
							if(enemy.getHealth() == 0) {
								
								enemyIter.remove();
								ship.setAdrenalin(ship.getAdrenalin() + 5);
							}
							
						}
						
						
						if(shipRect.overlaps(enemyRect)) {
							ship.setHealth(ship.getHealth() - 5);
						}
					}
				}

			}
			
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		font.dispose();
		batch.dispose();
		ship.dispose();
		joyStick.dispose();
	}

}
