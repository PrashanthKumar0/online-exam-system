package org.example.Helpers;

import java.util.Scanner;

import org.example.Structs.EOptionType;
import org.example.Structs.Question;
import org.example.Structs.QuestionBank;

public class QuestionParser {
    static public QuestionBank parseQuestion(Scanner scanner){
        scanner.useDelimiter(" ");
        QuestionBank question_bank = new QuestionBank();

        String accumulator="";
        Question question= new Question();

        boolean isReadingQuestion = true;
        boolean isCorrectOption = false;
        boolean shouldStartAccumulating = false;

        while(scanner.hasNext()) {
            char[] word = scanner.next().toCharArray();
            for(char ch : word) {
                if(ch == '#') { // skip comments
                    if(scanner.hasNextLine()) scanner.nextLine();
                    continue;
                }

                // Question block begins
                if(ch == '+') {
                    if(!accumulator.equals("")) {
                        if(!isReadingQuestion) {
                            EOptionType option_type =  isCorrectOption ? EOptionType.CORRECT : EOptionType.INCORRECT;
                            question.addOption(accumulator.trim(),  option_type);
                            question_bank.addQuestion(question);
                            question = new Question();
                            accumulator = "";
                        }
                    }
                    
                    shouldStartAccumulating = true;                    
                    isReadingQuestion = true;
                    continue;
                }

                // option block begins
                if(ch == '-' || ch == '@') {
                    if(!accumulator.equals("")) {
                        if(isReadingQuestion) {
                        question.setQuestion(accumulator.trim()+"\n\n");
                        } else {
                            EOptionType option_type =  isCorrectOption ? EOptionType.CORRECT : EOptionType.INCORRECT;
                            question.addOption(accumulator.trim(), option_type);
                        }
                    }
                    accumulator = "";
                    isReadingQuestion = false;
                    if(ch=='@') isCorrectOption = true;
                    else isCorrectOption = false;
                    continue;
                }

                if(!shouldStartAccumulating) continue;
                accumulator += ch;
            }

            if(!accumulator.equals("")) accumulator += " ";
        }
        
        if(!question.getQuestion().trim().isEmpty()) {
            if(!isReadingQuestion && ! accumulator.trim().equals("")) {
                EOptionType option_type =  isCorrectOption ? EOptionType.CORRECT : EOptionType.INCORRECT;
                question.addOption(accumulator.trim(),  option_type);
            }
            question_bank.addQuestion(question);
        }

        return question_bank;
    }
}
