package com.max.Alienz2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class OnScreenJoystick {
	
	private float origin_x;
	private float origin_y;
	private float knob_x;
	private float knob_y;
	private float joyStickRadius;
	private float knobRadius;
	private double circ_x;
	private double circ_y;
	Texture joyStickImage;
	Texture knobImage;
	private boolean active;
	private double distance;
	
	public OnScreenJoystick(float location_x, float location_y,
			float joyStickRadius, float knobRadius)
	{
		this.origin_x = location_x;
		this.origin_y = location_y;
		this.joyStickRadius = joyStickRadius;
		this.knob_x = location_x;
		this.knob_y = location_y;
		this.knobRadius = knobRadius;
		circ_x = 0;
		circ_y = 0;
		joyStickImage  = new Texture(Gdx.files.internal("hollowCircle.png"));
		knobImage  = new Texture(Gdx.files.internal("filledCircle.png"));
		active = false;
	}
	
	public void updateKnobPosition(float x, float y)
	{
		if ((x - origin_x) * (x - origin_x)
				+ (y - origin_y) * (y - origin_y)
				<= joyStickRadius * joyStickRadius)
		{
			knob_x = x;
			knob_y = y;
			distance = Math.sqrt((x-origin_x)*(x-origin_x) + (y-origin_y)*(y-origin_y));
			active = true;
		}
		else
		{
			if (active)
			{		
				circ_x = origin_x + (joyStickRadius ) * (( x - origin_x) / (Math.sqrt(Math.pow((x - origin_x), 2) + (Math.pow((y - origin_y),2)))));
				circ_y = origin_y + (joyStickRadius ) * (( y - origin_y) / (Math.sqrt(Math.pow((x - origin_x), 2) + (Math.pow((y - origin_y),2)))));
				distance = Math.sqrt((circ_x-origin_x)*(circ_x-origin_x) + (circ_y-origin_y)*(circ_y-origin_y));
				
				knob_x = ((float) circ_x);
				knob_y = (float) circ_y;
			}
		}
	}
	
	public void resetKnob() {
		active = false;
		knob_x = origin_x;
		knob_y = origin_y;
	}
	
	private float deltaX() {
		return knob_x - origin_x;
	}
	
	private float deltaY() {
		return knob_y - origin_y;
	}
	
	
	public float angle() {
		return (float) (MathUtils.atan2(deltaY(), deltaX()) * 180 / MathUtils.PI);
	}
	
	public float getDrawOriginX() {
		return origin_x - joyStickRadius;
	}
	
	public float getDrawOriginY() {
		return origin_y - joyStickRadius;
	}
	
	public float getDrawKnobX() {
		return knob_x - knobRadius;
	}
	
	public float getDrawKnobY() {
		return knob_y - knobRadius;
	}
	
	public boolean getActive() {
		return active;
	}
	
	public void dispose()
	{
		joyStickImage.dispose();
		knobImage.dispose();
	}
	
	public double getDistance()
	{
		return distance;
	}
	
	public void render(SpriteBatch batch)
	{
		batch.draw(joyStickImage, getDrawOriginX(), getDrawOriginY());
		batch.draw(knobImage, getDrawKnobX(), getDrawKnobY());
	}
	

}
