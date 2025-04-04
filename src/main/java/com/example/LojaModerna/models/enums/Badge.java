package com.example.LojaModerna.models.enums;

public enum Badge {
    NOVATO(0.05),
    EXPERIENTE(0.10),
    LIDER(0.15);

    private final double comissao;

    Badge(double comissao) {
        this.comissao = comissao;
    }

    public double getComissao() {
        return comissao;
    }
}
