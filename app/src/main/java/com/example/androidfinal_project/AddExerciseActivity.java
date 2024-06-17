package com.example.androidfinal_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TabHost;

public class AddExerciseActivity extends AppCompatActivity {

    private Integer[][] posterIDs = {
            {R.drawable.chest1, R.drawable.chest2, R.drawable.chest3, R.drawable.chest4, R.drawable.chest5, R.drawable.chest6, R.drawable.chest7, R.drawable.chest8, R.drawable.chest9, R.drawable.chest10},
            {R.drawable.sh1, R.drawable.sh2, R.drawable.sh3, R.drawable.sh4, R.drawable.sh5},
            {R.drawable.bk1, R.drawable.bk2, R.drawable.bk3, R.drawable.bk4, R.drawable.bk5, R.drawable.bk6, R.drawable.bk7, R.drawable.bk8, R.drawable.bk9, R.drawable.bk10},
            {R.drawable.le1, R.drawable.le2, R.drawable.le3, R.drawable.le4, R.drawable.le5}
    };

    private String[][] posterTitles = {
            {"벤치프레스", "인클라인 벤치프레스", "케이블 크로스오버(하단)", "케이블 크로스오버(중앙)", "체스트 프레스 머신", ".인클라인 프레스 머신", "덤벨 풀오버", "딥스", "인클라인 덤벨프레스", "푸쉬업"},
            {"밀리터리 프레스", "덤벨 숄더프레스", "사이드레터럴레이즈", "덤벨 프론트레이즈", "벤트오버레터럴레이즈"},
            {"하이로우", "시티드로우", "롱풀", "덤벨풀오버", "데드리프트", "바벨로우", "티바로우", "풀업", "기립근운동", "덤벨로우"},
            {"레그프레스", "스탭업", "워킹런지", "루마니안 데드리프트", "스쿼트"}
    };

    private EditText exerciseNameEditText;
    private EditText repsEditText;
    private EditText weightEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        // 탭 설정
        setupTabs();

        // EditText와 Button 초기화
        exerciseNameEditText = findViewById(R.id.exerciseNameEditText);
        repsEditText = findViewById(R.id.repsEditText);
        weightEditText = findViewById(R.id.weightEditText);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExercise();
            }
        });

        // 각 탭에 GridView를 설정
        GridView gridView1 = findViewById(R.id.gridView1);
        GridView gridView2 = findViewById(R.id.gridView2);
        GridView gridView3 = findViewById(R.id.gridView3);
        GridView gridView4 = findViewById(R.id.gridView4);

        gridView1.setAdapter(new MyGridAdapter(this, posterIDs[0], posterTitles[0]));
        gridView2.setAdapter(new MyGridAdapter(this, posterIDs[1], posterTitles[1]));
        gridView3.setAdapter(new MyGridAdapter(this, posterIDs[2], posterTitles[2]));
        gridView4.setAdapter(new MyGridAdapter(this, posterIDs[3], posterTitles[3]));
    }

    private void setupTabs() {
        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();

        // 첫 번째 탭: 가슴 운동
        TabHost.TabSpec tab1 = tabHost.newTabSpec("Tab1");
        tab1.setIndicator("가슴 운동");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        // 두 번째 탭: 어깨 운동
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Tab2");
        tab2.setIndicator("어깨 운동");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        // 세 번째 탭: 하체 운동
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Tab3");
        tab3.setIndicator("등 운동");
        tab3.setContent(R.id.tab3);
        tabHost.addTab(tab3);

        // 네 번째 탭: 등 운동
        TabHost.TabSpec tab4 = tabHost.newTabSpec("Tab4");
        tab4.setIndicator("하체 운동");
        tab4.setContent(R.id.tab4);
        tabHost.addTab(tab4);
    }

    private void saveExercise() {
        String exerciseName = exerciseNameEditText.getText().toString();
        String repsString = repsEditText.getText().toString();
        String weightString = weightEditText.getText().toString();

        if (exerciseName.isEmpty() || repsString.isEmpty() || weightString.isEmpty()) {
            // Handle empty fields if necessary
            return;
        }

        int reps = Integer.parseInt(repsString);
        int weight = Integer.parseInt(weightString);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("exerciseName", exerciseName);
        resultIntent.putExtra("reps", reps);
        resultIntent.putExtra("weight", weight);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public class MyGridAdapter extends BaseAdapter {
        Context context;
        Integer[] posters;
        String[] titles;

        public MyGridAdapter(Context c, Integer[] posters, String[] titles) {
            context = c;
            this.posters = posters;
            this.titles = titles;
        }

        @Override
        public int getCount() {
            return posters.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(200, 300));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setPadding(5, 5, 5, 5);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(posters[position]);

            final int pos = position;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View dialogView = View.inflate(AddExerciseActivity.this, R.layout.item, null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(AddExerciseActivity.this);
                    ImageView ivPoster = dialogView.findViewById(R.id.ivPoster);
                    ivPoster.setImageResource(posters[pos]);
                    dlg.setTitle(titles[pos]);
                    dlg.setIcon(R.drawable.dumbel);
                    dlg.setView(dialogView);
                    dlg.setNegativeButton("닫기", null);
                    dlg.show();
                }
            });

            return imageView;
        }
    }
}