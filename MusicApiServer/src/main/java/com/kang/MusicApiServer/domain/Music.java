package com.kang.MusicApiServer.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Music {

	@SerializedName("results")
	@Expose
	private Result results;

	public Result getResults() {
		return results;
	}

	public void setResults(Result results) {
		this.results = results;
	}

}