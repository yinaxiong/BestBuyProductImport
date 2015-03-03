package net.koncise.catalog.bestbuy.converter

import net.koncise.catalog.bestbuy.transfer.bestbuy.Product
import net.koncise.catalog.bestbuy.transfer.catalog.CategoryPathElement
import net.koncise.catalog.bestbuy.transfer.catalog.ProductIndexRequest
import org.springframework.stereotype.Component

@Component
class ProductIndexRequestConverter {

    public ProductIndexRequest createProductIndexRequest(Product product) {
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
