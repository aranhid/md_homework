package com.aranhid.azuretranslator;

import androidx.annotation.NonNull;

public class Language {
    String shortName;
    String name;
    String nativeName;

    public Language(String shortName, Language language){
        this.shortName = shortName;
        this.name = language.name;
        this.nativeName = language.nativeName;
    }

    @NonNull
    @Override
    public String toString() {
        return nativeName;
    }
}
