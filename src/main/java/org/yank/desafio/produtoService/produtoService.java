package org.yank.desafio.produtoService;
import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.yank.desafio.produtoEntity.Produto;
import org.yank.desafio.produtoRepository.produtoRepository;

public class produtoService{
	
	/**
	 * Método responsável por acessar a sessão de Celulares e Smartphones no site da Americanas
	 * e capturar os dados dos produtos exibidos nas páginas principais.
	 * @throws IOException 
	 * @throws ParseException 
	 * 
	 */
	public void consultaProdutos() throws IOException, ParseException {				
		Document doc = Jsoup.connect("https://www.americanas.com.br/categoria/celulares-e-smartphones/smartphone/pagina-1?ordenacao=topSelling").get();
		Elements ul = doc.select("#content-middle > div:nth-child(4) > div > div > div > div:nth-child(2) > div > ul");
		Elements li = ul.select("li");
		
		for(int j = 1; j <= li.size() - 4; j++) {
			doc = Jsoup.connect("https://www.americanas.com.br/categoria/celulares-e-smartphones/smartphone/pagina-"+j+"?ordenacao=topSelling").get();
			Elements top = doc.getElementsByClass("product-grid-item ColUI-gjy0oc-0 hFbhrr ViewUI-sc-1ijittn-6 iXIDWU");
			
			for(int i = 0; i <= top.size()-1; i++) {
				Element post = top.get(i);
				String nome = post.getElementsByClass("TitleUI-bwhjk3-15 khKJTM TitleH2-sc-1wh9e1x-1 gYIWNc").text();
				String preco = post.getElementsByClass("PriceUI-bwhjk3-11 cmTHwB PriceUI-sc-1q8ynzz-0 dHyYVS TextUI-sc-12tokcy-0 bLZSPZ").text();
				
				if(preco.isEmpty()) {
					preco = post.getElementsByClass("PriceUI-bwhjk3-11 jtJOff PriceUI-sc-1q8ynzz-0 dHyYVS TextUI-sc-12tokcy-0 bLZSPZ").text();
				}
				
				String avaliacao = post.getElementsByClass("Quantity-sc-1fg2071-3 RsSwi TextUI-sc-12tokcy-0 kzcXGa").text();
				String desconto = post.getElementsByClass("TextUI-xlll2j-3 cRtgJA TextUI-sc-12tokcy-0 bLZSPZ").text();
				Elements link = doc.select("#content-middle > div:nth-child(4) > div > div > div > div.row.product-grid.no-gutters.main-grid > div:nth-child("+(i+1)+") > div > div.RippleContainer-sc-1rpenp9-0.dMCfqq > a");
				String url = link.attr("href");
				
				addProduto(nome, url, desconto, avaliacao, preco);
			}
		}
	}
	
	/**
	 * Método responsável por adicionar os produtos ao Banco de Dados
	 * @throws IOException 
	 * @throws ParseException 
	 * 
	 */
	public static void addProduto(String nome, String url, String desconto, String avaliacao, String preco) 
			throws ParseException {
		Produto produto = new Produto();
		produto.setNome(getNome(nome));
		produto.setURL("https://www.americanas.com.br"+url);
		produto.setCategoria(getCategoria(nome));
		produto.setCor(getCor(nome));
		produto.setDesconto(getDesconto(desconto));
		produto.setClassificacao(getClassificacao(avaliacao));
		produto.setPreco(getPreco(preco));
		
		produtoRepository dao = new produtoRepository();
		dao.inserirProduto(produto);
	}
	
	/**
	 * Método responsável por manipular a String através do regex
	 * @throws ParseException 
	 * 
	 */
	public static String getCategoria(String nome) {
		Pattern r = Pattern.compile("\\S+");
	    Matcher m = r.matcher(nome);
	    String categoriax = null;
	    if (m.find( )) {
		    categoriax = m.group(0);
	    	
		    if (categoriax.equals("Moto")) {
	    		categoriax = "Smartphone";
	    	}
	    }
		return categoriax;
	}
	

