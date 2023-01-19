package com.css.common.http;

public class HttpParameters {

	private String name;
	private Object object;
	
	public HttpParameters(String name, Object object){
		this.name=name;
		this.object=object;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	
	
}
