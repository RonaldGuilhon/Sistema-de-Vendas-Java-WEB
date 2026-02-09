package br.com.Vendas.test;

import java.sql.Connection;
import java.sql.DriverManager;
import org.junit.Test;

public class JdbcTest {
    @Test
    public void testConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver found!");
            Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/vendas", "postgres", "123");
            System.out.println("Connection successful!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
