package net.koncise.catalog.bestbuy.service

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import net.koncise.catalog.bestbuy.transfer.bestbuy.Product
import net.koncise.catalog.bestbuy.transfer.catalog.CategoryPathElement
import net.koncise.catalog.bestbuy.transfer.catalog.ProductIndexRequest
import org.elasticsearch.action.bulk.BulkRequestBuilder
import org.elasticsearch.action.bulk.BulkResponse
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.client.Client
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@Slf4j
class CatalogIndexService {

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    Client elasticSearchClient

    @Value('${elasticsearch.catalog}')
    String catalogName

    @Value('${elasticsearch.type.bestbuy}')
    String bestBuyType

    IndexResponse indexProduct(Product product) {
        log.info "Indexing product ${product.sku} named ${product.name}"
        ProductIndexRequest indexRequest = createProductIndexRequest(product)

        IndexResponse indexResponse = elasticSearchClient
                .prepareIndex(catalogName, bestBuyType, indexRequest.productId)
                .setSource(objectMapper.writeValueAsString(indexRequest))
                .execute()
                .actionGet()
        log.info("Product created with id ${indexResponse.id}: ${indexResponse.created}")
        return indexResponse
    }

    BulkResponse indexProducts(List<Product> productList) {
        BulkRequestBuilder bulkRequest = elasticSearchClient.prepareBulk()

        productList.each { Product product ->
            ProductIndexRequest indexRequest = createProductIndexRequest(product)
            // either use client#prepare, or use Requests# to directly build index/delete requests
            bulkRequest.add(elasticSearchClient
                    .prepareIndex(catalogName, bestBuyType, indexRequest.productId)
                    .setSource(objectMapper.writeValueAsString(indexRequest)))
        }

        BulkResponse bulkResponse = bulkRequest.execute().actionGet()
        if (bulkResponse.hasFailures()) {
            log.error bulkResponse.buildFailureMessage()
        }
        return bulkResponse
    }

    private static ProductIndexRequest createProductIndexRequest(Product product) {
        return new ProductIndexRequest(
                productId: product.sku,
                name: product.name,
                price: product.regularPrice,
                salePrice: product.salePrice,
                onSale: product.isOnSale,
                active: product.isActive,
                activeUpdateDate: product.activeUpdatedDate?.toDate(),
                categoryPath: product.categoryPath.collect { new CategoryPathElement(id: it.id, name: it.name) },
                shortDescription: product.shortDescription,
                shortDescriptionHtml: product.shortDescriptionHtml,
                longDescription: product.longDescription,
                longDescriptionHtml: product.longDescriptionHtml,
                url: product.url,
                digital: product.isDigital,
                preowned: product.isPreowned,
                inStoreAvailability: product.hasInStoreAvailability,
                onlineAvailability: product.hasOnlineAvailability,
                marketplace: product.isMarketplace,
                image: product.image,
                mediumImage: product.mediumImage,
                thumbnailImage: product.thumbnailImage,
                importJobGuid: UUID.randomUUID() as String,
                importJobDate: new Date()
        )
    }
}
