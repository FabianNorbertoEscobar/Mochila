package mochila;

public class Articulo {

	private int numero;
	private int peso;
	private int satisfaccion;

	public Articulo(int numero, int peso, int satisfaccion) {
		this.numero = numero;
		this.peso = peso;
		this.satisfaccion = satisfaccion;
	}

	public int getNumero() {
		return numero;
	}

	public int getPeso() {
		return peso;
	}

	public int getSatisfaccion() {
		return satisfaccion;
	}

}
