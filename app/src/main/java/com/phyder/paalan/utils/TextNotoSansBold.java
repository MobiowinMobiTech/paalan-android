package com.phyder.paalan.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * This is custom TextView class with NotoSansBold font
 */
public class TextNotoSansBold extends TextView {
    private Typeface typeface;

    public TextNotoSansBold(Context context) {
        super(context);
    }

    public TextNotoSansBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public TextNotoSansBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        setCustomFont(ctx);
    }

    // Here setting the NotoSansBold font to TextView label
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
