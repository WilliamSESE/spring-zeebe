package io.camunda.zeebe.spring.client.config;

import io.camunda.zeebe.client.ZeebeClientBuilder;
import io.camunda.zeebe.client.impl.ZeebeClientBuilderImpl;
import io.camunda.zeebe.client.impl.ZeebeClientImpl;
import io.camunda.zeebe.spring.client.ZeebeClientLifecycle;
import io.camunda.zeebe.spring.client.ZeebeClientObjectFactory;
import io.camunda.zeebe.spring.client.bean.value.factory.ReadAnnotationValueConfiguration;
import io.camunda.zeebe.spring.client.config.processor.PostProcessorConfiguration;
import java.util.Properties;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import({
  PostProcessorConfiguration.class,
  ReadAnnotationValueConfiguration.class,

})
@Configuration
public class ZeebeClientSpringConfiguration {

  @Autowired
  ZeebeClientBuilder zeebeClientBuilder;

  public static final ZeebeClientBuilderImpl DEFAULT =
    (ZeebeClientBuilderImpl) new ZeebeClientBuilderImpl().withProperties(new Properties());

  @Bean
  public ZeebeClientLifecycle zeebeClientLifecycle(
    final ZeebeClientObjectFactory factory,
    final ApplicationEventPublisher publisher) {
    return new ZeebeClientLifecycle(factory, publisher);
  }

  @Bean
  public ZeebeClientObjectFactory zeebeClientObjectFactory() {
    return () -> zeebeClientBuilder.build();
  }
}
