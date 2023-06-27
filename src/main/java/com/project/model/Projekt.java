package com.project.model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.model.Chat.ChatMessage;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="projekt") //TODO Indeksować kolumny, które są najczęściej wykorzystywane do wyszukiwania projektów
public class Projekt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="projekt_id") //tylko jeżeli nazwa kolumny w bazie danych ma być inna od nazwy zmiennej
	private Integer projektId;
	
	@NotBlank(message = "Pole nazwa nie może być puste!")
	@Size(min = 3, max = 50, message = "Nazwa musi zawierać od {min} do {max} znaków!")
	@Column(nullable = false, length = 50)
	private String nazwa;
	
	@Column(nullable = true, length = 1000)
	private String opis;
	
	@Column(name = "dataOddania")
	private LocalDate dataOddania;
	
	@CreationTimestamp
	@Column(name = "dataczas_utworzenia", nullable = false)
	private LocalDateTime dataCzasUtworzeniaDateTime;
	
	@UpdateTimestamp
	@Column(name = "dataczas_modyfikacji", nullable = false)
	private LocalDateTime dataCzasModyfikacji;
	
	@OneToMany(mappedBy = "projekt")
	@JsonIgnoreProperties({"projekt"})
	private List<Zadanie> zadaniaList;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "projekt_student",
			joinColumns = {@JoinColumn(name="projekt_id")},
			inverseJoinColumns = {@JoinColumn(name="student_id")})
	@JsonBackReference
	private Set<Student> studenci;

	@OneToMany(mappedBy = "projekt", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Plik> pliki;

	public Projekt(Integer projektId, String nazwa, String opis, LocalDate dataOddania, LocalDateTime dataCzasUtworzeniaDateTime, LocalDateTime dataCzasModyfikacji) {
		this.projektId = projektId;
		this.nazwa = nazwa;
		this.opis = opis;
		this.dataOddania = dataOddania;
		this.dataCzasUtworzeniaDateTime = dataCzasUtworzeniaDateTime;
		this.dataCzasModyfikacji = dataCzasModyfikacji;
	}



	public List<Plik> getPliki() {
		return pliki;
	}

	public void setPliki(List<Plik> pliki) {
		this.pliki = pliki;
	}

	public Projekt() {
		super();
	}

	public Projekt(String nazwa, String opis) {
		super();
		this.nazwa = nazwa;
		this.opis = opis;
	}

	
	public Integer getProjektId() {
		return projektId;
	}

	public void setProjektId(Integer projektId) {
		this.projektId = projektId;
	}

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

	public LocalDate getDataOddania() {
		return dataOddania;
	}

	public void setDataOddania(LocalDate dataOddania) {
		this.dataOddania = dataOddania;
	}

	public LocalDateTime getDataCzasUtworzeniaDateTime() {
		return dataCzasUtworzeniaDateTime;
	}

	public void setDataCzasUtworzeniaDateTime(LocalDateTime dataCzasUtworzeniaDateTime) {
		this.dataCzasUtworzeniaDateTime = dataCzasUtworzeniaDateTime;
	}

	public LocalDateTime getDataCzasModyfikacji() {
		return dataCzasModyfikacji;
	}

	public void setDataCzasModyfikacji(LocalDateTime dataCzasModyfikacji) {
		this.dataCzasModyfikacji = dataCzasModyfikacji;
	}

	public List<Zadanie> getZadaniaList() {
		return zadaniaList;
	}

	public void setZadaniaList(List<Zadanie> zadaniaList) {
		this.zadaniaList = zadaniaList;
	}

	public Set<Student> getStudenci() {
		return studenci;
	}

	public void setStudenci(Set<Student> studenci) {
		this.studenci = studenci;
	}

	
	
	
	
	
}