package com.project.model;

import java.util.Set;

public class ProjektDTO {
    private String nazwa;
    private String opis;
    private Set<Integer> studentIds;

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Set<Integer> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(Set<Integer> studentIds) {
        this.studentIds = studentIds;
    }
}
