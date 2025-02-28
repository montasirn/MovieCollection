import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        try {
            while(true){
                ArrayList<SimpleMovie> movies = MovieDatabaseBuilder.getMovieDB("src/movie_data");

                System.out.println("Enter an actor's name or (q) to quit");
                Scanner s = new Scanner(System.in);
                String check = s.nextLine();
                if (check.equals("q")){
                    break;
                }
                String actor = check;

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

                boolean isBacon = false;
                if (actor.equals("Kevin Bacon")){
                    isBacon = true;
                    System.out.println("That's Kevin Bacon");
                    System.out.println("Bacon Number of: 0");
                }

                // Creates hashset for the actors, removes duplicates
                Set<String> actorSet = MovieDatabaseBuilder.removeDupes(allActorsList);

                boolean firstDegree = actorSet.contains("Kevin Bacon");
                if (!isBacon && firstDegree){
                    if (!MovieDatabaseBuilder.movieName(actor, "Kevin Bacon", movies).isEmpty()){
                        System.out.println(actor + " -> " + MovieDatabaseBuilder.movieName(actor, "Kevin Bacon", movies) + " -> Kevin Bacon");
                        System.out.println("Bacon Number of: 1");
                    }
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
                            System.out.println("Bacon Number of: 2");
                            break;
                        }
                    }
                }


                // Hash set of the cocoStars of the original actors
                HashMap<String , Set<String>> actorCast = new HashMap<String, Set<String>>();
                // Link to Kevin Bacon
                String link1 = "";
                boolean thirdDegree = false;
                if (!secondDegree && !firstDegree){
                    for (String string : actorSet) {

                        String costar = MovieDatabaseBuilder.buildActorCast(movies, string);
                        String[] costarList = costar.split(":");

                        Set<String> test = MovieDatabaseBuilder.removeDupes(costarList);

                        // adds the cast that has worked with actor, string, actor -> coactor -> cocoactor and their link is the key
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
                // link from link1 to original actor
                String link2 = "";
                if (thirdDegree){
                    for (String str : actorCast.keySet()){
                        if (actorCast.get(str).contains(link1)){
                            for (String n : actorCast.get(str)){
                                if (!Objects.equals(MovieDatabaseBuilder.movieName(n, link1, movies), "") && !(Objects.equals(MovieDatabaseBuilder.movieName(actor, n, movies),""))){
                                    link2 = n;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (thirdDegree){
                    System.out.println(actor + " -> " + MovieDatabaseBuilder.movieName(actor,link2,movies) + " -> " + link2 + " -> " + MovieDatabaseBuilder.movieName(link2,link1,movies) + " -> " + link1 + " -> " + MovieDatabaseBuilder.movieName(link1, "Kevin Bacon", movies) + " -> Kevin Bacon");
                    System.out.println("Bacon Number: 3");
                }

                boolean fourthDegree = false;

                // Direct link to Kevin Bacon
                String name1 = "none";
                String name2 = "none";
                String name3 = "none";

                HashMap<String , Set<String>> actorCast4 = new HashMap<String, Set<String>>();

                if (!thirdDegree && !secondDegree && !firstDegree){
                for (String string : actorCast.keySet()){
                    for (String s1 : actorCast.get(string)){
                        String costar = MovieDatabaseBuilder.buildActorCast(movies, s1);
                        String[] costarList = costar.split(":");

                        Set<String> test = MovieDatabaseBuilder.removeDupes(costarList);

                        actorCast4.put(s1, test);
                        for (String s2 : data){
                            if (test.contains(s2)){
                                name1 = s2;
                                String costarNext = MovieDatabaseBuilder.buildActorCast(movies,s2);
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
                }

                if (fourthDegree){
                    for (String key : actorCast4.keySet()){
                        if (actorCast4.get(key).contains(name1)){
                            if (key.contains(" ") && !Objects.equals(MovieDatabaseBuilder.movieName(key, name1, movies), "")){
                                name2 = key;
                                break;
                            }
                        }
                    }
                }

                if (name2.equals("none")){
                    fourthDegree = false;
                    for (int counter = 0; counter < data.length; counter ++){
                        if (data[counter].equals(name1)){
                            data[counter] = "";
                        }
                    }
                }

                if (!fourthDegree){
                    for (String string : actorCast4.keySet()) {
                        for (String s2 : data) {
                            if (actorCast4.get(string).contains(s2) && !MovieDatabaseBuilder.movieName(s2,"Kevin Bacon", movies).isEmpty()) {
                                name1 = s2;
                                String costarNext = MovieDatabaseBuilder.buildActorCast(movies, s2);
                                String[] costarListNext = costarNext.split(":");

                                Set<String> testNext = MovieDatabaseBuilder.removeDupes(costarListNext);

                                fourthDegree = testNext.contains("Kevin Bacon");
                            }
                            if (fourthDegree){
                                break;
                            }
                        }
                        if (actorCast4.get(string).contains(name1)){
                            if (string.contains(" ") && !Objects.equals(MovieDatabaseBuilder.movieName(string, name1, movies), "")){
                                name2 = string;
                                fourthDegree = true;
                                break;
                            }
                        }
                    }
                }

                if (fourthDegree){
                    for (String str : actorCast.keySet()){
                        if (actorCast.get(str).contains(name2)){
                            for (String n : actorCast.get(str)){
                                if (!Objects.equals(MovieDatabaseBuilder.movieName(n, name2, movies), "") && !(Objects.equals(MovieDatabaseBuilder.movieName(actor, n, movies),""))){
                                    name3 = n;
                                    break;
                                }
                            }
                        }
                    }
                    System.out.println(actor + " -> " + MovieDatabaseBuilder.movieName(actor,name3,movies)+ " -> " + name3 + " -> "+ MovieDatabaseBuilder.movieName(name3,name2,movies) + " -> " + name2 + " -> " + MovieDatabaseBuilder.movieName(name2,name1,movies) + " -> " + name1 + " -> " + MovieDatabaseBuilder.movieName(name1, "Kevin Bacon", movies) + " -> Kevin Bacon");
                    System.out.println("Bacon number: 4");
                }
            }

            System.exit(10);

        }
        catch (FileNotFoundException noFile) {
            System.out.println("File not found!");
        }
    }
}
