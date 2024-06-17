package com.example.androidfinal_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentHistory extends Fragment {

    private DatePicker datePicker;
    private ListView listViewExercise;
    private ExerciseAdapter exerciseAdapter;
    private List<Exercise> exerciseList;

    public FragmentHistory() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_history, container, false);

        datePicker = rootView.findViewById(R.id.Date);
        listViewExercise = rootView.findViewById(R.id.listViewExercise);

        // 현재 날짜로 DatePicker 초기화
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, month, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 선택된 날짜에 대한 처리
                handleSelectedDate(year, monthOfYear, dayOfMonth);
            }
        });

        exerciseList = new ArrayList<>();
        exerciseAdapter = new ExerciseAdapter(requireContext(), exerciseList);
        listViewExercise.setAdapter(exerciseAdapter);

        return rootView;
    }

    private void handleSelectedDate(int year, int month, int dayOfMonth) {
        String selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
        loadWorkOutData(selectedDate);
    }

    private void loadWorkOutData(String selectedDate) {
        exerciseList.clear(); // 기존 데이터 초기화

        try {
            FileInputStream fis = requireContext().openFileInput("workout_data.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            String line;
            boolean isDateFound = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains("Date: " + selectedDate)) {
                    isDateFound = true;

                    // 읽은 날짜 아래로 추가 3줄을 더 읽어서 exerciseList에 추가
                    while ((line = reader.readLine()) != null && !line.isEmpty()) {
                        if (line.startsWith("Exercise: ")) {
                            String exerciseName = line.replace("Exercise: ", "");
                            int reps = Integer.parseInt(reader.readLine().replace("Reps: ", ""));
                            int weight = Integer.parseInt(reader.readLine().replace("Weight: ", ""));
                            int count = Integer.parseInt(reader.readLine().replace("Count: ", ""));
                            Exercise exercise = new Exercise(exerciseName, reps, weight);
                            exerciseList.add(exercise);
                        }
                    }
                }
            }

            reader.close(); // 파일 읽기 종료

            if (isDateFound) {
                exerciseAdapter.notifyDataSetChanged(); // 리스트뷰 갱신
            } else {
                Toast.makeText(requireContext(), "이날은 운동을 하지 않았습니다.", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error: 파일을 읽을 수 없습니다.", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error: 데이터 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}