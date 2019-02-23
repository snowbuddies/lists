import refs.forecast.*;
import refs.forecast.model.Latitude;
import refs.forecast.model.Longitude;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class Main {

    public static void main(String[] args) throws ForecastException {
        Main yeet = new Main();
        String[] fcTimes = yeet.fcTimes(47, -88.5);
       /** yeet.req(47, -88.5);
        for(String t : yeet.fcTimes(47, -88.5)){
            System.out.println(t);
        }
        **/
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
        ArrayList<Double []> cList = new ArrayList<>();
            Double[] ca = {-88.569399, 47.121866};
            Double[] cb = {-87.666476, 46.503312};
            Double[] cc = {-87.356813, 46.462938};
            cList.add(ca);
            cList.add(cb);
            cList.add(cc);
        return cList;
    }


}
