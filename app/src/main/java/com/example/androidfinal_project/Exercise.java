package com.example.androidfinal_project;

public class Exercise {
    private String name;
    private int weight;
    private int reps;
    private int count;

    public Exercise(String name, int weight, int reps) {
        this.name = name;
        this.weight = weight;
        this.reps = reps;
        this.count = 0; //초기 count값은 0
    }

      public Exercise(String name, int weight, int reps,int count) {
        this.name = name;
        this.weight = weight;
        this.reps = reps;
        this.count = count; //초기 count값은 0
    }

    public String getName() {
        return this.name;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getReps() {
        return this.reps;
    }

    public int getCount() {
        return this.count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public void incrementCount() {
        count++;
    }
    public String getWorkInfo() {
        return this.name + " - "  + "운동"+ "중량" + this.weight + "kg - " + this.reps + "세트 ";
    }
}
