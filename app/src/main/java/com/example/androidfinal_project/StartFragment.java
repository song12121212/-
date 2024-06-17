package com.example.androidfinal_project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StartFragment extends Fragment {
    private Button addExerciseButton;
    private Button finishWorkoutButton;
    private Button settingButton;
    private TextView dateTextView;
    private TextView timerTextView; // 타이머를 보여줄 TextView 추가
    private ExerciseAdapter adapter;
    private ArrayList<Exercise> exerciseList;
    private ListView listView;
    private static final int ADD_EXERCISE_REQUEST_CODE = 1;
    private CountDownTimer countDownTimer; // 타이머 객체
    private int selectedItemPosition = -1; // 선택된 아이템 위치 저장

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        // UI 요소 초기화
        settingButton = view.findViewById(R.id.settingButton);
        dateTextView = view.findViewById(R.id.dateTextView);
        addExerciseButton = view.findViewById(R.id.addExerciseButton);
        finishWorkoutButton = view.findViewById(R.id.finishWorkoutButton);
        listView = view.findViewById(R.id.exerciseListView);
        timerTextView = view.findViewById(R.id.timerTextView); // TextView 초기화

        // 오늘 날짜 표시
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        dateTextView.setText(currentDate);

        // 운동 리스트 초기화
        exerciseList = new ArrayList<>();
        adapter = new ExerciseAdapter(requireContext(), exerciseList);
        listView.setAdapter(adapter);

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
        // 운동 추가 버튼 클릭 리스너
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), AddExerciseActivity.class);
                startActivityForResult(intent, ADD_EXERCISE_REQUEST_CODE);
            }
        });

        // 운동 종료 버튼 클릭 리스너
        finishWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWorkoutDataToFile();
                Toast.makeText(requireContext(), "오늘의 운동 기록을 저장했습니다", Toast.LENGTH_SHORT).show();
            }
        });

        // 리스트뷰 아이템 클릭 리스너
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // 클릭된 아이템 위치 저장
                selectedItemPosition = position;
                adapter.setSelectedPosition(position);
                view.setSelected(true);

                // 설정 값 불러오기
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
                int timerDuration = sharedPreferences.getInt("timerDuration", 30) * 1000; // 초 단위에서 밀리초로 변환
                // 클릭된 아이템의 타이머 시작 (예: 30초 타이머)
                countDownTimer = new CountDownTimer(timerDuration, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // 타이머가 감소하는 동안의 동작
                        timerTextView.setText(millisUntilFinished / 1000 + "초");
                    }

                    @Override
                    public void onFinish() {
                        // 타이머 종료 시 실행되는 동작
                        exerciseList.get(position).incrementCount();
                        adapter.notifyDataSetChanged(); // 리스트뷰 업데이트
                        timerTextView.setText("운동 시작!"); // 타이머 종료 메시지
                    }
                };

                countDownTimer.start(); // 타이머 시작
            }
        });

        return view;
    }

    // 운동 데이터를 파일에 저장하는 메서드
    private void saveWorkoutDataToFile() {
        String filename = "workout_data.txt";
        FileOutputStream fos = null;
        try {
            fos = requireContext().openFileOutput(filename, Context.MODE_APPEND);
            StringBuilder data = new StringBuilder();
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            for (Exercise exercise : exerciseList) {
                data.append("Date: ").append(currentDate).append("\n");
                data.append("Exercise: ").append(exercise.getName()).append("\n");
                data.append("Reps: ").append(exercise.getReps()).append("\n");
                data.append("Weight: ").append(exercise.getWeight()).append("\n");
                data.append("Count: ").append(exercise.getCount()).append("\n\n");
            }

            fos.write(data.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // AddExerciseActivity에서 결과를 받아 처리하는 메서드
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_EXERCISE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            String exerciseName = data.getStringExtra("exerciseName");
            int reps = data.getIntExtra("reps", 0);
            int weight = data.getIntExtra("weight", 0);

            Exercise exercise = new Exercise(exerciseName, reps, weight);
            exerciseList.add(exercise);
            adapter.notifyDataSetChanged();
        }
    }

    // Fragment가 소멸될 때 CountDownTimer를 종료
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}