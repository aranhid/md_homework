package com.aranhid.azuredictors;

import androidx.annotation.NonNull;

public class Dictor {
    String Name;
    String ShortName;
    String Gender;
    String Locale;
    String SampleRateHertz;
    String VoiceType;

    @NonNull
    @Override
    public String toString() {
        return Name + " " + Gender + " " + Locale;
    }
}
