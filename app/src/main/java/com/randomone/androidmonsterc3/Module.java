package com.randomone.androidmonsterc3;


import java.util.Map;

public class Module {
    public String Title;
    public String Description;
    public String Time;
    public int Credits;
    public int Level;
    public Map<String, Boolean> Pathway;
    public Map<String, Boolean> Prerequisite;
    public Map<String, Boolean> Corequisite;

    public Module(){

    }

    public Module(String title, String description, String time, int credits, int level, Map<String, Boolean> pathway, Map<String, Boolean> prerequisite, Map<String, Boolean> corequisite) {
        this.Title = title;
        this.Description = description;
        this.Time = time;
        this.Credits = credits;
        this.Level = level;
        this.Pathway = pathway;
        this.Prerequisite = prerequisite;
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

    public Map<String, Boolean> getPrerequisite() {
        return Prerequisite;
    }

    public void setPrerequisite(Map<String, Boolean> prerequisite) {
        this.Prerequisite = prerequisite;
    }

    public Map<String, Boolean> getCorequisite() {
        return Corequisite;
    }

    public void setCorequisite(Map<String, Boolean> corequisite) {
        this.Corequisite = corequisite;
    }
}
