package it.unimib.justpizza.datasource;

import static it.unimib.justpizza.utils.DistanceUtils.distanceKm;

import java.util.ArrayList;
import java.util.List;

import it.unimib.justpizza.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeliveryRemoteDataSource {

    public interface DeliveryCallback {
        void onInRange(double km, String resolvedAddress);
        void onOutOfRange(double km);
        void onError(String message);
    }

    public interface SuggestionsCallback {
        void onResults(List<NominatimResult> results);
        void onError(String message);
    }

    private final NominatimApi api;

    public DeliveryRemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.NOMINATIM_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(NominatimApi.class);
    }

    public void checkAddress(String address, DeliveryCallback callback) {
        api.search(Constants.NOMINATIM_USER_AGENT, address, "json", 1)
                .enqueue(new Callback<List<NominatimResult>>() {
                    @Override
                    public void onResponse(Call<List<NominatimResult>> call,
                                           Response<List<NominatimResult>> response) {
                        if (!response.isSuccessful() || response.body() == null
                                || response.body().isEmpty()) {
                            callback.onError("Indirizzo non trovato");
                            return;
                        }
                        NominatimResult result = response.body().get(0);
                        try {
                            double lat = Double.parseDouble(result.lat);
                            double lon = Double.parseDouble(result.lon);
                            double km = distanceKm(
                                    Constants.PIZZERIA_LAT, Constants.PIZZERIA_LON,
                                    lat, lon);
                            if (km <= Constants.MAX_DELIVERY_KM) {
                                callback.onInRange(km, result.displayName);
                            } else {
                                callback.onOutOfRange(km);
                            }
                        } catch (NumberFormatException e) {
                            callback.onError("Errore coordinate");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<NominatimResult>> call, Throwable t) {
                        callback.onError("Serve connessione per verificare l'indirizzo");
                    }
                });
    }

    public void searchSuggestions(String query, SuggestionsCallback callback) {
        String q = query.toLowerCase().contains("monza") ? query : query + ", Monza";

        api.search(Constants.NOMINATIM_USER_AGENT, q, "json", 5)
                .enqueue(new Callback<List<NominatimResult>>() {
                    @Override
                    public void onResponse(Call<List<NominatimResult>> call,
                                           Response<List<NominatimResult>> response) {
                        List<NominatimResult> body = response.body();
                        if (!response.isSuccessful() || body == null) {
                            callback.onResults(new ArrayList<>());
                            return;
                        }
                        for (NominatimResult r : body) {
                            try {
                                r.km = distanceKm(
                                        Constants.PIZZERIA_LAT, Constants.PIZZERIA_LON,
                                        Double.parseDouble(r.lat),
                                        Double.parseDouble(r.lon));
                            } catch (NumberFormatException e) {
                                r.km = -1;
                            }
                        }
                        callback.onResults(body);
                    }

                    @Override
                    public void onFailure(Call<List<NominatimResult>> call, Throwable t) {
                        callback.onError("Nessun suggerimento");
                    }
                });
    }
}