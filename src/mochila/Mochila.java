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
		this.satisfaccionAcumulada = new int[this.articulos.size()][this.pesoSoportado + 1];
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
		//this.mostrarSatisfacciones();
		this.obtenerSatisfaccionMaxima();
		this.grabarSalida();
	}

	/*
	private void mostrarSatisfacciones() {
		for (int i = 0; i < this.articulos.size(); i++) {
			for (int j = 0; j <= this.pesoSoportado; j++) {
				System.out.print(this.satisfaccionAcumulada[i][j] + " ");
			}
			System.out.println();
		}
	}
	*/

	private void acumularSatisfacciones() {
		int satisfaccionSinArticulo, satisfaccionConArticulo;
		for (int j = this.articulos.get(0).getPeso(); j <= this.pesoSoportado; j++) {
			this.satisfaccionAcumulada[0][j] = this.articulos.get(0).getSatisfaccion();
		}
		for (int i = 1; i < this.articulos.size(); i++) {
			for (int j = 1; j <= this.pesoSoportado; j++) {
				satisfaccionSinArticulo = this.satisfaccionAcumulada[i - 1][j];
				if (j < this.articulos.get(i).getPeso()) {
					this.satisfaccionAcumulada[i][j] = satisfaccionSinArticulo;
				} else {
					satisfaccionConArticulo = this.satisfaccionAcumulada[i - 1][j - this.articulos.get(i).getPeso()] + this.articulos.get(i).getSatisfaccion();
					if (satisfaccionSinArticulo >= satisfaccionConArticulo) {
						this.satisfaccionAcumulada[i][j] = satisfaccionSinArticulo;
					} else {
						this.satisfaccionAcumulada[i][j] = satisfaccionConArticulo;
					}
				}
			}
		}
	}

	private void obtenerSatisfaccionMaxima() {
		this.maximaSatisfaccion = this.satisfaccionAcumulada[this.articulos.size() - 1][this.pesoSoportado];
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
