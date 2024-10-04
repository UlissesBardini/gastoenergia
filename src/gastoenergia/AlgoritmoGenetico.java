package gastoenergia;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class AlgoritmoGenetico {
    
    private static final Random RANDOM = new Random();
    private static final int CAPACIDADE_MOCHILA = 22;
    private static final int TAMANHO_POPULACAO = 7;
    private static final int NUMERO_GERACOES = 1000;
    private static final double TAXA_MUTACAO = 0.1;
    private static final int PENALIDADE_EXCESSO = 100;
    private static final int TEMPO_MINIMO_CHUVEIRO = 5;
    private final List<UsoDoEquipamento> equipamentos;
    private final double meta;

    public AlgoritmoGenetico(List<UsoDoEquipamento> equipamentos, double meta) {
        this.equipamentos = equipamentos;
        this.meta = meta;
    }
    
    private int[] calcularBeneficioDoPeso(int[] cromossomo) {
        int beneficioTotal = 0;
        int pesoTotal = 0;

        for (int i = 0; i < cromossomo.length; i++) {
            if (cromossomo[i] == 1) {
//                beneficioTotal += equipamentos[i].beneficio();
//                pesoTotal += equipamentos[i].peso();
            }
        }

        if (pesoTotal > CAPACIDADE_MOCHILA) beneficioTotal -= PENALIDADE_EXCESSO; // Penaliza se exceder a capacidade;

        return new int[]{ beneficioTotal, pesoTotal };
    }

    private int[][] selecionarPais(int[][] populacao) {
        int[] beneficios = Arrays.stream(populacao)
                .mapToInt(cromossomo -> calcularBeneficioDoPeso(cromossomo)[0])
                .toArray();

        int beneficioTotal = IntStream.of(beneficios).sum();
        double[] probabilidades = IntStream.of(beneficios)
                .mapToDouble(b -> (double) b /beneficioTotal).toArray();

        int[] pai1 = opcaoAleatoriaComPesos(populacao, probabilidades);
        int[] pai2 = opcaoAleatoriaComPesos(populacao, probabilidades);

        return new int[][]{ pai1, pai2 };
    }

    private int[] opcaoAleatoriaComPesos(int[][] populacao, double[] pesos) {

        // Calcula o peso total dos pesos
        double pesoTotal = DoubleStream.of(pesos).sum();

        // Gera um número aleatório entre 0 e o peso total
        double valorAleatorio = RANDOM.nextDouble(pesoTotal);

        // Acha o elemento correspondente na população
        double pesoAcumulativo = 0;
        for (int i = 0; i < populacao.length; i++) {
            pesoAcumulativo += pesos[i];
            if (valorAleatorio <= pesoAcumulativo) {
                return populacao[i];
            }
        }

        return populacao[populacao.length - 1];
    }

    private int[] concatenarArrays(int[] arr1, int[] arr2) {
        int[] resultado = Arrays.copyOf(arr1, arr1.length + arr2.length);
        System.arraycopy(arr2, 0, resultado, arr1.length, arr2.length);
        return resultado;
    }

    private int[][] cruzar(int[] pai1, int[] pai2) {
        int pontoDeCorte = RANDOM.nextInt(1, pai1.length);
        int[] filho1 = concatenarArrays(
                Arrays.copyOfRange(pai1, 0, pontoDeCorte),
                Arrays.copyOfRange(pai2, pontoDeCorte, pai2.length));

        int[] filho2 = concatenarArrays(
                Arrays.copyOfRange(pai2, 0, pontoDeCorte),
                Arrays.copyOfRange(pai1, pontoDeCorte, pai1.length));

        return new int[][]{ filho1, filho2 };
    }

    private void mutar(int[] cromossomo) {
        for (int i = 0; i < cromossomo.length; i++) {
            if (Math.random() < TAXA_MUTACAO) {
                cromossomo[i] = 1 - cromossomo[i]; // Inverte o bit;
            }
        }
    }

    private boolean populacaoNaoContemFilho(int[][] populacao, int[] filho) {
        for (int[] cromossomo : populacao) {
            boolean a = Arrays.equals(cromossomo, filho);
            System.out.println(a);
            if (a) {
                return false;
            }
        }
        return true;
    }

    public void executar() {
        int[][] populacao = new int[TAMANHO_POPULACAO][equipamentos.size()];
        for (int i = 0; i < TAMANHO_POPULACAO; i++) {
            int[] cromossomo = equipamentos.stream()
                    .mapToInt(it -> RANDOM.nextInt(2)).toArray();
            populacao[i] = cromossomo;
        }

        for (int i = 0; i < NUMERO_GERACOES; i++) {
            int[][] novaPopulacao = new int[TAMANHO_POPULACAO][equipamentos.size()];
            // Elitismo: mantém o melhor indivíduo da geração anterior
            int[] melhorIndividuo = Arrays.stream(populacao)
                    .max(Comparator.comparingInt(cromossomo -> calcularBeneficioDoPeso(cromossomo)[0]))
                    .orElse(populacao[populacao.length - 1]);
            novaPopulacao[0] = melhorIndividuo;

            int j = 0;
            while (j < TAMANHO_POPULACAO - 1) {
                int[][] pais = selecionarPais(populacao);
                int[] pai1 = pais[0];
                int[] pai2 = pais[1];
                int[][] cria = cruzar(pai1, pai2);
                int[] filho1 = cria[0];
                int[] filho2 = cria[1];
                mutar(filho1);
                mutar(filho2);
                // Adiciona apenas se não for duplicado
                if (populacaoNaoContemFilho(novaPopulacao, filho1)) {
                    novaPopulacao[++j] = filho1;
                }
                if (j < TAMANHO_POPULACAO - 1 && populacaoNaoContemFilho(novaPopulacao, filho2)) {
                    novaPopulacao[++j] = filho2;
                }
            }
            populacao = novaPopulacao;
        }
        int[] melhorSolucao = Arrays.stream(populacao)
                .max(Comparator.comparingInt(cromossomo -> calcularBeneficioDoPeso(cromossomo)[0]))
                .orElse(populacao[populacao.length - 1]);

        int[] calculo = calcularBeneficioDoPeso(melhorSolucao);
        int beneficio = calculo[0];
        int peso = calculo[1];

        System.out.println("Melhor solução: ");
        System.out.println("Cromossomo: " + Arrays.stream(melhorSolucao)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(", ")));
        System.out.println("Beneficio: " + beneficio);
        System.out.println("Peso: " + peso);
    }
}
