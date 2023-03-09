package DAO;

import Model.Movie;
import Util.ConnectionSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    public List<Movie> getAllMovies() {
        Connection connection = ConnectionSingleton.getConnection();
        List<Movie> allMovies = new ArrayList<>();

        try{
            String sql = "SELECT * FROM movie";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Movie movie = new Movie(rs.getInt("movie_id"), rs.getString("movie_name"), rs.getString("genre"), rs.getInt("rating"), rs.getInt("posted_by"));
                allMovies.add(movie);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return allMovies;
    }

    public Movie insertMovie(Movie movie){
        Connection connection = ConnectionSingleton.getConnection();
        try{
            String sql = "INSERT INTO movie (movie_name, genre, rating, posted_by) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, movie.getMovie_name());
            preparedStatement.setString(2, movie.getGenre());
            preparedStatement.setLong(3, movie.getRating());
            preparedStatement.setInt(4, movie.getPosted_by());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                int generated_movie_id = (int)rs.getLong(1);
                return new Movie(
                        generated_movie_id,
                        movie.getMovie_name(),
                        movie.getGenre(),
                        movie.getRating(),
                        movie.getPosted_by());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Movie> getMovieByRating(int rating){
        Connection connection = ConnectionSingleton.getConnection();
        List<Movie> moviesByRating = new ArrayList<>();

        try {
            String sql = "SELECT * FROM movie WHERE rating = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, rating);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Movie movie = new Movie(rs.getInt("movie_id"), rs.getString("movie_name"), rs.getString("genre"), rs.getInt("rating"), rs.getInt("posted_by"));
                moviesByRating.add(movie);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return moviesByRating;
    }

    public List<Movie> getMovieByGenre(String genre) {
        Connection connection = ConnectionSingleton.getConnection();
        List<Movie> moviesByGenre = new ArrayList<>();

        try{
            String sql = "SELECT * FROM movie WHERE genre = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, genre);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Movie movie = new Movie(rs.getInt("movie_id"), rs.getString("movie_name"), rs.getString("genre"), rs.getInt("rating"), rs.getInt("posted_by"));
                moviesByGenre.add(movie);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return moviesByGenre;
    }

    public Movie getMovieByID(int movie_id){
        Connection connection = ConnectionSingleton.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM movie WHERE movie_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, movie_id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                return new Movie(
                        rs.getInt("movie_id"), rs.getString("movie_name"), rs.getString("genre"), rs.getInt("rating"), rs.getInt("posted_by"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Movie deleteMovieByID(int movie_id){
        Connection connection = ConnectionSingleton.getConnection();
        try {
            //Write SQL logic here
            String sql = "DELETE FROM movie WHERE movie_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, movie_id);

            preparedStatement.executeUpdate();
            return getMovieByID(movie_id);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}