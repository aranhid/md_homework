package com.aranhid.azuretranslator;

import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

public class LanguagesResponse {
    Map<String, Language> translation;

    @Override
    public String toString() {
        String  languages = "";
        for (String l: translation.keySet()) {
            languages += l + ":";
        }
        return languages;
    }

    public ArrayList<Language> getLanguagesList() {
        ArrayList<Language> languages = new ArrayList<>();
        for (String key : translation.keySet())
        {
            languages.add(new Language(key, translation.get(key)));
        }
        return languages;
    }

    public ArrayList<String> getNativeLanguages() {
        ArrayList<String> nativeLanguages = new ArrayList<>();
        for (Language language : getLanguagesList()) {
            nativeLanguages.add(language.nativeName);
        }
        return nativeLanguages;
    }
}
