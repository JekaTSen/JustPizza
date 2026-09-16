package it.unimib.justpizza.datasource;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface NominatimApi {

    @GET("search")
    Call<List<NominatimResult>> search(
            @Header("User-Agent") String userAgent,
            @Query("q") String query,
            @Query("format") String format,
            @Query("limit") int limit
    );
}