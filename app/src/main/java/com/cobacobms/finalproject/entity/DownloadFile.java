package com.cobacobms.finalproject.entity;

public class DownloadFile {
    String Name;
    String Extension;
    String Path;
    String Size;

    public DownloadFile(String name, String extension, String path, String size) {
        Name = name;
        Extension = extension;
        Path = path;
        Size = size;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getExtension() {
        return Extension;
    }

    public void setExtension(String extension) {
        Extension = extension;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }
}
