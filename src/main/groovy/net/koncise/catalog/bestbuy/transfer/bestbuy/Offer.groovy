package net.koncise.catalog.bestbuy.transfer.bestbuy

import com.fasterxml.jackson.annotation.JsonFormat

class Offer {
    String id
    String heading
    String text
    String url
    String imageUrl
    String type
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="CT")
    Date startDate
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="CT")
    Date endDate
}
