package model;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileType {

	private final StringProperty name;
	private final StringProperty extPref;
	private final StringProperty path;
	private final ObservableList<String> supportedExt = FXCollections.observableList(new ArrayList<String>());
	
	public FileType(String name, String extPref, String[] ext) {
		this.name = new SimpleStringProperty(name);
		this.extPref = new SimpleStringProperty(extPref);
		this.supportedExt.addAll(ext);
		this.path = new SimpleStringProperty("");
	}
	
	public FileType(String name, String extPref) {
		this(name,extPref,null);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}
	
	public StringProperty nameProperty() {
        return this.name;
    }
	
	public void setSupportedExt(String ext) {
		this.supportedExt.add(ext);
	}
	
	public ObservableList<String> supportedExtProperty() {
		return this.supportedExt;
	}
	
	public String getExtSupported() {
		String res = "";
		for (String ext : this.supportedExt) { res += ext + " "; }
		return res;
	}
	
	public void setExtSupported(String ext) {
		this.supportedExt.add(ext);
	}
	
	public String getExtPref() {
		return extPref.get();
	}

	public void setExtPref(String extPref) {
		this.extPref.set(extPref);
	}
	
	public StringProperty extPrefProperty() {
        return this.extPref;
    }
	
	public StringProperty pathProperty() {
		return this.path;
	}
	
	public String getPath() {
		return path.get();
	}
	
	public void setPath(String path) {
		this.path.set(path);
	}
}
