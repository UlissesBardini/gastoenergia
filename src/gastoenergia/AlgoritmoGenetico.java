package gastoenergia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlgoritmoGenetico {
    private final List<UsoDoEquipamento> equipamentos;
    private final double metaDeConsumo;
    private static final int NUM_GERACOES = 1000;
    private static final int TAMANHO_POPULACAO = 100;
    private static final int TEMPO_MINIMO_CHUVEIRO = 5;
    private static final int NUMERO_ELITES = 2;

    public AlgoritmoGenetico(List<UsoDoEquipamento> equipamentos, double metaDeConsumo) {
        this.equipamentos = equipamentos;
        this.metaDeConsumo = metaDeConsumo;
    }

    public String executar() {
        List<int[]> populacao = inicializarPopulacao();

        for (int geracao = 0; geracao < NUM_GERACOES; geracao++) {
            // Encontra os melhores indivíduos (elites) antes de fazer crossover e mutação
            List<int[]> elites = encontrarMelhoresSolucao(populacao);

            List<int[]> novaPopulacao = new ArrayList<>(elites); // Adiciona as elites diretamente à nova população

            // Preenche o restante da nova população
            while (novaPopulacao.size() < TAMANHO_POPULACAO) {
                int[] pai1 = selecionar(populacao);
                int[] pai2 = selecionar(populacao);

                int[] filho = crossover(pai1, pai2);
                mutacao(filho);

                if (validar(filho)) {
                    novaPopulacao.add(filho);
                }
            }

            populacao = novaPopulacao; // Atualiza a população com a nova geração
        }

        int[] melhorSolucao = encontrarMelhorSolucao(populacao);

        StringBuilder solucao = new StringBuilder();
        for (int i = 0; i < melhorSolucao.length; i++) {
            solucao.append(equipamentos.get(i).getEquipamento().getNome()).append(": ").append(melhorSolucao[i]).append(" minutos");
        }
        return solucao.toString();
    }

    private List<int[]> inicializarPopulacao() {
        Random random = new Random();
        List<int[]> populacao = new ArrayList<>();

        for (int i = 0; i < TAMANHO_POPULACAO; i++) {
            int[] cromossomo = new int[equipamentos.size()];

            for (int j = 0; j < equipamentos.size(); j++) {
                if (equipamentos.get(j).getEquipamento().isEssencial()) {
                    cromossomo[j] = equipamentos.get(j).getTempoDeUso(); // Mantém o tempo de uso original
                } else {
                    cromossomo[j] = random.nextInt(equipamentos.get(j).getTempoDeUso() + 1); // Valor aleatório
                    if (equipamentos.get(j).getEquipamento().equals(Equipamento.CHUVEIRO_ELETRICO)) {
                        cromossomo[j] = Math.max(TEMPO_MINIMO_CHUVEIRO, cromossomo[j]); // Impõe mínimo
                    }
                }
            }

            populacao.add(cromossomo);
        }

        return populacao;
    }

    private List<int[]> encontrarMelhoresSolucao(List<int[]> populacao) {
        List<Double> fitness = calcularFitness(populacao);
        List<int[]> elites = new ArrayList<>();

        // Itera para encontrar o número de elites solicitado
        for (int e = 0; e < NUMERO_ELITES; e++) {
            double melhorFitness = fitness.get(0);
            int indiceMelhor = 0;

            // Encontrar o melhor indivíduo
            for (int i = 1; i < fitness.size(); i++) {
                if (fitness.get(i) > melhorFitness) {
                    melhorFitness = fitness.get(i);
                    indiceMelhor = i;
                }
            }

            // Adiciona o melhor indivíduo à lista de elites
            elites.add(populacao.get(indiceMelhor));

            // Remove o melhor fitness encontrado para evitar selecioná-lo de novo
            fitness.set(indiceMelhor, Double.NEGATIVE_INFINITY);
        }

        return elites;
    }

    private int[] selecionar(List<int[]> populacao) {
        List<Double> fitness = calcularFitness(populacao);
        return selecionarPorRoleta(populacao, fitness);
    }

    private int[] selecionarPorRoleta(List<int[]> populacao, List<Double> fitness) {
        double somaFitness = 0;
        for (double fit : fitness) {
            somaFitness += fit;
        }

        Random random = new Random();
        double valorSorteado = random.nextDouble() * somaFitness;

        double acumulado = 0;
        for (int i = 0; i < populacao.size(); i++) {
            acumulado += fitness.get(i);
            if (acumulado >= valorSorteado) {
                return populacao.get(i);
            }
        }

        return populacao.get(populacao.size() - 1);
    }

    // Função de fitness: quanto menor a diferença do consumo total em relação à meta, melhor o fitness
    private List<Double> calcularFitness(List<int[]> populacao) {
        List<Double> fitness = new ArrayList<>();

        for (int[] cromossomo : populacao) {
            double consumoTotal = 0;

            for (int i = 0; i < cromossomo.length; i++) {
                consumoTotal += cromossomo[i] * equipamentos.get(i).getEquipamento().getPotenciaMediaEmWatts() / 60.0 / 1000;
            }

            double diferencaConsumo = Math.abs(metaDeConsumo - consumoTotal);

            // Quanto menor a diferença, maior o fitness
            fitness.add(1 / (diferencaConsumo + 1)); // Adiciona 1 para evitar divisão por zero
        }

        return fitness;
    }

    private int[] crossover(int[] pai1, int[] pai2) {
        Random random = new Random();
        int pontoDeCorte = random.nextInt(pai1.length);
        int[] filho = new int[pai1.length];

        for (int i = 0; i < pai1.length; i++) {
            filho[i] = i < pontoDeCorte ? pai1[i] : pai2[i];
        }

        return filho;
    }

    private void mutacao(int[] cromossomo) {
        Random random = new Random();
        int index = random.nextInt(cromossomo.length);
        cromossomo[index] += random.nextInt(10) - 5; // Pequena mutação
    }

    private boolean validar(int[] cromossomo) {
        double consumoTotal = 0;

        for (int i = 0; i < cromossomo.length; i++) {
            consumoTotal += cromossomo[i] * equipamentos.get(i).getEquipamento().getPotenciaMediaEmWatts() / 60.0 / 1000;
        }

        return consumoTotal <= metaDeConsumo;
    }

    private int[] encontrarMelhorSolucao(List<int[]> populacao) {
        List<Double> fitness = calcularFitness(populacao);
        double melhorFitness = fitness.get(0);
        int indiceMelhor = 0;

        for (int i = 1; i < fitness.size(); i++) {
            if (fitness.get(i) > melhorFitness) {
                melhorFitness = fitness.get(i);
                indiceMelhor = i;
            }
        }

        return populacao.get(indiceMelhor);
    }

}