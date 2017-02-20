package tp;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

public class MaximizarProduccion {
	
	private static int MAX_EVOLUCIONES = 100; //ajustar 
	private static int MAX_CANT_PRODUCTO = 100; //ajustar 
	private final static int TAM_POBLACION = 10; //ajustar	
	private final static int HS_PROD1 = 6;
	private final static int HS_PROD2 = 8;
	private final static int HS_PROD3 = 3;
	private final static int UNI_PROD1 = 4;
	private final static int UNI_PROD2 = 2;
	private final static int UNI_PROD3 = 6;
	private final static int GAN_PROD1 = 7;
	private final static int GAN_PROD2 = 5;
	private final static int GAN_PROD3 = 6;	
	private int GAN_MAX = 700;
	
	public static int horasDisponibles;
	public static int materiaPrimaDisponible;
	
	
	private static void maximizar(int horas, int unidadesMP ) throws InvalidConfigurationException {
		
		Configuration configuracion = new DefaultConfiguration();
		configuracion.setPreservFittestIndividual(true);
		
		//creacion y seteo de la funcion de aptitud
		FitnessFunction funcAptitud = new FuncionDeAptitud(horas,unidadesMP);
		configuracion.setFitnessFunction(funcAptitud);
		
		//definicion de los genes
		Gene[] genes = new Gene[3];
		genes[0] = new IntegerGene(configuracion,1,MAX_CANT_PRODUCTO);
		genes[1] = new IntegerGene(configuracion,1,MAX_CANT_PRODUCTO);
		genes[2] = new IntegerGene(configuracion,1,MAX_CANT_PRODUCTO);
		
		//creacion y seteo del cromosoma
		IChromosome cromosoma = new Chromosome(configuracion, genes);
		configuracion.setSampleChromosome(cromosoma);		
		configuracion.setPopulationSize(TAM_POBLACION);
		
		Genotype Poblacion;		
		Poblacion = Genotype.randomInitialGenotype(configuracion);		
		
		long tInicio = System.currentTimeMillis();
		for (int contGeneracion= 0; contGeneracion< MAX_EVOLUCIONES; contGeneracion++) {
			Poblacion.evolve();	
			IChromosome mejorCromosomaActual = Poblacion.getFittestChromosome();
			salidaPorPantallaParcial(mejorCromosomaActual);
			
			
		}
		long tFin = System.currentTimeMillis();
		
		IChromosome cromosomaMasApto = Poblacion.getFittestChromosome();
		salidaFinalPorPantalla(cromosomaMasApto,tFin-tInicio);		
	}

	private static void salidaPorPantallaParcial(IChromosome cromosomaMasApto) {
		
		int cantProd1;
		int cantProd2;
		int cantProd3;
		cantProd1 = (int) cromosomaMasApto.getGene(0).getAllele();
		cantProd2 = (int) cromosomaMasApto.getGene(1).getAllele();
		cantProd3 = (int) cromosomaMasApto.getGene(2).getAllele();
		
		
		System.out.println("Aptitud del cromosoma mas apto: " + cromosomaMasApto.getFitnessValue());
		System.out.println("Horas de produccion necesarias: "+ (cantProd1*HS_PROD1 + cantProd2*HS_PROD2 + cantProd3*HS_PROD3));
		System.out.println("Materia Prima necesaria: "+ (cantProd1*UNI_PROD1 + cantProd2*UNI_PROD2 + cantProd3*UNI_PROD3));
		System.out.println("Unidades del producto 1: " + cantProd1 + " Ganancia: $" + cantProd1*GAN_PROD1);
		System.out.println("Unidades del producto 2: " + cantProd2 + " Ganancia: $" + cantProd2*GAN_PROD2);
		System.out.println("Unidades del producto 3: " + cantProd3 + " Ganancia: $" + cantProd3*GAN_PROD3);
		System.out.println("Ganancia total: $" + (cantProd1*GAN_PROD1 + cantProd3*GAN_PROD3 + cantProd3*GAN_PROD3));
		System.out.println(" ");
		
	}

	private static void salidaFinalPorPantalla(IChromosome cromosomaMasApto, long tiempo) {
		int cantProd1;
		int cantProd2;
		int cantProd3;
		cantProd1 = (int) cromosomaMasApto.getGene(0).getAllele();
		cantProd2 = (int) cromosomaMasApto.getGene(1).getAllele();
		cantProd3 = (int) cromosomaMasApto.getGene(2).getAllele();
		
		System.out.println("Tiempo total de evolucion: " + tiempo + "ms");
		System.out.println("Aptitud del cromosoma mas apto: " + cromosomaMasApto.getFitnessValue());
		System.out.println("Horas de produccion necesarias: "+ (cantProd1*HS_PROD1 + cantProd2*HS_PROD2 + cantProd3*HS_PROD3));
		System.out.println("Materia Prima necesaria: "+ (cantProd1*UNI_PROD1 + cantProd2*UNI_PROD2 + cantProd3*UNI_PROD3));
		System.out.println("Unidades del producto 1: " + cantProd1 + " Ganancia: $" + cantProd1*GAN_PROD1);
		System.out.println("Unidades del producto 2: " + cantProd2 + " Ganancia: $" + cantProd2*GAN_PROD2);
		System.out.println("Unidades del producto 3: " + cantProd3 + " Ganancia: $" + cantProd3*GAN_PROD3);
		System.out.println("Ganancia total: $" + (cantProd1*GAN_PROD1 + cantProd3*GAN_PROD3 + cantProd3*GAN_PROD3));
	}	
	
	public static void main (String[] valores) throws InvalidConfigurationException{		
		
		try{
			horasDisponibles = Integer.parseInt(valores[0]);
			materiaPrimaDisponible = Integer.parseInt(valores[1]);
		} catch (NumberFormatException e) {
			System.out.println("argumentos invalidos");			
			System.exit(1);			
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("argumentos invalidos");			
			System.exit(1);
		}		
	
		if (horasDisponibles <= 0 || materiaPrimaDisponible <=0){
			System.out.println("valores invalidos");			
			System.exit(1);
		}
		maximizar(horasDisponibles,materiaPrimaDisponible);
	}	
}
