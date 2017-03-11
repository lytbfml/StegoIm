package com.stego.lytbf.stegoim;

/**
 * Created by Yangxiao Wang on 3/9/2017.
 */

import android.content.Context;

interface IStegoThreadHandler {
	Context getContext();
	void onJobCreated(StegoJob stegoJob);
	void onJobDone(StegoJob stegoJob);
}