package com.example.quim.bykeapp.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quim.bykeapp.R;

@Deprecated
public class AddBikeSharingActivity extends AppCompatActivity {

    private Button mButton;
    private TextInputEditText mIdentifier;
    private TextInputEditText mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bike_sharing);

        mButton = findViewById(R.id.add_button);
        mIdentifier = findViewById(R.id.identifier_edit_text);
        mContent = findViewById(R.id.content_edit_text);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = mIdentifier.getText().toString().trim();
                String content = mContent.getText().toString().trim();
                if (id.isEmpty() || content.isEmpty()) {
                    if (id.isEmpty()) mIdentifier.setError("Empty");
                    if (content.isEmpty()) mContent.setError("Empty");
                } else {
                    Intent result = new Intent();
                    result.putExtra(getString(R.string.id), id);
                    result.putExtra(getString(R.string.content), content);
                    setResult(Activity.RESULT_OK, result);
                    finish();
                }
            }
        });
    }
}
