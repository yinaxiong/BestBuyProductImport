package net.koncise.catalog.bestbuy.service

import com.fasterxml.jackson.databind.ObjectMapper
import net.koncise.catalog.bestbuy.converter.ProductIndexRequestConverter
import net.koncise.catalog.bestbuy.transfer.bestbuy.Product
import net.koncise.catalog.bestbuy.transfer.catalog.ProductIndexRequest
import org.elasticsearch.action.ListenableActionFuture
import org.elasticsearch.action.bulk.BulkRequestBuilder
import org.elasticsearch.action.bulk.BulkResponse
import org.elasticsearch.action.index.IndexRequestBuilder
import org.elasticsearch.client.Client
import org.elasticsearch.transport.TransportResponse
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

class CatalogIndexServiceSpec extends Specification {

    CatalogIndexService catalogIndexService
    ObjectMapper objectMapperMock
    Client elasticSearchClientMock
    ProductIndexRequestConverter productIndexRequestConverter
    String catalogName
    String bestBuyType

    void setup() {
        objectMapperMock = Mock()
        elasticSearchClientMock = Mock()
        productIndexRequestConverter = new ProductIndexRequestConverter()
        catalogName = 'catalog'
        bestBuyType = 'bestbuy_product'
        catalogIndexService = new CatalogIndexService()
        ReflectionTestUtils.setField(catalogIndexService, 'objectMapper', objectMapperMock)
        ReflectionTestUtils.setField(catalogIndexService, 'elasticSearchClient', elasticSearchClientMock)
        ReflectionTestUtils.setField(catalogIndexService, 'productIndexRequestConverter', productIndexRequestConverter)
        ReflectionTestUtils.setField(catalogIndexService, 'catalogName', catalogName)
        ReflectionTestUtils.setField(catalogIndexService, 'bestBuyType', bestBuyType)

    }

    def "Should map a Product to an Indexable ElasticSearch record"() {

        setup:
        BulkRequestBuilder expectedBulkRequest = Mock()
        IndexRequestBuilder expectedIndexRequestBuilder = Mock()
        String expectedJsonString = '{}'
        ListenableActionFuture<TransportResponse> listenableResponse = Mock()
        BulkResponse expectedBulkResponse = Mock()
        List<Product> products = [new Product(sku: 12345)]

        when:
        catalogIndexService.indexProducts(products)

        then: 'The product is indexed in elastic search'
        1 * elasticSearchClientMock.prepareBulk() >> expectedBulkRequest
        1 * elasticSearchClientMock.prepareIndex(catalogName, bestBuyType, '12345') >> expectedIndexRequestBuilder
        1 * objectMapperMock.writeValueAsString(_ as ProductIndexRequest) >> expectedJsonString
        1 * expectedIndexRequestBuilder.setSource(expectedJsonString) >> expectedIndexRequestBuilder
        1 * expectedBulkRequest.add(expectedIndexRequestBuilder)
        1 * expectedBulkRequest.execute() >> listenableResponse
        1 * listenableResponse.actionGet() >> expectedBulkResponse
        1 * expectedBulkResponse.hasFailures() >> false
        0 * _
    }



}
