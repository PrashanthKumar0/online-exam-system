package org.example.Controllers;

import java.util.ArrayList;

import org.example.Helpers.StudentsManager;
import org.example.Model.StudentModel;

import javafx.scene.layout.GridPane;
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
        render(root, false);
    }

    void render(VBox root, boolean checked_only) {
        ArrayList<StudentModel> student_list = this.students_manager.getStudents();
        GridPane grid = new GridPane();

        grid.setHgap(10.0);

        int idx = 0;
        for (StudentModel student : student_list) {
            if (checked_only && !student.isSelected()) {
                continue;
            }
            Label name = new Label(student.getName());
            Label email = new Label(student.getEmail());

            CheckBox check_box = new CheckBox(student.getRoll());
            check_box.setSelected(student.isSelected());

            if (checked_only) {
                grid.addRow(idx, new Label(student.getRoll()));
            } else {
                grid.addRow(idx, check_box);
            }

            grid.addRow(idx, name);
            grid.addRow(idx, email);

            check_box.selectedProperty().addListener(
                    (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                        student.setSelected(new_val);
                    } //
            );

            //---------
            idx++;
        }

        root.getChildren().add(grid);
    }
}
