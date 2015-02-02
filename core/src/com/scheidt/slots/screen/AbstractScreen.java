package com.scheidt.slots.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.scheidt.slots.GdxSlots;

/**
 * The base class for all game screens.
 */
public abstract class AbstractScreen implements Screen {
	// the fixed viewport dimensions (ratio: 1.77778 seems to be common)
	public static final int GAME_VIEWPORT_WIDTH = 1280, GAME_VIEWPORT_HEIGHT = 800;

	protected final GdxSlots game;
	protected final Stage stage;

	private BitmapFont font;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private Skin skin;
	private TextureAtlas atlas;
	private Table table;

	public AbstractScreen(GdxSlots game) {
		this.game = game;
		// int width = ( isGameScreen() ? GAME_VIEWPORT_WIDTH :
		// MENU_VIEWPORT_WIDTH );
		// int height = ( isGameScreen() ? GAME_VIEWPORT_HEIGHT :
		// MENU_VIEWPORT_HEIGHT );
//		this.stage = new Stage(new ScreenViewport());
		this.stage = new Stage(new FitViewport(GAME_VIEWPORT_WIDTH,GAME_VIEWPORT_HEIGHT));
	}

	protected boolean isGameScreen() {
		return false;
	}

	// Lazily loaded collaborators

	public BitmapFont getFont() {
		if (font == null) {
			font = new BitmapFont();
		}
		return font;
	}

	public SpriteBatch getBatch() {
		if (batch == null) {
			batch = new SpriteBatch();
		}
		return batch;
	}

	public ShapeRenderer getShapeRenderer() {
		if (shapeRenderer == null) {
			shapeRenderer = new ShapeRenderer();
		}
		return shapeRenderer;
	}

	public TextureAtlas getAtlas() {
		if (atlas == null) {
			atlas = new TextureAtlas(Gdx.files.internal("image-atlases/textures.pack"));
		}
		return atlas;
	}

	protected Skin getSkin() {
		if (skin == null) {
			skin = new Skin(Gdx.files.internal("image-atlases/uiskin.json"));
		}
		return skin;
	}

	protected Table getTable() {
		if (table == null) {
			table = new Table(getSkin());
			table.setFillParent(true);
			if (GdxSlots.DEV_MODE) {
				table.debug();
			}
			stage.addActor(table);
		}
		return table;
	}

	// Screen implementation

	@Override
	public void show() {
		// set the stage as the input processor
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		//Table.drawDebug(stage);
	}

	@Override
	public void hide() {


		// dispose the screen when leaving the screen;
		// note that the dipose() method is not called automatically by the
		// framework, so we must figure out when it's appropriate to call it
//		dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

		// the following call disposes the screen's stage, but on my computer it
		// crashes the game so I commented it out; more info can be found at:
		// http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=3624
		// stage.dispose();

		// as the collaborators are lazily loaded, they may be null
		if (font != null)
			font.dispose();
		if (batch != null)
			batch.dispose();
		if (skin != null)
			skin.dispose();
		if (atlas != null)
			atlas.dispose();
	}
}