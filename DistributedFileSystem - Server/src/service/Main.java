/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import com.almworks.sqlite4java.SQLiteException;
import dao.DAOFactory;
import dao.TemporaryDAO;
import entity.Temporary;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hugo
 */
public class Main {
    public static void _main(String[] args) throws SQLiteException {
        Logger.getLogger("com.almworks.sqlite4java").setLevel(Level.OFF);
        try (DAOFactory dao = new DAOFactory();) {
            TemporaryDAO tempdao = dao.getTemporaryDAO();
            for (Temporary t: tempdao.readdir("/")) {
                System.out.println(t.getFname());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
