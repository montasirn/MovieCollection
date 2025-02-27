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
            if (firstDegree){
                System.out.println(actor + " -> " + " -> Kevin Bacon");
            }

            boolean secondDegree = false;
            String link = "";
            if (!firstDegree){
                for (String str : data){
                    if (actorSet.contains(str)) {
                        link = str;
                        String costar = MovieDatabaseBuilder.buildActorCast(movies,str);
                        String[] costarList = costar.split(":");

                        Set<String> test = MovieDatabaseBuilder.removeDupes(costarList);

                        secondDegree = test.contains("Kevin Bacon");
                    }
                    if (secondDegree){
                        System.out.println(actor + " -> " + MovieDatabaseBuilder.movieName(actor, link, movies) + " -> " + link + " -> " + MovieDatabaseBuilder.movieName(link, "Kevin Bacon", movies) + " -> Kevin Bacon");
                        break;
                    }
                }
            }

            Set<String> third = Set.of();


            HashMap<String , Set<String>> actorCast = new HashMap<String, Set<String>>();
            String link1 = "";
            boolean thirdDegree = false;
            if (!secondDegree){
                for (String string : actorSet) {

                    String costar = MovieDatabaseBuilder.buildActorCast(movies, string);
                    String[] costarList = costar.split(":");

                    Set<String> test = MovieDatabaseBuilder.removeDupes(costarList);

                    third = test;

                    actorCast.put(string, test);

                    for (String str : data){
                        if (test.contains(str)) {
                            link1 = str;
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
            String link2 = "";
            if (thirdDegree){
                for (String str : actorCast.keySet()){
                    Set<String> cocoStar = actorCast.get(str);
                    for (String string : cocoStar){
                        if (!Objects.equals(MovieDatabaseBuilder.movieName(string, link1, movies), "")){
                            link2 = string;
                            break;
                        }
                    }
                }
            }

            System.out.println(link1);
            System.out.println(link2);
            System.out.println(thirdDegree);

            if (thirdDegree){
                System.out.println(actor + " -> " + MovieDatabaseBuilder.movieName(actor,link2,movies) + " -> " + link2 + " -> " + MovieDatabaseBuilder.movieName(link2,link1,movies) + " -> " + link1 + " -> " + MovieDatabaseBuilder.movieName(link1, "Kevin Bacon", movies) + " -> Kevin Bacon");
            }

            boolean fourthDegree = false;

            Set<String> totalCast = new HashSet<>(Set.of());

            if (!thirdDegree){
                for (String str : third){
                    String costar = MovieDatabaseBuilder.buildActorCast(movies, str);
                    String[] costarList = costar.split(":");

                    Set<String> test = MovieDatabaseBuilder.removeDupes(costarList);

                    totalCast.addAll(test);
                }
                for (String str : totalCast){
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
