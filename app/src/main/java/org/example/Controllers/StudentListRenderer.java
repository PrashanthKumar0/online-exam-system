package org.example.Controllers;

import java.util.ArrayList;

import org.example.Helpers.StudentsManager;
import org.example.Model.StudentModel;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Separator;
import javafx.beans.value.ObservableValue;

public class StudentListRenderer {
    private StudentsManager students_manager;

    StudentListRenderer(StudentsManager students_manager) {
        this.students_manager = students_manager;
    }

    void render(VBox root) {
        ArrayList<StudentModel> student_list = this.students_manager.getStudents();

        for (StudentModel student : student_list) {

            Label name = new Label(student.getName());
            Label email = new Label(student.getEmail());

            HBox list_item = new HBox();
            CheckBox check_box = new CheckBox(student.getRoll());

            list_item.getChildren().add(check_box);
            list_item.getChildren().add(new Separator());
            list_item.getChildren().add(name);
            list_item.getChildren().add(new Separator());
            list_item.getChildren().add(email);

            root.getChildren().add(list_item);
            root.getChildren().add(new Separator());

            check_box.selectedProperty().addListener(
                    (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                        student.setSelected(new_val);
                    } //
            );
        }
    }
}
