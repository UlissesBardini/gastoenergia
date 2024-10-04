package gastoenergia;

public class UsoDoEquipamento {
    private final Equipamento equipamento;
    private final int tempoDeUso;

    public UsoDoEquipamento(Equipamento equipamento, int tempoDeUso) {
        this.equipamento = equipamento;
        this.tempoDeUso = tempoDeUso;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public int getTempoDeUso() {
        return tempoDeUso;
    }

    public double getConsumo() {
        return (double) this.tempoDeUso /60 * this.equipamento.getPotenciaMediaEmWatts()/1000;
    }

    @Override
    public String toString() {
        return equipamento.getNome() + ": " + tempoDeUso + "min";
    }
}
