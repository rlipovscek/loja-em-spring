	package br.com.casadocodigo.loja.conf;
	
	import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.CarrinhoCompras;
	
	@EnableWebMvc
	@ComponentScan(basePackageClasses={HomeController.class, ProdutoDAO.class,FileSaver.class, CarrinhoCompras.class})//informa as classes que o spring deve scanear 
	public class AppWebConfiguration extends WebMvcConfigurerAdapter{
	
		/*
		 * Configura o doretório padrão usado na aplicação, e qual é o sufixo usado nos arquivos chamados nesses diretórios 
		 * 
		 */
		
		@Bean
		public InternalResourceViewResolver internalResourceViewResolver(){
		    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		    resolver.setPrefix("/WEB-INF/views/");
		    resolver.setSuffix(".jsp");
		    resolver.setExposedContextBeanNames("carrinhoCompras");
		    return resolver;
		} // fim de internalResourceViewResolver
		
		/*
		 *  habilita e configura o arquivo messages.properties da aplicação
		 * 
		 */
		
		@Bean
		public MessageSource messageSource(){
			ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
			messageSource.setBasename("/WEB-INF/messages");
			messageSource.setDefaultEncoding("UTF-8");
			messageSource.setCacheSeconds(1);
			return messageSource;
			
		} // fim de messageSource
		
		
		/*
		 *  Responsável por configurar o padrão da data usada pelo sistema 
		 * 
		 */
		
		@Bean
		public FormattingConversionService mvcConversionService(){
			
			DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
			DateFormatterRegistrar registrar = new DateFormatterRegistrar();
			registrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
			registrar.registerFormatters(conversionService);
			return conversionService;
			
		}// fim de mvcConversionService
		
		/*
		 *  Responsável por configurar o multpart para receber arquivos  
		 */
		@Bean
	    public MultipartResolver multipartResolver(){
			return new StandardServletMultipartResolver();
		}
		
		   @Override
		    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		        registry.addResourceHandler("/resources/**").addResourceLocations(
		                "/resources/");
		    }
	}