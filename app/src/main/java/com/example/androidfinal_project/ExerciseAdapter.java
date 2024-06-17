package com.example.androidfinal_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ExerciseAdapter extends ArrayAdapter<Exercise> {
    private int selectedPosition = -1;

    public ExerciseAdapter(Context context, List<Exercise> exercises) {
        super(context, 0, exercises);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // ViewHolder 패턴 사용하여 성능 향상
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_exercise, parent, false);

            // ViewHolder 객체 생성 및 뷰 바인딩
            viewHolder = new ViewHolder();
            viewHolder.nameTextView = convertView.findViewById(R.id.exerciseNameTextView);
            viewHolder.infoTextView = convertView.findViewById(R.id.infoTextView);
            viewHolder.countTextView = convertView.findViewById(R.id.countTextView);

            convertView.setTag(viewHolder);
        } else {
            // 재사용 가능한 convertView에서 ViewHolder 가져오기
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 현재 위치에 해당하는 Exercise 객체 가져오기
        Exercise exercise = getItem(position);

        // Exercise 객체가 null인지 체크
        if (exercise != null) {
            // TextView에 Exercise 객체의 데이터 설정
            viewHolder.nameTextView.setText(exercise.getName());
            viewHolder.infoTextView.setText(exercise.getWorkInfo());
            viewHolder.countTextView.setText("세트 수 : " + exercise.getCount());


            // 선택된 위치에 따라 배경색 변경
            if (position == selectedPosition) {
                convertView.setBackgroundColor(getContext().getResources().getColor(android.R.color.holo_green_dark));
            } else {
                convertView.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
            }
        }
        // count와 reps가 같아지면 '수행완료'됐다는 뜻으로 초록색으로 바꿈
        if (exercise.getReps() <= exercise.getCount()) {
            viewHolder.countTextView.setTextColor(getContext().getResources().getColor(android.R.color.holo_green_dark));
        } else {
            viewHolder.countTextView.setTextColor(getContext().getResources().getColor(android.R.color.holo_red_dark));
        }


        return convertView;
    }

    // ViewHolder 클래스 정의
    private static class ViewHolder {
        TextView nameTextView;
        TextView infoTextView;
        TextView countTextView;
    }

    // 선택된 위치 설정 메서드
    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }
}