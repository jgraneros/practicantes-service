package org.pweb.domain;

public enum Mes {

    INSCRIPCION(0),
    ENERO(1),
    FEBRERO(2),
    MARZO(3),
    ABRIL(4),
    MAYO(5),
    JUNIO(6),
    JULIO(7),
    AGOSTO(8),
    SEPTIEMBRE(9),
    OCTUBRE(10),
    NOVIEMBRE(11),
    DICIEMBRE(12);

    private final int numero;

    Mes(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }


}
