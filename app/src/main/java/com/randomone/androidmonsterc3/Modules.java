package com.randomone.androidmonsterc3;


public class Modules {
    private String Title;
    private String Description;
    private int Credits;
    private int Level;
    private String Time;
    private String Pathway;
    private String Prerequisites;
    private String Corequisites;

    public Modules(){

    }

    public Modules(String title, String description, int credits, int level, String time, String pathway, String prerequisites, String corequisites) {
        Title = title;
        Description = description;
        Credits = credits;
        Level = level;
        Time = time;
        Pathway = pathway;
        Prerequisites = prerequisites;
        Corequisites = corequisites;
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

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPathway() {
        return Pathway;
    }

    public void setPathway(String pathway) {
        Pathway = pathway;
    }

    public String getPrerequisites() {
        return Prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        Prerequisites = prerequisites;
    }

    public String getCorequisites() {
        return Corequisites;
    }

    public void setCorequisites(String corequisites) {
        Corequisites = corequisites;
    }

}
