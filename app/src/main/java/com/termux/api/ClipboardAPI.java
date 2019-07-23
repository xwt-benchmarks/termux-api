package com.termux.api;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.termux.api.util.ResultReturner;

import org.json.JSONObject;

import java.io.PrintWriter;

public class ClipboardAPI {

    static void onReceive(final Context context, final JSONObject opts) {
        final ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipData clipData = clipboard.getPrimaryClip();
        final String newClipText = opts.optString("text");
        boolean set = opts.optBoolean("set", false);

        if (set) {
            // Set clip.
            clipboard.setPrimaryClip(ClipData.newPlainText("", newClipText));
            ResultReturner.returnData(context, out -> out.print(""));
        } else {
            ResultReturner.returnData(context, out -> {
                if (clipData == null) {
                    out.print("");
                } else {
                    int itemCount = clipData.getItemCount();
                    for (int i = 0; i < itemCount; i++) {
                        Item item = clipData.getItemAt(i);
                        CharSequence text = item.coerceToText(context);
                        if (!TextUtils.isEmpty(text)) {
                            out.println(text);
                        }
                    }
                }
            });
        }
    }

}
