package com.scheidt.slots.font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.MathUtils;

public class FontManager {
	
	public static BitmapFont font42_3D;
	public static BitmapFont font36;
	public static BitmapFont font24;
	public static BitmapFont font_names;
	public static BitmapFont font_names_match;
	
	public static BitmapFont font_debug;


	public static void init() {

		FreeTypeFontGenerator generator3D = new FreeTypeFontGenerator(Gdx.files.internal("fonts/3Dumb.ttf"));
		FreeTypeFontGenerator generator2D = new FreeTypeFontGenerator(Gdx.files.internal("fonts/2Dumb.ttf"));
		FreeTypeFontGenerator generatorCourierNew = new FreeTypeFontGenerator(Gdx.files.internal("fonts/cour.ttf"));
		FreeTypeFontGenerator generatorComic = new FreeTypeFontGenerator(Gdx.files.internal("fonts/comic_zine_ot.otf"));
		
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		
		parameter.size = 42;
		font42_3D = generator3D.generateFont(parameter);
		
		parameter.size = 36;
		font36 = generator2D.generateFont(parameter);
		font36.setColor(1,1,1,1);
		
		
		parameter.size = 24;
		font24 = generator2D.generateFont(parameter); // font size 24 pixels
		
		
		parameter.size = 24;
		font24 = generator2D.generateFont(parameter); // font size 24 pixels
		font_debug = generatorCourierNew.generateFont(parameter); // font size 24 pixels
		font_debug.setColor(1,0,0,1); // RED
		
		
		parameter.size = 24;
		font_names = generator2D.generateFont(parameter);
		font_names.setColor(1f,(float)210/(float)255,(float)3/(float)255,1);
		
		parameter.size = 26;
		font_names_match = generator2D.generateFont(parameter);
		font_names_match.setColor(0.2f,1,0.2f,1);
		
		
		generator3D.dispose(); // don't forget to dispose to avoid memory leaks!
		generator2D.dispose();
		generatorCourierNew.dispose();
	}

	public static void dispose() {
		font42_3D.dispose();
		font36.dispose();
		font24.dispose();
		font_debug.dispose();
		font_names.dispose();
	}
	
	
}

