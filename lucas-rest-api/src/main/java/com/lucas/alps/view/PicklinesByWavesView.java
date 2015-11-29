package com.lucas.alps.view;

import org.apache.commons.collections.MultiMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.PicklinesByWavesDetailsView;

public class PicklinesByWavesView {

	private MultiMap pickLinesByWave;

	public PicklinesByWavesView() {

	}

	public PicklinesByWavesView(MultiMap pickLinesByWave) {
		this.pickLinesByWave = pickLinesByWave;
	}

	@JsonView(PicklinesByWavesDetailsView.class)
	@JsonProperty("chart")
	public MultiMap getPickLinesByWave() {
		return pickLinesByWave;
	}

	public void setPickLinesByWave(MultiMap pickLinesByWave) {
		this.pickLinesByWave = pickLinesByWave;
	}

	
}
