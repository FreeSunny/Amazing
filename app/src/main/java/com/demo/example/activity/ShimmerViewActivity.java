package com.demo.example.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.demo.example.R;
import com.demo.example.util.AppActive;

public class ShimmerViewActivity extends AppCompatActivity {

	TextView tvWrap, tvMatch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shimmer_view);
		findViews();
		load();
	}

	private void load() {
		AppActive.kickoff(this, "aa", 10 * 1000, 10 * 1000);
	}

	private void findViews() {
		tvWrap = findViewById(R.id.wrap_test);
		tvMatch = findViewById(R.id.match_test);
	}
}
