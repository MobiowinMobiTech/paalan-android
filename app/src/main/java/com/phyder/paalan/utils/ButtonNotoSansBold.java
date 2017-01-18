package com.phyder.paalan.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * This is custom Button class with NotoSansBold font
 */
public class ButtonNotoSansBold extends Button {
    private Typeface typeface;

    public ButtonNotoSansBold(Context context) {
        super(context);
    }

    public ButtonNotoSansBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public ButtonNotoSansBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        setCustomFont(ctx);
    }

    // Here setting the NotoSansBold font to button label
    private boolean setCustomFont(Context ctx) {
        try {
            if (typeface == null) {
                typeface = Typeface.createFromAsset(ctx.getAssets(), "fonts/NotoSans-Bold.ttf");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        setTypeface(typeface);
        return true;
    }
}
