
package com.kang.MusicApiServer.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

	@SerializedName("opensearch:Query")
	@Expose
	private OpensearchQuery opensearchQuery;
	@SerializedName("opensearch:totalResults")
	@Expose
	private String opensearchTotalResults;
	@SerializedName("opensearch:startIndex")
	@Expose
	private String opensearchStartIndex;
	@SerializedName("opensearch:itemsPerPage")
	@Expose
	private String opensearchItemsPerPage;
	@SerializedName("trackmatches")
	@Expose
	private Trackmatches trackmatches;


	public OpensearchQuery getOpensearchQuery() {
		return opensearchQuery;
	}

	public void setOpensearchQuery(OpensearchQuery opensearchQuery) {
		this.opensearchQuery = opensearchQuery;
	}

	public String getOpensearchTotalResults() {
		return opensearchTotalResults;
	}

	public void setOpensearchTotalResults(String opensearchTotalResults) {
		this.opensearchTotalResults = opensearchTotalResults;
	}

	public String getOpensearchStartIndex() {
		return opensearchStartIndex;
	}

	public void setOpensearchStartIndex(String opensearchStartIndex) {
		this.opensearchStartIndex = opensearchStartIndex;
	}

	public String getOpensearchItemsPerPage() {
		return opensearchItemsPerPage;
	}

	public void setOpensearchItemsPerPage(String opensearchItemsPerPage) {
		this.opensearchItemsPerPage = opensearchItemsPerPage;
	}

	public Trackmatches getTrackmatches() {
		return trackmatches;
	}

	public void setTrackmatches(Trackmatches trackmatches) {
		this.trackmatches = trackmatches;
	}



}