import app.meetacy.sdk.files.download
import app.meetacy.sdk.files.upload
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.File
import kotlin.test.Test

class TestFiles {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test uploading and downloading files`() = runTestServer {
        val user = generateTestAccount()

        val inputFile = TestFiles::class.java.getResource("input.png")!!.file.let(::File)
        val outputFile = File(System.getenv("user.dir"), "output.png").apply {
            deleteOnExit()
        }

        val fileId = testApi.files.upload(
            token = user.token,
            source = inputFile,
        ) { uploaded, totalBytes ->
            println("Uploaded ${uploaded}b / ${totalBytes}b (${"%.2f".format(uploaded.toDouble() / totalBytes * 100)}%)...")
        }

        println("Uploaded! File id: $fileId")

        println(testApi.files.get(fileId))

        testApi.files.download(
            fileId = fileId,
            destination = outputFile
        ) { downloaded, totalBytes ->
            println("Downloaded ${downloaded}b / ${totalBytes}b (${"%.2f".format(downloaded.toDouble() / totalBytes * 100)}%)...")
        }

        require(inputFile.length() == outputFile.length())
    }

}
