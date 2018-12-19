package com.kante;

import java.sql.*;
import java.util.Vector;

public class hunterDataBase {
    private static final String DB_CONNECTION_URL="jdbc:sqlite:hunting.sqlite";

    private static final String ID_COLUMN="id";
    private static final String FIRST_NAME="firstName";
    private static final  String LAST_NAME="lastName";
    private static final  String SPECIES_COLUMN="species";
    private static final  String ANIMAL_NAME_COLUMN="animalName";
    private static final String ANIMAL_GENDER_COLUMN="animalGender";


    final static String ANIMAL_SPECIE_BIRDS="Birds";
    final static String ANIMAL_SPECIE_RODENTS="Rodents";
    final static String ANIMAL_SPECIE_HERBIVOROUS="Herbivorous";
    final static String MALE_GENDER="male";
    final static String FEMALE_GENDER="female";

    //SQL statements
    private static final String CREATE_HUNTING_TABLE="CREATE TABLE IF NOT EXISTS hunting(id INTEGER PRIMARY KEY, firstName TEXT, lastName TEXT, species TEXT,animalName TEXT,animalGender TEXT)";
    private static final String GET_ALL_HUNTERS= "SELECT * FROM hunting";
    private static final String EDIT_SPECIES= "UPDATE hunting SET species = ? WHERE ID = ?";
    private static final String DELETE_HUNTER ="DELETE FROM hunting WHERE ID = ?";
    private static final String ADD_HUNTER = " INSERT INTO hunting (firstName , lastName, species, animalName, animalGender) VALUES (?,?,?,?,?)";

    hunterDataBase(){
        createTable();
    }

    private void createTable(){
        try(Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
            Statement statement = connection.createStatement()){
                statement.executeUpdate(CREATE_HUNTING_TABLE);
            System.out.println("Created hunting table");


        }catch (SQLException e){
                throw  new RuntimeException(e);
        }
    }

    Vector getColumNames(){
        Vector colNames = new Vector();
        colNames.add("id");
        colNames.add("firstName");
        colNames.add("lastName");
        colNames.add("species");
        colNames.add("animalName");
        colNames.add("animalGender");

        return colNames;

    }
    Vector<Vector>getAllHunters(){
        try(Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
            Statement statement = connection.createStatement()){

            ResultSet rs =statement.executeQuery(GET_ALL_HUNTERS);

            Vector<Vector>Vectors = new Vector<>();
            String firstName,lastName,species,animalName,gender;
            int id;

            while (rs.next()){
                id = rs.getInt(ID_COLUMN);
                firstName=rs.getString(FIRST_NAME);
                lastName=rs.getString(LAST_NAME);
                species=rs.getString(SPECIES_COLUMN);
                animalName=rs.getString(ANIMAL_NAME_COLUMN);
                gender = rs.getString(ANIMAL_GENDER_COLUMN);

                Vector v = new Vector();
                v.add(id);v.add(firstName);v.add(lastName);v.add(species);v.add(animalName);v.add(gender);

                Vectors.add(v);


            }
            return Vectors;


        }catch (SQLException e){
            throw  new RuntimeException(e);
        }


    }
    public  void deleteHunter(int hunterID){
        try(Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
            PreparedStatement preparedStatement =connection.prepareStatement(DELETE_HUNTER)){

            preparedStatement.setInt(1,hunterID);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void addHunter(int iD,String firstName,String lastName,String species,String animalName, String gender){
        try(Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
            PreparedStatement preparedStatement= connection.prepareStatement(ADD_HUNTER)){


                preparedStatement.setString(1,firstName);
                preparedStatement.setString(2,lastName);
                preparedStatement.setString(3,species);
                preparedStatement.setString(4,animalName);
                preparedStatement.setString(5,gender);

                preparedStatement.executeUpdate();
        }catch (SQLException e){
                throw new RuntimeException(e);
        }

    }
    public  void changeSpecies(int id, String species){
        try(Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
            PreparedStatement preparedStatement = connection.prepareStatement(EDIT_SPECIES)) {

            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,species);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }

    }



}
