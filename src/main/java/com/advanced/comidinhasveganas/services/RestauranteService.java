package com.advanced.comidinhasveganas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advanced.comidinhasveganas.entities.Cardapio;
import com.advanced.comidinhasveganas.entities.Cliente;
import com.advanced.comidinhasveganas.entities.ItemCardapio;
import com.advanced.comidinhasveganas.entities.Mesa;
import com.advanced.comidinhasveganas.entities.Pedido;
import com.advanced.comidinhasveganas.entities.Requisicao;
import com.advanced.comidinhasveganas.repositories.CardapioRepository;
import com.advanced.comidinhasveganas.repositories.ClienteRepository;
import com.advanced.comidinhasveganas.repositories.ItemCardapioRepository;
import com.advanced.comidinhasveganas.repositories.MesaRepository;
import com.advanced.comidinhasveganas.repositories.PedidoRepository;
import com.advanced.comidinhasveganas.repositories.RequisicaoRepository;

@Service
public class RestauranteService {
  @Autowired
  private MesaRepository mesaRepository;
  @Autowired
  private ClienteRepository clienteRepository;
  @Autowired
  private RequisicaoRepository requisicaoRepository;
  @Autowired
  private PedidoRepository pedidoRepository;
  @Autowired
  private CardapioRepository cardapioRepository;
  @Autowired
  private ItemCardapioRepository itemCardapioRepository;

  public void addMesa(Mesa mesa) {
    mesaRepository.save(mesa);
  }

  public void addCliente(Cliente cliente) {
    clienteRepository.save(cliente);
  }

  public void addRequisicao(Requisicao requisicao) {
    requisicaoRepository.save(requisicao);
  }

  public void addPedido(Pedido pedido) {
    pedidoRepository.save(pedido);
  }

  public Requisicao obterRequisicaoPorId(Long id) {
    return requisicaoRepository.findById(id).orElse(null);
  }

  public ItemCardapio obterItemCardapioPorNome(String nome) {
    return itemCardapioRepository.findAll().stream()
        .filter(item -> item.getNome().equalsIgnoreCase(nome))
        .findFirst()
        .orElse(null);
  }

  public void alocarClientes() {
    List<Requisicao> requisicoes = requisicaoRepository.findAll();
    List<Mesa> mesas = mesaRepository.findAll();

    for (Requisicao requisicao : requisicoes) {
      if (!requisicao.isAtendida()) {
        for (Mesa mesa : mesas) {
          if (mesa.verificarDisponibilidade() && mesa.getCapacidade() >= requisicao.getNumeroPessoas()) {
            requisicao.setMesa(mesa);
            requisicao.setAtendida(true);
            mesa.setClientesSentados(requisicao.getNumeroPessoas());
            mesaRepository.save(mesa);
            requisicaoRepository.save(requisicao);
            break;
          }
        }
      }
    }
  }

  public void printConta(Long requisicaoId) {
    Requisicao requisicao = obterRequisicaoPorId(requisicaoId);
    if (requisicao != null) {
      System.out.println("Conta para a Requisição ID: " + requisicaoId);
      System.out.println("Cliente: " + requisicao.getCliente().getNome());
      System.out.println("Pedidos:");
      for (Pedido pedido : requisicao.getPedidos()) {
        for (ItemCardapio item : pedido.getItemCardapio()) {
          System.out.println("- " + item.getNome() + ": R$ " + item.getPreco());
        }
      }
      System.out.println("Total: R$ " + requisicao.getConta());
    } else {
      System.out.println("Requisição não encontrada.");
    }
  }

  public void iniciarRestaurante() {
    // Adicionando mesas padrão
    for (int i = 0; i < 4; i++) {
      addMesa(new Mesa(null, 4, 0));
      addMesa(new Mesa(null, 6, 0));
    }
    for (int i = 0; i < 2; i++) {
      addMesa(new Mesa(null, 8, 0));
    }

    // Adicionando itens de cardápio padrão
    List<ItemCardapio> itens = List.of(
        new ItemCardapio("Moqueca de Palmito", 32.00),
        new ItemCardapio("Falafel Assado", 20.00),
        new ItemCardapio("Salada Primavera com Macarrão Konjac", 25.00),
        new ItemCardapio("Escondidinho de Inhame", 18.00),
        new ItemCardapio("Strogonoff de Cogumelos", 35.00),
        new ItemCardapio("Caçarola de Legumes", 22.00),
        new ItemCardapio("Água", 3.00),
        new ItemCardapio("Copo de Suco", 7.00),
        new ItemCardapio("Refrigerante Orgânico", 7.00),
        new ItemCardapio("Cerveja Vegana", 9.00),
        new ItemCardapio("Taça de Vinho Vegano", 18.00));

    // Salvando os itens no repositório
    itens.forEach(itemCardapioRepository::save);

    // Carregando os itens salvos para associar ao cardápio
    List<ItemCardapio> itensSalvos = itemCardapioRepository.findAll();

    // Criando o cardápio e adicionando os itens salvos
    Cardapio cardapio = new Cardapio(itensSalvos);
    cardapioRepository.save(cardapio);
  }
}
