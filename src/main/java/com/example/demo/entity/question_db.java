package com.example.demo.entity;

import lombok.Data;

@Data
public class question_db {
    private int id;

    private String question;

   private String answer_A;
   private String answer_B;
   private String answer_C;
   private String answer_D;

   private int courseId;
}
