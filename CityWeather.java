import java.io.BufferedReader;//used to read text efficiently from an input stream.
import java.io.InputStreamReader;//which bridges a byte stream (InputStream) to a character stream (Reader).
import java.net.HttpURLConnection;//the Java class used to open and configure an HTTP connection.
import java.net.URL;// used to create an object representing the API endpoint address.
import org.json.JSONObject;//a convenient small JSON parser used here to interpret the API JSON response.
import java.util.Scanner;//used to read user input from System.in (console).

public class CityWeather {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Ask for city name
        System.out.print("Enter your city: ");
        String city = sc.nextLine();//Reads the full line the user types and stores it in the `city` string variable.


        // Your OpenWeatherMap API key
        String apiKey = "9eaeab9f56709746f05372cdbaf30aa3"; // Replace with your real API key

        try {// if something goes wrong (an exception is thrown), handle it instead of crashing the program.
            // Create API URL
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q="
                    + city + "&units=metric&appid=" + apiKey;

            // Make HTTP request
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read API response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse JSON
            JSONObject json = new JSONObject(response.toString());
            JSONObject main = json.getJSONObject("main");
            double temp = main.getDouble("temp");

            String weatherDescription = json.getJSONArray("weather")
                    .getJSONObject(0).getString("description");

            // Output weather info
            System.out.println("\n--- Weather Information ---");
            System.out.println("City: " + city);
            System.out.println("Temperature: " + temp + "°C");
            System.out.println("Condition: " + weatherDescription);

        } catch (Exception e) {
            System.out.println("Error fetching weather data. Please check the city name.");
        }

        sc.close();
    }
}
