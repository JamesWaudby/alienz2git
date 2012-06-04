package com.max.Alienz2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen implements Screen {
	
	Alienz game;
	private BitmapFont font;
	private OrthographicCamera camera;
	SpriteBatch batch;
	private float alpha;
	private boolean increase;
	
	// constructor to keep a reference to the main Game class
    public MenuScreen(Alienz game)
    {
            this.game = game;
            create();
    }
    
    private void create()
    {
    	camera = new OrthographicCamera();
		camera.setToOrtho(true, 800, 480);
    	
    	font = new BitmapFont(Gdx.files.internal("font32.fnt"),
    	Gdx.files.internal("font32.png"), true);
    	
		batch = new SpriteBatch();
		alpha = 0f;
		increase = true;
		
    }

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		font.setColor(0f, 0f, 0f, 1f);
		font.setScale(2);
		font.draw(batch,  "Alienz 2", 300, 180);
		font.setScale(1);
		font.draw(batch, "By Max and James", 290, 230);
		font.setColor(0f, 0f, 0f, alpha);
		font.draw(batch, "Touch the screen to start", 255, 275);
		
		batch.end();
		
		if (increase == true)
		{
			if (alpha < 1f) alpha += 0.05f;
			else 
			{
				alpha = 1f;
				increase = false;
			}
		}
		else
		{
			if (alpha > 0f) alpha -= 0.05f;
			else 
			{
				alpha = 0f;
				increase = true;
			}
		}
		
		if (Gdx.input.justTouched())
             game.setScreen(game.gameScreen);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		
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
		// TODO Auto-generated method stub
		font.dispose();
		batch.dispose();
	}

}
