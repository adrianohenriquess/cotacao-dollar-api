package com.github.adrianoss.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Root {
	
	@JsonProperty("@odata.context")
	String odataContext;
	
	@JsonProperty("value")
	ArrayList<Value> value;
	
}
