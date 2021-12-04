package com.example.demo.enitity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "developer")
public class DeveloperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long phoneNumber;

    private Date lastCalled;

    public Date getLastCalled() {
        return lastCalled;
    }

    public void setLastCalled(Date lastCalled) {
        this.lastCalled = lastCalled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public TeamEntity getTeamId() {
        return teamId;
    }

    public void setTeamId(TeamEntity teamId) {
        this.teamId = teamId;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    private TeamEntity teamId;
}
