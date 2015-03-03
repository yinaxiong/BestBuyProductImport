package net.koncise.catalog.bestbuy

import net.koncise.catalog.bestbuy.controller.ImportController
import org.elasticsearch.client.Client
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

class BestBuyImportJobAppSpec extends Specification {

    BestBuyImportJobApp bestBuyImportJobApp
    ImportController importControllerMock
    Client elasticSearchClientMock

    void setup() {
        importControllerMock = Mock()
        elasticSearchClientMock = Mock()
        bestBuyImportJobApp = new BestBuyImportJobApp()
        ReflectionTestUtils.setField(bestBuyImportJobApp, 'importController', importControllerMock)
        ReflectionTestUtils.setField(bestBuyImportJobApp, 'elasticSearchClient', elasticSearchClientMock)
    }

    def 'Download a file if the flag is provided'() {

        setup:
        String[] args = ['-d']

        when:
        bestBuyImportJobApp.run(args)

        then:
        1 * importControllerMock.importProducts(false)
        1 * elasticSearchClientMock.close()
        0 * _

    }

    def 'Skip downloading a file if the flag is provided'() {

        setup:
        String[] args = []

        when:
        bestBuyImportJobApp.run(args)

        then:
        1 * importControllerMock.importProducts(true)
        1 * elasticSearchClientMock.close()
        0 * _

    }

}
