package com.company;

import java.util.Date;


class Sport {
    private final String section;
    private final String subsection;
    private final String title;
    private final String description;
    private final String start;
    private final String stop;
    private final String address;
    private final int participants;

    public Sport(String section, String subsection, String title, String description, String start, String stop, String address, int participants) {
        this.section = section;
        this.subsection = subsection;
        this.title = title;
        this.description = description;
        this.start = start;
        this.stop = stop;
        this.address = address;
        this.participants = participants;
    }


    public String getSection() {
        return section;
    }

    public String getSubsection() {
        return subsection;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStart() {
        return start;
    }

    public String getStop() {
        return stop;
    }

    public String getAddress() {
        return address;
    }

    public int getParticipants() {
        return participants;
    }
}
