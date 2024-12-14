package org.pweb.domain.enums;

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

    public static Mes obtenerMesPorNumero(int numero) {
        for (Mes mes: Mes.values()) {
            if (mes.getNumero() == numero) {
                return mes;
            }
        }

        throw new IllegalArgumentException("Número de mes inválido: " + numero);
    }

}
