package com.phyder.paalan.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * This is custom Button class with NotoSansRegular font
 */
public class ButtonNotoSansRegular extends Button {
    private Typeface typeface;

    public ButtonNotoSansRegular(Context context) {
        super(context);
    }

    public ButtonNotoSansRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public ButtonNotoSansRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        setCustomFont(ctx);
    }

    // Here setting the NotoSansRegular font to button label
    private boolean setCustomFont(Context ctx) {
        try {
            if (typeface == null) {
                typeface = Typeface.createFromAsset(ctx.getAssets(), "fonts/NotoSans-Regular.ttf");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        setTypeface(typeface);
        return true;
    }
}
