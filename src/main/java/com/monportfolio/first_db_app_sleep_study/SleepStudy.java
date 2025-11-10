package com.monportfolio.first_db_app_sleep_study;

import jakarta.persistence.*; // On utilise @Entity, @Id, @GeneratedValue
import lombok.*; // On utilise @Data et @NoArgsConstructor
import com.opencsv.bean.CsvBindByName;

@Entity // Cet classe est une table de BDD
@Data // Annotation pour eviter utiliser setters and getters
@NoArgsConstructor // Annotattion pour eviter utiliser constructeur sans arguments
public class SleepStudy
{
    @Id // cet la clé primaire
    @GeneratedValue (strategy = GenerationType.IDENTITY) // auto-increment de l'id
    private long id;

    // tous le colonnes de la dataset

    // On mappe tous le colonnes

    @CsvBindByName(column = "Gender")
    private String gender;
    @CsvBindByName(column = "Age")
    private int age;
    @CsvBindByName(column = "Occupation")
    private String occupation;
    @CsvBindByName(column = "Sleep Duration")
    private double sleepDuration;
    @CsvBindByName(column = "Quality of Sleep")
    private String sleepQuality;
    @CsvBindByName(column = "Physical Activity Level")
    private int physicalActivity;
    @CsvBindByName(column = "Stress Level")
    private int stressLevel;
    @CsvBindByName(column = "BMI Category")
    private String bmiCategory;
    @CsvBindByName(column = "Blood Pressure")
    private String bloddPressure;
    @CsvBindByName(column = "Heart Rate")
    private int heartRate;
    @CsvBindByName(column = "Daily Steps")
    private int dailySteps;
    @CsvBindByName(column = "Sleep Disorder")
    private String sleepDisorder;

    public SleepStudy( String gender, int age, String occupation, double sleepDuration, String sleepQuality,
                       int physicalActivity, int stressLevel, String bmiCategory, String bloddPressure, int heartRate,
                       int dailySteps, String sleepDisorder )
    {
        this.gender = gender;
        this.age = age;
        this.occupation = occupation;
        this.sleepDuration = sleepDuration;
        this.sleepQuality = sleepQuality;
        this.physicalActivity = physicalActivity;
        this.stressLevel = stressLevel;
        this.bmiCategory = bmiCategory;
        this.bloddPressure = bloddPressure;
        this.heartRate = heartRate;
        this.dailySteps = dailySteps;
        this.sleepDisorder = sleepDisorder;
    }
}
