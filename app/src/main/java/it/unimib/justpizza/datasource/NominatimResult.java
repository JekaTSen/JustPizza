package it.unimib.justpizza.datasource;

import com.google.gson.annotations.SerializedName;

public class NominatimResult {

    public double km;
    @SerializedName("lat")
    public String lat;

    @SerializedName("lon")
    public String lon;

    @SerializedName("display_name")
    public String displayName;
}