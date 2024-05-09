package org.example.TestServer;

import org.example.Structs.GuiClientServerCommunicationObject;

import javafx.concurrent.Task;
import javafx.scene.control.Label;

public class TestServer {
    Label label;

    public void setLabel(Label label) {
        this.label = label;
    }

    public void run() throws Exception {

        try {
            int num = 0;
            while (num < 5) {
                if (label != null)
                    label.setText("count " + num);

                num++;
                
                System.out.println("Running server.. thread blocked?");
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // -----
    }

}
