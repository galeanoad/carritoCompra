package com.prueba.batch;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.prueba.dto.DataDto;
import com.prueba.entity.Inventario;

/**
 *  Esta clase contiene todos los aspectos relacionados con la configuracion
 *  del proceso batch
 * 
 * @author Diego Galeano
 *
 */

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);
		
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Resource
	private JobBuilderFactory jobBuilderFactory;
	
	@Resource
	private JobLauncher jobLauncher;
	
	private Job jobInstance;

	private static final String SEPARADOR_CSV = ",";
	private static final String[] ESTRUCTURA_DATA = {
			"nombre", "marca", "precio", "stock", "estado", "descuento"};
	
	@PostConstruct
	public void init() {
		jobInstance = reporteJob();
	}
	
	public Job reporteJob() {
		return jobBuilderFactory.get("cargaArchivoJob")
				.incrementer(new RunIdIncrementer())
				.flow(dataStep())
				.end()
				.build();

	}
	
	@Bean
	public Step dataStep() {
		return stepBuilderFactory
				.get("cargaArchivotep").<DataDto, Inventario>chunk(999999999)
				.reader(dataReader(null))
				.processor(dataArchivoProcessor())
				.writer(dataArchivoWriter())
				.allowStartIfComplete(true)
				.build();

	}
	
	@Bean
	@StepScope
	public FlatFileItemReader<DataDto> dataReader(
			@Value("#{jobParameters['rutaArchivo']}") String rutaArchivo) {
		
		return new FlatFileItemReaderBuilder<DataDto>()
				.name("InfoReader")
				.resource(new FileSystemResource(rutaArchivo))
				.linesToSkip(1)
				.delimited()
				.delimiter(SEPARADOR_CSV)
				.names(ESTRUCTURA_DATA)
				.targetType(DataDto.class)
				.build();

	}
	
	@Bean
	@StepScope
	public DataProcessor dataArchivoProcessor() {
		return new DataProcessor();
	}
	
	@Bean
	public DataWriter dataArchivoWriter() {
		return new DataWriter();
	}
	
	public void lanzarBatch() {
		executeJob();
	}
	
	private JobExecution executeJob() {
		JobExecution jobExecution;
		try {
			logger.info("::: Generando parametros cargue inventario");
			JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
			jobParametersBuilder.addString("rutaArchivo",  "D:\\Documents\\Productos.csv");
			jobParametersBuilder.addDate("date", new Date());
			logger.info("::: Termina Generacion de parametros cargue inventario");

			jobExecution = jobLauncher.run(jobInstance, jobParametersBuilder.toJobParameters());
			
		} catch (Exception ex) {
			jobExecution = null;
			logger.error("Error ejecutando :\n{}", ex);
		}
		return jobExecution;
	}
}
