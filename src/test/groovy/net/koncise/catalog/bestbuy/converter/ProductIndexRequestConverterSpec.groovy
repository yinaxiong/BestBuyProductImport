package net.koncise.catalog.bestbuy.converter

import net.koncise.catalog.bestbuy.transfer.bestbuy.Category
import net.koncise.catalog.bestbuy.transfer.bestbuy.Product
import net.koncise.catalog.bestbuy.transfer.catalog.ProductIndexRequest
import org.joda.time.LocalDateTime
import spock.lang.Specification

class ProductIndexRequestConverterSpec extends Specification {

    def "should convert a Product to a ProductIndexRequest"() {

        setup:
        Product product = new Product(
                sku: 12345,
                name: 'Apple iPod',
                regularPrice: 119.00,
                salePrice: 118.00,
                isOnSale: true,
                isActive: true,
                activeUpdatedDate: new LocalDateTime(2014, 10, 20, 0, 0),
                categoryPath: [new Category(id: 'tech', name: 'Technology')],
                shortDescription: 'Short Description',
                shortDescriptionHtml: '<p>Short Description</p>',
                longDescription: 'Long Description',
                longDescriptionHtml: '<p>Long Description</p>',
                url: 'http://example.com',
                isDigital: false,
                isPreowned: false,
                hasInStoreAvailability: true,
                hasOnlineAvailability: true,
                isMarketplace: false,
                image: 'http://example.com/img',
                mediumImage: 'http://example.com/img/med',
                thumbnailImage: 'http://example.com/img/thumb'
        )

        when:
        ProductIndexRequest productIndexRequest = new ProductIndexRequestConverter().createProductIndexRequest(product)

        then:
        assert productIndexRequest.productId ==  '12345'
        assert productIndexRequest.name == 'Apple iPod'
        assert productIndexRequest.price == 119.00
        assert productIndexRequest.salePrice == 118.00
        assert productIndexRequest.onSale
        assert productIndexRequest.active
        assert productIndexRequest.activeUpdateDate == new LocalDateTime(2014, 10, 20, 0, 0).toDate()
        assert productIndexRequest.categoryPath[0].id == 'tech'
        assert productIndexRequest.categoryPath[0].name == 'Technology'
        assert productIndexRequest.shortDescription == 'Short Description'
        assert productIndexRequest.shortDescriptionHtml == '<p>Short Description</p>'
        assert productIndexRequest.longDescription == 'Long Description'
        assert productIndexRequest.longDescriptionHtml == '<p>Long Description</p>'
        assert productIndexRequest.url == 'http://example.com'
        assert !productIndexRequest.digital
        assert !productIndexRequest.preowned
        assert productIndexRequest.inStoreAvailability
        assert productIndexRequest.onlineAvailability
        assert !productIndexRequest.marketplace
        assert productIndexRequest.image == 'http://example.com/img'
        assert productIndexRequest.mediumImage == 'http://example.com/img/med'
        assert productIndexRequest.thumbnailImage == 'http://example.com/img/thumb'
    }

}
