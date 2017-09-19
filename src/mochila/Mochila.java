package mochila;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Mochila extends EjercicioOIA {

	private ArrayList<Articulo> articulos;
	private int[][] satisfaccionAcumulada;
	private int pesoSoportado;
	private int maximaSatisfaccion;

	public Mochila(File entrada, File salida) {
		super(entrada, salida);
		this.articulos = new ArrayList<Articulo>();
		this.levantarEntrada();
		this.satisfaccionAcumulada = new int[this.articulos.size()][this.pesoSoportado];
	}

	private void levantarEntrada() {
		try {
			Scanner scan = new Scanner(new FileReader(this.entrada));
			int cantidadArticulos = scan.nextInt();
			int numero, peso, satisfaccion;
			for (int i = 0; i < cantidadArticulos; i++) {
				numero = scan.nextInt();
				peso = scan.nextInt();
				satisfaccion = scan.nextInt();
				this.articulos.add(new Articulo(numero, peso, satisfaccion));
			}
			this.pesoSoportado = scan.nextInt();
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error al levantar el archivo de entrada");
			e.printStackTrace();
		}
	}

	@Override
	public void resolver() {
		this.acumularSatisfacciones();
		this.mostrarSatisfacciones();
		this.obtenerSatisfaccionMaxima();
		this.grabarSalida();
	}

	private void mostrarSatisfacciones() {
		for (int i = 0; i < this.articulos.size(); i++) {
			for (int j = 0; j < this.pesoSoportado; j++) {
				System.out.print(this.satisfaccionAcumulada[i][j] + " ");
			}
			System.out.println();
		}
	}

	private void acumularSatisfacciones() {
		int anterior, siguiente;
		for (int i = 0; i < this.articulos.size(); i++) {
			for (int j = 1; j < this.pesoSoportado; j++) {
				if (i > 0 && j - this.articulos.get(i).getPeso() > 0) {
					anterior = this.satisfaccionAcumulada[i - 1][j];
					siguiente = this.satisfaccionAcumulada[i - 1][j - this.articulos.get(i).getPeso()]
							+ this.articulos.get(i).getSatisfaccion();
					this.satisfaccionAcumulada[i][j] = this.maximo(anterior, siguiente);
				} else {
					this.satisfaccionAcumulada[i][j] = this.satisfaccionAcumulada[i][j - 1];
				}
			}
		}
	}

	private int maximo(int a, int b) {
		if (a > b) {
			return a;
		} else {
			return b;
		}
	}

	private void obtenerSatisfaccionMaxima() {
		this.maximaSatisfaccion = this.satisfaccionAcumulada[this.articulos.size() - 1][this.pesoSoportado - 1];
	}

	private void grabarSalida() {
		try {
			BufferedWriter buffer = new BufferedWriter(new FileWriter(this.salida));
			buffer.write(this.pesoSoportado + " " + this.maximaSatisfaccion);
			buffer.close();
		} catch (IOException e) {
			System.out.println("Error al grabar el archivo de salida");
			e.printStackTrace();
		}
	}

}
