package in.eureka.app;

import org.apache.http.client.config.RequestConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
@EnableCaching
@EntityScan("in.eureka")
@EnableJpaRepositories("in.eureka")
@ComponentScan(basePackages = { "in.eureka" })
@Order(1)
public class GogoAppConfig extends WebMvcConfigurerAdapter {

	// timeout in ms to establish a connection to a upstream server. Actual
	// value: 10s
	private final int CONNECTION_TIMEOUT = 10000;

	// timeout in ms to read next packet from socket(connection). Actual value:
	// 10s
	private final int SOCKET_TIMEOUT = 10000;

	@Value("${allowed.origins}")
	private String originsAllowed;

	@Value("${add.index.url.suffix:false}")
	private boolean addUrlSuffix;

	@Value("${index.url.suffix:/**/*}")
	private String urlSuffix;

//	@Autowired
//	private Environment environment;

	@Bean
	public RequestConfig requestConfig() {
		return RequestConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		super.addCorsMappings(registry);
		String[] allowedOrigins = originsAllowed.split(",");
		if (allowedOrigins.length == 1) {
			registry.addMapping("/**").allowedOrigins(allowedOrigins[0]).allowedHeaders("*").allowedMethods("GET",
					"POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD");
		} else if (allowedOrigins.length == 2) {
			registry.addMapping("/**").allowedOrigins(allowedOrigins[0], allowedOrigins[1]).allowedHeaders("*")
					.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD");
		} else if (allowedOrigins.length == 3) {
			registry.addMapping("/**").allowedOrigins(allowedOrigins[0], allowedOrigins[1], allowedOrigins[2])
					.allowedHeaders("*").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD");
		} else {
			registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("GET", "POST", "PUT",
					"PATCH", "DELETE", "OPTIONS", "HEAD");
		}
	}

	@Bean
	public Executor syncCallExecutor() {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(15);
		executor.setQueueCapacity(10000);
		executor.setThreadNamePrefix("SyncExecutor-");
		executor.initialize();
		return executor;
	}

	@Bean(name = "jsonMapper")
	@Primary
	public ObjectMapper jsonMapper() {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		return objectMapper;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter(jsonMapper()));
		converters.add(byteArrayHttpMessageConverter());
	}
	 
	@Bean
	public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
	    ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
	    arrayHttpMessageConverter.setSupportedMediaTypes(getSupportedMediaTypes());
	    return arrayHttpMessageConverter;
	}
	 
	private List<MediaType> getSupportedMediaTypes() {
	    List<MediaType> list = new ArrayList<>();
	    list.add(MediaType.IMAGE_JPEG);
	    list.add(MediaType.IMAGE_PNG);
	    list.add(MediaType.APPLICATION_OCTET_STREAM);
	    return list;
	}
//	@Bean
//	public SimpleUrlHandlerMapping config() throws IOException {
//
//		String tempContent = IOUtils.toString(new FileInputStream(new ClassPathResource("stateConfig.json").getFile()));
//		JSONObject jsonObject = new JSONObject(tempContent);
//
//		SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
//		Map<String, Object> urlMap = new HashMap<>();
//
//		jsonObject.keySet().forEach(o -> {
//			if (addUrlSuffix)
//				urlMap.put(o.toString() + urlSuffix, index());
//			else
//				urlMap.put(o.toString(), index());
//		});
//		urlMap.put("/", index());
//
//		simpleUrlHandlerMapping.setUrlMap(urlMap);
//		simpleUrlHandlerMapping.setAlwaysUseFullPath(true);
//
//		return simpleUrlHandlerMapping;
//	}

//	@Bean
//	public CacheManager cacheManager() {
//		return new ConcurrentMapCacheManager("packages");
//	}


}
