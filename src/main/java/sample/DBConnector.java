package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBConnector {
    public static final  String JDBCURL = "jdbc:postgresql:test_db";
    public static final  String USERNAME = "postgres";
    public static final  String PASSWORD = "Sh52104547";
    public static final String GRAPHICCARDSTABLENAME = "graphiccards";
    public static final String BRANDSTABLENAME = "brands";

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD);
        return connection;
    }
    public static ObservableList<GraphicCardModel> getNewList(){
        ObservableList<GraphicCardModel> observableList = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnector.getConnection();
            String sql = "SELECT * FROM " +  GRAPHICCARDSTABLENAME + ", " + BRANDSTABLENAME +
                    " WHERE " + GRAPHICCARDSTABLENAME + ".brandid = " + BRANDSTABLENAME + ".id AND " + GRAPHICCARDSTABLENAME + " .status = 1";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while(rs.next()){
                observableList.add(new GraphicCardModel( rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("brandid"),
                        rs.getInt("memory"),
                        rs.getString("brandname"),
                        rs.getInt("gpufrequency"),
                        rs.getInt("price")));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return observableList;
    }

    public static ObservableList<String> createBrandListFilter(boolean addAllFilters){
        ObservableList<String> observableList = FXCollections.observableArrayList();
        if(addAllFilters==true){
            observableList.add("Все");
        }
        try {
            Connection connection = DBConnector.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * from " + BRANDSTABLENAME +
                    " WHERE status = 1");
            while (resultSet.next()){
                observableList.add(resultSet.getString("brandname"));
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return observableList;
    }
    public static ObservableList<String> createMemoryList(){
        ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.add("Все");
        try {
            Connection connection = DBConnector.getConnection();
            String sql = "SELECT DISTINCT memory  from " + GRAPHICCARDSTABLENAME;
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()){
                observableList.add(resultSet.getString("memory"));
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return observableList;
    }

    public static void  addNewInfo(String name, String brandname, int memory){
        try{
            Connection connection = DBConnector.getConnection();
            String sql = "INSERT INTO " +  GRAPHICCARDSTABLENAME + " (name, brandid, memory, status) " +
                    "VALUES ( '" + name + "' , " +
                    "(select id from brands WHERE brandname = '" + brandname + "' ), " + memory + ", 1)";

            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            System.out.println(rows);
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static ObservableList<GraphicCardModel> UseFilter(String brandFilter, String memoryFilter){
        if(brandFilter == "Все" && memoryFilter == "Все"){
            return getNewList();
        }else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT * FROM " +  GRAPHICCARDSTABLENAME + ", " + BRANDSTABLENAME +
                    " WHERE " + GRAPHICCARDSTABLENAME + ".brandid = " + BRANDSTABLENAME + ".id");
                if(brandFilter != "Все"){
                    stringBuilder.append(" AND brandname = '" + brandFilter + "'");
                    if(memoryFilter != "Все"){
                        stringBuilder.append(" AND memory = " + memoryFilter);
                    }
                }
                if(brandFilter == "Все" && memoryFilter != "Все"){
                    stringBuilder.append(" AND memory = " + memoryFilter);
                }
            ObservableList<GraphicCardModel> observableList = FXCollections.observableArrayList();
            try {
                Connection connection = DBConnector.getConnection();
                ResultSet rs = connection.createStatement().executeQuery(stringBuilder.toString());

                while(rs.next()){
                    observableList.add(new GraphicCardModel( rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("brandid"),
                            rs.getInt("memory"),
                            rs.getString("brandname"),
                            rs.getInt("gpufrequency"),
                            rs.getInt("price")));
                }
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return observableList;
        }
    }
    public static ObservableList<BrandModel> getBrandList(){
        ObservableList<BrandModel> observableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM " + BRANDSTABLENAME;
        try {
            Connection connection = DBConnector.getConnection();
            ResultSet  rs = connection.createStatement().executeQuery(sql);
            while(rs.next()){
                observableList.add(new BrandModel(rs.getInt("id"),
                        rs.getString("brandname"),
                        rs.getInt("status")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return observableList;
    }
     public static ObservableList<BrandModel> changeStatus(int status, int id){
         String sql = "UPDATE " + BRANDSTABLENAME + " SET status = " + status + " WHERE id = " + id;
         try {
             Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement();
             statement.executeUpdate(sql);
             connection.close();
         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }
         return getBrandList();
     }
}
