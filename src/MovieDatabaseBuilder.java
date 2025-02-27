import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;

public class MovieDatabaseBuilder {

    public static ArrayList<SimpleMovie> getMovieDB(String fileName) {
        ArrayList<SimpleMovie> movies = new ArrayList<SimpleMovie>();
        try {
            File movieData = new File(fileName);
            Scanner reader = new Scanner(movieData);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] data = line.split("---");
                if (data.length > 1) {
                    SimpleMovie s = new SimpleMovie(data[0], data[1]);
                    movies.add(s);
                }

            }
        }
        catch (FileNotFoundException noFile) {
            System.out.println("File not found!");
            return null;
        }
        return movies;
    }

    public static String buildActorCast(ArrayList<SimpleMovie> movies, String actor){
        String allActors = "";
        for (SimpleMovie movie : movies) {
            if (movie.getActorsData().contains(actor)) {
                allActors += movie.getActorsData();
            }
        }
        return allActors;
    }

    public static Set<String> removeDupes(String[] allActorsList){
        Set<String> actorSet = new HashSet<String>();
        for (int i = 0; i < allActorsList.length; i ++){
            actorSet.add(allActorsList[i]);
        }
        return actorSet;
    }

    public static String movieName(String actor1, String actor2, ArrayList<SimpleMovie> movies){
        String movie = "";
        for (SimpleMovie title : movies){
            if (title.getActorsData().contains(actor1) && title.getActorsData().contains(actor2)){
                movie = title.getTitle();
            }
        }
        return movie;
    }

    public static String fourth(boolean thirdDegree, Set<String> third, ArrayList<SimpleMovie> movies, Set<String> totalCast, HashMap<String, Set<String>> actorCast4, String[] data, boolean fourthDegree, String name1) {
        if (!thirdDegree) {
            for (String str : third) {
                String costar = MovieDatabaseBuilder.buildActorCast(movies, str);
                String[] costarList = costar.split(":");

                Set<String> test = MovieDatabaseBuilder.removeDupes(costarList);

                totalCast.addAll(test);
            }
            for (String str : totalCast) {
                String costar = MovieDatabaseBuilder.buildActorCast(movies, str);
                String[] costarList = costar.split(":");

                Set<String> test = MovieDatabaseBuilder.removeDupes(costarList);

                actorCast4.put(str, test);
            }
            for (String str : totalCast) {
                String costar = MovieDatabaseBuilder.buildActorCast(movies, str);
                String[] costarList = costar.split(":");

                Set<String> test = MovieDatabaseBuilder.removeDupes(costarList);
                for (String string : data) {
                    if (test.contains(string)) {
                        name1 = string;
                        String costarNext = MovieDatabaseBuilder.buildActorCast(movies, string);
                        String[] costarListNext = costarNext.split(":");

                        Set<String> testNext = MovieDatabaseBuilder.removeDupes(costarListNext);

                        fourthDegree = testNext.contains("Kevin Bacon");
                    }
                    if (fourthDegree) {
                        break;
                    }
                }
            }

        }
        return name1;
    }

}