	/**
	 * Método responsável por manipular a String através do regex
	 * @throws ParseException 
	 * 
	 */
	public static String getCor(String nome) {
		Pattern r = Pattern.compile("- .*");
	    Matcher m = r.matcher(nome);
	    String corx = null;
	    if (m.find( )) {
		    corx = m.group(0).replace("-", "");
	    }
	    
	    if(corx == " Apple" || corx != null) {
	    	Pattern rm = Pattern.compile("GB(.*)Tela|gb(.*)Tela");
		    Matcher mr = rm.matcher(nome);
		    if(mr.find( )) {
		    	corx = mr.group(1);
		    }
	    } 
	    
		return corx;
	}
	

	/**
	 * Método responsável por manipular a String através do regex
	 * @throws ParseException 
	 * 
	 */
	public static int getClassificacao(String avaliacao) {
		Pattern r = Pattern.compile("[?:0-9]{1,10}");
	    Matcher m = r.matcher(avaliacao);
	    String avaliacaox = null;
	    if (m.find( )) {
	    	avaliacaox = m.group(0);
	    }
	    
	    if(avaliacaox == null) return 0;

	    int avaliacaoxd = Integer.parseInt(avaliacaox);

		return avaliacaoxd;
	}
	

	/**
	 * Método responsável por manipular a String através do regex
	 * @throws ParseException 
	 * 
	 */
	public static String getNome(String nome) {
		Pattern r = Pattern.compile("(.*)GB|(.*)gb");
	    Matcher m = r.matcher(nome);
	    String nomex = null;
	    if (m.find( )) {
	    	nomex = m.group(0);
	    }
	    
	    if(nomex == null) return nome;
	    
		return nomex;
	}
	

	/**
	 * Método responsável por manipular a String através do regex
	 * @throws ParseException 
	 * 
	 */
	public static Double getPreco(String preco) throws ParseException {
		Pattern r = Pattern.compile("R\\$[\\t ]*((\\d{1,3}\\.?)+(,\\d{2}))");
	    Matcher m = r.matcher(preco);
	    String precox = null;
	    if (m.find( )) {
	    	precox = m.group(1);
	    }
	    
	    if(precox == null)
	    	return 0.00;
	    
	    precox = precox.replace(".","");
	    precox = precox.replace(",",".");
	    
		return Double.parseDouble(precox);
	}
	
	/**
	 * Método responsável por manipular a String através do regex
	 * @throws ParseException 
	 * 
	 */
	public static int getDesconto(String desconto) throws ParseException {
		Pattern r = Pattern.compile("\\d\\d");
	    Matcher m = r.matcher(desconto);
	    String descontox = null;
	    if (m.find( )) {
	    	descontox = m.group(0).replace("%", "");;
	    }
	    
	    if(descontox == null)
	    	return 0;
	    
		return Integer.parseInt(descontox);
	}
	
	/**
	 * Método responsável por buscar o produto mais barato
	 */
	public void consultaProdutoMaisBarato() {
		produtoRepository dao = new produtoRepository();
		dao.consultaProdutoMaisBarato();
	}
	
	/**
	 * Método responsável por buscar o produto mais avaliado
	 */
	public void consultaProdutoMaisAvaliado() {
		produtoRepository dao = new produtoRepository();
		dao.consultaProdutoMaisAvaliado();
	}
	
	/**
	 * Método responsável por buscar o produto com maior desconto
	 */
	public void consultaProdutoMaiorDesconto() {
		produtoRepository dao = new produtoRepository();
		dao.consultaProdutoMaiorDesconto();
		
	}
	
	/**
	 * Método responsável por listar todos os produtos
	 */
	public void listProdutos() {
		produtoRepository dao = new produtoRepository();
		dao.listProdutos();		
	}
}
