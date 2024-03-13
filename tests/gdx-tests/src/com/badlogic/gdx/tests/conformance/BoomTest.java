package com.badlogic.gdx.tests.conformance;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.tests.utils.GdxTest;

public class BoomTest extends GdxTest {
    private Sound   sound;
    private Sound   soundStereo;
    private boolean toggle;


    @Override
    public void create() {
        sound = Gdx.audio.newSound(Gdx.files.getFileHandle("data/boom.ogg", Files.FileType.Internal));
        soundStereo = Gdx.audio.newSound(Gdx.files.getFileHandle("data/boom-stereo.ogg", Files.FileType.Internal));
    }


    @Override
    public void render() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (toggle) {
                System.out.println("mono");
                sound.play();
            } else {
                System.out.println("stereo");
                soundStereo.play();
            }
            toggle = !toggle;
        }
    }

}
