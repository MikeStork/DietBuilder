package app;

import app.controllers.ProductController;
import app.database.Database;
import app.views.MainForm;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println(ProductController.list());
        SwingUtilities.invokeLater(()->{
            new MainForm();
        });

    }
}