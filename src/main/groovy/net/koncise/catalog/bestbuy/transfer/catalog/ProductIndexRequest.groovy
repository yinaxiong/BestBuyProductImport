package net.koncise.catalog.bestbuy.transfer.catalog

import com.fasterxml.jackson.annotation.JsonProperty

class ProductIndexRequest {

    String productId
    String name
    BigDecimal price
    @JsonProperty('saleprice')
    BigDecimal salePrice
    @JsonProperty('onsale')
    Boolean onSale
    Boolean active
    Date activeUpdateDate
    List<CategoryPathElement> categoryPath
    String shortDescription
    String shortDescriptionHtml
    String longDescription
    String longDescriptionHtml
    String url
    Boolean digital
    Boolean preowned
    Boolean inStoreAvailability
    Boolean onlineAvailability
    Boolean marketplace
    String image
    String mediumImage
    String thumbnailImage
    String importJobGuid
    Date importJobDate

}
