package net.koncise.catalog.bestbuy.transfer.bestbuy

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import org.joda.time.LocalDateTime

class Product {

    Long sku
    Long productId
    String name
    String source
    String type
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="CT")
    Date startDate
    @JsonProperty('new')
    Boolean isNew
    @JsonProperty('active')
    Boolean isActive
    Boolean lowPriceGuarantee
    LocalDateTime activeUpdatedDate
    BigDecimal regularPrice
    BigDecimal salePrice
    @JsonProperty('onSale')
    Boolean isOnSale
    LocalDateTime priceUpdateDate
    @JsonProperty('digital')
    Boolean isDigital
    @JsonProperty('preowned')
    Boolean isPreowned
    String url
    String mobileUrl
    String addToCartUrl
    String upc
    List<Category> categoryPath
    Integer customerReviewCount
    String customerReviewAverage
    @JsonProperty('customerTopRated')
    Boolean isCustomerTopRated
    @JsonProperty('freeShipping')
    Boolean isFreeShipping
    @JsonProperty('freeShippingEligible')
    Boolean isFreeShippingEligible
    @JsonProperty('inStoreAvailability')
    Boolean hasInStoreAvailability
    String inStoreAvailabilityText
    String inStoreAvailabilityTextHtml
    LocalDateTime inStoreAvailabilityUpdateDate
    LocalDateTime itemUpdateDate
    @JsonProperty('onlineAvailability')
    Boolean hasOnlineAvailability
    String onlineAvailabilityText
    String onlineAvailabilityTextHtml
    LocalDateTime onlineAvailabilityUpdateDate
    String longDescription
    String longDescriptionHtml
    List<Detail> details
    List<Feature> features
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="CT")
    Date releaseDate
    BigDecimal shippingCost
    @JsonProperty('specialOrder')
    Boolean isSpecialOrder
    String shortDescription
    String shortDescriptionHtml
    @JsonProperty('class')
    String productClass
    @JsonProperty('classId')
    Integer productClassId
    @JsonProperty('subClass')
    String productSubClass
    @JsonProperty('subClassId')
    Integer productSubClassId
    String department
    Integer departmentId
    Long bestBuyItemId
    String manufacturer
    String image
    String largeFrontImage
    String mediumImage
    String thumbnailImage
    String largeImage
    String alternateViewsImage
    String angleImage
    String backViewImage
    String energyGuideImage
    String leftViewImage
    String accessoriesImage
    String remoteControlImage
    String rightViewImage
    String topViewImage
    String albumTitle
    String artistName
    String artistId
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="CT")
    Date originalReleaseDate
    Boolean parentalAdvisory
    Integer mediaCount
    String monoStereo
    String studioLive
    @JsonProperty('inStorePickup')
    Boolean hasInStorePickup
    @JsonProperty('friendsAndFamilyPickup')
    Boolean hasFriendsAndFamilyPickup
    @JsonProperty('homeDelivery')
    Boolean hasHomeDelivery
    Integer quantityLimit
    String fulfilledBy
    String albumLabel
    String genre
    BigDecimal dollarSavings
    String percentSavings
    String orderable
    String shippingWeight
    List<Offer> offers
    @JsonProperty('marketplace')
    Boolean isMarketplace
    String listingId
    String sellerId
    String shippingRestrictions

}
