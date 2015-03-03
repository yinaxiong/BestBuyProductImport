package net.koncise.catalog.bestbuy

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.joda.JodaModule
import org.elasticsearch.node.NodeBuilder
import org.elasticsearch.node.Node
import org.elasticsearch.client.Client
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource

@SpringBootApplication
class BestBuyImportJobConfig {

    @Value('${elasticsearch.cluster}')
    private String elasticSearchCluster

    /*
    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        final PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer()
        ppc.setIgnoreUnresolvablePlaceholders(true)
        ppc.setIgnoreResourceNotFound(true)
        ppc.setLocations([
                new ClassPathResource('application.properties'),
                new FileSystemResource('/usr/local/etc/BestBuyProductImport/application.properties'),
                new FileSystemResource('/Users/kris/BestBuyProductImport/application.properties'),
        ] as Resource[])
        return ppc
    }*/

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.registerModule(new JodaModule())
        return objectMapper
    }

    @Bean
    Client elasticSearchClient() {
        Node node = NodeBuilder.nodeBuilder()
                .clusterName(elasticSearchCluster)
                .node()
        return node.client()
    }



}
