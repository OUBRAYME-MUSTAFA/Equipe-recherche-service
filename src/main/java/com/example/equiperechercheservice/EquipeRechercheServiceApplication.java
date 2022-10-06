package com.example.equiperechercheservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class EquipeRechercheServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext =
				SpringApplication.run(EquipeRechercheServiceApplication.class, args);

//        EquipeRepository equipeRepository =
//                configurableApplicationContext.getBean(EquipeRepository.class);
//        AxeRepository axeRepository =
//                configurableApplicationContext.getBean(AxeRepository.class);
//
//
//////            Collection<Axe> col = new HashSet<Axe>();
//        Axe axe_1 = new Axe("axe_111" );
//        Axe axe_2 = new Axe("axe_222");
//        Axe axe_3 = new Axe("axe_333");
//        List<Axe> ddd = Arrays.asList(axe_1, axe_2, axe_3);
//
//////            col.add(axe_1);
//////            col.add(axe_2);
//////
//        Equipe ep1 = new Equipe( "acro1", "nom1", "rep1");
//        Equipe ep2 = new Equipe( "acro2", "nom2", "rep2") ;
//        List<Equipe> ccc = Arrays.asList(ep1, ep2);
//        equipeRepository.saveAll(ccc);
//
//        axe_1.addEquipe(ep1);
//        axe_2.addEquipe(ep1);
//        axe_3.addEquipe(ep2);
//        axeRepository.saveAll(ddd);
//
//
////            //Equipe ep3 = new Equipe(null, "acro3", "nom2","rep3",null);
////
////            equipeRepository.save(ep1);
////            equipeRepository.save(ep2);
////            //equipeRepository.save(ep3);
////            axeRepository.save(new Axe(null, "axe_1" ,null));
////            axeRepository.save(new Axe(null, "axe_2",null));
////            axeRepository.save(new Axe(null, "axe_3",null));
////            axeRepository.save(new Axe(null, "axe_4",null));
////
////            Axe axe = new Axe(null, "axe_6",ep1);
////            //axe.setEquipe(ep1);
////          axeRepository.save(axe);
//////            equipeRepository.findAll().forEach(c -> {
//////                System.out.println(c.toString());
//////
//////            });
//
//
	}


}


