package com.max.Alienz2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pointer {

	private int finger;
	private Texture image;
	int x;
	int y;
	private boolean active;
	
	public Pointer(int finger)
	{
		this.finger = finger;
		image  = new Texture(Gdx.files.internal("dot3.png"));
		active = false;
	}
	
	public void update()
	{
		if (Gdx.input.isTouched(0))
		{
			x = Gdx.input.getX(finger) - 1;
			y = Gdx.input.getY(finger) - 1;
			active = true;
		}
		else
		{
			x = 0;
			y = 0;
			active = false;
		}
	}
	
	public void render(SpriteBatch batch)
	{
		batch.draw(image, x, y);
	}
	
	public boolean getActive()
	{
		return active;
	}
	
	public void dispose() {
		image.dispose();
	}

}
