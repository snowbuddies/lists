
import refs.forecast.*;
import refs.forecast.model.Latitude;
import refs.forecast.model.Longitude;

import java.io.DataOutput;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;





@SuppressWarnings("ALL")
public class Main {

    public static void main(String[] args) throws ForecastException {
        Main yeet = new Main();
        String[] fcTimes = yeet.fcTimes(47, -88.5);

//        yeet.req(47, -88.5);
//        for(String t : yeet.fcTimes(47, -88.5)){
//            System.out.println(t);
//        }


        yeet.makeCoordList();
        System.out.println();
    }
    public void req(double lat, double lon) throws ForecastException {
        ForecastRequest request = new ForecastRequestBuilder()
                .key(new APIKey("9eec249edf77f5e9ea1adb48f24820fb"))
                .language(ForecastRequestBuilder.Language.en)
                .exclude(ForecastRequestBuilder.Block.minutely,ForecastRequestBuilder.Block.daily)
                .extendHourly()
                .location(new GeoCoordinates(new Longitude(lon), new Latitude(lat))).build();
        DarkSkyClient client = new DarkSkyClient();
        String forecast = client.forecastJsonString(request);
        System.out.println(forecast);
    }

    public String fcString(double lat, double lon) throws ForecastException {
        ForecastRequest request = new ForecastRequestBuilder()
                .key(new APIKey("9eec249edf77f5e9ea1adb48f24820fb"))
                .language(ForecastRequestBuilder.Language.en)
                .units(ForecastRequestBuilder.Units.us)
                .exclude(ForecastRequestBuilder.Block.minutely,ForecastRequestBuilder.Block.daily)
                .extendHourly()
                .location(new GeoCoordinates(new Longitude(lon), new Latitude(lat))).build();
        DarkSkyClient client = new DarkSkyClient();
        return client.forecastJsonString(request);
    }
    public String[] fcTimes(double lat, double lon) throws ForecastException{
        String fcf = fcString(lat, lon);
        return fcf.split("time");
    }
    //public String[] fcDat(
    public ArrayList<Double []> makeCoordList(){
        JSONParser parser = new JSONParser();  //pareser for JSON
        ArrayList<Double []> cordsList = new ArrayList<>();  //list of coordinates
        Double [] first = {0.0, 0.0 ,0.0};
        cordsList.add(first);
        int i = 0;

        try {
            JSONObject drive = (JSONObject) parser.parse(new FileReader("src/data.json"));
            JSONArray routes = (JSONArray) drive.get("routes");

            // Loop for every route
            for(Object route : routes) {
                JSONObject jRoute = (JSONObject) route; //creates a JSON object
                JSONArray legs = (JSONArray) jRoute.get("legs"); //creates array of legs

                // Loop for legs
                for(Object leg : legs) {
                    JSONObject jLeg = (JSONObject) leg;  //creates a JSON object
                    JSONArray steps = (JSONArray) jLeg.get("steps"); //creates an array of steps

//                    Double duration = (Double) jLeg.get("duration"); //retrieves deration in steps
//                    System.out.println(duration); //prints duration

                    //Loop for steps
                    for(Object step : steps) {
                        JSONObject jStep = (JSONObject) step;
                        JSONObject jManuv = (JSONObject) jStep.get("maneuver");
                        JSONArray jLocations = (JSONArray) jManuv.get("location");
                        Double[] coord = new Double[3];
                        coord[0] = (Double) jLocations.get(0);
                        coord[1] = (Double) jLocations.get(1);

                        //adding the duraion
                        Double[] last = cordsList.get(i); //previous array
                        double total = last[2]; //total running time
                        i++; //increment i
                        try {
                            coord[2] = (Double) jStep.get("duration") + total;
                        } catch (ClassCastException e){
                            coord[2] = 0.0 + total;
                        }

                        cordsList.add(coord);


                        System.out.println("[" + coord[0] + ", " + coord[1] + ", " + coord[2] + "]");

//                        JSONArray intersections = (JSONArray) jStep.get("intersections");
////
////                        //Loop for intsections
////                        for(Object intersection : intersections) {
////                            JSONObject jIntersection = (JSONObject) intersection; //creats a JSON object
////
////                            //gets the coodanate pare of location
////                            JSONArray location = (JSONArray) jIntersection.get("location");
////                            Double [] cords = {(Double) location.get(0), (Double) location.get(1)};
////                            cordsList.add(cords);
////                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return cordsList;
    }


}
