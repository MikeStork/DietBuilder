package app.controllers;

import javax.swing.*;
import java.awt.*;

public class ValidationController {
    public static boolean isString(Component component, JTextField textField) {
        String text = textField.getText().trim();
        if (text.isEmpty()) {
            showMessage(component, "Field cannot be empty.", "Validation Error");
            return false;
        }
        return true;
    }
    public static boolean isString(Component c, String textEntered) {
        String text = textEntered;
        if (text.isEmpty()) {
            showMessage(c, "String cannot be empty.", "Validation Error");
            return false;
        }
        return true;
    }

    public static boolean isFloat(Component component, JTextField textField) {
        String text = textField.getText().trim();
        try {
            Float.parseFloat(text);
        } catch (NumberFormatException e) {
            showMessage(component, "Float point field must be a valid floating-point number.", "Validation Error");
            return false;
        }
        return true;
    }

    public static boolean isInteger(Component component,JTextField textField) {
        String text = textField.getText().trim();
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException e) {
            showMessage(component, "Integer field must be a valid integer.", "Validation Error");
            return false;
        }
        return true;
    }
    public static boolean isInteger(Component component, String textEntered) {
        String text = textEntered.trim();
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException e) {
            showMessage(component,textEntered + " must be a valid integer.", "Validation Error");
            return false;
        }
        return true;
    }

    public static void showMessage(Component component, String message, String title) {
        System.out.println(component.getName()+" : "+title+" : "+message);
    }
}
