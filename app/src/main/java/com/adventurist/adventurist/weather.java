package com.adventurist.adventurist;

import com.google.gson.annotations.SerializedName;

public class weather {
    @SerializedName("main")
    mainWeatherClass main;

    public mainWeatherClass getMain() {
        return main;
    }

    public void setMain(mainWeatherClass main) {
        this.main = main;
    }
}
