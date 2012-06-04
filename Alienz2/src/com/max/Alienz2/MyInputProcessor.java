package com.max.Alienz2;

import com.badlogic.gdx.InputProcessor;

public class MyInputProcessor implements InputProcessor {
	Ship ship;
	OnScreenJoystick joyStick;
	
	public MyInputProcessor(Ship ship, OnScreenJoystick joyStick)
	{
		this.ship = ship;
		this.joyStick = joyStick;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		joyStick.resetKnob();
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		joyStick.updateKnobPosition(x, y);
		ship.setDir(joyStick.angle());
		return true;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
