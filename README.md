## Best Buy Product Import

This project isn't intended for production use. It was just a mechanism for me to
become more familiar with ElasticSearch.

This project imports Best Buy's product archive into an ElasticSearch index.

### Build Status

[ ![Codeship Status for krichardson/BestBuyProductImport](https://codeship.com/projects/997c53b0-a381-0132-ce0c-366d28abf18c/status?branch=master)](https://codeship.com/projects/66056)

## Setting up your Environment

First, you should obtain an API Key from Best Buy and add it to the application.properties. You can
register for an API key for free at https://developer.bestbuy.com

You'll also need to install and run an ElasticSearch server. See the install instructions for your
system at http://www.elasticsearch.org.

You'll also want to edit application.properties to populate `tempDirectory.input` and
`tempDirectory.output` with wherever you want the application to write files on your system.

## Running the application

The application builds as a fat jar which can be executed like this:

    java -jar BestBuyImportJob.jar -d