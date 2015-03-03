package net.koncise.catalog.bestbuy

import groovy.util.logging.Slf4j
import net.koncise.catalog.bestbuy.controller.ImportController
import org.elasticsearch.client.Client
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.Import

@Slf4j
@Import(BestBuyImportJobConfig)
class BestBuyImportJobApp implements CommandLineRunner {

    @Autowired
    private ImportController importController

    @Autowired
    private Client elasticSearchClient

    public static void main(String... args) {
        SpringApplication.run(BestBuyImportJobApp, args)
    }

    @Override
    public void run(String... args) throws Exception {

        CliBuilder cli = new CliBuilder(usage: 'java -jar BestBuyProductImport.jar -d')
        // Create the list of options.
        cli.with {
            h longOpt: 'help', 'Show usage information'
            d longOpt: 'download', 'Download a new product file'
        }
        OptionAccessor options = cli.parse(args)

        // Show usage text when -h or --help option is used.
        if (options.h) {
            cli.usage()
            return
        }
        Boolean skipDownload = !(options.d)
        importController.importProducts(skipDownload)
        elasticSearchClient.close()
    }

}
