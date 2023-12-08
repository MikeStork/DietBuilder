package app.controllers;

import app.database.Database;
import app.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MealController {
    String table_listing_query = "SELECT m.type AS rodzaj_posiłku, m.name AS nazwa_posiłku, p.name AS nazwa_produktu, p.carbs, p.fats, p.proteins, p.weight FROM meals m JOIN products p ON m.product_id = p.id ORDER BY m.type, m.name, p.name; ";
}
