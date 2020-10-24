package com.randomone.androidmonsterc3;


import java.util.List;
import java.util.Map;

public class Module {

    private String Code;
    private String Title;
    private String Description;
    private String Time;
    private int Credits;
    private int Level;
    private List<String> Pathway;
    private List<String> Prerequisites;
    private List<String> Corequisite;

    public Module(){

    }

    public Module(String code, String title, String description, String time, int credits, int level, List<String> pathway, List<String> prerequisites, List<String> corequisite) {
        this.Code = code;
        this.Title = title;
        this.Description = description;
        this.Time = time;
        this.Credits = credits;
        this.Level = level;
        this.Pathway = pathway;
        this.Prerequisites = prerequisites;
        this.Corequisite = corequisite;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }

    public int getCredits() {
        return Credits;
    }

    public void setCredits(int credits) {
        this.Credits = credits;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        this.Level = level;
    }

    public List<String> getPathway() {
        return Pathway;
    }

    public void setPathway(List<String> pathway) {
        this.Pathway = pathway;
    }

    public List<String> getPrerequisites() {
        return Prerequisites;
    }

    public void setPrerequisites(List<String> prerequisites) {
        this.Prerequisites = prerequisites;
    }

    public List<String> getCorequisite() {
        return Corequisite;
    }

    public void setCorequisite(List<String> corequisite) {
        this.Corequisite = corequisite;
    }
}
