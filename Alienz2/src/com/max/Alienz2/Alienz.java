package com.max.Alienz2;

import com.badlogic.gdx.Game;

public class Alienz extends Game {
	  MenuScreen menuScreen;
      GameScreen gameScreen;
	
	@Override
	public void create() 
	{		
		menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        setScreen(menuScreen);         
	}

	@Override
	public void dispose() 
	{
		menuScreen.dispose();
		gameScreen.dispose();
	}

}
