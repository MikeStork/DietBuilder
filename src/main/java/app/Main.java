package app;

import app.controllers.MealController;
import app.controllers.ProductController;
import app.database.Database;
import app.model.Recipe;
import app.views.MainForm;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            new MainForm();
        });

    }
}