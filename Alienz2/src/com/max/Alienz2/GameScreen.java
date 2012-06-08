package com.max.Alienz2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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
	
	Controller controller;
	
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
		
		
    }

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		log.log();
		
		// Do all the updating...
		camera.update();
		controller.update();
		ship.update(camera);
		
		if (joyStick.getActive()) ship.setSpeed((float)joyStick.getDistance() / 8);
		else ship.slowSpeed();
		
		
		// Do all the drawing
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.setScale(1);
		font.draw(batch,  "Score: ", 24, 32);
		
		font.draw(batch, "Left:" + controller.isLeftJoystickDown(), 24, 96);
		font.draw(batch,  "Right: " + controller.isRightJoystickDown(), 24, 128);
		
		ship.render(batch);
		//test.render(batch);
		
		joyStick.render(batch);
		joyStick2.render(batch);
		
		batch.end();
		
		ship.drawBars(shapes, camera);
		
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
