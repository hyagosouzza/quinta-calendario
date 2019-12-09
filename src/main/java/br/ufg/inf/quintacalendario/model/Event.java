package br.ufg.inf.quintacalendario.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "event")
public class Event {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date initialDate;
    private Date finalDate;
    private String description;
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category")
    private Category category;

    /**
     * Foreign Key Attribute for Regional Table
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "regional_event", joinColumns = {@JoinColumn(name = "event")}, inverseJoinColumns = {@JoinColumn(name = "regional")})
    private List<Regional> regionals;

    /**
     * Foreign Key Attribute for Institute Table
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "institute_event", joinColumns = {@JoinColumn(name = "event")}, inverseJoinColumns = {@JoinColumn(name = "institute")})
    private List<Institute> institutes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Regional> getRegionals() {
        return regionals;
    }

    public void setRegionals(List<Regional> regionals) {
        this.regionals = regionals;
    }

    public List<Institute> getInstitutes() {
        return institutes;
    }

    public void setInstitutes(List<Institute> institutes) {
        this.institutes = institutes;
    }
}
