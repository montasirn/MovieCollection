import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ArrayList<SimpleMovie> movies = MovieDatabaseBuilder.getMovieDB("src/movie_data");

        System.out.println("Enter an actor's name or (q) to quit");
        Scanner s = new Scanner(System.in);
        String actor = s.nextLine();

        String allActors = "";
        for (int i = 0; i < movies.size(); i ++){
            if (movies.get(i).getActorsData().contains(actor)){
                allActors += movies.get(i).getActorsData();
            }
        }

        String[] allActorsList = allActors.split(":");

        System.out.println(Arrays.toString(allActorsList));

    }
}