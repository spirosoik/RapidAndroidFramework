package com.soi.rapidandroidapp.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

public class GsonInstance {

    public static Gson gson;

	public GsonInstance() {
		gson = new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .serializeNulls()
				.registerTypeAdapter(Date.class, new DateTypeAdapter())
				.create();
	}
}