import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        try {

            ArrayList<SimpleMovie> movies = MovieDatabaseBuilder.getMovieDB("src/movie_data");


            System.out.println("Enter an actor's name or (q) to quit");
            Scanner s = new Scanner(System.in);
            String actor = s.nextLine();

            // gets the cast of the actor inputted
            String cast = MovieDatabaseBuilder.buildActorCast(movies,actor);

            // All coActors in the cast
            String[] allActorsList = cast.split(":");

            //Reads file for actors
            String fileData = "";
            File movieData = new File("src/Actors");
            Scanner reader = new Scanner(movieData);
            while (reader.hasNextLine()) {
                fileData += reader.nextLine();
            }

            //Creates an array of all popular actors
            String[] data = fileData.split("\\)");

            //Correctly formats
            for (int i = 0; i < data.length; i ++){
                data[i] = data[i].substring(0,data[i].indexOf("(") - 1);
            }

            // Creates hashset for the actors, removes duplicates
            Set<String> actorSet = MovieDatabaseBuilder.removeDupes(allActorsList);

            boolean firstDegree = actorSet.contains("Kevin Bacon");

            System.out.println(firstDegree);

            boolean secondDegree = false;
            if (!firstDegree){
                for (String str : data){
                    if (actorSet.contains(str)) {
                        String costar = MovieDatabaseBuilder.buildActorCast(movies,str);
                        String[] costarList = costar.split(":");

                        Set<String> test = MovieDatabaseBuilder.removeDupes(costarList);

                        secondDegree = test.contains("Kevin Bacon");
                    }
                    if (secondDegree){
                        break;
                    }
                }
            }

            System.out.println(secondDegree);

            Set<String> third = Set.of();

            boolean thirdDegree = false;
            if (!secondDegree){
                for (String string : actorSet) {

                    String costar = MovieDatabaseBuilder.buildActorCast(movies, string);
                    String[] costarList = costar.split(":");

                    Set<String> test = MovieDatabaseBuilder.removeDupes(costarList);

                    third = test;

                    for (String str : data){
                        if (test.contains(str)) {
                            String costarNext = MovieDatabaseBuilder.buildActorCast(movies,str);
                            String[] costarListNext = costarNext.split(":");

                            Set<String> testNext = MovieDatabaseBuilder.removeDupes(costarListNext);

                            thirdDegree = testNext.contains("Kevin Bacon");
                        }
                        if (thirdDegree){
                            break;
                        }
                    }
                }
            }

            System.out.println(thirdDegree);


            boolean fourthDegree = false;

            if (!thirdDegree){
                for (String str : third){
                    String costar = MovieDatabaseBuilder.buildActorCast(movies, str);
                    String[] costarList = costar.split(":");

                    Set<String> test = MovieDatabaseBuilder.removeDupes(costarList);

                    for (String string : data){
                        if (test.contains(string)) {
                            String costarNext = MovieDatabaseBuilder.buildActorCast(movies, string);
                            String[] costarListNext = costarNext.split(":");

                            Set<String> testNext = MovieDatabaseBuilder.removeDupes(costarListNext);

                            fourthDegree = testNext.contains("Kevin Bacon");
                        }
                        if (fourthDegree){
                            break;
                        }
                    }
                }
            }

            System.out.println(fourthDegree);

        }
        catch (FileNotFoundException noFile) {
            System.out.println("File not found!");
        }
    }
}