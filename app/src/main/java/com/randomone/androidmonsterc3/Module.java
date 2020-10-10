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
        Title = title;
        Description = description;
        Time = time;
        Credits = credits;
        Level = level;
        Pathway = pathway;
        Prerequisite = prerequisite;
        Corequisite = corequisite;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public int getCredits() {
        return Credits;
    }

    public void setCredits(int credits) {
        Credits = credits;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public Map<String, Boolean> getPathway() {
        return Pathway;
    }

    public void setPathway(Map<String, Boolean> pathway) {
        Pathway = pathway;
    }

    public Map<String, Boolean> getPrerequisite() {
        return Prerequisite;
    }

    public void setPrerequisite(Map<String, Boolean> prerequisite) {
        Prerequisite = prerequisite;
    }

    public Map<String, Boolean> getCorequisite() {
        return Corequisite;
    }

    public void setCorequisite(Map<String, Boolean> corequisite) {
        Corequisite = corequisite;
    }
}
