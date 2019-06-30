package br.com.casadocodigo.loja.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/*
 * Classe responsavel por configurar o banco de dados usado pela aplicação
 * 
 * */

@EnableTransactionManagement //O Spring ativa o gerenciamento de transações e já reconhece o TransactionManager
public class JPAConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		//informamos a variavel responsável por atualizar as configurações necessárias no spring
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		//configuramos o padrão de gerenciamento do banco de dados
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

		factoryBean.setJpaVendorAdapter(vendorAdapter);// informamos qual o tipo de gerenciamento de banco
		
		
		//informa os dados de acesso ao banco de dados
	
        DriverManagerDataSource dataSource = new DriverManagerDataSource(); // cria um repositório para receber as configurações do banco
        dataSource.setUsername("root"); // usuario do banco de dados
        dataSource.setPassword("TesTe@!23"); //senha do banco de dados
        dataSource.setUrl("jdbc:mysql://localhost:3306/casadocodigo");//o endereço do banco usado pela aplicação
        dataSource.setDriverClassName("com.mysql.jdbc.Driver"); //o driver usado pela aplicação

        factoryBean.setDataSource(dataSource); // informa ao nosso gerenciador os dados usados pelo banco

        Properties props = new Properties(); // repositório que recebe as propriedades do banco
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect"); // informamos qual o banco usado
        props.setProperty("hibernate.show_sql", "true"); 
        props.setProperty("hibernate.hbm2ddl.auto", "update");

        factoryBean.setJpaProperties(props); //enviados as proriedades para nosso gerenciador

        factoryBean.setPackagesToScan("br.com.casadocodigo.loja.models"); // informamos onde estão os modelos usados pelo sistema

        return factoryBean;
	}
	
 
	//Gerencia a transação com o banco de dados
	@Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf){//recebe a classe responsavel pelas configurações do banco de dados
        return new JpaTransactionManager(emf);
    }

}
