package gastoenergia;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InitApp {

    private static final double PRECO_KWH = 0.6;
    private static final List<UsoDoEquipamento> ITENS_SELECIONADOS = new ArrayList<>();
    private static final String MENU_PRINCIPAL =
    """
    Selecione uma opção
        1 - Adicionar item
        2 - Ver Itens
        3 - Calcular
        0 - Sair
    """;

    private static String input(String mensagem) {
        do {
            try {
                 return JOptionPane.showInputDialog(null, mensagem);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro, tente novamente");
            }
        } while (true);
    }

    private static Equipamento inputDeSelecao() {
        do {
            try {
                return (Equipamento) JOptionPane.showInputDialog(null,
                        "Selecione um equipamento", "Seleção", JOptionPane.PLAIN_MESSAGE, null,
                        Equipamento.EQUIPAMENTOS.toArray(), Equipamento.EQUIPAMENTOS.get(0));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro, tente novamente");
            }
        } while (true);
    }

    private static void adicionarItem() {
        Equipamento equipamentoSelecionado = inputDeSelecao();
        int tempoMedio = Integer.parseInt(input("Qual o tempo médio de uso (em min)?"));

        ITENS_SELECIONADOS.add(new UsoDoEquipamento(equipamentoSelecionado, tempoMedio));
    }

    private static void verItens() {
        String msg;
        if (ITENS_SELECIONADOS.isEmpty()) {
            msg = "Não há itens selecionados";
        } else {
            String lista = ITENS_SELECIONADOS.stream()
                    .map(UsoDoEquipamento::toString).collect(Collectors.joining("\n"));

            double consumoTotal = ITENS_SELECIONADOS.stream().mapToDouble(UsoDoEquipamento::getConsumo).sum() * 30;
            double valorAPagar = consumoTotal * PRECO_KWH;

            String consumoFormatado = NumberFormat.getNumberInstance().format(consumoTotal) + "kWh";
            String valorFormatado = NumberFormat.getCurrencyInstance().format(valorAPagar);
            msg = lista + "\nConsumo total: " + consumoFormatado + "\nValor a pagar: " + valorFormatado;
        }
        JOptionPane.showMessageDialog(null, msg);
    }

    private static void calcular() {
        int meta = Integer.parseInt(input("Qual o valor máximo que deseja pagar?"));
        AlgoritmoGenetico algoritmo = new AlgoritmoGenetico(ITENS_SELECIONADOS, meta);
        algoritmo.executar();
    }

    public static void main(String[] args) {
        int opcaoSelecionada;
        do {
            opcaoSelecionada = Integer.parseInt(JOptionPane.showInputDialog(MENU_PRINCIPAL));
            switch (opcaoSelecionada) {
                case 1 -> adicionarItem();
                case 2 -> verItens();
                case 3 -> calcular();
                case 0 -> System.exit(0);
                default -> JOptionPane.showMessageDialog(null, "Opção inválida");
            }
        } while (true);
    }
}
