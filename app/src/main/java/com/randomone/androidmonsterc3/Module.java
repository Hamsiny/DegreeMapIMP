package com.randomone.androidmonsterc3;


public class Module {
    private String Title;
    private String Description;
    private int Credits;
    private int Level;
    private String Time;
    private Pathway Pathway;
    private Prerequisite Prerequisites;
    private Corequisite Corequisites;

    public Module(){

    }

    public Module(String title, String description, int credits, int level, String time, Pathway pathway, Prerequisite prerequisites, Corequisite corequisites) {
        Title = title;
        Description = description;
        Credits = credits;
        Level = level;
        Time = time;
        Pathway = pathway;
        Prerequisites = prerequisites;
        Corequisites = corequisites;
    }

    public class Pathway {

    }

    public class Prerequisite {

    }

    public class Corequisite {

    }

}
