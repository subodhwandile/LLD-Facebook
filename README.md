# LLD - Facebook
LLD problems with thier solutions
We will focus on the following set of requirements while designing Facebook:
1. Each member should be able to add information about their basic profile, work
experience, education, etc.
2. Any user of our system should be able to search other members, groups or pages by
their name.
201
202 | P a g e
3. Members should be able to send and accept/reject connection requests from other
members.
4. Members should be able to follow other members without becoming their connection.
5. Members should be able to create groups and pages, as well as join already created
groups and follow pages.
6. Members should be able to create new posts to share with their connections.
7. Members should be able to add comments to posts, as well as like or share a post or
comment.
8. Members should be able to create privacy lists containing their connections. Members
can link any post with a privacy list, to make the post visible to the members of that
privacy list only.
9. Any member should be able to send messages to other members.
10. Any member will be able to add a recommendation for any page.
11. The system should send a notification to a member, whenever there is a new message
or connection invitation or a comment on their post.
12. Members should be able to search through posts for a word.
Extended Requirement: Write a function to find connection suggestion for a member.

import android.graphics.ImageFormat
import android.media.Image
import android.media.ImageReader
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Surface
import java.nio.ByteBuffer

class PreviewFrameExtractor {

    private var imageReader: ImageReader? = null
    private val handlerThread = HandlerThread("ImageReaderThread").apply { start() }
    private val handler = Handler(handlerThread.looper)

    fun setupImageReader(width: Int, height: Int) {
        // Create ImageReader for receiving preview frames
        imageReader = ImageReader.newInstance(width, height, ImageFormat.YUV_420_888, 2).apply {
            setOnImageAvailableListener({ reader ->
                val image = reader.acquireLatestImage()
                image?.let {
                    extractImage(it)
                    it.close()
                }
            }, handler)
        }
    }

    private fun extractImage(image: Image) {
        // Get YUV planes
        val yBuffer = image.planes[0].buffer // Y plane
        val uBuffer = image.planes[1].buffer // U plane
        val vBuffer = image.planes[2].buffer // V plane

        val yBytes = ByteArray(yBuffer.remaining())
        yBuffer.get(yBytes)

        Log.d("ImageReader", "Extracted preview image frame with size: ${yBytes.size}")
    }

    fun getSurface(): Surface? = imageReader?.surface

    fun release() {
        imageReader?.close()
        handlerThread.quitSafely()
    }
}


===

val previewSurface = Surface(textureView.surfaceTexture) // TextureView for displaying preview
val imageCaptureSurface = imageReader.getSurface() // Surface from ImageReader

cameraDevice.createCaptureSession(
    listOf(previewSurface, imageCaptureSurface),
    object : CameraCaptureSession.StateCallback() {
        override fun onConfigured(session: CameraCaptureSession) {
            val captureRequest = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW).apply {
                addTarget(previewSurface) // Display preview
                addTarget(imageCaptureSurface) // Capture frames
            }
            session.setRepeatingRequest(captureRequest.build(), null, handler)
        }

        override fun onConfigureFailed(session: CameraCaptureSession) {
            Log.e("Camera", "Session Configuration Failed")
        }
    },
    handler
)
