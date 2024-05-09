package org.example.Model;

public class ModelsInitializer {
    public static void init() throws Exception{
        // initialize all model
        TestCandidatesModel.init();
        TestResponseModel.init();
        QuestionSetModel.init();
        QuestionModel.init();
        StudentModel.init();
        OptionModel.init();
        TestModel.init();
    }
}
