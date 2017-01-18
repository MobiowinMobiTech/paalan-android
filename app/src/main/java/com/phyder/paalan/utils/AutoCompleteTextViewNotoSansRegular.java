package com.phyder.paalan.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

/**
 * This is custom EditText class with NotoSansRegular font
 */
public class AutoCompleteTextViewNotoSansRegular extends AutoCompleteTextView {
    private Typeface typeface;

    public AutoCompleteTextViewNotoSansRegular(Context context) {
        super(context);
    }

    public AutoCompleteTextViewNotoSansRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public AutoCompleteTextViewNotoSansRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        setCustomFont(ctx);
    }

    // Here setting the NotoSansRegular font to EditText label
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
