package br.com.revup.revup.controller.request;

import br.com.revup.revup.controller.constraints.AnoFabricacao;
import br.com.revup.revup.entity.Veiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditarVeiculoRequest(
    @NotBlank(message = "O campo \"marca\" é obrigatório.")
    String marca,

    @NotBlank(message = "O campo \"modelo\" é obrigatório.")
    String modelo,

    @NotNull(message = "O campo \"ano\" é obrigatório.")
    @AnoFabricacao
    int ano,

    @NotBlank(message = "O campo \"cor\" é obrigatório.")
    String cor
) {
    public Veiculo paraEntidade() {
        Veiculo veiculo = new Veiculo();
        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);
        veiculo.setAno(ano);
        veiculo.setCor(cor);

        return veiculo;
    }
}
