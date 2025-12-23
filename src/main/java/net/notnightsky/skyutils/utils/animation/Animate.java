package net.notnightsky.skyutils.utils.animation;

public class Animate {
    private float value;
    private float min;
    private float max;
    private float speed;
    private float time;
    private float target; // Added: target value to animate towards
    private boolean animatingToMax; // Added: direction of animation
    private Easing ease;

    public Animate() {
        this.ease = Easing.LINEAR;
        this.value = 0;
        this.min = 0;
        this.max = 1;
        this.speed = 50;
        this.time = 0;
        this.target = min;
        this.animatingToMax = false;
    }

    public void reset() {
        time = min;
        value = min;
        target = min;
    }

    public Animate update() {
        // Determine which direction to animate
        if (animatingToMax && time < max) {
            time += (Delta.getDeltaTime() * 0.001F * speed);
        } else if (!animatingToMax && time > min) {
            time -= (Delta.getDeltaTime() * 0.001F * speed);
        }

        time = clamp(time, min, max);
        float easeVal = ease.ease(time, min, max, max);
        this.value = Math.min(easeVal, max);
        return this;
    }

    // NEW: Set the animation target
    public Animate setTarget(float target) {
        this.target = clamp(target, min, max);
        this.animatingToMax = (target > time);
        return this;
    }

    // NEW: Animate to max value
    public Animate toMax() {
        return setTarget(max);
    }

    // NEW: Animate to min value
    public Animate toMin() {
        return setTarget(min);
    }

    public Animate setValue(float value) {
        this.value = clamp(value, min, max);
        this.time = clamp(value, min, max); // Also update time for consistency
        return this;
    }

    public Animate setMin(float min) {
        this.min = min;
        this.time = clamp(time, min, max);
        this.value = clamp(value, min, max);
        return this;
    }

    public Animate setMax(float max) {
        this.max = max;
        this.time = clamp(time, min, max);
        this.value = clamp(value, min, max);
        return this;
    }

    public Animate setSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public Animate setEase(Easing ease) {
        this.ease = ease;
        return this;
    }

    // REMOVED: setReversed() - we don't need this anymore

    public float getValueInt() { return (int) value; } // Remove static modifier
    public float getValue() { return value; }
    public float getMin() { return min; }
    public float getMax() { return max; }
    public float getSpeed() { return speed; }
    public Easing getEase() { return ease; }
    public float getTarget() { return target; } // Added
    public boolean isAnimating() { return Math.abs(time - target) > 0.01f; } // Added

    private float clamp(float num, float min, float max) {
        return num < min ? min : (Math.min(num, max));
    }
}