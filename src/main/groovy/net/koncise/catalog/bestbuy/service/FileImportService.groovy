package net.koncise.catalog.bestbuy.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import groovy.util.logging.Slf4j
import net.koncise.catalog.bestbuy.transfer.bestbuy.Product
import net.lingala.zip4j.core.ZipFile
import net.lingala.zip4j.exception.ZipException
import net.lingala.zip4j.model.FileHeader
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@Slf4j
class FileImportService {

    @Value('${bestbuy.url}')
    String baseUrl

    @Value('${bestbuy.apiKey}')
    String apiKey

    @Value('${tempDirectory.input}')
    String inputTempDirectory

    @Value('${tempDirectory.output}')
    String outputTempDirectory

    @Autowired
    ObjectMapper objectMapper

    List<FileHeader> downloadFile(String fileName) {
        OkHttpClient client = new OkHttpClient()
        Request request = new Request.Builder()
                .url("${baseUrl}/v1/subsets/${fileName}?apiKey=${apiKey}")
                .build()

        Response response = client.newCall(request).execute()
        InputStream inputStream = response.body().byteStream()

        File archive = new File("${inputTempDirectory}/${fileName}")
        FileOutputStream outputStream = new FileOutputStream(archive)

        IOUtils.copy(inputStream, outputStream)
        inputStream.close()
        outputStream.close()

        try {
            ZipFile zipFile = new ZipFile(archive)
            zipFile.extractAll(outputTempDirectory)
            return zipFile.getFileHeaders()
        } catch (ZipException e) {
            log.error("Error extracting archive" ,e)
        }
        return []
    }

    List<File> getImportableFiles() {
        File folder = new File(outputTempDirectory)
        return folder.listFiles()
    }

    List<Product> readFile(File file) {
        log.info "Reading and deserializing file ${file.absolutePath}"
        return objectMapper.readValue(file, new TypeReference<List<Product>>() {})
    }

    void cleanupFiles() {
        for (File file : importableFiles) {
            try {
                file.delete()
            } catch (Exception e) {
                log.warn "Unable to delete file ${file.absolutePath}", e
            }
        }
    }

}
