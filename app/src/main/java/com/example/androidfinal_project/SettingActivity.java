package com.example.androidfinal_project;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingActivity extends Activity {
    private EditText timerEditText;
    private Button saveButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        // UI 요소 초기화
        timerEditText = findViewById(R.id.timerEditText);
        saveButton = findViewById(R.id.saveButton);
        sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);

        // 기존에 저장된 타이머 시간 불러오기
        int timerDuration = sharedPreferences.getInt("timerDuration", 30); // 기본값 30초
        timerEditText.setText(String.valueOf(timerDuration));

        // 저장 버튼 클릭 리스너
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자가 입력한 타이머 시간 저장
                int newTimerDuration = Integer.parseInt(timerEditText.getText().toString());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("timerDuration", newTimerDuration);
                editor.apply();

                // 설정 화면 종료
                finish();
            }
        });
    }
}