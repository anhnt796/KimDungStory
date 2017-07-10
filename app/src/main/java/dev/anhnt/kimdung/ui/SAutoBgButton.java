package dev.anhnt.kimdung.ui;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.Button;

public class SAutoBgButton extends Button {

    protected class SAutoBgButtonBackgroundDrawable extends LayerDrawable {
        protected final float[] SCROLL_CANCEL;
        protected final float[] SCROLL_DOWN;
        protected int _disabledAlpha;
        protected int _fullAlpha;
        protected ColorFilter _pressedFilter;

        public SAutoBgButtonBackgroundDrawable(Drawable d) {
            super(new Drawable[]{d});
            this.SCROLL_DOWN = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 100.0f, 0.0f, 1.0f, 0.0f, 0.0f, 100.0f, 0.0f, 0.0f, 1.0f, 0.0f, 100.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
            this.SCROLL_CANCEL = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
            this._pressedFilter = new ColorMatrixColorFilter(this.SCROLL_DOWN);
            this._disabledAlpha = 100;
            this._fullAlpha = 255;
        }

        protected boolean onStateChange(int[] states) {
            boolean enabled = false;
            boolean pressed = false;
            for (int state : states) {
                if (state == android.R.attr.state_enabled) {
                    enabled = true;
                } else if (state == android.R.attr.state_pressed) {
                    pressed = true;
                }
            }
            mutate();
            if (enabled && pressed) {
                setColorFilter(this._pressedFilter);
            } else if (enabled) {
                setColorFilter(null);
                setAlpha(this._fullAlpha);
            } else {
                setColorFilter(null);
                setAlpha(this._disabledAlpha);
            }
            invalidateSelf();
            return super.onStateChange(states);
        }

        public boolean isStateful() {
            return true;
        }
    }

    public SAutoBgButton(Context context) {
        super(context);
    }

    public SAutoBgButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SAutoBgButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setBackgroundDrawable(Drawable d) {
        super.setBackgroundDrawable(new SAutoBgButtonBackgroundDrawable(d));
    }
}