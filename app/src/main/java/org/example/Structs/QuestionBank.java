package org.example.Structs;

import java.util.ArrayList;

import org.example.Model.QuestionModel;
import org.example.Model.QuestionSetModel;

public class QuestionBank {
    private ArrayList<Question> questions = new ArrayList<Question>();
    private String set_ID = null;
    private String set_description = null;

    public void addQuestion(Question question){
        questions.add(question);
    }
    
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    // Getters
    public String getSetID() {
        return set_ID;
    }

    public String getSetDesctiption() {
        return set_description;
    }

    //--------------
    // factory Methods
    public static QuestionBank fromSetID(String setID) throws Exception{
        QuestionBank qb = new QuestionBank();
        qb.questions = QuestionModel.getAllQuestions(setID);
        qb.set_description =  QuestionSetModel.getDescription(setID);
        return qb;
    }

    public void print(){
        for(Question question : questions) {
            System.out.println("-------------------------------------");
            System.out.println("Q : "+question.getQuestion());
            for(Option option :  question.getOptions()) {
                if(option.getOptionType() == EOptionType.CORRECT) {
                    System.out.print("C : ");
                }else{
                    System.out.print("W : ");
                }
                System.out.println(option.getOption());
            }
        }
    }
}
