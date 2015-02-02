package com.scheidt.slots.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.scheidt.slots.GdxSlots;
import com.scheidt.slots.sound.LRUCache.CacheEntryRemovedListener;

/**
 * A service that manages the sound effects.
 */
public class SoundManager implements CacheEntryRemovedListener<SlotSound, Sound>, Disposable {

	/**
	 * The volume to be set on the sound.
	 */
	private float volume = 1f;

	/**
	 * Whether the sound is enabled.
	 */
	private boolean enabled = true;

	/**
	 * The sound cache.
	 */
	private final LRUCache<SlotSound, Sound> soundCache;

	/**
	 * Creates the sound manager.
	 */
	public SoundManager() {
		soundCache = new LRUCache<SlotSound, Sound>(10);
		soundCache.setEntryRemovedListener(this);

		Gdx.app.log("SoundManager", "Preload all sounds");
		for (SlotSound sound : SlotSound.values()) {
			FileHandle soundFile = Gdx.files.internal(sound.getFileName());
			Sound soundToPlay = Gdx.audio.newSound(soundFile);
			soundCache.add(sound, soundToPlay);
		}
	}

	/**
	 * Plays the specified sound.
	 */
	public void play(SlotSound sound) {
		// check if the sound is enabled
		if (!enabled)
			return;

		// try and get the sound from the cache
		Sound soundToPlay = soundCache.get(sound);
		if (soundToPlay == null) {
			FileHandle soundFile = Gdx.files.internal(sound.getFileName());
			soundToPlay = Gdx.audio.newSound(soundFile);
			soundCache.add(sound, soundToPlay);
		}

		// play the sound
		try {
			soundToPlay.play(volume);
		} catch (Exception e) {
			System.out.println("wtf?");
		}
	}

	/**
	 * Sets the sound volume which must be inside the range [0,1].
	 */
	public void setVolume(float volume) {
		Gdx.app.log(GdxSlots.LOG, "Adjusting sound volume to: " + volume);

		// check and set the new volume
		if (volume < 0 || volume > 1f) {
			throw new IllegalArgumentException("The volume must be inside the range: [0,1]");
		}
		this.volume = volume;
	}

	/**
	 * Enables or disabled the sound.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	// EntryRemovedListener implementation

	@Override
	public void notifyEntryRemoved(SlotSound key, Sound value) {
		Gdx.app.log(GdxSlots.LOG, "Disposing sound: " + key.name());
		value.dispose();
	}

	/**
	 * Disposes the sound manager.
	 */
	public void dispose() {
		Gdx.app.log(GdxSlots.LOG, "Disposing sound manager");
		for (Sound sound : soundCache.retrieveAll()) {
			sound.stop();
			sound.dispose();
		}
	}

}