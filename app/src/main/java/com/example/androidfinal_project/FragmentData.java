package com.example.androidfinal_project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentData extends Fragment {

    private EditText editWeight, editHeight;
    private Button btnCalculate;
    private TextView textResult;
    private ImageView bmiImageView;

    public FragmentData() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_data, container, false);

        editWeight = rootView.findViewById(R.id.edit_weight);
        editHeight = rootView.findViewById(R.id.edit_height);
        btnCalculate = rootView.findViewById(R.id.btn_calculate);
        textResult = rootView.findViewById(R.id.text_result);
        bmiImageView = rootView.findViewById(R.id.BMI); // ImageView 초기화

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });

        return rootView;
    }

    private void calculateBMI() {
        String weightStr = editWeight.getText().toString();
        String heightStr = editHeight.getText().toString();

        if (TextUtils.isEmpty(weightStr) || TextUtils.isEmpty(heightStr)) {
            textResult.setText("체중과 키를 모두 입력하세요.");
            return;
        }

        float weight = Float.parseFloat(weightStr);
        float height = Float.parseFloat(heightStr) / 100; // cm를 m로 변환

        float bmi = weight / (height * height);

        String bmiResult;
        int imageResId; // 이미지 리소스 ID 변수

        if (bmi < 18.5) {
            bmiResult = "BMI: " + String.format("%.1f", bmi) + "\n밥을 더 먹어야 할 거 같네요!!";
            imageResId = R.drawable.underweight; // underweight 이미지
        } else if (bmi >= 18.5 && bmi < 25) {
            bmiResult = "BMI: " + String.format("%.1f", bmi) + "\n정상 체중이에요!!";
            imageResId = R.drawable.normal; // normal 이미지
        } else if (bmi >= 25 && bmi < 30) {
            bmiResult = "BMI: " + String.format("%.1f", bmi) + "\n웨이트 트레이닝과 유산소가 필요해요!";
            imageResId = R.drawable.overweight; // overweight 이미지
        } else {
            bmiResult = "BMI: " + String.format("%.1f", bmi) + "\n살을 빼야 할 거 같아요!";
            imageResId = R.drawable.obese; // obese 이미지
        }

        textResult.setText(bmiResult);
        bmiImageView.setImageResource(imageResId); // ImageView에 이미지 설정
    }
}