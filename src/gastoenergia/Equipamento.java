package gastoenergia;

import java.util.Arrays;
import java.util.List;

public class Equipamento {

    private final String nome;
    private final int potenciaMediaEmWatts;
    private boolean isEssencial;

    public Equipamento(String nome, int potenciaMediaEmWatts) {
        this.nome = nome;
        this.potenciaMediaEmWatts = potenciaMediaEmWatts;
    }

    public Equipamento(String nome, int potenciaMediaEmWatts, boolean isEssencial) {
        this(nome, potenciaMediaEmWatts);
        this.isEssencial = isEssencial;
    }

    public int getPotenciaMediaEmWatts() {
        return potenciaMediaEmWatts;
    }

    public String getNome() {
        return nome;
    }

    public boolean isEssencial() {
        return isEssencial;
    }

    @Override
    public String toString() {
        return nome;
    }

    public static final Equipamento CHUVEIRO_ELETRICO = new Equipamento("CHUVEIRO ELÉTRICO", 3500);

    public static final List<Equipamento> EQUIPAMENTOS = Arrays.asList(
            new Equipamento("ABRIDOR / AFIADOR", 135),
            new Equipamento("AFIADOR DE FACAS", 20),
            new Equipamento("APARELHO DE SOM 3 EM 1", 80),
            new Equipamento("APARELHO DE SOM PEQUENO", 20),
            new Equipamento("AQUECEDOR DE AMBIENTE", 1550),
            new Equipamento("AQUECEDOR DE MAMADEIRA", 100),
            new Equipamento("AR-CONDICIONADO 7.500 BTU", 1000),
            new Equipamento("AR-CONDICIONADO 10.000 BTU", 1350),
            new Equipamento("AR-CONDICIONADO 12.000 BTU", 1450),
            new Equipamento("AR-CONDICIONADO 15.000 BTU", 2000),
            new Equipamento("AR-CONDICIONADO 18.000 BTU", 2100),
            new Equipamento("ASPIRADOR DE PÓ", 100),
            new Equipamento("BARBEADOR / DEPILADOR / MASSAGEADOR", 10),
            new Equipamento("BATEDEIRA", 120),
            new Equipamento("BOILER 50 e 60 L", 1500),
            new Equipamento("BOILER 100 L", 2030),
            new Equipamento("BOILER 200 a 500 L", 3000),
            new Equipamento("BOMBA D'ÁGUA 1 / 4 CV", 335, true),
            new Equipamento("BOMBA D'ÁGUA 1 / 2 CV", 613, true),
            new Equipamento("BOMBA D'ÁGUA 3 / 4 CV", 849, true),
            new Equipamento("BOMBA D'ÁGUA 1 CV", 1051, true),
            new Equipamento("BOMBA AQUÁRIO GRANDE", 10, true),
            new Equipamento("BOMBA AQUÁRIO PEQUENO", 5, true),
            new Equipamento("CAFETEIRA ELÉTRICA", 600),
            new Equipamento("CHURRASQUEIRA", 3800),
            CHUVEIRO_ELETRICO,
            new Equipamento("CIRCULADOR AR GRANDE", 200),
            new Equipamento("CIRCULADOR AR PEQUENO / MÉDIO", 90),
            new Equipamento("COMPUTADOR / IMPRESSORA / ESTABILIZADOR", 180),
            new Equipamento("CORTADOR DE GRAMA GRANDE", 1140),
            new Equipamento("CORTADOR DE GRAMA PEQUENO", 500),
            new Equipamento("ENCERADEIRA", 500),
            new Equipamento("ESCOVA DE DENTES ELÉTRICA", 50),
            new Equipamento("ESPREMEDOR DE FRUTAS", 65),
            new Equipamento("EXAUSTOR FOGÃO", 170),
            new Equipamento("EXAUSTOR PAREDE", 110),
            new Equipamento("FACA ELÉTRICA", 220),
            new Equipamento("FERRO ELÉTRICO AUTOMÁTICO", 1000),
            new Equipamento("FOGÃO COMUM", 60),
            new Equipamento("FOGÃO ELÉTRICO 4 CHAPAS", 9120),
            new Equipamento("FORNO À RESISTÊNCIA GRANDE", 1500),
            new Equipamento("FORNO À RESISTÊNCIA PEQUENO", 800),
            new Equipamento("FORNO MICRO-ONDAS ", 1200, true),
            new Equipamento("FREEZER VERTICAL / HORIZONTAL", 130, true),
            new Equipamento("FRIGOBAR", 70, true),
            new Equipamento("FRITADEIRA ELÉTRICA", 1000),
            new Equipamento("GELADEIRA 1 PORTA", 90, true),
            new Equipamento("GELADEIRA 2 PORTAS", 130, true),
            new Equipamento("GRILL", 900),
            new Equipamento("IOGURTEIRA", 26),
            new Equipamento("LÂMPADA FLUORESCENTE COMPACTA - 11W", 11),
            new Equipamento("LÂMPADA FLUORESCENTE COMPACTA - 15 W", 15),
            new Equipamento("LÂMPADA FLUORESCENTE COMPACTA - 23 W", 23),
            new Equipamento("LÂMPADA INCANDESCENTE - 40 W", 40),
            new Equipamento("LÂMPADA INCANDESCENTE - 60 W", 60),
            new Equipamento("LÂMPADA INCANDESCENTE -100 W", 100),
            new Equipamento("LAVADORA DE LOUÇAS", 1500),
            new Equipamento("LAVADORA DE ROUPAS", 500),
            new Equipamento("LIQUIDIFICADOR", 300),
            new Equipamento("MÁQUINA DE COSTURA", 100),
            new Equipamento("MÁQUINA DE FURAR", 350),
            new Equipamento("MICROCOMPUTADOR", 120),
            new Equipamento("MOEDOR DE CARNES", 320),
            new Equipamento("MULTIPROCESSADOR", 420),
            new Equipamento("NEBULIZADOR", 40),
            new Equipamento("OZONIZADOR", 100),
            new Equipamento("PANELA ELÉTRICA", 1100),
            new Equipamento("PIPOQUEIRA", 1100),
            new Equipamento("RÁDIO ELÉTRICO GRANDE", 45),
            new Equipamento("RÁDIO ELÉTRICO PEQUENO", 10),
            new Equipamento("RÁDIO RELÓGIO", 5, true),
            new Equipamento("SAUNA", 5000),
            new Equipamento("SECADOR DE CABELO GRANDE", 1400),
            new Equipamento("SECADOR DE CABELOS PEQUENO", 600),
            new Equipamento("SECADORA DE ROUPA GRANDE", 3500),
            new Equipamento("SECADORA DE ROUPA PEQUENA", 1000),
            new Equipamento("SECRETÁRIA ELETRÔNICA", 20),
            new Equipamento("SORVETEIRA", 15),
            new Equipamento("TORNEIRA ELÉTRICA", 3500),
            new Equipamento("TORRADEIRA", 800),
            new Equipamento("TV EM CORES - 14", 60),
            new Equipamento("TV EM CORES - 18", 70),
            new Equipamento("TV EM CORES - 20", 90),
            new Equipamento("TV EM CORES - 29", 110),
            new Equipamento("TV EM PRETO E BRANCO", 40),
            new Equipamento("TV PORTÁTIL", 40),
            new Equipamento("VENTILADOR DE TETO", 120),
            new Equipamento("VENTILADOR PEQUENO", 65),
            new Equipamento("VÍDEOCASSETE", 10),
            new Equipamento("VÍDEOGAME", 15)
    );
}
