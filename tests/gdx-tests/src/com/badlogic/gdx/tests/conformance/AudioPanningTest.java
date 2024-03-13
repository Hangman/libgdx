
package com.badlogic.gdx.tests.conformance;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.tests.utils.GdxTest;

/** This test checks the panning function of 4 different cases:
 * <ul>
 * <li>Sound mono</li>
 * <li>Music mono</li>
 * <li>Sound stereo</li>
 * <li>Music stereo</li>
 * </ul>
 *
 * @author Hangman */
public class AudioPanningTest extends GdxTest {
	private Sound soundMono;
	private Sound soundStereo;
	private Music musicMono;
	private Music musicStereo;
	private Sound currentSound;
	private Music currentMusic;
	private float panTime;
	private float playTime = Float.MAX_VALUE;
	private long soundID;
	private boolean soundPlaying;
	private boolean panDirection;

	@Override
	public void create () {
		soundMono = Gdx.audio.newSound(Gdx.files.getFileHandle("data/shotgun.ogg", Files.FileType.Internal));
		soundStereo = Gdx.audio.newSound(Gdx.files.getFileHandle("data/8.12.loop.wav", Files.FileType.Internal));
		musicMono = Gdx.audio.newMusic(Gdx.files.internal("data/shotgun.ogg"));
		musicStereo = Gdx.audio.newMusic(Gdx.files.internal("data/8.12.loop.wav"));
		currentSound = soundStereo;
		currentMusic = musicMono;
	}

	@Override
	public void render () {
		float delta = Gdx.graphics.getDeltaTime();
		panTime += delta;
		playTime += delta;
		while (panTime >= 1f) {
			panTime -= 1f;
			panDirection = !panDirection;
		}

		if (playTime > 5f) {
			playTime = 0f;
			currentMusic.stop();
			currentSound.stop(soundID);
			if (soundPlaying) {
				currentMusic = currentMusic == musicMono ? musicStereo : musicMono;
				currentMusic.setLooping(true);
				currentMusic.play();
				System.out.println("playing: Music " + (currentMusic == musicMono ? "mono" : "stereo"));
			} else {
				currentSound = currentSound == soundMono ? soundStereo : soundMono;
				soundID = currentSound.play();
				currentSound.setLooping(soundID, true);
				System.out.println("playing: Sound " + (currentSound == soundMono ? "mono" : "stereo"));
			}
			soundPlaying = !soundPlaying;
		}

		float pan = MathUtils.lerp(panDirection ? -1f : 1f, panDirection ? 1f : -1f, panTime);
		if (soundPlaying) {
			currentSound.setPan(soundID, pan, 1f);
		} else {
			currentMusic.setPan(pan, 1f);
		}
	}

	public void dispose () {
		currentSound.stop(soundID);
		currentMusic.stop();
		soundMono.dispose();
		soundStereo.dispose();
		musicMono.dispose();
		musicStereo.dispose();
	}

}
