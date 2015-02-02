package com.scheidt.slots.sound;


public enum SlotSound {
	
//	CLICK("sound/click.mp3"),
	PULL("sound/pull.mp3"),
	STOP("sound/row_stop.mp3"),
	HAPPY("sound/mario.mp3"),
	RINGING("sound/ring_loop.mp3"),
	SUSPENSE("sound/suspense.mp3"),
	EVIL_LAUGH("sound/evil_laugh.mp3");


	private final String fileName;

	private SlotSound(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

}
