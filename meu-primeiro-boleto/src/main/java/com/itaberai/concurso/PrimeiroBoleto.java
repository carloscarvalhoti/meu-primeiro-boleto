package com.itaberai.concurso;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;

public class PrimeiroBoleto {
	public static void main(String[] args) {
		Cedente cedente = new Cedente("Municipio de Itaberaí", "02.451.938/0001-53");
		Sacado sacado = new Sacado("Carlos C. Alves Carvalho");
		Endereco endereco = new Endereco();
		endereco.setUF(UnidadeFederativa.GO);
		endereco.setLocalidade("Itaberaí");
		endereco.setCep(new CEP("76630-000"));
		endereco.setBairro("Alto da Bela Vista");
		endereco.setLogradouro("Rua 22 Qd 24 Lt 06");
		endereco.setNumero("s/n");
		
		sacado.addEndereco(endereco);
		
		//Criando o titulo
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.CAIXA_ECONOMICA_FEDERAL.create());
		contaBancaria.setAgencia(new Agencia(859));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(3395, "1"));
		contaBancaria.setCarteira(new Carteira(02));
		
		Titulo titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNumeroDoDocumento("12042018-1");
		titulo.setNossoNumero("14000000000000019");
		titulo.setDigitoDoNossoNumero("7");
		
		titulo.setValor(BigDecimal.valueOf(100.00));
		titulo.setDataDoDocumento(new Date());
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 05, 13);
		titulo.setDataDoVencimento(calendar.getTime());
		
		titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
		
		titulo.setAceite(Aceite.N);
		
		//Dados do boleto
		Boleto boleto = new Boleto(titulo);
		boleto.setLocalPagamento("Pagar preferencialmente nas Casas Lotericas e Agencias da Caixa");
		boleto.setInstrucaoAoSacado("A incrição será homologada após a compensação bancaria do pagamento do boleto");
		boleto.setInstrucao1("Após o vencimento, aplicar multa de 2,00% e juros de 1,00% ao mês");
		
		BoletoViewer boletoViewer = new BoletoViewer(boleto);
		File arquivoPdf = boletoViewer.getPdfAsFile("boleto2.pdf");
		
		mostrarNaTela(arquivoPdf);
				
	}
	
	private static void mostrarNaTela(File arquivo){
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(arquivo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
