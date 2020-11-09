package com.ipiecoles.java.java230.repository;

import com.ipiecoles.java.java230.model.Employe;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeRepository extends PagingAndSortingRepository<Employe, Long> {

    //En interne spring data va faire : SELECT * FROM emlpoye WHERE matricule = ?; avec ? la valeur en paramètre
    Employe findByMatricule(String matricule);
    //FindBy nom et prénom
    List<Employe> findByNomAndPrenom(String nom, String prenom);

    //Find nom sans prendre en compte la casse (le nom peut être en minuscule, majuscule, cela retournera la même chose)
    List<Employe> findByNomIgnoreCase(String nom);

    //Find by dataEmbauche avant une certaines date
    List<Employe> findByDateEmbaucheBefore(LocalDate dateEmbauche);

    //Find by dateEmbauche apès une certaines date
    List<Employe> findByDateEmbaucheAfter(LocalDate dateEmbauche);

    List<Employe> findBySalaireGreaterThanOrderBySalaireDesc(Double salaire);

    //@Query(value = "SELECT * FROM employe WHERE employe.nom = :nomOuPrenom OR employe.prenom = :nomOuPrenom",nativeQuery = true)
    //The lower in the sql request is to parse the text to lowercase, cause in the database the 'nom' and 'prenom' are store in lowercase
    //@Query(value= "select e from Employe e where lower(e.prenom) = lower(:nomOuPrenom) or lower(e.nom) = lower(:nomOuPrenom)",nativeQuery = true) //Need to validate the unit test
    //List<Employe> findByNomOrPrenomAllIgnoreCase(@Param("nomOuPrenom") String nomOuPrenom);
    //Here no need to add the nativeQuery to true because we use the Employe java class not the table in the database
    @Query("select e from Employe e where lower(e.prenom) = lower(:nomOuPrenom) or lower(e.nom) = lower(:nomOuPrenom)") //Need to validate the unit test
    List<Employe> findByNomOrPrenomAllIgnoreCase(@Param("nomOuPrenom") String nomOuPrenom);

//    @Query(value = "SELECT * FROM employe WHERE nom = ? OR prenom = ?")
//    Page<Employe> findByNomOrPrenomAllIgnoreCase(@Param("nom") String nom, @Param("prenom") String prenom, Pageable pageable);

    //Cause the sql request use a 'sous requete' we have to set the nativeQuery var to true
    //@Query(value = "SELECT * FROM employe WHERE salaire > (SELECT AVG(salaire) FROM employe)", nativeQuery = true)
    @Query(value = "SELECT * FROM Employe WHERE salaire > (SELECT avg(e2.salaire) FROM Employe e2)", nativeQuery = true)
    List<Employe> findEmployePlusRiches();

    Page<Employe> findByNomIgnoreCase(String nom, Pageable pageable);
}
