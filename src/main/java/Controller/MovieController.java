package Controller;

import Model.Movie;
import Model.Account;

import Service.MovieService;
import Service.AccountService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;


import java.util.List;

public class MovieController {
    AccountService accountService;
    MovieService movieService;

    public MovieController(){
        this.accountService = new AccountService();
        this.movieService = new MovieService();
    }

    public Javalin startAPI(){
        Javalin app = Javalin.create();
        app.post("register", this::registerHandler);
        app.post("login", this::userLoginHandler);
        app.get("/movies", this::getAllMoviesHandler);
        app.post("/movies", this::postMoviesHandler);
        app.get("/movies/rating/<rating>", this::getMoviesByRatingHandler);
        app.get("/movies/genre/<genre>", this::getMoviesByGenresHandler);
        app.delete("movies/{movie_id}", this::deleteMovieByIDHandler);
        return app;
    }

    private void registerHandler(Context ctx) throws JsonProcessingException {

        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account addAccount = accountService.addAccount(account);
        if(addAccount!=null){
            ctx.json(om.writeValueAsString(addAccount));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }

    private void userLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account userLogIn = accountService.logIn(account.getUsername(), account.getPassword());
        if(userLogIn != null){
            ctx.json(om.writeValueAsString(userLogIn));
        }else{
            ctx.status(401);
        }
    }

    private void getAllMoviesHandler(Context ctx) {
        List<Movie> movies = movieService.getAllMovies();
        ctx.json(movies);
    }

    private void postMoviesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Movie movie = om.readValue(ctx.body(), Movie.class);
        Movie createdMovie = movieService.createMovie(movie);
        if(createdMovie != null){
            ctx.json(om.writeValueAsString(createdMovie));
        }else{
            ctx.status(400);
        }
    }

    private void getMoviesByRatingHandler(Context ctx) {
        int rating = Integer.parseInt(ctx.pathParam("rating"));
        List<Movie> moviesByRating = movieService.getMovieByRating(rating);
        ctx.json(moviesByRating);
    }


    private void getMoviesByGenresHandler(Context ctx) {
        String genre = ctx.pathParam("genre");
        List<Movie> moviesByGenre = movieService.getMovieByGenre(genre);
        ctx.json(moviesByGenre);
    }

    private void deleteMovieByIDHandler(Context ctx) throws JsonProcessingException {
        int movie_id = Integer.parseInt(ctx.pathParam("movie_id"));
        Movie deleteMovie = movieService.deleteMovieByID(movie_id);
        if(deleteMovie != null){
            ctx.json(deleteMovie);
        }else{
            ctx.status(200);
        }
    }


}