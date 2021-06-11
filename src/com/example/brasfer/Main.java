package com.example.brasfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class Main extends Activity {
	LinearLayout linear;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		linear = new LinearLayout(this);
		setContentView(linear);
		Intent ourintent = new Intent(this, move.class);
		startActivity(ourintent);
	}


}
