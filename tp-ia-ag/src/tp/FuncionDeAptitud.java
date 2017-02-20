package tp;

import org.jgap.*;

public class FuncionDeAptitud extends FitnessFunction{
	
	private static final long serialVersionUID = 1L;
	
	private final int HS_PROD1 = 6;
	private final int HS_PROD2 = 8;
	private final int HS_PROD3 = 3;
	private final int UNI_PROD1 = 4;
	private final int UNI_PROD2 = 2;
	private final int UNI_PROD3 = 6;
	private final int GAN_PROD1 = 7;
	private final int GAN_PROD2 = 5;
	private final int GAN_PROD3 = 6;
	private final int HS_TOTALES = 500;
	private final int UNI_TOTALES = 400;
	private final int GAN_MAX = 700;	

	
	public FuncionDeAptitud (int horas, int materiaPrima){		
		if (HS_TOTALES < horas){
			throw new IllegalArgumentException("Error se dispone de un maximo de " + HS_TOTALES + " horas de produccion" );
		}
		if (UNI_TOTALES < materiaPrima){
			throw new IllegalArgumentException("Error se dispone de un maximo de " + UNI_TOTALES + " unidades de materia prima" );
		}						
	}
	
	/*
	public float aptitud() {
		int hsLibres = HS_TOTALES - ((prod1*HS_PROD1)+(prod2*HS_PROD2)+(prod3*HS_PROD3));
		int uniLibres = UNI_TOTALES - ((prod1*UNI_PROD1)+(prod2*UNI_PROD2)+(prod3*UNI_PROD3));
		int ganancia = prod1*GAN_PROD1 + prod2*GAN_PROD2 + prod3*GAN_PROD3;
		return 40*(1-(hsLibres/HS_TOTALES)) + 40* (1-(uniLibres/UNI_TOTALES)) + 20* (ganancia/GAN_MAX); //TODO 
	}
	*/
	@Override
	protected double evaluate(IChromosome cromosoma) {
		//calcula y devuelve la aptitud de cada cromosoma		
		
		int hsLibres = this.calcularHsLibres(cromosoma);
		int uniLibres = this.calcularUniLibres(cromosoma);
		int ganancia = this.calcularGanancia(cromosoma);
		
				
		//verifico que no se empleen mas horas libres o unidades de materia prima que las que estan disponibles
		if (hsLibres < 0 || uniLibres < 0) {
			return 0;
		}		
		
	//	System.out.println("Datos del Cromosoma prod1: " + (int) cromosoma.getGene(0).getAllele() + " prod 2: " + (int) cromosoma.getGene(1).getAllele() + " prod 3: " + (int) cromosoma.getGene(2).getAllele());
		
		//TODO revisar esto 
		//return (40*(1-(hsLibres/HS_TOTALES)) + 40* (1-(uniLibres/UNI_TOTALES)) + 20* (ganancia/GAN_MAX));
		//return ganancia/HS_TOTALES;
		return ganancia-(UNI_TOTALES-uniLibres)/HS_TOTALES;
	}
	
	private int calcularGanancia(IChromosome cromosoma) {
		return (int) cromosoma.getGene(0).getAllele()*GAN_PROD1 + (int) cromosoma.getGene(1).getAllele()*GAN_PROD2 + (int) cromosoma.getGene(2).getAllele()*GAN_PROD3;
	}

	private int calcularUniLibres(IChromosome cromosoma) {
		int mp = UNI_TOTALES - ( (int) cromosoma.getGene(0).getAllele()*UNI_PROD1 + (int) cromosoma.getGene(1).getAllele()*UNI_PROD2 + (int) cromosoma.getGene(2).getAllele()*UNI_PROD3 );
		return mp;
	}

	private int calcularHsLibres(IChromosome cromosoma) {
		int horas = HS_TOTALES - ( (int) cromosoma.getGene(0).getAllele()*HS_PROD1 + (int) cromosoma.getGene(1).getAllele()*HS_PROD2 + (int) cromosoma.getGene(2).getAllele()*HS_PROD3 );;
		return horas;
	}
	
	
}
