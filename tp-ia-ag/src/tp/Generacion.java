package tp;

import java.util.ArrayList;

public class Generacion {
	
	private ArrayList<FuncionDeAptitud> poblacion;
	
	public Generacion(ArrayList<FuncionDeAptitud> funcionDeAptituds) {
		this.poblacion = funcionDeAptituds;
	}

	public float promedioAptitud() {
		float suma = 0;
		int totIndividuos = 0;
		for (FuncionDeAptitud funcionDeAptitud : poblacion) {
		//	suma = suma + funcionDeAptitud.aptitud();
			totIndividuos++;
		}
		return suma/totIndividuos;
	}
}
