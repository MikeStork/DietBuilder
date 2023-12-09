package app.controllers;

import javax.swing.*;

public class ValidationController {
    public static boolean isString(JTextField textField, String fieldName) {
        String text = textField.getText().trim();
        if (text.isEmpty()) {
            showMessage(fieldName + " cannot be empty.", "Validation Error");
            return false;
        }
        return true;
    }

    public static boolean isFloat(JTextField textField, String fieldName) {
        String text = textField.getText().trim();
        try {
            Float.parseFloat(text);
        } catch (NumberFormatException e) {
            showMessage(fieldName + " must be a valid floating-point number.", "Validation Error");
            return false;
        }
        return true;
    }

    public static boolean isInteger(JTextField textField, String fieldName) {
        String text = textField.getText().trim();
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException e) {
            showMessage(fieldName + " must be a valid integer.", "Validation Error");
            return false;
        }
        return true;
    }
    private static void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
