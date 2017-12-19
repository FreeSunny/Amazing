package com.demo.example.util;

import android.content.Context;
import android.os.Handler;

public final class AppActive {
	private static final String TAG = "AppActive";

	private static final String SP_PREFIX = "AppActive-";

	private static final String KEY = "Last";

	public static final boolean kickoff(final Context context, final String tag, final long interval, long timeout) {
		long last = getLast(context, tag);

		long now = updateLast(context, tag);

		final Handler handler = new Handler();

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				updateLast(context, tag);

				handler.postDelayed(this, interval);
			}
		}, interval);

		// first
		if (last == 0) {
			return false;
		}

		// UTC mod ? or timeout really
		long elapse = now - last;
		if (elapse < timeout) {
			return false;
		}

		LogUtil.d(TAG, tag + " elapse " + elapse);

		return true;
	}

	private static long getLast(Context context, String tag) {
		long last = context.getSharedPreferences(SP_PREFIX + tag, Context.MODE_PRIVATE).getLong(KEY, 0);

		LogUtil.d(TAG, tag + " last " + last);

		return last;
	}

	private static long updateLast(Context context, String tag) {
		long now = System.currentTimeMillis();

		LogUtil.d(TAG, tag + " now " + now);

		context.getSharedPreferences(SP_PREFIX + tag, Context.MODE_PRIVATE).edit().putLong(KEY, now).commit();

		return now;
	}
}