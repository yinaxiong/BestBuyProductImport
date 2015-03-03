package net.koncise.catalog.bestbuy.controller

import groovy.util.logging.Slf4j
import net.koncise.catalog.bestbuy.service.CatalogIndexService
import net.koncise.catalog.bestbuy.service.FileImportService
import net.koncise.catalog.bestbuy.transfer.bestbuy.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@Slf4j
class ImportController {

    @Value('${bestbuy.activeProductsFilename}')
    String activeProductsFilename

    @Autowired
    FileImportService fileImportService

    @Autowired
    CatalogIndexService catalogIndexService


    Integer importProducts(Boolean skipDownload = true) {

        if (!skipDownload) {
            fileImportService.cleanupFiles()
            fileImportService.downloadFile(activeProductsFilename)
        }
        List<File> files = fileImportService.importableFiles

        Integer productCount = 0
        for (final File file : files) {
            List<Product> productList = fileImportService.readFile(file)
            catalogIndexService.indexProducts(productList)
            productCount += productList.size()
        }
        return productCount

    }

}
