package com.ipiecoles.java.java230;

import com.ipiecoles.java.java230.model.Employe;
import com.ipiecoles.java.java230.model.Manager;
import com.ipiecoles.java.java230.model.Technicien;
import com.ipiecoles.java.java230.repository.EmployeRepository;
import com.ipiecoles.java.java230.repository.ManagerRepository;
import com.ipiecoles.java.java230.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyRunner implements CommandLineRunner {

    //Spring vas automatiquement instancié en employé repository (même si c'est une interface)
    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private TechnicienRepository technicienRepository;

    @Autowired
    private ManagerRepository managerRepository;
    @Override
    public void run(String... strings) throws Exception {
        Optional<Employe> employe = employeRepository.findById(5L);
//        if(employe.isEmpty()){
//              System.out.println("Employé non trouvé");
//        } else {
//            Employe e = employe.get();
//            e.setNom("DAVID");
//            e = employeRepository.save(e);
//            System.out.println(e.toString());
//        }

        if(employe.isEmpty()){System.out.println("Employé non trouvé");}
        else {
            Employe e = employe.get();
            e.setNom("DAVID");
            e = employeRepository.save(e);
            System.out.println(e.toString());
        }



        PageRequest pageRequest = PageRequest.of(0,10, Sort.Direction.ASC, "salaire");
        Page<Employe> page = employeRepository.findByNomIgnoreCase("Andre", pageRequest);
        //Page<Employe> page = employeRepository.findAll(pageRequest);

        System.out.println("Nb éléments : "+page.getTotalElements());
        System.out.println("Nb pages : "+page.getTotalPages());

        for(Employe e : page){
            System.out.println(e.toString());
        }
        System.out.println("Page suivante");
        for(Employe e : employeRepository.findByNomIgnoreCase("Andre", page.nextPageable())){
            System.out.println(e.toString());
        }

//        for(Employe unEmployer : employeRepository.findBySalaireGreaterThanOrderBySalaireDesc(2000d)){
//            System.out.println(unEmployer.toString());
//        }

//        System.out.println(employeRepository.count());
//
//        for(Employe e : employeRepository.findAll())
//            System.out.println(e.toString());


        for(Technicien e : technicienRepository.findAll()){
            System.out.println(e.getManager().toString());
        }
        Manager m = managerRepository.findOneWithEquipeById(11L);
        for(Technicien t : m.getEquipe()){
            System.out.println(m.getMatricule() + " " + t.toString());
        }


    }

    public static void print(Object t) {
        System.out.println(t);
    }
}
