package com.randomone.androidmonsterc3;


import java.util.Map;

public class Module {
    private String Title;
    private String Description;
    private String Time;
    private int Credits;
    private int Level;
    private Map<String, Boolean> Pathway;
    private Map<String, Boolean> Prerequisites;
    private Map<String, Boolean> Corequisite;

    public Module(){

    }

    public Module(String title, String description, String time, int credits, int level, Map<String, Boolean> pathway, Map<String, Boolean> prerequisites, Map<String, Boolean> corequisite) {
        this.Title = title;
        this.Description = description;
        this.Time = time;
        this.Credits = credits;
        this.Level = level;
        this.Pathway = pathway;
        this.Prerequisites = prerequisites;
        this.Corequisite = corequisite;
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

    public Map<String, Boolean> getPathway() {
        return Pathway;
    }

    public void setPathway(Map<String, Boolean> pathway) {
        this.Pathway = pathway;
    }

    public Map<String, Boolean> getPrerequisites() {
        return Prerequisites;
    }

    public void setPrerequisites(Map<String, Boolean> prerequisites) {
        this.Prerequisites = prerequisites;
    }

    public Map<String, Boolean> getCorequisite() {
        return Corequisite;
    }

    public void setCorequisite(Map<String, Boolean> corequisite) {
        this.Corequisite = corequisite;
    }
}
