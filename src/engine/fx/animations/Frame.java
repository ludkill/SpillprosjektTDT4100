package engine.fx.animations;

import engine.fx.Image;

public class Frame {

    private Image frame;
    private double duration;

    public Frame(Image frame, double duration) {
        this.frame = frame;
        this.duration = duration;
    }

    public Image getFrame() {
        return frame;
    }

    public void setFrame(Image frame) {
        this.frame = frame;
    }

	public double getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
